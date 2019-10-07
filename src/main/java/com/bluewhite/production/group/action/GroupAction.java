package com.bluewhite.production.group.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.poifs.storage.ListManagedBlock;
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
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.UnUtil;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.finance.attendance.service.AttendancePayService;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.group.service.GroupService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Controller
public class GroupAction {

	private static final Log log = Log.getLog(GroupAction.class);

	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	@Autowired
	private TemporarilyDao temporarilyDao;
	@Autowired
	private AttendancePayDao attendancePayDao;
	@Autowired
	private AttendanceService attendanceService;
	
	

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Group.class, "id", "name", "type", "users", "temporaryUsers","kindWork", "remark")
				.addRetainTerm(User.class, "id", "userName")
				.addRetainTerm(TemporaryUser.class, "id", "userName")
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
	public CommonResponse allGroup(HttpServletRequest request,Long id, Date temporarilyDate) {
		CommonResponse cr = new CommonResponse();
		Map<String,Object> groupMap = new HashMap<>();
		Group group = groupService.findOne(id);
		Date startTime = DatesUtil.getfristDayOftime(ProTypeUtils.countAllotTime(temporarilyDate));
		Date endTime = DatesUtil.getLastDayOftime(ProTypeUtils.countAllotTime(temporarilyDate));
		if (group.getType() == 1 || group.getType() == 2 || group.getType() == 3) {
			List<Temporarily> temporarilyList = temporarilyDao.findByTypeAndTemporarilyDateAndGroupId(group.getType(),startTime,id);
			List<AttendancePay> attendancePayList = attendancePayDao.findByGroupIdAndTypeAndAllotTimeBetween(id,group.getType(), startTime, endTime);
			List<Map<String, Object>> userList = new ArrayList<>();
			List<Map<String, Object>> temporarilyUserList = new ArrayList<>();
			
			if (temporarilyList.size() > 0) {
				for (Temporarily temporarily : temporarilyList) {
					// 查询出该分组临时员工的出勤数据
					if (temporarily.getTemporaryUserId() != null) {
						Map<String, Object>  temporarilyUserMap = new HashMap<>();
						temporarilyUserMap.put("id", temporarily.getId());
						temporarilyUserMap.put("secondment", 0);
						temporarilyUserMap.put("name",temporarily.getTemporaryUser().getUserName());
						temporarilyUserMap.put("time",temporarily.getWorkTime());
						temporarilyUserList.add(temporarilyUserMap);
					}
					// 查询出该分组本厂借调员工的出勤数据
					if (temporarily.getUserId() != null) {
						Map<String, Object>  userMap = new HashMap<>();
						userMap.put("id", temporarily.getId());
						userMap.put("secondment", 0);
						userMap.put("name",temporarily.getUser().getUserName());
						userMap.put("time",temporarily.getWorkTime());
						userList.add(userMap);
					}
				} 
				groupMap.put("temporarilyUser", temporarilyUserList);
			}
			
			if (attendancePayList.size() > 0) {
				// 查询出该分组本厂员工的出勤数据
				for (AttendancePay attendancePay : attendancePayList) {
					Map<String, Object>  userMap = new HashMap<>();
					userMap.put("id", attendancePay.getId());
					userMap.put("secondment", 1);
					userMap.put("name",attendancePay.getUserName());
					userMap.put("time",attendancePay.getWorkTime());
					userList.add(userMap);
				}
				groupMap.put("userList", userList);
			}
			//按打卡记录显示工作人员
//			if(!UnUtil.isFromMobile(request)){
//			}else{
//				if (attendancePayList.size() > 0) {
//					List<User> userList = userService.findByGroupId(id);
//					List<Long> userLong = userList.stream().map(user ->user.getId()).collect(Collectors.toList());
//					List<Attendance> attendanceList = attendanceService.findByUserIdInAndTimeBetween(userLong,startTime,endTime);
//					
//					for (AttendancePay attendancePay : attendancePayList) {
//						User user = new User();
//						user.setUserName(attendancePay.getUserName());
//						user.setTurnWorkTime(attendancePay.getWorkTime());
//						userlist.add(user);
//					}
//				}
//
//			}
		}
		cr.setData(groupMap);
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 查询分组
	 * type 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
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
	public CommonResponse userGroup(String userIds,Long groupId) {
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
				if (temporarilyDao.findByUserIdAndTemporarilyDateAndType(temporarily.getUserId(),date, temporarily.getType()) != null) {
					cr.setMessage("当天当前分组已添加过正式人员的工作时间,不必再次添加");
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					return cr;
				}
			}
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
		if (StringUtils.isEmpty(temporarily.getUserId())) {
			Temporarily oldtemporarily = temporarilyDao.findOne(temporarily.getId());
			BeanCopyUtils.copyNotEmpty(temporarily, oldtemporarily);
			temporarilyDao.save(oldtemporarily);
			cr.setMessage("修改成功");
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
		List<Temporarily> temporarilyList = temporarilyDao.findByTypeAndTemporarilyDate(type, temporarilyDate)
				.stream().filter(Temporarily->Temporarily.getUserId()!=null).collect(Collectors.toList());
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(Temporarily.class, "id","workTime", "temporarilyDate", "groupName", "group","user")
				.addRetainTerm(User.class, "id","userName")
				.addRetainTerm(Group.class,"id","name")
				.format(temporarilyList)
				.toJSON());
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
				.addRetainTerm(User.class, "userName")
				.addRetainTerm(Group.class, "name")
				.format(temporarilyList)
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
			for(String id : idString){
				temporarilyDao.delete(Long.parseLong(id));
				count++;
			}
		}
		cr.setMessage("成功删除"+count+"个");
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
