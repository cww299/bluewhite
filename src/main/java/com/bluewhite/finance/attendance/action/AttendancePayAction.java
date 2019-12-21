package com.bluewhite.finance.attendance.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.system.user.service.UserService;

/**
 * 财务部 考勤
 * 
 * @author zhangliang
 *
 */
@Controller
public class AttendancePayAction {

	private static final Log log = Log.getLog(AttendancePayAction.class);

	@Autowired
	private AttendancePayService attendancePayService;
	@Autowired
	private UserService userService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FarragoTaskService farragoTaskService;

	/**
	 * 添加考情工资(A工资)
	 * 
	 */
	@RequestMapping(value = "/finance/addAttendance", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public CommonResponse addAttendance(HttpServletRequest request, AttendancePay attendancePay) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(attendancePay.getUsersId())) {
			int count= attendancePayService.addAttendancePay(attendancePay);
			cr.setMessage("成功添加"+count+"人考勤");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("考勤人员不能为空");
		}
		return cr;
	}

	/**
	 * 修改考情工资(A工资)
	 * 
	 */
	@RequestMapping(value = "/finance/updateAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateAttendance(AttendancePay attendancePay) {
		CommonResponse cr = new CommonResponse();
		// 修改
		if (attendancePay.getId()!=null) {
			attendancePayService.updateAttendance(attendancePay);
			cr.setMessage("修改成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("考勤流水不能为空");
		}
		return cr;
	}
	
	/**
	 * 核对预警取消错误预预警
	 * 
	 */
	@RequestMapping(value = "/finance/checkAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse checkAttendance(String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			int count = attendancePayService.checkAttendance(ids);
			cr.setMessage("成功核对"+count+"条错误考勤");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("考勤流水不能为空");
		}
		return cr;
	}

	/**
	 *  批量修改员工考勤工资流水（由于当月预计小时收入不精确，导致要修改上月员工的所有收入流水）
	 * 
	 */
	@RequestMapping(value = "/finance/updateAllAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateAllAttendance(HttpServletRequest request, AttendancePay attendancePay) {
		CommonResponse cr = new CommonResponse();
		// 修改
		if (!StringUtils.isEmpty(attendancePay.getUsersId())) {
			int count = attendancePayService.updateAllAttendance(attendancePay);
			cr.setMessage("成功修改"+count+"人考勤工资流水");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("用户不能为空");
		}
		return cr;
	}

	/**
	 * 删除考勤
	 * 
	 */
	@RequestMapping(value = "/finance/deleteAttendance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateAllAttendance(HttpServletRequest request, String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				AttendancePay attendancePay = attendancePayService.findOne(id);
				Date orderTimeBegin = DatesUtil.getfristDayOftime(attendancePay.getAllotTime());
				Date orderTimeEnd = DatesUtil.getLastDayOftime(attendancePay.getAllotTime());
				List<Task> taskList = taskService.findInSetIds(ids[i],orderTimeBegin,orderTimeEnd);
				List<FarragoTask> farragoTaskList = farragoTaskService.findInSetIds(ids[i], orderTimeBegin, orderTimeEnd);
				if(taskList.size()>0 || farragoTaskList.size()>0){
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage(attendancePay.getUserName()+"当天考勤已分配任务，无法删除，需删除，请先删除任务");
					return cr;
				}else{
					attendancePayService.delete(id);
					count++;
				}
			}
		}
		cr.setMessage("成功删除" + count + "条");
		return cr;
	}


}
