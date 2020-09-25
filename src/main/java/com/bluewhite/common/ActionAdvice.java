package com.bluewhite.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

import org.apache.shiro.ShiroException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;

@ControllerAdvice
public class ActionAdvice{
	
	private static final Log log = Log.getLog(ActionAdvice.class);

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// 去除前端传入的前后空格
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		// 格式化日期格式
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		// 格式化double类型
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Short.class, new CustomNumberEditor(Short.class, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
		binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
		binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	CommonResponse handleException(Exception exception) {
		CommonResponse responseInfo = new CommonResponse();
		responseInfo.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		if (exception instanceof ServiceException) {
			ServiceException se = (ServiceException) exception;
			responseInfo.setMessage(exception.getMessage());
			if (se.getErrorCode() != null) {
				responseInfo.setCode(se.getErrorCode().getCode());
			}
			log.error(exception);
		}else if(exception instanceof ShiroException){	
			log.error("权限异常",exception);
			responseInfo.setMessage(exception.getCause().getMessage());
		}else if(exception instanceof IllegalArgumentException){   
		    responseInfo.setMessage(exception.getMessage());
		} else {
			log.error("系统异常",exception);
			responseInfo.setMessage("抱歉,服务器异常了,详情 [" + (exception == null ? "未知" : exception.getClass().getSimpleName().replace("Exception", "")) + "]");
		}
		return responseInfo;
	}
	
}