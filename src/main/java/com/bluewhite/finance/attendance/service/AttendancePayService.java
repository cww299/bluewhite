package com.bluewhite.finance.attendance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.entity.AttendancePay;
@Service
public interface AttendancePayService  extends BaseCRUDService<AttendancePay,Long>{
	
	/**
	 * 分页查看车间考勤
	 * @param attendancePay
	 * @param page
	 * @return
	 */
	public PageResult<AttendancePay>  findPages(AttendancePay attendancePay, PageParameter page);

	public void addAttendancePay(AttendancePay attendancePay);

	public AttendancePay  findByUserIdAndAllotTime(AttendancePay attendancePay);
	/**
	 * 根据条件查询
	 * @param attendancePay
	 * @return
	 */
	public List<AttendancePay> findAttendancePay(AttendancePay attendancePay);

	List<AttendancePay> findAttendancePayNoId(AttendancePay attendancePay);

}
