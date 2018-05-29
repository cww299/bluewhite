package com.bluewhite.production.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.PayB;
@Service
public class PayBServiceImpl extends BaseServiceImpl<PayB, Long> implements PayBService{
	
	@Autowired
	private PayBDao dao;
	@Autowired
	private AttendancePayService AttendancePayService;
	@Autowired
	private FarragoTaskService farragoTaskService;
	
	@Override
	public PageResult<PayB> findPages(PayB param, PageParameter page) {
			  Page<PayB> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (param.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
					}
		        	//按产品id
		        	if(param.getProductId()!=null){
		        		predicate.add(cb.equal(root.get("productId").as(Long.class),param.getProductId()));
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
		        PageResult<PayB> result = new PageResult<PayB>(pages,page);
		        return result;
		    }

	@Override
	public List<CollectPay> collectPay(CollectPay collectPay) {
		List<CollectPay> collectPayList = new ArrayList<CollectPay>();
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		//A当天工资
		AttendancePay attendancePay = new AttendancePay();
		attendancePay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
		attendancePay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
		List<AttendancePay> attendancePayList = AttendancePayService.findPages(attendancePay, page).getRows();
		//B当天工资
		PayB payB = new PayB();
		payB.setOrderTimeBegin(collectPay.getOrderTimeBegin());
		payB.setOrderTimeEnd(collectPay.getOrderTimeEnd());
		for(AttendancePay attendance : attendancePayList ){
			CollectPay collect = new CollectPay();
			collect.setUserName(attendance.getUserName());
			collect.setTime(attendance.getWorkTime());
			collect.setPayA(attendance.getPayNumber());
			payB.setUserId(attendance.getUserId());
			List<PayB> payBList = this.findPages(payB, page).getRows();
			//b工资加上加绩工资， 一天的总工资
			Double sumPayB = 0.0;
			for(PayB payBs :payBList){
				sumPayB+=((payBs.getPayNumber()!=null ? payBs.getPayNumber() : 0.0)+(payBs.getPerformancePayNumber()!=null ? payBs.getPerformancePayNumber() : 0.0 ));
			}
			collect.setPayB(sumPayB);
			//整体上浮后的B
			collect.setAddPayB(collect.getPayB()*collectPay.getAddNumber());
			//个人调节系数
			collect.setAddSelfNumber(1.0);
			//考虑个人调节上浮后的B
			collect.setAddSelfPayB(collect.getPayB()*collect.getAddSelfNumber());
			//上浮后的加绩
			collect.setAddPerformancePay(collect.getAddSelfPayB()-collect.getPayA()>0 ? collect.getAddSelfPayB()-collect.getPayA() : 0.0);
			//上浮后无加绩固定给予(当没有考勤的员工无此加绩固定工资)
			collect.setNoPerformanceNumber( collect.getPayA()!=0.0 ? collectPay.getNoPerformancePay() : 0.0);
			//无绩效小时工资
			collect.setNoTimePay(collect.getPayA()/collect.getTime());
			//有绩效小时工资(取所有工资中的最大项)
			Double timePay = 0.0 ;
			if(collect.getAddSelfPayB()>collect.getPayA()){
				timePay = collect.getAddSelfPayB()/collect.getTime();
			}else{
				timePay = (collect.getPayA()+collect.getNoPerformanceNumber())/collect.getTime();
			}
			collect.setTimePay(timePay);
			collectPayList.add(collect);
		}
		
		return collectPayList;
	}

}
