package com.bluewhite.production.group.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.group.entity.GroupTime;

public interface GroupTimeDao extends BaseRepository<GroupTime, Long>{
	
	/**
	 * 
	 * @param userId
	 * @param type
	 * @param groupId
	 * @param allotTime
	 * @return
	 */
	GroupTime findByUserIdAndTypeAndGroupIdAndAllotTime(Long userId,Integer type,Long groupId,Date allotTime);

}
