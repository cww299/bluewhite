package com.bluewhite.production.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
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
				newTask.setProcedureId(id);
				newTask.setProcedureName(procedureDao.findOne(id).getName());
				//预计完成时间
				Procedure procedure = procedureDao.findOne(id);
				newTask.setExpectTime(NumUtils.round(ProTypeUtils.sumExpectTime(procedure,procedure.getType(),newTask.getNumber())));
				//任务价值
				newTask.setTaskPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(newTask.getExpectTime(), procedure.getType())));
				//B工资净值
				newTask.setBPrice(NumUtils.round(ProTypeUtils.sumBPrice(newTask.getTaskPrice(),  procedure.getType())));
				dao.save(newTask);
				
				///员工和任务形成多对多关系
				if (task.getUsersIds().length>0) {
					for (int j = 0; j < task.getUsersIds().length; j++) {
						Long userid = Long.parseLong(task.getUsersIds()[j]);
						User user = userDao.findOne(userid);
						if(user.getTaskIds()!=null){
							user.setTaskIds(user.getTaskIds()+","+String.valueOf(newTask.getId()));
						}else{
							user.setTaskIds(user.getTaskIds());
						}
						userDao.save(user);
					}
				}
			}
		}
		//查出该批次的所有任务
		Bacth bacth = bacthDao.findOne(task.getBacthId());
		//计算出该批次下所有人的预计成本总和
		for(Task ta : bacth.getTasks()){
			sumTaskPrice+=ta.getTaskPrice();
		};
		bacth.setSumTaskPrice(sumTaskPrice);
		//计算出该批次的地区差价
		bacth.setRegionalPrice(NumUtils.round(ProTypeUtils.sumRegionalPrice(bacth, bacth.getType())));
		
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
	        	//按批次号
	        	if(param.getBacthNumber()!=null){
	        		predicate.add(cb.equal(root.get("bacth").get("bacthNumber").as(String.class),param.getBacthNumber()));
	        	}
	        	//按产品名称
	        	if(!StringUtils.isEmpty(param.getProductName())){
	        		predicate.add(cb.like(root.get("productName").as(String.class), "%"+param.getProductName()+"%"));
	        	}
	        	//按类型
	        	if(!StringUtils.isEmpty(param.getType())){
	        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
	        	}
	            //按时间过滤
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
					predicate.add(cb.between(root.get("createdAt").as(Date.class),
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

}
