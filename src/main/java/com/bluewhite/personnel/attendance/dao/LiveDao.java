package com.bluewhite.personnel.attendance.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Live;

public interface LiveDao extends BaseRepository<Live, Long>{
	
	
	/**
	 * 按员工 ID 宿舍ID 类型查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Live> findByUserIdAndHostelIdAndType(Long userId,Long hostelId,Integer type);
	
	/**
	 * 宿舍ID 类型查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Live> findByHostelIdAndType(Long hostelId,Integer type);
	
	/**
	 * 按员工Id查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public Live findByUserIdAndType(Long userId,Integer type);
}
