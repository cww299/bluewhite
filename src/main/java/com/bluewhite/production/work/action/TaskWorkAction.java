package com.bluewhite.production.work.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.production.work.entity.TaskWork;
import com.bluewhite.production.work.service.TaskWorkService;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Controller
@RequestMapping("/tasks")
public class TaskWorkAction {
	
	@Autowired
	private TaskWorkService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(TaskWork.class, "id", "taskNumber", "type", "productId", "productName", "processesId", 
						"processesName", "timeMin", "currTimeMin", "number", "finishNumber", "allocationNumber",
						"deliverNumber", "deliverDate", "status", "timeSecond", "surplusNumber", "createdAt", "remark" );
	}
	
	/**
	 * 分页查询任务列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allBacth(TaskWork task, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(service.findPages(task, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 保存任务
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(TaskWork task) {
		CommonResponse cr = new CommonResponse();
		service.saveTask(task);
		cr.setMessage("保存成功");
		return cr;
	}
	
	/**
	 * 删除任务
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteCustomer(String ids) {
		CommonResponse cr = new CommonResponse();
		int size = service.deleteTask(ids);
		cr.setMessage("成功删除： " + size + " 条任务");
		return cr;
	}
	
}
