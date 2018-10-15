package com.bluewhite.production.farragotask.service;

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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.farragotask.dao.FarragoTaskDao;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.finance.dao.FarragoTaskPayDao;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
@Service
public class FarragoTaskServiceImpl extends BaseServiceImpl<FarragoTask, Long> implements FarragoTaskService{

	@Autowired
	private FarragoTaskDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FarragoTaskPayDao farragoTaskPayDao ;
	
	@Override
	public PageResult<FarragoTask> findPages(FarragoTask param, PageParameter page) {
		Page<FarragoTask> pages = dao.findAll((root,query,cb) -> {
        	List<Predicate> predicate = new ArrayList<>();
        	//按id过滤
        	if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
			}
        	//按类型
        	if(!StringUtils.isEmpty(param.getType())){
        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
        	}
        	//任务名
        	if(!StringUtils.isEmpty(param.getName())){
        		predicate.add(cb.like(root.get("name").as(String.class),"%"+ param.getName()+"%"));
        	}
        	//按批次名
        	if(!StringUtils.isEmpty(param.getBacth())){
        		predicate.add(cb.like(root.get("bacth").as(String.class),"%"+ param.getBacth()+"%"));
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
        }, SalesUtils.getQueryNoPageParameter());
		  PageResultStat<FarragoTask> result = new PageResultStat<>(pages,page);
		  result.setAutoStateField("payB", "price");
		  result.count();
        return result;
}

	@Override
	public FarragoTask addFarragoTask(FarragoTask farragoTask) {
		
		//将用户变成string类型储存
		if (!StringUtils.isEmpty(farragoTask.getUserIds())) {
			String[] idArr = farragoTask.getUserIds().split(",");
			farragoTask.setUsersIds(idArr);
		}
		//当数量不为null，计算出实际完成时间
		if(farragoTask.getNumber()!=null){
			farragoTask.setTime(NumUtils.round(ProTypeUtils.sumFarragoTaskTime(farragoTask.getProcedureTime(),farragoTask.getType(),farragoTask.getNumber()), null));
		}
		//杂工任务价值
		farragoTask.setPrice(NumUtils.round(ProTypeUtils.sumTaskPrice(farragoTask.getTime(), farragoTask.getType(),0,farragoTask.getAC5()), null));
		//杂工加绩具体数值
		if(farragoTask.getPerformanceNumber()!=null){
			farragoTask.setPerformancePrice(NumUtils.round(ProTypeUtils.sumPerformancePrice(farragoTask), null));
		}
		//B工资净值
		farragoTask.setPayB(NumUtils.round(ProTypeUtils.sumBPrice(farragoTask.getPrice(), farragoTask.getType()), null));
		
		farragoTask =  dao.save(farragoTask);
		//将杂工工资统计成流水
		if (farragoTask.getUsersIds().length>0) {
			for (int j = 0; j < farragoTask.getUsersIds().length; j++) {
				Long userid = Long.parseLong(farragoTask.getUsersIds()[j]);
				User user = userDao.findOne(userid);
				FarragoTaskPay farragoTaskPay = new FarragoTaskPay();
				farragoTaskPay.setAllotTime(farragoTask.getAllotTime());
				//计算杂工工资
				farragoTaskPay.setPayNumber(farragoTask.getPayB()/farragoTask.getUsersIds().length);
				farragoTaskPay.setType(farragoTask.getType());
				farragoTaskPay.setUserId(user.getId());
				farragoTaskPay.setTaskId(farragoTask.getId());
				farragoTaskPay.setUserName(user.getUserName());
				farragoTaskPay.setTaskName(farragoTask.getName());
				//计算杂工加绩工资
				if(farragoTask.getPerformancePrice()!=null){
					farragoTaskPay.setPerformancePayNumber(farragoTask.getPerformancePrice()/farragoTask.getUsersIds().length);
				}
				farragoTaskPayDao.save(farragoTaskPay);
			}
		}
		return dao.save(farragoTask);
	}

	@Override
	public FarragoTask updateFarragoTask(FarragoTask farragoTask) {
//		farragoTaskPayDao.findByTaskId();	
	
		return null;
	}

	@Override
	@Transactional
	public void deleteFarragoTask(Long id) {
		List<FarragoTaskPay> taskList = farragoTaskPayDao.findByTaskId(id);	
		if(taskList!=null){
			farragoTaskPayDao.delete(taskList);
		}
		dao.delete(id);
	}
}
