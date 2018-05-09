package com.bluewhite.system.user.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Constants;
import com.bluewhite.common.Log;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
import com.google.common.base.Joiner;
import com.sun.star.reflection.InvocationTargetException;

@Controller
public class IndexAction {

	private final static Log log = Log.getLog(IndexAction.class);

	@Autowired
	private UserService userService;
	
	/**
	 * 普通用户登录
	 * @param request 请求
	 * @param reponse 回复
	 * @param username 用户名
	 * @param password 密码
	 * @return cr
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse login(HttpServletRequest request,
			HttpServletResponse reponse, String username, String password) throws IllegalAccessException, InvocationTargetException {
		CommonResponse cr = new CommonResponse();
		Subject subject = SecurityUtils.getSubject();
		User user = (User)subject.getSession().getAttribute("user");
		//当用户已经认证且用户匹配时
		if(user != null && subject.isAuthenticated() && subject.getPrincipal().equals(username)){
			cr.setMessage("用户登录成功。");
		}else{
			//用户未登录
			try {
				subject.login(new UsernamePasswordToken(username, password));
				user = userService.loginByUsernameAndPassword(username,password);//普通用户
				cr.setMessage("用户登录成功。");
				//手动调取授权
				SecurityUtils.getSubject().isPermitted(username);
				
			} catch (AuthenticationException e) {
				cr.setCode(ErrorCode.SYSTEM_USER_NOT_AUTHENTICATED.getCode());
				cr.setMessage("用户认证失败。");//可能的原因：1.账号不存在；2.密码错误;3.token已经过期
				return cr;
			}
		}
		CurrentUser cu = new CurrentUser();
		BeanCopyUtils.copyNotEmpty(user, cu, "");
		SessionManager.setUserSession(cu);
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("user", cu);
//		cr.setData(data);
		return cr;
	}
	

	/**
	 * 登出
	 * 
	 * @param token
	 * @return cr
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse logout() {
		Subject subject = SecurityUtils.getSubject();
		CommonResponse commonResponse = new CommonResponse();
		subject.logout();
		commonResponse.setMessage("登出成功！");
		return commonResponse;
	}

}
