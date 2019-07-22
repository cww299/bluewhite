package com.bluewhite.personnel.roomboard.action;

import java.text.SimpleDateFormat;
import java.util.List;

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
import com.bluewhite.personnel.roomboard.dao.SundryDao;
import com.bluewhite.personnel.roomboard.entity.Hostel;
import com.bluewhite.personnel.roomboard.entity.Sundry;
import com.bluewhite.personnel.roomboard.service.SundryService;

@Controller
public class SundryAction {

	@Autowired
	private SundryService service;
	@Autowired
	private SundryDao sundryDao;
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(Sundry.class,"id","name","hostel","hostelId","monthDate","rent","water","power","coal","broadband","administration","fixed")
				.addRetainTerm(Hostel.class, "id", "name","number");
	}

	/**
	 * 分页查看
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/personnel/getSundry", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getContact(HttpServletRequest request,PageParameter page,Sundry sundry) {
		CommonResponse cr = new CommonResponse();
		PageResult<Sundry>  mealList= service.findPage(sundry, page); 
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
	@RequestMapping(value = "/fince/addSundry", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addConsumption(HttpServletRequest request, Sundry sundry) {
		CommonResponse cr = new CommonResponse();
		if(sundry.getId() != null){
			Sundry sundry2 = service.findOne(sundry.getId());
				BeanCopyUtils.copyNullProperties(sundry2, sundry);
				sundry.setCreatedAt(sundry2.getCreatedAt());
			cr.setMessage("修改成功");
			service.addSundry(sundry);
		}else{
		List<Sundry> list=sundryDao.findByMonthDate(sundry.getMonthDate());
			if (list.size()>0) {
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("当月已有数据 请勿重复添加");
			}else {
				cr.setMessage("添加成功");
				service.addSundry(sundry);
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
