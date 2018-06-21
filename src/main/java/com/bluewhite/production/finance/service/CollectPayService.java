package com.bluewhite.production.finance.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.MonthlyProduction;
@Service
public interface CollectPayService extends BaseCRUDService<CollectPay,Long>{

	public PageResult<CollectPay> findPages(CollectPay collect, PageParameter page);
	/**
	 * 按条件汇总员工绩效
	 * @param collectPay
	 */
	public List<CollectPay> collect(CollectPay collectPay);
	
	/**
	 * 按条件汇总生产费用所有数据
	 * @param collectPay
	 * @return
	 */
	public CollectInformation collectInformation(CollectInformation collectInformation);
	
	
	/**
	 * 质检月产量报表
	 * @param collectInformation
	 * @return
	 */
	public List<MonthlyProduction> monthlyProduction(MonthlyProduction monthlyProduction);
	
	
	/**
	 * 计算各组人员考情时间和B工资+杂工的汇总
	 * @param monthlyProduction
	 * @return
	 */
	public List<Map<String,Object>> bPayAndTaskPay(MonthlyProduction monthlyProduction);
	
	/**
	 * 获取非一线人员的绩效汇总表，每个组的男女组长，按月
	 * @param monthlyProduction
	 * @return
	 */
	public Object headmanPay(MonthlyProduction monthlyProduction);
	
	
	/**
	 * 查询出二楼整个月的绩效
	 * @param collectPay
	 * @return
	 */
	public List<CollectPay> twoPerformancePay(CollectPay collectPay);
	
	
	/**
	 * 根据系数进行调节
	 * @param collectPay
	 * @return
	 */
	public CollectPay upadtePerformancePay(CollectPay collectPay);
	


}
