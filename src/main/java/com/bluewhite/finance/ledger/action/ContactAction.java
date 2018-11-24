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
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.finance.ledger.entity.Customer;
import com.bluewhite.finance.ledger.service.ContactService;
import com.bluewhite.finance.ledger.service.CustomerService;

/**
 * 财务部  客户信息
 * @author qiyong
 *
 */
@Controller
public class ContactAction {
	private static final Log log = Log.getLog(ContactAction.class);
	
	@Autowired
	private ContactService contactService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Customer.class,"id","conPartyNames","conPhone","conWechat");
	}
	
	/**
	 * 分页查看客户售价
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getContact", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Contact contact) {
		CommonResponse cr = new CommonResponse();
		PageResult<Contact>  ContactList= contactService.findPages(contact, page); 
		cr.setData(clearCascadeJSON.format(ContactList).toJSON());
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
	@RequestMapping(value = "/fince/addContact", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addContact(HttpServletRequest request,Contact contact) {
		CommonResponse cr = new CommonResponse();
		contactService.save(contact);
			cr.setMessage("添加成功");
		return cr;
	}
	
	
	
	/**
	 * 财务客户Id查询
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getContactId", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse ContactId(Long id) {
		CommonResponse cr = new CommonResponse();
		Contact contact=contactService.findOne(id);
		cr.setData(clearCascadeJSON.format(contact).toJSON());	
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
