package com.bluewhite.system.user.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;

public interface TemporaryUserDao extends BaseRepository<TemporaryUser, Long> {
	
	/**
	 * 通过多个id查找用户
	 * @param ids 用户ids
	 * @return list
	 */
	public List<TemporaryUser> findByIdIn(List<Long> ids);

}
