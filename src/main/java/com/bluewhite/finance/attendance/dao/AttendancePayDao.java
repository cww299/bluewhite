package com.bluewhite.finance.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.attendance.entity.AttendancePay;

public interface AttendancePayDao extends BaseRepository<AttendancePay, Long>{

	AttendancePay findByUserIdAndAllotTimeLike(Long userId, Date allotTime);

	/**
	 * 根据条件查询
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<AttendancePay> findByUserIdAndTypeAndAllotTimeBetween(Long userId, Integer type, Date orderTimeBegin,
			Date orderTimeEnd);
	
	
	/**
	 * 根据条件查询
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<AttendancePay> findByGroupIdAndTypeAndAllotTimeBetween(Long GroupId, Integer type, Date orderTimeBegin,
			Date orderTimeEnd);
	
	/**
	 * 根据条件查询
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<AttendancePay> findByIdInAndTypeAndAllotTimeBetween(List<Long> ids, Integer type, Date orderTimeBegin,
			Date orderTimeEnd);

	List<AttendancePay> findByTypeAndAllotTimeBetween( Integer type, Date orderTimeBegin,
			Date orderTimeEnd);
	
	
	
	List<AttendancePay> findByAllotTimeBetween(Date orderTimeBegin,
			Date orderTimeEnd );

}
