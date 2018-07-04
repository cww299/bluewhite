package com.bluewhite.production.finance.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.finance.entity.FarragoTaskPay;
@Service
public interface FarragoTaskPayService  extends BaseCRUDService<FarragoTaskPay,Long>{

	public PageResult<FarragoTaskPay>  findPages(FarragoTaskPay farragoTaskPay, PageParameter page);

}
