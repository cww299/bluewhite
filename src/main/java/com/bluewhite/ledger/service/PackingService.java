package com.bluewhite.ledger.service;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Bill;
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
	 * 查看实际发货单
	 * @param packingChild
	 * @param page
	 * @return
	 */
	public List<PackingChild> findPackingChildList(Bill bill);
	
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
	 * 修改贴包子单（财务）
	 * @param packingChild
	 * @return
	 */
	public PackingChild updateFinancePackingChild(PackingChild packingChild);

	/**
	 * 修改贴包子单（业务员）
	 * @param packingChild
	 * @return
	 */
	public PackingChild updateUserPackingChild(PackingChild packingChild);
	
	/**
	 * 删除包装材料子单
	 * @param ids
	 * @return
	 */
	public int deletePackingMaterials(String ids);

	/**
	 * 审核贴包子单（实际发货单）(同时进行货物账单的生成和统计)
	 * @param packingChild
	 * @return
	 */
	public int auditPackingChild(String ids,Integer audit);
	
	/**
	 * 汇总账单
	 * @param packingChild
	 */
	public List<Bill> collectBill(Bill bill);

	/**
	 * 确认收货单
	 * @param ids
	 * @param deliveryStatus
	 * @return
	 */
	public int auditUserPackingChild(String ids, Integer deliveryStatus);
	
	/**
	 * 库管确认调拨单入库数量
	 * @param ids
	 * @return
	 */
	public int confirmPackingChild(String ids);

	public PackingChild updateInventoryPackingChild(PackingChild packingChild);


}
