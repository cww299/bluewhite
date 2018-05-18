package com.bluewhite.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;



public class UserRealm extends AuthorizingRealm {
	
	@Autowired
    private UserService userService;
	

	private static final Logger log = LoggerFactory.getLogger("user-error");
	
	//AuthorizationInfo:角色的权限信息集合
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	 String username = (String) principals.getPrimaryPrincipal();
    	 User user = userService.findByUserName(username);
         // 根据用户查询当前用户拥有的角色
         Set<String> roleNames = new HashSet<String>();
         for (Role role : user.getRoles()) {
             roleNames.add(role.getRole());
         }
         // 根据用户查询当前用户权限
         Set<String> permissions = userService.findStringPermissions(user);
         SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
         // 将角色名称提供给info
         authorizationInfo.setRoles(roleNames);
         // 将权限名称提供给info
         authorizationInfo.setStringPermissions(permissions);
         
         CurrentUser currentUser = SessionManager.getUserSession();
 		if (currentUser == null || currentUser.getId().equals(user.getId())) {
			currentUser = new CurrentUser();
			currentUser.setIsAdmin(user.getIsAdmin());
			currentUser.setId(user.getId());
			currentUser.setUserName(user.getUserName());
			currentUser.setOrgNameId(user.getOrgNameId());
			currentUser.setPositionId(user.getPositionId());
			currentUser.setRole(roleNames);
			currentUser.setPermissions(permissions);
		}
         SessionManager.setUserSession(currentUser);
        return authorizationInfo;
    }
    
    //先加载用户身份的AuthenticationInfo，在通过MyCredentialsMatcher，将用户身份传过去，得到用户的密码，进行比对
    //AuthenticationInfo:用户的角色信息集合
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws ServiceException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        if(upToken == null){
        	throw new ServiceException("请输入账号与密码！");
        }
        String username = (String) token.getPrincipal();
        User user = userService.findByUserName(username);
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        if (user.getDelFlag() == 0) {
            // 用户被管理员锁定抛出异常
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		user.getUserName(),
                user.getPassword(), 
                ByteSource.Util.bytes(user.getUserName()), 
                getName());
        return authenticationInfo;
    }
}
