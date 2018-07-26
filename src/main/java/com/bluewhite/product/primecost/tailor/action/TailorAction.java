package com.bluewhite.product.primecost.tailor.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;

@Controller
public class TailorAction {
	
	
	private final static Log log = Log.getLog(TailorAction.class);
	
	
	@Autowired
	private TailorService tailorService;
	
	
	/**
	 * 裁剪填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addTailor", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTailor(HttpServletRequest request,Tailor tailor) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(tailor.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			try {
				tailorService.saveTailor(tailor);
			} catch (Exception e) {
				cr.setMessage(e.getMessage());
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				return cr;
			}
			cr.setMessage("添加成功");
		}
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
