package com.bluewhite.reportexport.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.excel.Excelutil;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.personnel.attendance.service.AttendanceTimeService;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.GroupProduction;
import com.bluewhite.production.finance.entity.MonthlyProduction;
import com.bluewhite.production.finance.service.CollectPayService;
import com.bluewhite.production.finance.service.PayBService;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.reportexport.entity.EightTailorPoi;
import com.bluewhite.reportexport.entity.MachinistProcedurePoi;
import com.bluewhite.reportexport.entity.ProcedurePoi;
import com.bluewhite.reportexport.entity.ProductPoi;
import com.bluewhite.reportexport.entity.ReworkPoi;
import com.bluewhite.reportexport.entity.User2Poi;
import com.bluewhite.reportexport.entity.UserPoi;
import com.bluewhite.reportexport.service.ReportExportService;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;

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
	
	@Autowired
	private BacthService bacthService;
	
	@Autowired
	private ProcedureDao procedureDao;
	
	@Autowired
	private AttendancePayService attendancePayService;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private AttendanceTimeService attendanceTimeService;
	
	@Autowired
	private UserDao userDao;
	/**
	 * 基础产品导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importProduct",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importProduct(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request) throws Exception{
		CommonResponse cr = new CommonResponse();
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
				in.close();
		
		return cr;
	}
	
	
	/**
	 * 基础用户导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importUser",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importUser(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request) throws Exception{
		CommonResponse cr = new CommonResponse();
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
		return cr;
	}
	
	
	
	/**
	 * 基础用户导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importUserContract",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importUserContract(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request) throws Exception{
		CommonResponse cr = new CommonResponse();
				List<UserContract> excelUser = new ArrayList<UserContract>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<UserContract> util = new Excelutil<UserContract>(UserContract.class);
				excelUser = util.importExcel(filename, in);// 导入
				int count = reportExportService.importImportUserContract(excelUser);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
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
				in.close();
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
				in.close();
		} catch (Exception e) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	
	/**
	 * 八号裁剪工序导入
	 */
	@RequestMapping(value = "/importEightTailor",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importEightTailor(@RequestParam(value="file",required=false) MultipartFile file,Long productId,Integer type,Integer sign,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<EightTailorPoi> excelProcedure = new ArrayList<EightTailorPoi>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<EightTailorPoi> util = new Excelutil<EightTailorPoi>(EightTailorPoi.class);
				excelProcedure = util.importExcel(filename, in);// 导入
				int count = reportExportService.importEightTailorProcedure(excelProcedure,productId,type,sign);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
				in.close();
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
	 * @throws IOException 
	 */
	@RequestMapping("/importExcel")
	public void DownStudentExcel(HttpServletResponse response,Task task) throws IOException{
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out = response.getOutputStream();  
        //输出的实体与反射的实体相对应
        task.setFlag(1);
        task.setStatus(1);
        PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
	    List<Task> taskList  =taskService.findPages(task, page).getRows();
	    List<ReworkPoi> reworkPoiList = new ArrayList<ReworkPoi>();
	    Map<Object, List<Task>> mapTask = taskList.stream().collect(Collectors.groupingBy(Task::getBacthId,Collectors.toList()));
		for(Object ps : mapTask.keySet()){
			List<Task> psList= mapTask.get(ps);
			ReworkPoi reworkPoi = new ReworkPoi(); 
	    	reworkPoi.setBacthNumber(psList.get(0).getBacthNumber());
	    	reworkPoi.setName(psList.get(0).getProductName());
	    	reworkPoi.setRemark(psList.get(0).getBacth().getRemarks());
	    	reworkPoi.setDatetime(psList.get(0).getBacth().getStatusTime());
	    	reworkPoi.setUsername(psList.get(0).getUserNames());
	    	//去任务中最大值
	    	Optional<Task> mactask = psList.stream().max(Comparator.comparingInt(Task::getNumber));
//	    	IntSummaryStatistics summaryStatistics = psList.stream().collect(Collectors.summarizingInt(Task::getNumber));
	    	if(task.getType()==1){
	    		 reworkPoi.setRemarkTime(mactask.get().getRemark());
	    	}
		    reworkPoi.setNumber(mactask.get().getNumber());
		    reworkPoi.setTime((psList.stream().mapToDouble(Task::getTaskTime).sum())/60);
			reworkPoi.setPrice((psList.stream().mapToDouble(Task::getTaskPrice).sum())/0.00621*0.003833333);
			reworkPoi.setSumNumber(psList.get(0).getBacth().getNumber());
			reworkPoi.setReworkRate( ((double)reworkPoi.getNumber()/(double)reworkPoi.getSumNumber()));
		    reworkPoiList.add(reworkPoi);
 		}  
	    Excelutil<ReworkPoi> util = new Excelutil<ReworkPoi>(ReworkPoi.class);
        util.exportExcel(reworkPoiList, "返工价值表", out);// 导出  
        out.close();
	}
	
	
	/**
	 * 导出月产量报表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/importExcel/monthlyProduction")
	public void DownMonthlyProductionExcel(HttpServletResponse response,MonthlyProduction monthlyProduction) throws IOException{
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out = response.getOutputStream();  
        //输出的实体与反射的实体相对应
        List<MonthlyProduction> monthlyProductionList =  collectPayBService.monthlyProduction(monthlyProduction);
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd"); 
        monthlyProductionList.stream().forEach(MonthlyProduction->MonthlyProduction.setStartDate(sdf.format(MonthlyProduction.getOrderTimeBegin())));
	    Excelutil<MonthlyProduction> util = new Excelutil<MonthlyProduction>(MonthlyProduction.class);
        util.exportExcel(monthlyProductionList, "月产量报表", out);// 导出 
        out.close();
	}
	
	
	/**
	 * 导出各组产量报表
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/importExcel/groupProduction")
	public void groupProduction(HttpServletResponse response,GroupProduction groupProduction) throws IOException{
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out = response.getOutputStream();  
        //输出的实体与反射的实体相对应
        List<GroupProduction> production =  collectPayBService.groupProduction(groupProduction);	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        production.stream().forEach(GroupProduction->GroupProduction.setStartDate(sdf.format(GroupProduction.getOrderTimeBegin())));
	    Excelutil<GroupProduction> util = new Excelutil<GroupProduction>(GroupProduction.class);
        util.exportExcel(production, "月产量报表", out);// 导出  
        out.close();
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
				in.close();
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
				in.close();
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
				in.close();
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	/**
	 * 基础数据3 导入                          
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importBaseThree",method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importBaseThree(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request){
		CommonResponse cr = new CommonResponse();
		try {
				List<BaseThree> excelBaseThree = new ArrayList<BaseThree>();
				InputStream in = file.getInputStream();
				String filename = file.getOriginalFilename();
				// 创建excel工具类
				Excelutil<BaseThree> util = new Excelutil<BaseThree>(BaseThree.class);
				excelBaseThree = util.importExcel(filename, in);// 导入
				int count = reportExportService.importexcelBaseThreeExcel(excelBaseThree);
				if(count > 0){
					cr.setMessage("成功导入"+count+"条数据");
				}
				in.close();
		} catch (Exception e) {
			cr.setMessage("导入失败");
		}
		return cr;
	}
	
	
	/**
	 * 导出包装绩效
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/importExcel/DownCollectPay")
	public void DownCollectPay(HttpServletResponse response,CollectPay collectPay) throws IOException{
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out = response.getOutputStream();  
        List<CollectPay> collectPayList = payBService.collectPay(collectPay);
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd"); 
        collectPayList.stream().forEach(CollectPay->CollectPay.setStartDate(sdf.format(collectPay.getOrderTimeBegin())));
	    Excelutil<CollectPay> util = new Excelutil<CollectPay>(CollectPay.class);
        util.exportExcel(collectPayList, "绩效报表", out);// 导出  
        out.close();
	}
	
	
	/**
	 * 导出机工绩效
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/importExcel/DownMachinistCollectPay")
	public void DownMachinistCollectPay(HttpServletResponse response,CollectPay collectPay) throws IOException{
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out = response.getOutputStream();  
        //输出的实体与反射的实体相对应
        List<CollectPay> collectPayList = collectPayBService.twoPerformancePay(collectPay);
	    Excelutil<CollectPay> util = new Excelutil<CollectPay>(CollectPay.class);
        util.exportExcelTwo(collectPayList, "绩效报表", "machinist", out);// 导出  
        out.close();
	}
	

	
	/**
	 * 机工导出批次任务工序详细
	 * @author zhangliang
	 */
	@RequestMapping("/importExcel/DownBacth")
	public void DownBacth(HttpServletRequest request,HttpServletResponse response,Long id){
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=task.xlsx");
	    // 第一步，创建一个webbook，对应一个Excel文件  
        XSSFWorkbook wb = new XSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = wb.createSheet("任务报表"); 
        //设置表格默认宽度为15个字节
    	sheet.setDefaultColumnWidth(15);
        // 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        XSSFRow row = sheet.createRow(0);
        XSSFRow row2 = sheet.createRow(2);
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        XSSFCellStyle style = wb.createCellStyle();  
       
        XSSFCell cell = row.createCell(0);  
        cell.setCellValue("批次号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("产品名");  
        cell.setCellStyle(style);  
       
       Bacth bacth =  bacthService.findOne(id);		 		
	 
        // 第四步，创建单元格，并设置值  
       	row = sheet.createRow(1);  
        row.createCell(0).setCellValue(bacth.getBacthNumber());  
        row.createCell(1).setCellValue(bacth.getProduct().getName());  
       
        
        XSSFCell cell2 = row2.createCell(0);  
        cell2.setCellValue("需要完成的工序");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell(1);  
        cell2.setCellValue("未完成的数量");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell(2);  
        cell2.setCellValue("未完成的数量预计时间（分）");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell(3);  
        cell2.setCellValue("分配任务填写备注");  
        cell2.setCellStyle(style);
        
        List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(bacth.getProduct().getId(), bacth.getType(),0);
		       
    	for (int i = 0; i < procedureList.size(); i++)  
        {  
            row2 = sheet.createRow( i + 3);  
            // 第四步，创建单元格，并设置值  
            row2.createCell(0).setCellValue(procedureList.get(i).getName());  
            row2.createCell(1).setCellValue(bacth.getNumber());  
            row2.createCell(2).setCellValue(bacth.getNumber()*procedureList.get(i).getWorkingTime()/60);  
            row2.createCell(3).setCellValue("");  
        } 
    try {	
    	OutputStream outputStream=response.getOutputStream();
    	wb.write(outputStream);
    	outputStream.flush();
    	outputStream.close(); 
  		} catch (IOException e1) {
  			e1.printStackTrace();
  		}
}
	
	
	
	/**
	 * 针工导出考勤
	 * @author zhangliang
	 */
	@RequestMapping("/importExcel/DownAttendance")
	public void DownAttendance(HttpServletRequest request,HttpServletResponse response, AttendancePay attendancePay){
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=attendancePay.xlsx");
	    
	   Long size =  DatesUtil.getDaySub(attendancePay.getOrderTimeBegin(), attendancePay.getOrderTimeEnd());
	    // 声明String数组，并初始化元素（表头名称）
       //第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
	   String excelHeader0String = ""+","+""+","+""+","+"日期"; 
	   //  “0,2,0,0”  ===>  “起始行，截止行，起始列，截止列”
	   String headnum0String = "0,0,0,0" +"."+ "0,0,1,1"+"."+ "0,0,2,2"+"."+"0,0,3,3";
	   
	   //第二行表头字段，合并单元格时字段跨几列就将该字段重复几次
	   String excelHeader1String = ""+","+""+","+""+","+"星期"; 
	   //  “0,2,0,0”  ===>  “起始行，截止行，起始列，截止列”
	   String headnum1String = "1,1,0,0" +"."+ "1,1,1,1"+"."+ "1,1,2,2"+"."+"1,1,3,3";
	   
	   //第三行表头字段，合并单元格时字段跨几列就将该字段重复几次
	   String excelHeader2String = "该人员所在部"+","+"姓名"+","+"约定正常工作时间保底小时工资"+","+"约定加班小时工资"; 
	   
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	   int count = 3;
	   int sount = 3;
	   Date starTime = attendancePay.getOrderTimeBegin();
		for(int i=0 ; i<size ; i++){
			count++;
			sount++;
			Date beginTimes = null;
			if(i!=0){
				//获取下一天的时间
				beginTimes = DatesUtil.nextDay(starTime);
			}else{
				beginTimes = starTime;
			}
			 
			String week =DatesUtil.JudgeWeek(beginTimes);
			String work = "出勤,加班,缺勤";
//			(0,0,4,6) (0,0,7,9)
			String headnum = "0,0,"+count+","+(++count+1);
			String headnum1 = "1,1,"+sount+","+(++sount+1);
			excelHeader0String = excelHeader0String+","+sdf.format(beginTimes);
			headnum0String = headnum0String +"."+ headnum;
			excelHeader1String = excelHeader1String+","+week;
			headnum1String = headnum1String +"."+ headnum1;
			excelHeader2String = excelHeader2String +","+work;
			count++;
			sount++;
			starTime = beginTimes;
		}
		
		String[] excelHeader0 = null;
		String[] headnum0  = null;
		String[] excelHeader1 = null;
		String[] headnum1  = null;
		String[] excelHeader2 = null;
		if(!StringUtils.isEmpty(excelHeader0String) && !StringUtils.isEmpty(headnum0String)){
			excelHeader0 = excelHeader0String.split(",");
			headnum0 = headnum0String.split("\\.");
			excelHeader1 = excelHeader1String.split(",");
			headnum1 = headnum1String.split("\\.");
			excelHeader2 = excelHeader2String.split(",");
		}
	    
	    // 第一步，创建一个webbook，对应一个Excel文件  
        XSSFWorkbook wb = new XSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = wb.createSheet("考勤报表"); 
        sheet.setDefaultColumnWidth(10);
        // 在sheet中添加表头第0行
        XSSFRow row = sheet.createRow(0);
        int j = 0;
        for (int i = 0; i < excelHeader0.length; i++) {
        	if(i>4){
        		j=j+2;
        	}
        	int num = i>4 ? (i+j) : i;
            row.createCell(num).setCellValue(excelHeader0[i]);
        }

        // 动态合并单元格
        for (int i = 0; i < headnum0.length; i++) {
            String[] temp = headnum0[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            if (!(startrow.equals(overrow) && startcol.equals(overcol))) {  
                sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));  
           }  
        }
        
        // 第二行表头
        row = sheet.createRow(1);
        j = 0;
        for (int i = 0; i < excelHeader1.length; i++) {
        	if(i>4){
        		j=j+2;
        	}
        	int num = i>4 ? (i+j) : i;
            row.createCell(num).setCellValue(excelHeader1[i]);
        }

        // 动态合并单元格
        for (int i = 0; i < headnum1.length; i++) {
            String[] temp = headnum1[i].split(",");
            Integer startrow = Integer.parseInt(temp[0]);
            Integer overrow = Integer.parseInt(temp[1]);
            Integer startcol = Integer.parseInt(temp[2]);
            Integer overcol = Integer.parseInt(temp[3]);
            if (!(startrow.equals(overrow) && startcol.equals(overcol))) {  
                sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));  
           } 
        }
        
        
        // 第三行表头
        row = sheet.createRow(2);
        for (int i = 0; i < excelHeader2.length; i++) {
            row.createCell(i).setCellValue(excelHeader2[i]);
        }
        
        
       //填充数据
       //将针工一整个月的考勤查询出来
       List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay, new PageParameter(0,Integer.MAX_VALUE,new Sort(Sort.Direction.ASC,"allotTime"))).getRows();
       //按人员分组
       Map<Long, List<AttendancePay>> mapAttendancePay = attendancePayList.stream().collect(Collectors.groupingBy(AttendancePay::getUserId,Collectors.toList()));
		//循环出一整个月的每个人的人员考勤
       int l = 3;
		for(Object ps : mapAttendancePay.keySet()){
			List<AttendancePay> psList= mapAttendancePay.get(ps);
			row = sheet.createRow(l);
			row.createCell(0).setCellValue(psList.get(0).getUser().getGroup() !=null ? psList.get(0).getUser().getGroup().getName() : ""); 
			row.createCell(1).setCellValue(psList.get(0).getUserName()); 
			row.createCell(2).setCellValue(10); 
			row.createCell(3).setCellValue(""); 
			int k = 4; 
			for (int i = 0; i < psList.size(); i++){
            	row.createCell(k).setCellValue(psList.get(i).getWorkTime());
            	row.createCell(++k).setCellValue(psList.get(i).getOverTime()!=null ? psList.get(i).getOverTime() : 0.0);
            	row.createCell(++k).setCellValue(psList.get(i).getDutyTime()!=null ? psList.get(i).getDutyTime() : 0.0);
            	k++;
	        	}
			l++;
		}
         
    try {	
    	OutputStream outputStream=response.getOutputStream();
    	wb.write(outputStream);
    	outputStream.flush();
    	outputStream.close(); 
  		} catch (IOException e1) {
  			e1.printStackTrace();
  		}
}
	
	
	/**
	 * 人事导出打卡记录
	 * @author zhangliang
	 */
	@RequestMapping("/importExcel/personnel/DownAttendanceSign")
	public void DownAttendanceSign(HttpServletRequest request,HttpServletResponse response,Attendance attendance){
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=Attendance.xlsx");
	    // 第一步，创建一个webbook，对应一个Excel文件  
        XSSFWorkbook wb = new XSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = wb.createSheet("签到表"); 
        //设置表格默认宽度为15个字节
    	sheet.setDefaultColumnWidth(15);
        // 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        XSSFCellStyle style = wb.createCellStyle();  
        XSSFCell cell = row.createCell(0);  
        cell.setCellValue("员工编号");  
        cell.setCellStyle(style);  
        cell = row.createCell(1);  
        cell.setCellValue("员工姓名");  
        cell.setCellStyle(style);  
        cell = row.createCell(2);  
        cell.setCellValue("签到时间");  
        cell.setCellStyle(style); 
        List<Attendance> attendanceList = attendanceService.findPageAttendance(attendance,new PageParameter(0,Integer.MAX_VALUE)).getRows();
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	for (int i = 0; i < attendanceList.size(); i++)  
        {  
            row = sheet.createRow( i + 1);  
            // 第四步，创建单元格，并设置值  
            row.createCell(0).setCellValue(attendanceList.get(i).getNumber());  
            row.createCell(1).setCellValue(attendanceList.get(i).getUser().getUserName());
            row.createCell(2).setCellValue(sdf.format(attendanceList.get(i).getTime()));  
        } 
    try {	
    	OutputStream outputStream=response.getOutputStream();
    	wb.write(outputStream);
    	outputStream.flush();
    	outputStream.close(); 
  		} catch (IOException e1) {
  			e1.printStackTrace();
  		}
}
	
	
	/**
	 * 导出退休返聘
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/importExcel/retire")
	public void DownMonthlyProductionExcel(HttpServletResponse response,Date orderTimeBegin) throws IOException, ParseException{
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out = response.getOutputStream();  
        //输出的实体与反射的实体相对应
	    Calendar calendar = new GregorianCalendar();
	    Calendar calendar2 = new GregorianCalendar();
	    Date date2 = null;
	    if (orderTimeBegin!=null) {
	    	date2=orderTimeBegin;
		}else {
			 date2 = new Date();
		}
		Date date3;
		Date date4;
		calendar.setTime(date2);
		calendar2.setTime(date2);
	    calendar.add(calendar.YEAR, -50);//把日期往后增加一年.整数往后推,负数往前移动
	    calendar2.add(calendar.YEAR, -60);//把日期往后增加一年.整数往后推,负数往前移动
	    date3=calendar.getTime(); 
	    date4=calendar2.getTime(); 
	    List<User> lists = new ArrayList<>();
	    List<User> users= userDao.findAll();
	    List<User> list=users.stream().filter(User->User.getGender()!=null && User.getAge()!=null && User.getBirthDate()!=null && User.getForeigns().equals(0) && User.getGender().equals(1) && User.getAge()>=50 && User.getBirthDate().before(date3)).collect(Collectors.toList());
	    List<User> list2=users.stream().filter(User->User.getGender()!=null && User.getAge()!=null && User.getBirthDate()!=null && User.getForeigns().equals(0) && User.getGender().equals(0) && User.getAge()>=60 && User.getBirthDate().before(date4)).collect(Collectors.toList());
	    lists.addAll(list);
	    lists.addAll(list2);
	    List<UserPoi> lists2 = new ArrayList<>();
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	    for (User user : lists) {
		UserPoi poi=new UserPoi();
		poi.setName(user.getUserName());
		poi.setAge(user.getAge());
		poi.setBirthDate(fmt.format(user.getBirthDate()));
		poi.setOrgName(user.getOrgName().getName());
		poi.setGender(user.getGender()==0 ? "男" :"女");
		lists2.add(poi);
	   	}
	    Excelutil<UserPoi> util = new Excelutil<UserPoi>(UserPoi.class);
        util.exportExcel(lists2, "退休返聘", out);// 导出 
        out.close();
	}
	
	
	/**
	 * 导出身份证号码
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/importExcel/cat")
	public void DownMonthlyUserExcel(HttpServletResponse response) throws IOException{
		response.setContentType("octets/stream");
	    response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
	    OutputStream out = response.getOutputStream();  
        //输出的实体与反射的实体相对应
	    List<User> lists = new ArrayList<>();
	    List<User> users= userDao.findAll();
	    List<User> list=users.stream().filter(User->User.getIdCard()!=null && !User.getIdCard().equals("") && User.getForeigns().equals(0) && User.getIsAdmin()==false).collect(Collectors.toList());
	    lists.addAll(list);
	    List<User2Poi> lists2 = new ArrayList<>();
	    for (User user : lists) {
		User2Poi poi=new User2Poi();
		poi.setName(user.getUserName());
		if (user.getOrgName()!=null) {
			poi.setOrgName(user.getOrgName().getName());
		}
		if (user.getGender()!=null) {
			poi.setGender(user.getGender()==0 ? "男" :"女");
		}
		if (user.getQuit()!=null) {
			poi.setQuit(user.getQuit()==0 ? "在职" :"离职");
		}
		poi.setIdCard(user.getIdCard());
		if (user.getPosition()!=null) {
			poi.setPositionName(user.getPosition().getName());
		}
		lists2.add(poi);
	   	}
	    Excelutil<User2Poi> util = new Excelutil<User2Poi>(User2Poi.class);
        util.exportExcel(lists2, "身份证", out);// 导出 
        out.close();
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
