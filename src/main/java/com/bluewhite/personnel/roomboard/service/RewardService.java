package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Reward;

public interface RewardService  extends BaseCRUDService<Reward,Long>{
	
	/**
	 * 按条件查寻招工奖励
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Reward> findPage(Reward reward, PageParameter page);
	
	/**
	 * 新增招工奖励
	 * @param onlineOrder
	 */
	public Reward addReward(Reward reward);
	
}
