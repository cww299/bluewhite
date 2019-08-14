package com.bluewhite.production.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.finance.dao.CollectInformationDao;
import com.bluewhite.production.finance.dao.NonLineDao;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.NonLine;
import com.bluewhite.production.finance.entity.UsualConsume;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
@Service
public class CollectInformationServiceImpl extends BaseServiceImpl<CollectInformation, Long> implements  CollectInformationService{
	
	
	@Autowired
	private CollectInformationDao dao;
	
	@Autowired
	private BacthService bacthService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FarragoTaskService farragoTaskService;
	
	@Autowired
	private AttendancePayService attendancePayService;
	
	@Autowired
	private UsualConsumeService usualConsumeService;
	
	@Autowired
	private NonLineDao nonLineDao;
	
	@Autowired
	private CollectPayService collectPayService;
	
	
	
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
		double regionalPrice = bacthList.stream().filter(Bacth->Bacth.getRegionalPrice()!=null).mapToDouble(Bacth::getRegionalPrice).sum();
		collectInformation.setRegionalPrice(regionalPrice);
		
		//全表加工费  汇总
		Task task = new Task();
		task.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		task.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		task.setType(collectInformation.getType());
		task.setFlag(0);
		List<Task> taskList = taskService.findPages(task, page).getRows();
		double sumTask = taskList.stream().mapToDouble(Task::getTaskPrice).sum();
		collectInformation.setSumTask(sumTask);
		
		//返工费 汇总
		task.setFlag(1);
		List<Task> taskFlagList = taskService.findPages(task, page).getRows();
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
		double proportion = NumUtils.division(regionalPrice/sumTask);
		collectInformation.setProportion(proportion);
		//我们的表和小关的表差价不予给付
		double priceDifferences = (sumTask-regionalPrice)*( NumUtils.division(regionalPrice/sumTask));
		//预算多余在手部分
		double overtop = 0;
		if(collectInformation.getType()==1 || collectInformation.getType()==2){
			 overtop = regionalPrice > 0 ? 0 : Math.abs(regionalPrice)/0.25*0.2;
		}else{
			 overtop = regionalPrice > 0 ? 0 : Math.abs(regionalPrice);
		}
		collectInformation.setOvertop(overtop);
		
		//没有给一线的已经赚到的
		
		//多给了一线的和打棉的
	
		//已经打算给予A汇总(a工资汇总 + 绩效奖励汇总)
		AttendancePay attendancePay = new AttendancePay();
		attendancePay.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		attendancePay.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		attendancePay.setType(collectInformation.getType());
		List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
		double sumAttendancePay = attendancePayList.stream().mapToDouble(AttendancePay::getPayNumber).sum();
		//绩效奖励汇总
		CollectPay collectPay = new CollectPay();
		collectPay.setOrderTimeBegin(collectInformation.getOrderTimeBegin());
		collectPay.setOrderTimeEnd(collectInformation.getOrderTimeEnd());
		collectPay.setType(collectInformation.getType());
		List<CollectPay>  collectPayList = collectPayService.findPages(collectPay, page).getRows();
		double addPerformancePay = 0;
		if(collectInformation.getType()==3 || collectInformation.getType()==4 || collectInformation.getType()==5){
			   addPerformancePay = collectPayList.stream().filter(CollectPay->CollectPay.getAddPerformancePay()!=null).mapToDouble(CollectPay::getAddPerformancePay).sum(); 
		}else{
			addPerformancePay = collectPayList.stream().filter(CollectPay->CollectPay.getHardAddPerformancePay()!=null).mapToDouble(CollectPay::getHardAddPerformancePay).sum(); 
		}
		collectInformation.setSumAttendancePay(sumAttendancePay + addPerformancePay);
		
		//我们可以给予一线的
		task.setFlag(null);
		taskList = taskService.findPages(task, page).getRows();
		//任务价值汇总
		double sumTaskOne = taskList.stream().mapToDouble(Task::getTaskPrice).sum();
		//b工资净值汇总
		double sumPayB = taskList.stream().mapToDouble(Task::getPayB).sum();
		//产生的管理费汇总
		double sumManage = sumTaskOne-sumPayB;
		collectInformation.setManage(sumManage);
		//H和N相差天数
		double days = 0;
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
		List<UsualConsume> usualConsumeList = usualConsumeService.findPages(usualConsume, page).getRows();
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
		
		//净管理费给付比
		double manageProportion = 0;
		if(collectInformation.getType()==1 || collectInformation.getType()==2){
			 manageProportion = 0.18;
		}else if(collectInformation.getType()==3){
			manageProportion = 0.11;
		}else if(collectInformation.getType()==5){
			manageProportion = 0.4;
		}
		collectInformation.setManageProportion(manageProportion);
		
		//从开始日至今可发放管理费加绩比
		double managePerformanceProportion = surplusManage*manageProportion;
		collectInformation.setManagePerformanceProportion(managePerformanceProportion);
		
		//模拟当月非一线人员出勤小时
		double  analogTime = 0;
		if(collectInformation.getType()==1 || collectInformation.getType()==4 || collectInformation.getType()==5){
			  analogTime = 450;
		}else{
			List<NonLine> nonLine = nonLineDao.findAll();
			analogTime = nonLine.stream().filter(NonLine->NonLine.getChangeTime()!=null).mapToDouble(NonLine::getChangeTime).sum();
		}
		collectInformation.setAnalogTime(analogTime);
		
		//每小时可发放
		double grant =  NumUtils.division( managePerformanceProportion/analogTime );
		collectInformation.setGrants(grant);
		
		//该车间事故损耗
		//给付后车间剩余
		double giveSurplus =(1-manageProportion)*surplusManage;
		collectInformation.setGiveSurplus(giveSurplus);
		//其中股东收益
		double shareholder = 0;
		if(collectInformation.getShareholderProportion()!=null){
			shareholder = giveSurplus * collectInformation.getShareholderProportion();
		}
		collectInformation.setShareholder(shareholder);
		//车间剩余
		double workshopSurplus = giveSurplus - shareholder;
		collectInformation.setWorkshopSurplus(workshopSurplus);
		if(collectInformation.getStatus()==null){
			CollectInformation ct = dao.findByType(collectInformation.getType());
			if(ct!=null){
				collectInformation.setId(ct.getId());
			}
			dao.save(collectInformation);
		}
		return collectInformation;
	}
	
	//将之前的数据汇总到现在的数据中，作为起点
	private void beforeData(CollectInformation collectInformation){
		
		switch (collectInformation.getType()) {
		case 1:// 生产部一楼质检
			
			break;
		case 2://生产部一楼打包
			
			break;
		case 3://生产部二楼针工
			
			break;
		case 4://生产部二楼机工
			
			break;
		case 5://八号裁剪
			
			break;
		default:
			break;
		}
		
	}

	@Override
	public CollectInformation savaDepartmentalExpenditure(CollectInformation collectInformation) {
		CollectInformation ct =	dao.findOne(collectInformation.getId());
		ct.setDepartmentalExpenditure(collectInformation.getDepartmentalExpenditure());
		return dao.save(collectInformation);
	}

	@Override
	public CollectInformation findByType(CollectInformation collectInformation) {
		if(collectInformation.getStatus()!=null){
			return collectInformation(collectInformation);
		}
		return dao.findByType(collectInformation.getType());
	}
	


}
