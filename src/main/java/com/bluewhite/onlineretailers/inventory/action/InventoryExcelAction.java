package com.bluewhite.onlineretailers.inventory.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.poi.OnlineOrderPoi;
import com.bluewhite.onlineretailers.inventory.entity.poi.OutProcurementPoi;
import com.bluewhite.onlineretailers.inventory.entity.poi.SalesDetailPoi;
import com.bluewhite.onlineretailers.inventory.service.OnlineOrderService;
import com.bluewhite.onlineretailers.inventory.service.ProcurementService;

@Controller
public class InventoryExcelAction {
	
	
	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private ProcurementService procurementService;
	
	/**
	 * 新增销售单(导入)
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inventory/import/excelOnlineOrder", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse excelOnlineOrder(@RequestParam(value = "file", required = false) MultipartFile file
			,Long onlineCustomerId,Long userId ,Long warehouseId) throws IOException {
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, OnlineOrderPoi.class), excelListener);
		int count = onlineOrderService.excelOnlineOrder(excelListener,onlineCustomerId,userId,warehouseId);
		inputStream.close();
		cr.setMessage("成功导入"+count+"条销售单");
		return cr;
	}
	
	
	/**
	 * 新增出库单(导入)
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inventory/import/excelOutProcurement", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse excelOutProcurement(@RequestParam(value = "file", required = false) MultipartFile file
			,Long userId ,Long warehouseId) throws IOException {
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, OutProcurementPoi.class), excelListener);
		int count = procurementService.excelProcurement(excelListener, userId, warehouseId);
		inputStream.close();
		cr.setMessage("成功导入"+count+"条出库单");
		return cr;
	}
	
	/**
	 * 销售单明细(导出)
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inventory/export/excelOnlineOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse excelOnlineOrderDetail(String ids,HttpServletResponse response) throws IOException  {
		CommonResponse cr = new CommonResponse();
	    OutputStream out = response.getOutputStream();  
	    response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=sale.xlsx");
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        Sheet sheet1 = new Sheet(1, 0, SalesDetailPoi.class);
        sheet1.setSheetName("sheet1");
        String [] idArr = ids.split(",");
        List<SalesDetailPoi> salesDetailPoiList = new ArrayList<>();
        for(String id : idArr){
        	OnlineOrder onlineOrder  = onlineOrderService.findOne(Long.valueOf(id));
        	for(OnlineOrderChild onlineOrderChild : onlineOrder.getOnlineOrderChilds()){
        		SalesDetailPoi salesDetailPoi = new SalesDetailPoi();
        		salesDetailPoi.setTime(onlineOrder.getCreatedAt());
        		salesDetailPoi.setCustomer(onlineOrder.getBuyerName());
        		salesDetailPoi.setName(onlineOrderChild.getCommodity().getSkuCode());
        		salesDetailPoi.setPrice(onlineOrderChild.getPrice());
        		salesDetailPoi.setNumber(onlineOrderChild.getNumber());
        		salesDetailPoi.setMoney(onlineOrderChild.getSumPrice());
        		salesDetailPoiList.add(salesDetailPoi);
        	}
        	
        }
        writer.write(salesDetailPoiList, sheet1);
        writer.finish();
        out.flush();
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
	
	/**
	 * 修正库存（1.根据剩余库存判断是新增出库单：当导入的数量比实际库存小，则新增出库单 
	 * 					还是新增入库单 ：当导入的数量比实际库存大，则新增入库单）
	 * 
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inventory/import/correctionInventory", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse correctionInventory(@RequestParam(value = "file", required = false) MultipartFile file
			,Long userId ,Long warehouseId) throws IOException {
		CommonResponse cr = new CommonResponse();
		InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, OutProcurementPoi.class), excelListener);
		int count = procurementService.correctionInventory(excelListener, userId, warehouseId);
		inputStream.close();
		cr.setMessage("成功导入"+count+"条出库单");
		return cr;
	}
	

}
