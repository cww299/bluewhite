package com.bluewhite.production.task.service;

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
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.ProTypeUtils;
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
	
	@Override
	public Task addTask(Task task) {
		//将工序分成多个任务
		if(task.getProcedureIds().length>0){
			for (int i = 0; i < task.getProcedureIds().length; i++) {
				Long id = Long.parseLong(task.getProcedureIds()[i]);
				task.setProcedureId(id);
				Task newTask = dao.save(task);
				///员工和任务形成多对多关系
				if (task.getUserIds().length>0) {
					for (int j = 0; j < task.getUserIds().length; j++) {
						Long userid = Long.parseLong(task.getUserIds()[j]);
						User user = userDao.findOne(userid);
						user.setTaskIds(user.getTaskIds()+","+String.valueOf(newTask.getId()));
						userDao.save(user);
					}
				}
				//预计完成时间
				Procedure procedure = procedureDao.findOne(newTask.getProcedureId());
				newTask.setExpectTime(ProTypeUtils.sumExpectTime(procedure,procedure.getType(),newTask.getNumber()));
				//任务价值
				dao.save(newTask);
			}
		}
		
		
		
		

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
	        		predicate.add(cb.equal(root.get("productName").as(String.class), "%"+param.getProductName()+"%"));
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
