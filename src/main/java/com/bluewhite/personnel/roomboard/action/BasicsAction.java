package com.bluewhite.personnel.roomboard.action;

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

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.personnel.roomboard.entity.Basics;
import com.bluewhite.personnel.roomboard.service.BasicsService;
import com.bluewhite.system.user.service.UserService;

@Controller
public class BasicsAction {

	@Autowired
	private BasicsService service;
	@Autowired
	private UserService userService;
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Basics.class,"id","time","advertisementPrice","sharePrice","recruitUserPrice","number","occupyPrice","trainPrice","admissionNum","train","trainPrice","Teacher","qualified","type","planNumber","planPrice");
	}
	
	/**
	 * 分页查看招聘成本
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getBasics", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,Basics basics) {
		CommonResponse cr = new CommonResponse();
		Basics result= service.findBasics(basics); 
		cr.setData(clearCascadeJSON.format(result).toJSON());
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
	@RequestMapping(value = "/personnel/addBasics", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Basics basics) {
		CommonResponse cr = new CommonResponse();
		if(basics.getId() != null){
			Basics basics2 = service.findOne(basics.getId());
				BeanCopyUtils.copyNullProperties(basics2, basics);
				basics.setCreatedAt(basics2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addBasics(basics);
		return cr;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/deleteBasics", method = RequestMethod.GET)
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
	 *按部门汇总
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/findBasicsSummary", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findBasicsSummary(HttpServletRequest request,Basics basics) {
		CommonResponse cr = new CommonResponse();
		 List<Map<String, Object>> list = service.findBasicsSummary(basics);
			cr.setData(clearCascadeJSON.format(list).toJSON());
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
