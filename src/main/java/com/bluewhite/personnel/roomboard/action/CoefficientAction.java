package com.bluewhite.personnel.roomboard.action;

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
import com.bluewhite.personnel.roomboard.entity.coefficient;
import com.bluewhite.personnel.roomboard.service.CoefficientService;

@Controller
public class CoefficientAction {

	@Autowired
	private CoefficientService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(coefficient.class,"id","positionId","position", "orgNameId","orgName","basics","basics1","basics2","basics3");
	}
	/**
	 * 分页查看
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getCoefficeient", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSingleMeal(HttpServletRequest request,PageParameter page,coefficient coefficient) {
		CommonResponse cr = new CommonResponse();
		PageResult<coefficient>  mealList= service.findPage(coefficient, page); 
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
	@RequestMapping(value = "/personnel/addCoefficient", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addPlan(HttpServletRequest request, coefficient coefficient) {
		CommonResponse cr = new CommonResponse();
		if(coefficient.getId() != null){
			coefficient coefficient2 = service.findOne(coefficient.getId());
				BeanCopyUtils.copyNullProperties(coefficient2, coefficient);
				coefficient.setCreatedAt(coefficient2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addCoefficient(coefficient);
		return cr;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/deleteCoefficient", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deletePlan(HttpServletRequest request, String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count= service.deletes(ids);
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
