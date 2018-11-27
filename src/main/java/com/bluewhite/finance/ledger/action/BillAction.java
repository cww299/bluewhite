package com.bluewhite.finance.ledger.action;

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
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.entity.Bill;
import com.bluewhite.finance.ledger.service.BillService;

/**
 * 乙方账单核对
 * @author qiyong
 *
 */
@Controller
public class BillAction {
	
private static final Log log = Log.getLog(BillAction.class);
	
	@Autowired
	private BillService billService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Bill.class,"id","partyNames","partyNamesId","offshorePay","acceptPay","acceptPayable","disputePay","nonArrivalPay","overpaymentPay","arrivalPay","dateToPay");
	}
	
	
	
	
	/**
	 * 分页查看账单
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getBill", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBill(HttpServletRequest request,PageParameter page,Bill bill) {
		CommonResponse cr = new CommonResponse();
		PageResult<Bill>  orderList= billService.findPages(bill, page); 
		cr.setData(clearCascadeJSON.format(orderList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 通过乙方账单，获取每天日期的金额和备注
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getBillDate", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse addBillDate(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		Object obj = billService.getBillDate(id);
		cr.setData(obj);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 通过乙方账单，修改每天日期的金额和备注
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/updateBill", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateBill(HttpServletRequest request,Bill bill) {
		CommonResponse cr = new CommonResponse();
		bill = billService.updateBill(bill);
		cr.setData(bill);
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
