package com.bluewhite.product.primecost.tailor.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.OrdinaryLaserService;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

@Controller
public class TailorAction {
	
	
	private final static Log log = Log.getLog(TailorAction.class);
	
	
	@Autowired
	private TailorService tailorService;
	
	@Autowired
	private OrdinaryLaserService  ordinaryLaserService;
	

	/**
	 * 裁剪填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addTailor", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addTailor(HttpServletRequest request,Tailor tailor) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(tailor.getId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("裁剪不能为空");
		}else{
			try {
				Tailor oldTailor = tailorService.findOne(tailor.getId());
				BeanCopyUtils.copyNullProperties(oldTailor,tailor);
				tailor.setCreatedAt(oldTailor.getCreatedAt());
				tailorService.saveTailor(tailor);
			} catch (Exception e) {
				cr.setMessage(e.getMessage());
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				return cr;
			}
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	
	/**
	 * 分页查看裁剪
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getTailor", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getTailor(HttpServletRequest request,PageParameter page,Tailor tailor) {
		CommonResponse cr = new CommonResponse(tailorService.findPages(tailor,page));
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * (裁剪普通激光,绣花定位激光，冲床，电烫，电推，手工剪刀)填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addOrdinaryLaser", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addOrdinaryLaser(HttpServletRequest request,OrdinaryLaser ordinaryLaser,PrimeCoefficient primeCoefficient) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(ordinaryLaser.getId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			try {
				ordinaryLaserService.saveOrdinaryLaser(ordinaryLaser,primeCoefficient);
			} catch (Exception e) {
				cr.setMessage(e.getMessage());
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				return cr;
			}
			cr.setMessage("添加成功");
		}
		return cr;
	}
	

	/**
	 * 在使用（手选裁剪方式，选择入成本价格↓）调用
	 *(裁剪普通激光,绣花定位激光，冲床，电烫，电推，手工剪刀) 通过裁剪类型获取各种数据（  得到理论(市场反馈）含管理价值,得到实验推算价格  ）
	 * @param 
	 */
	@RequestMapping(value = "/product/getOrdinaryLaserDate", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getOrdinaryLaserDate(HttpServletRequest request,Tailor tailor) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(tailor.getTailorTypeId()) && StringUtils.isEmpty(tailor.getTailorSize())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("裁剪方式和该裁片的平方（m）不能为空");
			return cr;
		}
		//得到实验推算价格
		OrdinaryLaser  prams = new  OrdinaryLaser();
		prams.setSave(1);
		OrdinaryLaser ordinaryLaser = tailorService.getOrdinaryLaserDate(tailor,prams);
		tailor = tailorService.getTailorDate(tailor,ordinaryLaser);
		cr.setData(tailor);
		cr.setMessage("添加成功");
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
