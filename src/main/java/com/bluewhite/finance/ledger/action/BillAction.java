package com.bluewhite.finance.ledger.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.entity.Bill;
import com.bluewhite.finance.ledger.entity.Contact;
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
				.addRetainTerm(Bill.class,"id","partyNames");
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
	 * 通过乙方账单，添加日期的金额和备注
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addBillDate", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse addBillDate(HttpServletRequest request,long id) {
		CommonResponse cr = new CommonResponse();
		
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	

}
