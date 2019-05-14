package com.bluewhite.shiro.web.filter.user;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

/**
 * 验证用户过滤器
 * 1、用户是否删除
 * 2、用户是否锁定
 */
public class SysUserFilter extends AccessControlFilter {
	

	@Autowired
	private CacheManager cacheManager;
	
    @Autowired
    private UserService userService;


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		//获取请求的路径拍判断为前段用户请求或后端请求
		String requestUri = WebUtils.getPathWithinApplication(req);
		//用户缓存查询
		if(requestUri.startsWith("/login.jsp")){
			//判断有没有用户登录成功，没有则返回错误信息继续登录
			Cache<String, User> apiAccessTokenCache = cacheManager.getCache("sysUserCache");
			CurrentUser currentUser = SessionManager.getUserSession();
			CommonResponse commonResponse = new CommonResponse();
			if (currentUser == null) {
				return true;
			}else{
				WebUtils.issueRedirect(request, response, "/");
			}
		}
		return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        return false;
    }
}
