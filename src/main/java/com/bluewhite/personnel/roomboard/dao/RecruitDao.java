package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Recruit;

public interface RecruitDao extends BaseRepository<Recruit, Long>{
	
	public List<Recruit> findByTimeBetween(Date orderTimeBegin, Date orderTimeEnd);
	
	public List<Recruit> findByTestTimeBetween(Date orderTimeBegin, Date orderTimeEnd);
	
	public List<Recruit> findByRecruitId(Long recruitId);
	public List<Recruit> findByRecruitIdAndState(Long long1,Integer state);
	public Recruit findByPhone(String phone);
}
