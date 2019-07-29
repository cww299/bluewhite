package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Packing;

public interface PackingService extends BaseCRUDService<Packing, Long>{
	/**
	 * 分页查看贴包单
	 * @param packing
	 * @param page
	 * @return
	 */
	public PageResult<Packing>  findPages(Packing packing, PageParameter page);
	
	/**
	 * 根据当前得到贴包编号
	 * @return
	 */
	public String getPackingNumber();

	/**
	 * 新增贴包单
	 * @param packing
	 * @return
	 */
	public Packing addPacking(Packing packing);
	
	/**
	 * 一键发货
	 * @param ids
	 * @return
	 */
	public int sendPacking(String ids);

}
