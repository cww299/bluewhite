package com.bluewhite.personnel.attendance.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
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
import com.bluewhite.personnel.attendance.entity.Live;
import com.bluewhite.personnel.attendance.service.LiveService;
import com.bluewhite.system.user.entity.User;

@Controller
public class LiveAction {

	@Autowired
	private LiveService service;
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Attendance.class,"id","name","hostel","bed","inLiveDate","otLiveDate","liveRemark","type")
				.addRetainTerm(User.class, "id", "userName","orgName","orgNameId","age");
				
	}

	/**
	 * 分页查看报餐
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getLive", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Live live) {
		CommonResponse cr = new CommonResponse();
		PageResult<Live>  mealList= service.findPage(live, page); 
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
	@RequestMapping(value = "/fince/addLive", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Live live) {
		CommonResponse cr = new CommonResponse();
		if(live.getId() != null){
			Live live2 = service.findOne(live.getId());
				BeanCopyUtils.copyNullProperties(live2, live);
				live.setCreatedAt(live2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addLive(live);
		return cr;
	}

	/**
	 * 宿舍分摊
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getSummaryShare", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findShareSummary(Date monthDate,Long hostelId,Long orgNameId) {
		CommonResponse cr = new CommonResponse();
		 List<Map<String, Object>> list = service.findShareSummary(monthDate,hostelId,orgNameId); 
		cr.setData(clearCascadeJSON.format(list).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 部门分摊
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getSummaryDepartment", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findShareSummaryDepartment(Date monthDate,Long hostelId,Long orgNameId) {
		CommonResponse cr = new CommonResponse();
		 List<Map<String, Object>> list = service.findShareSummaryDepartment(monthDate,hostelId,orgNameId); 
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
