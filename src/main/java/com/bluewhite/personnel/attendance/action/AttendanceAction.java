package com.bluewhite.personnel.attendance.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.personnel.attendance.service.ApplicationLeaveService;
import com.bluewhite.personnel.attendance.service.AttendanceCollectService;
import com.bluewhite.personnel.attendance.service.AttendanceInitService;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.personnel.attendance.service.AttendanceTimeService;
import com.bluewhite.personnel.attendance.service.PersonVariableService;
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
	private PersonVariableDao personVariableDao;
	@Autowired
	private PersonVariableService personVariableService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Attendance.class, "number", "user", "time", "inOutMode", "verifyMode")
				.addRetainTerm(User.class, "id", "userName", "number");
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
	 * 修改考勤机中的人员信息
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getuser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getuser(HttpServletRequest request, String address, String number) {
		CommonResponse cr = new CommonResponse();
		cr.setData(attendanceService.getUser(address, number));
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
		int count1 = attendanceService.fixAttendance(null, null);
		cr.setMessage("成功同步" + count + "人员,同时成功修正" + count1 + "名员工考勤记录");
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
	public CommonResponse getAllAttendance(HttpServletRequest request, String address, Date startTime, Date endTime) {
		CommonResponse cr = new CommonResponse();
		cr.setData(
				clearCascadeJSON.format(attendanceService.allAttendance(address, startTime, endTime, null)).toJSON());
		cr.setMessage("同步成功");
		return cr;
	}

	/**
	 * 按条件重置考勤机中考勤记录
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/restAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse restAttendance(HttpServletRequest request, String address, Date startTime, Date endTime,
			Long userId) {
		CommonResponse cr = new CommonResponse();
		int count = attendanceService.restAttendance(address, startTime, endTime, userId);
		cr.setMessage("成功重置" + count + "条考勤记录");
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
	 * 请假事项后的统计考勤
	 * 
	 * @return cr
	 * @throws ParseException
	 */
	@RequestMapping(value = "/personnel/addAttendanceTime", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addAttendanceTime(AttendanceTime attendanceTime) throws ParseException {
		CommonResponse cr = new CommonResponse();
		// 同步锁，批量新增
		synchronized (this) {
			List<Map<String, Object>> maps = attendanceTimeService.syncAttendanceTimeCollect(attendanceTime);
			cr.setData(maps);
			cr.setMessage("统计成功");
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
	public CommonResponse findAttendanceTime(HttpServletRequest request, AttendanceTime attendanceTime)
			throws ParseException {
		CommonResponse cr = new CommonResponse();
		cr.setData(attendanceTimeService.findAttendanceTimeCollectList(attendanceTime));
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 修改考勤工作详情
	 * 
	 * @param request
	 *            请求
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
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/findAttendanceCollect", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse findAttendanceCollect(HttpServletRequest request, AttendanceCollect attendanceCollect) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(AttendanceCollect.class, "id", "time", "turnWork", "overtime", "dutyWork", "allWork",
						"manDay", "manDayOvertime", "weekendTurnWork", "leaveTime", "takeWork", "leaveDetails",
						"remarks", "userName", "sign", "belateDetails", "ordinaryOvertime", "productionOvertime")
				.format(attendanceCollectService.findAttendanceCollect(attendanceCollect)).toJSON());
		cr.setMessage("查找成功");
		return cr;
	}

	/**
	 * 修改考勤汇总（打印）
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/updateAttendanceCollect", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateAttendanceCollect(HttpServletRequest request, AttendanceCollect attendanceCollect) {
		CommonResponse cr = new CommonResponse();
		AttendanceCollect ot = null;
		if (attendanceCollect.getId() != null) {
			ot = attendanceCollectService.findOne(attendanceCollect.getId());
		}
		attendanceCollectService.update(attendanceCollect, ot);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 存档考勤汇总
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/sealAttendanceCollect", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse sealAttendanceCollect(AttendanceCollect attendanceCollect) {
		CommonResponse cr = new CommonResponse();
		attendanceCollectService.sealAttendanceCollect(attendanceCollect);
		cr.setMessage("存档成功");
		return cr;
	}

	/**
	 * 车间人员根据填写考勤情况和打卡考勤汇总进行对比
	 * 
	 * @param attendanceCollect
	 * @return
	 */
	@RequestMapping(value = "/personnel/workshopAttendanceContrast", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse workshopAttendanceContrast(AttendanceTime attendanceTime) {
		CommonResponse cr = new CommonResponse();
		cr.setData(attendanceTimeService.workshopAttendanceContrast(attendanceTime));
		cr.setMessage("查询成功");
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
	public CommonResponse addApplicationLeave(ApplicationLeave applicationLeave, String userIds) throws ParseException {
		CommonResponse cr = new CommonResponse();
		if (applicationLeave.getId() != null) {
			cr.setMessage("修改成功");
			applicationLeaveService.saveApplicationLeave(applicationLeave);
		} else {
			ApplicationLeave applicationLeaveUser = null;
			if (applicationLeave.getUserId() != null) {
				userIds = applicationLeave.getUserId().toString();
			}
			if (!StringUtils.isEmpty(userIds)) {
				String[] idArr = userIds.split(",");
				if (idArr.length > 0) {
					for (String id : idArr) {
						applicationLeaveUser = new ApplicationLeave();
						BeanCopyUtils.copyNotEmpty(applicationLeave, applicationLeaveUser);
						applicationLeaveUser.setUserId(Long.valueOf(id));
						applicationLeaveService.saveApplicationLeave(applicationLeaveUser);
					}
				}
				cr.setMessage("新增成功" + idArr.length + "条");
			}
		}
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
	 * 默认补签
	 * 
	 * @param request
	 * @param ids
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/personnel/defaultRetroactive", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse defaultRetroactive(HttpServletRequest request, ApplicationLeave applicationLeave)
			throws ParseException {
		CommonResponse cr = new CommonResponse();
		applicationLeaveService.defaultRetroactive(applicationLeave);
		cr.setMessage("补签成功");
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
			if (attendanceInit.getUserId() != null) {
				AttendanceInit at = attendanceInitService.findByUserId(attendanceInit.getUserId());
				if (at != null) {
					cr.setMessage("该员工已有考勤初始设定数据，请核对");
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				} else {
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
						"restTimeWinter", "restSummer", "restWinter", "restTimeWork", "overTimeType", "comeWork",
						"workType", "earthWork", "eatType", "fail")
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
	 * 新增休息方式数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/addRestType", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addRestType(PersonVariable personVariable) {
		CommonResponse cr = new CommonResponse();
		PersonVariable ot = personVariableDao.findByTypeAndTime(0,DatesUtil.getFirstDayOfMonth(personVariable.getTime()));
		if(ot == null){
			personVariableDao.save(personVariable);
			cr.setMessage("添加成功");
		}else{
			cr.setMessage("该月已添加过固定休息方式，无法再次新增，请选择修改");
		}
		return cr;
	}

	/**
	 * 查找休息方式数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/findRestTypePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findRestTypePage(PersonVariable personVariable, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		personVariable.setType(0);
		cr.setData(ClearCascadeJSON.get().addRetainTerm(PersonVariable.class, "id", "keyValue", "keyValueTwo")
				.format(personVariableService.findPersonVariablePage(personVariable,page)).toJSON());
		return cr;
	}

	/**
	 * 修改休息方式数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/updateRestType", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateRestType(PersonVariable personVariable) {
		CommonResponse cr = new CommonResponse();
		if (personVariable.getId() != null) {
			PersonVariable ot = personVariableDao.findOne(personVariable.getId());
			BeanCopyUtils.copyNotEmpty(personVariable, ot, "");
			cr.setMessage("修改成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	/**
	 * 查找所有初始化人员
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personnel/findInitAll", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findInit() {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get().addRetainTerm(AttendanceInit.class, "id", "user")
				.addRetainTerm(User.class, "id", "userName").format(attendanceInitService.findInit()).toJSON());
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
