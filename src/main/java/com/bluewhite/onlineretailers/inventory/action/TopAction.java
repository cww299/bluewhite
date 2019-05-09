package com.bluewhite.onlineretailers.inventory.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluewhite.common.Log;
import com.bluewhite.onlineretailers.inventory.service.CommodityService;
import com.bluewhite.onlineretailers.inventory.service.OnlineCustomerService;
import com.bluewhite.onlineretailers.inventory.service.OnlineOrderService;
import com.bluewhite.onlineretailers.inventory.service.ProcurementService;

@Controller
public class TopAction {
	
	private static final Log log = Log.getLog(TopAction.class);
	
	
	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private OnlineCustomerService onlineCustomerService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private ProcurementService procurementService;
	
	
	
	

	
	
	
	

}
