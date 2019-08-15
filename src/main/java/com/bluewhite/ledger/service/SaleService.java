package com.bluewhite.ledger.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.Sale;

public interface SaleService  extends BaseCRUDService<Sale,Long>{
	
	/**
	 * 查看销售单分页
	 * @param Sale
	 * @param page
	 * @return
	 */
	public PageResult<Sale> findSalePage(Sale sale, PageParameter page);
	
	/**
	 * 查看销售单
	 * @param packingChild
	 * @param page
	 * @return
	 */
	public List<Sale> findSaleList(Bill bill);
	
	/**
	 * 修改销售单（财务）
	 * @param sale
	 * @return
	 */
	public void updateFinanceSale(Sale sale);

	/**
	 * 修改销售单（业务员）
	 * @param sale
	 * @return
	 */
	public void updateUserSale(Sale sale);
	
	/**
	 * 审核销售单(同时进行货物账单的生成和统计)
	 * @param packingChild
	 * @return
	 */
	public int auditSale(String ids,Integer audit);
	
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
	public int auditUserSale(String ids, Integer deliveryStatus);
	
	/**
	 * 根据产品和客户查找以往价格
	 * @param page
	 * @param packingChild
	 * @return
	 */
	public List<Sale> getSalePrice(Sale sale);

}
