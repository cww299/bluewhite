package com.bluewhite.finance.attendance.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.entity.AttendancePay;
@Service
public interface AttendancePayService  extends BaseCRUDService<AttendancePay,Long>{

	public PageResult<AttendancePay>  findPages(AttendancePay attendancePay, PageParameter page);

	public void addAttendancePay(AttendancePay attendancePay);

	public AttendancePay  findByUserIdAndAllotTime(AttendancePay attendancePay);

}
