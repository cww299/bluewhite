package com.bluewhite.shiro.web.filter.user;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
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
    
    private String loginUrl = "/login.jsp";  
    
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		CurrentUser cu = SessionManager.getUserSession();
		if(cu==null){
			return false;
		}else{
			//获取缓存
			Cache<String, User> sysUserCache =  cacheManager.getCache("sysUserCache");
			//从缓存中获取当前登录用户，当缓存中没有，重定向到登录页面，同时清空session
			if(sysUserCache.get(cu.getUserName())==null){
				SessionManager.removeUserSession();
				return false;
			}else{
				if(cu.getRole()==null && cu.getPermissions()==null){
					Cache<String, SimpleAuthorizationInfo> apiAccessTokenCache =  cacheManager.getCache("sysAuthCache");
					SimpleAuthorizationInfo simpleAuthorizationInfo = apiAccessTokenCache.get(cu.getUserName());
					if(simpleAuthorizationInfo!=null){
						cu.setRole(simpleAuthorizationInfo.getRoles());
						cu.setPermissions(simpleAuthorizationInfo.getStringPermissions());
					}
				}
			}
			return true;
		}
    }  
  
    @Override  
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
        WebUtils.issueRedirect(request, response, loginUrl);  
        return false;  
    }  


}
