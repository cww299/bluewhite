package com.bluewhite.personnel.attendance.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.system.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 考勤汇总列总表，按月按员工汇总（员工每月一条汇总数据）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_attendance_collect" )
public class AttendanceCollect extends BaseEntity<Long>{
	
	/**
	 * 考勤汇总日期
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	@Column(name = "time")
	private Date time;
	
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
	 * 出勤时长
	 */
	@Column(name = "turn_work")
	private Double turnWork;
	
	/**
	 * 
	 * 加班时长
	 */
	@Column(name = "over_time")
	private Double overtime;
	
	/**
	 * 
	 * 缺勤时长
	 */
	@Column(name = "duty_work")
	private Double dutyWork;
	
	/**
	 * 
	 * 总出勤时长(出勤+加班)
	 */
	@Column(name = "all_work")
	private Double allWork;
	
	/**
	 * 
	 * 工作日出勤时长
	 */
	@Column(name = "man_day")
	private Double manDay;
	
	/**
	 * 
	 * 工作日加班时长
	 */
	@Column(name = "man_day_dvertime")
	private Double manDayOvertime;
	
	/**
	 * 
	 * 周末出勤时长
	 */
	@Column(name = "weekend_turn_work")
	private Double weekendTurnWork;
	
	/**
	 * 请假时长
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
	 * 请假事项详情
	 */
	@Column(name = "leave_details")
	private String leaveDetails="";
	
	/**
	 * 迟到事项详情
	 */
	@Column(name = "belate_details")
	private String belateDetails="";
	
	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private String remarks;
	
	/**
	 * 封存（0=否，1=是）
	 */
	@Column(name = "seal")
	private Integer seal=0;
	
	/**
	 * 姓名
	 */
	@Transient
	private String  userName;
	
	/**
	 * 签字
	 */
	@Transient
	private String  sign;
	
	/**
	 * 查询字段（部门）
	 */
	@Transient
	private Long orgNameId;
	
	
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
	
	
	
	
    public  AttendanceCollect (){
    	
    }
	
	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    
	//有参构造，直接传入AttendanceTime的list，计算出汇总后的数据
    public AttendanceCollect (List<AttendanceTime> list){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	time = list.get(0).getTime();
    	userId = list.get(0).getUserId();
    	turnWork =  list.stream().filter(AttendanceTime->AttendanceTime.getTurnWorkTime()!=null).mapToDouble(AttendanceTime::getTurnWorkTime).sum();
    	overtime =  list.stream().filter(AttendanceTime->AttendanceTime.getOvertime()!=null).mapToDouble(AttendanceTime::getOvertime).sum();
    	dutyWork = list.stream().filter(AttendanceTime->AttendanceTime.getDutytime()!=null).mapToDouble(AttendanceTime::getDutytime).sum();
    	leaveTime = list.stream().filter(AttendanceTime->AttendanceTime.getLeaveTime()!=null).mapToDouble(AttendanceTime::getLeaveTime).sum();
    	takeWork =  list.stream().filter(AttendanceTime->AttendanceTime.getTakeWork()!=null).mapToDouble(AttendanceTime::getTakeWork).sum();
    	//工作日AttendanceTime集合
		List<AttendanceTime> manDayList = null;
		//周末AttendanceTime集合
		List<AttendanceTime> weekendTurnWorkList = null;
    	for(AttendanceTime attendanceTime : list ){
    		 manDayList = new ArrayList<>();
    		 weekendTurnWorkList = new ArrayList<>();
    		boolean flag = DatesUtil.isWeekend(attendanceTime.getTime());
    		if(flag){
    			weekendTurnWorkList.add(attendanceTime);
    		}else{
    			manDayList.add(attendanceTime);
    		}
    	}
    	manDay = manDayList.stream().filter(AttendanceTime->AttendanceTime.getTurnWorkTime()!=null).mapToDouble(AttendanceTime::getTurnWorkTime).sum();
    	manDayOvertime =  manDayList.stream().filter(AttendanceTime->AttendanceTime.getOvertime()!=null).mapToDouble(AttendanceTime::getOvertime).sum();
    	weekendTurnWork = weekendTurnWorkList.stream().filter(AttendanceTime->AttendanceTime.getTurnWorkTime()!=null).mapToDouble(AttendanceTime::getTurnWorkTime).sum();
    	overtime = overtime-takeWork;
    	
    	List<AttendanceTime> belateAttendanceTime = list.stream().filter(AttendanceTime->AttendanceTime.getBelate()==1).collect(Collectors.toList());
    	belateAttendanceTime.forEach(at->{
    					belateDetails += formatter.format(at.getTime())+"迟到"+ at.getBelateTime().intValue()+"分钟,";
    				}
    			);
    	list.stream().filter(AttendanceTime->AttendanceTime.getHolidayDetail()!=null).collect(Collectors.toList())
    	.stream().filter(distinctByKey(b -> b.getHolidayDetail())).forEach(at->{
    					leaveDetails += at.getHolidayDetail()+",";
    	});
    	belateDetails = "共迟到"+belateAttendanceTime.size()+"次："+belateDetails;
    	allWork = NumUtils.sum(turnWork, overtime);
    }
	
    
    
    

	public Integer getSeal() {
		return seal;
	}

	public void setSeal(Integer seal) {
		this.seal = seal;
	}

	public String getBelateDetails() {
		return belateDetails;
	}

	public void setBelateDetails(String belateDetails) {
		this.belateDetails = belateDetails;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
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

	public Double getTakeWork() {
		return takeWork;
	}

	public void setTakeWork(Double takeWork) {
		this.takeWork = takeWork;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	

	public Double getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Double leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getLeaveDetails() {
		return leaveDetails;
	}

	public void setLeaveDetails(String leaveDetails) {
		this.leaveDetails = leaveDetails;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getTurnWork() {
		return turnWork;
	}

	public void setTurnWork(Double turnWork) {
		this.turnWork = turnWork;
	}

	public Double getOvertime() {
		return overtime;
	}

	public void setOvertime(Double overtime) {
		this.overtime = overtime;
	}

	public Double getDutyWork() {
		return dutyWork;
	}

	public void setDutyWork(Double dutyWork) {
		this.dutyWork = dutyWork;
	}

	public Double getAllWork() {
		return allWork;
	}

	public void setAllWork(Double allWork) {
		this.allWork = allWork;
	}

	public Double getManDay() {
		return manDay;
	}

	public void setManDay(Double manDay) {
		this.manDay = manDay;
	}

	public Double getManDayOvertime() {
		return manDayOvertime;
	}

	public void setManDayOvertime(Double manDayOvertime) {
		this.manDayOvertime = manDayOvertime;
	}

	public Double getWeekendTurnWork() {
		return weekendTurnWork;
	}

	public void setWeekendTurnWork(Double weekendTurnWork) {
		this.weekendTurnWork = weekendTurnWork;
	}
	
	
	
	
	

}
