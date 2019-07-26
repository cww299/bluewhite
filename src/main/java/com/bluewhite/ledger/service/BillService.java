//package com.bluewhite.ledger.service;
//
//import org.springframework.stereotype.Service;
//
//import com.bluewhite.base.BaseCRUDService;
//import com.bluewhite.common.entity.PageParameter;
//import com.bluewhite.common.entity.PageResult;
//import com.bluewhite.ledger.entity.Bill;
//import com.bluewhite.ledger.entity.Order;
//
//@Service
//public interface BillService extends BaseCRUDService<Bill,Long>{
//	
//	/**
//	 * 分页查看乙方账单
//	 * @param bill
//	 * @param page
//	 * @return
//	 */
//	public PageResult<Bill> findPages(Bill bill, PageParameter page);
//	
//	
//	/**
//	 *  在新增订单时新增乙方账单，按月份进行对乙方分组
//	 * @param bill
//	 * @param page
//	 * @return
//	 */
//	public Bill addBill(Order order);
//
//	/**
//	 * 
//	 * @param id
//	 * @param time
//	 * @return
//	 */
//	public Object getBillDate(Long id);
//
//	/**
//	 * 修改账单每日详细
//	 * @param bill
//	 * @return
//	 */
//	public Bill updateBill(Bill bill);
//
//	/**
//	 * 汇总账单
//	 * @param bill
//	 * @return
//	 */
//	public Bill collectBill(Bill bill);
//	
//
//}
