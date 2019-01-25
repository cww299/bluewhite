package com.bluewhite.personnel.attendance.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
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
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.tax.entity.Tax;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.system.user.entity.User;


@Controller
public class AttendanceAction {

	@Autowired
	private AttendanceService attendanceService;

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
	public CommonResponse getAllUser(HttpServletRequest request, String address) {
		CommonResponse cr = new CommonResponse();
		cr.setData(attendanceService.getAllUser(address));
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
	public CommonResponse allAttendance(HttpServletRequest request, String address) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(attendanceService.allAttendance(address)).toJSON());
		cr.setMessage("同步成功");
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
	 * 按条件查看考勤工作时长
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/personnel/findAttendanceTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAttendanceTime(HttpServletRequest request,Attendance attendance) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(AttendanceTime.class,"time","number","username","checkIn","checkOut","turnWorkTime","overtime","week","dutytime")
				.format(attendanceService.findAttendanceTime(attendance)).toJSON());
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
