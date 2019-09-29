package com.bluewhite.personnel.contract.service;

import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.contract.entity.Contract;

public interface ContractService extends BaseCRUDService<Contract,Long>{
	
	
	/**
	 * 分页查看
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Contract> findContractPage(Contract contract, PageParameter page);
	
	
	/**
	 * 新增修改合同
	 * @param contract
	 */
	public void addContract(Contract contract);
	
	/**
	 * 删除
	 */
	public int deleteContract(String ids);

	
	/**
	 * 合同到期提醒
	 * @return
	 */
	public List<Map<String,Object>> remindContract();
}
