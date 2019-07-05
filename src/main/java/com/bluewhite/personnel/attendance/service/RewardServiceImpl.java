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
import com.bluewhite.personnel.attendance.dao.RewardDao;
import com.bluewhite.personnel.attendance.entity.Reward;

@Service
public class RewardServiceImpl extends BaseServiceImpl<Reward, Long>
		implements RewardService {
	@Autowired
	private RewardDao dao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Reward> findPage(Reward reward, PageParameter page) {
		Page<Reward> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (reward.getRecruitId() != null) {
				predicate.add(cb.equal(root.get("recruitId").as(Long.class), reward.getRecruitId()));
			}
			// 按用户 类型过滤
			if (reward.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), reward.getType()));
			}
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
