package com.bluewhite.production.finance.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.production.finance.entity.PayB;
@Service
public interface CollectPayService extends BaseCRUDService<CollectPay,Long>{

	public PageResult<CollectPay> findPages(CollectPay collect, PageParameter page);
	/**
	 * 按条件汇总员工绩效
	 * @param collectPay
	 */
	public List<CollectPay> collect(CollectPay collectPay);

}
