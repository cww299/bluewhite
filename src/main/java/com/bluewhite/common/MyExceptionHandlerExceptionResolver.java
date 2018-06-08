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
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;



/**
 * 控制器异常处理，对表单提交返回的CommonResponse做了特殊处理。
 * 
 */
public class MyExceptionHandlerExceptionResolver extends
		ExceptionHandlerExceptionResolver {
	
	private static Logger logger = Logger.getLogger(MyExceptionHandlerExceptionResolver.class);
	
	private String defaultErrorView = "/common/error/error.jsp";
	
//	private String unauthorizedErrorView = "/common/error/403.jsp";//没有权限的错误提示页面，统一用json，不需要定义页面

	private FastJsonJsonView fastJsonView;
	
	private MessageSource messageSource;

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	public FastJsonJsonView getFastJsonView() {
		return fastJsonView;
	}

	public void setFastJsonView(FastJsonJsonView fastJsonView) {
		this.fastJsonView = fastJsonView;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		if (exception != null) {
			logger.error(getDetailException(exception));
		}
		if (handlerMethod == null) {
			return null;
		}
		Method method = handlerMethod.getMethod();
		if (method == null) {
			return null;
		}
		ModelAndView returnValue = super.doResolveHandlerMethodException(
				request, response, handlerMethod, exception);
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method,
				ResponseBody.class);

		//没有权限的异常判断，
		if (exception instanceof UnauthorizedException) {
//			String requestType = request.getHeader("X-Requested-With");
//			if(requestType != null && "XMLHttpRequest".equals(requestType)) {
			CommonResponse responseInfo = new CommonResponse();
			responseInfo.setCode(ErrorCode.FORBIDDEN.getCode());
			responseInfo.setMessage(messageSource.getMessage("user.no.authorization", null,null));
			return new ModelAndView(getFastJsonView(),responseInfo.toMap());
//			}
//			else{
//				setDefaultErrorView(unauthorizedErrorView);//设置重定向到403页面
//				return getDefaultModelAndView(new Exception("没有权限"));
//			}
		}
		
		// 使用注解，需要输出JSON格式的
		if (responseBodyAnn != null) {
			// 对通用响应的处理。
			CommonResponse responseInfo = new CommonResponse();
			responseInfo.setCode(ErrorCode.INTERNAL_SERVER_ERROR
					.getCode());
			if (exception instanceof ServiceException) {
				ServiceException se = (ServiceException) exception;
				responseInfo.setMessage(exception.getMessage());
				if (se.getErrorCode() != null) {
					responseInfo.setCode(se.getErrorCode().getCode());
				}
			}else{
				responseInfo.setMessage("抱歉,服务器异常了,详情 [" + (exception == null ? "未知" : exception.getClass().getSimpleName().replace("Exception", "")) + "]");
			}
			return new ModelAndView(getFastJsonView(),responseInfo.toMap());
			
		}// end if( responseBodyAnn != null ) {
		if (returnValue == null || returnValue.getViewName() == null) {
			return getDefaultModelAndView(exception);
		}
		returnValue.addObject("error", getError(exception));
		return returnValue;

	}// end doResolveHandlerMethodException

	

	private ModelAndView getDefaultModelAndView(Exception exception) {
		if (StringUtils.isBlank(getDefaultErrorView())) {
			return null;
		}
		ModelAndView mv = null;
		if (getDefaultErrorView().endsWith(".jsp")) {
			// 需要重定向的页面
			mv = new ModelAndView("redirect:" + getDefaultErrorView());
		} else {
			mv = new ModelAndView(getDefaultErrorView());
		}
		mv.addObject("error", getError(exception));
		return mv;
	}

	private Map<String, Object> getError(Exception exception) {
		Map<String, Object> error = new HashMap<String, Object>();
		error.put("message", exception.getMessage());
		return error;
	}
	
	private static Object getDetailException(Throwable e){
		if(!(e instanceof NullPointerException)){
			if(!(e instanceof ServiceException)) logger.error("",e);
			return e;
		}
		try{
	        StringWriter sw = new StringWriter();   
	        PrintWriter pw = new PrintWriter(sw, true);   
	        e.printStackTrace(pw);   
	        pw.flush();   
	        sw.flush();   
	        return sw.toString();
		}catch(Exception ex){
			ex.printStackTrace();
			return "获取exception失败。";
		}
	} 

}
