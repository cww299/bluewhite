package com.bluewhite.finance.attendance.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

/**
 * 财务部  考勤 
 * @author zhangliang
 *
 */
@Controller
public class AttendanceAction {
	
	private static final Log log = Log.getLog(AttendanceAction.class);
	
		@Autowired
		private AttendancePayService attendancePayService;
		@Autowired
		private UserService userService;
		
		private ClearCascadeJSON clearCascadeJSON;
	
		{
			clearCascadeJSON = ClearCascadeJSON
					.get()
					.addRetainTerm(PayB.class,"id","userName","allotTime","payNumber","bacth","productName",
							"allotTime","performancePayNumber");
		}
		
		
		/** 
		 * 添加考情工资(A工资)
		 * 
		 */
		@RequestMapping(value = "/finance/addAttendance", method = RequestMethod.GET)
		@ResponseBody
		public CommonResponse allAttendancePay(HttpServletRequest request,AttendancePay attendancePay) {
			CommonResponse cr = new CommonResponse();
				//新增考勤工资，一键增加考勤
				if(!StringUtils.isEmpty(attendancePay.getUsersId())){
					for (int i = 0; i < attendancePay.getUsersId().length; i++) {
						PageParameter page = new PageParameter();
						page.setSize(Integer.MAX_VALUE);
						Long userid = Long.parseLong(attendancePay.getUsersId()[i]);
						AttendancePay attendance = new AttendancePay();
						User user = userService.findOne(userid);
						attendance.setUserId(userid);
						if(attendancePay.getAllotTime() == null){
							Calendar  cal = Calendar.getInstance();
							cal.add(Calendar.DATE,-1);
							attendance.setAllotTime(cal.getTime());
						}else{
							attendance.setAllotTime(attendancePay.getAllotTime());
						}
						attendance.setOrderTimeBegin(DatesUtil.getFirstDayOfMonth(attendance.getAllotTime()));
						attendance.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(attendance.getAllotTime()));
						if(attendancePayService.findPages(attendance, page).getRows().size()>0){
							cr.setMessage(user.getUserName()+"该月已存在考情记录，无需再次添加，请重新选择");
							cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
							return cr;
						}else{
							attendance.setType(attendancePay.getType());
							attendance.setWorkTime(attendancePay.getWorkTimes()[i]);
							attendance.setWorkPrice(user.getPrice());
							attendance.setUserName(user.getUserName());
							attendancePayService.addAttendancePay(attendance);
							cr.setMessage("任务分配成功");
						}
					}
				}else{
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
		public CommonResponse updateAttendance(HttpServletRequest request,AttendancePay attendancePay) {
			CommonResponse cr = new CommonResponse();
			//修改
			if(!StringUtils.isEmpty(attendancePay.getId())){
				AttendancePay oldAttendancePay = attendancePayService.findOne(attendancePay.getId());
				BeanCopyUtils.copyNullProperties(oldAttendancePay,attendancePay);
				attendancePay.setCreatedAt(oldAttendancePay.getCreatedAt());
				attendancePayService.addAttendancePay(attendancePay);
				cr.setMessage("修改成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("考勤流水不能为空");
			}
			return cr;
		}
		
		
		/** 
		 * 修改员工考情工资流水（由于当月预计小时收入不精确，导致要修改当月员工的所有收入流水）
		 * 
		 */
		@RequestMapping(value = "/finance/updateAllAttendance", method = RequestMethod.GET)
		@ResponseBody
		public CommonResponse updateAllAttendance(HttpServletRequest request,AttendancePay attendancePay) {
			CommonResponse cr = new CommonResponse();
			PageParameter page = new PageParameter();
			page.setSize(Integer.MAX_VALUE);
			//修改
			if(!StringUtils.isEmpty(attendancePay.getUsersId())){
				for (int i = 0; i < attendancePay.getUsersId().length; i++) {
					Long userid = Long.parseLong(attendancePay.getUsersId()[i]);
					attendancePay.setUserId(userid);
					attendancePay.setOrderTimeBegin(DatesUtil.getFirstDayOfMonth(attendancePay.getAllotTime()));
					attendancePay.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(attendancePay.getAllotTime()));
					//获取所有的工资流水
					List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, page).getRows();
					for(AttendancePay pay : attendancePayList){
						pay.setWorkPrice(attendancePay.getWorkPrice());
						attendancePayService.addAttendancePay(pay);
					}
				}
				cr.setMessage("修改成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("用户不能为空");
			}
			return cr;
		}
		
		
		@InitBinder
		protected void initBinder(WebDataBinder binder) {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
					DateTimePattern.DATEHM.getPattern());
			binder.registerCustomEditor(java.util.Date.class, null,
					new CustomDateEditor(dateTimeFormat, true));
			binder.registerCustomEditor(byte[].class,
					new ByteArrayMultipartFileEditor());
		}
		
		

}
