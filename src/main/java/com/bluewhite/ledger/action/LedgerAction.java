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
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.Mixed;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.ledger.entity.ReceivedMoney;
import com.bluewhite.ledger.entity.Sale;
import com.bluewhite.ledger.entity.SendGoods;
import com.bluewhite.ledger.service.MixedService;
import com.bluewhite.ledger.service.OrderMaterialService;
import com.bluewhite.ledger.service.OrderProcurementService;
import com.bluewhite.ledger.service.OrderService;
import com.bluewhite.ledger.service.PackingService;
import com.bluewhite.ledger.service.ReceivedMoneyService;
import com.bluewhite.ledger.service.SaleService;
import com.bluewhite.ledger.service.ScatteredOutboundService;
import com.bluewhite.ledger.service.SendGoodsService;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
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
	@Autowired
	private MixedService mixedService;
	@Autowired
	private ReceivedMoneyService receivedMoneyService;
	@Autowired
	private SaleService saleService;
	@Autowired
	private OrderMaterialService orderMaterialService;
	@Autowired
	private OrderProcurementService orderProcurementService;
	@Autowired
	private ScatteredOutboundService scatteredOutboundService;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Packing.class, "id", "number", "customer", "packingMaterials", "packingChilds",
						"packingDate", "packingMaterials", "flag", "user", "type", "warehouseTypeId", "warehouseType")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(PackingChild.class, "id", "bacthNumber", "product", "count", "sendGoodsId",
						"lastPackingChildId", "surplusNumber")
				.addRetainTerm(PackingMaterials.class, "id", "packagingMaterials", "packagingCount")
				.addRetainTerm(Product.class, "id", "name", "number").addRetainTerm(BaseData.class, "id", "name");
	}

	private ClearCascadeJSON clearCascadeJSONSale;
	{
		clearCascadeJSONSale = ClearCascadeJSON.get()
				.addRetainTerm(Sale.class, "id", "bacthNumber", "product", "price", "count", "sumPrice", "copyright",
						"newBacth", "saleNumber", "sendDate", "flag", "customer", "remark", "audit", "delivery",
						"deliveryNumber", "deliveryDate", "disputeNumber", "disputeRemark", "deliveryCollectionDate",
						"offshorePay", "acceptPay", "disputePay", "deliveryStatus", "warehouse", "warehouseType",
						"confirm", "confirmNumber")
				.addRetainTerm(BaseData.class, "id", "name").addRetainTerm(Customer.class, "id", "name", "user")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(Product.class, "id", "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSONChild;
	{
		clearCascadeJSONChild = ClearCascadeJSON.get()
				.addRetainTerm(PackingChild.class, "id", "bacthNumber", "product", "count", "sendDate", "flag",
						"customer", "remark", "warehouse", "warehouseType", "confirm", "confirmNumber",
						"warehouseTypeDelivery", "surplusNumber")
				.addRetainTerm(BaseData.class, "id", "name").addRetainTerm(Customer.class, "id", "name", "user")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(Product.class, "id", "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSONPrice;
	{
		clearCascadeJSONPrice = ClearCascadeJSON.get()
				.addRetainTerm(PackingChild.class, "id", "product", "price", "customer", "sendDate")
				.addRetainTerm(Customer.class, "id", "name").addRetainTerm(Product.class, "id", "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSONSendGoods;
	{
		clearCascadeJSONSendGoods = ClearCascadeJSON.get()
				.addRetainTerm(SendGoods.class, "id", "customer", "bacthNumber", "product", "number", "sendNumber",
						"surplusNumber", "sendDate", "orderId")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(Product.class, "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSONOrder;
	{
		clearCascadeJSONOrder = ClearCascadeJSON.get()
				.addRetainTerm(Order.class, "id", "remark", "orderDate", "customer", "bacthNumber", "product", "number",
						"price")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(Product.class, "id", "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSONMixed;
	{
		clearCascadeJSONMixed = ClearCascadeJSON.get()
				.addRetainTerm(Mixed.class, "id", "customer", "mixtTime", "mixDetailed", "mixPrice")
				.addRetainTerm(Customer.class, "id", "name");
	}

	private ClearCascadeJSON clearCascadeJSONBill;
	{
		clearCascadeJSONBill = ClearCascadeJSON.get().addRetainTerm(Bill.class, "customerName", "customerId",
				"billDate", "offshorePay", "acceptPay", "acceptPayable", "disputePay", "nonArrivalPay",
				"overpaymentPay", "arrivalPay");
	}

	private ClearCascadeJSON clearCascadeJSONReceivedMoney;
	{
		clearCascadeJSONReceivedMoney = ClearCascadeJSON.get()
				.addRetainTerm(ReceivedMoney.class, "id", "customer","receivedMoneyDate", "receivedMoney", "receivedRemark")
				.addRetainTerm(Customer.class, "id", "name");
	}
	
	private ClearCascadeJSON clearCascadeJSONOrderMaterial;
	{
		clearCascadeJSONOrderMaterial = ClearCascadeJSON.get()
				.addRetainTerm(OrderMaterial.class,"id","order", "materiel","receiveMode", "user", "unit","dosage","audit",
						"orderProcurements","state","inventoryTotal")
				.addRetainTerm(Order.class, "id", "bacthNumber","product","number","remark")
				.addRetainTerm(Materiel.class, "id", "name","number","orderProcurements","inventoryNumber")
				.addRetainTerm(OrderProcurement.class, "id", "orderProcurementNumber","placeOrderNumber","arrivalNumber",
						"placeOrderTime","expectArrivalTime","arrivalTime","customer","user","materielLocation","price","squareGram","residueNumber")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName")
				.addRetainTerm(Product.class, "id", "name");
	}
	
	private ClearCascadeJSON clearCascadeJSONOrderProcurement;
	{
		clearCascadeJSONOrderProcurement = ClearCascadeJSON.get()
				.addRetainTerm(OrderProcurement.class, "id", "orderProcurementNumber","placeOrderNumber","arrivalNumber",
						"placeOrderTime","expectArrivalTime","arrivalTime","customer","user","materielLocation","price","squareGram")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName");
	}
	

	/**
	 * 分页查看订单
	 * 
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
	 * 
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
	 * 
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
	 * （生产计划部）查看耗料订单
	 * 
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/getOrderMaterial", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOrderMaterial(PageParameter page, OrderMaterial orderMaterial) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONOrderMaterial.format(orderMaterialService.findPages(orderMaterial, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * （生产计划部）确认订单自动生成耗料表
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/confirmOrderMaterial", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse confirmOrderMaterial(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderMaterialService.confirmOrderMaterial(ids);
		cr.setMessage("成功生成" + count + "条耗料表");
		return cr;
	}

	/**
	 * （生产计划部）修改耗料表
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/updateOrderMaterial", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateOrderMaterial(OrderMaterial orderMaterial) {
		CommonResponse cr = new CommonResponse();
		orderMaterialService.updateOrderMaterial(orderMaterial);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * （生产计划部）删除耗料表
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/deleteOrderMaterial", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOrderMaterial(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderMaterialService.deleteOrderMaterial(ids);
		cr.setMessage("成功删除" + count + "条耗料");
		return cr;
	}
	
	/**
	 * （生产计划部）审核耗料表
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/auditOrderMaterial", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditOrderMaterial(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderMaterialService.auditOrderMaterial(ids);
		cr.setMessage("成功审核" + count + "条耗料表");
		return cr;
	}

	
	
	/**
	 * （采购部）查看采购订单
	 * 
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/getOrderProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOrderProcurement(PageParameter page, OrderProcurement orderProcurement) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONOrderProcurement.format(orderProcurementService.findPages(orderProcurement, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}
	
	/**
	 * （采购部）确认库存不足的面料
	 *        生成采购订单
	 *        需要自动新增物料编号
	 *        1.自动生成带克重的新物料编号
	 *        填写了平方克重 （ 面料-“花2大”119{平方克重:190克}）
	 *        2.自动生成新物料编号 （辅料-“花1大”54）
	 * @return
	 */
	@RequestMapping(value = "/ledger/confirmOrderProcurement", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse confirmOrderProcurement(OrderProcurement orderProcurement) {
		CommonResponse cr = new CommonResponse();
		orderProcurementService.saveOrderProcurement(orderProcurement);
		cr.setMessage("新增采购订单成功");
		return cr;
	}
	
	
	
	
	/**
	 * （生产计划部）删除采购单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/deleteOrderProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOrderProcurement(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderProcurementService.deleteOrderProcurement(ids);
		cr.setMessage("成功删除" + count + "条采购单");
		return cr;
	}
	
	
	/**
	 * 将已经订购的采购单面料当作库存，进行出库
	 * 冻结当前下单合同的当前耗料表对于库存的消耗
	 * （采购部）将所有已有库存的耗料表生成分散出库记录
	 *        
	 * @return
	 */
	@RequestMapping(value = "/ledger/saveScatteredOutbound", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse saveScatteredOutbound(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = scatteredOutboundService.saveScatteredOutbound(ids);
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
	 * 发货贴包单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/sendPacking", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse sendPacking(String ids, Date sendDate) {
		CommonResponse cr = new CommonResponse();
		int count = packingService.sendPacking(ids, sendDate);
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
		cr.setMessage("成功删除" + count + "条贴包子单");
		return cr;
	}

	/**
	 * 删除包装材料子单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deletePackingMaterials", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletePackingMaterials(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = packingService.deletePackingMaterials(ids);
		cr.setMessage("成功删除" + count + "条包装材料");
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
		cr.setData(clearCascadeJSONSendGoods.format(sendGoodsService.findPages(sendGoods, page)).toJSON());
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
		cr.setData(clearCascadeJSONSendGoods.format(sendGoodsService.findLists(sendGoods)).toJSON());
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
		if (sendGoods.getId() != null) {
			cr.setMessage("修改成功");
		} else {
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
	 * 分页查看销售单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/salePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse salePage(PageParameter page, Sale sale) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONSale.format(saleService.findSalePage(sale, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 修改销售单(财务填写 )
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/updateFinanceSale", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateFinanceSale(Sale sale) {
		CommonResponse cr = new CommonResponse();
		saleService.updateFinanceSale(sale);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 修改销售单(业务员填写 )
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/updateUserSale", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateUserSale(Sale sale) {
		CommonResponse cr = new CommonResponse();
		saleService.updateUserSale(sale);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 审核销售单(业务员)
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/auditUserSale", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditUserSale(String ids, Integer deliveryStatus) {
		CommonResponse cr = new CommonResponse();
		int count = saleService.auditUserSale(ids, deliveryStatus);
		cr.setMessage("成功确认" + count + "条销售单");
		return cr;
	}

	/**
	 * 根据产品和客户查找以往价格
	 * 
	 * @param page
	 * @param packingChild
	 * @return
	 */
	@RequestMapping(value = "/ledger/getSalePrice", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSalePrice(Sale sale) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONPrice.format(saleService.getSalePrice(sale)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 审核销售单（财务）
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/auditSale", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditSale(String ids, Integer audit) {
		CommonResponse cr = new CommonResponse();
		int count = saleService.auditSale(ids, audit);
		cr.setMessage("成功审核" + count + "条销售单");
		return cr;
	}

	/**
	 * 分页查看杂支
	 * 
	 * @param page
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/mixedPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse mixedPage(PageParameter page, Mixed mixed) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONMixed.format(mixedService.findPages(mixed, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 新增修改杂支
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/addMixed", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addMixed(Mixed mixed) {
		CommonResponse cr = new CommonResponse();
		if (mixed.getId() != null) {
			Mixed ot = mixedService.findOne(mixed.getId());
			mixedService.update(mixed, ot);
			cr.setMessage("修改成功");
		} else {
			mixedService.addMixed(mixed);
			cr.setMessage("新增成功");
		}
		return cr;
	}

	/**
	 * 删除杂支
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deleteMixed", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteMixed(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = mixedService.deleteMixed(ids);
		cr.setMessage("成功删除" + count + "条杂支");
		return cr;
	}

	/**
	 * 已到货款分页
	 * 
	 * @param page
	 * @param receivedMoney
	 * @return
	 */
	@RequestMapping(value = "/ledger/receivedMoneyPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse mixedList(ReceivedMoney receivedMoney, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONReceivedMoney.format(receivedMoneyService.receivedMoneyPage(receivedMoney, page))
				.toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 新增修改货款
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/addReceivedMoney", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addReceivedMoney(ReceivedMoney receivedMoney) {
		CommonResponse cr = new CommonResponse();
		if (receivedMoney.getId() != null) {
			ReceivedMoney ot = receivedMoneyService.findOne(receivedMoney.getId());
			receivedMoneyService.update(receivedMoney, ot);
			cr.setMessage("修改成功");
		} else {
			receivedMoneyService.save(receivedMoney);
			cr.setMessage("新增成功");
		}
		return cr;
	}

	/**
	 * 删除货款
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deleteReceivedMoney", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteReceivedMoney(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = receivedMoneyService.deleteReceivedMoney(ids);
		cr.setMessage("成功删除" + count + "条货款");
		return cr;
	}

	/**
	 * 汇总货款
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/collectBill", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse collectBill(Bill bill) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONBill.format(saleService.collectBill(bill)).toJSON());
		cr.setMessage("汇总成功");
		return cr;
	}

	/***************** 仓库 ************/

	/**
	 * 分页查看贴包子单
	 * 
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
	 * 查看贴包子单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/packingChildList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse packingChildList(PackingChild packingChild) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONChild.format(packingService.packingChildList(packingChild)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * 修改贴包子单 (仓管填写 )
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/updateInventoryPackingChild", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateInventoryPackingChild(PackingChild packingChild) {
		CommonResponse cr = new CommonResponse();
		packingService.updateInventoryPackingChild(packingChild);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 审核入库
	 */
	@RequestMapping(value = "/ledger/confirmPackingChild", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse confirmPackingChild(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = packingService.confirmPackingChild(ids);
		cr.setMessage("成功审核" + count + "条入库单");
		return cr;
	}

	/**
	 * 取消审核入库
	 */
	@RequestMapping(value = "/ledger/cancelConfirmPackingChild", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse cancelConfirmPackingChild(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = packingService.cancelConfirmPackingChild(ids);
		cr.setMessage("成功取消审核" + count + "条入库单");
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
