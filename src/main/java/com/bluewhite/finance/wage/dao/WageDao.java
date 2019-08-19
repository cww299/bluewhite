package com.bluewhite.finance.wage.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.wage.entity.Wage;

public interface WageDao extends BaseRepository<Wage, Long>{
	
	/**
	 * 按时间 类型查询
	 * @return
	 */
	public List<Wage> findByTypeAndTimeBetween(Integer type,Date orderTimeBegin, Date orderTimeEnd);
	
	/**
	 * 按员工 时间查询
	 * @return
	 */
	public List<Wage> findByUserIdAndTimeBetween(Long userId,Date orderTimeBegin, Date orderTimeEnd);
}
