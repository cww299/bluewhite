package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.system.user.entity.User;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

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

    // 一小时的分钟数
    private final static double MINUTES = 60;

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
    public static AttendanceTime attendanceIntTool(boolean sign, Date workTimeStrat, Date workTimeEnd, Date restBeginTime,
        Date restEndTime, double minute, Double turnWorkTime, AttendanceTime attendanceTime,
        AttendanceInit attendanceInit, User user, Double restTime) {
        boolean flag = false;
        // 实际出勤，加班，缺勤,早退时间
        // 实际出勤不可能大于员工默认出勤时长
        double actualTurnWorkTime = 0;
        double actualOverTime = 0;
        double actualDutyTime = 0;
        double actualLeaveEarly = 0;
        double actualbelateTime = 0;
        double actualDutytimMinute = 0;
        // 将上班开始时间延长一分钟计算迟到
        workTimeStrat = DatesUtil.getDaySum(workTimeStrat, 1.0);

        // 当没有签入签出的时候直接返回
        if (attendanceTime.getCheckIn() == null || attendanceTime.getCheckOut() == null) {
            return attendanceTime;
        }
        if (sign) {
            // 出勤时间 缺勤时间 会出现的状态
            // 满足于：员工可以加班后晚到岗，签入时间在（初始化上班开始时间后的加班分钟数+30分钟）之前，签出时间在工作结束时间之后
            // 没有缺勤出现（没缺勤）
            flag = attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, DUTYMIN)))
                && (attendanceTime.getCheckOut().after(workTimeEnd)
                    || attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0
                    || DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) <= DUTYMIN);
            if (flag) {
                actualTurnWorkTime = turnWorkTime;
                flag = false;
            }

            // 满足于：员工可以加班后晚到岗 ，签入时间在（初始化上班开始时间后的加班分钟数+30分钟）之后，签出时间在工作结束时间之前 出现缺勤
            // (迟到时间过长导致缺勤)
            flag = attendanceTime.getCheckIn().after(DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, DUTYMIN)))
                && (attendanceTime.getCheckOut().after(workTimeEnd)
                    || attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0);
            if (flag) {
                // 等于实际工作时间+前一天的加班时间
                actualTurnWorkTime = NumUtils.sum(attendanceTime.getWorkTime(), DatesUtil.getTimeHour(minute));
                // 等于默认出勤-实际出勤
                actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
                // 实际缺勤时长分钟数
                actualDutytimMinute = DatesUtil.getTime(workTimeStrat, attendanceTime.getCheckIn());
                flag = false;
                attendanceTime.setFlag(1);
            }

            // 满足于：员工可以加班后晚到岗 ，签入时间在（初始化上班开始时间后的加班分钟数+30分钟）之后，签出时间在工作结束时间之后 出现缺勤
            // (早退时间过长出现缺勤)
            flag = attendanceTime.getCheckOut().before(workTimeEnd)
                && DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) > DUTYMIN;
            if (flag) {
                actualTurnWorkTime = NumUtils.sum(attendanceTime.getWorkTime(), DatesUtil.getTimeHour(minute));
                actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
                // 实际缺勤时长分钟数
                actualDutytimMinute += DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd);
                flag = false;
                attendanceTime.setFlag(1);
            }

            // 迟到状态
            // 满足于：员工可以加班后晚到岗 ，签入时间在（初始化上班开始时间后的加班分钟数10到30分钟）之间，签出时间在工作结束时间之后
            // 出现迟到
            flag = attendanceTime.getCheckIn().after(DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, LATERMIN)))
                && attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, DUTYMIN)));
            if (flag) {
                attendanceTime.setBelate(1);
                actualbelateTime = setActualbelateTime(DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, LATERMIN)),
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
            double earlyTime = Math.floor(
                DatesUtil.getTime(attendanceTime.getCheckIn(), DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, 0)))
                    / MINUTES)
                * 0.5;
            flag = user.getOrgNameId() == 79
                && attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, 0)))
                && attendanceInit.getOverTimeType() == 2;
            if (flag) {
                actualOverTime =
                    NumUtils.sum(DatesUtil.getTimeHour(workTimeEnd, attendanceTime.getCheckOut()), earlyTime);
                flag = false;
            }

            // 满足于：员工可以加班后晚到岗 ，属于包装部，签入时间在（初始化上班开始时间后的加班分钟数）之前，工作时间结束后签到不算加班加班
            flag = user.getOrgNameId() == 79
                && attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTimeStrat, NumUtils.sum(minute, 0)))
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

        // 正常情况下：员工不可以加班后晚到岗
        if (!sign) {
            // 签入时间在默认上班开始时间之前，签出时间在工作结束时间之后 没有缺勤出现（没缺勤）
            flag = attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTimeStrat, DUTYMIN))
                && (attendanceTime.getCheckOut().after(workTimeEnd)
                    || attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0
                    || DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) <= DUTYMIN);
            if (flag) {
                actualTurnWorkTime = turnWorkTime;
                flag = false;
            }

            // 签入时间在默认上班开始时间之后，签出时间在工作结束时间之后 出现缺勤 (迟到时间过长导致缺勤)
            flag = attendanceTime.getCheckIn().after(DatesUtil.getDaySum(workTimeStrat, DUTYMIN))
                && (attendanceTime.getCheckOut().after(workTimeEnd) || attendanceTime.getCheckOut().compareTo(workTimeEnd) == 0);
            if (flag) {
                // 等于实际工作时间
                actualTurnWorkTime = attendanceTime.getWorkTime();
                // 等于默认出勤-实际出勤
                actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
                // 实际缺勤时长分钟数
                actualDutytimMinute = DatesUtil.getTime(workTimeStrat, attendanceTime.getCheckIn());
                attendanceTime.setFlag(1);
                flag = false;
            }

            // 签出时间在工作结束时间之前 出现缺勤 (早退时间过长出现缺勤)
            flag = attendanceTime.getCheckOut().before(workTimeEnd)
                && DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) > DUTYMIN;
            if (flag) {
                actualTurnWorkTime = attendanceTime.getWorkTime();
                actualDutyTime = NumUtils.sub(turnWorkTime, actualTurnWorkTime);
                // 实际缺勤时长分钟数
                actualDutytimMinute += DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd);
                flag = false;
                attendanceTime.setFlag(1);
            }

            // 迟到状态 签入时间在（默认上班开始时间后1到30分钟）之间
            flag = attendanceTime.getCheckIn().after(workTimeStrat)
                && attendanceTime.getCheckIn().before(DatesUtil.getDaySum(workTimeStrat, DUTYMIN));
            if (flag) {
                attendanceTime.setBelate(1);
                actualbelateTime = setActualbelateTime(workTimeStrat, attendanceTime.getCheckIn());
                flag = false;
            }

            // 早退状态  签出时间在工作结束时间小于等于30 （有早退）
            flag = attendanceTime.getCheckOut().before(workTimeEnd)
                && DatesUtil.getTime(attendanceTime.getCheckOut(), workTimeEnd) <= DUTYMIN;
            if (flag) {
                attendanceTime.setLeaveEarly(1);
                actualLeaveEarly = DatesUtil.getTime(workTimeEnd, attendanceTime.getCheckOut());
                flag = false;
            }

            // 加班 签出时间在默认工作时间之后，工作时间结束后签到加班
            flag = workTimeEnd.before(attendanceTime.getCheckOut()) && attendanceInit.getOverTimeType() == 2;
            if (flag) {
                actualOverTime = DatesUtil.getTimeHour(workTimeEnd, attendanceTime.getCheckOut());
                flag = false;
            }
        }

        if (attendanceInit.getRestTimeWork() == 3) {
            // 签入在休息时间之前
            if (attendanceTime.getCheckIn().compareTo(restBeginTime) != 1
                && attendanceTime.getCheckOut().compareTo(restEndTime) != -1) {
                actualOverTime += restTime;
            }
            if (attendanceTime.getCheckIn().compareTo(restBeginTime) == 1
                && attendanceTime.getCheckIn().compareTo(restEndTime) == -1
                && attendanceTime.getCheckOut().compareTo(restEndTime) == 1) {
                actualOverTime += DatesUtil.getTimeHour(attendanceTime.getCheckIn(), restEndTime);
            }
            if (attendanceTime.getCheckIn().compareTo(restBeginTime) == -1
                && attendanceTime.getCheckOut().compareTo(restBeginTime) == 1
                && attendanceTime.getCheckOut().compareTo(restEndTime) == -1) {
                actualOverTime += DatesUtil.getTimeHour(restBeginTime, attendanceTime.getCheckOut());
            }
            // 之间
            if (attendanceTime.getCheckIn().compareTo(restBeginTime) == 1
                && attendanceTime.getCheckOut().compareTo(restEndTime) == -1) {
                actualOverTime += DatesUtil.getTimeHour(attendanceTime.getCheckIn(), attendanceTime.getCheckOut());
            }
        }
        // if (attendanceTime.getCheckIn().before(restBeginTime) && attendanceTime.getCheckOut().after(restEndTime) &&
        // attendanceInit.getRestTimeWork() == 2) {
        // actualTurnWorkTime += 1.0;
        // }

        if (attendanceInit.isEarthWork() && DatesUtil.getTime(attendanceTime.getCheckIn(), workTimeStrat) >= 20) {
            actualOverTime += 0.5;
        }
        attendanceTime.setTurnWorkTime(actualTurnWorkTime);
        attendanceTime.setOrdinaryOvertime(actualOverTime);
        attendanceTime.setOvertime(actualOverTime);
        attendanceTime.setDutytime(actualDutyTime);
        attendanceTime.setLeaveEarlyTime(actualLeaveEarly);
        attendanceTime.setBelateTime(actualbelateTime);
        attendanceTime.setDutytimMinute(actualDutytimMinute);
        return attendanceTime;

    }

    private static Double setActualbelateTime(Date beginTime, Date endTime) {
        double actualbelateTime = 0;
        double sec = DatesUtil.getTimeSec(beginTime, endTime);
        double alltime = Math.floor(sec / MINUTES);
        actualbelateTime = alltime + 1;
        return actualbelateTime;
    }

    //排除集合中每相邻的不超过数值的打卡记录
    public static List<Attendance> sumIntervalDate(List<Attendance> attendanceList, int intervalMin) {
        List<Attendance> ms = new ArrayList<Attendance>();
        for (int i = 0; i < attendanceList.size(); i++) {
            Date date = attendanceList.get(i).getTime();
            if (i == 0) {
                attendanceList.get(i).setInoutType(0);
                Attendance attendance = attendanceList.get(i);
                ms.add(attendance);
            } else {
                //获取上一个时间
                Attendance lastAttendance = CollUtil.get(ms, -1);
                Long diffMin = DateUtil.between(date, lastAttendance.getTime(), DateUnit.MINUTE);
                if (lastAttendance.getInoutType()==0) { // 如果上次打卡是签入
                    if (diffMin > intervalMin) { // 本次打卡，跟上次打卡大于规定间隔时间，记录本次打卡为签出
                        lastAttendance.setInoutType(1);
                    }
                } else { // 如果上次打卡为签出
                    if (diffMin > intervalMin) { // 本次打卡与上次签出打卡间隔大于规定时间。记录为签入时间
                        attendanceList.get(i).setInoutType(0);
                        Attendance attendance = attendanceList.get(i);
                        ms.add(attendance);
                    } else { // 本次打卡与上次签出打卡间隔小于规定时间。更新签出记录,删除上次签出记录
                        ms.remove(CollUtil.get(ms, -1));
                        attendanceList.get(i).setInoutType(1);
                        Attendance attendance = attendanceList.get(i);
                        ms.add(attendance);
                    }
                }
            }
        }
        return ms.stream().sorted(Comparator.comparing(Attendance::getTime)).collect(Collectors.toList());
    }
}
