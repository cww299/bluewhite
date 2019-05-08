package com.bluewhite.system.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.system.sys.entity.RegionAddress;

public interface RegionAddressService extends BaseCRUDService<RegionAddress, Long>{

	public List<RegionAddress> queryProvince(Long parentId);

}
