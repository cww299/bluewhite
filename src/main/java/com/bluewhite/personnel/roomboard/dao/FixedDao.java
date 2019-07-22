package com.bluewhite.personnel.roomboard.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Fixed;

public interface FixedDao extends BaseRepository<Fixed, Long>{
	

	public List<Fixed> findByHostelId(Long hostelId);

}
