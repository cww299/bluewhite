package com.bluewhite.finance.attendance.action;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.finance.entity.PayB;

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
		@RequestMapping(value = "/finance/allAttendancePay", method = RequestMethod.GET)
		@ResponseBody
		public CommonResponse allAttendancePay(HttpServletRequest request,AttendancePay attendancePay) {
			CommonResponse cr = new CommonResponse();
			//修改
			if(!StringUtils.isEmpty(attendancePay.getId())){
				AttendancePay oldAttendancePay = attendancePayService.findOne(attendancePay.getId());
				BeanCopyUtils.copyNullProperties(oldAttendancePay,attendancePay);
				attendancePay.setCreatedAt(oldAttendancePay.getCreatedAt());
				attendancePayService.addAttendancePay(attendancePay);
				cr.setMessage("修改成功");
			}else{
				//新增
				if(!StringUtils.isEmpty(attendancePay.getUserId())){
					if(attendancePay.getAllotTime() == null){
						Calendar  cal = Calendar.getInstance();
						cal.add(Calendar.DATE,-1);
						attendancePay.setAllotTime(cal.getTime());
					}
					attendancePayService.addAttendancePay(attendancePay);
					cr.setMessage("任务分配成功");
				}else{
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("考勤人员不能为空");
				}
			}
			return cr;
		}

}
