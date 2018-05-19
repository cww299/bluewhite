package com.bluewhite.reportexport.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.utils.excel.Excelutil;
import com.bluewhite.reportexport.entity.ProcedurePoi;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.UserPoi;
import com.bluewhite.reportexport.service.ReportExportService;

@Controller
@RequestMapping("excel")
public class ReportExportAction {
	
	private final static Log log = Log.getLog(ReportExportAction.class);
	
	@Autowired
	private ReportExportService ReportExportService;
	
	
	/**
	 * 基础产品导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importProduct",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importProduct(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<ProductPoi> excelProduct = new ArrayList<ProductPoi>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<ProductPoi> util = new Excelutil<ProductPoi>(ProductPoi.class);
				excelProduct = util.importExcel(filename, in);// 导入
				int count = ReportExportService.importProductExcel(excelProduct);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	/**
	 * 基础用户导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importUser",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importUser(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<UserPoi> excelUser = new ArrayList<UserPoi>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<UserPoi> util = new Excelutil<UserPoi>(UserPoi.class);
				excelUser = util.importExcel(filename, in);// 导入
				int count = ReportExportService.importUserExcel(excelUser);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	/**
	 * 工序导入
	 */
	@RequestMapping(value = "/importProcedure",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importProcedure(@RequestParam(value="file",required=false) MultipartFile file,Long productId,Integer type,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<ProcedurePoi> excelProcedure = new ArrayList<ProcedurePoi>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<ProcedurePoi> util = new Excelutil<ProcedurePoi>(ProcedurePoi.class);
				excelProcedure = util.importExcel(filename, in);// 导入
				int count = ReportExportService.importProcedureExcel(excelProcedure,productId,type);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	

}
