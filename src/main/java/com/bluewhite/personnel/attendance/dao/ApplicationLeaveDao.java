package com.bluewhite.personnel.attendance.dao;

import java.util.Date;
import java.util.List;

import org.springframework.dao.support.DaoSupport;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.sun.tools.javadoc.Start;

import java.lang.Long;

public interface ApplicationLeaveDao  extends BaseRepository<ApplicationLeave, Long>{
	/**
	 * 根据员工查找
	 * @param userId
	 * @return
	 */
	public List<ApplicationLeave> findByUserIdAndWriteTimeBetween(Long userId,Date startTime,Date endTime);
	
}
