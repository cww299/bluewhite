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
import com.bluewhite.ledger.entity.MaterialOutStorage;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.entity.MaterialRequisition;
import com.bluewhite.ledger.entity.Mixed;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderChild;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.OutStorage;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.ledger.entity.ProcessPrice;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.ledger.entity.ReceivedMoney;
import com.bluewhite.ledger.entity.RefundBills;
import com.bluewhite.ledger.entity.Sale;
import com.bluewhite.ledger.entity.ScatteredOutbound;
import com.bluewhite.ledger.entity.SendGoods;
import com.bluewhite.ledger.service.MaterialOutStorageService;
import com.bluewhite.ledger.service.MaterialPutStorageService;
import com.bluewhite.ledger.service.MaterialRequisitionService;
import com.bluewhite.ledger.service.MixedService;
import com.bluewhite.ledger.service.OrderMaterialService;
import com.bluewhite.ledger.service.OrderOutSourceService;
import com.bluewhite.ledger.service.OrderProcurementService;
import com.bluewhite.ledger.service.OrderService;
import com.bluewhite.ledger.service.OutStorageService;
import com.bluewhite.ledger.service.PackingService;
import com.bluewhite.ledger.service.PutStorageService;
import com.bluewhite.ledger.service.ReceivedMoneyService;
import com.bluewhite.ledger.service.RefundBillsService;
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
	@Autowired
	private OrderOutSourceService orderOutSourceService;
	@Autowired
	private MaterialRequisitionService materialRequisitionService;
	@Autowired
	private RefundBillsService refundBillsService;
	@Autowired
	private MaterialPutStorageService materialPutStorageService;
	@Autowired
	private MaterialOutStorageService materialOutStorageService;
	@Autowired
	private PutStorageService putStorageService;
	@Autowired
	private OutStorageService outStorageService;

	private ClearCascadeJSON clearCascadeJSONOrder;
	{
		clearCascadeJSONOrder = ClearCascadeJSON.get()
				.addRetainTerm(Order.class, "id", "remark", "orderDate", "bacthNumber", "product", "number",
						"orderMaterials", "prepareEnough", "orderChilds", "audit", "orderNumber")
				.addRetainTerm(OrderMaterial.class, "id")
				.addRetainTerm(OrderChild.class, "id", "customer", "user", "childNumber", "childRemark")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName")
				.addRetainTerm(Product.class, "id", "name", "number");
	}

	private ClearCascadeJSON clearCascadeJSONPacking;
	{
		clearCascadeJSONPacking = ClearCascadeJSON.get()
				.addRetainTerm(Packing.class, "id", "number", "customer", "packingMaterials", "packingChilds",
						"packingDate", "packingMaterials", "flag", "user", "type", "warehouseTypeId", "warehouseType")
				.addRetainTerm(User.class, "id", "userName")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(PackingChild.class, "id", "bacthNumber", "product", "count", "sendGoodsId",
						"lastPackingChildId", "surplusNumber")
				.addRetainTerm(PackingMaterials.class, "id", "packagingMaterials", "packagingCount")
				.addRetainTerm(Product.class, "id", "name", "number")
				.addRetainTerm(BaseData.class, "id", "name");
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
				.addRetainTerm(Customer.class, "id", "name").addRetainTerm(Product.class, "name", "number");
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
		clearCascadeJSONReceivedMoney = ClearCascadeJSON.get().addRetainTerm(ReceivedMoney.class, "id", "customer",
				"receivedMoneyDate", "receivedMoney", "receivedRemark").addRetainTerm(Customer.class, "id", "name");
	}

	private ClearCascadeJSON clearCascadeJSONOrderMaterial;
	{
		clearCascadeJSONOrderMaterial = ClearCascadeJSON.get()
				.addRetainTerm(OrderMaterial.class, "id", "order", "materiel", "receiveMode", "user", "unit", "dosage",
						"audit", "outbound", "state", "inventoryTotal", "outAudit")
				.addRetainTerm(Order.class, "id", "bacthNumber", "product", "number", "remark", "orderNumber")
				.addRetainTerm(Materiel.class, "id", "name", "number", "orderProcurements", "inventoryNumber")
				.addRetainTerm(OrderProcurement.class, "id", "orderProcurementNumber", "placeOrderNumber",
						"arrivalNumber", "placeOrderTime", "expectArrivalTime", "arrivalTime", "customer", "user",
						"materielLocation", "price", "squareGram", "residueNumber")
				.addRetainTerm(Customer.class, "id", "name").addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(Product.class, "id", "name");
	}

	private ClearCascadeJSON clearCascadeJSONOrderProcurement;
	{
		clearCascadeJSONOrderProcurement = ClearCascadeJSON.get()
				.addRetainTerm(OrderProcurement.class, "id", "orderProcurementNumber", "placeOrderNumber",
						"arrivalNumber", "placeOrderTime", "expectArrivalTime", "arrivalTime", "customer", "user",
						"materielLocation", "price", "squareGram", "userStorage", "arrival", "audit",
						"expectPaymentTime", "materiel", "returnNumber", "partDelayNumber", "partDelayTime",
						"gramPrice", "interest", "paymentMoney", "bill", "conventionPrice", "conventionSquareGram",
						"partDelayPrice", "returnRemark", "arrivalStatus", "replenishment")
				.addRetainTerm(Materiel.class, "id", "name", "number", "materialQualitative")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName");
	}

	private ClearCascadeJSON clearCascadeJSONScatteredOutbound;
	{
		clearCascadeJSONScatteredOutbound = ClearCascadeJSON.get()
				.addRetainTerm(ScatteredOutbound.class, "id", "outboundNumber", "orderMaterial", "orderProcurement",
						"receiveUser", "user", "dosage", "remark", "audit", "auditTime", "placeOrderTime",
						"openOrderAudit", "residueDosage", "dosageNumber", "residueDosageNumber")
				.addRetainTerm(OrderProcurement.class, "id", "orderProcurementNumber")
				.addRetainTerm(OrderMaterial.class, "id", "receiveMode", "materiel")
				.addRetainTerm(Materiel.class, "id", "name", "number")
				.addRetainTerm(Order.class, "id", "bacthNumber", "number", "remark", "orderNumber")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName")
				.addRetainTerm(Customer.class, "id", "name");
	}

	private ClearCascadeJSON clearCascadeJSONSOutSource;
	{
		clearCascadeJSONSOutSource = ClearCascadeJSON.get()
				.addRetainTerm(OrderOutSource.class, "id", "fill", "fillRemark", "outSourceNumber", "order", "user",
						"customer", "remark", "gramWeight", "processNumber", "openOrderTime", "flag", "audit",
						"outsourceTask", "gramWeight", "kilogramWeight", "processingUser", "outsource")
				.addRetainTerm(Order.class, "id", "bacthNumber", "product", "number", "remark", "orderNumber")
				.addRetainTerm(Customer.class, "id", "name")
				.addRetainTerm(Product.class, "id", "name", "number")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(BaseData.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName");
	}

	private ClearCascadeJSON clearCascadeJSONMaterialRequisition;
	{
		clearCascadeJSONMaterialRequisition = ClearCascadeJSON.get()
				.addRetainTerm(MaterialRequisition.class, "id", "order", "type", "requisitionNumber",
						"scatteredOutbound", "customer", "user", "outsource", "processNumber", "dosage", "remark",
						"audit", "requisitionTime", "requisition", "flag")
				.addRetainTerm(Order.class, "id", "bacthNumber", "product", "number", "remark", "orderNumber")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName");
	}

	private ClearCascadeJSON clearCascadeJSONPutStorage;
	{
		clearCascadeJSONPutStorage = ClearCascadeJSON.get()
				.addRetainTerm(PutStorage.class, "id", "product",
				"orderOutSource", "inStatus", "inWarehouseType", "inventory", "arrivalTime", "arrivalNumber",
				"storageArea", "storageLocation", "surplusNumber", "userStorage")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName");
	}
	private ClearCascadeJSON clearCascadeJSONMaterialPutStorage;
	{
		clearCascadeJSONMaterialPutStorage = ClearCascadeJSON.get()
				.addRetainTerm(MaterialPutStorage.class, "id", "materiel",
				"orderProcurement", "inStatus", "inWarehouseType", "arrivalTime", "arrivalNumber",
				"storageArea", "storageLocation", "surplusNumber", "userStorage")
				.addRetainTerm(OrderProcurement.class, "id", "orderProcurementNumber")
				.addRetainTerm(Materiel.class, "id", "name")
				.addRetainTerm(BaseOne.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName");
	}
	private ClearCascadeJSON clearCascadeJSONMaterialOutStorage;
	{
		clearCascadeJSONMaterialOutStorage = ClearCascadeJSON.get()
				.addRetainTerm(MaterialOutStorage.class, "id", "materialPutStorage",
				"outStatus", "inStatus", "arrivalTime", "arrivalNumber");
	}

	/**
	 * 分页查看生产计划单
	 * 
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
	 * 查看生产计划单 当订单已经被销售部审核，且已经生成耗料单 1.生产计划部查看订单，有耗料单才可以查看 2.查看出库下单
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
	 * (销售部)新增生产计划单
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
	 * (销售部)修改生产计划单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/updateOrder", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateOrder(Order order) {
		CommonResponse cr = new CommonResponse();
		orderService.updateOrder(order);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * (销售部) 删除生产计划单
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
	 * (销售部) 审核生产计划单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/auditOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditOrder(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderService.auditOrder(ids);
		cr.setMessage("成功审核" + count + "订单合同");
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
		cr.setData(clearCascadeJSONOrderProcurement.format(orderProcurementService.findPages(orderProcurement, page))
				.toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * （采购部）确认库存不足的面料 生成采购订单 需要自动新增物料编号 1.自动生成带克重的新物料编号 填写了平方克重
	 * （面料-“花2大”119{平方克重:190克}） 2.自动生成新物料编号 （辅料-“花1大”54）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/confirmOrderProcurement", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse confirmOrderProcurement(OrderProcurement orderProcurement) {
		CommonResponse cr = new CommonResponse();
		if (orderProcurement.getId() != null) {
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("新增成功");
		}
		orderProcurementService.saveOrderProcurement(orderProcurement);
		return cr;

	}

	/**
	 * （采购部）审核采购单，进入面辅料仓库
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/auditOrderProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditOrderProcurement(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderProcurementService.auditOrderProcurement(ids);
		cr.setMessage("成功审核" + count + "条采购单");
		return cr;
	}

	/**
	 * （采购部）删除采购单
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
	 * （采购部）采购单出入不符预警 采购单经过面辅料仓库审核入库后，将出入库数量不相同的进行标记预警
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/warningOrderProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse warningOrderProcurement() {
		CommonResponse cr = new CommonResponse();
		cr.setData(
				clearCascadeJSONOrderProcurement.format(orderProcurementService.warningOrderProcurement(1)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * （采购部）当采购单出入不符预警 进行一键更新采购单数量
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/fixOrderProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse fixOrderProcurement(String ids) {
		CommonResponse cr = new CommonResponse();
		orderProcurementService.fixOrderProcurement(ids);
		cr.setMessage("更新成功");
		return cr;
	}

	/**
	 * （采购部）生成采购应付账单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/billOrderProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse billOrderProcurement(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderProcurementService.billOrderProcurement(ids);
		cr.setMessage("成功生成"+count+"应付账单");
		return cr;
	}

	/**
	 * （采购部）将所有已有库存的耗料表生成分散出库记录 将已经订购的采购单面料当作库存， 进行出库 冻结当前下单合同的当前耗料表对于库存的消耗
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/saveScatteredOutbound", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse outboundOrderMaterial(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = scatteredOutboundService.saveScatteredOutbound(ids);
		cr.setMessage("成功出库" + count + "条耗料单");
		return cr;
	}

	/**
	 * （采购部）审核分散出库单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/auditScatteredOutbound", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditScatteredOutbound(String ids, Date time) {
		CommonResponse cr = new CommonResponse();
		int count = scatteredOutboundService.auditScatteredOutbound(ids, time);
		cr.setMessage("成功审核" + count + "条分散出库单");
		return cr;
	}

	/**
	 * （采购部）清除分散出库单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/deleteScatteredOutbound", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteScatteredOutbound(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = scatteredOutboundService.deleteScatteredOutbound(ids);
		cr.setMessage("成功删除" + count + "条分散出库单");
		return cr;
	}

	/**
	 * （采购部）（生产计划部） 分页查看分散出库单 生产计划部查看的是审核之后的采购单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/getScatteredOutbound", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getScatteredOutbound(PageParameter page, ScatteredOutbound scatteredOutbound) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONScatteredOutbound.format(scatteredOutboundService.findPages(scatteredOutbound, page))
				.toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * (生产计划部)查看领料单 
	 * (面辅料仓库)查看出库单 --- 查看审核后的 领料单对于仓库来说是出库单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/getMaterialRequisition", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMaterialRequisition(PageParameter page, MaterialRequisition materialRequisition) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONScatteredOutbound
				.format(materialRequisitionService.findPages(page, materialRequisition)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * (生产计划部)生成领料单  1.领料单 
	 * 				   2.外发领料单 在生成领料单的时候，耗料单一定是已经审核出库的数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/saveMaterialRequisition", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveMaterialRequisition(MaterialRequisition materialRequisition) {
		CommonResponse cr = new CommonResponse();
		materialRequisitionService.saveMaterialRequisition(materialRequisition);
		cr.setMessage("成功生成领料单");
		return cr;
	}

	/**
	 * (生产计划部) 修改领料单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/updateMaterialRequisition", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateMaterialRequisition(MaterialRequisition materialRequisition) {
		CommonResponse cr = new CommonResponse();
		materialRequisitionService.updateMaterialRequisition(materialRequisition);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * (生产计划部) 删除领料单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deleteMaterialRequisition", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteMaterialRequisition(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = materialRequisitionService.deleteMaterialRequisition(ids);
		cr.setMessage("成功删除" + count + "领料单");
		return cr;
	}

	/**
	 * （生产计划部）审核领料单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/auditMaterialRequisition", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditMaterialRequisition(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = materialRequisitionService.auditMaterialRequisition(ids);
		cr.setMessage("成功审核" + count + "领料单");
		return cr;
	}

	/**
	 * （生产计划部） 分页查看加工单 （仓库）查看 入库单 --- 加工单对于仓库来说是入库单
	 * 
	 * @param page
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/orderOutSourcePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse orderOutSourcePage(PageParameter page, OrderOutSource orderOutSource) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONSOutSource.format(orderOutSourceService.findPages(orderOutSource, page)).toJSON());
		cr.setMessage("查看成功");
		return cr;
	}

	/**
	 * （生产计划部）新增加工单 1.加工单 2.外发加工单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/saveOrderOutSource", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveOrderOutSource(OrderOutSource orderOutSource) {
		CommonResponse cr = new CommonResponse();
		orderOutSourceService.saveOrderOutSource(orderOutSource);
		cr.setMessage("新增成功");
		return cr;
	}

	/**
	 * （生产计划部）判断是否可以新增加工单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/judgeOrderOutSource", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse judgeOrderOutSource(Long orderId) {
		CommonResponse cr = new CommonResponse();
		cr.setData(orderOutSourceService.judgeOrderOutSource(orderId));
		cr.setMessage("验证");
		return cr;
	}

	/**
	 * （生产计划部）修改加工单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/updateOrderOutSource", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateOrderOutSource(OrderOutSource orderOutSource) {
		CommonResponse cr = new CommonResponse();
		orderOutSourceService.updateOrderOutSource(orderOutSource);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * （生产计划部）删除加工单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/deleteOrderOutSource", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOrderOutSource(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderOutSourceService.deleteOrderOutSource(ids);
		cr.setMessage("成功删除" + count + "条加工单");
		return cr;
	}

	/**
	 * （生产计划部） 审核加工单，审核成功后，仓库可见
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/auditOrderOutSource", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse auditOrderOutSource(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = orderOutSourceService.auditOrderOutSource(ids);
		cr.setMessage("成功审核" + count + "条加工单");
		return cr;
	}

	/**
	 * （生产计划部）生成加工退货单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/saveRefundBills", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveRefundBills(RefundBills refundBills) {
		CommonResponse cr = new CommonResponse();
		refundBillsService.saveRefundBills(refundBills);
		cr.setMessage("新增成功");
		return cr;
	}

	/**
	 * （生产计划部）修改加工退货单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/updateRefundBills", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateRefundBills(RefundBills refundBills) {
		CommonResponse cr = new CommonResponse();
		refundBillsService.updateRefundBills(refundBills);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * （生产计划部）删除加工退货单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/deleteRefundBills", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteRefundBills(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = refundBillsService.deleteRefundBills(ids);
		cr.setMessage("成功删除" + count + "条");
		return cr;
	}

	/**
	 * （生产计划部）将外发加工单,退货单,加工单价格糅合，得出加工单的工序的实际任务数量和价格，进行账单的生成
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/mixOutSoureRefund", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse mixOutSoureRefund(Long id) {
		CommonResponse cr = new CommonResponse();
		cr.setData(orderOutSourceService.mixOutSoureRefund(id));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * （生产计划部）对工序价值进行新增或者修改 
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/updateProcessPrice", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateProcessPrice(ProcessPrice processPrice) {
		CommonResponse cr = new CommonResponse();
		orderOutSourceService.updateProcessPrice(processPrice);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * （生产计划部）生成外发加工单账单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/saveOutSoureBills", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveOutSoureBills(OrderOutSource orderOutSource) {
		CommonResponse cr = new CommonResponse();
		orderOutSourceService.saveOutSoureBills(orderOutSource);
		cr.setMessage("成功生成加工单账单");
		return cr;
	}
	
	
	
	

	/****************************** 库存管理  **************************/

	/**
	 * （面辅料仓库）生成物料入库单，进行入库
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/saveMaterialPutStorage", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveMaterialPutStorage(MaterialPutStorage materialPutStorage) {
		CommonResponse cr = new CommonResponse();
		materialPutStorageService.saveMaterialPutStorage(materialPutStorage);
		cr.setMessage("成功入库");
		return cr;
	}
	
	/**
	 * （面辅料仓库）入库单列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/materialPutStoragePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse materialPutStoragePage(PageParameter page,MaterialPutStorage materialPutStorage) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONMaterialPutStorage.format(materialPutStorageService.findPages(page, materialPutStorage)).toJSON());
		return cr;
	}
	
	/** 
	 * （面辅料仓库）删除入库单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/deletematerialPutStorage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletematerialPutStorage(String ids) {
		CommonResponse cr = new CommonResponse();
		int count= materialPutStorageService.deletematerialPutStorage(ids);
		cr.setMessage("成功删除"+count+"入库单");
		return cr;
	}
	
	
	/**
	 * （面辅料仓库）质检入库单，进行验货
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/inspectionMaterialPutStorage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse inspectionOrderProcurement(MaterialPutStorage materialPutStorage) {
		CommonResponse cr = new CommonResponse();
		materialPutStorageService.inspectionMaterialPutStorage(materialPutStorage);
		cr.setMessage("验货成功");
		return cr;
	}

	/**
	 * （面辅料仓库）审核采购单是否全部到货 （全部到货后，采购部才可以进行耗料分散出库）
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/arrivalOrderProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse arrivalOrderProcurement(String ids,Date time) {
		CommonResponse cr = new CommonResponse();
		int count = orderProcurementService.arrivalOrderProcurement(ids,time);
		cr.setMessage("成功审核" + count + "条采购入库单，进行入库");
		return cr;
	}


	/**
	 * （面辅料仓库）生成物料出库单   对于领料单生成(确认已被领取)
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/saveMaterialOutStorage", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveMaterialOutStorage(MaterialOutStorage materialOutStorage) {
		CommonResponse cr = new CommonResponse();
		materialOutStorageService.saveMaterialOutStorage(materialOutStorage);
		cr.setMessage("成功入库");
		return cr;
	}
	
	
	/**
	 * （面辅料仓库）物料出库单列表   
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/materialOutStoragePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse materialPutStoragePage(PageParameter page,MaterialOutStorage materialOutStorage) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONMaterialOutStorage.format(materialOutStorageService.findPages(page, materialOutStorage)).toJSON());
		return cr;	
	}
	
	/**
	 * （面辅料仓库）删除物料出库单
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/deleteMaterialOutStorage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteMaterialOutStorage(String ids) {
		CommonResponse cr = new CommonResponse();
		materialOutStorageService.deleteMaterialOutStorage(ids);
		cr.setMessage("成功删除");
		return cr;
	}
	


	/************************ （1.成品仓库，2.皮壳仓库） ********************/

	/**
	 * （1.成品仓库，2.皮壳仓库）对外发加工单收货入库
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/savePutStorage", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savePutStorage(PutStorage putStorage) {
		CommonResponse cr = new CommonResponse();
		putStorageService.savePutStorage(putStorage);
		cr.setMessage("成功入库");
		return cr;
	}

	/**
	 * （1.成品仓库，2.皮壳仓库）入库单列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/putStoragePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse putStoragePage(PageParameter page, PutStorage putStorage) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONPutStorage.format(putStorageService.findPages(page, putStorage)).toJSON());
		return cr;
	}
	
	/**
	 * （1.成品仓库，2.皮壳仓库）删除入库单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/deletePutStorage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletePutStorage(String ids) {
		CommonResponse cr = new CommonResponse();
		putStorageService.delete(ids);
		return cr;
	}
	
	
	/**
	 * （1.成品仓库，2.皮壳仓库）出库单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/saveOutStorage", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveOutStorage(OutStorage outStorage) {
		CommonResponse cr = new CommonResponse();
		outStorageService.saveOutStorage(outStorage);
		cr.setMessage("成功入库");
		return cr;
	}
	
	/**
	 * （1.成品仓库，2.皮壳仓库）入库单列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/outStoragePage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse outStoragePage(PageParameter page, OutStorage outStorage) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONPutStorage.format(outStorageService.findPages(page, outStorage)).toJSON());
		return cr;
	}
	
	
	/**
	 * （1.成品仓库，2.皮壳仓库）出库单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ledger/inventory/deleteOutStorage", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteOutStorage(String ids) {
		CommonResponse cr = new CommonResponse();
		outStorageService.deleteOutStorage(ids);
		cr.setMessage("成功删除");
		return cr;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 
	 * 查看发货单
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
	 * 通过条件查找发货单
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
	 * (销售部)新增修改发货单
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
	 * 删除发货单
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
	
	
	
	
	
	
	
	
	
	
	

	/*********************** 包装 ******************************/
	/**
	 * 分页查看贴包单
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/packingPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse packingPage(PageParameter page, Packing packing) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONPacking.format(packingService.findPages(packing, page)).toJSON());
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
