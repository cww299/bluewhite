package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.HostelDao;
import com.bluewhite.personnel.attendance.entity.Hostel;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;

@Service
public class HostelServiceImpl extends BaseServiceImpl<Hostel, Long>
		implements HostelService {
	@Autowired
	private HostelDao dao;
	
	@Autowired
	private UserDao  userDao;
	/**
     * 按条件查询住宿
     */
	@Override
	public PageResult<Hostel> findPage(Hostel param, PageParameter page) {
		Page<Hostel> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Hostel> result = new PageResult<>(pages, page);
		return result;
	}
	
	/**
     * 新增住宿
     */
	@Override
	public Hostel addHostel(Hostel hostel) {
		return dao.save(hostel);
	}
	
	/**
     * 宿舍分配
     */
	public void updateUserHostelId(Hostel hostel){
		JSONObject jsonObject =JSONObject.parseObject(hostel.getJsonName());
		String id = jsonObject.getString("id");
		Hostel hostel2= dao.findOne(Long.valueOf(id));
		int  size =	jsonObject.size();
		Set<User> users = new HashSet<User>();
		for (int i = 0; i < size-1; i++) {
			String aString= jsonObject.getString("province["+i+"]");
			users.add(userDao.findOne(Long.valueOf(aString)));
		}
		hostel2.setUsers(users);
		dao.save(hostel2);
	}

	@Override
	public void updateUser(User user) {
		userDao.save(user);
	}
}
