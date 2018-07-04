package com.bluewhite.production.farragotask.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Controller
public class FarragoTaskAction {
	
private static final Log log = Log.getLog(FarragoTaskAction.class);
	
	@Autowired
	private FarragoTaskService farragoTaskService;
	
	@Autowired
	private UserService userService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(FarragoTask.class,"id","bacth","name","price","time","allotTime","userIds",
						"performance","performanceNumber","performancePrice","remarks");
	}
	
	/**
	 * 获取杂工加绩类型列表
	 */
	@RequestMapping(value = "/farragoTask/farragoTaskPerformance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse farragoTaskPerformance(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		List<Map<String,Object>> mapList= ProTypeUtils.farragoTaskPerformance();
		cr.setData(mapList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 添加杂工任务（修改）
	 * 
	 * 
	 */
	@RequestMapping(value = "/farragoTask/addFarragoTask", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addFarragoTask(HttpServletRequest request,FarragoTask farragoTask) {
		CommonResponse cr = new CommonResponse();
		//修改
		if(!StringUtils.isEmpty(farragoTask.getId())){
			FarragoTask oldTask = farragoTaskService.findOne(farragoTask.getId());
			BeanCopyUtils.copyNullProperties(oldTask,farragoTask);
			farragoTask.setCreatedAt(oldTask.getCreatedAt());
			farragoTaskService.update(farragoTask);
			cr.setMessage("新增成功");
		}else{
			//新增
			if(!StringUtils.isEmpty(farragoTask.getUserIds())){
				farragoTask.setAllotTime(ProTypeUtils.countAllotTime(farragoTask.getAllotTime(), farragoTask.getType()));
				farragoTaskService.addFarragoTask(farragoTask);
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
	@RequestMapping(value = "/farragoTask/allFarragoTask", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allTask(HttpServletRequest request,FarragoTask farragoTask,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(farragoTaskService.findPages(farragoTask, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 删除任务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/farragoTask/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		if(id!=null){
			farragoTaskService.deleteFarragoTask(id);
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
	@RequestMapping(value = "/farragoTask/taskUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse taskUser(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		if(id!=null){
			FarragoTask farragoTask = farragoTaskService.findOne(id);
			List<User> userList = new ArrayList<User>();
			if (!StringUtils.isEmpty(farragoTask.getUserIds())) {
				String[] idArr = farragoTask.getUserIds().split(",");
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
