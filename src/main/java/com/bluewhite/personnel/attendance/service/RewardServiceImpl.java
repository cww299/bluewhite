package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.RewardDao;
import com.bluewhite.personnel.attendance.entity.Reward;

@Service
public class RewardServiceImpl extends BaseServiceImpl<Reward, Long>
		implements RewardService {
	@Autowired
	private RewardDao dao;
	@Autowired
	private BaseDataDao baseDataDao;
	
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Reward> findPage(Reward reward, PageParameter page) {
		Page<Reward> pages = dao.findAll((root, query, cb) -> {
			List<Reward> predicate = new ArrayList<>();
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Reward> result = new PageResult<>(pages, page);
		return result;
	}
	/*
	 *新增修改
	 */
	@Override
	public Reward addReward(Reward reward) {
		
		return dao.save(reward);
	}




	
	
	
}
