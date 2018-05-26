package com.bluewhite.production.farragotask.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.task.entity.Task;

@Service
public interface FarragoTaskService extends BaseCRUDService<FarragoTask,Long>{

	public PageResult<FarragoTask> findPages(FarragoTask farragoTask, PageParameter page);


}
