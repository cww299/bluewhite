package com.bluewhite.product.primecostbasedata.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;

public interface BaseOneTimeDao  extends BaseRepository<BaseOneTime, Long>{
	
	/**
	 * 根据基础id查找
	 * @param id
	 * @return
	 */
	List<BaseOneTime> findByBaseOneId(Long id);

}
