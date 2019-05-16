package com.bluewhite.system.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.Log;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;


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
	@Autowired
	private CacheManager cacheManager;
	
	
	/**
	 * 跳转首页
	 * @return
	 */
	@RequiresPermissions( "sys:*" ) 
	@RequestMapping(value="/")
	public String index() {
		return "index";
	}
	
	/**
	 * 根据不同菜单跳转不同的jsp
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
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse login(HttpServletRequest request,
			HttpServletResponse reponse, String username, String password,Boolean rememberme){
		CommonResponse cr = new CommonResponse();
		Cache<String, User> sysUserCache = cacheManager.getCache("sysUserCache");
		Subject subject = SecurityUtils.getSubject();
		CurrentUser cu = SessionManager.getUserSession();
		//当用户已经认证且用户匹配时
		if(cu != null && subject.isAuthenticated() && subject.getPrincipal().equals(username)){
			cr.setMessage("用户已登录");
		}else{
			//用户未登录
			try {
				subject.login(new UsernamePasswordToken(username, password));
				User user = userService.loginByUsernameAndPassword(username, password);
				//登录后更新缓存和session用户信息
				sysUserCache.put(username, user);
				cu = new CurrentUser();
				cu.setId(user.getId());
				cu.setIsAdmin(user.getIsAdmin());
				cu.setUserName(user.getUserName());
				cu.setOrgNameId(user.getOrgNameId());
				cu.setPositionId(user.getPositionId());
				SessionManager.setUserSession(cu);
			} catch (ServiceException e) {
				cr.setCode(ErrorCode.FORBIDDEN.getCode());
				cr.setMessage(e.getCause().getMessage());
				return cr;
			} catch (IncorrectCredentialsException e1) {
				cr.setCode(ErrorCode.SYSTEM_USER_PASSWORD_WRONG.getCode());
                cr.setMessage("密码错误");
                return cr;
            }
			cr.setMessage("用户登录成功");
		}
		return cr;
	}
	
	

	/**
	 * 登出
	 * 
	 * @param token
	 * @return cr
	 */
	@RequestMapping(value = "/logout" , method = RequestMethod.GET)
	public String logout() {
		SessionManager.removeUserSession();
		return "redirect:login.jsp";
	}
	

}
