package com.bluewhite.system.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.system.sys.dao.RegionAddressDao;
import com.bluewhite.system.sys.entity.RegionAddress;

@Service
public class RegionAddressServiceImpl extends BaseServiceImpl<RegionAddress, Long> implements RegionAddressService{

	@Autowired
	private RegionAddressDao dao;
	
	
	@Override
	public List<RegionAddress> queryProvince(Long parentId) {
		return dao.findByParentId(parentId);
	}

}
