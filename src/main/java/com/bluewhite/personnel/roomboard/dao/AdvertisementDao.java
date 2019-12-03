package com.bluewhite.personnel.roomboard.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Advertisement;

public interface AdvertisementDao extends BaseRepository<Advertisement, Long>{
	
	public List<Advertisement> findByType(Integer type);
	
	public List<Advertisement> findByTypeAndMold(Integer type,Integer mold);
	
	public List<Advertisement> findByRecruitIdAndType(Long recruitId,Integer type);
	
	public List<Advertisement> findByRecruitIdAndTypeAndMold(Long recruitId,Integer type,Integer mold);
	
	public List<Advertisement> findByOrgNameIdAndType(Long orgNameId,Integer type);

}
