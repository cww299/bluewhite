package com.bluewhite.shiro.web.filter.user;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.bluewhite.common.Constants;
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
    private UserService userService;


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		//判断有没有用户登录成功，没有则返回错误信息继续登录
		CurrentUser currentUser = SessionManager.getUserSession();
		CommonResponse commonResponse = new CommonResponse();
		if (currentUser == null) {
			commonResponse.setCode(ErrorCode.UN_LOGIN_OR_LOGIN_EXPIRED.getCode());
			commonResponse.setData(null);
			commonResponse.setMessage("您的登录信息已失效，请重新登录");
			rep.setContentType("application/json;charset=utf-8");
			rep.getWriter().print(JSON.toJSONString(commonResponse));
			return false;
		} else {
			//用户存在，重新更新用户权限，确保用户权限处于最新状态
			User user = userService.findByUserName(currentUser.getUserName());
			Set<String> permissions = userService.findStringPermissions(user);
			Set<String> roles = new HashSet<String>();
			 for (Role role : user.getRoles()) {
				 roles.add(role.getRole());
	         }
			if (currentUser == null || currentUser.getId().equals(user.getId())) {
				currentUser = new CurrentUser();
				currentUser.setIsAdmin(user.getIsAdmin());
				currentUser.setId(user.getId());
				currentUser.setUserName(user.getUserName());
				currentUser.setRole(roles);
				currentUser.setPermissions(permissions);
			}
			SessionManager.setUserSession(currentUser);
			return true;
		}
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        getSubject(request, response).logout();
        return false;
    }
}
