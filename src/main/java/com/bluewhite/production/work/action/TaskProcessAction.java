package com.bluewhite.production.work.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.production.work.entity.TaskProcess;
import com.bluewhite.production.work.service.TaskProcessService;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Controller
@RequestMapping("/taskProcess")
public class TaskProcessAction {

	@Autowired
	private TaskProcessService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(TaskProcess.class, "id", "taskId", "taskAllocationId", "Type", "number", "timeMin", 
						"remark", "createdAt", "userNames");
	}
	
	/**
	 * 分页查询任务分配列表
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allBacth(Long taskId, Long taskAllocationId) {
		if (taskId == null && taskAllocationId == null) {
			throw new ServiceException("参数错误, 任务id或者任务分配id不能同时为空！");
		}
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(service.getAll(taskId, taskAllocationId)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
}
