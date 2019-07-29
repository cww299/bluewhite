package com.bluewhite.ledger.action;

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

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.ledger.entity.Customr;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.SendGoods;
import com.bluewhite.ledger.service.OrderService;
import com.bluewhite.ledger.service.PackingService;
import com.bluewhite.ledger.service.SendGoodsService;
import com.bluewhite.product.product.entity.Product;

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
				.addRetainTerm(Packing.class, "number", "customr","packingMaterials","packingChilds","packingDate")
				.addRetainTerm(Customr.class, "name")
				.addRetainTerm(PackingChild.class, "bacthNumber", "product","count")
				.addRetainTerm(Product.class, "name")
				.addRetainTerm(BaseData.class, "name");
	}
	
	private ClearCascadeJSON clearCascadeJSON1;
	{
		clearCascadeJSON1 = ClearCascadeJSON.get()
				.addRetainTerm(SendGoods.class, "customr","bacthNumber","product","number")
				.addRetainTerm(Customr.class, "name")
				.addRetainTerm(Product.class, "name");
	}
	
	private ClearCascadeJSON clearCascadeJSONOrder;
	{
		clearCascadeJSONOrder = ClearCascadeJSON.get()
				.addRetainTerm(Order.class, "remark","orderDate","customr","bacthNumber","product","number","price")
				.addRetainTerm(Customr.class, "name")
				.addRetainTerm(Product.class, "name");
	}
	
	
	/**
	 * 分页查看订单
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/orderPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse orderPage(PageParameter page,Order order) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONOrder.format(orderService.findPages(order, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}
	
	/**
	 * 查看订单
	 * 
	 * @param request 请求
	 * @return cr
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
	 * 
	 * @param request 请求
	 * @return cr
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
	 * 删除订单
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deleteOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOrder(String ids) { 
		CommonResponse cr = new CommonResponse();
		int count = orderService.delete(ids);
 		cr.setMessage("成功删除"+count+"订单合同");
		return cr;
	}
	

	
	/**
	 * 分页查看贴包单
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/packingPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPacking(PageParameter page,Packing packing) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(packingService.findPages(packing, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}
	
	/**
	 * 新增贴包单
	 * 
	 * @param request 请求
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
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/sendPacking", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse sendPacking(String ids) { 
		CommonResponse cr = new CommonResponse();
		int count = packingService.sendPacking(ids);
 		cr.setMessage("成功发货"+count+"条");
		return cr;
	}
	
	
	
	/**
	 * 获取编号
	 * 
	 */
	@RequestMapping(value = "/ledger/getPackingNumber", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPackingNumber() {
		CommonResponse cr = new CommonResponse();
		String packingNumber = packingService.getPackingNumber();
		cr.setData(packingNumber);
		cr.setMessage("新增成功");
		return cr;
	}
	
	
	/**
	 * 查看待发货单
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/getSendGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSendGoods(PageParameter page,SendGoods sendGoods) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON1.format(sendGoodsService.findPages(sendGoods, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}
	
	/**
	 * 通过条件查找待发货单
	 * 
	 * @param request 请求
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
	 * 新增待发货单
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/addSendGoods", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addSendGoods(SendGoods sendGoods) { 
		CommonResponse cr = new CommonResponse();
		sendGoodsService.addSendGoods(sendGoods);
 		cr.setMessage("新增成功");
		return cr;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
	
}
