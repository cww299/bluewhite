package com.bluewhite.production.group.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.group.entity.Temporarily;

public interface TemporarilyDao extends BaseRepository<Temporarily, Long>{
	/**
	 * 根据类型查询
	 * @param type
	 * @return
	 */
	public List<Temporarily> findByType(Integer type);
	/**
	 * 根据员工id和日期
	 * @param userId
	 * @param getfristDayOftime
	 * @return
	 */
	public Temporarily findByUserIdAndTemporarilyDate(Long userId, Date getfristDayOftime);
	/**
	 * 根据日期和类型查找
	 * @param type
	 * @param temporarilyDate
	 * @return
	 */
	public List<Temporarily> findByTypeAndTemporarilyDate(Integer type, Date temporarilyDate);
	/**
	 * 根据日期和类型和分组id查找
	 * @param type
	 * @param temporarilyDate
	 * @return
	 */
	public List<Temporarily> findByTypeAndTemporarilyDateAndGroupId(Integer type, Date temporarilyDate, Long id);
	

}
