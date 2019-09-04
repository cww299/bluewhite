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
import com.bluewhite.personnel.roomboard.entity.Plan;
import com.bluewhite.personnel.roomboard.service.PlanService;

@Controller
public class PlanAction {

	@Autowired
	private PlanService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Plan.class,"id","positionId","position", "orgNameId","orgName","number","estimate","target","coefficient","time");
	}
	/**
	 * 分页查看
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getPlan", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSingleMeal(HttpServletRequest request,PageParameter page,Plan plan) {
		CommonResponse cr = new CommonResponse();
		PageResult<Plan>  mealList= service.findPage(plan, page); 
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
	@RequestMapping(value = "/personnel/addPlan", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addPlan(HttpServletRequest request, Plan plan) {
		CommonResponse cr = new CommonResponse();
		if(plan.getId() != null){
			Plan plan2 = service.findOne(plan.getId());
				BeanCopyUtils.copyNullProperties(plan2, plan);
				plan.setCreatedAt(plan2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addPlan(plan);
		return cr;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/deletePlan", method = RequestMethod.GET)
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
