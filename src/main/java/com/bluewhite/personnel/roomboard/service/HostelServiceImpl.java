package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.dao.HostelDao;
import com.bluewhite.personnel.roomboard.dao.LiveDao;
import com.bluewhite.personnel.roomboard.entity.Hostel;
import com.bluewhite.personnel.roomboard.entity.Live;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;

@Service
public class HostelServiceImpl extends BaseServiceImpl<Hostel, Long>
		implements HostelService {
	@Autowired
	private HostelDao dao;
	
	@Autowired
	private UserDao  userDao;
	@Autowired
	private LiveDao liveDao;
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
		//获取宿舍原本人员
		Set<User> userList =  hostel2.getUsers();
		int  size =	jsonObject.size();
		//新增的人员
		Set<User> users = new HashSet<User>();
		List<Live> lives=new ArrayList<Live>();
		CopyOnWriteArraySet<User>  exSet = new  CopyOnWriteArraySet<User>(userList);
		for (int i = 0; i < size-1; i++) {
			String aString= jsonObject.getString("province["+i+"]");
			User user= userDao.findOne(Long.valueOf(aString));
			//当宿舍原本人员新增人员
			if(!userList.contains(user)){
				Live livee = liveDao.findByUserIdAndType(Long.valueOf(aString), 1);
				if (livee!=null) {
					livee.setType(2);
					liveDao.save(livee);
				}
				Live live = new Live();
				live.setHostelId(Long.valueOf(id));
				live.setUserId(Long.valueOf(aString));
				live.setType(1);
				lives.add(live);
			}
			//当宿舍人员减少
			if(exSet.contains(user)){
				exSet.remove(user);
			}
			
			users.add(user);
		}
		for (User user2 : exSet) {
			Live live=liveDao.findByUserIdAndType(user2.getId(), 1);
			live.setType(2);
			liveDao.save(live);
		}
		liveDao.save(lives);
		hostel2.setNumber(size-1);
		hostel2.setUsers(users);
		dao.save(hostel2);
	}

	@Override
	public void updateUser(User user) {
		userDao.save(user);
	}
}
