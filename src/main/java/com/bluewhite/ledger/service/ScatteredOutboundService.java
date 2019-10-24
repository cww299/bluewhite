package com.bluewhite.ledger.service;

import java.util.Date;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.ScatteredOutbound;

public interface ScatteredOutboundService extends BaseCRUDService<ScatteredOutbound,Long> {
	
	/**
	 * 将耗料单转换成分散出库单，新增分散出库单
	 * @param ids
	 */
	public int saveScatteredOutbound(String ids);
	
	/**
	 * 分页查看
	 * @param scatteredOutbound
	 * @param page
	 * @return
	 */
	public PageResult<ScatteredOutbound> findPages(ScatteredOutbound scatteredOutbound, PageParameter page);
	
	/**
	 * 删除出库单
	 * @param ids
	 * @return
	 */
	public int deleteScatteredOutbound(String ids);
	
	/**
	 * 审核出库单
	 * @param ids
	 * @return
	 */
	public int auditScatteredOutbound(String ids,Date time);

	/**
	 * 修改出库单
	 * @param ids
	 * @return
	 */
	public void updateScatteredOutbound(ScatteredOutbound scatteredOutbound);
	
	/**
	 * 生成下单表
	 * @param ids
	 * @return
	 */
	public int generatePlaceOrder(String ids);
	/**
	 * 修改出库单
	 * @param ids
	 * @return
	 */
	public void updatePlaceOrder(ScatteredOutbound scatteredOutbound);
}
