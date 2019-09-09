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
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.PersonVariable;

@Service
public class PersonVariableServiceImpl extends BaseServiceImpl<PersonVariable, Long> implements PersonVariableService {

	@Autowired
	private PersonVariableDao dao;

	@Override
	public PageResult<PersonVariable> findPersonVariablePage(PersonVariable param, PageParameter page) {
		Page<PersonVariable> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), param.getType()));
			}
			// 按申请日期
			if (!StringUtils.isEmpty(param.getTime()) ) {
				predicate.add(cb.equal(root.get("time").as(Date.class), param.getTime()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<PersonVariable> result = new PageResult<>(pages, page);
		return result;
	}

}
