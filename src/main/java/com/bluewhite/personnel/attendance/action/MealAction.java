package com.bluewhite.personnel.attendance.action;

import java.text.SimpleDateFormat;

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
import com.bluewhite.personnel.attendance.entity.Meal;
import com.bluewhite.personnel.attendance.service.MealService;
import com.bluewhite.system.user.entity.User;

@Controller
public class MealAction {

	@Autowired
	private MealService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Attendance.class, "userId", "user", "mode", "tradeDaysTime", "price")
				.addRetainTerm(User.class, "id", "userName","orgName","orgNameId");
	}

	/**
	 * 分页查看报餐
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Meal meal) {
		CommonResponse cr = new CommonResponse();
		PageResult<Meal>  mealList= service.findPage(meal, page); 
		cr.setData(clearCascadeJSON.format(mealList).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	@RequestMapping(value = "/fince/addMeal", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Meal meal) {
		CommonResponse cr = new CommonResponse();
		if(meal.getId() != null){
				Meal meal2 = service.findOne(meal.getId());
				BeanCopyUtils.copyNullProperties(meal2, meal);
				meal.setCreatedAt(meal2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addMeal(meal);
		return cr;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
