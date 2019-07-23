package com.bluewhite.ledger.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.SendGoods;

public interface SendGoodsService extends BaseCRUDService<SendGoods,Long>{

	/**
	 * 分页查看发货单
	 * @param packing
	 * @param page
	 * @return
	 */
	public PageResult<SendGoods>  findPages(SendGoods sendGoods, PageParameter page);
	
	/**
	 * 新增发货单
	 * @param sendGoods
	 */
	public SendGoods addSendGoods(SendGoods sendGoods);
	/**
	 * 查看发货单
	 * @param packing
	 * @param page
	 * @return
	 */
	public List<SendGoods> findLists(SendGoods sendGoods);

}
