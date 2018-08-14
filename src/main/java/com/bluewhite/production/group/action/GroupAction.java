package com.bluewhite.production.group.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.production.group.service.GroupService;
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
	
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Group.class,"id","name","price","type","users","userName","userId","kindWork","womanUserName","womanUserId","remark")
				.addRetainTerm(User.class,"id","userName")
				.addRetainTerm(BaseData.class, "id","name", "type");
	}
	
	/**
	 * 添加，修改分组
	 * 
	 * type 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工,4=二楼机工)
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
	 * 根据条件查询分组
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/allGroup", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allGroup(HttpServletRequest request,Group group) {
		CommonResponse cr = new CommonResponse();
		List<Group> groupAll = null;
		if(group.getId()==null){
			Set<User> userlist = new HashSet<User>();
			List<Group>	groupList = groupService.findList(group);
			for(Group gr : groupList){
				userlist.addAll(gr.getUsers());
			}
			group.setUsers(userlist);
			groupAll = new ArrayList<Group>();
			groupAll.add(group);
		}else{
			groupAll = groupService.findList(group);
			
			if(group.getType()==1 || group.getType()==2){
				List<Temporarily> temporarilyList =  temporarilyDao.findByType(group.getType());
				if(temporarilyList.size()>0){
					Set<User> userlist  = groupAll.get(0).getUsers();
					for(Temporarily temporarily : temporarilyList){
						User user = userService.findOne(temporarily.getUserId());
						userlist.add(user);
					}
				}
			}
		}
		for(Group gr : groupAll){
			Set<User> users= gr.getUsers().stream().filter(u -> u.getStatus()!=1).collect(Collectors.toSet());
			gr.setUsers(users);
		}
		cr.setData(clearCascadeJSON.format(groupAll).toJSON());
		cr.setMessage("查询成功");
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
		if(user.getUserIds()!=null){
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
			cr.setMessage("用户不能为空");
		}
		return cr;
	}
	
	/**
	 * 删除任务
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/production/group/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse delete(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if(!StringUtils.isEmpty(ids)){
			groupService.deleteGroup(ids);
			cr.setMessage("删除成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("不能为空");
		}
		return cr;
	}
	
	
	/**
	 * 新增借调人员
	 * 
	 * (1=一楼质检)
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/addTemporarily", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTemporarily(HttpServletRequest request,String ids,Integer type) {
		CommonResponse cr = new CommonResponse();
		if(ids!=null){
			String[] userIds = ids.split(",");
			for (String id : userIds) {
				Temporarily temporarily = new Temporarily();
				Long userId = Long.parseLong(id);
				User user = userService.findOne(userId);
				temporarily.setUserId(userId);
				temporarily.setUserName(user.getUserName());
				temporarily.setType(type);
				temporarilyDao.save(temporarily);
			}
		}
		cr.setMessage("添加成功");
		return cr;
	}
	
	
	/**
	 * 新增借调人员
	 * 
	 * (2=一楼包装)
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/addTemporarilyTwo", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTemporarily(HttpServletRequest request,Temporarily temporarily) {
		CommonResponse cr = new CommonResponse();
		temporarilyDao.save(temporarily);
		cr.setMessage("添加成功");
		return cr;
	}
	
	
	
	/**
	 * 查询借调人员
	 * 
	 * (1=一楼质检，2=一楼包装)
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/getTemporarily", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getTemporarily(HttpServletRequest request,Integer type) {
		CommonResponse cr = new CommonResponse();
		cr.setData(temporarilyDao.findByType(type));
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 归还借调人员
	 * 
	 * (1=一楼质检，2=一楼包装)
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/production/deleteTemporarily", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteTemporarily(HttpServletRequest request,String[] ids) {
		CommonResponse cr = new CommonResponse();
		for (String id : ids) {
			temporarilyDao.delete(Long.parseLong(id));
		}
		cr.setMessage("删除成功");
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
