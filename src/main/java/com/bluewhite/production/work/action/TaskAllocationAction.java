package com.bluewhite.production.work.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.production.work.common.TaskConstant;
import com.bluewhite.production.work.entity.TaskAllocation;
import com.bluewhite.production.work.entity.TaskWork;
import com.bluewhite.production.work.service.TaskAllocationService;


/**
 * @author cww299
 * @date 2020/11/17
 */
@Controller
@RequestMapping("/taskAllocation")
public class TaskAllocationAction {

	@Autowired
	private TaskAllocationService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(TaskAllocation.class, "id", "taskId", "userIds", "userNames", "number", "finishNumber", 
						"returnNumber", "status", "surplusNumber", "createdAt", "task")
				.addRetainTerm(TaskWork.class, "taskNumber", "productName", "processesName");
	}
	
	/**
	 * 分页查询任务分配列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse list(TaskAllocation allocation, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(service.findPages(allocation, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 任务分配
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(TaskAllocation allocation) {
		CommonResponse cr = new CommonResponse();
		service.saveTaskAllocation(allocation);
		cr.setMessage("保存成功");
		return cr;
	}
	
	/**
	 * 删除任务分配
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteCustomer(String ids) {
		CommonResponse cr = new CommonResponse();
		int size = service.deleteTaskAllocation(ids);
		cr.setMessage("成功删除： " + size + " 条任务");
		return cr;
	}
	
	/**
	 * 暂停任务
	 */
	@RequestMapping(value = "/pause", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse pause(String ids, String remark) {
		CommonResponse cr = new CommonResponse();
		if (StringUtils.isEmpty(remark)) {
			remark = TaskConstant.REMARK_PAUSE;
		}
		int size = service.startOrPause(ids, remark, TaskConstant.ALLOCATION_PAUSE);
		cr.setMessage("成功暂停： " + size + " 条任务");
		return cr;
	}
	
	/**
	 * 开始任务
	 */
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse start(String ids) {
		CommonResponse cr = new CommonResponse();
		int size = service.startOrPause(ids, TaskConstant.REMARK_START, TaskConstant.ALLOCATION_PROCESS);
		cr.setMessage("成功开始： " + size + " 条任务");
		return cr;
	}
	
	/**
	 * 退回任务
	 */
	@RequestMapping(value = "/returns", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse returns(Long allocationId, int number, String remark) {
		CommonResponse cr = new CommonResponse();
		service.returns(allocationId, number, remark);
		cr.setMessage("成功退回数量： " + number);
		return cr;
	}
	
	/**
	 * 完成任务
	 */
	@RequestMapping(value = "/finish", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse finish(Long allocationId, int number) {
		CommonResponse cr = new CommonResponse();
		service.finish(allocationId, number);
		cr.setMessage("成功完成数量： " + number);
		return cr;
	}
	
	/**
	 * 一键完成任务
	 */
	@RequestMapping(value = "/finishs", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse finishs(String ids) {
		CommonResponse cr = new CommonResponse();
		int size = service.finishs(ids);
		cr.setMessage("成功完成 " + size + " 条任务");
		return cr;
	}
	
	
}
