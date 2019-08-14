package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Reward;

public interface RewardDao extends BaseRepository<Reward, Long>{
	/*根据招聘人id查询出所有的奖励*/
	public List<Reward> findByRecruitIdAndType(Long recruitId,Integer type);
	/*根据招聘id查询出所有的奖励*/
	public List<Reward> findBycoverRecruitIdAndType(Long coverRecruitId,Integer type);
	/*根据招聘id  时间 查询出所有的奖励 */
	public List<Reward> findBycoverRecruitIdAndTypeAndTimeBetween(Long coverRecruitId,Integer type,Date beginDate, Date endDate);
	public List<Reward> findBycoverRecruitId(Long coverRecruitId);
}
