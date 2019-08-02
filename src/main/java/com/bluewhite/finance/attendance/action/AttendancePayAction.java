package com.bluewhite.finance.attendance.action;

import java.text.SimpleDateFormat;
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

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

/**
 * 财务部  考勤 
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
		
		
		
		/** 
		 * 添加考情工资(A工资)
		 * 
		 */
		@RequestMapping(value = "/finance/addAttendance", method = RequestMethod.POST)
		@ResponseBody
		@Transactional
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
						attendance.setGroupId(user.getGroupId());
						attendance.setAllotTime(ProTypeUtils.countAllotTime(attendancePay.getAllotTime(), attendancePay.getType()));
						attendance.setOrderTimeBegin(DatesUtil.getfristDayOftime(attendance.getAllotTime()));
						attendance.setOrderTimeEnd(DatesUtil.getLastDayOftime(attendance.getAllotTime()));
						attendance.setType(attendancePay.getType());
						
						if(attendancePayService.findPages(attendance, new PageParameter(0,Integer.MAX_VALUE) ).getRows().size()>0){
							cr.setMessage(user.getUserName()+"该天已存在考情记录，无需再次添加，请重新选择");
							cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
							return cr;
						}else{
							//出勤时长
							if(attendancePay.getTurnWorkTimes()!=null && attendancePay.getTurnWorkTimes().length>0){
							attendance.setTurnWorkTime(attendancePay.getTurnWorkTimes()[i]);
							}
							if(attendancePay.getOvertimes()!=null && attendancePay.getOvertimes().length>0){
								attendance.setOverTime(attendancePay.getOvertimes()[i]);
							}
							//工作时长
							attendance.setWorkTime(NumUtils.sum(attendancePay.getWorkTimes()[i],attendancePay.getOvertimes()[i]));
							attendance.setWorkPrice(user.getPrice());
							attendance.setUserName(user.getUserName());
							attendancePayService.addAttendancePay(attendance);
							cr.setMessage("考勤添加成功");
							
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
		 * 修改员工考勤工资流水（由于当月预计小时收入不精确，导致要修改上月员工的所有收入流水）
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
					User user = userService.findOne(userid);
					user.setPrice(attendancePay.getWorkPrice());
					userService.save(user);
					attendancePay.setUserId(userid);
					attendancePay.setOrderTimeBegin(DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth(attendancePay.getAllotTime())));
					attendancePay.setOrderTimeEnd(DatesUtil.getLastDayOftime(DatesUtil.getLastDayOfMonth(attendancePay.getAllotTime())));
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
		
		
		/** 
		 * 删除考勤
		 * 
		 */
		@RequestMapping(value = "/finance/deleteAttendance", method = RequestMethod.GET)
		@ResponseBody
		public CommonResponse updateAllAttendance(HttpServletRequest request,String[] ids) {
			CommonResponse cr = new CommonResponse();
			int count = 0;
			if(!StringUtils.isEmpty(ids)){
				for (int i = 0; i < ids.length; i++) {
					Long id = Long.parseLong(ids[i]);
					attendancePayService.delete(id);
					count++;
				}
			}
			cr.setMessage("成功删除"+count+"条");
			return cr;
		}
		
		
		
		@InitBinder
		protected void initBinder(WebDataBinder binder) {
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
					DateTimePattern.DATEHMS.getPattern());
			binder.registerCustomEditor(java.util.Date.class, null,
					new CustomDateEditor(dateTimeFormat, true));
			binder.registerCustomEditor(byte[].class,
					new ByteArrayMultipartFileEditor());
		}
		
		

}
