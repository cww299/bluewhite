package com.bluewhite.personnel.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Sundry;

public interface SundryDao extends BaseRepository<Sundry, Long>{
	//根据时间查询当月其他费用的记录
	public List<Sundry> findByMonthDate(Date monthDate);
}
