package com.bluewhite.personnel.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Hydropower;

public interface HydropowerDao extends BaseRepository<Hydropower, Long>{
	//根据时间查出所有记录
	public List<Hydropower> findByMonthDate(Date monthDate);
	
	public Hydropower findByMonthDateAndHostelIdAndType(Date monthDate,Long hostelId,Integer type);
}
