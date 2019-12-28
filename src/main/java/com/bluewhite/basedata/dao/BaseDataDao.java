package com.bluewhite.basedata.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.basedata.entity.BaseData;
import java.lang.Long;

public interface BaseDataDao extends BaseRepository<BaseData, Long>{

	public BaseData findByName(String orgName);
	
	/**
	 * 根绝类型查找所有的基础数据
	 * @return
	 */
	public List<BaseData> findByType(String type);
	
	/**
	 * 根绝类型查找所有的基础数据
	 * @return
	 */
	public List<BaseData> findByTypeAndOrd(String type,String ord);
	
	/**
	 * 通过fuid查找
	 * @param parentid
	 * @return
	 */
	List<BaseData> findByParentId(Long parentid);

}
