package com.bluewhite.personnel.attendance.dao;

import java.util.Date;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.PersonVariable;

public interface PersonVariableDao  extends BaseRepository<PersonVariable, Long>{
	
	/**
	 * 根据类型查找人事基础数据
	 * @param type
	 * @return
	 */
	PersonVariable findByType(Integer type);
	
	/**
	 * 根据类型日期查找人事基础数据
	 * @param type
	 * @param startTime
	 * @return
	 */
	PersonVariable findByTypeAndTime(Integer type,Date startTime);
	
}
