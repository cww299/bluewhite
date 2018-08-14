package com.bluewhite.production.group.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.group.entity.Temporarily;

public interface TemporarilyDao extends BaseRepository<Temporarily, Long>{
	
	public List<Temporarily> findByType(Integer type);

	public Temporarily findByUserIdAndTemporarilyDate(Long userId, Date getfristDayOftime);

}
