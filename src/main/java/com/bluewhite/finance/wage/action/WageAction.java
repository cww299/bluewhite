package com.bluewhite.finance.wage.action;

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
import com.bluewhite.finance.wage.entity.Wage;
import com.bluewhite.finance.wage.service.WageService;
import com.bluewhite.system.user.entity.User;

@Controller
public class WageAction {

	@Autowired
	private WageService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Wage.class,"id","userId","user", "time","wage","type","wages")
				.addRetainTerm(User.class, "id", "userName","orgName","orgNameId");
	}
	/**
	 * 分页查看
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getWage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSingleMeal(HttpServletRequest request,PageParameter page,Wage wage) {
		CommonResponse cr = new CommonResponse();
		PageResult<Wage>  mealList= service.findPages(wage, page);
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
	@RequestMapping(value = "/personnel/addWage", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addSingleMeal(HttpServletRequest request, Wage wage) {
		CommonResponse cr = new CommonResponse();
		if(wage.getId() != null){
			Wage wage2 = service.findOne(wage.getId());
				BeanCopyUtils.copyNullProperties(wage2, wage);
				wage.setCreatedAt(wage2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addWage(wage);
		return cr;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/deleteWage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteSingleMeal(HttpServletRequest request, String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count= service.deleteWage(ids);
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
