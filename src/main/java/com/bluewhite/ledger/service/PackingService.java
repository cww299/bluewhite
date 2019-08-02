package com.bluewhite.ledger.service;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;

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
	public String getPackingNumber(Date time);

	/**
	 * 新增贴包单
	 * @param packing
	 * @return
	 */
	public Packing addPacking(Packing packing);
	
	/**
	 * 一键发货(发货时，加上版权和调拨批次)
	 * @param ids
	 * @return
	 */
	public int sendPacking(String ids,Date time);
	
	/**
	 * 查看实际发货单
	 * @param packingChild
	 * @param page
	 * @return
	 */
	public PageResult<PackingChild> findPackingChildPage(PackingChild packingChild, PageParameter page);
	
	/**
	 * 根据产品和客户查找以往价格
	 * @param page
	 * @param packingChild
	 * @return
	 */
	public List<PackingChild> getPackingChildPrice(PackingChild packingChild);
	
	/**
	 * 删除贴包单
	 * @param ids
	 * @return
	 */
	public int deletePacking(String ids);
	
	/**
	 * 删除贴包子单
	 * @param ids
	 * @return
	 */
	public int deletePackingChild(String ids);
	
	/**
	 * 修改贴包子单（实际发货单）
	 * @param packingChild
	 * @return
	 */
	public PackingChild updatePackingChild(PackingChild packingChild);
	
	/**
	 * 删除包装材料子单
	 * @param ids
	 * @return
	 */
	public int deletePackingPackingMaterials(String ids);

}
