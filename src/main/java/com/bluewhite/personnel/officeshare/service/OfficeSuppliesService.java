package com.bluewhite.personnel.officeshare.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.officeshare.entity.OfficeSupplies;

public interface OfficeSuppliesService extends BaseCRUDService<OfficeSupplies, Long> {
	
	/**
	 * 分頁查看
	 * @param officeSupplies
	 * @param page
	 * @return
	 */
	public PageResult<OfficeSupplies> findPages(OfficeSupplies officeSupplies, PageParameter page);
	
	/**
	 * 新增修改
	 * @param officeSupplies
	 */
	public void addOfficeSupplies(OfficeSupplies officeSupplies);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public int deleteOfficeSupplies(String ids);

}
