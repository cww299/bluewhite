package com.bluewhite.personnel.attendance.entity;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.finance.entity.CollectPay;

/**
 * 考勤汇总列表
 * @author zhangliang
 *
 */
public class AttendanceCollect {
	
	/**
	 * 
	 * 出勤
	 */
	private Double turnWork;
	
	/**
	 * 
	 * 加班
	 */
	private Double overtime;
	
	/**
	 * 
	 * 缺勤
	 */
	private Double dutyWork;
	
	/**
	 * 
	 * 总出勤(出勤+加班)
	 */
	private Double allWork;
	
	/**
	 * 
	 * 工作日出勤
	 */
	private Double manDay;
	
	/**
	 * 
	 * 工作日加班
	 */
	private Double manDayOvertime;
	
	/**
	 * 
	 * 周末出勤
	 */
	private Double weekendTurnWork;
	
	
    public  AttendanceCollect (){
    	
    }
	
	//有参构造，直接传入AttendanceTime的list，计算出汇总后的数据
    public AttendanceCollect (List<AttendanceTime> list){
    	turnWork =  list.stream().filter(AttendanceTime->AttendanceTime.getTurnWorkTime()!=null).mapToDouble(AttendanceTime::getTurnWorkTime).sum();
    	overtime =  list.stream().filter(AttendanceTime->AttendanceTime.getOvertime()!=null).mapToDouble(AttendanceTime::getOvertime).sum();
    	dutyWork = list.stream().filter(AttendanceTime->AttendanceTime.getDutytime()!=null).mapToDouble(AttendanceTime::getDutytime).sum();
    	allWork = NumUtils.sum(turnWork, overtime);
    	
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
