package com.bluewhite.personnel.attendance.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;

public interface AttendanceInitDao extends BaseRepository<AttendanceInit, Long>{

	public AttendanceInit findByUserId(Long UserId);

}
