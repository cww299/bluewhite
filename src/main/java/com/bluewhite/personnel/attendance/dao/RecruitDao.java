package com.bluewhite.personnel.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Recruit;

public interface RecruitDao extends BaseRepository<Recruit, Long>{
	
	public List<Recruit> findByTimeBetween(Date orderTimeBegin, Date orderTimeEnd);
	

}
