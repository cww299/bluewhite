package com.bluewhite.production.task.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Controller
public class TaskAction {
	
private static final Log log = Log.getLog(TaskAction.class);
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Task.class,"id","userNames","bacthNumber","allotTime","productName","userIds","procedureName","number","status","expectTime"
						,"expectTaskPrice","taskTime","payB","taskPrice","type","createdAt");
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
//		if(!StringUtils.isEmpty(task.getId())){
//			Task oldTask = taskService.findOne(task.getId());
//			BeanCopyUtils.copyNullProperties(oldTask,task);
//			task.setCreatedAt(oldTask.getCreatedAt());
//			taskService.update(task);
//			cr.setMessage("工序修改成功");
//		}else{
			//新增
			if(!StringUtils.isEmpty(task.getUserIds())){
				if(task.getAllotTime() == null){
					Calendar  cal = Calendar.getInstance();
					cal.add(Calendar.DATE,-1);
					task.setAllotTime(cal.getTime());
				}
				task = taskService.addTask(task);
				cr.setMessage("任务分配成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("领取人不能为空");
			}
//		}
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
		cr.setMessage("查询成功");
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
			taskService.deleteTask(id);
			cr.setMessage("删除成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	
	/**
	 * 查询该任务的所有领取人
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task/taskUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse taskUser(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		if(id!=null){
			Task task = taskService.findOne(id);
			List<User> userList = new ArrayList<User>();
			if (!StringUtils.isEmpty(task.getUserIds())) {
				String[] idArr = task.getUserIds().split(",");
				if (idArr.length>0) {
					for (int i = 0; i < idArr.length; i++) {
						Long userid = Long.parseLong(idArr[i]);
						User user = userService.findOne(userid);
						userList.add(user);
						}
				}
			}
			cr.setData(ClearCascadeJSON.get().addRetainTerm(User.class, "id","userName")
					.format(userList).toJSON());
			cr.setMessage("查询成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHM.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
	

}
