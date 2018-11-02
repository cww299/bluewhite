package com.bluewhite.system.user.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.BankUtil;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.system.user.dao.UserContractDao;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;
import com.bluewhite.system.user.service.UserService;

@Controller
@RequestMapping("/system/user")
public class UserAction {
	

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserContractDao userContractDao;
	
	@Autowired
	private UserDao userDao;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(User.class,"id","fileId","idCardEnd","price","status","workTime","number","pictureUrl", "userName", "phone","position","orgName","idCard",
						"nation","email","gender","birthDate","group","idCard","permanentAddress","livingAddress","marriage","procreate","education"
						,"school","major","contacts","information","entry","estimate","actua","socialSecurity","bankCard1","bankCard2","agreement","safe","commitment"
						,"promise","contract","contractDate","contractDateEnd","frequency","quitDate","quit","reason","train","remark","userContract","commitments"
						,"agreementId","company","age","type","ascriptionBank1")
				.addRetainTerm(Group.class, "id","name", "type", "price")
				.addRetainTerm(Role.class, "name", "role", "description","id")
				.addRetainTerm(BaseData.class, "id","name", "type")
				.addRetainTerm(UserContract.class, "id","number", "username","archives","pic","idCard","bankCard","physical",
						"qualification","formalSchooling","agreement","secrecyAgreement","contract","remark","quit");
	}
	
	/**
	 *  查看用户列表
	 *  按不同部门显示的不同的人员
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse userPages(HttpServletRequest request, User user,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(userService.getPagedUser(page,user)).toJSON());
		return cr;
	}
	
	
	
	
	/**
	 * 新增一个用户
	 * 
	 * @param request 请求
	 * @param user 用户实体类
	 * @return cr
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse createUser(HttpServletRequest request, User user) {
		CommonResponse cr = new CommonResponse();
		user.setPassword("123456");
		user.setForeigns(0);
		user.setLoginName(user.getUserName());
		UserContract userContract = new UserContract();
		userContract.setUsername(user.getUserName());
		userContractDao.save(userContract);
		user.setUserContract(userContract);
		if(!StringUtils.isEmpty(user.getPhone())){
			User u = userService.findByPhone(user.getPhone());
			if(u != null){
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("该用户手机号已存在");
				return cr;
			}
		}
		cr.setData(clearCascadeJSON.format(userService.save(user)).toJSON());
		return cr;
	}
	
	
	
	/**
	 * 修改用户信息
	 * @param request 请求
	 * @param user 用户实体类
	 * @return cr
	 */	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse changeUser(HttpServletRequest request, User user) {
		CommonResponse cr = new CommonResponse();
		if(user.getId() == null){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("id为空");
			return cr;
		}
		User oldUser = userService.findOne(user.getId());
		
		if(oldUser.getUserContract()==null){
			UserContract userContract = new UserContract();
			userContract.setUsername(user.getUserName());
			userContractDao.save(userContract);
			oldUser.setUserContract(userContract);
		}
		BeanCopyUtils.copyNotEmpty(user,oldUser,"");
		cr.setData(clearCascadeJSON.format(userService.save(oldUser)).toJSON());
		cr.setMessage("修改成功");
		return cr;
	}
	
	
	/**
	 * 修改用户合同信息
	 * @param request 请求
	 * @param user 用户实体类
	 * @return cr
	 */	
	@RequestMapping(value = "/updateContract", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateContract(HttpServletRequest request, UserContract userContract) {
		CommonResponse cr = new CommonResponse();
		if(userContract.getNumber()!=null){
			UserContract uc = userContractDao.findByNumber(userContract.getNumber());
			if(uc!=null){
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("该合同编号已存在");
				return cr;
			}
		}
		
		if(userContract.getId() == null){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("id为空");
			return cr;
		}
		UserContract oldUser = userContractDao.findOne(userContract.getId());
		BeanCopyUtils.copyNotEmpty(userContract,oldUser);
		cr.setData(clearCascadeJSON.format(userContractDao.save(oldUser)).toJSON());
		cr.setMessage("修改成功");
		return cr;
	}
	
	/**
	 * 查询用户详细信息
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse userInfo(HttpServletRequest request,Long id) {
		User user = userService.findOne(id);
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(user).toJSON());
		return cr;
	}
	

	/**
	 * 查询单个员工的合同信息
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/getUserContract", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getUser(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		UserContract userContract = userContractDao.findOne(id);
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(UserContract.class, "id","number", "username","archives","pic","idCard","bankCard","physical",
						"qualification","formalSchooling","agreement","secrecyAgreement","contract","remark","quit").format(userContract).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 通过银行卡号得到银行名称
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/getbank", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getbank(HttpServletRequest request,String idCard) {
		CommonResponse cr = new CommonResponse();
		String bankName = BankUtil.getNameOfBank(idCard);
		cr.setMessage("查询成功");
		cr.setData(bankName);
		return cr;
	}
	
	/**
	 * 合同到期，退休时间到期提醒
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/remind", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse remind(HttpServletRequest request) {
		CommonResponse cr = new CommonResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String , Object> map = new HashMap<String, Object>();
		List<User> userList = 	userService.findAll();
		//退休时间，过滤出有生日的员工
		List<Map<String,Object>> userBirthList = new ArrayList<Map<String,Object>>();
		List<User> userBirth = userList.stream().filter(User->User.getBirthDate()!=null && User.getGender()!=null ).collect(Collectors.toList());
		
		for(User user : userBirth){
			int age = DatesUtil.getAgeByBirth(user.getBirthDate());
			user.setAge(age);
		}
		userDao.save(userBirth);
		
		for(User user : userBirth ){
			Map<String,Object> us = new HashMap<String,Object>();
			int co = DatesUtil.getAgeByBirth(user.getBirthDate());
			long co2 = DatesUtil.getDaySub( DatesUtil.getfristDayOftime(new Date()),DatesUtil.getfristDayOftime(user.getBirthDate()));
			//男
			if(user.getGender()==0 && co==60 && co2<=10){
				us.put("username", user.getUserName());
				us.put("birthDate",sdf.format(user.getBirthDate()));
				userBirthList.add(us);
			//女
			}else if(user.getGender()==1 && co==55  && co2<=10){
				us.put("username", user.getUserName());
				us.put("birthDate", sdf.format(user.getBirthDate()));
				userBirthList.add(us);
			}
			
		}
		//合同到期时间
		List<Map<String , Object>> userContractList = new ArrayList<Map<String , Object>>();
		List<User> userContract = userList.stream().filter(User->User.getContractDateEnd()!=null).collect(Collectors.toList());
		for(User user : userContract ){
			Map<String,Object> us = new HashMap<String,Object>();
			long co = DatesUtil.getDaySub( DatesUtil.getfristDayOftime(new Date()),DatesUtil.getfristDayOftime(user.getContractDateEnd()));
			if(co<=10){
				us.put("username", user.getUserName());
				us.put("contractDateEnd", sdf.format(user.getContractDateEnd()));
				userContractList.add(us);
			}
		}
		map.put("userBirth", userBirthList);
		map.put("userContract", userContractList);
		cr.setMessage("查询成功");
		cr.setData(map);
		return cr;
	}

	
	/**
	 * 测试
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	private CommonResponse test(User user) {
		CommonResponse cr = new CommonResponse();
		List<User> userList = userService.findAll();
		userList = userList.stream().filter(User->User.getBankCard1()!=null).collect(Collectors.toList());
		for(User us : userList){
			String bankName = BankUtil.getNameOfBank(us.getBankCard1());
			us.setAscriptionBank1(bankName);
		}
		userDao.save(userList);
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
