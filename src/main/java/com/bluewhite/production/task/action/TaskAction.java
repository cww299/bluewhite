package com.bluewhite.production.task.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.procedure.service.ProcedureService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.TemporaryUserService;
import com.bluewhite.system.user.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat.Value;

@Controller
public class TaskAction {

	private static final Log log = Log.getLog(TaskAction.class);

	@Autowired
	private TaskService taskService;
	@Autowired
	private AttendancePayDao attendancePayDao;
	@Autowired
	private TemporarilyDao temporarilyDao;
	@Autowired
	private ProcedureDao procedureDao;
	@Autowired
	private ProcedureService procedureService;
	@Autowired
	private UserService userService;
	@Autowired
	private PayBDao payBDao;
	@Autowired
	private TemporaryUserService temporaryUserService;
	@Autowired
	private BacthService bacthService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Task.class, "id", "remark", "userNames", "bacthNumber", "allotTime", "productName",
						"userIds", "procedure", "procedureName", "number", "status", "expectTime", "expectTaskPrice",
						"taskTime", "payB", "taskPrice", "taskActualTime", "type", "createdAt", "performance",
						"performanceNumber", "performancePrice", "flag")
				.addRetainTerm(Procedure.class, "id", "procedureTypeId");
	}

	/**
	 * 质检获取任务加绩类型列表
	 */
	@RequestMapping(value = "/task/taskPerformance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse taskPerformance(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> mapList = ProTypeUtils.taskPerformance();
		cr.setData(mapList);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 给批次添加任务
	 * 
	 */
	@RequestMapping(value = "/task/addTask", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTask(HttpServletRequest request, Task task) {
		CommonResponse cr = new CommonResponse();
		// 同步锁
		synchronized (this) {
			// 新增
			if (!StringUtils.isEmpty(task.getUserIds()) || !StringUtils.isEmpty(task.getTemporaryUserIds())) {
				Bacth bacth = bacthService.findOne(task.getBacthId());
				for (int i = 0; i < task.getProcedureIds().length; i++) {
					int num = i;
					// 获取该工序的已分配的任务数量
					int count = bacth.getTasks().stream()
							.filter(Task -> Task.getProcedureId().equals(task.getProcedureIds()[num]))
							.mapToInt(Task::getNumber).sum();
					// 当前分配数量加已分配数量大于批次总数量则不通过
					if ((task.getNumber() + count) > bacth.getNumber()) {
						cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
						cr.setMessage("当前数量剩余不足，请确认数量");
						return cr;
					}
				}
				task.setAllotTime(ProTypeUtils.countAllotTime(task.getAllotTime()));
				taskService.addTask(task, request);
				cr.setMessage("任务分配成功");
			} else {
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("领取人不能为空");
			}
		}
		return cr;
	}

	/**
	 * 修改任务
	 * 
	 * 
	 */
	@RequestMapping(value = "/task/upTask", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse upTask(HttpServletRequest request, Task task) {
		CommonResponse cr = new CommonResponse();
		if (task.getId() != null) {
			int count = 0;
			Task oldTask = taskService.findOne(task.getId());
			for (Task ta : oldTask.getBacth().getTasks()) {
				if (ta.getProcedureId().equals(oldTask.getProcedureId())) {
					count += ta.getNumber();
				}
			}
			if ((count - oldTask.getNumber() + task.getNumber()) > oldTask.getBacth().getNumber()) {
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("修改数量不能超过该批次总数:" + oldTask.getBacth().getNumber());
				return cr;
			}
			BeanCopyUtils.copyNotEmpty(task, oldTask, "");
			String[] arrayRefVar = { String.valueOf(oldTask.getProcedureId()) };
			oldTask.setProcedureIds(arrayRefVar);
			taskService.addTask(oldTask, request);
			cr.setMessage("修改成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("任务不能为空");
		}
		return cr;
	}

	/**
	 * 给批次添加任务(方式2) 应业务要求，增加按时间占比，分配不同任务数量给不同的员工，进行新增任务
	 * 
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/task/addTaskTwo", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTaskTwo(HttpServletRequest request, Task task) throws Exception {
		CommonResponse cr = new CommonResponse();
		// 根据时间占比，组装出新任务
		List<Task> taskList = taskService.assembleTask(task);
		// 新增
		for (Task tasks : taskList) {
			if (!StringUtils.isEmpty(tasks.getUserIds())) {
				tasks.setAllotTime(ProTypeUtils.countAllotTime(task.getAllotTime()));
				taskService.addTask(tasks, request);
				cr.setMessage("任务分配成功");
			} else {
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
	public CommonResponse allTask(HttpServletRequest request, Task task, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		CurrentUser cu = SessionManager.getUserSession();
		if (!cu.getRole().contains("superAdmin") && !cu.getRole().contains("personnel")) {
			task.setUserId(cu.getId());
		}
		cr.setData(clearCascadeJSON.format(taskService.findPages(task, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 删除任务
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			taskService.deleteTask(ids);
			cr.setMessage("删除成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	/**
	 * 查询该任务的所有领取人
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task/taskUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse taskUser(Long id) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> list = new ArrayList<>();
		if (id != null) {
			Task task = taskService.findOne(id);
			if (!StringUtils.isEmpty(task.getUserIds())) {
				String[] idArr = task.getUserIds().split(",");
				if (idArr.length > 0) {
					for (int i = 0; i < idArr.length; i++) {
						Map<String, Object> userMap = new HashMap<>();
						Long userid = Long.parseLong(idArr[i]);
						User user = userService.findOne(userid);
						userMap.put("id", user.getId());
						userMap.put("userName", user.getUserName());
						list.add(userMap);
					}
				}
			}
			if (!StringUtils.isEmpty(task.getTemporaryUserIds())) {
				String[] idArr = task.getTemporaryUserIds().split(",");
				if (idArr.length > 0) {
					for (int i = 0; i < idArr.length; i++) {
						Map<String, Object> userMap = new HashMap<>();
						Long userid = Long.parseLong(idArr[i]);
						TemporaryUser temporaryUser = temporaryUserService.findOne(userid);
						userMap.put("id", temporaryUser.getId());
						userMap.put("userName", temporaryUser.getUserName());
						list.add(userMap);
					}
				}
			}
			cr.setData(list);
			cr.setMessage("查询成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	/******** 一楼包装 *********/

	/**
	 * 获取任务加绩类型列表
	 */
	@RequestMapping(value = "/task/pickTaskPerformance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse pickTaskPerformance(Long procedureId) {
		CommonResponse cr = new CommonResponse();
		List<Map<String, Object>> mapList = ProTypeUtils.pickTaskPerformance();
		if (procedureId != null) {
			Procedure procedure = procedureService.findOne(procedureId);
			if (procedure != null) {
				if (procedure.getName().indexOf("发货位堆放") != -1 || procedure.getName().indexOf("推包到发货位") != -1
						|| procedure.getName().indexOf("推箱到发货位") != -1) {
					mapList.stream().forEach(m -> {
						if (String.valueOf(m.get("name")).equals("推货工序")) {
							m.put("checked", 1);
						}
					});
				}

				if (procedure.getName().indexOf("写编码") != -1) {
					mapList.stream().forEach(m -> {
						if (String.valueOf(m.get("name")).equals("精细填写工序")) {
							m.put("checked", 1);
						}
					});
				}

				if (procedure.getName().indexOf("大包堆放原打包位") != -1 || procedure.getName().indexOf("压包") != -1
						|| procedure.getName().indexOf("点数") != -1 || procedure.getName().indexOf("绞口") != -1
						|| procedure.getName().indexOf("套袋") != -1 || procedure.getName().indexOf("封箱") != -1
						|| procedure.getName().indexOf("封空箱") != -1 || procedure.getName().indexOf("原打包位") != -1) {
					mapList.stream().forEach(m -> {
						if (String.valueOf(m.get("name")).equals("装箱装包工序")) {
							m.put("checked", 1);
						}
					});
				}

				if (procedure.getName().indexOf("大包上车") != -1 || procedure.getName().indexOf("箱上车") != -1) {
					mapList.stream().forEach(m -> {
						if (String.valueOf(m.get("name")).equals("上下车力工工序")) {
							m.put("checked", 1);
						}
					});
				}

			}
		}
		cr.setData(mapList);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 通过任务id，重新分配人员的加绩工资
	 */
	@RequestMapping(value = "/task/giveTaskPerformance", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse giveTaskPerformance(HttpServletRequest request, String[] taskIds, String[] ids,
			String[] performance, Double[] performanceNumber, Integer update) {
		CommonResponse cr = new CommonResponse();
		taskService.giveTaskPerformance(taskIds, ids, performance, performanceNumber, update);
		cr.setMessage("添加成功");
		return cr;
	}

	/**
	 * 通过任务id，获取人员的加绩工资
	 * 
	 */
	@RequestMapping(value = "/task/getUserPerformance", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getUserPerformance(HttpServletRequest request, Long id) {
		CommonResponse cr = new CommonResponse();
		List<PayB> payBList = payBDao.findByTaskId(id);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		Map<Object, List<PayB>> mapPayB = payBList.stream().filter(PayB -> PayB.getPerformance() != null)
				.collect(Collectors.groupingBy(PayB::getPerformance, Collectors.toList()));
		for (Object ps : mapPayB.keySet()) {
			map = new HashMap<String, Object>();
			List<PayB> psList = mapPayB.get(ps);
			List<String> userNameList = new ArrayList<String>();
			for (PayB payB : psList) {
				userNameList.add(payB.getUserName());
			}
			map.put("performance", ps);
			map.put("username", userNameList);
			listMap.add(map);
		}
		cr.setData(listMap);
		cr.setMessage("查询成功");
		return cr;
	}

	/******** 二楼机工 *********/

	/**
	 * 添加返工任务任务
	 * 
	 * 
	 */
	@RequestMapping(value = "/task/addReTask", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addReTask(HttpServletRequest request, Task task) {
		CommonResponse cr = new CommonResponse();
		// 修改
		if (!StringUtils.isEmpty(task.getId())) {
			Task oldTask = taskService.findOne(task.getId());
			BeanCopyUtils.copyNullProperties(oldTask, task);
			task.setCreatedAt(oldTask.getCreatedAt());
			taskService.save(task);
			cr.setMessage("修改成功");
		} else {
			// 新增
			if (!StringUtils.isEmpty(task.getUserIds())) {
				task.setAllotTime(ProTypeUtils.countAllotTime(task.getAllotTime()));
				taskService.addReTask(task);
				cr.setMessage("任务分配成功");
			} else {
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("领取人不能为空");
			}
		}
		return cr;
	}

	/**
	 * 删除技工返工任务
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/task/deleteReTask", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteReTask(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			taskService.deleteReTask(ids);
			cr.setMessage("删除成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
