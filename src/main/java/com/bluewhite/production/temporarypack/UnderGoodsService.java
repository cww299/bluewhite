package com.bluewhite.production.temporarypack;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.bacth.entity.Bacth;

public interface UnderGoodsService extends BaseCRUDService<UnderGoods,Long>{

	/**
	 * 分页查询
	 * @param underGoods
	 * @param page
	 * @return
	 */
	public PageResult<UnderGoods> findPages(UnderGoods underGoods, PageParameter page);
	
	/**
	 * 新增下货单
	 * @param underGoods
	 */
	public void saveUnderGoods(UnderGoods underGoods);

}
