package com.bluewhite.personnel.attendance.service;

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
import com.bluewhite.personnel.attendance.dao.AttendanceInitDao;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;

@Service
public class AttendanceInitServiceImpl extends BaseServiceImpl< AttendanceInit, Long> implements  AttendanceInitService{
	
	@Autowired
	private AttendanceInitDao dao;
	@Autowired
	private UserDao userDao;


	@Override
	public PageResult<AttendanceInit> findAttendanceInitPage(AttendanceInit param, PageParameter page) {
		Page<AttendanceInit> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按姓名查找
			if (!StringUtils.isEmpty(param.getUserId())) {
				predicate.add(cb.equal(root.get("user").get("id").as(String.class), param.getUserId()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("user").get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			// 按日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("writeTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<AttendanceInit> result = new PageResult<>(pages, page);
		return result;
	}


	@Override
	public AttendanceInit findByUserId(Long id) {
		User user = userDao.findOne(id);
		return dao.findByUser(user);
	}


	@Override
	public int deleteAttendanceInit(String ids) {
    	int count = 0;
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.valueOf(arrIds[i]);
			AttendanceInit attendanceInit = dao.findOne(id);
			attendanceInit.setUser(null);
			dao.delete(attendanceInit);
			count++;
		}
		return count;
	}


	@Override
	public List<AttendanceInit> findInit() {
	List<AttendanceInit> attendanceInits=dao.findAll();
		return attendanceInits;
	}

}
