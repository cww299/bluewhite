package com.bluewhite.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class MyCredentialsMatcher implements CredentialsMatcher {
	
	
	/**
	 * 查询数据库存储密码
	 * @param info
	 * @return
	 */
	public String getStoredPassword(AuthenticationInfo info){
		  return (String)info.getCredentials(); 
	}

	/**
	 * 校验用户身份
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info) {
		//系统传入的用户信息，用户提交的身份信息
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		//得到用户的密码令牌
		Object authenticatedCredentials = info.getCredentials();
		//得到用户的用户名
		String username = (String)token.getPrincipal();
		//得到用户的密码
		String submittedCredentials = new String(upt.getPassword());
		//进行身份比对		
		return equals(authenticatedCredentials, submittedCredentials);
	}
	
	/**
	 * 判断用户提交的身份认证是否通过
	 * @param authenticatedCredentials 已认证身份信息
	 * @param submittedCredentials 用户提交的身份信息
	 * @return
	 */
	private boolean equals(Object authenticatedCredentials, Object submittedCredentials){
		if(submittedCredentials == null){
			return false;
		}else if(!authenticatedCredentials.getClass().equals(submittedCredentials.getClass())){
			return false;
		}else if(authenticatedCredentials.equals(submittedCredentials)){
			return true;
		}
		return false;
	}

}
