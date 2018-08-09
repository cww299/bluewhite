package com.bluewhite.production.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.dao.TaskDao;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, Long> implements TaskService{

	@Autowired
	private TaskDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProcedureDao procedureDao;
	@Autowired
	private BacthDao bacthDao;
	@Autowired
	private PayBDao payBDao;

	
	
	private final static String  QUALITY_STRING = "贴破洞";
	
	
	@Override
	@Transactional
	public Task addTask(Task task) {
		//将用户变成string类型储存
		if (!StringUtils.isEmpty(task.getUserIds())) {
			String[] idArr = task.getUserIds().split(",");
			task.setUsersIds(idArr);
		}
		Double sumTaskPrice = 0.0;
		//将工序ids分成多个任务
		if(task.getProcedureIds().length>0){
			Task newTask = null;
			for (int i = 0; i < task.getProcedureIds().length; i++) {
				newTask = new Task();
				BeanCopyUtils.copyNullProperties(task,newTask);
				Long id = Long.parseLong(task.getProcedureIds()[i]);
				Procedure procedure = procedureDao.findOne(id);
				if(procedure.getName().equals(QUALITY_STRING)){
					newTask.setNumber(task.getHoleNumber());
				}
				newTask.setProcedureId(id);
				newTask.setProcedureName(procedure.getName());
				//二楼特殊业务，当存在实际不为null的时候，先计算出任务数量
				if(task.getTaskTime()!=null && task.getType()==3){
					newTask.setNumber(NumUtils.roundTwo(ProTypeUtils.getTaskNumber(newTask.getTaskTime(), newTask.getType(), procedure.getWorkingTime())));
				}
				
				//预计完成时间（1.工序类型不是返工，预计时间利用公式计算的得出。2.工序类型是返工，手填预计完成时间）
				//当前台传值得预计时间不为null，说明该任务类型是返工类型
				newTask.setFlag(procedure.getFlag());
				
				if(task.getExpectTime()==null){
					newTask.setExpectTime(NumUtils.round(ProTypeUtils.sumExpectTime(procedure,procedure.getType(),newTask.getNumber()), null));
				}
				
				//实际完成时间（1.工序类型不是返工，预计时间等于实际时间，2工序类型是返工，实际完成时间根据公式的出）
				if(task.getExpectTime()==null){
					newTask.setTaskTime(newTask.getExpectTime());
				}else{
					newTask.setTaskTime(NumUtils.round(ProTypeUtils.sumTaskTime(procedure.getWorkingTime(),procedure.getType(),newTask.getNumber()), null));
				}
				
				//预计任务价值（通过预计完成时间得出）（1.工序类型不是返工，预计任务价值通过计算得出   2.工序类型是返工,没有预计任务价值）
				if(task.getExpectTime()==null){
					newTask.setExpectTaskPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(newTask.getExpectTime(), procedure.getType(),0,null), null));
				}else{
					newTask.setExpectTaskPrice(null);
				}
				
				//实际任务价值（通过实际完成时间得出）
				newTask.setTaskPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(newTask.getTaskTime(), procedure.getType(),newTask.getFlag(),null), null));
				
				//B工资净值
				newTask.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(newTask.getTaskPrice(), procedure.getType()), null));
				
				//当任务有加绩情况时
				//任务加绩具体数值
				if(task.getPerformanceNumber()!=null){
					newTask.setPerformancePrice(NumUtils.round(ProTypeUtils.sumtaskPerformancePrice(newTask), null));
				}
				
				dao.save(newTask);
				
				///员工和任务形成多对多关系
				if (task.getUsersIds().length>0) {
					for (int j = 0; j < task.getUsersIds().length; j++) {
						Long userid = Long.parseLong(task.getUsersIds()[j]);
						User user = userDao.findOne(userid);
						//给予每个员工b工资
						PayB payB  = new PayB();
						payB.setUserId(userid);
						payB.setUserName(user.getUserName());
						payB.setBacth(newTask.getBacthNumber());
						payB.setBacthId(newTask.getBacthId());
						payB.setProductId(newTask.getProductId());
						payB.setProductName(newTask.getProductName());
						payB.setTaskId(newTask.getId());
						payB.setType(newTask.getType());
						payB.setAllotTime(newTask.getAllotTime());
						payB.setFlag(newTask.getFlag());
						//计算B工资数值
						payB.setPayNumber(newTask.getPayB()/task.getUsersIds().length);
						//当存在加绩时，计算加绩工资
						if(newTask.getPerformanceNumber()!=null){
							payB.setPerformancePayNumber(newTask.getPerformancePrice()/task.getUsersIds().length);
						}
						payBDao.save(payB);
					}
				}
			}
		}
		

		//查出该批次的所有任务
		Bacth bacth = bacthDao.findOne(task.getBacthId());
		//计算出该批次下所有人的实际成本总和
		int count = 0;
		for(Task ta : bacth.getTasks()){
			sumTaskPrice+=ta.getTaskPrice();
			if(task.getType()==2){
				if(ta.getProcedureName().equals(Constants.BAGABOARD) || ta.getProcedureName().equals(Constants.BOXBOARD)){
					count+=ta.getNumber();
				}
			}
		};
		if(bacth.getNumber()==count){
			bacth.setStatus(1);
			bacth.setStatusTime(task.getAllotTime());
		}
		bacth.setSumTaskPrice(sumTaskPrice);
		//计算出该批次的地区差价
		if(bacth.getFlag()==0){
			bacth.setRegionalPrice(NumUtils.round(ProTypeUtils.sumRegionalPrice(bacth, bacth.getType()), null));
		}
		bacthDao.save(bacth);
		return task;
	}
	
	@Override
	public PageResult<Task> findPages(Task param, PageParameter page) {
		 Page<Task> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按id过滤
	        	if (param.getBacthId() != null) {
					predicate.add(cb.equal(root.get("bacthId").as(Long.class),param.getBacthId()));
				}
	        	//按批次号
	        	if(!StringUtils.isEmpty(param.getBacthNumber())){
	        		predicate.add(cb.like(root.get("bacthNumber").as(String.class),"%"+param.getBacthNumber()+"%"));
	        	}
	        	//按产品名称
	        	if(!StringUtils.isEmpty(param.getProductName())){
	        		predicate.add(cb.like(root.get("productName").as(String.class), "%"+param.getProductName()+"%"));
	        	}
	        	//按工序类型
	        	if(!StringUtils.isEmpty(param.getProcedureTypeId())){
	        		predicate.add(cb.equal(root.get("procedure").get("procedureTypeId").as(Long.class), param.getProcedureTypeId()));
	        	}
	        	
	        	//按工序id
	        	if(!StringUtils.isEmpty(param.getProcedureId())){
	        		predicate.add(cb.equal(root.get("procedureId").as(Long.class), param.getProcedureId()));
	        	}
	        	
	        	//按类型
	        	if(!StringUtils.isEmpty(param.getType())){
	        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
	        	}
	          
	        	//按返工类型
	        	if(!StringUtils.isEmpty(param.getFlag())){
	        		predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
	        	}
	        	
	         	//机工楼层区分
	        	if(!StringUtils.isEmpty(param.getMachinist())){
	        		predicate.add(cb.equal(root.get("bacth").get("machinist").as(Integer.class), param.getMachinist()));
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
	        PageResult<Task> result = new PageResult<>(pages,page);
	        return result;
	}

	@Override
	@Transactional
	public void deleteTask(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					//同时删除B工资
					List<PayB> payB = payBDao.findByTaskId(id);
					if(payB.size()>0){
						payBDao.delete(payB);
					}
					//更新该批次的数值(sumTaskPrice(总任务价值),regionalPrice（地区差价）)
					Task task = dao.findOne(id);
					Bacth bacth = task.getBacth();
					Double sumTaskPrice = 0.0;
					//计算出该批次下所有人的实际成本总和
					CopyOnWriteArraySet<Task> taskset = new CopyOnWriteArraySet<Task>(bacth.getTasks());		
					for(Task ta : taskset){
						//排除要删除的任务id
						if(!ta.getId().equals(id)){
							sumTaskPrice+=ta.getTaskPrice();
						}else{
							 dao.delete(ta);
							 bacth.getTasks().remove(ta);
						}
					};
					bacth.setSumTaskPrice(sumTaskPrice);
					if(bacth.getFlag()==0){
						//计算出该批次的地区差价
						bacth.setRegionalPrice(NumUtils.round(ProTypeUtils.sumRegionalPrice(bacth, bacth.getType()), null));
					}
					//更新批次
					bacthDao.save(bacth);
				};
			}
		}
	}

	@Override
	public List<Task> assembleTask(Task task) {
		
		Integer number = task.getNumber();
		List<Task> taskList = new ArrayList<Task>();
		
		//总时间
		double sumTime = 0;
		//将时间段转变成数组,计算出总时间
		if (!StringUtils.isEmpty(task.getTimes())) {
			String[] timeArr = task.getTimes().split(",");
			if (timeArr.length>0) {
				for (int i = 0; i <timeArr.length; i++) {
					if(!timeArr[i].equals("delete")){
						double time = Double.valueOf(timeArr[i]);
						sumTime += time;
					}
				}
			}
		}
		
		int sumNumber = 0;
		//将时间段转变成数组
		if (!StringUtils.isEmpty(task.getTimes())) {
			String[] timeArr = task.getTimes().split(",");
			if (timeArr.length>0) {
				for (int i = 0; i <timeArr.length; i++) {
					Task tasks = new Task();
					double time = 0;
					if(!timeArr[i].equals("delete")){
					time = Double.valueOf(timeArr[i]);
					tasks.setBacthId(task.getBacthId());
					tasks.setExpectTime(task.getExpectTime());
					tasks.setAllotTime(task.getAllotTime());
					tasks.setType(task.getType());
					tasks.setBacthNumber(task.getBacthNumber());
					tasks.setProductName(task.getProductName());
					tasks.setProcedureIds(task.getProcedureIds());
					tasks.setPerformance(task.getPerformance());
					tasks.setPerformanceNumber(task.getPerformanceNumber());
					tasks.setNumber(NumUtils.roundInt(time*number/sumTime));
					tasks.setHoleNumber(NumUtils.roundInt(time*number/sumTime));
					sumNumber+=tasks.getNumber();
					if(i==timeArr.length-1){
						tasks.setNumber(tasks.getNumber()+(number-sumNumber));
					}
					//将用户段转换成数组,将同顺序的用户添加到任务中
					if (!StringUtils.isEmpty(task.getUsers())) {
						String[] userArr = task.getUsers().split("\\.");
						if (userArr.length>0) {
							if(!timeArr[i].equals("delete")){
							tasks.setUserIds(userArr[i]);
							}
						}
					}
					taskList.add(tasks);
				}
				}
			}
		}
		return taskList;
	
	}

	@Override
	@Transactional
	public int updateTask(String ids) throws Exception{
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Task task = dao.findOne(id);
					if(task.getStatus()==null){
						throw new ServiceException("编号为"+id+"任务，未开始，无法结束，请核实后操作");
					}
					//先停止任务，更新出实际时间
					this.getTaskActualTime(id, 2);
					
					//查出该任务的所有b工资并删除
					List<PayB> payBList = payBDao.findByTaskId(id);
					if(payBList.size()>0){
						payBDao.delete(payBList);
					}
					//更新为结束状态
					task.setStatus(2);
					
					//实际任务价值（通过实际完成时间得出）
					task.setTaskPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(task.getTaskActualTime(), task.getType(),0,null), null));
					//B工资净值
					task.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(task.getTaskPrice(),  task.getType()), null));
					dao.save(task);
					//将用户变成string类型储存
					if (!StringUtils.isEmpty(task.getUserIds())) {
						String[] taskArr = task.getUserIds().split(",");
						for (int j= 0; j < taskArr.length; j++) {
							Long userid = Long.parseLong(taskArr[j]);
							User user = userDao.findOne(userid);
							//给予每个员工b工资
							PayB payB  = new PayB();
							payB.setUserId(userid);
							payB.setUserName(user.getUserName());
							payB.setBacth(task.getBacthNumber());
							payB.setBacthId(task.getBacthId());
							payB.setProductName(task.getProductName());
							payB.setTaskId(task.getId());
							payB.setType(task.getType());
							payB.setAllotTime(task.getAllotTime());
							payB.setFlag(task.getFlag());
							//计算B工资数值
							payB.setPayNumber(task.getPayB()/taskArr.length);
							payBDao.save(payB);
							count++;
						}
					}
				}
			}
		}
		return count;
		
	}

	
	@Override
	public void getTaskActualTime(Long id,Integer status) throws Exception {
			Task task = dao.findOne(id);
			//开始
			if(status==0){
				if(task.getStatus()==null){
					task.setStartTime(new Date());
				}else if(task.getStatus()==2){
					throw new ServiceException("任务编号为"+id+"的任务已经结束，无法开始或暂停");
				}
			
			}
			//暂停
			if(status==1){
				if(task.getStatus()==null){
					throw new ServiceException("任务编号为"+id+"的任务未开始，无法暂停或结束，请先开始任务");
				} 
				if(task.getStatus()==2){
					throw new ServiceException("任务编号为"+id+"的任务已经结束，无法开始或暂停");
				}
				//得到任务实时时间
				task.setTaskActualTime(DatesUtil.getTime(task.getStartTime(),new Date()));
				//同时更新开始时间
				task.setStartTime(new Date());
			}
			//结束
			if(status==2){
				if(task.getStatus()==null){
					throw new ServiceException("任务编号为"+id+"的任务未开始，无法暂停或结束，请先开始任务");
				} 
				//得到任务实时时间
				task.setTaskActualTime(DatesUtil.getTime(task.getStartTime(),new Date()));
			}
			task.setStatus(status);
			dao.save(task);
		}

	@Override
	@Transactional
	public Task upTask(Task task) {
		Integer number = task.getNumber();
		Date allotTime = task.getAllotTime();
		task = dao.findOne(task.getId());
		//查出该任务的所有b工资并删除
		List<PayB> payBList = payBDao.findByTaskId(task.getId());
		if(payBList.size()>0){
			payBDao.delete(payBList);
		}
		task.setAllotTime(allotTime);
		task.setNumber(number);
		
		//预计时间
		task.setExpectTime(NumUtils.round(ProTypeUtils.sumTaskTime(task.getProcedure().getWorkingTime(),task.getType(),number), null));
		//实际时间
		task.setTaskTime(task.getExpectTime());
		//预计完成价值
		task.setExpectTaskPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(task.getExpectTime(), task.getType(),0,null), null));
		//实际任务价值（通过实际完成时间得出）
		task.setTaskPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(task.getTaskTime(), task.getType(),0,null), null));
		//B工资净值
		task.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(task.getTaskPrice(),  task.getType()), null));
		dao.save(task);
		//将用户变成string类型储存
		if (!StringUtils.isEmpty(task.getUserIds())) {
			String[] taskArr = task.getUserIds().split(",");
			for (int j= 0; j < taskArr.length; j++) {
				Long userid = Long.parseLong(taskArr[j]);
				User user = userDao.findOne(userid);
				//给予每个员工b工资
				PayB payB  = new PayB();
				payB.setUserId(userid);
				payB.setUserName(user.getUserName());
				payB.setBacth(task.getBacthNumber());
				payB.setBacthId(task.getBacthId());
				payB.setProductName(task.getProductName());
				payB.setTaskId(task.getId());
				payB.setType(task.getType());
				payB.setAllotTime(task.getAllotTime());
				payB.setFlag(task.getFlag());
				//计算B工资数值
				payB.setPayNumber(task.getPayB()/taskArr.length);
				payBDao.save(payB);
			}
		
		}
		return task;
	}

	@Override
	public Integer getTaskNumber(Task task) {
		if(task.getProcedureIds().length>0){
			Task newTask = null;
			for (int i = 0; i < task.getProcedureIds().length; i++) {
				newTask = new Task();
				BeanCopyUtils.copyNullProperties(task,newTask);
				Long id = Long.parseLong(task.getProcedureIds()[i]);
				Procedure procedure = procedureDao.findOne(id);
				newTask.setNumber(NumUtils.roundTwo(ProTypeUtils.getTaskNumber(newTask.getTaskTime(), newTask.getType(), procedure.getWorkingTime())));
			}
		}
		return null;
		
	}

	@Override
	@Transactional
	public Task addReTask(Task task) {
		//将用户变成string类型储存
				if (!StringUtils.isEmpty(task.getUserIds())) {
					String[] idArr = task.getUserIds().split(",");
					task.setUsersIds(idArr);
				}
				//当数量不为null，计算出实际完成时间
				if(task.getNumber()!=null){
					task.setTaskTime(NumUtils.round(ProTypeUtils.sumFarragoTaskTime(task.getTaskTime(),task.getType(),task.getNumber()), null));
				}
				//返工任务价值
				task.setTaskPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(task.getTaskTime(), task.getType(),0,task.getAC5()), null));
				
				//B工资净值
				task.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(task.getTaskPrice(), task.getType()), null));
				
				task = dao.save(task);
				//将返工工资统计成流水
				if (task.getUsersIds().length>0) {
					for (int j = 0; j < task.getUsersIds().length; j++) {
						Long userid = Long.parseLong(task.getUsersIds()[j]);
						User user = userDao.findOne(userid);
						//给予每个员工b工资
						PayB payB  = new PayB();
						payB.setUserId(userid);
						payB.setUserName(user.getUserName());
						payB.setBacth(task.getBacthNumber());
						payB.setBacthId(task.getBacthId());
						payB.setProductId(task.getProductId());
						payB.setProductName(task.getProductName());
						payB.setTaskId(task.getId());
						payB.setType(task.getType());
						payB.setAllotTime(task.getAllotTime());
						payB.setFlag(task.getFlag());
						//计算B工资数值
						payB.setPayNumber(task.getPayB()/task.getUsersIds().length);
						payBDao.save(payB);
					}
				}
				return dao.save(task);
			}

	@Override
	@Transactional
	public void deleteReTask(String ids) {
			if (!StringUtils.isEmpty(ids)) {
				String[] idArr = ids.split(",");
				if (idArr.length>0) {
					for (int i = 0; i < idArr.length; i++) {
						Long id = Long.parseLong(idArr[i]);
						//同时删除B工资
						List<PayB> payB = payBDao.findByTaskId(id);
						if(payB.size()>0){
							payBDao.delete(payB);
						}
						 dao.delete(id);
					}
				}
			}
		}


}
