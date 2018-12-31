package com.bluewhite.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;

/**
 * 控制器异常处理，对表单提交返回的CommonResponse做了特殊处理。
 * 
 */
@Component
public class MyExceptionHandlerExceptionResolver implements HandlerExceptionResolver {

	private static Logger logger = Logger.getLogger(MyExceptionHandlerExceptionResolver.class);


	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		// 输出错误Json
		ModelAndView mav = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		// 对通用响应的处理。
		CommonResponse responseInfo = new CommonResponse();
		responseInfo.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
		if (exception instanceof ServiceException) {
			ServiceException se = (ServiceException) exception;
			responseInfo.setMessage(exception.getMessage());
			if (se.getErrorCode() != null) {
				responseInfo.setCode(se.getErrorCode().getCode());
			}
		} else {
			responseInfo.setMessage("抱歉,服务器异常了,详情 ["
					+ (exception == null ? "未知" : exception.getClass().getSimpleName().replace("Exception", "")) + "]");
		}
		view.setAttributesMap(responseInfo.toMap());
		mav.setView(view);
		return mav;
	}

}
