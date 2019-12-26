package com.bluewhite.production.temporarypack;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.excel.ExcelListener;

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
	
	/**
	 * 批量导入
	 * @param excelListener
	 * @param userId
	 * @param warehouseId
	 * @return
	 */
	public int excelUnderGoods(ExcelListener excelListener);
	
	/**
	 * 条件查询
	 * @param underGoods
	 * @param page
	 * @return
	 */
	public List<UnderGoods> findAll();

}
