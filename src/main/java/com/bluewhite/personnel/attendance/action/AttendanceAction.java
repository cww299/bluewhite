package com.bluewhite.personnel.attendance.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.personnel.attendance.dao.RestTypeDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.RestType;
import com.bluewhite.personnel.attendance.service.ApplicationLeaveService;
import com.bluewhite.personnel.attendance.service.AttendanceCollectService;
import com.bluewhite.personnel.attendance.service.AttendanceInitService;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.personnel.attendance.service.AttendanceTimeService;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Controller
public class AttendanceAction {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AttendanceInitService attendanceInitService;
	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceTimeService attendanceTimeService;
	@Autowired
	private ApplicationLeaveService applicationLeaveService;
	@Autowired
	private AttendanceCollectService attendanceCollectService;
	@Autowired
	private RestTypeDao restTypeDao;
	
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Attendance.class, "number", "user", "time", "inOutMode", "verifyMode")
				.addRetainTerm(User.class, "id", "userName");
	}

	/***** 考勤机设置 */

	/**
	 * 获取考勤机中的所有人员信息
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getAllUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getAllUser(HttpServletRequest request, String address, String number) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(number)) {
			cr.setData(attendanceService.findUser(address, number));
		} else {
			cr.setData(attendanceService.getAllUser(address));
		}
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 修改考勤机中的人员信息
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/updateUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateUser(HttpServletRequest request, String address, String number, String name,
			int isPrivilege, boolean enabled) {
		CommonResponse cr = new CommonResponse();
		boolean flag = attendanceService.updateUser(address, number, name, isPrivilege, enabled);
		if (flag) {
			cr.setMessage("修改成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("修改失败");
		}
		return cr;
	}

	/**
	 * 删除考勤机中的人员信息（包括指纹，脸，卡）
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/deleteUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteUser(HttpServletRequest request, String address, String number) {
		CommonResponse cr = new CommonResponse();
		boolean flag = attendanceService.deleteUser(address, number);
		if (flag) {
			cr.setMessage("删除成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("删除失败");
		}
		return cr;
	}

	/**
	 * 同步考勤机中的人员信息到系统用户表 (同步后，系统用户的编号和考勤机编号绑定，在查看考情记录是可以显示名字，如果未显示，说明未同步或者姓名有问题)
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/syncAttendanceUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse syncAttendanceUser(HttpServletRequest request, String address) {
		CommonResponse cr = new CommonResponse();
		int count = attendanceService.syncAttendanceUser(address);
		cr.setMessage("成功同步" + count + "人员");
		return cr;
	}

	/***** 考勤记录 *******/

	/**
	 * 获取考勤机中全部考勤记录
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getAllAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getAllAttendance(HttpServletRequest request, String address) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(attendanceService.getAllAttendance(address)).toJSON());
		cr.setMessage("同步成功");
		return cr;
	}

	/**
	 * 按日期重置考勤机中全部考勤记录
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/restAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse restAttendance(HttpServletRequest request, String address, Date startTime, Date endTime) {
		CommonResponse cr = new CommonResponse();
		attendanceService.restAttendance(address, startTime, endTime);
//		attendanceService.allAttendance(address, startTime, endTime)
		cr.setMessage("重置成功");
		return cr;
	}
	
	
	

	/**
	 * 手动修正未同步人员编号时，同步考勤记录而导致的人员姓名为null问题
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/fixAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse fixAttendance(HttpServletRequest request, Date startTime, Date endTime) {
		CommonResponse cr = new CommonResponse();
		int count = attendanceService.fixAttendance(startTime, endTime);
		cr.setMessage("成功修正" + count + "名员工考勤记录");
		return cr;
	}

	/**
	 * 分页查看考勤
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/findPageAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allAttendance(HttpServletRequest request, PageParameter page, Attendance attendance) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(attendanceService.findPageAttendance(attendance, page)).toJSON());
		cr.setMessage("查找成功");
		return cr;
	}

	/*********** 考勤汇总 **********/

	/**
	 * 初始化考勤工作详情
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws ParseException
	 */
	@RequestMapping(value = "/personnel/intAttendanceTime", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse intAttendanceTime(HttpServletRequest request, AttendanceTime attendanceTime)
			throws ParseException {
		CommonResponse cr = new CommonResponse();
		cr.setData(attendanceTimeService.findAttendanceTimeCollect(attendanceTime)); 
		cr.setMessage("初始化成功");
		return cr;
	}

	/**
	 * 新增在请假事项后的考勤工作详情
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws ParseException
	 */
	@RequestMapping(value = "/personnel/addAttendanceTime", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addAttendanceTime(HttpServletRequest request, AttendanceTime attendanceTime, int sign)
			throws ParseException {
		CommonResponse cr = new CommonResponse();
		switch (sign) {
		case 1:
			String ex = attendanceTimeService.checkAttendanceTime(attendanceTime);
			if (ex != "") {
				// 当code为2时，已有统计数据，返回前台，由前台确认是否再次统计，再次统计sign=2
				cr.setCode(2);
				cr.setMessage(ex);
				cr.setData(attendanceTimeService.findAttendanceTimeCollectList(attendanceTime));
			} else {
				cr.setData(attendanceTimeService.findAttendanceTimeCollectAdd(attendanceTime));
				cr.setMessage("初始化成功");
			}
			break;
		case 2:
			attendanceTimeService.deleteAttendanceTimeCollect(attendanceTime);
			cr.setData(attendanceTimeService.findAttendanceTimeCollectAdd(attendanceTime));
			cr.setMessage("初始化成功");
			break;
		}
		return cr;
	}
	
	/**
	 * 查询考勤工作详情
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/personnel/findAttendanceTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAttendanceTime(HttpServletRequest request, AttendanceTime attendanceTime) throws ParseException {
		CommonResponse cr = new CommonResponse();
		cr.setData(attendanceTimeService.findAttendanceTimeCollectList(attendanceTime)); 
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 修改考勤工作详情
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/updateAttendanceTime", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateAttendanceTime(HttpServletRequest request, AttendanceTime attendanceTime) {
		CommonResponse cr = new CommonResponse();
		if (attendanceTime.getId() == null) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("id为空");
			return cr;
		}
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(AttendanceTime.class, "time", "number", "user", "checkIn", "checkOut", "turnWorkTime",
						"overtime", "week", "dutytime", "flag", "leaveEarly", "leaveEarlyTime", "belate", "belateTime")
				.format(attendanceTimeService.updateAttendanceTime(attendanceTime)).toJSON());
		cr.setMessage("修改成功");
		return cr;
	}
	
	/**
	 * 查看考勤汇总（打印）
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/findAttendanceCollect", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse findAttendanceCollect(HttpServletRequest request, AttendanceCollect attendanceCollect) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(AttendanceCollect.class,"id","time", "turnWork","overtime", "dutyWork", "allWork",
						"manDay", "manDayOvertime", "weekendTurnWork", "leaveTime", "takeWork", "leaveDetails", "remarks","userName","sign","belateDetails")
				.format(attendanceCollectService.findAttendanceCollect(attendanceCollect)).toJSON());
		cr.setMessage("查找成功");
		return cr;
	}
	
	/**
	 * 修改考勤汇总（打印）
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/updateAttendanceCollect", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateAttendanceCollect(HttpServletRequest request, AttendanceCollect attendanceCollect) {
		CommonResponse cr = new CommonResponse();
		AttendanceCollect ot = null;
		if(attendanceCollect.getId()!=null){
			ot = attendanceCollectService.findOne(attendanceCollect.getId());
		}
		attendanceCollectService.update(attendanceCollect, ot);
		cr.setMessage("修改成功");
		return cr;
	}
	

	/**
	 * 新增修改请假事项
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/personnel/addApplicationLeave", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addApplicationLeave(HttpServletRequest request, ApplicationLeave applicationLeave) throws ParseException {
		CommonResponse cr = new CommonResponse();
		if (applicationLeave.getId() != null) {
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("新增成功");
		}
		applicationLeaveService.saveApplicationLeave(applicationLeave);
		return cr;
	}

	/**
	 * 删除请假事项
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/personnel/deleteApplicationLeave", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteApplicationLeave(HttpServletRequest request, String ids) throws ParseException {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			int count = applicationLeaveService.deleteApplicationLeave(ids);
			cr.setMessage("删除成功" + count + "条");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("请假事项不能为空");
		}
		return cr;
	}


	/**
	 * 分页查看请假事项
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getApplicationLeavePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getApplicationLeavePage(ApplicationLeave applicationLeave, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(ApplicationLeave.class, "id", "writeTime", "beginTime", "endTime", "user", "longTime",
						"holidayType", "type", "applyOvertime", "content", "holiday", "tradeDays", "addSignIn",
						"applyOvertime", "time", "holidayDetail")
				.addRetainTerm(User.class, "id", "userName")
				.format(applicationLeaveService.findApplicationLeavePage(applicationLeave, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增修改考勤表的初始数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/addAttendanceInit", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addAttendanceInit(AttendanceInit attendanceInit) {
		CommonResponse cr = new CommonResponse();
		if (attendanceInit.getId() != null) {
			AttendanceInit ot = attendanceInitService.findOne(attendanceInit.getId());
			attendanceInitService.update(attendanceInit, ot);
			cr.setMessage("修改成功");
		} else {
			if(attendanceInit.getUserId()!=null){
				AttendanceInit at = attendanceInitService.findByUserId(attendanceInit.getUserId());
				if(at!=null){
					cr.setMessage("该员工已有考勤初始设定数据，请核对");
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				}else{
					User user = userService.findOne(attendanceInit.getUserId());
					attendanceInit.setUser(user);
					attendanceInitService.save(attendanceInit);
					cr.setMessage("新增成功");
				}
			}
		}
		return cr;
	}

	/**
	 * 分页查询考勤表的初始数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/findAttendanceInit", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAttendanceInit(AttendanceInit attendanceInit, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(AttendanceInit.class, "id", "restType", "restDay", "workTimeSummer", "user",
						"workTimeWinter", "turnWorkTimeSummer", "turnWorkTimeWinter", "restTimeSummer",
						"restTimeWinter", "restSummer", "restWinter", "restTimeWork", "overTimeType", "comeWork","workType")
				.addRetainTerm(User.class, "id", "userName")
				.format(attendanceInitService.findAttendanceInitPage(attendanceInit, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 删除考勤表的初始数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/deleteAttendanceInit", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteAttendanceInit(String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			int count = attendanceInitService.deleteAttendanceInit(ids);
			cr.setMessage("删除成功" + count + "条");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("员工不能为空");
		}
		return cr;
	}
	
	
	/**
	 * 查找休息方式数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/findRestType", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findRestType() {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(RestType.class, "id", "weeklyRestDate", "monthRestDate")
				.format(restTypeDao.findAll()).toJSON());
		return cr;
	}
	
	/**
	 * 修改休息方式数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/updateRestType", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateRestType(RestType restType) {
		CommonResponse cr = new CommonResponse();
		if(restType.getId()!=null){
			RestType ot = restTypeDao.findOne(restType.getId());
			BeanCopyUtils.copyNotEmpty(restType,ot,"");
			cr.setData(ClearCascadeJSON.get()
					.addRetainTerm(RestType.class, "id", "weeklyRestDate", "monthRestDate")
					.format(restTypeDao.save(ot) ).toJSON());
			cr.setMessage("修改成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	/*************************************** *************************/

	/**
	 * 初始化考勤表的初始数据人员
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/user", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAttendanceuser() {
		CommonResponse cr = new CommonResponse();
		List<AttendanceInit> list = attendanceInitService.findAll();
		for (AttendanceInit arr : list) {
			User user = userService.findByUserName(arr.getUsername());
			if (user != null) {
				arr.setUser(user);
			}
		}
		attendanceInitService.save(list);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 启动考勤机实时监控
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/regEvent", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse regEvent(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		ZkemSDKUtils sdk = new ZkemSDKUtils();
		sdk.initSTA();
		try {
			boolean flag = sdk.connect("192.168.1.204", 4370);
			System.out.println(flag);
			sdk.regEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		cr.setMessage("同步成功");
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
