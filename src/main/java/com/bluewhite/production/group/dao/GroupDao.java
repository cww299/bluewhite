package com.bluewhite.production.group.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.group.entity.Group;

public interface GroupDao extends BaseRepository<Group, Long>{
	/**
	 * 根据类型查找分组
	 * @param type
	 * @return
	 */
	List<Group> findByType(Integer type);
	
	/**
	 * 根据名字查找分组
	 * @param type
	 * @return
	 */
	Group findByNameAndType(String group,Integer type);

}
