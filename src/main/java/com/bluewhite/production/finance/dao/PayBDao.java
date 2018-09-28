package com.bluewhite.production.finance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.finance.entity.PayB;

public interface PayBDao extends BaseRepository<PayB, Long>{
	/**
	 * 根据任务id查询工资
	 * @return
	 */
	List<PayB> findByTaskId(Long id);
	
	/**
	 * 根据任务id和员工id查询工资
	 * @param id
	 * @param userid
	 * @return
	 */
	PayB findByTaskIdAndUserId(Long id, Long userid);
	
	/**
	 * 根据条件查询b
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<PayB> findByUserIdAndTypeAndAllotTimeBetween(Long userId, Integer type, Date orderTimeBegin,
			Date orderTimeEnd);

}
