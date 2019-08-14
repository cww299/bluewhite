package com.bluewhite.production.finance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.finance.entity.FarragoTaskPay;

/**
 * 
 * @author zhangliang
 *
 */
public interface FarragoTaskPayDao extends BaseRepository<FarragoTaskPay, Long>{
	/**
	 * 根据任务id查询工资流水
	 * @param id
	 * @return
	 */
	List<FarragoTaskPay> findByTaskId(Long id);

	FarragoTaskPay findByTaskIdAndUserId(Long id, Long userid);
	
	/**
	 * 根据条件查询杂工工资
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<FarragoTaskPay> findByUserIdAndTypeAndAllotTimeBetween(Long userId, Integer type, Date orderTimeBegin,
			Date orderTimeEnd);
	
	/**
	 * 根据条件查询杂工工资
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<FarragoTaskPay> findByTypeAndAllotTimeBetween(Integer type, Date orderTimeBegin,
			Date orderTimeEnd);

	List<FarragoTaskPay> findByAllotTimeBetween(Date orderTimeBegin, Date orderTimeEnd);

}
