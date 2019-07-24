package com.bluewhite.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Customr;

@Service
public interface CustomrService extends BaseCRUDService<Customr, Long> {

	public PageResult<Customr> findPages(Customr Customr, PageParameter page);

	public int deleteCustomr(String ids);
}
