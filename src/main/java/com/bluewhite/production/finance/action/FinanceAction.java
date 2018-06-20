package com.bluewhite.production.finance.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
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
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.MonthlyProduction;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.finance.entity.UsualConsume;
import com.bluewhite.production.finance.service.CollectPayService;
import com.bluewhite.production.finance.service.FarragoTaskPayService;
import com.bluewhite.production.finance.service.PayBService;
import com.bluewhite.production.finance.service.UsualConsumeService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;

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
	
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(PayB.class,"id","userName","allotTime","payNumber","bacth","productName",
						"allotTime","performancePayNumber");
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
					.addRetainTerm(AttendancePay.class,"id","userName","allotTime","payNumber","workPrice","workTime","overtime","dutyTime")
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
	public CommonResponse delete(HttpServletRequest request,UsualConsume usualConsume) {
		CommonResponse cr = new CommonResponse();
		usualConsumeservice.delete(usualConsume.getId());
		cr.setMessage("删除成功");
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
		collectPayBService.save(pay);
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
	 * 生产成本数据汇总 0
	 * 
	 * 员工成本数据汇总 1
	 * 
	 * @param binder
	 */
	@RequestMapping(value = "/finance/collectInformation", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse collectInformation(HttpServletRequest request,CollectInformation collectInformation) {
		CommonResponse cr = new CommonResponse();
		collectInformation = collectPayBService.collectInformation(collectInformation);
		if(collectInformation.getStatus()==0){
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(CollectInformation.class,"regionalPrice","sumTask","sumTaskFlag","sumFarragoTask","priceCollect","proportion","overtop")
					.format(collectInformation).toJSON());	
		}else if(collectInformation.getStatus()==1){
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(CollectInformation.class,"sumAttendancePay","giveThread","surplusThread",
							"deployPrice","analogDeployPrice","sumChummage","sumHydropower","sumLogistics",
							"analogPerformance","surplusManage","manageProportion","managePerformanceProportion",
							"analogTime","grant","giveSurplus","shareholderProportion","shareholder","workshopSurplus").format(collectInformation).toJSON());	
		}
				
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 质检月产量报表
	 * 
	 * @param binder
	 */
	@RequestMapping(value = "/finance/monthlyProduction", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse monthlyProduction(HttpServletRequest request,MonthlyProduction monthlyProduction) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(MonthlyProduction.class,"peopleNumber","time","productNumber","productPrice","reworkNumber","reworkTurnTime",
						"userName","rework","reworkTime","orderTimeBegin","orderTimeEnd")
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
	 * @param binder
	 */
	@RequestMapping(value = "/finance/headmanPay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse headmanPay(HttpServletRequest request,MonthlyProduction monthlyProduction) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(MonthlyProduction.class,"peopleNumber","time","productNumber","productPrice","reworkNumber","reworkTurnTime",
						"userName","rework","reworkTime","orderTimeBegin","orderTimeEnd")
				.format(collectPayBService.headmanPay(monthlyProduction)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/****************二楼固有功能**********************/
	
	/**
	 * 获取整个月考勤时间的汇总，各组人员的B工资+杂工工资汇总，计算出他们之间的比值
	 * 
	 * @param binder
	 */
	@RequestMapping(value = "/finance/bPayAndTaskPay", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse bPayAndTaskPay(HttpServletRequest request,MonthlyProduction monthlyProduction) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(MonthlyProduction.class,"peopleNumber","time","productNumber","productPrice","reworkNumber","reworkTurnTime",
						"userName","rework","reworkTime","orderTimeBegin","orderTimeEnd")
				.format(collectPayBService.bPayAndTaskPay(monthlyProduction)).toJSON());
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
