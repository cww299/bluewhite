package com.bluewhite.finance.attendance.dao;

import java.util.Date;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.attendance.entity.AttendancePay;

public interface AttendancePayDao extends BaseRepository<AttendancePay, Long>{

	AttendancePay findByUserIdAndAllotTimeLike(Long userId, Date allotTime);

}
