package com.bluewhite.onlineretailers.inventory.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.bluewhite.common.entity.CommonResponse;
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
	

}
