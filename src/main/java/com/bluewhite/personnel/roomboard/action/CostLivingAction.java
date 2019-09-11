package com.bluewhite.personnel.roomboard.action;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.roomboard.entity.CostLiving;
import com.bluewhite.personnel.roomboard.service.CostLivingService;

@Controller
public class CostLivingAction {
	
	@Autowired
	private CostLivingService costLivingService;

	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(CostLiving.class,"id","beginTime","endTime","siteType","liveRemark","costType","total","totalCost")
				.addRetainTerm(BaseData.class,"id","name");
	}
	
	
	/**
	 * 分页查看生活费用
	 * 
	 */
	@RequestMapping(value = "/personnel/costLivingPage", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse costLivingPage(CostLiving costLiving,PageParameter page) {
		CommonResponse cr = new CommonResponse();
		cr.setData(clearCascadeJSON.format(costLivingService.findPage(costLiving, page)).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 新增修改
	 * 
	 */
	@RequestMapping(value = "/personnel/saveCostLiving", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(CostLiving costLiving) {
		CommonResponse cr = new CommonResponse();
		if(costLiving.getId() != null){
			CostLiving ot = costLivingService.findOne(costLiving.getId());
			long day= DatesUtil.getDaySub(costLiving.getBeginTime(), costLiving.getEndTime());
			Double averageCost = NumUtils.div(costLiving.getTotalCost(), day, 2);
			costLiving.setAverageCost(averageCost);
			costLivingService.update(costLiving, ot);
			cr.setMessage("修改成功");
		}else{
			costLivingService.saveCostLiving(costLiving);
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/personnel/deleteCostLiving", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteCostLiving(String ids) {
		CommonResponse cr = new CommonResponse();
		int count  = costLivingService.deleteCostLiving(ids);
		cr.setMessage("成功"+count +"条费用");
		return cr;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
}
