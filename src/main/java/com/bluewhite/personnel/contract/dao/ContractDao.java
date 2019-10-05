package com.bluewhite.personnel.contract.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.contract.entity.Contract;

public interface ContractDao extends BaseRepository<Contract, Long> {
	
	
	/**
	 * 有效的合同
	 * @param flag
	 * @return
	 */
	List<Contract> findByFlag(Integer flag);
	

}
