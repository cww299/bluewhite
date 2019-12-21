package com.bluewhite.production.group.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.UnUtil;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.farragotask.service.FarragoTaskService;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.group.service.GroupService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.production.task.service.TaskService;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

import cn.hutool.core.date.DateUtil;

@Controller
public class GroupAction {

	private static final Log log = Log.getLog(GroupAction.class);

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TemporarilyDao temporarilyDao;
	@Autowired
	private AttendancePayDao attendancePayDao;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private FarragoTaskService farragoTaskService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Group.class, "id", "name", "type", "users", "temporaryUsers", "kindWork", "remark")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(TemporaryUser.class, "id", "userName")
				.addRetainTerm(BaseData.class, "id", "name", "type");
	}

	/**
	 * 添加，修改分组
	 * 
	 * type 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工,4=二楼机工)
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/production/addGroup", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addGroup(Group group) {
		CommonResponse cr = new CommonResponse();
		if (group.getId() != null) {
			Group oldGroup = groupService.findOne(group.getId());
			BeanCopyUtils.copyNotEmpty(group, oldGroup, "");
			groupService.save(oldGroup);
			cr.setMessage("分组修改成功");
		} else {
			if (group.getType() != null) {
				cr.setData(clearCascadeJSON.format(groupService.save(group)).toJSON());
				cr.setMessage("分组添加成功");
			} else {
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("分组类型不能为空");
			}
		}
		return cr;
	}

	/**
	 * 查询单个分组
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/getGroupOne", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getGroupOne(Long id, Date temporarilyDate) {
		CommonResponse cr = new CommonResponse();
		if (id != null) {
			cr.setData(clearCascadeJSON.format(groupService.findOne(id)).toJSON());
			cr.setMessage("查询成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("组不能为空");
		}
		return cr;
	}
	
	
	/**
	 * 根据时间查看当前分组里面的人
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/allGroup", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allGroup(HttpServletRequest request, Long id, Date temporarilyDate) {
		CommonResponse cr = new CommonResponse();
		CurrentUser cu = SessionManager.getUserSession();
		String sourceMachine = "";
		if (cu.getRole().contains("productFristPack")) {
			sourceMachine = "ONE_FLOOR";
		}
		if (cu.getRole().contains("packScene")) {
			sourceMachine = "ELEVEN_WAREHOUSE";
		}
		Map<String, Object> groupMap = new HashMap<>();
		Date startTime = DatesUtil.getfristDayOftime(ProTypeUtils.countAllotTime(temporarilyDate));
		Date endTime = DatesUtil.getLastDayOftime(ProTypeUtils.countAllotTime(temporarilyDate));
		Group group = groupService.findOne(id);
		List<Temporarily> temporarilyList = temporarilyDao
				.findByTypeAndGroupIdAndTemporarilyDateBetween(group.getType(), id, startTime, endTime);
		List<Map<String, Object>> userList = new ArrayList<>();
		List<Map<String, Object>> temporarilyUserList = new ArrayList<>();
		// 平板模式下，按打卡记录显示正式工作人员
		if (UnUtil.isFromMobile(request)) {
			//从第一天早上五点到第二天早上4：59：59 的打卡人员
			Date startTimeSix = DateUtil.offsetHour(startTime, 5);
			Date endTimeSix = DateUtil.offsetHour(endTime, 5); 
			List<User> userGroupList = userService.findByGroupId(id);
			String sourceMachineFinal = sourceMachine;
			userGroupList = userGroupList.stream().filter(user -> {
				List<Attendance> attendanceList = attendanceService.findByUserIdAndSourceMachineAndTimeBetween(
						user.getId(), sourceMachineFinal, startTimeSix, endTimeSix);
				if (attendanceList.size() > 0) {
					return true;
				} else {
					return false;
				}
			}).collect(Collectors.toList());
			/// secondment =0 临时, secondment = 1 正式
			if (temporarilyList.size() > 0) {
				for (Temporarily temporarily : temporarilyList) {
					// 查询出该分组临时员工的出勤数据
					if (temporarily.getTemporaryUserId() != null) {
						Map<String, Object> temporarilyUserMap = new HashMap<>();
						temporarilyUserMap.put("id", temporarily.getTemporaryUserId());
						temporarilyUserMap.put("userId", temporarily.getId());
						temporarilyUserMap.put("secondment", 0);
						temporarilyUserMap.put("groupId", temporarily.getGroupId());
						temporarilyUserMap.put("name", temporarily.getTemporaryUser().getUserName());
						temporarilyUserMap.put("time",
								temporarily.getWorkTime() == null ? 0 : temporarily.getWorkTime());
						// 工作休息状态
						temporarilyUserMap.put("status", temporarily.getStatus());
						temporarilyUserList.add(temporarilyUserMap);
					}
					// 查询出该分组本厂借调员工的出勤数据
					if (temporarily.getUserId() != null) {
						Map<String, Object> userMap = new HashMap<>();
						userMap.put("id", temporarily.getUserId());
						userMap.put("userId", temporarily.getId());
						userMap.put("secondment", 0);
						userMap.put("groupId", temporarily.getGroupId());
						userMap.put("name", temporarily.getUser().getUserName());
						userMap.put("time", temporarily.getWorkTime() == null ? 0 : temporarily.getWorkTime());
						userMap.put("status", temporarily.getStatus());
						userList.add(userMap);
					}
				}
			}

			if (userGroupList.size() > 0) {
				// 查询出该分组本厂员工的出勤数据
				for (User user : userGroupList) {
					// 获取员工一天内的打卡记录并按照自然排序
					List<Attendance> attendanceList = attendanceService
							.findByUserIdAndSourceMachineAndTimeBetween(user.getId(), sourceMachine, startTime, endTime)
							.stream().sorted(Comparator.comparing(Attendance::getTime)).collect(Collectors.toList());
					Attendance attendanceIn = null;
					Attendance attendanceOut = null;
					Double time = null;
					Double timeH = null;
					int flag = 0;
					// 根据签到时间实时显示工作时长
					if (attendanceList.size() > 0) {
						flag = 1;
						attendanceIn = attendanceList.get(0);
						time = DatesUtil.getTime(attendanceIn.getTime(), new Date());
						timeH = DatesUtil.getTimeHourPick(attendanceIn.getTime(), new Date());
					}
					if (attendanceIn.getManualTime() != null) {
						flag = 0;
						time = DatesUtil.getTime(attendanceIn.getTime(), attendanceIn.getManualTime());
						timeH = DatesUtil.getTimeHourPick(attendanceIn.getTime(), attendanceIn.getManualTime());
					}
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("id", user.getId());
					userMap.put("userId", attendanceIn.getId());
					userMap.put("secondment", 1);
					userMap.put("groupId", user.getGroupId());
					userMap.put("name", user.getUserName());
					userMap.put("time", timeH + "H/(" + time + "M)");
					userMap.put("status", flag);
					userList.add(userMap);
				}
			}
		} else {
			// pc模式下
			List<AttendancePay> attendancePayList = attendancePayDao.findByGroupIdAndTypeAndAllotTimeBetween(id,
					group.getType(), startTime, endTime);
			if (temporarilyList.size() > 0) {
				for (Temporarily temporarily : temporarilyList) {
					// 查询出该分组临时员工的出勤数据
					if (temporarily.getTemporaryUserId() != null) {
						Map<String, Object> temporarilyUserMap = new HashMap<>();
						temporarilyUserMap.put("id", temporarily.getTemporaryUserId());
						temporarilyUserMap.put("userId", temporarily.getId());
						temporarilyUserMap.put("secondment", 0);
						temporarilyUserMap.put("groupId", temporarily.getGroupId());
						temporarilyUserMap.put("name", temporarily.getTemporaryUser().getUserName());
						temporarilyUserMap.put("time", temporarily.getWorkTime());
						temporarilyUserMap.put("status", temporarily.getStatus());
						temporarilyUserList.add(temporarilyUserMap);
					}
					// 查询出该分组本厂借调员工的出勤数据
					if (temporarily.getUserId() != null) {
						Map<String, Object> userMap = new HashMap<>();
						userMap.put("id", temporarily.getUserId());
						userMap.put("userId", temporarily.getId());
						userMap.put("secondment", 0);
						userMap.put("groupId", temporarily.getGroupId());
						userMap.put("name", temporarily.getUser().getUserName());
						userMap.put("time", temporarily.getWorkTime());
						userMap.put("status", temporarily.getStatus());
						userList.add(userMap);
					}
				}
			}
			if (attendancePayList.size() > 0) {
				// 查询出该分组本厂员工的出勤数据
				for (AttendancePay attendancePay : attendancePayList) {
					Map<String, Object> userMap = new HashMap<>();
					userMap.put("id", attendancePay.getUserId());
					userMap.put("userId", attendancePay.getId());
					userMap.put("secondment", 1);
					userMap.put("groupId", attendancePay.getGroupId());
					userMap.put("name", attendancePay.getUserName());
					userMap.put("time", attendancePay.getWorkTime());
					userMap.put("status", 1);
					userList.add(userMap);
				}
			}
		}
		groupMap.put("temporarilyUser", temporarilyUserList);
		groupMap.put("userList", userList);
		cr.setData(groupMap);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 手动签出
	 * 
	 */
	@RequestMapping(value = "/production/updateManualTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateManualTime(Long id, Integer status, Date time, Integer flag) {
		CommonResponse cr = new CommonResponse();
		if (id != null) {
			if (flag == 1) {
				Attendance attendance = attendanceService.findOne(id);
				// 休息
				if (status == 0) {
					attendance.setManualTime(time);
				}
				// 工作
				if (status == 1) {
					attendance.setManualTime(null);
				}
				attendanceService.save(attendance);
			}
			if (flag == 0) {
				Temporarily temporarily = temporarilyDao.findOne(id);
				// 休息
				if (status == 0) {
					temporarily.setStatus(0);
				}
				// 工作
				if (status == 1) {
					temporarily.setStatus(1);
				}
				temporarilyDao.save(temporarily);
			}
		}
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 查询分组 type 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 * 
	 */
	@RequestMapping(value = "/production/getGroup", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getGroup(Group group) {
		CommonResponse cr = new CommonResponse();
		if (group.getType() != null) {
			cr.setData(clearCascadeJSON.format(groupService.findByType(group.getType())).toJSON());
			cr.setMessage("查询成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("分组类型不能为空");
		}
		return cr;
	}

	/**
	 * 给用户分组
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/production/userGroup", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse userGroup(String userIds, Long groupId) {
		CommonResponse cr = new CommonResponse();
		if (userIds != null) {
			String[] userIdString = userIds.split(",");
			for (String id : userIdString) {
				Long userId = Long.parseLong(id);
				User userGroup = userService.findOne(userId);
				userGroup.setGroupId(groupId);
				userService.save(userGroup);
			}
			cr.setMessage("分组成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("用户不能为空");
		}
		return cr;
	}

	/**
	 * 删除分组
	 * 
	 * @return
	 */
	@RequestMapping(value = "/production/group/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			groupService.delete(ids);
			cr.setMessage("删除成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	/**
	 * 新增借调人员的借调记录(对于公司员工的部门之间 的借调使用)
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/production/addTemporarily", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTemporarily(Temporarily temporarily) {
		CommonResponse cr = new CommonResponse();
		if (temporarily.getGroupId() == null) {
			cr.setMessage("分组不能为空");
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			return cr;
		}
		if (temporarily.getUserId() == null) {
			cr.setMessage("人员不能为空");
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			return cr;
		}
		List<Date> dateList = new ArrayList<>();
		if (!StringUtils.isEmpty(temporarily.getTemporarilyDates())) {
			String[] dateArr = temporarily.getTemporarilyDates().split(" ~ ");
			// 获取所有日期
			dateList = DatesUtil.getPerDaysByStartAndEndDate(dateArr[0], dateArr[1], "yyyy-MM-dd");
		} else {
			dateList.add(ProTypeUtils.countAllotTime(temporarily.getTemporarilyDate()));
		}
		List<Temporarily> temporarilyList = new ArrayList<>();
		for (Date date : dateList) {
			Temporarily temporarilyNew = new Temporarily();
			BeanCopyUtils.copyNotEmpty(temporarily, temporarilyNew, "");
			temporarilyNew.setTemporarilyDate(DatesUtil.getfristDayOftime(date));
			if (temporarily.getUserId() != null) {
				if (temporarilyDao.findByUserIdAndTemporarilyDateAndTypeAndGroupId(temporarily.getUserId(), date,
						temporarily.getType(), temporarily.getGroupId()) != null) {
					cr.setMessage("当天当前分组已添加过正式人员的工作时间,不必再次添加");
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					return cr;
				}
			}
			temporarilyNew.setStatus(1);
			temporarilyList.add(temporarilyNew);
		}
		temporarilyDao.save(temporarilyList);
		cr.setMessage("添加成功");
		return cr;
	}

	/**
	 * 修改借调人员
	 * 
	 * (1=一楼质检,2=一楼包装)
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/production/updateTemporarily", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateTemporarily(Temporarily temporarily) {
		CommonResponse cr = new CommonResponse();
		if (temporarily.getId() != null) {
			Temporarily oldtemporarily = temporarilyDao.findOne(temporarily.getId());
			BeanCopyUtils.copyNotEmpty(temporarily, oldtemporarily);
			temporarilyDao.save(oldtemporarily);
			cr.setMessage("修改成功");
		} else {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}

	/**
	 * 查询借调人员
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/production/getTemporarily", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getTemporarily(Integer type, Date temporarilyDate) {
		CommonResponse cr = new CommonResponse();
		List<Temporarily> temporarilyList = temporarilyDao.findByTypeAndTemporarilyDate(type, temporarilyDate).stream()
				.filter(Temporarily -> Temporarily.getUserId() != null).collect(Collectors.toList());
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Temporarily.class, "id", "workTime", "temporarilyDate", "groupName", "group", "user")
				.addRetainTerm(User.class, "id", "userName").addRetainTerm(Group.class, "id", "name")
				.format(temporarilyList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 查询借调人员
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/production/getTemporarilyList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getTemporarilyList(Temporarily temporarily) {
		CommonResponse cr = new CommonResponse();
		List<Temporarily> temporarilyList = groupService.findTemporarilyList(temporarily);
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Temporarily.class, "id", "workTime", "workTimeSlice", "temporarilyDate", "group", "user")
				.addRetainTerm(User.class, "userName").addRetainTerm(Group.class, "name").format(temporarilyList)
				.toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 打特急人员绩效汇总
	 * 
	 * @param request
	 * @return cr
	 */
	@RequestMapping(value = "/production/sumTemporarily", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse sumTemporarily(Temporarily temporarily) {
		CommonResponse cr = new CommonResponse();
		cr.setData(groupService.sumTemporarily(temporarily));
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 删除临时人员
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/production/deleteTemporarily", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteTemporarily(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idString = ids.split(",");
			for (String id : idString) {
				Temporarily temporarily = temporarilyDao.findOne(Long.parseLong(id));
				if (temporarily != null) {
					Date orderTimeBegin = DatesUtil.getfristDayOftime(temporarily.getTemporarilyDate());
					Date orderTimeEnd = DatesUtil.getLastDayOftime(temporarily.getTemporarilyDate());
					List<Task> taskList = taskService.findInSetTemporaryIds(id, orderTimeBegin, orderTimeEnd);
					List<FarragoTask> farragoTaskList = farragoTaskService.findInSetIds(id, orderTimeBegin,
							orderTimeEnd);
					if (taskList.size() > 0 || farragoTaskList.size() > 0) {
						cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
						cr.setMessage((temporarily.getTemporaryUser()==null ?temporarily.getUser().getUserName():temporarily.getTemporaryUser().getUserName())+"当天考勤已分配任务，无法删除，需删除，请先删除任务");
						return cr;
					} else {
						temporarilyDao.delete(Long.parseLong(id));
						count++;
					}
				}

			}
		}
		cr.setMessage("成功删除" + count + "条出勤记录");
		return cr;
	}

}
