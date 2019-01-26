package com.bluewhite.system.user.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.system.user.entity.User;

public interface UserDao extends BaseRepository<User, Long> {
	/**
	 * 通过用户姓名查找用户
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName);
	
	/**
	 * 通过登录名查找用户
	 * @param userName
	 * @return
	 */
	public User findByLoginName(String userName);
		
	/**
	 * 通过用户名及密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public User findByUserNameAndPassword(String userName,String password);
	
	/**
	 * 通过手机号查找用户
	 * @param phoneNum 手机号
	 * @return user
	 */
	public User findByPhone(String phone);

	/**
	 * 通过多个id查找用户
	 * @param ids 用户ids
	 * @return list
	 */
	public List<User> findByIdIn(List<Long> ids);
	
	/**
	 * 通过用户编号查找用户
	 * @param number
	 * @return
	 */
	public User findByNumber(String number);
	
	/**
	 * 通过部门查找未离职用户
	 * @param number
	 * @return
	 */
	public List<User> findByOrgNameIdAndQuit(Long orgNameId, int quit);


	

}
