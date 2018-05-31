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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.finance.entity.UsualConsume;
import com.bluewhite.production.finance.service.CollectPayService;
import com.bluewhite.production.finance.service.FarragoTaskPayService;
import com.bluewhite.production.finance.service.PayBService;
import com.bluewhite.production.finance.service.UsualConsumeService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.productionutils.constant.dao.ProductionConstantDao;

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
					.addRetainTerm(AttendancePay.class,"id","userName","allotTime","payNumber","workPrice","workTime")
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
							"monthHydropower","chummage","hydropower","logistics","monthLogistics")
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
							"monthHydropower","chummage","hydropower","logistics","monthLogistics")
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
		usualConsume.setConsumeDate(new Date());
		usualConsumeservice.save(usualConsume);
		cr.setMessage("新增成功");
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
					.addRetainTerm(UsualConsume.class,"chummage","hydropower","logistics","consumeDate")
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
				.addRetainTerm(CollectPay.class,"userName","addPerformancePay","orderTimeBegin","orderTimeEnd")
				.format(collectPayBService.collect(collectPay)).toJSON());	
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 生产成本数据汇总 0
	 * 
	 * 员工成本数据汇总 1
	 * 
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
		}else if(collectInformation.getStatus()==0){
			cr.setData(ClearCascadeJSON
					.get()
					.addRetainTerm(CollectInformation.class,"sumAttendancePay","giveThread","surplusThread").format(collectInformation).toJSON());	
		}
				
		cr.setMessage("查询成功");
		return cr;
	}
	
	

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHM.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
	

}
