package com.bluewhite.finance.ledger.dao;


import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.ledger.entity.Contact;

public interface ContactDao extends BaseRepository<Contact, Long>{
	
	/**
	 * 通过乙方姓名查找乙方
	 * @param partyNames
	 * @return
	 */
	public Contact findByConPartyNames(String partyNames);
}
