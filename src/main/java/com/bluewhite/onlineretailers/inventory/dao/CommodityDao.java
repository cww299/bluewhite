package com.bluewhite.onlineretailers.inventory.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;

public interface CommodityDao extends BaseRepository<Commodity, Long>{

	Commodity findByskuCode(String name);

	Commodity findByProductId(Long id);
	
	List<Commodity> findByProductIdIsNull();
}
