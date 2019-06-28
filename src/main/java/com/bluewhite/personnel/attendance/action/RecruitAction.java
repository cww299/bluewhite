package com.bluewhite.personnel.attendance.action;

import java.text.SimpleDateFormat;
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

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Recruit;
import com.bluewhite.personnel.attendance.service.RecruitService;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;
import com.bluewhite.system.user.service.UserService;

@Controller
public class RecruitAction {

	@Autowired
	private RecruitService service;
	@Autowired
	private UserService userService;
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Recruit.class,"id","user","remarksThree", "platformId","testTime","platformName","position","positionId","time","position","orgNameId","orgName","name","gender","phone","livingAddress","entry","type","remarks","typeOne","remarksOne","typeTwo","remarksTwo","state","adopt");
	}

	private ClearCascadeJSON clearCascadeJSONUser;
	{
		clearCascadeJSONUser = ClearCascadeJSON.get()
		.addRetainTerm(User.class,"id","fileId","idCardEnd","price","status","workTime","number","pictureUrl", "userName", "phone","position","orgName","idCard",
				"nation","email","gender","birthDate","group","idCard","permanentAddress","livingAddress","marriage","procreate","education"
				,"school","major","contacts","information","entry","estimate","actua","socialSecurity","bankCard1","bankCard2","agreement","safe","commitment"
				,"frequency","quitDate","quit","reason","train","remark","userContract","commitments"
				,"agreementId","company","age","type","ascriptionBank1","sale","roles")
		.addRetainTerm(Group.class, "id","name", "type", "price")
		.addRetainTerm(Role.class, "name", "role", "description","id")
		.addRetainTerm(BaseData.class, "id","name", "type")
		.addRetainTerm(UserContract.class, "id","number", "username","archives","pic","idCard","bankCard","physical",
				"qualification","formalSchooling","agreement","secrecyAgreement","contract","remark","quit");
	}
	/**
	 * 分页查看招聘
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getRecruit", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Recruit recruit) {
		CommonResponse cr = new CommonResponse();
		PageResult<Recruit>  mealList= service.findPage(recruit, page); 
		cr.setData(clearCascadeJSON.format(mealList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/addRecruit", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Recruit recruit) {
		CommonResponse cr = new CommonResponse();
		if(recruit.getId() != null){
			Recruit recruit2 = service.findOne(recruit.getId());
				BeanCopyUtils.copyNullProperties(recruit2, recruit);
				recruit.setCreatedAt(recruit2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addRecruit(recruit);
		return cr;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/deleteRecruit", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteConsumption(HttpServletRequest request, String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				service.delete(id); 
				count++;
			}
		}
		cr.setMessage("成功删除"+count+"条");
		return cr;
	}
	
	/**
	 * 审核
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/updateRecruit", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse updateConsumption(HttpServletRequest request, String[] ids,Integer state) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
			Recruit recruit=service.findOne(id);
			if (state==1) {
				User user=new User();
				user.setUserName(recruit.getName());
				user.setPhone(recruit.getPhone());
				user.setForeigns(0);
				user.setQuit(0);
				user.setEntry(recruit.getTestTime());
				userService.addUser(user);
				recruit.setUserId(user.getId());
				recruit.setState(state);
				service.save(recruit);
				count++;
				cr.setMessage("成功入职"+count+"人");
			}
			if (state==2) {
				recruit.setState(state);
				service.save(recruit);
				count++;
				cr.setMessage("拒绝入职"+count+"人");
			}
			if (state==3) {
				recruit.setState(state);
				service.save(recruit);
				count++;
				cr.setMessage("即将入职"+count+"人");
			}
			}
		}
		
		return cr;
	}
	
	/**
	 * 招聘汇总
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/Statistics", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse Statistics(HttpServletRequest request, Recruit recruit) {
		CommonResponse cr = new CommonResponse();
		 List<Map<String, Object>> list = service.Statistics(recruit);
			cr.setData(clearCascadeJSON.format(list).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 短期入职人员
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/soon", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse soon(HttpServletRequest request, Recruit recruit) {
		CommonResponse cr = new CommonResponse();
		 List<Recruit> list = service.soon(recruit);
			cr.setData(clearCascadeJSON.format(list).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 离职人员
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/usersl", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse users(HttpServletRequest request, Recruit recruit) {
		CommonResponse cr = new CommonResponse();
		 List<User> list = service.users(recruit);
			cr.setData(clearCascadeJSONUser.format(list).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
