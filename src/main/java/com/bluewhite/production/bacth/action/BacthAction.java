package com.bluewhite.production.bacth.action;

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
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.entity.Product;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.bacth.service.BacthService;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;

@Controller
public class BacthAction {
	
private static final Log log = Log.getLog(BacthAction.class);
	
	@Autowired
	private BacthService bacthService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Bacth.class,"id","time","statusTime","bacthDeedlePrice","receive","sumReworkPrice","sumTaskPrice","regionalPrice","name","allotTime","number","createdAt","remarks","status","bacthDepartmentPrice","bacthHairPrice","bacthNumber","price","type","product")
				.addRetainTerm(Product.class,"id","number","name");
	}
	
	/**
	 * 给产品添加批次（修改）
	 * 
	 * 是否完成（0=默认未完成，1=完成，status传参）
	 *  type (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@RequestMapping(value = "/bacth/addBacth", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addBacth(HttpServletRequest request,Bacth bacth) {
		CommonResponse cr = new CommonResponse();
		if(bacth.getId()!=null){
			Bacth oldBacth = bacthService.findOne(bacth.getId());
			BeanCopyUtils.copyNullProperties(oldBacth,bacth);
			bacth.setCreatedAt(oldBacth.getCreatedAt());
			bacthService.update(bacth);
			cr.setMessage("修改成功");
		}else{
			if(bacth.getProductId()!=null){
				try {
					bacthService.saveBacth(bacth);
				} catch (Exception e) {
					cr.setMessage(e.getMessage());
					cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
					return cr;
				}
				cr.setMessage("添加成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("产品不能为空");
			}
		}
		return cr;
	}
	
	/** 
	 * 查询所有批次
	 * 
	 */
	@RequestMapping(value = "/bacth/allBacth", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse allBacth(HttpServletRequest request,Bacth bacth,PageParameter page) {
		CommonResponse cr = new CommonResponse();
			cr.setData(clearCascadeJSON.format(bacthService.findPages(bacth, page)).toJSON());
			cr.setMessage("查询成功");
		return cr;
	}
	
	/**
	 * 删除批次
	 * 
	 */
	@RequestMapping(value = "/bacth/deleteBacth", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteBacth(HttpServletRequest request,Bacth bacth) {
		CommonResponse cr = new CommonResponse();
		if(bacth.getId()!=null){
			bacthService.deleteBacth(bacth.getId());
			cr.setMessage("删除成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("批次不能为空");
		}
		return cr;
	}
	
	
	/** 
	 * 一键完成批次，改变status，会转入包装列表
	 * 
	 */
	@RequestMapping(value = "/bacth/statusBacth", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse statusBacth(HttpServletRequest request,String[] ids) {
		CommonResponse cr = new CommonResponse();
		int count = bacthService.statusBacth(ids);
		cr.setMessage("成功完成"+count+"批次");
		return cr;
	}
	
	/****************************一楼包装*************************/
	
	/** 
	 * 一键接收批次，改变批次的数量，重新变成批次到包装列表，type=2
	 * 
	 */
	@RequestMapping(value = "/bacth/receiveBacth", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse receiveBacth(HttpServletRequest request,String[] ids,String[] numbers) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		try {
			count = bacthService.receiveBacth(ids,numbers);
		} catch (Exception e) {
			cr.setMessage(e.getMessage());
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			return cr;
		}
		cr.setMessage("成功完成"+count+"条批次");
		return cr;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				DateTimePattern.DATEHMS.getPattern());
		binder.registerCustomEditor(java.util.Date.class, null,
				new CustomDateEditor(dateTimeFormat, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}


}
