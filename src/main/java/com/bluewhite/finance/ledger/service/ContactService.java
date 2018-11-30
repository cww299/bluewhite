package com.bluewhite.finance.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.entity.Contact;

@Service
public interface ContactService extends BaseCRUDService<Contact,Long>{
	public PageResult<Contact>  findPages(Contact contact, PageParameter page);
	
	
	public void addContact(Contact contact);
	public void deleteContact(String ids);
}
