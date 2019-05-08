package com.bluewhite.system.sys.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.system.sys.entity.RegionAddress;

public interface RegionAddressDao extends BaseRepository<RegionAddress, Long>{

	public List<RegionAddress> findByParentId(Long id);

}
