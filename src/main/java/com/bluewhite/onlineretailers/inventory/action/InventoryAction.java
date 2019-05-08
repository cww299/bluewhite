package com.bluewhite.onlineretailers.inventory.action;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineCustomer;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.service.OnlineOrderService;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.user.entity.User;


@Controller
public class InventoryAction {
	
private static final Log log = Log.getLog(InventoryAction.class);
	
	@Autowired
	private OnlineOrderService onlineOrderService;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
	clearCascadeJSON = ClearCascadeJSON
			.get()
			.addRetainTerm(OnlineOrder.class,"id","user","sellerNick","picPath","payment"
					,"sellerRate","postFee","onlineCustomer","consignTime"
					,"receivedPayment","tid","buyerRemarks","num","payTime"
					,"endTime","status","documentNumber","commoditys","allBillPreferential","trackingNumber"
					,"buyerMessage","buyerMemo","buyerFlag","sellerMemo","sellerFlag","buyerRate","systemPreferential"
					,"sellerReadjustPrices","actualSum","warehouse","shippingType")
			.addRetainTerm(User.class,"id","userName")
			.addRetainTerm(OnlineCustomer.class,"id","name","addressee","grade","type",
					"address","phone","account","zipCode","buyerName","provinces","city","county")
			.addRetainTerm(RegionAddress.class,"id","regionName","parentId")
			.addRetainTerm(Commodity.class,"id","number","name","weight","size",
					"material","fillers","cost","propagandaCost","remark","remark","price","quantity",
					"warehouse");
	}
	
	
	
	/****** 订单  *****/
	
	/** 
	 * 获取销售单列表
	 * 
	 */
	@RequestMapping(value = "/inventory/onlineOrderPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse onlineOrderPage(OnlineOrder onlineOrder , PageParameter page) {
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(onlineOrderService.findPage(onlineOrder,page))
				.toJSON());
		cr.setMessage("查询成功");
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
		onlineOrderService.save(onlineOrder);
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
		onlineOrderService.deleteOnlineOrder(ids);
		return cr;
	}
	
	
	/****** 商品  *****/
	/** 
	 * 获取商品列表
	 * 
	 */
	@RequestMapping(value = "/inventory/commodityPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse commodityPage(Commodity commodity , PageParameter page) {
		CommonResponse cr = new CommonResponse();
		
		
		
		return cr;
	}
	
	/** 
	 * 新增商品
	 * 
	 */
	@RequestMapping(value = "/inventory/addCommodity", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addCommodity(Commodity commodity) {
		CommonResponse cr = new CommonResponse();
		
		
		
		return cr;
	}
	
	
	/** 
	 * 删除商品
	 * 
	 */
	@RequestMapping(value = "/inventory/deleteCommodity", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteCommodity(String ids) {
		CommonResponse cr = new CommonResponse();
		
		return cr;
	}
	
	
	
	
	/****** 客户  *****/
	
	/** 
	 * 获取客户列表
	 * 
	 */
	@RequestMapping(value = "/inventory/onlineCustomerPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse onlineCustomerPage(OnlineCustomer onlineCustomer , PageParameter page) {
		CommonResponse cr = new CommonResponse();
		
		
		
		return cr;
	}
	
	/** 
	 * 新增客户
	 * 
	 */
	@RequestMapping(value = "/inventory/addOnlineCustomer", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOnlineCustomer(OnlineCustomer onlineCustomer) {
		CommonResponse cr = new CommonResponse();
		
		
		
		return cr;
	}
	
	
	/** 
	 * 删除客户
	 * 
	 */
	@RequestMapping(value = "/inventory/deleteOnlineCustomer", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOnlineCustomer(String ids) {
		CommonResponse cr = new CommonResponse();
		
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
