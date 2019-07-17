package com.bluewhite.personnel.attendance.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.system.user.entity.User;

/**
 * 用于计算考勤数据的工具方法
 * 
 * @author zhangliang
 *
 */
public class AttendanceTool {

	// 计算缺勤时满足的分钟数(30)
	private final static double DUTYMIN = 30;

	// 计算员工可以加班后晚到岗,迟到分钟不超过的分钟数不算迟到(10)
	private final static double LATERMIN = 10;

	/**
	 * 进行出勤，加班，缺勤，迟到，早退的计算 举例出能满足的所有条件，合适条件的进行
	 * 
	 * @param sign
	 *            员工是否可以加班后晚到岗 ture = 是，false =否
	 * @param workTime
	 *            员工默认上班开始时间
	 * @param workTimeEnd
	 *            员工默认上班结束时间
	 * @param turnWorkTime
	 *            员工默认出勤时长
	 * @param minute
	 *            用于计算延后上班开始时间的分钟（来源于sign）
	 * @param attendanceTime
	 *            考勤数据实体
	 * @param attendanceInit
	 *            考勤初始化参数实体
	 * @param user
	 *            员工实体
	 * @return
	 */
	public static AttendanceTime attendanceIntTool(boolean sign, Date workTime, Date workTimeEnd, Date restBeginTime,
			Date restEndTime, double minute, Double turnWorkTime, AttendanceTime attendanceTime,
			AttendanceInit attendanceInit, User user,Double restTime) {
		boolean flag = false;
		// 实际出勤，加班，缺勤,早退时间
		// 实际出勤不可能大于员工默认出勤时长
		double actualTurnWorkTime = 0;
		double actualOverTime = 0;
		double actualDutyTime = 0;
		double actualLeaveEarly = 0;
		double actualbelateTime = 0;
		// 将实际工作时间延长一分钟计算迟到
		workTime = DatesUtil.getDaySum(workTime, 1.0);

		// 当没有签入签出的时候直接返回
		if (attendanceTime.getCheckIn() == null || attendanceTime.getCheckOut() == null) {
			return attendanceTime;
		}
		if (sign) {
			// 出勤时间 缺勤时间 会出现的状态
			// 满足于：员工可以加班后晚到岗，签入时间在（初始化上班开始时间后的加班分钟数+30分钟）之前，签出时间在工作结束时间之后
			// 没有缺勤出现（没缺勤）
			flag = attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTime, NumUtils.sum(minute, DUTYMIN)))
					&& (attendanceTime.getCheckOut().after(workTimeEnd)
							|| attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0
							|| DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) <= DUTYMIN);
			if (flag) {
				actualTurnWorkTime = turnWorkTime;
				flag = false;
			}

			// 满足于：员工可以加班后晚到岗 ，签入时间在（初始化上班开始时间后的加班分钟数+30分钟）之后，签出时间在工作结束时间之前 出现缺勤
			// (迟到时间过长导致缺勤)
			flag = attendanceTime.getCheckIn().after(DatesUtil.getDaySum(workTime, NumUtils.sum(minute, DUTYMIN)))
					&& (attendanceTime.getCheckOut().after(workTimeEnd)  || attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0 );
			if (flag) {
				// 等于实际工作时间+前一天的加班时间
				actualTurnWorkTime = NumUtils.sum(attendanceTime.getWorkTime(), NumUtils.div(minute, 60, 1));
				// 等于默认出勤-实际出勤
				actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
				flag = false;
				attendanceTime.setFlag(1);
			}

			// 满足于：员工可以加班后晚到岗 ，签入时间在（初始化上班开始时间后的加班分钟数+30分钟）之后，签出时间在工作结束时间之后 出现缺勤
			// (早退时间过长出现缺勤)
			flag = attendanceTime.getCheckOut().before(workTimeEnd)
					&& DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) > DUTYMIN;
			if (flag) {
				actualTurnWorkTime = NumUtils.sum(attendanceTime.getWorkTime(), NumUtils.div(minute, 60, 1));
				actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
				flag = false;
				attendanceTime.setFlag(1);
			}

			// 迟到状态
			// 满足于：员工可以加班后晚到岗 ，签入时间在（初始化上班开始时间后的加班分钟数10到30分钟）之间，签出时间在工作结束时间之后
			// 出现迟到
			flag = attendanceTime.getCheckIn().after(DatesUtil.getDaySum(workTime, NumUtils.sum(minute, LATERMIN)))
					&& attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTime, NumUtils.sum(minute, DUTYMIN)));
			if (flag) {
				attendanceTime.setBelate(1);
				actualbelateTime = setActualbelateTime(DatesUtil.getDaySum(workTime, NumUtils.sum(minute, LATERMIN)),
						attendanceTime.getCheckIn());
				flag = false;
			}

			// 早退状态
			// 满足于：员工可以加班后晚到岗，签出时间在工作结束时间小于等于30 （有早退）
			flag = attendanceTime.getCheckOut().before(workTimeEnd)
					&& DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) <= DUTYMIN;
			if (flag) {
				attendanceTime.setLeaveEarly(1);
				actualLeaveEarly = DatesUtil.getTime(workTimeEnd, attendanceTime.getCheckOut());
				flag = false;
			}

			// 加班
			// 满足于：员工可以加班后晚到岗 ，属于包装部，签入时间在（初始化上班开始时间后的加班分钟数）之前，工作时间结束后签到加班
			// 早到时间 ,包装部员工的签入时间早于实际上班时间超过30分钟后算0.5个加班，超过60分钟算1个加班
			double earlyTime = Math.floor(DatesUtil.getTime(attendanceTime.getCheckIn(),
					DatesUtil.getDaySum(workTime, NumUtils.sum(minute, 0))) / 60) * 0.5;
			flag = user.getOrgNameId() == 79
					&& attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTime, NumUtils.sum(minute, 0)))
					&& attendanceInit.getOverTimeType() == 2;
			if (flag) {
				actualOverTime = NumUtils.sum(DatesUtil.getTimeHour(workTimeEnd, attendanceTime.getCheckOut()),
						earlyTime);
				flag = false;
			}

			// 满足于：员工可以加班后晚到岗 ，属于包装部，签入时间在（初始化上班开始时间后的加班分钟数）之前，工作时间结束后签到不算加班加班
			flag = user.getOrgNameId() == 79
					&& attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTime, NumUtils.sum(minute, 0)))
					&& attendanceInit.getOverTimeType() == 1;
			if (flag) {
				actualOverTime = earlyTime;
				flag = false;
			}

			// 满足于：员工可以加班后晚到岗 ，不属于包装部，签入时间在（初始化上班开始时间后的加班分钟数）之前，工作时间结束后签到加班
			flag = user.getOrgNameId() != 79 && workTimeEnd.before(attendanceTime.getCheckOut())
					&& attendanceInit.getOverTimeType() == 2;
			if (flag) {
				actualOverTime = DatesUtil.getTimeHour(workTimeEnd, attendanceTime.getCheckOut());
				flag = false;
			}
		}

		// 正常情况下
		if (!sign) {
			// 出勤
			// 满足于：员工不可以加班后晚到岗，签入时间在默认上班开始时间之前，签出时间在工作结束时间之后 没有缺勤出现（没缺勤）
			flag = attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTime, DUTYMIN))
					&& (attendanceTime.getCheckOut().after(workTimeEnd) || attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0
							|| DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) <= DUTYMIN);
			if (flag) {
				actualTurnWorkTime = turnWorkTime;
				flag = false;
			}

			// 满足于：员工不可以加班后晚到岗 ，签入时间在默认上班开始时间之后，签出时间在工作结束时间之后 出现缺勤 (迟到时间过长导致缺勤)
			flag = attendanceTime.getCheckIn().after(DatesUtil.getDaySum(workTime, DUTYMIN))
					&& (attendanceTime.getCheckOut().after(workTimeEnd) || attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0 );
			if (flag) {
				// 等于实际工作时间
				actualTurnWorkTime = attendanceTime.getWorkTime();
				// 等于默认出勤-实际出勤
				actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
				attendanceTime.setFlag(1);
				flag = false;
			}

			// 满足于：员工可以不加班后晚到岗 ，签出时间在工作结束时间之前 出现缺勤 (早退时间过长出现缺勤)
			flag = attendanceTime.getCheckOut().before(workTimeEnd) && DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) > DUTYMIN;
			if (flag) {
				actualTurnWorkTime = attendanceTime.getWorkTime();
				actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
				flag = false;
				attendanceTime.setFlag(1);
			}

			// 迟到状态
			// 满足于：员工不可以加班后晚到岗 ，签入时间在（默认上班开始时间后1到30分钟）之间
			flag = attendanceTime.getCheckIn().after(workTime)
					&& attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTime, DUTYMIN));
			if (flag) {
				attendanceTime.setBelate(1);
				actualbelateTime = setActualbelateTime(workTime, attendanceTime.getCheckIn());
				flag = false;
			}

			// 早退状态
			// 满足于：员工不可以加班后晚到岗，签出时间在工作结束时间小于等于30 （有早退）
			flag = attendanceTime.getCheckOut().before(workTimeEnd)
					&& DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) <= DUTYMIN;
			if (flag) {
				attendanceTime.setLeaveEarly(1);
				actualLeaveEarly = DatesUtil.getTime(workTimeEnd, attendanceTime.getCheckOut());
				flag = false;
			}

			// 加班
			// 满足于：员工不可以加班后晚到岗 ，签出时间在默认工作时间之后，工作时间结束后签到加班
			flag = workTimeEnd.before(attendanceTime.getCheckOut()) && attendanceInit.getOverTimeType() == 2;
			if (flag) {
				actualOverTime = DatesUtil.getTimeHour(workTimeEnd, attendanceTime.getCheckOut());
				flag = false;
			}
		}

		if (attendanceTime.getCheckIn().before(restBeginTime) && attendanceTime.getCheckOut().after(restEndTime) && attendanceInit.getRestTimeWork() == 3) {
			actualOverTime += restTime; 
		}
//		if (attendanceTime.getCheckIn().before(restBeginTime) && attendanceTime.getCheckOut().after(restEndTime) && attendanceInit.getRestTimeWork() == 2) {
//			actualTurnWorkTime += 1.0;
//		}

		if (attendanceInit.isEarthWork() && DatesUtil.getTime(attendanceTime.getCheckIn(), workTime) >= 20) {
			actualOverTime += 0.5;
		}
		attendanceTime.setTurnWorkTime(actualTurnWorkTime);
		attendanceTime.setOvertime(actualOverTime);
		attendanceTime.setDutytime(actualDutyTime);
		attendanceTime.setLeaveEarlyTime(actualLeaveEarly);
		attendanceTime.setBelateTime(actualbelateTime);
		return attendanceTime;

	}

	private static Double setActualbelateTime(Date beginTime, Date endTime) {
		double actualbelateTime = 0;
		double sec = DatesUtil.getTimeSec(beginTime, endTime);  
		double alltime = Math.floor(sec / 60);
		actualbelateTime = alltime + 1;
		return actualbelateTime;
	}

}
