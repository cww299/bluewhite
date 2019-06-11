package com.bluewhite.onlineretailers.inventory.action;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.onlineretailers.inventory.entity.poi.OnlineOrderPoi;
import com.bluewhite.onlineretailers.inventory.service.OnlineOrderService;

@Controller
public class InventoryExcelAction {
	
	
	@Autowired
	private OnlineOrderService onlineOrderService;
	
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
		onlineOrderService.excelOnlineOrder(excelListener,onlineCustomerId,userId,warehouseId);
		inputStream.close();
		return cr;
	}
	
	
	/**
	 * 销售单明细(导出)
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/inventory/export/excelOnlineOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse excelOnlineOrderDetail() throws IOException {
		CommonResponse cr = new CommonResponse();
		
		
		return cr;
	}
	
	

}
