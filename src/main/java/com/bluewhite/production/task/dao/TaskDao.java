package com.bluewhite.production.task.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.task.entity.Task;

public interface TaskDao  extends BaseRepository<Task, Long>{
	
	/**
	 * 按工序id查找任务
	 * @param id
	 * @return
	 */
	List<Task> findByProcedureId(Long id);

}
