package com.bluewhite.system.user.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.SimpleHash;
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
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.annotation.SysLogAspectAnnotation;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.BankUtil;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.service.AttendanceInitService;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.group.service.GroupService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.system.sys.entity.SysLog;
import com.bluewhite.system.user.dao.UserContractDao;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;
import com.bluewhite.system.user.service.TemporaryUserService;
import com.bluewhite.system.user.service.UserContractService;
import com.bluewhite.system.user.service.UserService;

@Controller
@RequestMapping("/system/user")
public class UserAction {

	@Autowired
	private UserService userService;
	@Autowired
	private UserContractDao userContractDao;
	@Autowired
	private AttendanceInitService attendanceInitService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private TemporaryUserService temporaryUserService;
	@Autowired
	private TemporarilyDao temporarilyDao;
	@Autowired
	private UserContractService userContractService;
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(User.class, "id", "fileId", "idCardEnd", "price", "status", "workTime", "number",
						"pictureUrl", "userName", "phone", "position", "orgName", "idCard", "nation", "email", "gender",
						"birthDate", "group", "idCard", "permanentAddress", "livingAddress", "marriage", "procreate",
						"education", "school", "major", "contacts", "information", "entry", "estimate", "actua",
						"socialSecurity", "bankCard1", "bankCard2", "agreement", "safe", "commitment", "promise",
						"contract", "contractDate", "contractDateEnd", "frequency", "quitDate", "quit", "reason",
						"train", "remark", "userContract", "commitments", "agreementId", "company", "age", "type",
						"ascriptionBank1", "sale", "roles", "turnWorkTime", "quitTypeId", "quitType","lotionNumber")
				.addRetainTerm(Group.class, "id", "name", "type", "price")
				.addRetainTerm(Role.class, "name", "role", "description", "id")
				.addRetainTerm(BaseData.class, "id", "name", "type");
	}

	private ClearCascadeJSON clearCascadeJSONTemporaryUser;
	{
		clearCascadeJSONTemporaryUser = ClearCascadeJSON.get().addRetainTerm(TemporaryUser.class, "userName", "id",
				"phone", "idCard", "bankCard1", "group", "status", "turnWorkTime")
				.addRetainTerm(Group.class, "name", "id");
	}

	private ClearCascadeJSON clearCascadeJSONUserContract;
	{
		clearCascadeJSONUserContract = ClearCascadeJSON.get().addRetainTerm(UserContract.class, "id", "number",
				"username", "archives", "pic", "idCard", "bankCard", "physical", "qualification", "formalSchooling",
				"agreement", "secrecyAgreement", "contract", "remark", "quit","user")
				.addRetainTerm(User.class, "id","userName","lotionNumber");
	}

	/**
	 * 查看员工列表 按不同部门显示的不同的人员
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse userPages(User user, PageParameter page,String filterSos) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(userService.getPagedUser(page, user)).toJSON());
		return cr;
	}

	/**
	 * 查看特急 按不同部门显示的不同的人员
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/foreignsPages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse foreignsPages(User user, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get().addRetainTerm(User.class, "id", "idCardEnd", "price", "status", "workTime",
				"userName", "phone", "idCard").format(userService.getPagedUser(page, user)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 新增员工
	 * 
	 * @param user
	 *            用户实体类
	 * @return cr
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@SysLogAspectAnnotation(description = "员工新增操作", module = "员工管理", operateType = "增加", logType = SysLog.ADMIN_LOG_TYPE)
	public CommonResponse createUser(User user) {
		CommonResponse cr = new CommonResponse();
		userService.addUser(user);
		cr.setCode(2);
		return cr;
	}

	/**
	 * 新增临时员工
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/addTemporaryUser", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTemporaryUser(TemporaryUser temporaryUser) {
		CommonResponse cr = new CommonResponse();
		if (temporaryUser.getId() != null) {
			cr.setMessage("修改成功");
		} else {
			cr.setMessage("新增成功");
		}
		temporaryUserService.addTemporaryUser(temporaryUser);
		return cr;
	}

	/**
	 * 新增临时员工的考勤时间
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/addTemporaryUserTime", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTemporaryUserTime(Temporarily temporarily) {
		CommonResponse cr = new CommonResponse();
		if (temporarily.getGroupId() == null) {
			cr.setMessage("分组不能为空");
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			return cr;
		}
		if (temporarily.getTemporaryUserId() == null) {
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
			if (temporarily.getTemporaryUserId() != null) {
				if (temporarilyDao.findByTemporaryUserIdAndTemporarilyDateAndTypeAndGroupId(
						temporarily.getTemporaryUserId(), date, temporarily.getType(),
						temporarily.getGroupId()) != null) {
					cr.setMessage("当天当前分组已添加过临时工的工作时间,不必再次添加");
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
	 * 查看临时员工列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findTemporaryUserTimePages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findTemporaryUserTimePages(TemporaryUser temporaryUser, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(
				clearCascadeJSONTemporaryUser.format(temporaryUserService.getPagedUser(page, temporaryUser)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 查看临时员工列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findTemporaryUserTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findTemporaryUserTime() {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONTemporaryUser.format(temporaryUserService.findAll()).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 删除临时员工
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteTemporaryUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteTemporaryUser(String ids) {
		CommonResponse cr = new CommonResponse();
		int count = temporaryUserService.deleteTemporaryUser(ids);
		cr.setMessage("成功删除" + count + "条人员");
		return cr;
	}

	/**
	 * 修改用户信息
	 * 
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@SysLogAspectAnnotation(description = "员工修改操作", module = "修改管理", operateType = "修改", logType = SysLog.ADMIN_LOG_TYPE)
	public CommonResponse updateUser(User user) {
		CommonResponse cr = new CommonResponse();
		if (user.getId() == null) {
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("id为空");
			return cr;
		}
		User oldUser = userService.findOne(user.getId());
		// 离职去除分组信息
		if (user.getQuit() != null && user.getQuit() == 1) {
			user.setGroupId(null);
			user.setNumber(null);
		}
		BeanCopyUtils.copyNotEmpty(user, oldUser, "");
		AttendanceInit attendanceInit = attendanceInitService.findByUserId(user.getId());
		if (attendanceInit == null) {
			cr.setCode(2);
		}
		userService.save(oldUser);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 修改用户合同信息
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/updateContract", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateContract(UserContract userContract) {
		CommonResponse cr = new CommonResponse();
		UserContract ot = userContractDao.findOne(userContract.getId());
		BeanCopyUtils.copyNotEmpty(userContract, ot, "");
		userContractDao.save(ot);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 查询用户详细信息
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse userInfo(Long id) {
		User user = userService.findOne(id);
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(user).toJSON());
		return cr;
	}

	/**
	 * 查询单个员工的合同信息
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/getUserContract", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getUser(Long id) {
		CommonResponse cr = new CommonResponse();
		UserContract userContract = userContractDao.findOne(id);
		cr.setData(ClearCascadeJSON.get()
				.addRetainTerm(UserContract.class, "id", "number", "username", "archives", "pic", "idCard", "bankCard",
						"physical", "qualification", "formalSchooling", "agreement", "secrecyAgreement", "contract",
						"remark", "quit")
				.format(userContract).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 通过银行卡号得到银行名称
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/getbank", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getbank(String idCard) {
		CommonResponse cr = new CommonResponse();
		String bankName = BankUtil.getNameOfBank(idCard);
		cr.setMessage("查询成功");
		cr.setData(bankName);
		return cr;
	}

	/**
	 * 合同到期，退休时间到期提醒
	 * 
	 * @return cr
	 */
	@RequestMapping(value = "/remind", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse remind() {
		CommonResponse cr = new CommonResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> userList = userService.findAll();
		List<Map<String, Object>> userBirthList = new ArrayList<Map<String, Object>>();
		// 更新员工年龄
		List<User> userAge = userList.stream().filter(User -> User.getBirthDate() != null).collect(Collectors.toList());
		userAge.stream().forEach(u -> {
			int age = DatesUtil.getAgeByBirth(u.getBirthDate());
			u.setAge(age);
		});
		userService.save(userAge);

		// 退休时间
		List<User> userBirth = userList.stream()
				.filter(User -> User.getBirthDate() != null && User.getGender() != null && User.getQuit() != null
						&& User.getQuit() != 1 && User.getCommitmentId() != null && User.getCommitmentId() != 144
						&& User.getCommitmentId() != 145 && User.getCommitmentId() != 317)
				.collect(Collectors.toList());
		for (User user : userBirth) {
			Map<String, Object> us = new HashMap<String, Object>();
			int co = DatesUtil.getAgeByBirth(user.getBirthDate());
			long co2 = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(new Date()),
					DatesUtil.getfristDayOftime(user.getBirthDate()));
			// 男
			if (user.getGender() == 0 && co == 60 && co2 <= 60) {
				us.put("userId", user.getId());
				us.put("username", user.getUserName());
				us.put("birthDate", sdf.format(user.getBirthDate()));
				userBirthList.add(us);
				// 女
			} else if (user.getGender() == 1 && co == 50 && co2 <= 60) {
				us.put("userId", user.getId());
				us.put("username", user.getUserName());
				us.put("birthDate", sdf.format(user.getBirthDate()));
				userBirthList.add(us);
			}

		}

		// 合同到期时间
		List<Map<String, Object>> userContractList = new ArrayList<Map<String, Object>>();
		List<User> userContract = userList.stream().filter(User -> User.getContractDateEnd() != null
				&& User.getQuit() != null && User.getQuit() != 1 && User.getCommitmentId() != null)
				.collect(Collectors.toList());
		for (User user : userContract) {
			Map<String, Object> us = new HashMap<String, Object>();
			long co = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(new Date()),
					DatesUtil.getfristDayOftime(user.getContractDateEnd()));
			if (co <= 80) {
				us.put("userId", user.getId());
				us.put("username", user.getUserName());
				us.put("contractDateEnd", sdf.format(user.getContractDateEnd()));
				userContractList.add(us);
			}
		}

		// 身份证到期
		List<Map<String, Object>> userCardList = new ArrayList<Map<String, Object>>();
		List<User> userrCard = userList.stream().filter(User -> User.getIdCardEnd() != null && User.getQuit() != 1
				&& User.getQuit() != null && User.getCommitmentId() != null).collect(Collectors.toList());
		for (User user : userrCard) {
			Map<String, Object> us = new HashMap<String, Object>();
			long co = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(new Date()),
					DatesUtil.getfristDayOftime(user.getIdCardEnd()));
			if (co <= 60) {
				us.put("userId", user.getId());
				us.put("username", user.getUserName());
				us.put("idCardEnd", sdf.format(user.getIdCardEnd()));
				userCardList.add(us);
			}
		}

		map.put("userBirth", userBirthList);
		map.put("userContract", userContractList);
		map.put("userCard", userCardList);
		cr.setMessage("查询成功");
		cr.setData(map);
		return cr;
	}

	/**
	 * 删除人员信息
	 * 
	 * @param request
	 *            请求
	 * @return cr
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteUser(String id) {
		CommonResponse cr = new CommonResponse();
		int count = userService.deleteUser(id);
		cr.setMessage("成功删除" + count + "名员工");
		return cr;
	}

	/**
	 * 查询符合要求的人员信息
	 * 
	 * @param request请求
	 * @return cr
	 */
	@RequestMapping(value = "/findAllUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findAllUser() {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get().addRetainTerm(User.class, "id", "userName")
				.format(userService.findByForeigns()).toJSON());
		return cr;
	}

	/**
	 * 查询符合要求的人员信息
	 * 
	 * @param request请求
	 * @return cr
	 */
	@RequestMapping(value = "/findUserList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findUserList(User user) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get().addRetainTerm(User.class, "id", "userName", "phone")
				.format(userService.findUserList(user)).toJSON());
		return cr;
	}

	/**
	 * 验证密码
	 */
	@RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse checkPassword(String password) {
		CommonResponse cr = new CommonResponse();
		if (userService.checkPassword(password)) {
			cr.setMessage("密码正确");
		} else {
			cr.setCode(ErrorCode.SYSTEM_USER_PASSWORD_WRONG.getCode());
			cr.setMessage("密码错误");
		}
		return cr;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updatePassword(String password) {
		CommonResponse cr = new CommonResponse();
		CurrentUser cu = SessionManager.getUserSession();
		String newPassword = new SimpleHash("md5", password).toHex();
		User user = userService.findOne(cu.getId());
		user.setPassword(newPassword);
		userService.save(user);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 获取可转正人员 （人事自动获取）
	 */
	@RequestMapping(value = "/getPositiveUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getPositiveUser() {
		CommonResponse cr = new CommonResponse();
		// cr.setData(ClearCascadeJSON
		// .get()
		// .addRetainTerm(User.class,"id","userName","phone","type")
		// .format(userService.getPositiveUser()).toJSON());
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 一键转正人员
	 */
	@RequestMapping(value = "/positiveUser", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse positiveUser(String positiveUser) {
		CommonResponse cr = new CommonResponse();
		int count = userService.positiveUser(positiveUser);
		cr.setMessage("成功转正" + count + "名员工");
		return cr;
	}

	/**
	 * 按部门查找所有人员
	 */
	@RequestMapping(value = "/findUserOrg", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findUserOrg() {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON.get().addRetainTerm(BaseData.class, "id", "name", "users")
				.addRetainTerm(User.class, "id", "userName").format(userService.findUserOrg()).toJSON());
		cr.setMessage("成功");
		return cr;
	}

	/**
	 * 给人员批量设定约定休息日期
	 */
	@RequestMapping(value = "/setUserRestDate", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse setUserRestDate(String userIds, String restDay) {
		CommonResponse cr = new CommonResponse();
		int count = userService.setUserRestDate(userIds, restDay);
		cr.setMessage("成功设定" + count + "个员工的约定休息日期");
		return cr;
	}

	/**
	 * 查看员工档案列表 
	 * 
	 * @param request
	 * @param UserContract
	 * @return
	 */
	@RequestMapping(value = "/userContractPages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse userContractPages(UserContract contract, PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSONUserContract.format(userContractService.findPage(contract, page)).toJSON());
		return cr;
	}
	
	/**
	 * 档案新增修改
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUserContract", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addPlan(HttpServletRequest request, UserContract userContract) {
		CommonResponse cr = new CommonResponse();
		if(userContract.getId() != null){
			UserContract userContract2 = userContractService.findOne(userContract.getId());
				BeanCopyUtils.copyNullProperties(userContract2, userContract);
				userContract.setCreatedAt(userContract2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		userContractService.addUserContract(userContract);
		return cr;
	}
	
	/**
	 * 档案删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteUserContract", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletePlan(HttpServletRequest request, String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count= userContractService.deletes(ids);
		cr.setMessage("成功删除"+count+"条");
		return cr;
	}
	
}
