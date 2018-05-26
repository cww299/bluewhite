package com.bluewhite.production.finance.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.finance.entity.PayB;

public interface PayBDao extends BaseRepository<PayB, Long>{
	/**
	 * 根据任务id查询工资
	 * @return
	 */
	List<PayB> findByTaskId(Long id);

}
