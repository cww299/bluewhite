package com.bluewhite.production.finance.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.finance.dao.CollectPayDao;
import com.bluewhite.production.finance.dao.NonLineDao;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.GroupProduction;
import com.bluewhite.production.finance.entity.MonthlyProduction;
import com.bluewhite.production.finance.entity.NonLine;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.finance.entity.UsualConsume;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.service.GroupService;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
@Service
public class CollectPayServiceImpl extends BaseServiceImpl<CollectPay, Long> implements CollectPayService{
	
	@Autowired
	private CollectPayDao dao;
	
	@Autowired
	private BacthService bacthService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService TaskService;
	
	@Autowired
	private FarragoTaskService farragoTaskService;
	
	@Autowired
	private AttendancePayService attendancePayService;
	
	@Autowired
	private PayBService payBService;
	
	@Autowired
	private FarragoTaskPayService farragoTaskPayService;
	
	@Autowired
	private UsualConsumeService usualConsumeService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private NonLineDao nonLineDao;
	
	private static String rework = "返工再验";
	
	
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
			double sumPay = psList.stream().filter(CollectPay->CollectPay.getAddPerformancePay()!=null).mapToDouble(CollectPay::getAddPerformancePay).sum();
			//计算A工资总和
			double sumPayA = psList.stream().mapToDouble(CollectPay::getPayA).sum();
			//计算b工资总和
			double sumPayB = psList.stream().mapToDouble(CollectPay::getPayB).sum();
			//计算上浮后b工资总和
			double sumAddPayB = psList.stream().filter(CollectPay->CollectPay.getAddPayB()!=null).mapToDouble(CollectPay::getAddPayB).sum();

