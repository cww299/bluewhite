package com.bluewhite.production.work.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.work.entity.TaskDeliver;

public interface TaskDeliverDao  extends BaseRepository<TaskDeliver, Long>{

	void deleteByTaskIdIn(List<Long> taskIds);

}
