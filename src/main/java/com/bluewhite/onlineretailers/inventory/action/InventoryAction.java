package com.bluewhite.onlineretailers.inventory.action;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.onlineretailers.inventory.dao.WarningDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Delivery;
import com.bluewhite.onlineretailers.inventory.entity.DeliveryChild;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.onlineretailers.inventory.entity.OnlineCustomer;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.ProcurementChild;
import com.bluewhite.onlineretailers.inventory.entity.Warning;
import com.bluewhite.onlineretailers.inventory.entity.poi.OnlineOrderPoi;
import com.bluewhite.onlineretailers.inventory.service.CommodityService;
import com.bluewhite.onlineretailers.inventory.service.OnlineCustomerService;
import com.bluewhite.onlineretailers.inventory.service.OnlineOrderService;
import com.bluewhite.onlineretailers.inventory.service.ProcurementService;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;

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
	@Autowired
	private WarningDao warningDao;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(OnlineOrder.class, "documentNumber", "id", "user", "onlineCustomer", "onlineOrderChilds",
						"sellerNick", "name", "buyerName", "picPath", "payment", "postFee", "consignTime",
						"buyerRemarks", "num", "sumPrice", "status", "allBillPreferential",
						"buyerMessage", "buyerMemo", "buyerFlag", "sellerMemo", "sellerFlag", "buyerRate",
						"shippingType", "createdAt", "updatedAt", "address", "phone", "zipCode", "buyerName",
						"provinces", "city", "county", "flag", "telephone", "residueNumber", "deliverys")
				.addRetainTerm(OnlineOrderChild.class, "id", "number", "commodity", "price", "sumPrice",
						"systemPreferential", "sellerReadjustPrices", "actualSum", "warehouse", "status",
						"residueNumber")
				.addRetainTerm(Delivery.class, "id", "sumNumber", "trackingNumber", "deliveryChilds", "createdAt")
				.addRetainTerm(DeliveryChild.class, "id", "number", "commodity")
				.addRetainTerm(Commodity.class, "id", "skuCode").addRetainTerm(BaseData.class, "id", "name")
				.addRetainTerm(User.class, "id", "userName")
				.addRetainTerm(RegionAddress.class, "id", "regionName", "parentId");
	}

	/****** 订单 *****/

	/**
	 * 获取销售单列表
	 * 
	 */
	@RequestMapping(value = "/inventory/onlineOrderPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse onlineOrderPage(OnlineOrder onlineOrder, PageParameter page) {
		CommonResponse cr = new CommonResponse(
				clearCascadeJSON.format(onlineOrderService.findPage(onlineOrder, page)).toJSON());
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
		cr.setMessage("新增成功");
		return cr;
	}

	/**
	 * 获取上一个相同商品的订单的单价
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inventory/getOnlineOrderPrice", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOnlineOrderPrice(Long commodityId) {
		CommonResponse cr = new CommonResponse();
		double price = onlineOrderService.getOnlineOrderPrice(commodityId);
		cr.setData(price);
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 一键发货 1.将父订单的状态改变成发货状态和一个仓库时，所有子订单的发货状态和仓库改变 2.子订单部分发货和不同仓库
	 * （将销售状态改变,同时减少库存）
	 * 
	 */
	@RequestMapping(value = "/inventory/delivery", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse delivery(String delivery) {
		CommonResponse cr = new CommonResponse();
		int count = onlineOrderService.delivery(delivery);
		cr.setMessage("成功发货" + count + "销售单");
		return cr;
	}

	/**
	 * 一键反冲销售单(整单)
	 * 
	 */
	@RequestMapping(value = "/inventory/deleteOnlineOrder", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteOnlineOrder(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = onlineOrderService.deleteOnlineOrder(ids);
		cr.setMessage("成功反冲" + count + "条销售单");
		return cr;
	}

	/****** 商品 *****/
	/**
	 * 获取商品列表
	 * 
	 */
	@RequestMapping(value = "/inventory/commodityPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse commodityPage(Commodity commodity, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Commodity.class, "id", "productID", "skuCode", "fileId", "picUrl", "name", "description",
						"weight", "size", "material", "fillers", "cost", "propagandaCost", "remark", "tianmaoPrice",
						"oseePrice", "offlinePrice", "inventorys")
				.addRetainTerm(Inventory.class, "number", "place", "warehouse")
				.addRetainTerm(BaseData.class, "id", "name").format(commodityService.findPage(commodity, page))
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
		if (commodity.getId() != null) {
			Commodity ot = commodityService.findOne(commodity.getId());
			BeanCopyUtils.copyNotEmpty(commodity, ot, "");
			commodityService.save(ot);
			cr.setMessage("修改成功");
		} else {
			if (commodityService.findByName(commodity.getSkuCode()) != null) {
				cr.setMessage("该商品已存在无法新增");
			} else {
				// 同步商品名称
				commodity.setName(commodity.getSkuCode());
				commodityService.save(commodity);
				cr.setMessage("新增成功");
			}
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
		cr.setMessage("成功删除" + count + "件商品");
		return cr;
	}

	/****** 客户 *****/

	/**
	 * 获取客户列表
	 * 
	 */
	@RequestMapping(value = "/inventory/onlineCustomerPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse onlineCustomerPage(OnlineCustomer onlineCustomer, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Commodity.class, "id", "user", "name", "buyerName", "grade", "type", "provinces",
						"city", "county", "address", "phone", "account", "zipCode", "telephone")
				.addRetainTerm(User.class, "userName")
				.addRetainTerm(RegionAddress.class, "id", "regionName", "parentId")
				.format(onlineCustomerService.findPage(onlineCustomer, page)).toJSON());
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
		if (onlineCustomer.getId() != null) {
			cr.setMessage("修改成功");
		} else {
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
		cr.setMessage("成功删除" + count + "个客户");
		return cr;
	}

	/**** 采购 ***/

	/**
	 * 分页查看出库入库单
	 * 
	 * @param onlineCustomer
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/inventory/procurementPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse procurementPage(Procurement procurement, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Procurement.class, "id", "documentNumber", "user", "procurementChilds", "number",
						"residueNumber", "type", "flag", "remark", "transfersUser", "onlineCustomer", "status",
						"createdAt")
				.addRetainTerm(ProcurementChild.class, "id", "commodity", "number", "residueNumber", "warehouse",
						"status", "childRemark", "batchNumber")
				.addRetainTerm(Commodity.class, "id", "skuCode", "name", "inventorys")
				.addRetainTerm(Inventory.class, "number", "place", "warehouse")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(BaseData.class, "name")
				.format(procurementService.findPage(procurement, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增生产单
	 * 
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
	 * 一键反冲单据(整单)
	 * 
	 */
	@RequestMapping(value = "/inventory/deleteProcurement", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteProcurement(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = procurementService.deleteProcurement(ids);
		cr.setMessage("成功反冲" + count + "条单据");
		return cr;
	}

	/************** 预警设置 *************/

	/**
	 * 自动检测预警数据 (可过滤)
	 * 
	 */
	@RequestMapping(value = "/inventory/checkWarning", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse checkWarning(String skuCode) {
		CommonResponse cr = new CommonResponse();
		cr.setData(commodityService.checkWarning(skuCode));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 获取所有的库存预警
	 * 
	 */
	@RequestMapping(value = "/inventory/getWarning", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getWarning() {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Warning.class, "id", "type", "number", "time", "warehouseId", "warehouse")
				.addRetainTerm(BaseData.class, "name").format(warningDao.findAll()).toJSON());
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 新建（修改）仓库预警
	 * 
	 * 
	 */
	@RequestMapping(value = "/inventory/addWarning", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addWarning(Warning warning) {
		CommonResponse cr = new CommonResponse();
		commodityService.saveWarning(warning);
		cr.setMessage("新增成功");
		return cr;
	}

	/**
	 * 删除仓库预警
	 * 
	 * 
	 */
	@RequestMapping(value = "/inventory/deleteWarning", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteWarning(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = commodityService.deleteWarning(ids);
		cr.setMessage("成功删除" + count + "条库存预警");
		return cr;
	}

	/********************* 报表 ************************/
	/**
	 * 1.销售报表 2.入库报表
	 */

	/**
	 * 1.销售 日报表 月报表
	 * 
	 */
	@RequestMapping(value = "/inventory/report/salesDay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse sales(OnlineOrder onlineOrder) {
		CommonResponse cr = new CommonResponse();
		cr.setData(onlineOrderService.reportSales(onlineOrder));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 1.销售 商品销售报表
	 * 
	 */
	@RequestMapping(value = "/inventory/report/salesGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse salesMonth(OnlineOrder onlineOrder) {
		CommonResponse cr = new CommonResponse();
		cr.setData(onlineOrderService.reportSalesGoods(onlineOrder));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 1.销售 员工销售报表 客户销售报表
	 */
	@RequestMapping(value = "/inventory/report/salesUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse salesUser(OnlineOrder onlineOrder) {
		CommonResponse cr = new CommonResponse();
		cr.setData(onlineOrderService.reportSalesUser(onlineOrder));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 1.销售 员工销售报表 客户销售报表详细
	 */
	@RequestMapping(value = "/inventory/report/salesUserDetailed", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse salesUserDetailed(OnlineOrderChild onlineOrderChild, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(OnlineOrderChild.class, "id", "commodity", "onlineOrder", "warehouse", "createdAt",
						"number", "price", "sumPrice")
				.addRetainTerm(Commodity.class, "skuCode")
				.addRetainTerm(OnlineOrder.class, "documentNumber", "user", "onlineCustomer")
				.addRetainTerm(User.class, "userName").addRetainTerm(OnlineCustomer.class, "name", "buyerName")
				.addRetainTerm(BaseData.class, "name").format(onlineOrderService.findPage(onlineOrderChild, page))
				.toJSON());
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 2.入库 日报表 月报表
	 * 
	 */
	@RequestMapping(value = "/inventory/report/storageDay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse storageDay(Procurement procurement) {
		CommonResponse cr = new CommonResponse();
		cr.setData(procurementService.reportStorage(procurement));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 2.入库 商品入库报表
	 * 
	 */
	@RequestMapping(value = "/inventory/report/storageGoods", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse storageGoods(Procurement procurement) {
		CommonResponse cr = new CommonResponse();
		cr.setData(procurementService.reportStorageGoods(procurement));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 2.入库 员工入库报表 客户入库报表
	 * 
	 */
	@RequestMapping(value = "/inventory/report/storageUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse storageUser(Procurement procurement) {
		CommonResponse cr = new CommonResponse();
		cr.setData(procurementService.reportStorageUser(procurement));
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 库存(导入)
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inventory/import/excelInventory", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse excelInventory(@RequestParam(value = "file", required = false) MultipartFile file)
			throws IOException {
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1), excelListener);
		List<Object> objects = excelListener.getData();
		for (Object ob : objects) {
			Procurement procurement = new Procurement();
			List<Map<String, Object>> mapList = new ArrayList<>();
			List<Object> obs = (List<Object>) ob;
			Map<String, Object> map = new HashMap<>();
			map.put("batchNumber", obs.get(0));
			map.put("number", obs.get(1));
			map.put("commodityId", obs.get(2));
			map.put("warehouseId", 157);
			map.put("status", 0);
			mapList.add(map);
			// map转字符串
			procurement.setType(2);
			procurement.setStatus(0);
			procurement.setUserId((long) 770);
			procurement.setNumber(Integer.valueOf((String) obs.get(1)));
			JSONArray ja = JSONArray.parseArray(JSON.toJSONString(mapList));
			procurement.setCommodityNumber(ja.toJSONString());
			procurementService.saveProcurement(procurement);
		}
		inputStream.close();
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
