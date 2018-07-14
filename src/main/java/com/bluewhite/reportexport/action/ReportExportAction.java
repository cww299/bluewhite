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
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.GroupProduction;
import com.bluewhite.production.finance.entity.MonthlyProduction;
import com.bluewhite.production.finance.service.CollectPayService;
import com.bluewhite.production.finance.service.PayBService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.reportexport.entity.MachinistProcedurePoi;
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
	private ReportExportService reportExportService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private CollectPayService collectPayBService;
	
	@Autowired
	private PayBService payBService;
	
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
				int count = reportExportService.importProductExcel(excelProduct);
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
				int count = reportExportService.importUserExcel(excelUser);
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
				int count = reportExportService.importProcedureExcel(excelProcedure,productId,type,flag);
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
	 * 二楼机工工序导入
	 */
	@RequestMapping(value = "/importMachinistProcedure",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importMachinistProcedure(@RequestParam(value="file",required=false) MultipartFile file,Long productId,Integer type,Integer flag,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<MachinistProcedurePoi> excelProcedure = new ArrayList<MachinistProcedurePoi>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<MachinistProcedurePoi> util = new Excelutil<MachinistProcedurePoi>(MachinistProcedurePoi.class);
				excelProcedure = util.importExcel(filename, in);// 导入
				int count = reportExportService.importMachinistProcedureExcel(excelProcedure,productId,type,flag);
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
	    	reworkPoi.setPrice(tasks.getTaskPrice()/0.00621*0.003833333);
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
	
	
	/**
	 * 基础面料数据导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importMateriel",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importMateriel(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<Materiel> excelMateriel = new ArrayList<Materiel>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<Materiel> util = new Excelutil<Materiel>(Materiel.class);
				excelMateriel = util.importExcel(filename, in);// 导入
				int count = reportExportService.importMaterielExcel(excelMateriel);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	/**
	 * 基础数据1导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importBaseOne",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importBaseOne(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<BaseOne> excelBaseOne = new ArrayList<BaseOne>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<BaseOne> util = new Excelutil<BaseOne>(BaseOne.class);
				excelBaseOne = util.importExcel(filename, in);// 导入
				int count = reportExportService.importexcelBaseOneExcel(excelBaseOne);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	/**
	 * 基础数据1 时间导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importBaseOneTime",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importBaseOneTime(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<BaseOneTime> excelBaseOneTime = new ArrayList<BaseOneTime>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<BaseOneTime> util = new Excelutil<BaseOneTime>(BaseOneTime.class);
				excelBaseOneTime = util.importExcel(filename, in);// 导入
				int count = reportExportService.importexcelBaseOneTimeExcel(excelBaseOneTime);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	/**
	 * 导出包装绩效
	 * @param request
	 * @param response
	 */
	@RequestMapping("/importExcel/DownCollectPay")
	public void DownCollectPay(HttpServletResponse response,CollectPay collectPay){
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out=null;
        try {  
            out = response.getOutputStream();  
        } catch (IOException e) {  
            e.printStackTrace();
		}  
        //输出的实体与反射的实体相对应
        List<CollectPay> collectPayList = payBService.collectPay(collectPay);
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd"); 
        collectPayList.stream().forEach(CollectPay->CollectPay.setStartDate(sdf.format(collectPay.getOrderTimeBegin())));
	    Excelutil<CollectPay> util = new Excelutil<CollectPay>(CollectPay.class);
        util.exportExcel(collectPayList, "绩效报表", out);// 导出  
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
