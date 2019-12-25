package com.bluewhite.ledger.service;

import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.ProcessPrice;
import com.bluewhite.ledger.entity.PutStorage;

public interface OrderOutSourceService extends BaseCRUDService<OrderOutSource, Long> {

	/**
	 * 新增加工单
	 * 
	 * @param orderOutSource
	 */
	public void saveOrderOutSource(OrderOutSource orderOutSource);

	/**
	 * 分页查看加工单
	 * 
	 * @param order
	 * @param page
	 * @return
	 */
	public PageResult<OrderOutSource> findPages(OrderOutSource orderOutSource, PageParameter page);

	/**
	 * 删除加工单
	 * 
	 * @param orderOutSource
	 */
	public int deleteOrderOutSource(String ids);

	/**
	 * 修改加工单
	 * 
	 * @param orderOutSource
	 */
	public void updateOrderOutSource(OrderOutSource orderOutSource);

	/**
	 * 审核加工单
	 * 
	 * @param ids
	 * @return
	 */
	public int auditOrderOutSource(String ids);

	/**
	 * （1.成品仓库，2.皮壳仓库）修改加工单
	 * 
	 * @param orderOutSource
	 */
	public void updateInventoryOrderOutSource(OrderOutSource orderOutSource);

	/**
	 * 生成外发加工单账单
	 * 
	 * @param orderOutSource
	 */
	public void saveOutSoureBills(OrderOutSource orderOutSource);

	/**
	 * 将外发加工单和退货单糅合，得出该工序的实际任务数量，进行账单的生成
	 * 
	 * @param id
	 */
	public List<Map<String, Object>> mixOutSoureRefund(Long id);

	/**
	 * 对工序价值进行新增或者修改
	 * 
	 * @param id
	 */
	public void updateProcessPrice(ProcessPrice processPrice);

	/**
	 * 对领料单的工序任务数量进行整合
	 * 
	 * @param id
	 */
	public List<Map<String, Object>> getProcessNumber(Long id);

	/**
	 * 加工单工序对应的价格列表
	 */
	public PageResult<ProcessPrice> processNumberPage(ProcessPrice processPrice, PageParameter page);

}
