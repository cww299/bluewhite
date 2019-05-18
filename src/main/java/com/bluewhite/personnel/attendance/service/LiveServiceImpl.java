package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.LiveDao;
import com.bluewhite.personnel.attendance.entity.Live;
import com.bluewhite.system.user.service.UserService;

@Service
public class LiveServiceImpl extends BaseServiceImpl<Live, Long>
		implements LiveService {
	@Autowired
	private LiveDao dao;
	@Autowired
	private UserService userService;
	/*
	 * 按条件查询
	 */
	@Override
	public PageResult<Live> findPage(Live param, PageParameter page) {
		Page<Live> pages = dao.findAll((root, query, cb) -> {
			List<Live> predicate = new ArrayList<>();
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Live> result = new PageResult<>(pages, page);
		return result;
	}
	//新增
	@Override
	public Live addLive(Live live) {
		return dao.save(live);
	}
}
