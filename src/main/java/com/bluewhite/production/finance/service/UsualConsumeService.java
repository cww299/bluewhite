package com.bluewhite.production.finance.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.finance.entity.UsualConsume;
@Service
public interface UsualConsumeService extends BaseCRUDService<UsualConsume,Long>{
	/**
	 * 调节日常消费系数
	 * @param usualConsume
	 * @return
	 */
	UsualConsume usualConsume(UsualConsume usualConsume);
	/**
	 * 根据条件查询
	 * @param usualConsume
	 * @param page
	 * @return
	 */
	public PageResult<UsualConsume>  findPages(UsualConsume usualConsume, PageParameter page);

}
