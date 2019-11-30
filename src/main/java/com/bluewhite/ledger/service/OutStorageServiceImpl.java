package com.bluewhite.ledger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.OutStorageDao;
import com.bluewhite.ledger.entity.OutStorage;

@Service
public class OutStorageServiceImpl extends BaseServiceImpl<OutStorage, Long> implements OutStorageService {
	
	@Autowired
	private OutStorageDao dao;

	@Override
	public void saveOutStorage(OutStorage outStorage) {
		save(outStorage);
	}

	@Override
	public int deleteOutStorage(String ids) {
		return delete(ids);
	}

	@Override
	public PageResult<OutStorage> findPages(PageParameter page, OutStorage outStorage) {
		// TODO Auto-generated method stub
		return null;
	}

}
