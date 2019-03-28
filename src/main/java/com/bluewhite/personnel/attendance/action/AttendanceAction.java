package com.bluewhite.personnel.attendance.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.personnel.attendance.dao.AttendanceInitDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.service.ApplicationLeaveService;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.personnel.attendance.service.AttendanceTimeService;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;


@Controller
public class AttendanceAction {

	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AttendanceInitDao attendanceInitDao;
	@Autowired
	private UserService UserService;
	@Autowired
	private AttendanceTimeService attendanceTimeService;
	@Autowired
	private ApplicationLeaveService applicationLeaveService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Attendance.class, "number", "user","time","inOutMode","verifyMode")
				.addRetainTerm(User.class, "id", "userName")
				;
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
	public CommonResponse getAllUser(HttpServletRequest request, String address,String number) {
		CommonResponse cr = new CommonResponse();
		if(!StringUtils.isEmpty(number)){
			cr.setData(attendanceService.findUser(address, number));
		}else{
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
	public CommonResponse updateUser(HttpServletRequest request, String address, String number,String name, int isPrivilege, boolean enabled) {
		CommonResponse cr = new CommonResponse();
		boolean flag = attendanceService.updateUser(address, number, name,  isPrivilege, enabled);
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
	 * 同步考勤机中的人员信息到系统用户表
	 * (同步后，系统用户的编号和考勤机编号绑定，在查看考情记录是可以显示名字，如果未显示，说明未同步或者姓名有问题)
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/syncAttendanceUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse syncAttendanceUser(HttpServletRequest request, String address) {
		CommonResponse cr = new CommonResponse();
		int count = attendanceService.syncAttendanceUser(address);
		cr.setMessage("成功同步"+count+"人员");
		return cr;
	}
	
	
	
	/***** 考勤记录  *******/
	
	
	
	/**
	 * 获取考勤机中全部考勤记录
	 * 
	 * @param request 请求
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
	 * 同步考勤机中全部考勤记录
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/allAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allAttendance(HttpServletRequest request, String address,Date startTime , Date endTime) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(attendanceService.allAttendance(address,startTime,endTime)).toJSON());
		cr.setMessage("同步成功");
		return cr;
	}
	
	/**
	 * 手动修正未同步人员编号时，同步考勤记录而导致的人员姓名为null问题
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/fixAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse fixAttendance(HttpServletRequest request, Date startTime , Date endTime) {
		CommonResponse cr = new CommonResponse();
		int count = attendanceService.fixAttendance(startTime,endTime);
		cr.setMessage("成功修正"+count+"名员工考勤记录");
		return cr;
	}
	
	
	/**
	 * 分页查看考勤
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/findPageAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allAttendance(HttpServletRequest request,PageParameter page,Attendance attendance) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(attendanceService.findPageAttendance(attendance,page)).toJSON());
		cr.setMessage("同步成功");
		return cr;
	}
	
	
	/**
	 * 初始化考勤工作详情
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/addAttendanceTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse addAttendanceTime(HttpServletRequest request,AttendanceTime attendanceTime, Integer sign) {
		CommonResponse cr = new CommonResponse();
		switch (sign) {
		case 1:
			String ex  = attendanceTimeService.checkAttendanceTime(attendanceTime);
			if(ex!=""){
				//当code为2时，已有统计数据，返回前台，由前台确认是否再次统计，再次统计sign=2
				cr.setCode(2);
				cr.setMessage(ex);
			}else{
				cr.setData(attendanceTimeService.findAttendanceTimeCollect(attendanceTime));
				cr.setMessage("初始化成功");
			}
			break;
		case 2:
			cr.setData(attendanceTimeService.findAttendanceTimeCollect(attendanceTime));
			cr.setMessage("初始化成功");
			break;
		}
		return cr;
	}
	
	
	/**
	 * 查询考勤工作详情
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/findAttendanceTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAttendanceTime(HttpServletRequest request,AttendanceTime attendanceTime) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(AttendanceTime.class,"time","number","user","checkIn","checkOut","turnWorkTime",
						"overtime","week","dutytime","flag","leaveEarly","leaveEarlyTime","belate","belateTime")
				.format(attendanceTimeService.findAttendanceTimePage(attendanceTime)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 修改考勤工作详情
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/updateAttendanceTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateAttendanceTime(HttpServletRequest request,AttendanceTime attendanceTime) {
		CommonResponse cr = new CommonResponse();
		if(attendanceTime.getId() == null){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("id为空");
			return cr;
		}
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(AttendanceTime.class,"time","number","user","checkIn","checkOut","turnWorkTime",
						"overtime","week","dutytime","flag","leaveEarly","leaveEarlyTime","belate","belateTime")
				.format(attendanceTimeService.updateAttendanceTime(attendanceTime)).toJSON());
		cr.setMessage("修改成功");
		return cr;
	}
	
	
	/**
	 * 新增修改请假事项
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/addApplicationLeave", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse addApplicationLeave(HttpServletRequest request,ApplicationLeave applicationLeave) {
		CommonResponse cr = new CommonResponse();
		if(applicationLeave.getId() != null){
			applicationLeaveService.updateApplicationLeave(applicationLeave);
			cr.setMessage("修改成功");
		}else{
			applicationLeaveService.saveApplicationLeave(applicationLeave);
			cr.setMessage("新增成功");
		}
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(ApplicationLeave.class,"id")
				.format(applicationLeave).toJSON());
		return cr;
	}
	
	
	/**
	 * 删除请假事项
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/deleteApplicationLeave", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteApplicationLeave(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if(!StringUtils.isEmpty(ids)){
			int count = applicationLeaveService.deleteApplicationLeave(ids);
			cr.setMessage("删除成功"+count+"条");
		}else{
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	/**
	 * 分页查看请假事项
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/getApplicationLeavePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getApplicationLeavePage(ApplicationLeave applicationLeave, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		 cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(ApplicationLeave.class,"id","writeTime","beginTime","endTime","user","longTime",
							"holidayType","type","applyOvertime","content","holiday","tradeDays","addSignIn","applyOvertime")
					.addRetainTerm(User.class, "id", "userName")
					.format(applicationLeaveService.findApplicationLeavePage(applicationLeave, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	
	/*************************************** *************************/
	
	/**
	 * 初始化考勤表的初始数据人员
	 * @return
	 */
	@RequestMapping(value = "/personnel/user", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAttendanceuser() {
		CommonResponse cr = new CommonResponse();
		List<AttendanceInit> list = attendanceInitDao.findAll();
		for(AttendanceInit arr : list){
			User user  = UserService.findByUserName(arr.getUsername());
			if(user!=null){
				arr.setUser(user);
			}
		}
		attendanceInitDao.save(list);
		cr.setMessage("查询成功");
		return cr;
	}
	
	


	/**
	 * 启动考勤机实时监控
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/regEvent", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse regEvent(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		ZkemSDKUtils sdk = new ZkemSDKUtils();
		sdk.initSTA();
		try{
			boolean  flag = sdk.connect("192.168.1.204", 4370);
			System.out.println(flag);
			sdk.regEvent();
		}catch(Exception e){
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
