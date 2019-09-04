package com.bluewhite.finance.consumption.dao;

import java.util.Date;
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
	
	/**
	 * 查出所有未付款和部分付款
	 * @param type
	 * @param flag
	 * @param flag1
	 * @return
	 */
	public List<Consumption> findByTypeAndFlagAndExpenseDateBetween(Integer type,Integer flag,Date beginTime, Date endTime);

}
