package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.personnel.attendance.dao.RecruitDao;
import com.bluewhite.personnel.attendance.dao.RewardDao;
import com.bluewhite.personnel.attendance.dao.SeatDao;
import com.bluewhite.personnel.attendance.entity.Recruit;
import com.bluewhite.personnel.attendance.entity.Reward;

@Service
public class RewardServiceImpl extends BaseServiceImpl<Reward, Long>
		implements RewardService {
	@Autowired
	private RewardDao dao;
	@Autowired
	private RecruitDao recruitDao;
	@Autowired
	private SeatDao seatDao;
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
	
	
	/*
	 *计算招工奖励
	 */
	@Override
	public List<Reward> rewards(Reward reward,Integer integer) {
		List<Recruit> recruits= recruitDao.findByRecruitId(integer);
		Date now = new Date();
		for (Recruit recruit : recruits) {
		Long positionId=recruit.getUser().getPositionId();//职位ID
		Integer age= recruit.getUser().getAge();//年龄
		Date entry=recruit.getUser().getEntry();//入职时间
		Date actua=recruit.getUser().getActua();//实际转正时间
		long long1= DatesUtil.getDaySub(actua, entry);//实际转正时间减去入职时间
		long long2= DatesUtil.getDaySub(now, entry);//当前时间减去入职时间
		
		if (actua!=null && long1>60 && long1<180 ) {
			Reward reward2=new Reward();
			reward2.setRecruitId(recruit.getId());
			reward2.setSpeed(reward.getSpeed());
			reward2.setPrice(300.0);
			reward2.setType(0);
		}
		}
		return null;
	}


public static void main(String[] args) {
	Date now = new Date();
	System.out.println(now);
}

	
	
	
}
