package com.bluewhite.personnel.attendance.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.personnel.attendance.dao.ApplicationLeaveDao;
import com.bluewhite.personnel.attendance.dao.AttendanceInitDao;
import com.bluewhite.personnel.attendance.dao.AttendanceTimeDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.system.user.entity.User;

@Service
public class ApplicationLeaveServiceImpl extends BaseServiceImpl<ApplicationLeave, Long>
		implements ApplicationLeaveService {
	@Autowired
	private AttendanceInitService attendanceInitService;
	@Autowired
	private ApplicationLeaveDao dao;
	@Autowired
	private AttendanceTimeDao attendanceTimeDao;
	@Autowired
	private AttendanceInitDao attendanceInitDao;

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
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<ApplicationLeave> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public ApplicationLeave saveApplicationLeave(ApplicationLeave applicationLeave) {
		ApplicationLeave oldApplicationLeave = null;
		if (applicationLeave.getId() != null) {
			oldApplicationLeave = dao.findOne(applicationLeave.getId());
			BeanCopyUtils.copyNotEmpty(applicationLeave, oldApplicationLeave, "");
		} else {
			oldApplicationLeave = applicationLeave;
		}
		try {
			setApp(oldApplicationLeave);
		} catch (ParseException e) {
			throw new ServiceException(e);
		}
		return dao.save(oldApplicationLeave);
	}

	private ApplicationLeave setApp(ApplicationLeave applicationLeave) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject jo = JSON.parseObject(applicationLeave.getTime());
		String date = jo.getString("date");
		String time = jo.getString("time");
		//获取时间区间
		String[] dateArr =  date.split("~");
		Date dateLeave =null;
		AttendanceTime attendanceTime = null;
		// 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
		boolean flag = false;
		// 上班开始时间
		Date workTime = null;
		// 上班结束时间
		Date workTimeEnd = null;
		// 中午休息开始时间
		Date restBeginTime = null;
		// 中午休息结束时间
		Date restEndTime = null;
		// 出勤时长
		Double turnWorkTime = null;
		// 休息时长
		Double restTime = null;
        if(dateArr.length<2){
       	 	dateLeave = sdf.parse(date);
       	 	attendanceTime = attendanceTimeDao.findByUserIdAndTime(applicationLeave.getUserId(), dateLeave);
		try {
			flag = DatesUtil.belongCalendar(dateLeave);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 获取员工考勤的初始化参数
		AttendanceInit attendanceInit = attendanceInitService.findByUserId(applicationLeave.getUserId());
		if(attendanceInit==null){
			throw new ServiceException("该员工没有考勤初始化数据，无法申请，请先添加考勤初始数据");
		}
		// flag=ture 为夏令时
		if (flag) {
			String[] workTimeArr = attendanceInit.getWorkTimeSummer().split("-");
			// 将 工作间隔开始结束时间转换成当前日期的时间
			workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
			workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
			String[] restTimeArr = attendanceInit.getRestTimeSummer().split("-");
			// 将 休息间隔开始结束时间转换成当前日期的时间
			restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
			restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
			restTime = attendanceInit.getRestSummer();
			turnWorkTime = attendanceInit.getTurnWorkTimeSummer();
		}
		// 冬令时
		String[] workTimeArr = attendanceInit.getWorkTimeWinter().split("-");
		// 将 工作间隔开始结束时间转换成当前日期的时间
		workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
		workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
		// 将 休息间隔开始结束时间转换成当前日期的时间
		String[] restTimeArr = attendanceInit.getRestTimeSummer().split("-");
		// 将 休息间隔开始结束时间转换成当前日期的时间
		restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
		restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
		restTime = attendanceInit.getRestWinter();
		turnWorkTime = attendanceInit.getTurnWorkTimeWinter();
        }
		String holidayDetail = "";
		if (applicationLeave.isHoliday()) {
			// (0=事假、1=病假、2=丧假、3=婚假、4=产假、5=护理假
			String detail = "";
			switch (applicationLeave.getHolidayType()) {
			case 0:
				detail = "事假";
				break;
			case 1:
				detail = "病假";
				break;
			case 2:
				detail = "事假";
				break;
			case 3:
				detail = "丧假";
				break;
			case 4:
				detail = "婚假";
				break;
			case 5:
				detail = "产假";
				break;
			case 6:
				detail = "护理假";
				break;
			}
			holidayDetail = date + detail + time + "小时";
		}
		if (applicationLeave.isAddSignIn()) {
			holidayDetail = date + (time == "0" ? "补签入" : "补签入");
		}
		if (applicationLeave.isApplyOvertime()) {
			if(attendanceTime==null){
				throw new ServiceException("该员工未初始化考勤详细，无法比对加班时长，请先初始化该员工考勤");
			}
			if(workTimeEnd.before(attendanceTime.getCheckOut())){
				double actualOverTime = DatesUtil.getTimeHour(workTimeEnd, attendanceTime.getCheckOut());	
				if(actualOverTime<Double.valueOf(time) ){
					throw new ServiceException("根据签到时间当日该员工加班时间为"+actualOverTime+"小时，加班申请时间有误请重新核对");
				}
			}
			holidayDetail = date + "申请加班" + time + "小时";
		}
		if (applicationLeave.isTradeDays()) {
			holidayDetail = date + "调休" + time + "小时";
		}
		applicationLeave.setHolidayDetail(holidayDetail);

		return applicationLeave;

	}

	@Override
	public int deleteApplicationLeave(String ids) {
		int count = 0;
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.valueOf(arrIds[i]);
			ApplicationLeave applicationLeave = dao.findOne(id);
			applicationLeave.setUser(null);
			dao.delete(applicationLeave);
			count++;
		}
		return count;
	}
}
