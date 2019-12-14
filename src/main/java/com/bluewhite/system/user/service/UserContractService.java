package com.bluewhite.system.user.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.entity.UserContract;

public interface UserContractService extends BaseCRUDService<UserContract, Long>{
		
	/**
	 * 按条件查看
	 * @param 
	 * @return
	 */
	public PageResult<UserContract> findPage(UserContract contract, PageParameter page);
	
	/**
	 * 新增
	 * @param 
	 */
	public UserContract addUserContract(UserContract userContract);
	
	/**
	 * 删除
	 * @param 
	 */
	public int deletes(String[] ids);
	
}
