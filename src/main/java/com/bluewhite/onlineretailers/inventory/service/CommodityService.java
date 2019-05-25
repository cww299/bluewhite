package com.bluewhite.onlineretailers.inventory.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.Warning;
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
	
	/**
	 * 自动预警
	 */
	public List<Map<String, Object>> checkWarning();
	
	/**
	 * 新增仓库预警
	 * @param warning
	 */
	public Warning saveWarning(Warning warning);
	
	/**
	 * 批量删除仓库预警
	 * @param ids
	 */
	public int deleteWarning(String ids);

}
