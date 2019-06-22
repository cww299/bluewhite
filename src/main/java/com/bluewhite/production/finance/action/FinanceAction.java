package com.bluewhite.production.finance.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.MonthlyProduction;
import com.bluewhite.production.finance.entity.NonLine;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.finance.entity.UsualConsume;
import com.bluewhite.production.finance.service.CollectInformationService;
import com.bluewhite.production.finance.service.CollectPayService;
import com.bluewhite.production.finance.service.FarragoTaskPayService;
import com.bluewhite.production.finance.service.PayBService;
import com.bluewhite.production.finance.service.UsualConsumeService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;

/**
 * 生产部财务相关action 
 * @author zhangliang
 *
 */
@Controller
public class FinanceAction {
	
private static final Log log = Log.getLog(FinanceAction.class);
	
	@Autowired
	private PayBService payBService;
	@Autowired
	private CollectPayService collectPayBService;
	@Autowired
	private FarragoTaskPayService farragoTaskPayService;
	@Autowired
	private AttendancePayService attendancePayService;
	@Autowired
	private UsualConsumeService usualConsumeservice;
	@Autowired
	private CollectInformationService collectInformationService;
	@Autowired
	private PayBDao payBDao;
	
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(PayB.class,"id","userName","allotTime","payNumber","bacth","productName",
						"allotTime","performancePayNumber","task")
				.addRetainTerm(Task.class,"procedureName");
	}
	
	
	/** 
	 * 查询考情工资流水(A工资)
	 * 
	 */
	@RequestMapping(value = "/finance/allAttendancePay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allAttendancePay(HttpServletRequest request,AttendancePay attendancePay,PageParameter page) {
		CommonResponse cr = new CommonResponse();
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(AttendancePay.class,"id","userName","allotTime","payNumber","workPrice","workTime","overTime","dutyTime","maxPay","disparity")
					.format(attendancePayService.findPages(attendancePay, page)).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	
	/** 
	 * 查询b工资流水(正常任务)(包括加绩)
	 * 
	 */
	@RequestMapping(value = "/finance/allPayB", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allPayB(HttpServletRequest request,PayB payB,PageParameter page) {
		CommonResponse cr = new CommonResponse();
			cr.setData(clearCascadeJSON.format(payBService.findPages(payB, page)).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	/** 
	 * 查询b工资流水同时汇总金额
	 * 
	 */
	@RequestMapping(value = "/finance/allPayBSum", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allPayBSum(HttpServletRequest request,PayB payB) {
		CommonResponse cr = new CommonResponse();
			List<Object> payBList = payBDao.findPayNumber(payB.getType(),payB.getOrderTimeBegin(),payB.getOrderTimeEnd(),payB.getUserName(),payB.getBacth(),payB.getProductName());
			// 总金额
			List<Double> listPayNumber = new ArrayList<>();
			// 实际运费
			List<Double> listPerformancePayNumber = new ArrayList<>();
			Double sumPayNumber = 0.0;
			Double sumPerformancePayNumber = 0.0;
			if (payBList.size() > 0) {
				payBList.stream().forEach(c -> {
					PayB pb = (PayB)c;
					listPayNumber.add(pb.getPayNumber()==null ? 0 : pb.getPayNumber());
					listPerformancePayNumber.add(pb.getPerformancePayNumber()==null ? 0 : pb.getPerformancePayNumber());
				});
				sumPayNumber = NumUtils.sum(listPayNumber);
				sumPerformancePayNumber = NumUtils.sum(listPerformancePayNumber);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("sumPayNumber", sumPayNumber);
			map.put("sumPerformancePayNumber", sumPerformancePayNumber);
			cr.setData(payBList.size());
			cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/** 
	 * 查询杂工工资流水(包括加绩)
	 * 
	 */
	@RequestMapping(value = "/finance/allFarragoTaskPay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allFarragoTaskPay(HttpServletRequest request,FarragoTaskPay farragoTaskPay,PageParameter page) {
		CommonResponse cr = new CommonResponse();
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(FarragoTaskPay.class,"id","userName","allotTime","performancePayNumber","payNumber","taskId","taskName")
					.format(farragoTaskPayService.findPages(farragoTaskPay, page)).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 获取日常消费数值
	 */
	@RequestMapping(value = "/finance/getUsualConsume", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getUsualConsume(HttpServletRequest request,UsualConsume usualConsume) {
		CommonResponse cr = new CommonResponse();
		usualConsume = ProTypeUtils.usualConsume(usualConsume);
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(UsualConsume.class,"peopleLogistics","peopleNumber","monthChummage",
							"monthHydropower","chummage","hydropower","logistics","monthLogistics","equipment")
					.format(usualConsumeservice.usualConsume(usualConsume)).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 调节日常消费数值
	 */
	@RequestMapping(value = "/finance/usualConsume", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse usualConsume(HttpServletRequest request,UsualConsume usualConsume) {
		ProTypeUtils.updateUsualConsume(usualConsume);
		CommonResponse cr = new CommonResponse();
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(UsualConsume.class,"peopleLogistics","peopleNumber","monthChummage",
							"monthHydropower","chummage","hydropower","logistics","monthLogistics","equipment")
					.format(usualConsumeservice.usualConsume(usualConsume)).toJSON());
			cr.setMessage("修改成功");
		return cr;
	}
	
	/**
	 * 一键填加日常消费
	 */
	@RequestMapping(value = "/finance/addUsualConsume", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse addUsualConsume(HttpServletRequest request,UsualConsume usualConsume) {
		CommonResponse cr = new CommonResponse();
		if(usualConsume.getConsumeDate()==null){
			usualConsume.setConsumeDate(new Date());
		}
		PageParameter page = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		//获取今天的开始和结束时间
		usualConsume.setOrderTimeBegin(DatesUtil.getfristDayOftime(usualConsume.getConsumeDate()));
		usualConsume.setOrderTimeEnd(DatesUtil.getLastDayOftime(usualConsume.getConsumeDate()));
		if(usualConsumeservice.findPages(usualConsume, page).getRows().size()>0){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("该日已经添加过日常消费，无需再次添加");
		}else{
			usualConsumeservice.save(usualConsume);
			cr.setMessage("新增成功");
		}
		return cr;
	}
	
	/**
	 * 修改日常消费
	 */
	@RequestMapping(value = "/finance/updateConsume", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateConsume(HttpServletRequest request,UsualConsume usualConsume) {
		CommonResponse cr = new CommonResponse();
		usualConsumeservice.save(usualConsume);
		cr.setMessage("修改成功");
		return cr;
	}
	
	
	/**
	 * 删除日常消费
	 */
	@RequestMapping(value = "/finance/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request,String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				usualConsumeservice.delete(id);
				count++;
			}
		}
		cr.setMessage("成功删除"+count+"条");
		return cr;
	}
	
	/** 
	 * 查询日销流水
	 * 
	 */
	@RequestMapping(value = "/finance/allUsualConsume", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allUsualConsume(HttpServletRequest request,UsualConsume usualConsume,PageParameter page) {
		CommonResponse cr = new CommonResponse();
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(UsualConsume.class,"chummage","hydropower","logistics","consumeDate","id")
					.format(usualConsumeservice.findPages(usualConsume, page)).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**************************  汇总相关业务    ********************************/
	
	
	/** 
	 * 单天员工的绩效汇总表（上报财务）
	 * 
	 */
	@RequestMapping(value = "/finance/collectPay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse collectPay(HttpServletRequest request,CollectPay collectPay) {
		CommonResponse cr = new CommonResponse();
		if(DatesUtil.sameDate(collectPay.getOrderTimeBegin(), collectPay.getOrderTimeEnd())){
			cr.setData(payBService.collectPay(collectPay));	
			cr.setMessage("查询成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("请选择两个日期为同一天");
		}
		return cr;
	}
	
	
	/** 
	 * 单天员工的绩效按个人比例调节
	 * 
	 */
	@RequestMapping(value = "/finance/updateCollectPay", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateCollectPay(HttpServletRequest request,CollectPay collectPay) {
		CommonResponse cr = new CommonResponse();
		CollectPay pay = collectPayBService.findOne(collectPay.getId());
		pay.setAddSelfNumber(collectPay.getAddSelfNumber());
		pay.setAddSelfPayB(collectPay.getAddSelfNumber()*pay.getPayB());
		pay.setAddPerformancePay(pay.getAddSelfPayB()-pay.getPayA()>0 ? pay.getAddSelfPayB()-pay.getPayA() : 0.0);
		pay.setHardAddPerformancePay(collectPay.getHardAddPerformancePay());
		collectPayBService.save(pay);
		cr.setData(collectPayBService.save(pay));
		cr.setMessage("修改成功");
		return cr;
	}
	
	
	/** 
	 * 日期内员工的绩效汇总表（上报财务）
	 * 
	 */
	@RequestMapping(value = "/finance/sumCollectPay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse sumCollectPay(HttpServletRequest request,CollectPay collectPay) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(CollectPay.class,"userName","addPerformancePay","orderTimeBegin","orderTimeEnd","payA","payB","addPayB")
				.format(collectPayBService.collect(collectPay)).toJSON());	
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 生产成本数据汇总 
	 * 
	 * 员工成本数据汇总 
	 * 
	 */
	@RequestMapping(value = "/finance/collectInformation", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse collectInformation(HttpServletRequest request,CollectInformation collectInformation) {
		CommonResponse cr = new CommonResponse();
		collectInformation = collectInformationService.findByType(collectInformation);
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(CollectInformation.class,"regionalPrice","sumTask","sumTaskFlag","sumFarragoTask","priceCollect","proportion","overtop","sumAttendancePay","giveThread","surplusThread","manage",
							"deployPrice","analogDeployPrice","sumChummage","sumHydropower","sumLogistics",
							"analogPerformance","surplusManage","manageProportion","managePerformanceProportion",
							"analogTime","grant","giveSurplus","shareholderProportion","shareholder","workshopSurplus")
					.format(collectInformation).toJSON());	
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 质检月产量报表
	 * 
	 */
	@RequestMapping(value = "/finance/monthlyProduction", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse monthlyProduction(HttpServletRequest request,MonthlyProduction monthlyProduction) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(MonthlyProduction.class,"peopleNumber","time","productNumber","productPrice","reworkNumber","reworkTurnTime",
						"userName","rework","reworkTime","orderTimeBegin","orderTimeEnd","farragoTaskTime","farragoTaskPrice","reworkCount")
				.format(collectPayBService.monthlyProduction(monthlyProduction)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/****************一楼包装固有功能**********************/
	
	/**
	 * 每天需要进行更新
	 * 获取非一线人员的绩效汇总表，
	 * 将 每个组的男女组长+潘松固定成一个列表
	 * 男组长产量每天产生，女组长根据进行调控填写
	 * 
	 * 
	 */
	@RequestMapping(value = "/finance/headmanPay", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse headmanPay(HttpServletRequest request,NonLine nonLine) {
		CommonResponse cr = new CommonResponse();
		cr.setData(collectPayBService.headmanPay(nonLine));
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 每天需要进行更新
	 * 获取非一线人员的绩效汇总表，
	 * 将 每个组的男女组长+潘松固定成一个列表
	 * 男组长产量每天产生，女组长根据进行调控填写
	 * 
	 * 
	 */
	@RequestMapping(value = "/finance/updateHeadmanPay", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateHeadmanPay(HttpServletRequest request,NonLine nonLine) {
		CommonResponse cr = new CommonResponse();
		cr.setData(collectPayBService.updateHeadmanPay(nonLine));
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 获取当月的产量详细数值 
	 * 
	 */
	@RequestMapping(value = "/finance/getMouthYields", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getMouthYields(HttpServletRequest request,Long id,String date) {
		CommonResponse cr = new CommonResponse();
		cr.setData(collectPayBService.getMouthYields(id,date));
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/****************二楼固有功能**********************/
	
	/**
	 * 获取整个月考勤时间的汇总，各组人员的B工资+杂工工资汇总，计算出他们之间的比值
	 * 
	 */
	@RequestMapping(value = "/finance/bPayAndTaskPay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse bPayAndTaskPay(HttpServletRequest request,MonthlyProduction monthlyProduction) {
		CommonResponse cr = new CommonResponse();
		cr.setData(collectPayBService.bPayAndTaskPay(monthlyProduction));
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 获取整个月人员的绩效
	 * 
	 * @param binder
	 */
	@RequestMapping(value = "/finance/twoPerformancePay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse twoPerformancePay(HttpServletRequest request,CollectPay collectPay) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
						.get()
						.addRetainTerm(CollectPay.class,"id","payB","payA","ratio","time","timePrice","timePay","userId","userName","addSelfNumber","addPerformancePay")
						.format(collectPayBService.twoPerformancePay(collectPay)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 获取整个月分组人员的绩效,根据系数进行调节奖励
	 * 
	 */
	@RequestMapping(value = "/finance/upadtePerformancePay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse upadtePerformancePay(HttpServletRequest request,CollectPay collectPay) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(CollectPay.class,"id","time","timePrice","timePay","userId","userName","addSelfNumber","addPerformancePay")
					.format(collectPayBService.upadtePerformancePay(collectPay)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 二楼统计出充棉组做其他任务的b工资和数量
	 * 
	 */
	@RequestMapping(value = "/finance/cottonOtherTask ", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse cottonOtherTask(HttpServletRequest request,CollectPay collectPay) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(CollectPay.class,"payB","userName")
				.format(collectPayBService.cottonOtherTask(collectPay)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 记录部门支出，存入数据汇总
	 * 
	 */
	@RequestMapping(value = "/finance/departmentalExpenditure ", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse departmentalExpenditure(HttpServletRequest request,CollectInformation collectInformation) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(CollectInformation.class,"")
				.format(collectInformationService.savaDepartmentalExpenditure(collectInformation)).toJSON());
		cr.setMessage("查询成功");
		return cr;
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
