package com.bluewhite.production.work.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.work.entity.TaskProcess;

public interface TaskProcessDao extends BaseRepository<TaskProcess, Long> {

	int deleteByTaskAllocationIdIn(List<Long> allocationIds);

}
