package com.bluewhite.production.finance.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.PayB;
@Service
public interface PayBService extends BaseCRUDService<PayB,Long>{
	/**
	 * 分页查询b工资流水
	 * @param payB
	 * @param page
	 * @return
	 */
	public PageResult<PayB> findPages(PayB payB, PageParameter page);
	
	/**
	 * 汇总后的各项工资
	 * @param collectPay
	 * @return
	 */
	public CollectPay collectPay(CollectPay collectPay);

}
