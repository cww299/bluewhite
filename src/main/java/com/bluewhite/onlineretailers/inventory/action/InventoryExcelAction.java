package com.bluewhite.onlineretailers.inventory.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;
import com.bluewhite.onlineretailers.inventory.entity.poi.OnlineOrderPoi;
import com.bluewhite.onlineretailers.inventory.entity.poi.SalesDetailPoi;
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
        		salesDetailPoi.setName(onlineOrderChild.getCommodity().getName());
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
	
	

}
