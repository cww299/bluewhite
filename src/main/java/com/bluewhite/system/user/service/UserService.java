package com.bluewhite.system.user.service;

import java.util.List;
import java.util.Set;

import javax.security.auth.Subject;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;

@Service
public interface UserService extends BaseCRUDService<User, Long> {
	
	/**
	 * 通过用户名查找
	 * @param username 用户名
	 * @return user
	 */
	public User findByUserName(String userName);
	
	
	/**
	 * 通过用户名查找
	 * @param username 用户名
	 * @return user
	 */
	public User findByloginName(String userName);
	
	/**
	 * 通过用户名密码验证登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User loginByUsernameAndPassword(String username, String password);
	
	
	/**
	 * 查询用户拥有的权限
	 */
	Set<String> findStringPermissions(User user);
	
	
	/**
	 * 分页查询 用户信息
	 * @param page
	 * @param user
	 * @return
	 */
	public List<User> getPagedUser(PageParameter page, User user);


	/**
	 * 通过手机号查找
	 * @param phoneNum 手机号
	 * @return user
	 */
	public User findByPhone(String phone);




	/**
	 * 删除指定ids用户
	 * @param ids 多条id
	 * @return boolean
	 */
	public boolean deleteUsers(String ids);
	
	/**
	 * 添加不同角色到同一用户
	 * @param user  用户实体类
	 * @param roleIds 角色id
	 * @return user
	 */
	public User saveHasExistUser(User user, String roleIds);
	
	/**
	 * 分页查询统计
	 * @param page 分页参数
	 * @param user 用户实体类
	 * @return list
	 */
	public List<User> getPagedUserForCount(PageParameter page, User user);

	

	/**
	 * 恢复用户初始化密码为123456
	 * @param user dai 
	 */
	public boolean resetPwdByDefault(Long userId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
