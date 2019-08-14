package com.bluewhite.finance.consumption.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.consumption.entity.Custom;

public interface CustomDao extends BaseRepository<Custom, Long>{
	
	/**
	 * 根据类型和名称模糊查询
	 * @param type
	 * @param name
	 * @return
	 */
	public List<Custom> findByTypeAndNameLike(Integer type,String name);

	
	/**
	 * 根据类型和名称查询
	 * @param type
	 * @param name
	 * @return
	 */
	public Custom findByTypeAndName(Integer type,String name);
	
	/**
	 * 根据类型查询
	 * @param type
	 * @return
	 */
	public List<Custom> findByType(Integer type);
}
