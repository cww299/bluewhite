package com.bluewhite.finance.attendance.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.production.bacth.entity.Bacth;
@Service
public interface AttendancePayService  extends BaseCRUDService<AttendancePay,Long>{
	
	/**
	 * 分页查看车间考勤
	 * @param attendancePay
	 * @param page
	 * @return
	 */
	public PageResult<AttendancePay> findPages(AttendancePay attendancePay, PageParameter page);
	
	/**
	 * 批量新增考勤
	 * @param attendancePay
	 * @return
	 */
	public int addAttendancePay(AttendancePay attendancePay);

	public AttendancePay  findByUserIdAndAllotTime(AttendancePay attendancePay);
	/**
	 * 根据条件查询
	 * @param attendancePay
	 * @return
	 */
	public List<AttendancePay> findAttendancePay(AttendancePay attendancePay);

	List<AttendancePay> findAttendancePayNoId(AttendancePay attendancePay);
	
	/**
	 * 批量修改员工考勤工资流水（由于当月预计小时收入不精确，导致要修改上月员工的所有收入流水）
	 * 
	 */
	public int updateAllAttendance(AttendancePay attendancePay);
	
	/**
	 * 修改考勤
	 * @param attendancePay
	 */
	public void updateAttendance(AttendancePay attendancePay,HttpServletRequest request);
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<AttendancePay> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);
	
}
