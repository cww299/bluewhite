package com.bluewhite.production.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.finance.dao.CollectPayDao;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.PayB;
@Service
public class PayBServiceImpl extends BaseServiceImpl<PayB, Long> implements PayBService{
	
	@Autowired
	private PayBDao dao;
	@Autowired
	private AttendancePayService AttendancePayService;
	@Autowired
	private FarragoTaskPayService farragoTaskPayService;
	@Autowired
	private CollectPayDao collectPayDao;
	@Autowired
	private CollectPayService collectPayService;
	
	@Override
	public PageResult<PayB> findPages(PayB param, PageParameter page) {
			  page.setSort(null);
			  Page<PayB> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (param.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
					}
		         	//按员工id过滤
		        	if (param.getUserId() != null) {
						predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
					}
		           	//按分组id过滤
		        	if (param.getGroupId() != null) {
						predicate.add(cb.equal(root.get("user").get("groupId").as(Long.class),param.getGroupId()));
					}
		        	//按产品id
		        	if(param.getProductId()!=null){
		        		predicate.add(cb.equal(root.get("productId").as(Long.class),param.getProductId()));
		        	}
		        	//按id
		        	if(!StringUtils.isEmpty(param.getTaskId())){
		        		predicate.add(cb.equal(root.get("taskId").as(Long.class),param.getTaskId()));
		        	}
		        	//按产品名称
		        	if(!StringUtils.isEmpty(param.getProductName())){
		        		predicate.add(cb.like(root.get("productName").as(String.class), "%"+param.getProductName()+"%"));
		        	}
		        	//按员工姓名
		        	if(!StringUtils.isEmpty(param.getUserName())){
		        		predicate.add(cb.like(root.get("userName").as(String.class), "%"+param.getUserName()+"%"));
		        	}
		        	//按批次
		        	if(!StringUtils.isEmpty(param.getBacth())){
		        		predicate.add(cb.like(root.get("bacth").as(String.class), "%"+param.getBacth()+"%"));
		        	}
		        	//按类型
		        	if(!StringUtils.isEmpty(param.getType())){
		        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
		        	}
		            //按时间过滤
					if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
						predicate.add(cb.between(root.get("allotTime").as(Date.class),
								param.getOrderTimeBegin(),
								param.getOrderTimeEnd()));
					}
					Predicate[] pre = new Predicate[predicate.size()];
					query.where(predicate.toArray(pre));
		        	return null;
		        }, page);
			  PageResult<PayB> result = new PageResult<>(pages, page);
		      return result;
		    }

	@Override
	public List<CollectPay> collectPay(CollectPay collectPay) {
		List<CollectPay> collectPayList = new ArrayList<CollectPay>();
		//A当天工资
		AttendancePay attendancePay = new AttendancePay();
		attendancePay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
		attendancePay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
		attendancePay.setType(collectPay.getType());
		List<AttendancePay> attendancePayList = AttendancePayService.findAttendancePayNoId(attendancePay);
		//B当天工资
		PayB payB = new PayB();
		payB.setOrderTimeBegin(collectPay.getOrderTimeBegin());
		payB.setOrderTimeEnd(collectPay.getOrderTimeEnd());
		payB.setType(collectPay.getType());
		List<PayB> payBList = this.findPayBTwo(payB);
		
		//杂工当天工资
		FarragoTaskPay farragoTaskPay = new FarragoTaskPay();
		farragoTaskPay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
		farragoTaskPay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
		farragoTaskPay.setType(collectPay.getType());
		List<FarragoTaskPay> farragoTaskPayList = farragoTaskPayService.findFarragoTaskPayTwo(farragoTaskPay);
		
		for(AttendancePay attendance : attendancePayList ){
			CollectPay collect = new CollectPay();
			collect.setType(attendance.getType());
			collect.setUserId(attendance.getUserId());
			collect.setUserName(attendance.getUserName());
			collect.setTime(attendance.getWorkTime());
			collect.setAllotTime(collectPay.getOrderTimeBegin());
			collect.setPayA(attendance.getPayNumber());
			//个人b工资
			List<PayB> BList = payBList.stream().filter(PayB->PayB.getUserId().equals(attendance.getUserId())).collect(Collectors.toList());
			//个人杂工工资
			List<FarragoTaskPay> FList = farragoTaskPayList.stream().filter(FarragoTaskPay->FarragoTaskPay.getUserId().equals(attendance.getUserId())).collect(Collectors.toList());
			//b工资加上加绩工资，加上杂工和杂工绩效 。 一天的总工资
			Double sumPayB = 0.0;
			Double sumPayF = 0.0;
			List<Double> listDoubleB = new ArrayList<>();
			List<Double> listDoubleF = new ArrayList<>();
			 if(BList.size()>0){
				 BList.stream().forEach(c->{
					 listDoubleB.add(NumUtils.sum(NumUtils.setzro(c.getPayNumber()), NumUtils.setzro(c.getPerformancePayNumber())));
			    	});
				 sumPayB = NumUtils.sum(listDoubleB);
			    }
			 if(FList.size()>0){
				 FList.stream().forEach(c->{
					 listDoubleF.add(NumUtils.sum(NumUtils.setzro(c.getPayNumber()), NumUtils.setzro(c.getPerformancePayNumber())));
			    	});
				 sumPayF = NumUtils.sum(listDoubleF);
			    }
		 
			//b工资+杂工工资
			collect.setPayB(NumUtils.sum(sumPayB, sumPayF));
			//整体上浮后的B
			collect.setAddPayB(NumUtils.mul(collect.getPayB(), collectPay.getAddNumber()));
			//个人调节系数
			collect.setAddSelfNumber(1.0);
			//考虑个人调节上浮后的B
			collect.setAddSelfPayB(NumUtils.mul(collect.getPayB(),collect.getAddSelfNumber()));
			//上浮后的加绩
			collect.setAddPerformancePay(NumUtils.sub(collect.getAddSelfPayB(),collect.getPayA())>0 ? NumUtils.sub(collect.getAddSelfPayB(),collect.getPayA()) : 0.0);
			//手动调节上浮后的加绩
			collect.setHardAddPerformancePay(collectPay.getHardAddPerformancePay());
			//上浮后无加绩固定给予(当没有考勤的员工无此加绩固定工资)
			collect.setNoPerformanceNumber( collect.getPayA()!=0.0 ? collectPay.getNoPerformancePay() : 0.0);
			//无绩效小时工资
			collect.setNoTimePay(NumUtils.div(collect.getPayA(), collect.getTime(), 5) );
			//有绩效小时工资(取所有工资中的最大项)
			Double timePay = 0.0 ;
			if(collect.getAddSelfPayB()>collect.getPayA()){
				timePay = NumUtils.div(collect.getAddSelfPayB(),collect.getTime(),5);
			}else{
				timePay = NumUtils.div(NumUtils.sum(collect.getPayA(),collect.getNoPerformanceNumber()),collect.getTime(),5);
			}
			collect.setTimePay(timePay);
			
			//查询这条数据是否存在加绩流水表中
			collect.setOrderTimeBegin(collectPay.getOrderTimeBegin());
			collect.setOrderTimeEnd(collectPay.getOrderTimeEnd());
			collect.setType(collectPay.getType());
			CollectPay cpList = collectPayService.findCollectPay(collect);
			if(cpList!=null){
				//根据查询出来的统计数据和计算数据比对，不相同重新计算
				if(cpList.getPayA().equals(collect.getPayA())  && cpList.getPayB().equals(collect.getPayB())){
					collect = cpList;
				}else{
					collect.setAddSelfNumber(cpList.getAddSelfNumber());
					//考虑个人调节上浮后的B
					collect.setAddSelfPayB(NumUtils.mul(collect.getPayB(),collect.getAddSelfNumber()));
					//上浮后的加绩
					collect.setAddPerformancePay(NumUtils.sub(collect.getAddSelfPayB(),collect.getPayA())>0 ? NumUtils.sub(collect.getAddSelfPayB(),collect.getPayA()) : 0.0);
					//上浮后无加绩固定给予(当没有考勤的员工无此加绩固定工资)
					collect.setNoPerformanceNumber( collect.getPayA()!=0.0 ? collectPay.getNoPerformancePay() : 0.0);
					//无绩效小时工资
					collect.setNoTimePay( NumUtils.div(collect.getPayA(), collect.getTime(), 5) );
					//有绩效小时工资(取所有工资中的最大项)
					if(collect.getAddSelfPayB()>collect.getPayA()){
						timePay = NumUtils.div(collect.getAddSelfPayB(),collect.getTime(),5);
					}else{
						timePay = NumUtils.div(NumUtils.sum(collect.getPayA(),collect.getNoPerformanceNumber()),collect.getTime(),5);
					}
					collect.setTimePay(timePay);
					collect.setId(cpList.getId());
					collectPayDao.save(collect);
				}
			}else{
				//将加绩流水入库
				collectPayDao.save(collect);
			};
			collectPayList.add(collect);
		}
		return collectPayList;
	}

	@Override
	public List<PayB> findPayB(PayB payB) {
		return dao.findByUserIdAndTypeAndAllotTimeBetween(payB.getUserId(),payB.getType(),payB.getOrderTimeBegin(),payB.getOrderTimeEnd());
	}

	@Override
	public List<PayB> findPayBTwo(PayB payB) {
		return dao.findByTypeAndAllotTimeBetween(payB.getType(),payB.getOrderTimeBegin(),payB.getOrderTimeEnd());
	}

}
