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
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.ApplicationLeaveDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.system.user.entity.User;

@Service
public class ApplicationLeaveServiceImpl extends BaseServiceImpl<ApplicationLeave, Long>
		implements ApplicationLeaveService {

	@Autowired
	private ApplicationLeaveDao dao;

	@Override
	public ApplicationLeave updateApplicationLeave(ApplicationLeave applicationLeave) {
		ApplicationLeave oldApplicationLeave = dao.findOne(applicationLeave.getId());
		BeanCopyUtils.copyNotEmpty(applicationLeave, oldApplicationLeave, "");
		return dao.save(oldApplicationLeave);
	}

	@Override
	public int deleteApplicationLeave(String ids) {
		int count = 0;
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.getLong(arrIds[i]);
			dao.delete(id);
			count++;
		}
		return count;
	}

	@Override
	public PageResult<ApplicationLeave> findApplicationLeavePage(ApplicationLeave param, PageParameter page) {
		Page<ApplicationLeave> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按姓名查找
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(cb.equal(root.get("user").get("userName").as(String.class), param.getUserName()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("user").get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			// 按考勤日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, new PageParameter());
		PageResult<ApplicationLeave> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public ApplicationLeave saveApplicationLeave(ApplicationLeave applicationLeave) {
		
		
		
		return null;
	}

}
