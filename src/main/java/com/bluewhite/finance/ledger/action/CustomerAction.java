package com.bluewhite.finance.ledger.action;

import java.text.SimpleDateFormat;
import java.util.List;

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
import com.bluewhite.finance.ledger.entity.Customer;
import com.bluewhite.finance.ledger.service.CustomerService;

/**
 * 财务部  客户
 * @author qiyong
 *
 */
@Controller
public class CustomerAction {
	private static final Log log = Log.getLog(CustomerAction.class);
	
	@Autowired
	private CustomerService customerService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Customer.class,"id","cusProductName","cusPartyNames","cusPrice");
	}
	
	/**
	 * 分页查看客户售价
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getCustomer", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOrder(HttpServletRequest request,PageParameter page,Customer customer) {
		CommonResponse cr = new CommonResponse();
		PageResult<Customer>  customerrList= customerService.findPages(customer, page); 
		cr.setData(clearCascadeJSON.format(customerrList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 财务客户售价新增
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addCustomer", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOrder(HttpServletRequest request,Customer customer) {
		CommonResponse cr = new CommonResponse();
		customerService.save(customer);
			cr.setMessage("添加成功");
		return cr;
	}
	
	
	
	/**
	 * 财务客户售价提示
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/tipsCustomer", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findByCusProductNameAndCusPartyNames(String productName ,String partyNames) {
		CommonResponse cr = new CommonResponse();
		List<Customer> listCustomer=customerService.findByCusProductNameAndCusPartyNames(productName, partyNames);
		cr.setData(clearCascadeJSON.format(listCustomer).toJSON());	
		cr.setMessage("成功");
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
