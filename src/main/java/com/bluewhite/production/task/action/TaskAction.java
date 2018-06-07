package com.bluewhite.production.task.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
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
				.addRetainTerm(Task.class,"id","userNames","bacthNumber","allotTime","productName","userIds","procedure","procedureName","number","status","expectTime"
						,"expectTaskPrice","taskTime","payB","taskPrice","type","createdAt","performance","performanceNumber","performancePrice","flag")
				.addRetainTerm(Procedure.class,"id","procedureTypeId");
	}
	
	
	/**
	 * 获取任务加绩类型列表
	 */
	@RequestMapping(value = "/task/taskPerformance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse taskPerformance(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		List<Map<String,Object>> mapList= ProTypeUtils.taskPerformance();
		cr.setData(mapList);
		cr.setMessage("查询成功");
		return cr;
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
			//新增
			if(!StringUtils.isEmpty(task.getUserIds())){
				if(task.getAllotTime() == null && task.getType() == 1){
					Calendar  cal = Calendar.getInstance();
					cal.add(Calendar.DATE,-1);
					task.setAllotTime(cal.getTime());
				}else{
					task.setAllotTime(new Date());
				}
				task = taskService.addTask(task);
				cr.setMessage("任务分配成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("领取人不能为空");
			}
		return cr;
	}
	
	
	
	/**
	 * 给批次添加任务(方式2)
	 * 应业务要求，增加按时间占比，分配不同任务数量给不同的员工，进行新增任务
	 * 
	 */
	@RequestMapping(value = "/task/addTaskTwo", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTaskTwo(HttpServletRequest request,Task task) {
		CommonResponse cr = new CommonResponse();
			//根据时间占比，组装出新任务
			List<Task> taskList = taskService.assembleTask(task);
			//新增
			for(Task tasks : taskList){
				if(!StringUtils.isEmpty(tasks.getUserIds())){
					if(tasks.getAllotTime() == null ){
						Calendar  cal = Calendar.getInstance();
						cal.add(Calendar.DATE,-1);
						tasks.setAllotTime(cal.getTime());
					}
					taskService.addTask(tasks);
					cr.setMessage("任务分配成功");
				}else{
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("领取人不能为空");
				}
			}
		return cr;
	}
	
	
	/**
	 *	2楼环境，记录任务实际完成时间（暂停开始）
	 * 
	 * 
	 */
	@RequestMapping(value = "/task/getTaskActualTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getTaskActualTime(HttpServletRequest request,String ids,Integer status) {
		CommonResponse cr = new CommonResponse();
			if(!StringUtils.isEmpty(ids)){
				if (!StringUtils.isEmpty(ids)) {
					String[] idArr = ids.split(",");
					if (idArr.length>0) {
						for (int i = 0; i < idArr.length; i++) {
							Long id = Long.parseLong(idArr[i]);
							Task task = taskService.findOne(id);
							if(task.getStatus()==1){
								cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
								cr.setMessage("任务编号为"+task.getId()+"的任务已经结束，无法开始或暂停");
								return cr;
							}else if(task.getStatus()==null){
								cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
								cr.setMessage("任务编号为"+task.getId()+"的任务未开始，无法暂停，请先开始任务");
								return cr;
							}
							taskService.getTaskActualTime(id,status);
						}
					}
				}
					if(status==0){
						cr.setMessage("开始成功");
					}else{
						cr.setMessage("暂停成功");
					}
				}else{
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("领取人不能为空");
				}
		return cr;
	}
	
	
	
	/**
	 *	2楼环境，需要实时获取任务时间，通过结束状态进行任务及B工资的修改
	 * （批量结束）
	 * 
	 */
	@RequestMapping(value = "/task/updateTask", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateTask(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
			if(!StringUtils.isEmpty(ids)){
					int count = taskService.updateTask(ids);
					cr.setMessage("成功结束"+count+"条任务");
				}else{
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("任务不能为空");
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
	public CommonResponse delete(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if(!StringUtils.isEmpty(ids)){
			taskService.deleteTask(ids);
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
				DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
	

}
