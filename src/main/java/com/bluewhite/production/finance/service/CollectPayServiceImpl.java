package com.bluewhite.production.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.finance.dao.CollectPayDao;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.UsualConsume;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
@Service
public class CollectPayServiceImpl extends BaseServiceImpl<CollectPay, Long> implements CollectPayService{
	
	@Autowired
	private CollectPayDao dao;
	
	@Autowired
	private BacthService bacthService;
	
	@Autowired
	private TaskService TaskService;
	
	@Autowired
	private FarragoTaskService farragoTaskService;
	
	@Autowired
	private AttendancePayService attendancePayService;
	
	@Autowired
	private UsualConsumeService usualConsumeservice;
	
	@Override
	public PageResult<CollectPay> findPages(CollectPay param, PageParameter page) {
		 Page<CollectPay> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	         	//按id过滤
	        	if (param.getUserId() != null) {
					predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
				}
	        	//按员工姓名
	        	if(!StringUtils.isEmpty(param.getUserName())){
	        		predicate.add(cb.like(root.get("userName").as(String.class), "%"+param.getUserName()+"%"));
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
	        PageResult<CollectPay> result = new PageResult<CollectPay>(pages,page);
	        return result;
	    }

	@Override
	public List<CollectPay> collect(CollectPay collectPay) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		List<CollectPay>  collectPayList = this.findPages(collectPay, page).getRows();
		Map<Object, List<CollectPay>> mapCollectPay = collectPayList.stream().collect(Collectors.groupingBy(CollectPay::getUserId,Collectors.toList()));
		List<CollectPay> list = new ArrayList<CollectPay>();
		for(Object ps : mapCollectPay.keySet()){
			List<CollectPay> psList= mapCollectPay.get(ps);
			//计算出加绩总和
			double sumPay = psList.stream().mapToDouble(CollectPay::getAddPerformancePay).sum();
			CollectPay collect = new CollectPay();
			collect.setOrderTimeBegin(collectPay.getOrderTimeBegin());
			collect.setOrderTimeEnd(collectPay.getOrderTimeEnd());
			collect.setUserName(psList.get(0).getUserName());
			collect.setAddPerformancePay(sumPay);
			list.add(collect);
		}
		return list;
	}

	@Override
	public CollectInformation collectInformation(CollectInformation collectInformation) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		
		//各批次地区差价汇总(不予给付汇总)
		Bacth bacth = new Bacth();
		bacth.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		bacth.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		bacth.setType(collectInformation.getType());
		List<Bacth> bacthList = bacthService.findPages(bacth, page).getRows();
		double regionalPrice = bacthList.stream().mapToDouble(Bacth::getRegionalPrice).sum();
		collectInformation.setRegionalPrice(regionalPrice);
		
		//全表加工费  汇总
		Task task = new Task();
		task.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		task.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		task.setType(collectInformation.getType());
		task.setFlag(0);
		List<Task> taskList = TaskService.findPages(task, page).getRows();
		double sumTask = taskList.stream().mapToDouble(Task::getTaskPrice).sum();
		collectInformation.setSumTask(sumTask);
		
		//返工费 汇总
		task.setFlag(1);
		List<Task> taskFlagList = TaskService.findPages(task, page).getRows();
		double sumTaskFlag = taskFlagList.stream().mapToDouble(Task::getTaskPrice).sum();
		collectInformation.setSumTaskFlag(sumTaskFlag);
		
		//杂工费 汇总
		FarragoTask farragoTask = new FarragoTask();
		farragoTask.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		farragoTask.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		farragoTask.setType(collectInformation.getType());
		List<FarragoTask> farragoTaskList = farragoTaskService.findPages(farragoTask, page).getRows();
		double sumFarragoTask = farragoTaskList.stream().mapToDouble(FarragoTask::getPrice).sum();
		collectInformation.setSumFarragoTask(sumFarragoTask);
		//全表加工费,返工费和杂工费汇总
		double priceCollect = sumTask+sumTaskFlag+sumFarragoTask;
		collectInformation.setPriceCollect(priceCollect);
		//不予给付汇总占比
		double proportion = regionalPrice/sumTask;
		collectInformation.setProportion(proportion);
		//预算多余在手部分
		double overtop = regionalPrice/0.25*0.2;
		collectInformation.setOvertop(overtop);
		
		//按条件汇总员工费用所有数据
		this.collectInformationPay(collectInformation, page);
		
