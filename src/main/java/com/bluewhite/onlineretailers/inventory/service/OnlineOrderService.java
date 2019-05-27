package com.bluewhite.onlineretailers.inventory.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.onlineretailers.inventory.entity.OnlineCustomer;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;
@Service
public interface OnlineOrderService extends BaseCRUDService<OnlineOrder,Long>{
	
	/**
	 * 分页查看销售单
	 * @param onlineOrder
	 * @param page
	 * @return
	 */
	public PageResult<OnlineOrder> findPage(OnlineOrder onlineOrder, PageParameter page);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public int deleteOnlineOrder(String ids);
	
	
	/**
	 * 新增销售单
	 * @param onlineOrder
	 */
	public OnlineOrder addOnlineOrder(OnlineOrder onlineOrder);

	/**
	 * 一键发货销售单
	 * @param onlineOrder
	 */
	public int delivery(String delivery);
	
	/**
	 * 导入销售单
	 * @param excelListener
	 * @return
	 */
	public int excelOnlineOrder(ExcelListener excelListener);
	
	/**
	 * 根据时间和类型获取销售报表
	 * @param type
	 * @param date
	 * @param beginTime
	 * @return
	 */
	List<Map<String, Object>> reportSales(OnlineOrder onlineOrder);
	
	/**
	 * 根据时间和类型获取销售子报表
	 * @param type
	 * @param date
	 * @param beginTime
	 * @return
	 */
	List<OnlineOrderChild> reportSalesChild(OnlineOrderChild onlineOrderChild);

}
