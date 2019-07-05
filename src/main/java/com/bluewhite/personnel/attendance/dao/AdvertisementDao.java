package com.bluewhite.personnel.attendance.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Advertisement;

public interface AdvertisementDao extends BaseRepository<Advertisement, Long>{
	
	public List<Advertisement> findByType(Integer type);
	
	public List<Advertisement> findByRecruitIdAndType(Long recruitId,Integer type);

}
