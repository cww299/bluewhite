package com.bluewhite.system.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
import com.sun.star.reflection.InvocationTargetException;

/**
 * 
 * @author zhangliang
 *
 */
@Controller
public class IndexAction {

	private final static Log log = Log.getLog(IndexAction.class);

	@Autowired
	private UserService userService;
	
	/**
	 * 跳转首页
	 * @return
	 */
	@RequiresPermissions( "sys:index" )  
	@RequestMapping(value="/index")
	public String index() {
		return "index";
		
	}
	
	/**
	 *根据不同菜单跳转不同的jsp
	 */
	@RequestMapping(value = "/menusToUrl", method = RequestMethod.GET)
	public String menusToJsp(HttpServletRequest request,String url) {
		return url;
	}
	
	
	
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
		Map<String, Object> data = new HashMap<String, Object>();
		Subject subject = SecurityUtils.getSubject();
		CurrentUser cu = (CurrentUser)subject.getSession().getAttribute("user");
		//当用户已经认证且用户匹配时
		if(cu != null && subject.isAuthenticated() && subject.getPrincipal().equals(username)){
			cr.setMessage("用户登录成功。");
		}else{
			//用户未登录
			try {
				if(cu==null){
					cu = new CurrentUser();
				}
				subject.login(new UsernamePasswordToken(username, password));
				User user = userService.findByUserName(username);//普通用户
				BeanCopyUtils.copyNotEmpty(user, cu, "");
				subject.getSession().setAttribute("user", cu);
				cr.setMessage("用户登录成功。");
			} catch (AuthenticationException e) {
				cr.setCode(ErrorCode.SYSTEM_USER_NOT_AUTHENTICATED.getCode());
				cr.setMessage("用户认证失败。");//可能的原因：1.账号不存在；2.密码错误;3.token已经过期
				return cr;
			}
		}
		data.put("user", cu);
		cr.setData(data);
		return cr;
	}
	

	/**
	 * 登出
	 * 
	 * @param token
	 * @return cr
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		 SecurityUtils.getSubject().logout();  
		return "/";
	}

}
