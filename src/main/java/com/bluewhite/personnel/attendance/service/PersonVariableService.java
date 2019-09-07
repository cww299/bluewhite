package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.PersonVariable;

public interface PersonVariableService  extends BaseCRUDService<PersonVariable,Long>{

	/**
	 * 按条件查看的人事基础数据
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<PersonVariable> findPersonVariablePage(PersonVariable personVariable, PageParameter page);

}
