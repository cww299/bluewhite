package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Customr;

public class CustomrServiceImpl extends BaseServiceImpl<Customr, Long> implements CustomrService{

	@Override
	public PageResult<Customr> findPages(Customr Customr, PageParameter page) {
		return null;
	}

	@Override
	public int deleteCustom(String ids) {
		return 0;
	}

	

}
