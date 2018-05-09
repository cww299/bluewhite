package com.bluewhite.system.user.action;

import java.awt.geom.Area;
import java.text.SimpleDateFormat;

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
import com.bluewhite.common.Constants;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Controller
@RequestMapping("/system/user")
public class UserAction {
	

	@Autowired
	private UserService userService;
	

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(User.class,"id", "userName", "phoneNum", "admin","realname","area","school","department","roles")
				.addRetainTerm(Role.class, "name", "role", "description","id")
				.addRetainTerm(Area.class, "areaName","areaCode","id");
	}


	
	/**
	 * 修改用户信息
	 * @param request 请求
	 * @param user 用户实体类
	 * @return cr
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public CommonResponse changeUser(HttpServletRequest request, User user) {
		CommonResponse cr = new CommonResponse();
		if(user.getId() == null){
			cr.setMessage("id为空");
			return cr;
		}
		User u = userService.findOne(user.getId());
		if(!StringUtils.isEmpty(user.getUserName())){
			u.setUserName(user.getUserName());
		}
		cr.setData(clearCascadeJSON.format(userService.update(u)).toJSON());
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 查询当前用户信息
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getUser(HttpServletRequest request) {
		CurrentUser cu = (CurrentUser) request.getAttribute(Constants.CURRENT_USER);
		User user = userService.findOne(cu.getId());
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(user).toJSON());
		return cr;
	}
	
	
	/**
	 * 重置密码
	 * @param request 请求
	 * @param id 用户id
	 * @return cr
	 */
	@RequestMapping(value = "/pwd/reset", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse reset(HttpServletRequest request, Long id) {
		CommonResponse cr = new CommonResponse();
		boolean success = userService.resetPwdByDefault(id);
		if(success){
			cr.setCode(1500);
			cr.setMessage("重置用户密码失败");
		}else{
			cr.setMessage("重置密码成功");
		}
		
		return cr;
	}

	/**
	 * 新增一个用户
	 * 
	 * @param request 请求
	 * @param user 用户实体类
	 * @return cr
	 */
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse createUser(HttpServletRequest request, User user) {
		CommonResponse cr = new CommonResponse();
		user.setPassword("123456");
		if(!StringUtils.isEmpty(user.getPhone())){
			User u = userService.findByPhone(user.getPhone());
			if(u != null){
//				userService.saveHasExistUser(u,user.getRoleIds());
			}
		}
		cr.setData(clearCascadeJSON.format(userService.save(user)).toJSON());
		return cr;
	}

	/**
	 * 判断username是否存在相同的
	 * 
	 * @param user 请求
	 * @return cr
	 */
	@RequestMapping(value = "/user/usernameExist", method = RequestMethod.GET)
	@ResponseBody
	private CommonResponse exists(User user) {
		CommonResponse cr = new CommonResponse();
		if (user.getUserName() != null) {
			if (userService.findByUsername(user.getUserName()) == null) {
				cr.setData("用户名可以使用");
			} else {
				cr.setCode(ErrorCode.SYSTEM_USER_NAME_REPEAT.getCode());
				cr.setData("用户名已存在");
			}
		} else {
			cr.setData("没有传递必要的参数username");
		}
		return cr;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATE.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}


}
