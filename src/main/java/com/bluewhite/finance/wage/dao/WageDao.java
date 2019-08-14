package com.bluewhite.finance.wage.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.wage.entity.Wage;

public interface WageDao extends BaseRepository<Wage, Long>{
	
	/**
	 * 按吃饭时间 员工ID查询
	 * @return
	 */
	public List<Wage> findByTypeAndTimeBetween(Integer type,Date orderTimeBegin, Date orderTimeEnd);
}
