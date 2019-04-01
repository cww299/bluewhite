package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.personnel.attendance.dao.AttendanceCollectDao;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
@Service
public class AttendanceCollectServiceImpl extends BaseServiceImpl<AttendanceCollect, Long>
		implements AttendanceCollectService {
	
	@Autowired
	private AttendanceCollectDao dao;

	@Override
	public List<AttendanceCollect> findAttendanceCollect(AttendanceCollect param) {
		List<AttendanceCollect> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("user").get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			// 按申请日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		result.stream().forEach( ac->{
			ac.setUserName(ac.getUser().getUserName());
			}
		);
		return result;
	}

}
