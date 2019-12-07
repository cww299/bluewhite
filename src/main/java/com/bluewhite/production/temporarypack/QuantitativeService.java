package com.bluewhite.production.temporarypack;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;

public interface QuantitativeService extends BaseCRUDService<Quantitative,Long>{
	
	/**
	 * 分页
	 * @param quantitative
	 * @param page
	 * @return
	 */
	PageResult<Quantitative> findPages(Quantitative quantitative, PageParameter page);
	
	/**
	 * 新增
	 * @param quantitative
	 */
	public void saveQuantitative(Quantitative quantitative);

	/**
	 * 发货
	 * @param ids
	 * @return
	 */
	int auditQuantitative(String ids);
	
	/**
	 * 打印
	 * @param ids
	 * @return
	 */
	int printQuantitative(String ids);
	
	/**
	 * 
	 * @param ids
	 */
	int deleteQuantitative(String ids);

	

}