		return collectInformation;
	}
	
	/**
	 * 按条件汇总员工费用所有数据
	 * @param collectPay
	 * @return
	 */
	private CollectInformation collectInformationPay(CollectInformation collectInformation,PageParameter page) {
		//已经打算给予A汇总(a工资汇总)
		AttendancePay attendancePay = new AttendancePay();
		attendancePay.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		attendancePay.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		attendancePay.setType(collectInformation.getType());
		List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
		double sumAttendancePay = attendancePayList.stream().mapToDouble(AttendancePay::getPayNumber).sum();
		collectInformation.setSumAttendancePay(sumAttendancePay);
		//我们可以给予一线的
		Task task = new Task();
		task.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		task.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		task.setType(collectInformation.getType());
		List<Task> taskList = TaskService.findPages(task, page).getRows();
		//任务价值汇总
		double sumTask = taskList.stream().mapToDouble(Task::getTaskPrice).sum();
		//b工资净值汇总
		double sumPayB = taskList.stream().mapToDouble(Task::getPayB).sum();
		//产生的管理费汇总
		double sumManage = sumTask-sumPayB;
		//H和N相差天数
		double days = 0;
		//天数计算值
		double dayNumber = DatesUtil.getDaySub(collectInformation.getOrderTimeBegin(), collectInformation.getOrderTimeEnd());
		//给予一线
		double giveThread = collectInformation.getPriceCollect()-collectInformation.getRegionalPrice()-sumManage;
		collectInformation.setGiveThread(giveThread);
		//一线剩余给我们
		double surplusThread = giveThread-sumAttendancePay;
		collectInformation.setSurplusThread(surplusThread);
		
		//考虑管理费，预留在手等。可调配资金
		double deployPrice = sumManage+collectInformation.getOvertop()+collectInformation.getSurplusThread();
		collectInformation.setDeployPrice(deployPrice);
		
		//模拟得出可调配资金
		double analogDeployPrice = deployPrice;
		collectInformation.setAnalogDeployPrice(analogDeployPrice);
		
		//从A考勤开始日期以消费的房租
		UsualConsume usualConsume = new UsualConsume();
		usualConsume.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		usualConsume.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		usualConsume.setType(collectInformation.getType());
		List<UsualConsume> usualConsumeList = usualConsumeservice.findPages(usualConsume, page).getRows();
		double sumChummage = usualConsumeList.stream().mapToDouble(UsualConsume::getChummage).sum();
		collectInformation.setSumChummage(sumChummage);
		
		//从A考勤开始日期以消费的水电
		double sumHydropower = usualConsumeList.stream().mapToDouble(UsualConsume::getHydropower).sum();
		collectInformation.setSumHydropower(sumHydropower);
		
		//从A考勤开始日期以消费的后勤
		double sumLogistics = usualConsumeList.stream().mapToDouble(UsualConsume::getLogistics).sum();
		collectInformation.setSumLogistics(sumLogistics);
		
		//模拟当月非一线人员发货绩效
		double analogPerformance = 0;
		collectInformation.setAnalogPerformance(analogPerformance);
		
		//剩余净管理
		double surplusManage = analogDeployPrice-sumChummage-sumHydropower-sumLogistics-analogPerformance;
		collectInformation.setSurplusManage(surplusManage);
		
		//净管理费给付比→
		double manageProportion = 0.18;
		collectInformation.setManageProportion(manageProportion);
		
		//从开始日至今可发放管理费加绩比
		double managePerformanceProportion = surplusManage*manageProportion;
		collectInformation.setManagePerformanceProportion(managePerformanceProportion);
		
		//模拟当月非一线人员出勤小时
		double  analogTime = 450;
		collectInformation.setAnalogTime(analogTime);
		
		//每小时可发放
		double grant =  managePerformanceProportion/analogTime;
		collectInformation.setGrant(grant);
		
		//该车间事故损耗
		//给付后车间剩余
		double giveSurplus =(1-manageProportion)*surplusManage;
		collectInformation.setGiveSurplus(giveSurplus);
		//其中股东收益
		double shareholder = giveSurplus * collectInformation.getShareholderProportion();
		collectInformation.setShareholder(shareholder);
		//车间剩余
		double workshopSurplus =giveSurplus - shareholder;
		collectInformation.setWorkshopSurplus(workshopSurplus);
		return collectInformation;
	}
	

}
