package com.bluewhite.shiro.web.filter.user;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

public class FormLoginFilter extends PathMatchingFilter{
	
	 	private String loginUrlJsp = "/login.jsp";  
	 	private String loginUrl = "/login";  
	    private String successUrl = "/"; 
	    
	    @Override  
	    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {  
	        HttpServletRequest req = (HttpServletRequest) request;  
	        HttpServletResponse resp = (HttpServletResponse) response;  
	        if(SecurityUtils.getSubject().isAuthenticated()) {  
	        	 return redirectToSuccessUrl(req, resp);//已经登录过  
	        }else{
	        	if(isLoginRequest(req) || isLoginRequestJsp(req)) {  
	        		return true;//继续过滤器链  
	        	} else {//保存当前地址并重定向到登录界面  
	        		saveRequestAndRedirectToLogin(req, resp);  
	        		return false;  
	        	}  
	        }  
	    }  
	    
	    private boolean redirectToSuccessUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {  
	        WebUtils.redirectToSavedRequest(req, resp, successUrl);  
	        return false;  
	    }  
	    private void saveRequestAndRedirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {  
	        WebUtils.saveRequest(req);  
	        WebUtils.issueRedirect(req, resp, loginUrlJsp);  
	    }  
	  
	    //登录页面
	    private boolean isLoginRequest(HttpServletRequest req) {  
	        return pathsMatch(loginUrlJsp, WebUtils.getPathWithinApplication(req));  
	    }  
	    
	    //登录方法
	    private boolean isLoginRequestJsp(HttpServletRequest req) {  
	        return pathsMatch(loginUrl, WebUtils.getPathWithinApplication(req));  
	    }  

}
