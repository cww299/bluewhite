package com.bluewhite.personnel.attendance.action;

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

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.Hostel;
import com.bluewhite.personnel.attendance.service.HostelService;
import com.bluewhite.system.user.entity.User;

@Controller
public class HostelAction {

	@Autowired
	private HostelService service;
	
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Attendance.class, "name")
				.addRetainTerm(User.class, "id", "userName","orgName","orgNameId","age");
	}

	/**
	 * 分页查看报餐
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getHostel", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Hostel hostel) {
		CommonResponse cr = new CommonResponse();
		PageResult<Hostel>  mealList= service.findPage(hostel, page); 
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
	@RequestMapping(value = "/fince/addHostel", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Hostel hostel) {
		CommonResponse cr = new CommonResponse();
		if(hostel.getId() != null){
			Hostel hostel2 = service.findOne(hostel.getId());
				BeanCopyUtils.copyNullProperties(hostel2, hostel);
				hostel.setCreatedAt(hostel2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addHostel(hostel);
		return cr;
	}
	
	/**
	 * 员工分配
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/updateUserHostelId", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateUserHostelId(Hostel hostel) {
		CommonResponse cr = new CommonResponse();
		cr.setMessage("分配成功");
		service.updateUserHostelId(hostel);
		return cr;
	}
	
	/**
	 * 删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/fince/deleteHostel", method = RequestMethod.GET)
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
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
