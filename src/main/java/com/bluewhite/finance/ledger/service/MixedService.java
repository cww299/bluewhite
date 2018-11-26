package com.bluewhite.finance.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.finance.ledger.entity.Mixed;

@Service
public interface MixedService extends BaseCRUDService<Mixed,Long>{
	public PageResult<Mixed>  findPages(Mixed mixed, PageParameter page);
	
	
	public void addMixed(Mixed mixed);
	
}
