package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.ApplicationLeaveDao;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;

import cn.hutool.core.date.DateUtil;

@Service
public class ApplicationLeaveServiceImpl extends BaseServiceImpl<ApplicationLeave, Long>
    implements ApplicationLeaveService {
    @Autowired
    private AttendanceInitService attendanceInitService;
    @Autowired
    private ApplicationLeaveDao dao;
    @Autowired
    private AttendanceTimeService attendanceTimeService;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private PersonVariableDao personVariableDao;

    @Override
    public PageResult<ApplicationLeave> findApplicationLeavePage(ApplicationLeave param, PageParameter page) {
        Page<ApplicationLeave> pages = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按用户 id过滤
            if (param.getUserId() != null) {
                predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
            }
            // 按姓名查找
            if (!StringUtils.isEmpty(param.getUserName())) {
                predicate.add(cb.equal(root.get("user").get("userName").as(String.class), param.getUserName()));
            }
            // 按部门查找
            if (!StringUtils.isEmpty(param.getOrgNameId())) {
                predicate.add(cb.equal(root.get("user").get("orgNameId").as(Long.class), param.getOrgNameId()));
            }
            // 按申请日期
            if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
                predicate.add(cb.between(root.get("writeTime").as(Date.class), param.getOrderTimeBegin(),
                    param.getOrderTimeEnd()));
            }
            // 是否调休
            if (param.isTradeDays()) {
                predicate.add(cb.equal(root.get("tradeDays"), param.isTradeDays()));
            }
            // 是否补签
            if (param.isAddSignIn()) {
                predicate.add(cb.equal(root.get("addSignIn"), param.isAddSignIn()));
            }
            // 是否申请加班
            if (param.isApplyOvertime()) {
                predicate.add(cb.equal(root.get("applyOvertime"), param.isApplyOvertime()));
            }
            // 是否请假
            if (param.isHoliday()) {
                predicate.add(cb.equal(root.get("holiday"), param.isHoliday()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        }, page);
        PageResult<ApplicationLeave> result = new PageResult<>(pages, page);
        return result;
    }

    @Override
    @Transactional
    public ApplicationLeave saveApplicationLeave(ApplicationLeave applicationLeave) {
        ApplicationLeave oldApplicationLeave = new ApplicationLeave();
        oldApplicationLeave = applicationLeave;
        if (applicationLeave.getId() != null) {
            oldApplicationLeave = dao.findOne(applicationLeave.getId());
            if (oldApplicationLeave.isAddSignIn()) {
                List<Attendance> attendance = attendanceDao.findByApplicationLeaveId(applicationLeave.getId());
                if (attendance.size() > 0) {
                    attendanceDao.delete(attendance);
                }
            }
            BeanCopyUtils.copyNotEmpty(applicationLeave, oldApplicationLeave, "");
        }
        setApp(oldApplicationLeave);
        return dao.save(oldApplicationLeave);
    }

    @Transactional
    private ApplicationLeave setApp(ApplicationLeave applicationLeave) {
        dao.save(applicationLeave);
        // 获取当前日期的固定休息日
        PersonVariable restType =
            personVariableDao.findByTypeAndTime(0, DatesUtil.getFirstDayOfMonth(applicationLeave.getWriteTime()));
        // 详细
        String holidayDetail = "";
        // 获取到当前员工统计一个月的考勤详细
        AttendanceTime attendanceTimeParme = new AttendanceTime();
        attendanceTimeParme.setUserId(applicationLeave.getUserId());
        attendanceTimeParme.setOrderTimeBegin(DatesUtil.getFirstDayOfMonth(applicationLeave.getWriteTime()));
        List<AttendanceTime> attendanceTimeList = attendanceTimeService
            .attendanceTimeByApplication(attendanceTimeService.findAttendanceTime(attendanceTimeParme));
        // 获取员工考勤的初始化参数
        AttendanceInit attendanceInit = attendanceInitService.findByUserId(applicationLeave.getUserId());
        if (attendanceInit == null) {
            throw new ServiceException("该员工没有考勤设定数据，无法申请，请先添加考勤设定数据");
        }
        JSONArray jsonArray = JSON.parseArray(applicationLeave.getTime());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String date = jsonObject.getString("date");
            String time = jsonObject.getString("time");
            // 请假
            if (applicationLeave.isHoliday()) {
                String[] dateArr = date.split("~");
                for (String dateString : dateArr) {
                    Date de = DateUtil.parse(dateString);
                    // 1.周休一天，
                    if (attendanceInit.getRestType() == 1) {
                        if (!StringUtils.isEmpty(restType.getKeyValue())) {
                            String[] weeklyRestDate = restType.getKeyValue().split(",");
                            if (weeklyRestDate.length > 0) {
                                for (int j = 0; j < weeklyRestDate.length; j++) {
                                    if (DatesUtil.getfristDayOftime(de)
                                        .compareTo(DateUtil.parse(weeklyRestDate[j])) == 0) {
                                        throw new ServiceException(dateString + "为固定休息日，无需请假");
                                    }
                                }
                            }
                        }
                    }
                    // 2.月休两天
                    if (attendanceInit.getRestType() == 2) {
                        if (!StringUtils.isEmpty(restType.getKeyValue())) {
                            String[] monthRestDate = restType.getKeyValueTwo().split(",");
                            if (monthRestDate.length > 0) {
                                for (int j = 0; j < monthRestDate.length; j++) {
                                    if (DatesUtil.getfristDayOftime(de)
                                        .compareTo(DateUtil.parse(monthRestDate[j])) == 0) {
                                        throw new ServiceException(dateString + "为固定休息日，无需请假");
                                    }
                                }
                            }
                        }
                    }
                    // 获取约定休息的日期
                    String[] restDayArr = null;
                    if (!StringUtils.isEmpty(attendanceInit.getRestDay())) {
                        restDayArr = attendanceInit.getRestDay().split(",");
                        if (restDayArr.length > 0) {
                            for (int j = 0; j < restDayArr.length; j++) {
                                if (DatesUtil.getfristDayOftime(de).compareTo(DateUtil.parse(restDayArr[j])) == 0) {
                                    throw new ServiceException(dateString + "为约定休息日，无需请假");
                                }
                            }
                        }
                    }
                }
                
                String detail = "";
                switch (applicationLeave.getHolidayType()) {
                    case 0:
                        detail = "事假";
                        break;
                    case 1:
                        detail = "病假";
                        break;
                    case 2:
                        detail = "丧假";
                        break;
                    case 3:
                        detail = "婚假";
                        break;
                    case 4:
                        detail = "产假";
                        break;
                    case 5:
                        detail = "护理假";
                        break;
                    case 6:
                        detail = "抵消迟到";
                        break;
                }
                holidayDetail = holidayDetail.equals("") ? (date + detail + time + "小时")
                    : (holidayDetail + "," + date + detail + time + "小时");
                continue;
            }
            // 调休申请
            if (applicationLeave.isTradeDays()) {
                holidayDetail = holidayDetail.equals("") ? (date + "调休" + time + "小时")
                    : holidayDetail + "," + date + "调休" + time + "小时";
                continue;
            }

            AttendanceTime attendanceTime = null;
            Date dateLeave = DateUtil.parse(date, "yyyy-MM-dd");;
            // 过滤出选择日期的考勤详细
            for (AttendanceTime at : attendanceTimeList) {
                if (at.getTime().compareTo(dateLeave) == 0) {
                    attendanceTime = at;
                    break;
                }
            }
            // 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
            boolean flag = DatesUtil.belongCalendar(dateLeave);
            // 上班开始时间
            Date workTime = null;
            // 上班结束时间
            Date workTimeEnd = null;
            // 中午休息开始时间
            Date restBeginTime = null;
            // 中午休息结束时间
            Date restEndTime = null;
            // 休息时长
            Double restTime = null;

            // flag=ture 为夏令时
            if (flag) {
                String[] workTimeArr = attendanceInit.getWorkTimeSummer().split(" - ");
                // 将 工作间隔开始结束时间转换成当前日期的时间
                workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
                workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
                String[] restTimeArr = attendanceInit.getRestTimeSummer().split(" - ");
                // 将 休息间隔开始结束时间转换成当前日期的时间
                restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
                restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
                restTime = attendanceInit.getRestSummer();
            } else {
                // 冬令时
                String[] workTimeArr = attendanceInit.getWorkTimeWinter().split(" - ");
                // 将 工作间隔开始结束时间转换成当前日期的时间
                workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
                workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
                // 将 休息间隔开始结束时间转换成当前日期的时间
                String[] restTimeArr = attendanceInit.getRestTimeWinter().split(" - ");
                // 将 休息间隔开始结束时间转换成当前日期的时间
                restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
                restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
                restTime = attendanceInit.getRestWinter();
            }

            // 补签
            if (applicationLeave.isAddSignIn()) {
                Attendance attendance = new Attendance();
                Date tm = DateUtil.parse(date);
                if (DatesUtil.timeIsZero(tm)) {
                    tm = time.equals("0") ? workTime : workTimeEnd;
                }
                attendance.setTime(tm);
                attendance.setUserId(applicationLeave.getUserId());
                attendance.setApplicationLeaveId(applicationLeave.getId());
                attendance.setInOutMode(2);
                attendanceDao.save(attendance);
                holidayDetail += date + (time.equals("0") ? "补签入," : "补签出,");
                continue;
            }

            // 加班
            if (applicationLeave.isApplyOvertime()) {
                if (attendanceTime == null) {
                    throw new ServiceException("该员工未统计考勤，无法比对加班时长，请先初始化该员工考勤");
                }
                // 获取所有的加班时间
                double actualOverTime = 0.0;
//                if (applicationLeave.getOvertimeType() != 2 && attendanceInit.getOverTimeType() == 2) {
//                    throw new ServiceException("该员工属于按打卡核算加班，无需填写加班申请");
//                }
                if (attendanceTime.getCheckIn() != null && attendanceTime.getCheckOut() != null) {
                    // 获取所有的休息日
                    if (attendanceInit.getRestDay() != null || attendanceInit.getRestType() != null) {
                        List<String> allArr = new ArrayList<>();
                        if (attendanceInit.getRestType() == 1) {
                            String[] weekArr = restType.getKeyValue().split(",");
                            List<String> listWeek = Arrays.asList(weekArr);
                            allArr.addAll(listWeek);
                        }
                        if (attendanceInit.getRestType() == 2) {
                            String[] monthArr = restType.getKeyValueTwo().split(",");
                            List<String> listMonth = Arrays.asList(monthArr);
                            allArr.addAll(listMonth);
                        }

                        if (attendanceInit.getRestDay() != null) {
                            String[] restArr = attendanceInit.getRestDay().split(",");
                            List<String> listRest = Arrays.asList(restArr);
                            allArr.addAll(listRest);
                        }

                        if (allArr.contains(date.substring(0, 10))) {
                            double one = 0;
                            double two = 0;
                            // 签入时间在中午休息开始时间之前
                            if (attendanceTime.getCheckIn().before(restBeginTime)) {
                                one = DatesUtil.getTime(attendanceTime.getCheckIn(), restBeginTime);
                            }
                            // 签出时间在中午休息结束时间之后
                            if (attendanceTime.getCheckOut().after(restEndTime)) {
                                two = DatesUtil.getTime(restEndTime, attendanceTime.getCheckOut());
                            }
                            actualOverTime = DatesUtil.getTimeHour(NumUtils.sum(one, two));
                            if (attendanceInit.getRestTimeWork() == 3) {
                                actualOverTime = NumUtils.sum(actualOverTime, restTime);
                            }
                        } else {
                            if (workTimeEnd.before(attendanceTime.getCheckOut())) {
                                actualOverTime = DatesUtil.getTimeHour(workTimeEnd, attendanceTime.getCheckOut());
                                if (attendanceTime.getCheckIn().before(restBeginTime)
                                    && attendanceTime.getCheckOut().after(restEndTime)) {
                                    actualOverTime = NumUtils.sum(actualOverTime, restTime);
                                }
                            }
                        }
                    }
                }
                // 加班申请
                if (applicationLeave.isApplyOvertime()) {
                    if (attendanceTime.getCheckIn() != null && attendanceTime.getCheckOut() != null
                        && actualOverTime < Double.valueOf(time)) {
                        throw new ServiceException("根据" + date + "的签到时间该员工加班时间为" + actualOverTime + "小时，加班申请时间有误请重新核对");
                    }
                    String overString = "";
                    switch (applicationLeave.getOvertimeType()) {
                        case 1:
                            overString = "申请加班";
                            break;
                        case 2:
                            overString = "撤销加班";
                            break;
                        case 3:
                            overString = "生产加班";
                            break;
                    }
                    holidayDetail = holidayDetail.equals("") ? (date + overString + time + "小时")
                        : (holidayDetail + "," + date + overString + time + "小时");
                }
            }
        }
        // 调休
        if (applicationLeave.isTradeDays()) {
            // 总加班时长
            double sumOverTime = attendanceTimeList.stream().mapToDouble(AttendanceTime::getOvertime).sum();
            // 已经调休总时长
            double takeWork = attendanceTimeList.stream().filter(AttendanceTime -> AttendanceTime.getTakeWork() != null)
                .mapToDouble(AttendanceTime::getTakeWork).sum();
            if (takeWork > sumOverTime) {
                throw new ServiceException("总调休时长不可大于总加班时长，请检查调休情况");
            }
        }
        applicationLeave.setHolidayDetail(holidayDetail);
        return applicationLeave;
    }

    @Override
    @Transactional
    public int deleteApplicationLeave(String ids) {
        int count = 0;
        String[] arrIds = ids.split(",");
        for (int i = 0; i < arrIds.length; i++) {
            Long id = Long.valueOf(arrIds[i]);
            ApplicationLeave applicationLeave = dao.findOne(id);
            if (applicationLeave.isAddSignIn()) {
                List<Attendance> attendance = attendanceDao.findByApplicationLeaveId(id);
                if (attendance.size() > 0) {
                    attendanceDao.delete(attendance);
                }
            }
            count++;
            dao.delete(applicationLeave);
        }
        return count;
    }

    @Override
    public void defaultRetroactive(ApplicationLeave applicationLeave) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", DateUtil.format(applicationLeave.getWriteTime(), "yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("time", applicationLeave.getSign());
        jsonArray.add(jsonObject);
        applicationLeave.setTime(JSONArray.toJSONString(jsonArray));
        applicationLeave.setAddSignIn(true);
        applicationLeave.setWriteTime(DatesUtil.getLastDayOfMonth(applicationLeave.getWriteTime()));
        saveApplicationLeave(applicationLeave);
    }

}
