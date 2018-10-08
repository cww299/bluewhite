package com.bluewhite.system.user.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;

public interface UserContractDao extends BaseRepository<UserContract, Long>{

	UserContract findByUsername(String username);

}
