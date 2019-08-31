package com.bluewhite.production.task.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.task.entity.Task;
import java.lang.String;

public interface TaskDao  extends BaseRepository<Task, Long>{
	
	/**
	 * 按工序id查找任务
	 * @param id
	 * @return
	 */
	List<Task> findByProcedureId(Long id);
	
	/**
	 * 根据id查询所有任务
	 * @param userids
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@Query(nativeQuery=true,value ="SELECT * FROM pro_task where find_in_set(?1,userIds) AND allot_time BETWEEN ?2 AND ?3")
	List<Task> findByUserIdAndAllotTime(String userid,Date beginTime,Date endTime);
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Task> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);
}
