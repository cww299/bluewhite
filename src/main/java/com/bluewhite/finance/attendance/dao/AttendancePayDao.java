package com.bluewhite.finance.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.attendance.entity.AttendancePay;

public interface AttendancePayDao extends BaseRepository<AttendancePay, Long>{

	AttendancePay findByUserIdAndAllotTimeLike(Long userId, Date allotTime);

	List<AttendancePay> findByUserIdAndAllotTimeBetween(Long userId, Date getfristDayOftime, Date lastDayOftime);

}
