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
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.service.CommodityService;
import com.bluewhite.onlineretailers.inventory.service.OnlineCustomerService;
import com.bluewhite.onlineretailers.inventory.service.OnlineOrderService;
import com.bluewhite.onlineretailers.inventory.service.ProcurementService;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.user.entity.User;


@Controller
public class InventoryAction {
	
private static final Log log = Log.getLog(InventoryAction.class);
	
	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private OnlineCustomerService onlineCustomerService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private ProcurementService procurementService;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
	clearCascadeJSON = ClearCascadeJSON
			.get()
			.addRetainTerm(OnlineOrder.class,"id","user","sellerNick","picPath","payment"
					,"sellerRate","postFee","onlineCustomer","consignTime"
					,"receivedPayment","tid","buyerRemarks","num","payTime"
					,"endTime","status","documentNumber","allBillPreferential","trackingNumber"
					,"buyerMessage","buyerMemo","buyerFlag","sellerMemo","sellerFlag","buyerRate"
					,"warehouse","shippingType","createdAt","updatedAt","onlineOrder Child"
					,"address","phone","zipCode","buyerName","provinces","city","county")
			.addRetainTerm(OnlineOrderChild.class,"id","number","commodity","price",
					"sumPrice","systemPreferential","sellerReadjustPrices","actualSum","status")
			.addRetainTerm(User.class,"id","userName")
			.addRetainTerm(RegionAddress.class,"id","regionName","parentId");
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
		onlineOrderService.addOnlineOrder(onlineOrder);
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
		int count = onlineOrderService.deleteOnlineOrder(ids);
		cr.setMessage("成功删除"+count+"t条销售单");
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
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(commodityService.findPage(commodity,page))
				.toJSON());
		cr.setMessage("查询成功");
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
		commodityService.save(commodity);
		if(commodity.getId()!=null){
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("新增成功");
		}
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
		int count = commodityService.deleteCommodity(ids);
		cr.setMessage("成功删除"+count+"件商品");
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
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(onlineCustomerService.findPage(onlineCustomer,page))
				.toJSON());
		cr.setMessage("查询成功");
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
		onlineCustomerService.save(onlineCustomer);
		if(onlineCustomer.getId()!=null){
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("新增成功");
		}
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
		int count = onlineCustomerService.deleteOnlineCustomer(ids);
		cr.setMessage("成功删除"+count+"件商品");
		return cr;
	}
	
	
	/**** 采购  ***/
	
	/**
	 * 分页查看出库入库单
	 * @param onlineCustomer
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/inventory/procurementPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse procurementPage(Procurement procurement , PageParameter page) {
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(procurementService.findPage(procurement,page))
				.toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/** 
	 * 新增出库入库单
	 * 
	 */
	@RequestMapping(value = "/inventory/addProcurement", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addProcurement(Procurement procurement) {
		CommonResponse cr = new CommonResponse();
		procurementService.saveProcurement(procurement);
		cr.setMessage("新增成功");
		return cr;
	}
	
	
	/** 
	 * 删除出库入库单
	 * 
	 */
	@RequestMapping(value = "/inventory/deleteProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteProcurement(String ids) {
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
