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
import com.bluewhite.finance.ledger.entity.Mixed;
import com.bluewhite.finance.ledger.service.ContactService;
import com.bluewhite.finance.ledger.service.CustomerService;
import com.bluewhite.finance.ledger.service.MixedService;

/**
 * 财务部  客户信息
 * @author qiyong
 *
 */
@Controller
public class MixedAction {
	private static final Log log = Log.getLog(MixedAction.class);
	
	@Autowired
	private MixedService mixedService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Mixed.class,"id","mixtSubordinateTime","mixPartyNames","mixtTime","mixDetailed","mixPrice","mixPartyNamesId");
	}
	
	/**
	 * 分页查看杂支明细
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/getMixes", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Mixed mixed) {
		CommonResponse cr = new CommonResponse();
		PageResult<Mixed>  MixedList= mixedService.findPages(mixed, page); 
		cr.setData(clearCascadeJSON.format(MixedList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 杂支明细新增
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/addMixed", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addContact(HttpServletRequest request,Mixed mixed) {
		CommonResponse cr = new CommonResponse();
		mixedService.save(mixed);
			cr.setMessage("添加成功");
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
