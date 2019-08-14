package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Sundry;

public interface SundryDao extends BaseRepository<Sundry, Long>{
	//根据时间查询当月其他费用的记录
	public List<Sundry> findByMonthDate(Date monthDate);
	
	//根据宿舍查询其他费用记录
	public Sundry findByHostelId(Long hostelId);
	//根据宿舍和月份查询其他费用记录
	public Sundry findByHostelIdAndMonthDate(Long hostelId,Date monthDate);
}
