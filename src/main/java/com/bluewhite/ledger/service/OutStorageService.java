package com.bluewhite.ledger.service;

import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.OutStorage;

public interface OutStorageService  extends BaseCRUDService<OutStorage,Long>{
	
	/**
	 * 生成出库单
	 * @param outStorage
	 */
	public void saveOutStorage(OutStorage outStorage);
	
	/**
	 * 删除出库单
	 * @param ids
	 * @return
	 */
	public int deleteOutStorage(String ids);
	
	/**
	 * 分页查看出库单
	 * @param page
	 * @param outStorage
	 * @return
	 */
	public PageResult<OutStorage> findPages(PageParameter page, OutStorage outStorage);
	
	/**
	 * 对发货单进行出库
	 * @param ids
	 */
	public void sendOutStorage(Long id,Integer sendNumber,String putStorage,Integer flag);
	
	/**
	 * 根据发货单获取库存详情
	 * @param id
	 */
	public List<Map<String, Object>> getSendPutStorage(Long id);
	
	/**
	 * 根据加工单获取皮壳库存详情
	 * @param id
	 * @return
	 */
	public Object getOrderOutSourcePutStorageDetails(Long id);
	
	/**
	 * 根据加工单单查询出库单
	 * @param orderoutsourceid
	 * @return
	 */
	List<OutStorage> findByOrderOutSourceId(Long orderoutsourceid);
	
}
