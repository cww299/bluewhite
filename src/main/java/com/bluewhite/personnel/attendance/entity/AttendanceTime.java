package com.bluewhite.personnel.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 考勤数据记录 （按日期和员工记录数据，一个员工一天有且仅有一条考勤记录）
 * 
 * @author LB-BY06
 *
 */
@Entity
@Table(name = "person_attendance_time")
public class AttendanceTime extends BaseEntity<Long> {

    /**
     * 员工考勤汇总日期
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "time")
    private Date time;

    /**
     * 员工编号（考勤机上的编号）
     * 
     */
    @Column(name = "number")
    private String number;

    /**
     * 员工id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 员工
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    /**
     * 上班签到时间 0—Check-In
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "check_in")
    private Date checkIn;

    /**
     * 下班签到时间 1—Check-Out
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "check_out")
    private Date checkOut;

    /**
     * 
     * 实际工作时长
     */
    @Column(name = "workTime")
    private Double workTime;

    /**
     * 
     * 出勤时长
     */
    @Column(name = "turnWorkTime")
    private Double turnWorkTime;

    /**
     * 总加班时长
     * 
     */
    @Column(name = "overtime")
    private Double overtime;

    /**
     * 普通加班时长
     * 
     */
    @Column(name = "ordinary_overtime")
    private Double ordinaryOvertime;

    /**
     * 生产加班时长
     * 
     */
    @Column(name = "production_overtime")
    private Double productionOvertime;

    /**
     * 缺勤时长
     * 
     */
    @Column(name = "dutytime")
    private Double dutytime;

    /**
     * 实际缺勤时长（分钟数）
     * 
     */
    @Column(name = "duty_time_minute")
    private Double dutytimMinute;

    /**
     * 请假时长
     * 
     */
    @Column(name = "leave_time")
    private Double leaveTime;

    /**
     * 调休时长
     * 
     */
    @Column(name = "take_work")
    private Double takeWork;

    /**
     * 请假类型(0=事假、1=病假、2=丧假、3=婚假、4=产假、5=护理假、6=抵消迟到）
     */
    @Column(name = "holiday_type")
    private Integer holidayType;

    /**
     * 星期
     * 
     */
    @Column(name = "week")
    private String week;

    /**
     * 考勤状态(0=正常，1=签到缺失，2=请假,3=默认休息日)
     * 
     */
    @Column(name = "flag")
    private Integer flag;

    /**
     * 是否早退(0=否，1=是)
     * 
     */
    @Column(name = "leave_early")
    private Integer leaveEarly;

    /**
     * 早退时长（超过30分钟算缺勤）
     * 
     */
    @Column(name = "leave_early_time")
    private Double leaveEarlyTime;

    /**
     * 是否迟到(0=否，1=是)
     * 
     */
    @Column(name = "belate")
    private Integer belate;

    /**
     * 迟到时长（超过30分钟算缺勤）
     * 
     */
    @Column(name = "belate_time")
    private Double belateTime;

    /**
     * 详情
     */
    @Column(name = "holiday_detail")
    private String holidayDetail;
    /**
     * 员工姓名
     */
    @Transient
    private String userName;

    /**
     * 查询字段（部门）
     */
    @Transient
    private Long orgNameId;

    /**
     * 查询字段（部门）
     */
    @Transient
    private String orgName;

    /**
     * 将考勤设定将入，避免查询
     */
    @Transient
    private AttendanceInit AttendanceInit;

    /**
     * 查询字段
     */
    @Transient
    private Date orderTimeBegin;
    /**
     * 查询字段
     */
    @Transient
    private Date orderTimeEnd;

    public Double getDutytimMinute() {
        return dutytimMinute;
    }

    public void setDutytimMinute(Double dutytimMinute) {
        this.dutytimMinute = dutytimMinute;
    }

    public Double getOrdinaryOvertime() {
        return ordinaryOvertime;
    }

    public void setOrdinaryOvertime(Double ordinaryOvertime) {
        this.ordinaryOvertime = ordinaryOvertime;
    }

    public Double getProductionOvertime() {
        return productionOvertime;
    }

    public void setProductionOvertime(Double productionOvertime) {
        this.productionOvertime = productionOvertime;
    }

    public AttendanceInit getAttendanceInit() {
        return AttendanceInit;
    }

    public void setAttendanceInit(AttendanceInit attendanceInit) {
        attendanceInit.setUser(null);
        AttendanceInit = attendanceInit;
    }

    public Double getTakeWork() {
        return takeWork;
    }

    public void setTakeWork(Double takeWork) {
        this.takeWork = takeWork;
    }

    public Integer getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(Integer holidayType) {
        this.holidayType = holidayType;
    }

    public String getHolidayDetail() {
        return holidayDetail;
    }

    public void setHolidayDetail(String holidayDetail) {
        this.holidayDetail = holidayDetail;
    }

    public Double getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Double leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Integer getLeaveEarly() {
        return leaveEarly;
    }

    public void setLeaveEarly(Integer leaveEarly) {
        this.leaveEarly = leaveEarly;
    }

    public Double getLeaveEarlyTime() {
        return leaveEarlyTime;
    }

    public void setLeaveEarlyTime(Double leaveEarlyTime) {
        this.leaveEarlyTime = leaveEarlyTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getOrderTimeBegin() {
        return orderTimeBegin;
    }

    public void setOrderTimeBegin(Date orderTimeBegin) {
        this.orderTimeBegin = orderTimeBegin;
    }

    public Date getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(Date orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    public Long getOrgNameId() {
        return orgNameId;
    }

    public void setOrgNameId(Long orgNameId) {
        this.orgNameId = orgNameId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBelate() {
        return belate;
    }

    public void setBelate(Integer belate) {
        this.belate = belate;
    }

    public Double getBelateTime() {
        return belateTime;
    }

    public void setBelateTime(Double belateTime) {
        this.belateTime = belateTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Double getDutytime() {
        return dutytime;
    }

    public void setDutytime(Double dutytime) {
        this.dutytime = Math.abs(dutytime);
    }

    public Double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Double workTime) {
        this.workTime = Math.abs(workTime);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Double getTurnWorkTime() {
        return turnWorkTime;
    }

    public void setTurnWorkTime(Double turnWorkTime) {
        this.turnWorkTime = Math.abs(turnWorkTime);
    }

    public Double getOvertime() {
        return overtime;
    }

    public void setOvertime(Double overtime) {
        this.overtime = Math.abs(overtime);
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}
