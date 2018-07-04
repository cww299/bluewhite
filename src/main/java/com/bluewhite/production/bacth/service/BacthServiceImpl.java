package com.bluewhite.production.bacth.service;

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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
@Service
public class BacthServiceImpl extends BaseServiceImpl<Bacth, Long> implements BacthService{

	@Autowired
	private BacthDao dao;
	
	@Autowired
	private PayBDao payBDao;
	@Autowired
	private ProcedureDao procedureDao;
	
	
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
		        		predicate.add(cb.like(root.get("product").get("name").as(String.class), "%"+param.getName()+"%"));
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
		        PageResult<Bacth> result = new PageResult<>(pages,page);
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
	public int statusBacth(String[] ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			if (ids.length>0) {
				for (int i = 0; i < ids.length; i++) {
					Long id = Long.parseLong(ids[i]);
					Bacth bacth = dao.findOne(id);
					bacth.setStatus(1);
					bacth.setStatusTime(new Date());
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
