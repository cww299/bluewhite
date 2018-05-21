package com.bluewhite.production.group.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.service.GroupService;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Controller
public class GroupAction {
	
private static final Log log = Log.getLog(GroupAction.class);
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Group.class,"id","name","price","type","users")
				.addRetainTerm(User.class,"id","userName")
				;
	}
	
	/**
	 * 添加，修改分组
	 * 
	 * type 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/addGroup", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addGroup(HttpServletRequest request,Group group) {
		CommonResponse cr = new CommonResponse();
		if(group.getId()!=null){
			Group oldGroup = groupService.findOne(group.getId());
			BeanCopyUtils.copyNullProperties(oldGroup,group);
			group.setCreatedAt(oldGroup.getCreatedAt());
			groupService.update(group);
			cr.setMessage("工序修改成功");
			
		}else{
			if(group.getType()!=null){
				cr.setData(clearCascadeJSON.format(groupService.save(group)).toJSON());
				cr.setMessage("分组添加成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("分组类型不能为空");
			}
		}
		return cr;
	}
	
	
	/**
	 * 查询单个分组
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/getGroupOne", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getGroupOne(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		if(id!=null){
			cr.setData(clearCascadeJSON.format(groupService.findOne(id)).toJSON());
			cr.setMessage("查询成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("组不能为空");
		}
		return cr;
	}
	
	/**
	 * 查询分组
	 * 
	 * type 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/getGroup", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getGroup(HttpServletRequest request,Group group) {
		CommonResponse cr = new CommonResponse();
		if(group.getType()!=null){
			cr.setData(clearCascadeJSON.format(groupService.findByType(group.getType())).toJSON());;
			cr.setMessage("查询成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("分组类型不能为空");
		}
		return cr;
	}
	
	
	/**
	 * 给用户分组
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/userGroup", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse userGroup(HttpServletRequest request,User user) {
		CommonResponse cr = new CommonResponse();
		if(user.getUserIds()!=null &&  user.getGroupId()!=null){
			String[] userIds = user.getUserIds().split(",");
			for (String id : userIds) {
				Long userId = Long.parseLong(id);
				User userGroup = userService.findOne(userId);
				userGroup.setGroupId(user.getGroupId());
				userService.save(userGroup);
			}
			cr.setMessage("分组成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("用户和分组不能为空");
		}
		return cr;
	}

}
