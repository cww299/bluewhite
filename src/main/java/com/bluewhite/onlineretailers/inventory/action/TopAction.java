package com.bluewhite.onlineretailers.inventory.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.product.param.AlibabaProcureLogisticsSyncParam;
import com.alibaba.product.param.AlibabaProductListGetParam;
import com.alibaba.product.param.AlibabaProductListGetResult;
import com.alibaba.product.param.AlibabaProductProductInfoListResult;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelTradeInfo;
import com.alibaba.trade.param.AlibabaTradeGetSellerOrderListParam;
import com.alibaba.trade.param.AlibabaTradeGetSellerOrderListResult;
import com.bluewhite.common.Constants;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
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
	
	
	
	
	/****** 订单  *****/
	
	/** 
	 * 获取销售单列表
	 * 
	 */
	@RequestMapping(value = "/getSellerOrderList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSellerOrderList(OnlineOrder onlineOrder , PageParameter page) {
		CommonResponse cr = new CommonResponse();
		//设置appkey和密钥(seckey)
		ApiExecutor apiExecutor = new ApiExecutor(Constants.ALI_APP_KEY,Constants.ALI_APP_SECRET); 
		//订单列表
		AlibabaTradeGetSellerOrderListParam param  = new AlibabaTradeGetSellerOrderListParam();
		//调用API并获取返回结果
		AlibabaTradeGetSellerOrderListResult result = apiExecutor.execute(param,"dab108b6-0e8b-4b6b-b2de-ddc4c549ebaa").getResult(); 
		AlibabaOpenplatformTradeModelTradeInfo[] list = null;
		if(result.getResult()!=null){
			list = result.getResult();
		}
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/****** 商品  *****/
	
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getProductList(OnlineOrder onlineOrder , PageParameter page) {
		CommonResponse cr = new CommonResponse();
		//设置appkey和密钥(seckey)
		ApiExecutor apiExecutor = new ApiExecutor(Constants.ALI_APP_KEY,Constants.ALI_APP_SECRET); 
		//订单列表
		AlibabaProductListGetParam  param = new AlibabaProductListGetParam();
		//调用API并获取返回结果
		AlibabaProductListGetResult result = apiExecutor.execute(param,"dab108b6-0e8b-4b6b-b2de-ddc4c549ebaa").getResult(); 
		AlibabaProductProductInfoListResult list = null;
		if(result.getResult()!=null){
			list = result.getResult();
		}
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

}
