package com.bluewhite.product.primecostbasedata.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;

public interface BaseOneDao  extends BaseRepository<BaseOne, Long>{
	
	/**
	 * 根据类型查找基础数据1
	 * @param type
	 * @return
	 */
	List<BaseOne> findByType(String type);

}
