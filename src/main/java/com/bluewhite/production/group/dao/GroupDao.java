package com.bluewhite.production.group.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.group.entity.Group;
import java.lang.Long;

public interface GroupDao extends BaseRepository<Group, Long>{
	/**
	 * 根据类型
	 * @param type
	 * @return
	 */
	List<Group> findByType(Integer type);
	
	/**
	 * 根据名字
	 * @param type
	 * @return
	 */
	Group findByNameAndType(String group,Integer type);
	
	/**
	 * 根据工种
	 * @param kindworkid
	 * @return
	 */
	List<Group> findByKindWorkIdAndType(Long kindworkid,Integer type);

}
