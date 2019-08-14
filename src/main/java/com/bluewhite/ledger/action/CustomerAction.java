package com.bluewhite.ledger.action;

import java.text.SimpleDateFormat;

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
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.service.CustomerService;
import com.bluewhite.system.sys.entity.RegionAddress;
import com.bluewhite.system.user.entity.User;

/**
 * 财务部 客户
 * 
 * @author qiyong
 *
 */

@Controller
public class CustomerAction {

	@Autowired
	private CustomerService customrService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON
				.get().addRetainTerm(Customer.class, "id", "name", "address", "type", "provinces", "city", "county",
						"phone", "user","buyerName")
				.addRetainTerm(User.class, "userName","id")
				.addRetainTerm(RegionAddress.class, "regionName","id");
	}

	/**
	 * 分页查看客户
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/customerPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse customerPage(PageParameter page, Customer customer) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(customrService.findPages(customer, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 
	 * 查看客户
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/allCustomer", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allCustomer() {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(customrService.findAll()).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 客户新增
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/addCustomer", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addCustomer(Customer customer) {
		CommonResponse cr = new CommonResponse();
		customrService.save(customer);
		cr.setMessage("添加成功");
		return cr;
	}

	/**
	 * 客户批量删除
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/ledger/deleteCustomer", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteCustomer(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = customrService.deleteCustomr(ids);
		cr.setMessage("成功删除" + count + "个客户");
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
}
