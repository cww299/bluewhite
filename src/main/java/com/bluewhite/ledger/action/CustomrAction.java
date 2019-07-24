package com.bluewhite.ledger.action;

import java.text.SimpleDateFormat;

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
import com.bluewhite.ledger.entity.Customr;

/**
 * 财务部 客户
 * 
 * @author qiyong
 *
 */

@Controller
public class CustomrAction {

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get().addRetainTerm(Customr.class, "id", "", "",
				"");
	}

	/**
	 * 分页查看客户售价
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/ledger/customrPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse customrPage(PageParameter page, Customr customr) {
		CommonResponse cr = new CommonResponse();
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 财务客户售价新增
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/ledger/addCustomr", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addCustomr(Customr customr) {
		CommonResponse cr = new CommonResponse();
		cr.setMessage("添加成功");
		return cr;
	}

	
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
}
