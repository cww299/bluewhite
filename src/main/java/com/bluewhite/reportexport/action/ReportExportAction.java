package com.bluewhite.reportexport.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.excel.Excelutil;
import com.bluewhite.production.finance.entity.GroupProduction;
import com.bluewhite.production.finance.entity.MonthlyProduction;
import com.bluewhite.production.finance.service.CollectPayService;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.reportexport.entity.ProcedurePoi;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.ReworkPoi;
import com.bluewhite.reportexport.entity.UserPoi;
import com.bluewhite.reportexport.service.ReportExportService;

@Controller
@RequestMapping("excel")
public class ReportExportAction {
	
	private final static Log log = Log.getLog(ReportExportAction.class);
	
	@Autowired
	private ReportExportService ReportExportService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CollectPayService collectPayBService;
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
	public CommonResponse importProcedure(@RequestParam(value="file",required=false) MultipartFile file,Long productId,Integer type,Integer flag,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<ProcedurePoi> excelProcedure = new ArrayList<ProcedurePoi>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<ProcedurePoi> util = new Excelutil<ProcedurePoi>(ProcedurePoi.class);
				excelProcedure = util.importExcel(filename, in);// 导入
				int count = ReportExportService.importProcedureExcel(excelProcedure,productId,type,flag);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
		} catch (Exception e) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	/**
	 * 导出返工价值
	 * @param request
	 * @param response
	 */
	@RequestMapping("/importExcel")
	public void DownStudentExcel(HttpServletResponse response,Task task){
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out=null;
        try {  
            out = response.getOutputStream();  
        } catch (IOException e) {  
            e.printStackTrace();  
		}  
        //输出的实体与反射的实体相对应
        task.setType(3);
        task.setFlag(1);
        PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
	    List<Task> taskList  =taskService.findPages(task, page).getRows();
	    List<ReworkPoi> reworkPoiList = new ArrayList<ReworkPoi>();
	    for(Task tasks : taskList){
	    	ReworkPoi reworkPoi = new ReworkPoi(); 
	    	reworkPoi.setBacthNumber(tasks.getBacthNumber());
	    	reworkPoi.setName(tasks.getProductName());
	    	reworkPoi.setNumber(tasks.getNumber());
	    	reworkPoi.setPrice(tasks.getTaskPrice());
	    	reworkPoi.setRemark(tasks.getRemark());
	    	reworkPoiList.add(reworkPoi);
	    }
	    Excelutil<ReworkPoi> util = new Excelutil<ReworkPoi>(ReworkPoi.class);
        util.exportExcel(reworkPoiList, "返工价值表", out);// 导出  
	}
	
	
	/**
	 * 导出月产量报表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/importExcel/monthlyProduction")
	public void DownMonthlyProductionExcel(HttpServletResponse response,MonthlyProduction monthlyProduction){
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out=null;
        try {  
            out = response.getOutputStream();  
        } catch (IOException e) {  
            e.printStackTrace();  
		}  
        //输出的实体与反射的实体相对应
        List<MonthlyProduction> monthlyProductionList =  collectPayBService.monthlyProduction(monthlyProduction);
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd"); 
        monthlyProductionList.stream().forEach(MonthlyProduction->MonthlyProduction.setStartDate(sdf.format(MonthlyProduction.getOrderTimeBegin())));
	    Excelutil<MonthlyProduction> util = new Excelutil<MonthlyProduction>(MonthlyProduction.class);
        util.exportExcel(monthlyProductionList, "月产量报表", out);// 导出  
	}
	
	
	/**
	 * 导出各组产量报表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/importExcel/groupProduction")
	public void groupProduction(HttpServletResponse response,GroupProduction groupProduction){
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out=null;
        try {  
            out = response.getOutputStream();  
        } catch (IOException e) {  
            e.printStackTrace();  
		}  
        //输出的实体与反射的实体相对应
        List<GroupProduction> production =  collectPayBService.groupProduction(groupProduction);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        production.stream().forEach(GroupProduction->GroupProduction.setStartDate(sdf.format(GroupProduction.getOrderTimeBegin())));
	    Excelutil<GroupProduction> util = new Excelutil<GroupProduction>(GroupProduction.class);
        util.exportExcel(production, "月产量报表", out);// 导出  
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
