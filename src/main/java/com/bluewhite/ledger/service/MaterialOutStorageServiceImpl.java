package com.bluewhite.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.MaterialOutStorage;

@Service
public class MaterialOutStorageServiceImpl extends BaseServiceImpl<MaterialOutStorage, Long> implements MaterialOutStorageService  {

	@Override
	public void saveMaterialOutStorage(MaterialOutStorage materialOutStorage) {
		
	}

	@Override
	public PageResult<MaterialOutStorage> findPages(PageParameter page, MaterialOutStorage materialOutStorage) {
		return null;
	}

	@Override
	public int deleteMaterialOutStorage(String ids) {
		
		return 0;
	}


}
