package com.bluewhite.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Mixed;

@Service
public interface MixedService extends BaseCRUDService<Mixed,Long>{
	public PageResult<Mixed>  findPages(Mixed mixed, PageParameter page);
	
	
	public void addMixed(Mixed mixed);
	public void deleteMixed(String ids);
	
}
