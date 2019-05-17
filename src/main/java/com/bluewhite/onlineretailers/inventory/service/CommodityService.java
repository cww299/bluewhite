package com.bluewhite.onlineretailers.inventory.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
@Service
public interface CommodityService  extends BaseCRUDService<Commodity,Long>{
	
	
	/**
	 * 分页查看商品
	 * @param onlineOrder
	 * @param page
	 * @return
	 */
	public PageResult<Commodity> findPage(Commodity commodity, PageParameter page);
	/**
	 * 删除商品
	 * @param ids
	 * @return
	 */
	public int deleteCommodity(String ids);

}
