package com.bluewhite.onlineretailers.inventory.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineCustomer;
@Service
public interface OnlineCustomerService extends BaseCRUDService<OnlineCustomer,Long>{

	/**
	 * 分页查看客户
	 * @param onlineOrder
	 * @param page
	 * @return
	 */
	public PageResult<OnlineCustomer> findPage(OnlineCustomer OnlineCustomer, PageParameter page);

	/**
	 * 删除客户
	 * @param ids
	 * @return
	 */
	public int deleteOnlineCustomer(String ids);

}
