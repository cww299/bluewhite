package com.bluewhite.production.finance.action;

import java.text.SimpleDateFormat;

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
import com.bluewhite.production.finance.entity.FarragoTaskPay;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.finance.service.FarragoTaskPayService;
import com.bluewhite.production.finance.service.PayBService;

/**
 * 生产部质检财务相关action 
 * @author zhangliang
 *
 */
@Controller
public class FinanceAction {
	
private static final Log log = Log.getLog(FinanceAction.class);
	
	@Autowired
	private PayBService payBService;
	@Autowired
	private FarragoTaskPayService FarragoTaskPayService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(PayB.class,"id","userName","allotTime","payNumber","bacth","productName",
						"allotTime","performancePayNumber");
	}
	
	
	/** 
	 * 查询b工资流水
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
					.format(FarragoTaskPayService.findPages(farragoTaskPay, page)).toJSON());
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
