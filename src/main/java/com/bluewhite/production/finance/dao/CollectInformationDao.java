package com.bluewhite.production.finance.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.finance.entity.CollectInformation;

public interface CollectInformationDao extends BaseRepository<CollectInformation, Long>{
	/**
	 * 根据类型查找绩效汇总
	 * @param type
	 * @return
	 */
	CollectInformation findByType(Integer type);

}
