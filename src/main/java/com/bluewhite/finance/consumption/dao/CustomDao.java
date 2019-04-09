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
	public List<Custom> findByType(Integer type);

}
