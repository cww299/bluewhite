package com.bluewhite.system.user.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;

public interface UserContractDao extends BaseRepository<UserContract, Long>{

	
	/**
	 * 通过员工合同编号查找
	 * @param number
	 * @return
	 */
	UserContract findByNumber(String number);

}
