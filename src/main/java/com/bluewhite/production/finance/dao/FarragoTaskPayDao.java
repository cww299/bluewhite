package com.bluewhite.production.finance.dao;

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

}
