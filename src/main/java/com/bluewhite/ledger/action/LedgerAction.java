package com.bluewhite.ledger.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.ledger.entity.SendGoods;
import com.bluewhite.ledger.service.OrderService;
import com.bluewhite.ledger.service.PackingService;
import com.bluewhite.ledger.service.SendGoodsService;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

/**
 * 销售
 * 
 * @author zhangliang
 *
 */
@Controller
public class LedgerAction {

	@Autowired
	private PackingService packingService;
	@Autowired
	private SendGoodsService sendGoodsService;
	@Autowired
	private OrderService orderService;


	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Packing.class, "id", "number", "customer", "packingMaterials", "packingChilds",
						"packingDate","packingMaterials")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(PackingChild.class, "id", "bacthNumber", "product", "count","sendGoodsId")
				.addRetainTerm(PackingMaterials.class, "id", "packagingMaterials","packagingCount")
				.addRetainTerm(Product.class, "id", "name", "number")
				.addRetainTerm(BaseData.class, "id", "name");
	}
	
	private ClearCascadeJSON clearCascadeJSONChild;
	{
		clearCascadeJSONChild = ClearCascadeJSON.get()
				.addRetainTerm(PackingChild.class, "id", "bacthNumber", "product", "count"
						,"packing","price","count","sumPrice","copyright"
						,"saleNumber","sendDate","flag","customer")
				.addRetainTerm(Customer.class, "id", "name","user")
				.addRetainTerm(User.class, "id", "userName")
				.addRetainTerm(Product.class, "id", "name", "number");
	}
	
	private ClearCascadeJSON clearCascadeJSONPricce;
	{
		clearCascadeJSONPricce = ClearCascadeJSON.get()
				.addRetainTerm(PackingChild.class, "id","product","price","customer")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(Product.class, "id", "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSON1;
	{
		clearCascadeJSON1 = ClearCascadeJSON.get()
				.addRetainTerm(SendGoods.class, "id", "customer", "bacthNumber", "product", "number", "sendNumber",
						"surplusNumber","sendDate","orderId")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(Product.class, "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSONOrder;
	{
		clearCascadeJSONOrder = ClearCascadeJSON.get()
				.addRetainTerm(Order.class, "id", "remark", "orderDate", "customer","bacthNumber", "product", "number","price")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(Product.class, "id", "name", "number");
	}

	/**
	 * 分页查看订单
	 * @param page
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/orderPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse orderPage(PageParameter page, Order order) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONOrder.format(orderService.findPages(order, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 查看订单
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/getOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOrder(Order order) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONOrder.format(orderService.findList(order)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 新增订单
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/addOrder", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOrder(Order order) {
		CommonResponse cr = new CommonResponse();
		orderService.addOrder(order);
		cr.setMessage("新增成功");
		return cr;
	}

	/**
	 * 修改订单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/updateOrder", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateOrder(Order order) {
		CommonResponse cr = new CommonResponse();
		orderService.save(order);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 删除订单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deleteOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOrder(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderService.deleteOrder(ids);
		cr.setMessage("成功删除" + count + "订单合同");
		return cr;
	}

	/**
	 * 分页查看贴包单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/packingPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse packingPage(PageParameter page, Packing packing) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(packingService.findPages(packing, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 新增贴包单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/addPacking", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addPacking(Packing packing) {
		CommonResponse cr = new CommonResponse();
		packingService.addPacking(packing);
		cr.setMessage("新增成功");
		return cr;
	}

	/**
	 * 出货贴包单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/sendPacking", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse sendPacking(String ids, Date time) {
		CommonResponse cr = new CommonResponse();
		int count = packingService.sendPacking(ids, time);
		cr.setMessage("成功发货" + count + "条");
		return cr;
	}
	
	
	/**
	 * 删除贴包单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deletePacking", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletePacking(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = packingService.deletePacking(ids);
		cr.setMessage("成功删除" + count + "条贴包单");
		return cr;
	}
	
	
	/**
	 * 删除贴包子单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deletePackingChild", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletePackingChild(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = packingService.deletePackingChild(ids);
		cr.setMessage("成功删除" + count + "条贴包单");
		return cr;
	}
	
	

	/**
	 * 获取编号
	 * 
	 */
	@RequestMapping(value = "/ledger/getPackingNumber", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPackingNumber(Date sendDate) {
		CommonResponse cr = new CommonResponse();
		String packingNumber = packingService.getPackingNumber(sendDate);
		cr.setData(packingNumber);
		cr.setMessage("获取成功");
		return cr;
	}

	/**
	 * 查看待发货单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/getSendGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSendGoods(PageParameter page, SendGoods sendGoods) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON1.format(sendGoodsService.findPages(sendGoods, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 通过条件查找待发货单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/getSearchSendGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSearchSendGoods(SendGoods sendGoods) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON1.format(sendGoodsService.findLists(sendGoods)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 新增修改待发货单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/addSendGoods", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addSendGoods(SendGoods sendGoods) {
		CommonResponse cr = new CommonResponse();
		if(sendGoods.getId()!=null){
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("新增成功");
		}
		sendGoodsService.addSendGoods(sendGoods);
		return cr;
	}
	
	/**
	 * 删除待发货单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deleteSendGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteSendGoods(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = sendGoodsService.deleteSendGoods(ids);
		cr.setMessage("成功删除" + count + "待发货单");
		return cr;
	}

	
	
	/***************************** 财务 **********************************/
	
	/**
	 * 分页查看贴包子单（实际发货单）
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/packingChildPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse packingChildPage(PageParameter page, PackingChild packingChild) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONChild.format(packingService.findPackingChildPage(packingChild, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}
	
	/**
	 * 修改贴包子单（实际发货单）
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/updatePackingChild", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updatePackingChild(PackingChild packingChild) {
		CommonResponse cr = new CommonResponse();
		packingService.updatePackingChild(packingChild);
		cr.setMessage("查看成功");
		return cr;
	}
	
	
	/**
	 * 根据产品和客户查找以往价格
	 * @param page
	 * @param packingChild
	 * @return
	 */
	@RequestMapping(value = "/ledger/getPackingChildPrice", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPackingChildPrice(PageParameter page, PackingChild packingChild) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONPricce.format(packingService.getPackingChildPrice(packingChild)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}
	
	
	

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
