package com.bluewhite.system.sys.action;

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

import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.system.sys.entity.SysLog;
import com.bluewhite.system.sys.service.SysLogService;

@Controller
@RequestMapping("/view/guest")
public class SysLogAction {
	@Autowired
	private SysLogService service;
	
	/**
	 * 行为数据接口
	 * @param request
	 * @param log
	 * @return
	 */
	@RequestMapping(value = "behavior", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPage(HttpServletRequest request, SysLog log,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(service.findLog(log,page));
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 格式化时间
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
	}
}
