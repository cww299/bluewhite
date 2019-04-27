package com.bluewhite.production.bacth.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.group.dao.GroupDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.system.user.entity.User;
@Service
public class BacthServiceImpl extends BaseServiceImpl<Bacth, Long> implements BacthService{

	@Autowired
	private BacthDao dao;
	
	@Autowired
	private PayBDao payBDao;
	
	@Autowired
	private ProcedureDao procedureDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private TaskService taskService;
	
	private static String GROUP = "返工组";
	
	
	@Override
	public PageResult<Bacth> findPages(Bacth param, PageParameter page) {
			  Page<Bacth> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (param.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
					}
		        	//按产品id
		        	if(param.getProductId()!=null){
		        		predicate.add(cb.equal(root.get("productId").as(Long.class),param.getId()));
		        	}
		        	//按产品名称
		        	if(!StringUtils.isEmpty(param.getName())){
		        		predicate.add(cb.like(root.get("product").get("name").as(String.class), "%"+StringUtil.specialStrKeyword(param.getName())+"%"));
		        	}
		        	//按产品编号
		        	if(!StringUtils.isEmpty(param.getProductNumber())){
		        		predicate.add(cb.like(root.get("product").get("number").as(String.class), "%"+param.getProductNumber()+"%"));
		        	}
		        	//按批次
		        	if(!StringUtils.isEmpty(param.getBacthNumber())){
		        		predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%"+param.getBacthNumber()+"%"));
		        	}
		        	//按类型
		        	if(!StringUtils.isEmpty(param.getType())){
		        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
		        	}
		           	//按接收状态
		        	if(!StringUtils.isEmpty(param.getReceive())){
		        		predicate.add(cb.equal(root.get("receive").as(Integer.class), param.getReceive()));
		        	}
		         	//按状态
		        	if(!StringUtils.isEmpty(param.getStatus())){
		        		predicate.add(cb.equal(root.get("status").as(Integer.class), param.getStatus()));
		        	}
		        	
		        	//是否返工
		        	if(!StringUtils.isEmpty(param.getFlag())){
		        		predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
		        	}
		        	
		        	//机工楼层区分
		        	if(!StringUtils.isEmpty(param.getMachinist())){
		        		predicate.add(cb.equal(root.get("machinist").as(Integer.class), param.getMachinist()));
		        	}
		        	
		        	if(!StringUtils.isEmpty(param.getStatusTime())){
		        		//按完成时间过滤
						if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd())  ) {
							predicate.add(cb.between(root.get("statusTime").as(Date.class),
									param.getOrderTimeBegin(),
									param.getOrderTimeEnd()));
						}
		        	}else{
		        		//按时间过滤
		        		if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
		        			predicate.add(cb.between(root.get("allotTime").as(Date.class),
		        					param.getOrderTimeBegin(),
		        					param.getOrderTimeEnd()));
		        		}
		        	}
		        	
					Predicate[] pre = new Predicate[predicate.size()];
					query.where(predicate.toArray(pre));
		        	return null;
		        }, SalesUtils.getQueryNoPageParameter());
				  PageResultStat<Bacth> result = new PageResultStat<>(pages,page);
				  result.setAutoStateField("number", "sumTaskPrice","time");
				  result.count();
			  return result;
		    }

	@Override
	@Transactional
	public int deleteBacth(Long id) {
		Bacth bacth = dao.findOne(id);
		for(Task task : bacth.getTasks()){
			//查询出该任务的所有B工资
			List<PayB> payB = payBDao.findByTaskId(task.getId());
			//删除该任务的所有B工资
			payBDao.delete(payB);
		};
		dao.delete(id);
		return 1;
	}

	@Override
	@Transactional
	public int statusBacth(String[] ids,Date time) throws Exception {
		int count = 0;
		Calendar  cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		if (!StringUtils.isEmpty(ids)) {
			if (ids.length>0) {
				for (int i = 0; i < ids.length; i++) {
					Long id = Long.parseLong(ids[i]);
					Bacth bacth = dao.findOne(id);
					if(bacth.getStatus()==1){
						throw new ServiceException("批次编号为"+bacth.getBacthNumber()+"的任务已经完成,无需再次完成");
					}
					if(bacth.getType()==3 && bacth.getFlag()==0){
						Group group = groupDao.findByNameAndType(GROUP,bacth.getType());
						List<Procedure> procedure = procedureDao.findByProductIdAndProcedureTypeIdAndType(bacth.getProductId(), (long)101, bacth.getType());						
						List<Task> taskList = bacth.getTasks().stream().filter(Task->Task.getProcedureId().equals(procedure.get(0).getId())).collect(Collectors.toList());
						if(taskList.size()==0){
							if(procedure.size()>0){
								Task task = new Task();
								String [] pro =  new String[]{String.valueOf (procedure.get(0).getId())};
								String userIds = "";
								String userNames = "";
								for(User user :group.getUsers()){
									userIds=userIds.equals("") ? String.valueOf(user.getId()) : userIds +","+user.getId();
									userNames=userNames.equals("") ? String.valueOf(user.getUserName()) : userNames +","+user.getUserName();
								}
								task.setNumber(bacth.getNumber());
								task.setType(bacth.getType());
								task.setAllotTime(ProTypeUtils.countAllotTime(time, task.getType()));
								task.setBacthId(bacth.getId());
								task.setProductName(bacth.getProduct().getName());
								task.setBacthNumber(bacth.getBacthNumber());
								task.setProcedureIds(pro);
								task.setUserIds(userIds);
								task.setUserNames(userNames);
								taskService.addTask(task);
							}
						}
					}
					
					bacth.setStatus(1);
					bacth.setStatusTime(time == null ? cal.getTime() :time);
					dao.save(bacth);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int receiveBacth(String[] ids, String[] numbers) throws Exception{
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			if (ids.length>0) {
				for (int i = 0; i < ids.length; i++) {
					Long id = Long.parseLong(ids[i]);
					Bacth bacth = new Bacth();
					Bacth oldBacth = dao.findOne(id);
					oldBacth.setReceive(1);
					dao.save(oldBacth);
					bacth.setProductId(oldBacth.getProductId());
					List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(oldBacth.getProductId(), 2,0);
	  				  if(procedureList!=null && procedureList.size()>0){
	  					bacth.setBacthHairPrice(procedureList.get(0).getHairPrice());
	  					bacth.setBacthDepartmentPrice(procedureList.get(0).getDepartmentPrice());
	  				  }else{
	  					throw new ServiceException("产品序号为"+oldBacth.getProductId()+"的"+oldBacth.getProduct().getName()+"未添加工序，无法接受，请先添加工序");
	  				  }
					bacth.setBacthNumber(oldBacth.getBacthNumber());
					bacth.setAllotTime(oldBacth.getAllotTime());
					bacth.setReceive(1);
					bacth.setStatus(0);
					bacth.setNumber(Integer.valueOf(numbers[i]));
					bacth.setType(2);
					dao.save(bacth);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public Bacth saveBacth(Bacth bacth) throws Exception {
		bacth.setAllotTime(ProTypeUtils.countAllotTime(bacth.getAllotTime(), bacth.getType()));
		bacth.setStatus(0);
		bacth.setReceive(0);
		List<Procedure> procedureList =procedureDao.findByProductIdAndTypeAndFlag(bacth.getProductId(), bacth.getType(), bacth.getFlag());
		double time = procedureList.stream().mapToDouble(Procedure::getWorkingTime).sum();
		if(procedureList!=null && procedureList.size()>0){
			bacth.setTime(time*bacth.getNumber()/60);
		  }else{
			throw new ServiceException("当前产品未添加工序，无法分配批次，请先添加工序");
		  }
		return dao.save(bacth);
	}
	
	
	
	
}
