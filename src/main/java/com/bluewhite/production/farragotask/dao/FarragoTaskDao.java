package com.bluewhite.production.farragotask.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.farragotask.entity.FarragoTask;

public interface FarragoTaskDao extends BaseRepository<FarragoTask, Long>{
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FarragoTask> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);
	
	
	/**
	 * 根据id查询所有任务
	 * @param userids
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@Query(nativeQuery=true,value ="SELECT * FROM pro_farrago_task where find_in_set(?1,ids) AND allot_time BETWEEN ?2 AND ?3")
	List<FarragoTask> findInSetIds(String ids,Date beginTime,Date endTime);
	
	/**
	 * 根据id查询所有任务
	 * @param userids
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@Query(nativeQuery=true,value ="SELECT * FROM pro_farrago_task where find_in_set(?1,temporaryIds) AND allot_time BETWEEN ?2 AND ?3")
	List<FarragoTask> findInSetTemporaryIds(String temporaryIds,Date beginTime,Date endTime);

}
