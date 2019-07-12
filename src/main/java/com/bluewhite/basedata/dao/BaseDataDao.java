package com.bluewhite.basedata.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.basedata.entity.BaseData;

public interface BaseDataDao extends BaseRepository<BaseData, Long>{

	public BaseData findByName(String orgName);
	
	/**
	 * 根绝类型查找所有的基础数据
	 * @return
	 */
	public List<BaseData> findByType(String type);

}
