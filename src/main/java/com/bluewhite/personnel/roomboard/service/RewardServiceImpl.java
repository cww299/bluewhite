package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.roomboard.dao.RewardDao;
import com.bluewhite.personnel.roomboard.entity.Reward;

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
			// 按被招聘人过滤
			if (reward.getCoverRecruitId() != null) {
				predicate.add(cb.equal(root.get("coverRecruitId").as(Long.class), reward.getCoverRecruitId()));
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
		if (reward.getType().equals(1)) {
			List<Reward> list=dao.findBycoverRecruitIdAndType(reward.getCoverRecruitId(),0);//累计奖励
			double price=0;
			for (Reward reward2 : list) {
				price=price+reward2.getPrice();
			}
			List<Reward> list2=dao.findBycoverRecruitIdAndType(reward.getCoverRecruitId(),1);//发放的奖励
			double price2=0;
			for (Reward reward2 : list2) {
				price2=price2+reward2.getPrice();
			}
			if (price-price2-reward.getPrice()<0) {
				throw new ServiceException("领取的奖励超过了 累计奖励");
			}else{
				return dao.save(reward);
			}
		}else{
			return dao.save(reward);
		}
	}
	/*
	 *查看剩余 累计发放奖励
	 */
	@Override
	public Reward findReward(Reward reward) {
		List<Reward> list=dao.findBycoverRecruitIdAndType(reward.getCoverRecruitId(),0);//累计奖励
		double price=0;
		for (Reward reward2 : list) {
			price=price+reward2.getPrice();
		}
		List<Reward> list2=dao.findBycoverRecruitIdAndType(reward.getCoverRecruitId(),1);//领取的奖励
		double price2=0;
		for (Reward reward2 : list2) {
			price2=price2+reward2.getPrice();
		}
		double d =NumUtils.sub(price, price2);
		reward.setCollarPrice(price);
		reward.setHairPrice(d);
		return reward;
	}

	
	
}
