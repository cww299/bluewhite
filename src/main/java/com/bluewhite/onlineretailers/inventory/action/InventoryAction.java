package com.bluewhite.onlineretailers.inventory.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.finance.attendance.entity.AttendancePay;
@Controller
public class InventoryAction {
	
	
	/** 
	 * 
	 * 
	 */
	@RequestMapping(value = "/inventory/test", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse test(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		
		return cr;
	}
	

	public static void main(String[] args) {
		//设置appkey和密钥(seckey)
		ApiExecutor apiExecutor = new ApiExecutor("4879538","oBhKXN1W38"); 
	}

}
