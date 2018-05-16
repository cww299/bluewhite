package com.bluewhite.common;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.alibaba.fastjson.JSONObject;
import com.bluewhite.common.annotation.SysLogAspectAnnotation;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.system.sys.entity.SysLog;
import com.bluewhite.system.sys.service.SysLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 系统日志处理切片类
 * 
 * @author long.xin
 * @since 2017-01-17 18:00
 * @version 1.0
 */
@Aspect
@Component
public class SysLogAspect {
	// 注入Service用于把日志保存数据库
	@Autowired
	private SysLogService logService;
	// 本地异常日志记录对象
	private static final Logger logger = LoggerFactory
			.getLogger(SysLogAspect.class);

	@Autowired

	// Service层切点
	@Pointcut("@annotation(com.bluewhite.common.annotation.SysLogAspectAnnotation)")
	public void serviceAspect() {
	}
	
	// Service层异常切点
	@Pointcut("@annotation(com.bluewhite.common.annotation.SysLogAspectAnnotation)")
	public void serviceExceptionAspect() {
	}

	// Controller层切点
	@Pointcut("@annotation(com.bluewhite.common.annotation.SysLogAspectAnnotation)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		// 读取session中的用户
		CurrentUser cu = SessionManager.getUserSession();
		// 请求的IP
		String ip = request.getRemoteAddr();
		try {
			// *========控制台输出=========*//
			/*System.out.println("=====前置通知开始=====");
			System.out.println("请求方法:"
					+ (joinPoint.getTarget().getClass().getName() + "."
							+ joinPoint.getSignature().getName() + "()"));
			System.out.println("方法描述:"
					+ getControllerMethodDescription(joinPoint));
			System.out.println("请求人:" + cu.getUserName());
			System.out.println("请求IP:" + ip);*/
			// *========数据库日志=========*//
			SysLog log = new SysLog();
			setAnnotationInfoToLog(joinPoint, log);
			log.setOpertator(cu.getUserName());
			log.setOpertatorId(cu.getId());
			log.setOperateTime(new Date());
			//登出日志必须在方法执行前才能获取到用户信息，所以在此处记录日志
			if(log.getOperateDetail().contains("logout")){
				log.setOpertionType("登出");
			}
			// 保存数据库
			logService.log(log);
			System.out.println("=====前置通知结束=====");
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}
	
	/**
	 * 切片时，将获取到的日志注解类信息放到日志中
	 * 处理request、response参数数据
	 * @param joinPoint 切点
	 * @param log 日志信息
	 */
	private void setAnnotationInfoToLog(JoinPoint joinPoint, SysLog log) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getResponse();
		SysLogAspectAnnotation annotation = null;
		// 获取请求ip
		String ip = request.getRemoteAddr();
		// 获取用户请求方法的参数并序列化为JSON格式字符串
		String params = "";
		//获取注解出现的实体类
		Class targetClass = joinPoint.getTarget().getClass();
		//获取注解的方法名
		String methodName = joinPoint.getSignature().getName();
		Method[] methods = targetClass.getMethods();
		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (int i = 0; i < joinPoint.getArgs().length; i++) {
				if(joinPoint.getArgs()[i] instanceof ServletRequest){
					params += JSONObject.toJSONString(request.getParameterMap());
					continue;
				}else if(joinPoint.getArgs()[i] instanceof ServletResponse){
					params += "encoding:" + response.getCharacterEncoding() +" contenttype:" + response.getContentType();
					continue;
				}
				//此处使用JSONObject.toJSONString有个致命问题，在取的参数是通过dao.save的方法返回的对象实体时，
				//object各种问题由于许多实体关联关系会出现stackOverFlow错误,改成JSONUtils可解决这个问题，待验证。问题还没解决，关键要解决hibernate循环引用的问题
				//params += JSONObject.toJSONString(joinPoint.getArgs()[i], SerializerFeature.DisableCircularReferenceDetect) + ";";
				ObjectMapper mapper = new ObjectMapper();
				//mapper.configure(SerializerFeature.DisableCircularReferenceDetect, true);
				//mapper.disable(MapperFeature..FAIL_ON_EMPTY_BEANS);
	            String json = "";
				try {
					json = mapper.writeValueAsString(joinPoint.getArgs()[i]);
				} catch (JsonProcessingException e) {
					json = "json序列化异常,未获取到参数序列化。";
				}
				//方法二：该方法可以将参数转为字符串输出
				//System.out.println("打印请求参数：" + Arrays.toString(joinPoint.getArgs()));
				params += json + ";";
				
			}
		}
		for(Method method: methods){
			//遍历类对象找到被注解的方法
			if(method.getName().equals(methodName)){
				annotation = method.getAnnotation(SysLogAspectAnnotation.class);
				String description = annotation.description();
				String logType = annotation.logType();
				String operateType = annotation.operateType();
				String module = annotation.module();
				log.setCreatedAt(new Date());
				log.setModule(module);
				log.setOperateDetail(description);
				log.setLogType(logType);
				log.setOpertionType(operateType);
				log.setOperateName(methodName);
			}
		}
		log.setOperateDetail(params);
		log.setOperateIp(ip);
	}
	
	/**
	 * 业务正常结束切片处理
	 * @param joinPoint
	 * @param e
	 */
	@AfterReturning(value = "serviceAspect()")
	public void doAfterService(JoinPoint joinPoint) {
		// 读取session中的用户
		CurrentUser cu = SessionManager.getUserSession();
		try {
			/* ========控制台输出========= */
			/*System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));
			System.out.println("请求人:" + cu.getUserName());
			System.out.println("请求IP:" + ip);
			System.out.println("请求参数:" + params);*/
			/* ==========数据库日志========= */
			SysLog log = new SysLog();
			setAnnotationInfoToLog(joinPoint, log);
			log.setOpertator(cu.getUserName());
			log.setOpertatorId(cu.getId());
			log.setOperateTime(new Date());
			System.out.println("=====Afterreturning通知结束=====");
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("异常信息:{}", ex.getMessage());
		}

	}

	/**
	 * 异常日志切片处理
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceExceptionAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		// 读取session中的用户
		CurrentUser cu = SessionManager.getUserSession();
		try {
			/* ========控制台输出========= */
			/*System.out.println("=====异常通知开始=====");
			System.out.println("异常代码:" + e.getClass().getName());
			System.out.println("异常信息:" + e.getMessage());
			System.out.println("异常方法:"
					+ (joinPoint.getTarget().getClass().getName() + "."
							+ joinPoint.getSignature().getName() + "()"));
			System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));
			System.out.println("请求人:" + cu.getUserName());
			System.out.println("请求IP:" + ip);
			System.out.println("请求参数:" + params);*/
			/* ==========数据库日志========= */
			SysLog log = new SysLog();
			setAnnotationInfoToLog(joinPoint, log);
			log.setOpertator(cu.getUserName());
			log.setOpertatorId(cu.getId());
			log.setOperateTime(new Date());
			log.setSuccess("异常");
			log.setException(e.getMessage());
		} catch (Exception ex) {
			// 记录本地异常日志
			logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget()
					.getClass().getName()
					+ joinPoint.getSignature().getName(), ex.getClass().getName(),
					e.getMessage(), "未记录参数");
		}
	}
}
