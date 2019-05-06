package com.bluewhite.onlineretailers.inventory.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.service.OnlineOrderService;


@Controller
public class InventoryAction {
	
private static final Log log = Log.getLog(InventoryAction.class);
	
	@Autowired
	private OnlineOrderService onlineOrderService;
	
	
	
	/****** 订单  *****/
	
	/** 
	 * 获取销售单列表
	 * 
	 */
	@RequestMapping(value = "/inventory/onlineOrderPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse onlineOrderPage() {
		CommonResponse cr = new CommonResponse();
		
		
		
		return cr;
	}
	
	
	/** 
	 * 新增销售单
	 * 
	 */
	@RequestMapping(value = "/inventory/addOnlineOrder", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOnlineOrder(OnlineOrder onlineOrder) {
		CommonResponse cr = new CommonResponse();
		
		return cr;
	}
	
	
	/** 
	 * 删除销售单
	 * 
	 */
	@RequestMapping(value = "/inventory/deleteOnlineOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOnlineOrder(String ids) {
		CommonResponse cr = new CommonResponse();
		
		return cr;
	}
	

}
