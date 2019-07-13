package com.bluewhite.production.group.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.group.entity.TemporarilyCollect;

public interface TemporarilyCollectDao extends BaseRepository<TemporarilyCollect, Long>{
	
	/**
	 * 根据日期查找特急人员考勤汇总
	 * @param string
	 * @return
	 */
	TemporarilyCollect findByTemporarilyDateAndUserId(String time,Long id);

}