			CollectPay collect = new CollectPay();
			collect.setOrderTimeBegin(collectPay.getOrderTimeBegin());
			collect.setOrderTimeEnd(collectPay.getOrderTimeEnd());
			collect.setUserName(psList.get(0).getUserName());
			collect.setAddPerformancePay(sumPay);
			collect.setPayA(sumPayA);
			collect.setPayB(sumPayB);
			collect.setAddPayB(sumAddPayB);
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
		double regionalPrice = bacthList.stream().filter(Bacth->Bacth.getRegionalPrice()!=null).mapToDouble(Bacth::getRegionalPrice).sum();
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
		//我们的表和小关的表差价不予给付
		double priceDifferences = (sumTask-regionalPrice)*(regionalPrice/sumTask);
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
		collectInformation.setManage(sumManage);
		//H和N相差天数
		double days = 0;
		//天数计算值
//		double dayNumber = DatesUtil.getDaySub(collectInformation.getOrderTimeBegin(), collectInformation.getOrderTimeEnd());
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
		double grant =  managePerformanceProportion/analogTime;
		collectInformation.setGrant(grant);
		
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
		double workshopSurplus =giveSurplus - shareholder;
		collectInformation.setWorkshopSurplus(workshopSurplus);
		return collectInformation;
	}

	@Override
	public List<MonthlyProduction> monthlyProduction(MonthlyProduction monthlyProduction) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		List<MonthlyProduction> monthlyProductionList = new ArrayList<MonthlyProduction>();
		long size = DatesUtil.getDaySub(monthlyProduction.getOrderTimeBegin(), monthlyProduction.getOrderTimeEnd());
		
		for(int j=0 ; j<size ; j++){
			Date beginTimes = null;
			if(j!=0){
				//获取下一天的时间
				beginTimes = DatesUtil.nextDay(monthlyProduction.getOrderTimeBegin());
			}else{
				//获取第一天的开始时间
				beginTimes = monthlyProduction.getOrderTimeBegin();
			}
			//获取一天的结束时间
			Date endTimes = DatesUtil.getLastDayOftime(beginTimes);
			Integer type = monthlyProduction.getType();
			monthlyProduction =	new MonthlyProduction();
			monthlyProduction.setOrderTimeBegin(beginTimes);
			monthlyProduction.setOrderTimeEnd(endTimes);
			monthlyProduction.setType(type);
			
				
		
		AttendancePay attendancePay = new AttendancePay();
		attendancePay.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
		attendancePay.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
		attendancePay.setType(monthlyProduction.getType());
		List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
		//考勤人数
		List<AttendancePay> list = attendancePayList.stream().filter(AttendancePay->AttendancePay.getWorkTime()!=0).collect(Collectors.toList());
		if(monthlyProduction.getType()==3){
			//去除管理组的员工
			Group group= new Group();
			group.setKindWorkId((long)116);
			List<Group> groupList = groupService.findList(group);
			for(Group gp : groupList){
				for(User user : gp.getUsers()){
					list = list.stream().filter(AttendancePay->!AttendancePay.getUserId().equals(user.getId()) ).collect(Collectors.toList());
				}
			}
		}
		int peopleNumber = list.size();
		monthlyProduction.setPeopleNumber(peopleNumber);
		//考勤总时间
		double time = list.stream().mapToDouble(AttendancePay::getWorkTime).sum();
		monthlyProduction.setTime(time);
		
		
		//当天产量
		Bacth bacth = new Bacth();
		bacth.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
		bacth.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
		bacth.setType(monthlyProduction.getType());
		List<Bacth> bacthList = bacthService.findPages(bacth, page).getRows();
		double productNumber = 0;
		if(monthlyProduction.getType()==5){
			Map<String, List<Bacth>> map = bacthList.stream().collect(Collectors.groupingBy(Bacth::getBacthNumber,Collectors.toList()));
			for(Object ps : map.keySet()){
				List<Bacth> psList= map.get(ps);
				Map<Long, List<Bacth>> map1 = psList.stream().collect(Collectors.groupingBy(Bacth::getProductId,Collectors.toList()));
					for(Object ps1 : map1.keySet()){
						List<Bacth> psList1= map1.get(ps1);
						if(psList1!=null && psList1.size()<=2){
							productNumber+=psList1.get(0).getNumber();
						}
					}
			}
		}else{
			productNumber = bacthList.stream().mapToDouble(Bacth::getNumber).sum();
		}
		monthlyProduction.setProductNumber(productNumber);
		//当天产值(外发单价乘以质检的个数)
		for(Bacth bac : bacthList){
			  if(monthlyProduction.getType()==3){
				  	bac.setHairPrice(bac.getBacthDeedlePrice());
				}else{
					bac.setHairPrice(bac.getBacthHairPrice());
				}
		}
		double productPrice = bacthList.stream().mapToDouble(Bacth::getProductPrice).sum();
		monthlyProduction.setProductPrice(productPrice);
		
		
		//当类型为机工时，产值和产量的计算方式变化
		if(monthlyProduction.getType()==4){
			
			
			
		}
		
		//返工出勤人数
		Task task = new Task();
		task.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
		task.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
		task.setType(monthlyProduction.getType());
		task.setFlag(1);
		List<Task> taskList = TaskService.findPages(task, page).getRows();
		List<Long> userList = new ArrayList<Long>();
		//返工出勤时间
		double reworkTurnTime = 0;
		for(Task ta : taskList){
			if (!StringUtils.isEmpty(ta.getUserIds())) {
				String[] idArr = ta.getUserIds().split(",");
				if (idArr.length>0) {
					for (int i = 0; i < idArr.length; i++) {
						Long userid = Long.parseLong(idArr[i]);
						User user = userService.findOne(userid);
						if(!userList.contains(userid)){
							attendancePay.setUserId(userid);
							attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
							if(attendancePayList.size()>0){
								reworkTurnTime+=attendancePayList.get(0).getWorkTime();
							}
							if(monthlyProduction.getUserName()!=null){
								monthlyProduction.setUserName(monthlyProduction.getUserName()+","+user.getUserName());
							}else{
								monthlyProduction.setUserName(user.getUserName());
							}
							userList.add(userid);
						}
					}
				}
			}
		}
		//质检返工出勤时间
		if(monthlyProduction.getType()==1 || monthlyProduction.getType()==2 || monthlyProduction.getType()==4){
			monthlyProduction.setReworkTurnTime(reworkTurnTime);
		//针工返工出勤时间
		}else if((monthlyProduction.getType()==3)){
			reworkTurnTime = taskList.stream().mapToDouble(Task::getTaskTime).sum();
			monthlyProduction.setReworkTurnTime(reworkTurnTime);
		}
		//返工再验个数
		double reworkCount = taskList.stream().filter(Task->Task.getProcedureName().equals(rework)).mapToDouble(Task::getNumber).sum();
		monthlyProduction.setReworkCount(reworkCount);
		
		
		int reworkNumber = userList.size();
		monthlyProduction.setReworkNumber(reworkNumber);
		//返工个数
		double rework =  taskList.stream().filter(Task->Task.getNumber()!=null).mapToDouble(Task::getNumber).sum();
		monthlyProduction.setRework(rework-reworkCount);
		//返工时间
		double reworkTime = reworkTurnTime;
		monthlyProduction.setReworkTime(reworkTime);
		if(monthlyProduction.getType()==1){
			monthlyProduction.setPeopleNumber(peopleNumber-reworkNumber);
			monthlyProduction.setTime(time-reworkTurnTime);
		}
		
		//杂工
		if(monthlyProduction.getType()==5){
			FarragoTask farragoTask = new FarragoTask();
			farragoTask.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
			farragoTask.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
			farragoTask.setType(monthlyProduction.getType());
			List<FarragoTask> farragoTaskList = farragoTaskService.findPages(farragoTask, page).getRows();
			double sumTime = farragoTaskList.stream().mapToDouble(FarragoTask::getTime).sum();
			monthlyProduction.setFarragoTaskTime(sumTime);
			monthlyProduction.setFarragoTaskPrice(sumTime*14);;
		}
		monthlyProductionList.add(monthlyProduction);
		
		
		}
		return monthlyProductionList;
	}

	@Override
	public List<Map<String,Object>> bPayAndTaskPay(MonthlyProduction monthlyProduction) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		List<Map<String,Object>> bPayAndTaskPay = new ArrayList<Map<String,Object>>();
		Group gp = new Group();
		gp.setType(3);
		List<Group> groupList = groupService.findList(gp);
		Map<String,Object>  map = null;
			for(Group group : groupList){
				map = new HashMap<String, Object>();
				AttendancePay attendancePay = new AttendancePay();
				attendancePay.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
				attendancePay.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
				attendancePay.setType(monthlyProduction.getType());
				attendancePay.setGroupId(group.getId());
				List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
				List<AttendancePay> list = attendancePayList.stream().filter(AttendancePay->AttendancePay.getWorkTime()!=0).collect(Collectors.toList());
				//考勤总时间
				double sunTime = list.stream().mapToDouble(AttendancePay::getWorkTime).sum();
				
				//B工资
				PayB payB = new PayB();
				payB.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
				payB.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
				payB.setType(monthlyProduction.getType());
				payB.setGroupId(group.getId());
				List<PayB> payBList = payBService.findPages(payB, page).getRows();
				//分组人员B工资总和
				double sumBPay = payBList.stream().mapToDouble(PayB::getPayNumber).sum();
				
				//杂工工资
				FarragoTaskPay farragoTaskPay =new FarragoTaskPay();
				farragoTaskPay.setOrderTimeBegin(monthlyProduction.getOrderTimeBegin());
				farragoTaskPay.setOrderTimeEnd(monthlyProduction.getOrderTimeEnd());
				farragoTaskPay.setType(monthlyProduction.getType());
				farragoTaskPay.setGroupId(group.getId());
				List<FarragoTaskPay> farragoTaskPayList = farragoTaskPayService.findPages(farragoTaskPay, page).getRows();
				//分组人员杂工工资总和
				double sumfarragoTaskPay = farragoTaskPayList.stream().mapToDouble(FarragoTaskPay::getPayNumber).sum();
				
				map.put("name", group.getName());
				map.put("id", group.getId());
				map.put("sunTime", sunTime);
				map.put("sumBPay", sumBPay+sumfarragoTaskPay);
				Double sum = (sumBPay+sumfarragoTaskPay)/sunTime;
				map.put("specificValue", sum.isNaN()?0.0:sum);
				bPayAndTaskPay.add(map);
			}
		return bPayAndTaskPay;
	}

	@Override
	public List<NonLine> headmanPay(NonLine nonLine) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		//A工资
		AttendancePay attendancePay = new AttendancePay();
		attendancePay.setOrderTimeBegin(nonLine.getOrderTimeBegin());
		attendancePay.setOrderTimeEnd(nonLine.getOrderTimeEnd());
		attendancePay.setType(nonLine.getType());
		
		//B工资
		PayB payB = new PayB();
		payB.setOrderTimeBegin(nonLine.getOrderTimeBegin());
		payB.setOrderTimeEnd(nonLine.getOrderTimeEnd());
		payB.setType(nonLine.getType());
		
		List<NonLine> nonLineList = nonLineDao.findAll();
		for(NonLine nl : nonLineList){
			attendancePay.setUserId(nl.getUserId());
			List<AttendancePay> manAttendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
			List<AttendancePay> list = manAttendancePayList.stream().filter(AttendancePay->AttendancePay.getWorkTime()!=0).collect(Collectors.toList());
			//产生考勤工作时间
			double sunTime = list.stream().mapToDouble(AttendancePay::getWorkTime).sum();
			nl.setTime(sunTime);
			
			//A工资总和
			double sumAPay = list.stream().mapToDouble(AttendancePay::getPayNumber).sum();
			payB.setUserId(nl.getUserId());
			List<PayB> payBList = payBService.findPages(payB, page).getRows();
			//B工资总和
			double sumBPay = payBList.stream().mapToDouble(PayB::getPayNumber).sum();
			//产生考勤工资和已发绩效
			double sumABPay = sumAPay+sumBPay;
			nl.setPay(sumABPay);
		}	
		nonLineDao.save(nonLineList);
		return nonLineList;
	}
	
	@Override
	public NonLine updateHeadmanPay(NonLine nonLine) {
		NonLine nl = nonLineDao.findOne(nonLine.getId());
		//将当月产量拼接到往月产量中
		
	
		
		//往月产量解析
		if(nl.getYields()!=null){
			JSONObject nlJsonObj = JSONObject.parseObject(nl.getYields());
			JSONArray nlOn = nlJsonObj.getJSONArray("data");
			for (int i = 0; i < nlOn.size(); i++) {
				JSONObject jo = nlOn.getJSONObject(i); 
				String name =  jo.getString("name");
				//当月产量解析
				JSONObject nonLineJsonObj = JSONObject.parseObject(nonLine.getYields());
				JSONArray nonLineOn = nonLineJsonObj.getJSONArray("data");
				for (int j = 0; j < nonLineOn.size(); j++) {
					String name1 =  nonLineOn.getJSONObject(j).getString("name");
					String value1 =  nonLineOn.getJSONObject(j).getString("value");
					if(name.equals(name1)){
						jo.put("name", name);
						jo.put("value", value1);
					}
				}
			}
			nonLine.setYields(JSONObject.toJSONString(nlJsonObj));
		}
		nl.setYields(nonLine.getYields());
		//产量
		Integer accumulateYield = 0;
		if(nl.getYields()!=null){
			JSONObject jsonObj = JSONObject.parseObject(nl.getYields());
			JSONArray on = jsonObj.getJSONArray("data");
			for (int i = 0; i < on.size(); i++) {
				JSONObject jo = on.getJSONObject(i); 
		         String value =  jo.getString("value");
		         value = value.equals("") ? "0" : value;  
		         accumulateYield+=Integer.parseInt(value);
			}
		}
		//获取各组的产量
		nl.setAccumulateYield(accumulateYield);
		
		//单只协助发货费用/元选择
		if(nonLine.getOnePay()!=null){
			nl.setOnePay(nonLine.getOnePay());
			//累计产生的发货绩效
			nl.setCumulative(nl.getAccumulateYield()*nonLine.getOnePay());
		}
		
		//人为手动加减量化绩效比
		if(nonLine.getAddition()!=null){
			nl.setAddition(nonLine.getAddition());
			//人为改变后产生的量化绩效出勤时间
			nl.setChangeTime(nl.getAddition()*nl.getTime());
		}
	
		
		return nonLineDao.save(nl);
	}
	

	@Override
	public List<CollectPay> twoPerformancePay(CollectPay collectPay) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		List<CollectPay> collectPayList = new ArrayList<CollectPay>();
		AttendancePay attendancePay = new AttendancePay();
		attendancePay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
		attendancePay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
		attendancePay.setType(collectPay.getType());
		attendancePay.setUserName(collectPay.getUserName());
		List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
		//将一个月考勤人员按员工id分组
		Map<Long, List<AttendancePay>> mapCollectPay = attendancePayList.stream().filter(AttendancePay->AttendancePay.getWorkTime()!=0).collect(Collectors.groupingBy(AttendancePay::getUserId,Collectors.toList()));
		CollectPay collect = null;
		for(Object ps : mapCollectPay.keySet()){
			collect = new CollectPay();
			List<AttendancePay> psList= mapCollectPay.get(ps);
			collectPay.setUserId((Long)ps);
			//通过条件查找绩效是否已入库
			List<CollectPay> list = this.findPages(collectPay, page).getRows();
			if(list.size()>0){
				collect = list.get(0);
			}
			//分别统计出考勤总时间
			double sunTime = psList.stream().mapToDouble(AttendancePay::getWorkTime).sum();
			//统计出A工资
			double payA = psList.stream().mapToDouble(AttendancePay::getPayNumber).sum();
			
			
			//B工资
			PayB payB = new PayB();
			payB.setOrderTimeBegin(collectPay.getOrderTimeBegin());
			payB.setOrderTimeEnd(collectPay.getOrderTimeEnd());
			payB.setType(collectPay.getType());
			payB.setUserId((Long)ps);
			List<PayB> payBList = payBService.findPages(payB, page).getRows();
			//分组人员B工资总和
			double sumBPay = payBList.stream().mapToDouble(PayB::getPayNumber).sum();
			
			double sumfarragoTaskPay = 0;
			if(collectPay.getType()==4){
				
				//杂工工资
				FarragoTaskPay farragoTaskPay =new FarragoTaskPay();
				farragoTaskPay.setOrderTimeBegin(collectPay.getOrderTimeBegin());
				farragoTaskPay.setOrderTimeEnd(collectPay.getOrderTimeEnd());
				farragoTaskPay.setType(collectPay.getType());
				farragoTaskPay.setUserId((Long)ps);
				List<FarragoTaskPay> farragoTaskPayList = farragoTaskPayService.findPages(farragoTaskPay, page).getRows();
				//分组人员杂工工资总和
				sumfarragoTaskPay = farragoTaskPayList.stream().mapToDouble(FarragoTaskPay::getPayNumber).sum();
				
			}
			
			
			//确定绩效汇总时间
			collect.setAllotTime(collectPay.getOrderTimeEnd());
			collect.setType(collectPay.getType());
			collect.setTime(sunTime);
			collect.setUserId(psList.get(0).getUserId());
			collect.setUserName(psList.get(0).getUserName());
			//汇总B工资
			if(collectPay.getType()==4){
				collect.setPayB(sumBPay+sumfarragoTaskPay);
			}else{
				collect.setPayB(sumBPay);
			}
			
			//汇总A工资
			collect.setPayA(payA);
			collect.setRatio(NumUtils.round(collect.getPayB()/collect.getPayA()*100,2));
			dao.save(collect);
			collectPayList.add(collect);
		}
		
		return collectPayList;
	}

	@Override
	public CollectPay upadtePerformancePay(CollectPay collectPay) {
		CollectPay	collect = dao.findOne(collectPay.getId());
		if(collectPay.getTimePrice()!=null){
			collectPay.setTimePay(collectPay.getTimePrice()+(collectPay.getAddSelfNumber()==null?0.0:collectPay.getAddSelfNumber()));
			collectPay.setAddPerformancePay(collect.getTime()*collectPay.getTimePay());
			if(collectPay.getTimePrice()!=null && collectPay.getAddSelfNumber()!=null){
				collectPay.setTimePay(collectPay.getTimePrice()+collectPay.getAddSelfNumber());
				collectPay.setAddPerformancePay(collect.getTime()*collectPay.getTimePay());
			}
		}
		
		collect.setTimePrice(collectPay.getTimePrice());
		collect.setTimePay(collectPay.getTimePay());
		collect.setAddSelfNumber(collectPay.getAddSelfNumber());
		collect.setAddPerformancePay(collectPay.getAddPerformancePay());
		dao.save(collect);
		return collect;
	}

	@Override
	public List<CollectPay> cottonOtherTask(CollectPay collectPay) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		
		List<CollectPay> collectPayList = new ArrayList<CollectPay>(); 
		Group group= new Group();
		group.setKindWorkId((long)111);
		List<Group> groupList = groupService.findList(group);
		//组装出只属于充棉工的人员
		CollectPay collect = null;
		for(Group rp: groupList){
			if(rp.getUsers().size()>0){
				for(User us : rp.getUsers()){
					collect = new CollectPay();
					double sumPayNumber = 0;
					
					//任务里没有充棉的类型，所以查出所有的任务
					Task task= new Task();
					task.setOrderTimeBegin(collectPay.getOrderTimeBegin());
					task.setOrderTimeEnd(collectPay.getOrderTimeEnd());
					task.setType(collectPay.getType());
					List<Task> taskList = TaskService.findPages(task, page).getRows();
				
					//遍历任务，组装出符合充棉的任务
					for(Task ta : taskList){
						if (!StringUtils.isEmpty(ta.getUserIds())) {
							String [] ids = ta.getUserIds().split(",");
							if (ids.length>0) {
								for (int i = 0; i < ids.length; i++) {
									Long id = Long.parseLong(ids[i]);
										if(us.getId().equals(id)){
											PayB payb =new PayB();
											payb.setOrderTimeBegin(collectPay.getOrderTimeBegin());
											payb.setOrderTimeEnd(collectPay.getOrderTimeEnd());
											payb.setType(collectPay.getType());
											payb.setUserId(us.getId());
											payb.setTaskId(ta.getId());
											List<PayB> payBList = payBService.findPages(payb, page).getRows();
											double payNumber = payBList.stream().mapToDouble(PayB::getPayNumber).sum();
											sumPayNumber+=payNumber;
											break;
										}
									}		
								}
							}
						}
					//统计充棉组所做费充棉组的任务价值
					collect.setUserName(us.getUserName());
					collect.setPayB(sumPayNumber);
					collectPayList.add(collect);
				}
			}
		}
		return collectPayList;
	}

	@Override
	public List<GroupProduction> groupProduction(GroupProduction groupProduction) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		//检验组
		Group group= new Group();
		group.setKindWorkId((long)113);
		List<Group> groupList = groupService.findList(group);
	

		//查出当日所有检验任务
		Task task= new Task();
		task.setOrderTimeBegin(groupProduction.getOrderTimeBegin());
		task.setOrderTimeEnd(groupProduction.getOrderTimeEnd());
		task.setType(groupProduction.getType());
		task.setProcedureTypeId((long)99);
		List<Task> taskList = TaskService.findPages(task, page).getRows();
		
		List<GroupProduction> groupProductionList = new ArrayList<GroupProduction>();
		
		//将检验任务按产品id分组，统计出数量
		Map<Object, List<Task>> mapTask = taskList.stream().collect(Collectors.groupingBy(Task::getProductId,Collectors.toList()));
		
		Integer oneNumber = null;
		Integer twoNumber = null;
		Integer threeNumber = null;
		Integer fourNumber = null;
		GroupProduction production =null;
		for(Object ps : mapTask.keySet()){
			production = new GroupProduction();
			List<Task> psList= mapTask.get(ps);
			//该产品检验组总数量
			Integer sumNumber = psList.stream().mapToInt(Task::getNumber).sum();
			 oneNumber = 0;
			 twoNumber = 0;
			 threeNumber = 0;
			 fourNumber = 0;
			//遍历任务，通过任务 的员工id和分组人员的员工id相匹配，相同则记录任务数
			for(Task ta : psList){
				Integer dex = null;
				if (!StringUtils.isEmpty(ta.getUserIds())) {
					String [] ids = ta.getUserIds().split(",");
					if (ids.length>0) {
						for (int i = 0; i < ids.length; i++) {
							dex = 0;
							Long id = Long.parseLong(ids[i]);
								//遍历出每个组
									for (int j = 0; j < groupList.size(); j++) {
										dex = 1;
										for(User us : groupList.get(j).getUsers()){
											//当任务员工id等于检验分组员工id时，记录数值，并跳出当前循环人员id，同时，该组的任务数量已被记载，跳出分组循环
											if(us.getId().equals(id)){
												dex=2;
												switch (j) {
												case 0:
													oneNumber+=ta.getNumber();
													break;
												case 1:
													twoNumber+=ta.getNumber();
													break;
												case 2:
													threeNumber+=ta.getNumber();
													break;
												case 3:
													fourNumber+=ta.getNumber();
													break;
												}
												break;
											}
										}
									if(dex==2){
										break;
									}	
							}
							if(dex==2){
								break;
							}		
						}
					}
				}
			}
		
			production.setName(psList.get(0).getProductName());
			production.setOneNumber(oneNumber);
			production.setTwoNumber(twoNumber);
			production.setThreeNumber(threeNumber);
			production.setFourNumber(fourNumber);
			production.setSumNumber(sumNumber);
			production.setOrderTimeBegin(groupProduction.getOrderTimeBegin());
			groupProductionList.add(production);
		}
		return groupProductionList;
	}

	@Override
	public Object getMouthYields(Long id,String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
		long size = 0;
		try {
			size = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth( format.parse(date))),DatesUtil.getLastDayOftime(DatesUtil.getLastDayOfMonth( format.parse(date))));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		JSONObject outData = new JSONObject();
		JSONArray gResTable = new JSONArray(); 
		Date beginTimes = null;
		NonLine nonLine = nonLineDao.findOne(id);
		//当产量不为null时，将数据取出，返回前端
		if(nonLine.getYields()!=null){
			JSONObject jsonObj = JSONObject.parseObject(nonLine.getYields());
			JSONArray on = jsonObj.getJSONArray("data");
			for (int i = 0; i < on.size(); i++) {
				JSONObject jo = on.getJSONObject(i); 
		         String value =  jo.getString("name");
		         try {
					if(DatesUtil.equalsDate(format.parse(value), format.parse(date))){
						gResTable.add(jo);
					 }
				} catch (ParseException e) {
				}
			}
			outData.put("data", gResTable);
			
			//当产量为null时，填充无数据json格式返回
		}
		if(nonLine.getYields()==null || gResTable.size()==0){
			for(int j=0 ; j<size ; j++){
				if(j!=0){
					//获取下一天的时间
					beginTimes = DatesUtil.nextDay(beginTimes);
				}else{
					//获取第一天的开始时间
					try {
						beginTimes = DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth(format.parse(date)));
					} catch (ParseException e) {
					}
				}
				JSONObject name = new JSONObject(); 
				name.put("name",sdf.format(beginTimes));
				name.put("value","");
				gResTable.add(name);
				
				//当月产量字段中没有当月json数据是，将当月json数据拼接到，原产量数据中
				if(nonLine.getYields()!=null){
					JSONObject jsonObj = JSONObject.parseObject(nonLine.getYields());
					JSONArray on = jsonObj.getJSONArray("data");
					on.add(name);
					JSONObject data = new JSONObject();
					data.put("data", on);
					nonLine.setYields(JSONObject.toJSONString(data));
					nonLineDao.save(nonLine);
				}
			
				
				
				
			}
			outData.put("data", gResTable);
		}
		return outData;
		
	}


	
	

}
