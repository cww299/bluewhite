package com.bluewhite.production.finance.service;

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
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.production.finance.dao.FarragoTaskPayDao;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
@Service
public class FarragoTaskPayServiceImpl extends BaseServiceImpl<FarragoTaskPay, Long> implements FarragoTaskPayService{
	
	@Autowired
	private FarragoTaskPayDao dao;

	@Override
	public PageResult<FarragoTaskPay> findPages(FarragoTaskPay param, PageParameter page) {
		 Page<FarragoTaskPay> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按员工id过滤
	        	if (param.getUserId() != null) {
					predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
				}
	           	//按分组id过滤
	        	if (param.getGroupId() != null) {
					predicate.add(cb.equal(root.get("user").get("groupId").as(Long.class),param.getGroupId()));
				}
	        	//按员工姓名
	        	if(!StringUtils.isEmpty(param.getUserName())){
	        		predicate.add(cb.like(root.get("userName").as(String.class), "%"+param.getUserName()+"%"));
	        	}
	        	//按任务名称
	        	if(!StringUtils.isEmpty(param.getTaskName())){
	        		predicate.add(cb.like(root.get("taskName").as(String.class), "%"+param.getTaskName()+"%"));
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
	        }, SalesUtils.getQueryNoPageParameter());
		 	PageResultStat<FarragoTaskPay> result = new PageResultStat<>(pages,page);
		 	result.setAutoStateField("performancePayNumber", "payNumber");
		 	result.count();
	        return result;
	    }

	@Override
	public List<FarragoTaskPay> findFarragoTaskPay(FarragoTaskPay farragoTaskPay) {
		return dao.findByUserIdAndTypeAndAllotTimeBetween(farragoTaskPay.getUserId(),farragoTaskPay.getType(),farragoTaskPay.getOrderTimeBegin(),farragoTaskPay.getOrderTimeEnd());
	}

	@Override
	public List<FarragoTaskPay> findFarragoTaskPayTwo(FarragoTaskPay farragoTaskPay) {
		return dao.findByTypeAndAllotTimeBetween(farragoTaskPay.getType(),farragoTaskPay.getOrderTimeBegin(),farragoTaskPay.getOrderTimeEnd());

	}

}
