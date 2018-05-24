package com.bluewhite.production.task.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;

@Controller
public class TaskAction {
	
private static final Log log = Log.getLog(TaskAction.class);
	
	@Autowired
	private TaskService taskService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Task.class,"id","userNames","bacth","productName","userIds","procedureName","number","status","expectTime"
						,"taskTime","BPrice","type")
				.addRetainTerm(Bacth.class,"id","bacthNumber");
	}
	
	/**
	 * 给批次添加任务（修改）
	 * 
	 * 
	 */
	@RequestMapping(value = "/task/addTask", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTask(HttpServletRequest request,Task task) {
		CommonResponse cr = new CommonResponse();
		//修改
		if(!StringUtils.isEmpty(task.getId())){
			Task oldTask = taskService.findOne(task.getId());
			BeanCopyUtils.copyNullProperties(oldTask,task);
			task.setCreatedAt(oldTask.getCreatedAt());
			taskService.update(task);
			cr.setMessage("工序修改成功");
		}else{
			//新增
			if(!StringUtils.isEmpty(task.getUserIds())){
				task = taskService.addTask(task);
				cr.setMessage("任务分配成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("领取人不能为空");
			}
		}
		return cr;
	}
	
	/** 
	 * 分页查询所有任务
	 * 
	 */
	@RequestMapping(value = "/task/allTask", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allTask(HttpServletRequest request,Task task,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(taskService.findPages(task, page)).toJSON());
		cr.setMessage("添加成功");
		return cr;
	}
	
	
	/**
	 * 删除任务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		if(id!=null){
			taskService.delete(id);
			cr.setMessage("删除成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}
	

}
