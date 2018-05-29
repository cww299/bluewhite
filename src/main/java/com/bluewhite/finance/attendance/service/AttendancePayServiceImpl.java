package com.bluewhite.finance.attendance.service;

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
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
@Service
public class AttendancePayServiceImpl extends BaseServiceImpl<AttendancePay, Long> implements AttendancePayService{
	
	@Autowired
	private AttendancePayDao dao;

	@Override
	public PageResult<AttendancePay> findPages(AttendancePay param, PageParameter page) {
		 Page<AttendancePay> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按员工姓名
	        	if(!StringUtils.isEmpty(param.getUserName())){
	        		predicate.add(cb.like(root.get("userName").as(String.class), "%"+param.getUserName()+"%"));
	        	}
	        	
	        	//按员工id过滤
	        	if (param.getUserId() != null) {
					predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
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
	        PageResult<AttendancePay> result = new PageResult<AttendancePay>(pages,page);
	        return result;
	    }

	@Override
	@Transactional
	public void addAttendancePay(AttendancePay attendancePay) {
		attendancePay.setPayNumber(attendancePay.getWorkPrice()*attendancePay.getWorkTime());
		dao.save(attendancePay);
	}

}

