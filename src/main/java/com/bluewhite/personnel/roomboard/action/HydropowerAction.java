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
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.dao.HydropowerDao;
import com.bluewhite.personnel.roomboard.entity.Hydropower;
import com.bluewhite.personnel.roomboard.service.HydropowerService;
import com.bluewhite.system.user.entity.User;

@Controller
public class HydropowerAction {

	@Autowired
	private HydropowerService service;
	@Autowired
	private HydropowerDao hydropowerDao;
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Hydropower.class,"id","name","hostel","monthDate","nowDegreeNum","upperDegreeNum","sum","talonNum","price","summaryPrice","type","exceedNum","exceedPrice")
				.addRetainTerm(User.class, "id", "userName","orgName","orgNameId","age");
				
	}

	/**
	 * 分页查看住宿水电费
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getHydropower", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getHydropower(HttpServletRequest request,PageParameter page,Hydropower hydropower) {
		CommonResponse cr = new CommonResponse();
		PageResult<Hydropower>  mealList= service.findPage(hydropower, page); 
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
	@RequestMapping(value = "/fince/addHydropower", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Hydropower hydropower) {
		CommonResponse cr = new CommonResponse();
		if(hydropower.getId() != null){
			Hydropower hydropower2 = service.findOne(hydropower.getId());
				BeanCopyUtils.copyNullProperties(hydropower2, hydropower);
				hydropower.setCreatedAt(hydropower2.getCreatedAt());
				cr.setMessage("修改成功");
				service.addHydropower(hydropower);
		}else{
			Hydropower  hydropowers=hydropowerDao.findByMonthDateAndHostelIdAndType(hydropower.getMonthDate(), hydropower.getHostelId(),hydropower.getType());
			if (hydropowers!=null) {
				if (hydropower.getType()==1) {
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("当月水费已有数据 请勿重复添加");
				}else{
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					cr.setMessage("当月电费已有数据 请勿重复添加");
				}
			}else{
				cr.setMessage("添加成功");
				service.addHydropower(hydropower);
			}
		}
		return cr;
	}

	


	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null, new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
