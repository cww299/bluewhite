package com.bluewhite.production.work.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.production.work.entity.TaskDeliver;
import com.bluewhite.production.work.service.TaskDeliverService;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Controller
@RequestMapping("/taskDeliver")
public class TaskDeliverAction {

	@Autowired
	private TaskDeliverService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(TaskDeliver.class, "id", "taskId", "createdAt", "deliverDate", "number", "remark",
						"productName", "taskNumber");
	}
	
	/**
	 * 分页查询任务分配列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse list(TaskDeliver deliver, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(service.findPages(deliver, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse save(TaskDeliver deliver) {
		CommonResponse cr = new CommonResponse();
		service.saveDeliver(deliver);
		cr.setMessage("保存成功");
		return cr;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(String ids) {
		CommonResponse cr = new CommonResponse();
		int size = service.delete(ids);
		cr.setMessage("保存删除" + size + " 条数据");
		return cr;
	}
	
	/**
	 * 预警
	 */
	@RequestMapping(value = "/warn", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse warn() {
		CommonResponse cr = new CommonResponse();
		cr.setData(service.warnList(null, null).getTotal());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 预警分页列表
	 */
	@RequestMapping(value = "/warnList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse warnList(TaskDeliver deliver, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(service.warnList(deliver, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
}
