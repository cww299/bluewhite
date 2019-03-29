package com.bluewhite.personnel.attendance.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.system.user.entity.User;

public interface AttendanceInitDao extends BaseRepository<AttendanceInit, Long>{

	public AttendanceInit findByUser(User user);

}
