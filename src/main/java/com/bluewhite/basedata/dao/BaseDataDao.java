package com.bluewhite.basedata.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.basedata.entity.BaseData;

public interface BaseDataDao extends BaseRepository<BaseData, Long>{

	public BaseData findByName(String orgName);

}
