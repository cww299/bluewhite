package com.bluewhite.production.finance.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.production.finance.entity.CollectInformation;
@Service
public interface CollectInformationService extends BaseCRUDService<CollectInformation,Long>{
	
	/**
	 * 按条件汇总生产费用所有数据
	 * @param collectPay
	 * @return
	 */
	public CollectInformation collectInformation(CollectInformation collectInformation);
	
	/**
	 * 保存部门支出
	 * @param collectInformation
	 * @return
	 */
	public CollectInformation savaDepartmentalExpenditure(CollectInformation collectInformation);
	
	/**
	 * 根据类型查找
	 * @param collectInformation
	 * @return
	 */
	public CollectInformation findByType(CollectInformation collectInformation);

}
