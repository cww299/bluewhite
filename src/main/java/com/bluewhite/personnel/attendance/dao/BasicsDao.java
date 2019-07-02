package com.bluewhite.personnel.attendance.dao;

import java.util.Date;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Basics;

public interface BasicsDao extends BaseRepository<Basics, Long>{
	
	public Basics findByTimeBetween(Date orderTimeBegin, Date orderTimeEnd);

}
