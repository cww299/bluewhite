package com.bluewhite.onlineretailers.inventory.action;

/*@Controller
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
	@Autowired
	private TopService topService;
	
	
	
	*//********* 获取各店铺的授权 ***********//*
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	*//****** 订单  *****//*
	
	*//** 
	 * 同步销售单
	 * 
	 *//*
	@RequestMapping(value = "/getSellerOrderList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSellerOrderList(PageParameter page) {
		CommonResponse cr = new CommonResponse();
		//设置appkey和密钥(seckey)
		ApiExecutor apiExecutor = new ApiExecutor(Constants.ALI_APP_KEY,Constants.ALI_APP_SECRET);
		//订单列表
		AlibabaTradeGetSellerOrderListParam param  = new AlibabaTradeGetSellerOrderListParam();
		//调用API并获取返回结果
		AlibabaTradeGetSellerOrderListResult result = apiExecutor.execute(param,topService.getAccessToken()).getResult(); 
		AlibabaOpenplatformTradeModelTradeInfo[] list = null;
		if(result.getResult()!=null){
			list = result.getResult();
			if(list.length>0){
				for(AlibabaOpenplatformTradeModelTradeInfo info : list){
					OnlineOrder onlineOrder = new OnlineOrder();
					onlineOrder.setAddress(info.getNativeLogistics().getAddress());
					
				}
			}
			cr.setData(list);
			cr.setMessage("同步成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("请重试！");
		}
		return cr;
	}
	
	
	*//****** 商品  *****//*
	
	*//**
	 * 同步商品
	 * @param onlineOrder
	 * @param page
	 * @return
	 *//*
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getProductList(PageParameter page) {
		CommonResponse cr = new CommonResponse();
		//设置appkey和密钥(seckey)
		ApiExecutor apiExecutor = new ApiExecutor(Constants.ALI_APP_KEY,Constants.ALI_APP_SECRET); 
		//商品列表
		AlibabaProductListGetParam  param = new AlibabaProductListGetParam();
		param.setPageNo(1);
		param.setPageSize(40);
		//调用API并获取返回结果
		AlibabaProductListGetResult result = apiExecutor.execute(param,topService.getAccessToken()).getResult(); 
		AlibabaProductProductInfoListResult list = null;
		if(result.getResult()!=null){
			list = result.getResult();
			Commodity Commodity = new Commodity();
		}
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}

}*/
