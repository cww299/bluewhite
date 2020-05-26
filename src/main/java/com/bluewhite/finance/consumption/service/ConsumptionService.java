package com.bluewhite.finance.consumption.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.finance.consumption.entity.Consumption;

public interface ConsumptionService extends BaseCRUDService<Consumption, Long> {

	/**
	 * 分页查看
	 * 
	 * @param expenseAccount
	 * @param page
	 * @return
	 */
	PageResult<Consumption> findPages(Consumption consumption, PageParameter page);
	
	/**
	 * 按条件查看
	 * 
	 * @param expenseAccount
	 * @param page
	 * @return
	 */
	List<Consumption> findList(Consumption consumption);

	/**
	 * 新增or修改
	 * 
	 * @param expenseAccount
	 * @return
	 */
	public Consumption addConsumption(Consumption consumption);

	/**
	 * 删除
	 * 
	 * @param expenseAccount
	 * @return
	 */
	public int deleteConsumption(String ids);

	/**
	 * 财务审核放款
	 * 
	 * @param expenseAccount
	 * @return
	 */
	public int auditConsumption( String ids, Integer flag);
	
	/**
	 * 人事部汇总报销金额
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 * @throws Exception
	 */
	Map<String,Object> countConsumptionMoney(Consumption consumption);
	/**
	 * 导入订单
	 * @param excelListener
	 * @return
	 */
	int excelAddConsumption(ExcelListener excelListener, Integer type);
	
	/**
	 * 计算财务未付款总额
	 * @return
	 */
	double totalAmount(Consumption consumption);
	
	 /**
     * 根据客户id和类型和申请时间查出所有的
     * 
     * @param type
     * @param flag
     * @param flag1
     * @return
     */
    public Consumption findByTypeAndLogisticsIdAndExpenseDateBetween(Integer type, Long id, Date beginTime,
        Date endTime);

    /**
     * 根据发货单
     * @param id
     * @return
     */
    public Consumption findBySendOrderId(Long id);
}
