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
import com.bluewhite.personnel.roomboard.entity.SingleMeal;
import com.bluewhite.personnel.roomboard.service.SingleMealService;

@Controller
public class SingleMealAction {

	@Autowired
	private SingleMealService service;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(SingleMeal.class,"id","time","singleMealConsumptionId", "singleMealConsumption","content","price","type");
	}
	/**
	 * 分页查看
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getSingleMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSingleMeal(HttpServletRequest request,PageParameter page,SingleMeal singleMealward) {
		CommonResponse cr = new CommonResponse();
		PageResult<SingleMeal>  mealList= service.findPage(singleMealward, page); 
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
	@RequestMapping(value = "/personnel/addSingleMeal", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addSingleMeal(HttpServletRequest request, SingleMeal singleMeal) {
		CommonResponse cr = new CommonResponse();
		if(singleMeal.getId() != null){
			SingleMeal singleMeal2 = service.findOne(singleMeal.getId());
				BeanCopyUtils.copyNullProperties(singleMeal2, singleMeal);
				singleMeal.setCreatedAt(singleMeal2.getCreatedAt());
			cr.setMessage("修改成功");
		}else{
			cr.setMessage("添加成功");
		}
		service.addSingleMeal(singleMeal);
		return cr;
	}
	
	
	/**
	 * 删除
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/deleteSingleMeal", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteSingleMeal(HttpServletRequest request, String[] ids) {
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
