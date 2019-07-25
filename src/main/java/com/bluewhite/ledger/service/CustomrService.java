package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Customr;

public interface CustomrService extends BaseCRUDService<Customr, Long> {

	/**
	 * 分页查看
	 * @param Customr
	 * @param page
	 * @return
	 */
	public PageResult<Customr> findPages(Customr Customr, PageParameter page);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteCustomr(String ids);
}
