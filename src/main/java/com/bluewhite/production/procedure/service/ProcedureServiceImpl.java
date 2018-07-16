package com.bluewhite.production.procedure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;

@Service
public class ProcedureServiceImpl extends BaseServiceImpl<Procedure, Long> implements ProcedureService{

	@Autowired
	private ProcedureDao procedureDao;
	@Autowired
	private BacthDao bacthDao;
	@Autowired
	private  TaskService taskService ;
	
	@Override
	public List<Procedure> findByProductIdAndType(Long productId, Integer type,Integer flag) {
		return procedureDao.findByProductIdAndTypeAndFlag(productId,type,flag);
	}
	
	
	@Override
	@Transactional
	public void countPrice(Procedure procedure) {
		List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(procedure.getProductId(), procedure.getType(),0);
		
		if(procedure.getType()!=null && procedure.getType()==5){
			procedureList = procedureList.stream().filter(Procedure->Procedure.getSign()==procedure.getSign()).collect(Collectors.toList());
		}
		//计算部门生产总价
		Double sumTime = 0.0;
		for(Procedure pro : procedureList){
				sumTime += pro.getWorkingTime();
		}
		Double sumPrice = ProTypeUtils.sumProTypePrice(sumTime, procedure.getType());
		Double sumDEEDLEPrice = ProTypeUtils.getDEEDLE(sumTime, procedure.getType());
		
		for(Procedure pro : procedureList){
			pro.setDepartmentPrice(NumUtils.round(sumPrice, null));
			pro.setDeedlePrice(NumUtils.round(sumDEEDLEPrice, null));
		}
		//计算外发价格
		Double price = ProTypeUtils.sumProTypeHairPrice(procedureList,procedure.getType());
		for(Procedure pro : procedureList){
			pro.setHairPrice(NumUtils.round(price, null));
		}
		procedureDao.save(procedureList);
	}


	@Override
	public List<Procedure> findByProductIdAndProcedureTypeIdAndType(Long productId, Long procedureTypeId,
			Integer type) {
		return procedureDao.findByProductIdAndProcedureTypeIdAndType(productId,procedureTypeId,type);
	}


	@Override
	public List<Procedure> findList(Procedure param) {
		 List<Procedure> list = procedureDao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按工序类型
	        	if(param.getProcedureTypeId()!=null){
	        		predicate.add(cb.equal(root.get("procedureTypeId").as(Long.class),param.getProcedureTypeId()));
	        	}
	        	//按产品id
	        	if(!StringUtils.isEmpty(param.getProductId())){
	        		predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
	        	}
	        	//按类型
	        	if(!StringUtils.isEmpty(param.getType())){
	        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
	        	}
	        	//是否返工
	        	if(!StringUtils.isEmpty(param.getFlag())){
	        		predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
	        	}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        });
		 if(param.getBacthId()!=null){
			 //查出所分配的批次
			 Bacth bacth = bacthDao.findOne(param.getBacthId());
			 //循环出所有任务，当所任务的工序和当前工序想匹配时，记录其数值
			 	for(Procedure pro : list){
			 		//任务总数
			 		int number = bacth.getNumber();
					if(bacth.getTasks().size()>0){
						 for(Task task : bacth.getTasks()){
							 if(task.getProcedureId().equals(pro.getId())){
								 number = number - task.getNumber();
							 }
						 }
					}
					pro.setResidualNumber(number);
			 	}
		 	}
		return list;
	}


	@Override
	public List<Procedure> saveList(List<Procedure> procedureList) {
		return procedureDao.save(procedureList);
	}


	@Override
	public void deleteProcedure(Long id) throws Exception {
		Task task = new Task();
		task.setProcedureId(id);
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		List<Task> taskList = taskService.findPages(task, page).getRows();
		String mag = "";
		if(taskList.size()>0){
			for(Task ta : taskList){
				String idString =  String.valueOf(ta.getId());
				mag+="  "+idString+"  ";
			}
			throw new ServiceException("该工序已经分配给任务编号为"+mag+"的任务，需要先删除任务。");
		}
		procedureDao.delete(id);
		
	}

}
