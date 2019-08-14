package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Hydropower;

public interface HydropowerDao extends BaseRepository<Hydropower, Long>{
	//根据时间查出所有记录
	public List<Hydropower> findByMonthDate(Date monthDate);
	//根据时间宿舍水电类型查询
	public Hydropower findByMonthDateAndHostelIdAndType(Date monthDate,Long hostelId,Integer type);
	
	//根据时间水电类型查询
	public List<Hydropower> findByMonthDateAndType(Date monthDate,Integer type);
}
