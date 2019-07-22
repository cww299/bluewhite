package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Basics;

public interface BasicsDao extends BaseRepository<Basics, Long>{
	
	public Basics findByTimeBetween(Date orderTimeBegin, Date orderTimeEnd);

}
