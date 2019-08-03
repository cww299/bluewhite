package com.bluewhite.ledger.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.Mixed;

@Service
public interface MixedService extends BaseCRUDService<Mixed, Long> {
	/**
	 * 分頁查看杂项支出
	 * 
	 * @param mixed
	 * @param page
	 * @return
	 */
	public PageResult<Mixed> findPages(Mixed mixed, PageParameter page);
	
	/**
	 * 分頁查看杂项支出
	 * 
	 * @param mixed
	 * @param page
	 * @return
	 */
	public List<Mixed> findList(Bill bill);

	/**
	 * 新增杂支
	 * 
	 * @param mixed
	 */
	public void addMixed(Mixed mixed);

	/**
	 * 删除杂支
	 * 
	 * @param ids
	 */
	public void deleteMixed(String ids);

}
