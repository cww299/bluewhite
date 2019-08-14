package com.bluewhite.personnel.attendance.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.system.user.entity.User;

public interface AttendanceInitDao extends BaseRepository<AttendanceInit, Long>{

	public AttendanceInit findByUser(User user);
	
	/**
	 * 根据id查询多个用户初始化数据
	 * @param idLongs
	 * @return
	 */
	public List<AttendanceInit> findByUserIdIn(List<Long> idLongs);

}
