package com.bluewhite.reportexport.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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

import com.alibaba.excel.EasyExcel;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.excel.Excelutil;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.personnel.attendance.service.AttendanceTimeService;
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
import com.bluewhite.reportexport.service.ReportExportService;

import cn.hutool.core.date.DateUtil;

@Controller
@RequestMapping("excel")
public class ReportExportAction {

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
	private BaseDataService baseDataService;

	/**
	 * 基础产品导入
	 * 
	 * @param residentmessage
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importProduct", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importProduct(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) throws Exception {
		CommonResponse cr = new CommonResponse();
		List<ProductPoi> excelProduct = new ArrayList<ProductPoi>();
		InputStream in = file.getInputStream();
		String filename = file.getOriginalFilename();
		// 创建excel工具类
		Excelutil<ProductPoi> util = new Excelutil<ProductPoi>(ProductPoi.class);
		excelProduct = util.importExcel(filename, in);// 导入
		int count = reportExportService.importProductExcel(excelProduct);
		if (count > 0) {
			cr.setMessage("成功导入" + count + "条数据");
		}
		in.close();

		return cr;
	}

	/**
	 * 工序导入
	 */
	@RequestMapping(value = "/importProcedure", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importProcedure(@RequestParam(value = "file", required = false) MultipartFile file,
			Long productId, Integer type, Integer flag, HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		try {
			List<ProcedurePoi> excelProcedure = new ArrayList<ProcedurePoi>();
			InputStream in = file.getInputStream();
			String filename = file.getOriginalFilename();
			// 创建excel工具类
			Excelutil<ProcedurePoi> util = new Excelutil<ProcedurePoi>(ProcedurePoi.class);
			excelProcedure = util.importExcel(filename, in);// 导入
			int count = reportExportService.importProcedureExcel(excelProcedure, productId, type, flag);
			if (count > 0) {
				cr.setMessage("成功导入" + count + "条数据");
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
	@RequestMapping(value = "/importMachinistProcedure", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importMachinistProcedure(@RequestParam(value = "file", required = false) MultipartFile file,
			Long productId, Integer type, Integer flag, HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		try {
			List<MachinistProcedurePoi> excelProcedure = new ArrayList<MachinistProcedurePoi>();
			InputStream in = file.getInputStream();
			String filename = file.getOriginalFilename();
			// 创建excel工具类
			Excelutil<MachinistProcedurePoi> util = new Excelutil<MachinistProcedurePoi>(MachinistProcedurePoi.class);
			excelProcedure = util.importExcel(filename, in);// 导入
			int count = reportExportService.importMachinistProcedureExcel(excelProcedure, productId, type, flag);
			if (count > 0) {
				cr.setMessage("成功导入" + count + "条数据");
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
	@RequestMapping(value = "/importEightTailor", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importEightTailor(@RequestParam(value = "file", required = false) MultipartFile file,
			Long productId, Integer type, Integer sign, HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		try {
			List<EightTailorPoi> excelProcedure = new ArrayList<EightTailorPoi>();
			InputStream in = file.getInputStream();
			String filename = file.getOriginalFilename();
			// 创建excel工具类
			Excelutil<EightTailorPoi> util = new Excelutil<EightTailorPoi>(EightTailorPoi.class);
			excelProcedure = util.importExcel(filename, in);// 导入
			int count = reportExportService.importEightTailorProcedure(excelProcedure, productId, type, sign);
			if (count > 0) {
				cr.setMessage("成功导入" + count + "条数据");
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
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/importExcel")
	public void DownStudentExcel(HttpServletResponse response, Task task) throws IOException {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
		OutputStream out = response.getOutputStream();
		// 输出的实体与反射的实体相对应
		task.setFlag(1);
		task.setStatus(1);
		PageParameter page = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		List<Task> taskList = taskService.findPages(task, page).getRows();
		List<ReworkPoi> reworkPoiList = new ArrayList<ReworkPoi>();
		Map<Object, List<Task>> mapTask = taskList.stream()
				.collect(Collectors.groupingBy(Task::getBacthId, Collectors.toList()));
		for (Object ps : mapTask.keySet()) {
			List<Task> psList = mapTask.get(ps);
			ReworkPoi reworkPoi = new ReworkPoi();
			reworkPoi.setBacthNumber(psList.get(0).getBacthNumber());
			reworkPoi.setName(psList.get(0).getProductName());
			reworkPoi.setRemark(psList.get(0).getBacth().getRemarks());
			reworkPoi.setDatetime(psList.get(0).getBacth().getStatusTime());
			// 去任务中最大值
			Optional<Task> mactask = psList.stream().max(Comparator.comparingInt(Task::getNumber));
			if (task.getType() == 1) {
				reworkPoi.setRemarkTime(mactask.get().getRemark());
			}
			reworkPoi.setNumber(mactask.get().getNumber());
			reworkPoi.setTime((psList.stream().mapToDouble(Task::getTaskTime).sum()) / 60);
			reworkPoi.setPrice((psList.stream().mapToDouble(Task::getTaskPrice).sum()) / 0.00621 * 0.003833333);
			reworkPoi.setSumNumber(psList.get(0).getBacth().getNumber());
			reworkPoi.setReworkRate(((double) reworkPoi.getNumber() / (double) reworkPoi.getSumNumber()));
			reworkPoiList.add(reworkPoi);
		}
		Excelutil<ReworkPoi> util = new Excelutil<ReworkPoi>(ReworkPoi.class);
		util.exportExcel(reworkPoiList, "返工价值表", out);// 导出
		out.close();
	}

	/**
	 * 导出月产量报表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/importExcel/monthlyProduction")
	public void DownMonthlyProductionExcel(HttpServletResponse response, MonthlyProduction monthlyProduction)
			throws IOException {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
		OutputStream out = response.getOutputStream();
		// 输出的实体与反射的实体相对应
		List<MonthlyProduction> monthlyProductionList = collectPayBService.monthlyProduction(monthlyProduction);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		monthlyProductionList.stream().forEach(
				MonthlyProduction -> MonthlyProduction.setStartDate(sdf.format(MonthlyProduction.getOrderTimeBegin())));
		Excelutil<MonthlyProduction> util = new Excelutil<MonthlyProduction>(MonthlyProduction.class);
		util.exportExcel(monthlyProductionList, "月产量报表", out);// 导出
		out.close();
	}

	/**
	 * 导出检验组各组产量报表
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/importExcel/groupProduction")
	public void groupProduction(HttpServletResponse response, GroupProduction groupProduction) throws IOException {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
		OutputStream out = response.getOutputStream();
		// 输出的实体与反射的实体相对应
		List<GroupProduction> production = collectPayBService.groupProduction(groupProduction);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		production.stream().forEach(
				GroupProduction -> GroupProduction.setStartDate(sdf.format(GroupProduction.getOrderTimeBegin())));
		Excelutil<GroupProduction> util = new Excelutil<GroupProduction>(GroupProduction.class);
		util.exportExcel(production, "月产量报表", out);// 导出
		out.close();
	}


	/**
	 * 导出包装绩效
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/importExcel/DownCollectPay")
	public void DownCollectPay(HttpServletResponse response, CollectPay collectPay) throws IOException {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
		OutputStream out = response.getOutputStream();
		List<CollectPay> collectPayList = payBService.collectPay(collectPay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		collectPayList.stream()
				.forEach(CollectPay -> CollectPay.setStartDate(sdf.format(collectPay.getOrderTimeBegin())));
		Excelutil<CollectPay> util = new Excelutil<CollectPay>(CollectPay.class);
		util.exportExcel(collectPayList, "绩效报表", out);// 导出
		out.close();
	}

	/**
	 * 导出机工绩效
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/importExcel/DownMachinistCollectPay")
	public void DownMachinistCollectPay(HttpServletResponse response, CollectPay collectPay) throws IOException {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=rework.xls");
		OutputStream out = response.getOutputStream();
		// 输出的实体与反射的实体相对应
		List<CollectPay> collectPayList = collectPayBService.twoPerformancePay(collectPay);
		Excelutil<CollectPay> util = new Excelutil<CollectPay>(CollectPay.class);
		util.exportExcelTwo(collectPayList, "绩效报表", "machinist", out);// 导出
		out.close();
	}

	/**
	 * 机工导出批次任务工序详细
	 * 
	 * @author zhangliang
	 */
	@RequestMapping("/importExcel/DownBacth")
	public void DownBacth(HttpServletRequest request, HttpServletResponse response, Long id) {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=task.xlsx");
		try {
			OutputStream outputStream = response.getOutputStream();
			// 第一步，创建一个webbook，对应一个Excel文件
			XSSFWorkbook wb = new XSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			XSSFSheet sheet = wb.createSheet("任务报表");
			// 设置表格默认宽度为15个字节
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

			Bacth bacth = bacthService.findOne(id);

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

			List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(bacth.getProduct().getId(),
					bacth.getType(), 0);

			for (int i = 0; i < procedureList.size(); i++) {
				row2 = sheet.createRow(i + 3);
				// 第四步，创建单元格，并设置值
				row2.createCell(0).setCellValue(procedureList.get(i).getName());
				row2.createCell(1).setCellValue(bacth.getNumber());
				row2.createCell(2).setCellValue(bacth.getNumber() * procedureList.get(i).getWorkingTime() / 60);
				row2.createCell(3).setCellValue("");
			}

			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 人事导出打卡记录
	 * 
	 * @author zhangliang
	 */
	@RequestMapping("/importExcel/personnel/DownAttendanceSign")
	public void DownAttendanceSign(HttpServletRequest request, HttpServletResponse response, Attendance attendance) {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=Attendance.xlsx");
		try {
			OutputStream outputStream = response.getOutputStream();
			// 第一步，创建一个webbook，对应一个Excel文件
			XSSFWorkbook wb = new XSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			XSSFSheet sheet = wb.createSheet("签到表");
			// 设置表格默认宽度为15个字节
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
			List<Attendance> attendanceList = attendanceService
					.findPageAttendance(attendance, new PageParameter(0, Integer.MAX_VALUE)).getRows();
			for (int i = 0; i < attendanceList.size(); i++) {
				row = sheet.createRow(i + 1);
				// 第四步，创建单元格，并设置值
				row.createCell(0).setCellValue(attendanceList.get(i).getNumber());
				row.createCell(1).setCellValue(
						attendanceList.get(i).getUser() != null ? attendanceList.get(i).getUser().getUserName() : "");
				row.createCell(2).setCellValue(DateUtil.formatDateTime(attendanceList.get(i).getTime()));
			}
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 针工导出考勤
	 * 
	 * @author zhangliang
	 */
	@RequestMapping("/importExcel/DownAttendance")
	public void DownAttendance(HttpServletRequest request, HttpServletResponse response, AttendancePay attendancePay) {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=attendancePay.xlsx");

		Long size = DatesUtil.getDaySub(attendancePay.getOrderTimeBegin(), attendancePay.getOrderTimeEnd());
		// 声明String数组，并初始化元素（表头名称）
		// 第一行表头字段，合并单元格时字段跨几列就将该字段重复几次
		String excelHeader0String = "" + "," + "" + "," + "" + "," + "日期";
		// “0,2,0,0” ===> “起始行，截止行，起始列，截止列”
		String headnum0String = "0,0,0,0" + "." + "0,0,1,1" + "." + "0,0,2,2" + "." + "0,0,3,3";

		// 第二行表头字段，合并单元格时字段跨几列就将该字段重复几次
		String excelHeader1String = "" + "," + "" + "," + "" + "," + "星期";
		// “0,2,0,0” ===> “起始行，截止行，起始列，截止列”
		String headnum1String = "1,1,0,0" + "." + "1,1,1,1" + "." + "1,1,2,2" + "." + "1,1,3,3";

		// 第三行表头字段，合并单元格时字段跨几列就将该字段重复几次
		String excelHeader2String = "该人员所在部" + "," + "姓名" + "," + "约定正常工作时间保底小时工资" + "," + "约定加班小时工资";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 3;
		int sount = 3;
		Date starTime = attendancePay.getOrderTimeBegin();
		for (int i = 0; i < size; i++) {
			count++;
			sount++;
			Date beginTimes = null;
			if (i != 0) {
				// 获取下一天的时间
				beginTimes = DatesUtil.nextDay(starTime);
			} else {
				beginTimes = starTime;
			}

			String week = DatesUtil.JudgeWeek(beginTimes);
			String work = "出勤,加班,缺勤";
			// (0,0,4,6) (0,0,7,9)
			String headnum = "0,0," + count + "," + (++count + 1);
			String headnum1 = "1,1," + sount + "," + (++sount + 1);
			excelHeader0String = excelHeader0String + "," + sdf.format(beginTimes);
			headnum0String = headnum0String + "." + headnum;
			excelHeader1String = excelHeader1String + "," + week;
			headnum1String = headnum1String + "." + headnum1;
			excelHeader2String = excelHeader2String + "," + work;
			count++;
			sount++;
			starTime = beginTimes;
		}

		String[] excelHeader0 = null;
		String[] headnum0 = null;
		String[] excelHeader1 = null;
		String[] headnum1 = null;
		String[] excelHeader2 = null;
		if (!StringUtils.isEmpty(excelHeader0String) && !StringUtils.isEmpty(headnum0String)) {
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
			if (i > 4) {
				j = j + 2;
			}
			int num = i > 4 ? (i + j) : i;
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
			if (i > 4) {
				j = j + 2;
			}
			int num = i > 4 ? (i + j) : i;
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

		// 填充数据
		// 将针工一整个月的考勤查询出来
		List<AttendancePay> attendancePayList = attendancePayService.findPages(attendancePay,
				new PageParameter(0, Integer.MAX_VALUE, new Sort(Sort.Direction.ASC, "allotTime"))).getRows();
		// 按人员分组
		Map<Long, List<AttendancePay>> mapAttendancePay = attendancePayList.stream()
				.collect(Collectors.groupingBy(AttendancePay::getUserId, Collectors.toList()));
		// 循环出一整个月的每个人的人员考勤
		int l = 3;
		for (Object ps : mapAttendancePay.keySet()) {
			List<AttendancePay> psList = mapAttendancePay.get(ps);
			row = sheet.createRow(l);
			row.createCell(0).setCellValue(
					psList.get(0).getUser().getGroup() != null ? psList.get(0).getUser().getGroup().getName() : "");
			row.createCell(1).setCellValue(psList.get(0).getUserName());
			row.createCell(2).setCellValue(10);
			row.createCell(3).setCellValue("");
			int k = 4;
			for (int i = 0; i < psList.size(); i++) {
				row.createCell(k).setCellValue(psList.get(i).getWorkTime());
				row.createCell(++k)
						.setCellValue(psList.get(i).getOverTime() != null ? psList.get(i).getOverTime() : 0.0);
				k++;
			}
			l++;
		}

		try {
			OutputStream outputStream = response.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}



	/**
	 * 导出考勤汇总
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/importExcel/downAttendance")
	public void downAttendance(HttpServletResponse response, AttendanceTime attendanceTime) throws IOException{
		CommonResponse cr = new CommonResponse();
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment;filename=attendance.xlsx");
		List<Map<String, Object>> listmap = attendanceTimeService.findAttendanceTimeCollectList(attendanceTime);
		BaseData baseData = baseDataService.findOne(attendanceTime.getOrgNameId());
		EasyExcel.write(response.getOutputStream())
				// 这里放入动态头
				.head(head(listmap)).sheet(baseData.getName())
				// table的时候 传入class 并且设置needHead =false
				.table().needHead(Boolean.FALSE).doWrite(data(listmap));
	}
	
	/**
	 * 写入
	 * @param listmap
	 * @return
	 */
	private List<List<String>> head(List<Map<String, Object>> listmap) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<List<String>> list = new ArrayList<List<String>>();
		if (listmap.size() == 0) {
			List<String> err = new ArrayList<>();
			err.add("请先统计考勤，再导出");
			list.add(err);
			return list;
		}
		List<AttendanceTime> attendanceTimeList = (List<AttendanceTime>) listmap.get(0).get("attendanceTimeData");
		Date date = attendanceTimeList.get(0).getTime();
		List<Date> dateList = DatesUtil.getPerDaysByStartAndEndDate(sdf.format(DatesUtil.getFirstDayOfMonth(date)),
				sdf.format(DatesUtil.getLastDayOfMonth(date)), "yyyy-MM-dd");
		List<String> headName = new ArrayList<String>();
		headName.add("姓名");
		headName.add("姓名");
		headName.add("姓名");
		list.add(headName);
		for (int i = 0; i < dateList.size(); i++) {
			List<String> head = new ArrayList<String>();
			List<String> head1 = new ArrayList<String>();
			List<String> head2 = new ArrayList<String>();
			Date da = dateList.get(i);
			head.add(sdf.format(da));
			head.add(DatesUtil.JudgeWeek(da));
			head.add("出勤");
			head1.add(sdf.format(da));
			head1.add(DatesUtil.JudgeWeek(da));
			head1.add("加班");
			head2.add(sdf.format(da));
			head2.add(DatesUtil.JudgeWeek(da));
			head2.add("缺勤");
			list.add(head);
			list.add(head1);
			list.add(head2);
		}
		List<String> headTrun = new ArrayList<String>();
		headTrun.add("出勤");
		headTrun.add("出勤");
		headTrun.add("出勤");
		List<String> headOver = new ArrayList<String>();
		headOver.add("加班");
		headOver.add("加班");
		headOver.add("加班");
		List<String> headDuty = new ArrayList<String>();
		headDuty.add("缺勤");
		headDuty.add("缺勤");
		headDuty.add("缺勤");
		List<String> headTake = new ArrayList<String>();
		headTake.add("调休");
		headTake.add("调休");
		headTake.add("调休");
		List<String> headSum = new ArrayList<String>();
		headSum.add("总出勤");
		headSum.add("总出勤");
		headSum.add("总出勤");
		list.add(headTrun);
		list.add(headOver);
		list.add(headDuty);
		list.add(headTake);
		list.add(headSum);
		return list;
	}

	/**
	 * 数据
	 * @param listmap
	 * @return
	 */
	private List<List<Object>> data(List<Map<String, Object>> listmap) {
		List<List<Object>> list = new ArrayList<>();
		if (listmap.size() != 0) {
			for (Map<String, Object> map : listmap) {
				List<AttendanceTime> attendanceTimeList = (List<AttendanceTime>) map.get("attendanceTimeData");
				AttendanceCollect collect = (AttendanceCollect) map.get("collect");
				List<Object> bList = new ArrayList<>();
				bList.add(attendanceTimeList.get(0).getUserName());
				for (int i = 0; i < attendanceTimeList.size(); i++) {
					AttendanceTime attendanceTime = attendanceTimeList.get(i);
					bList.add(attendanceTime.getTurnWorkTime());
					bList.add(attendanceTime.getOvertime());
					bList.add(attendanceTime.getDutytime());
				}
				bList.add(collect.getTurnWork());
				bList.add(collect.getOvertime());
				bList.add(collect.getDutyWork());
				bList.add(collect.getTakeWork());
				bList.add(collect.getAllWork());
				list.add(bList);
			}
		}
		return list;
	}
	
	
}
