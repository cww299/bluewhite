package com.bluewhite.finance.consumption.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.consumption.entity.Consumption;

public interface ConsumptionDao extends BaseRepository<Consumption, Long>{
	
	/**
	 * 根据父id查询
	 * @param id
	 * @return
	 */
	public List<Consumption> findByParentId(Long id);

}
