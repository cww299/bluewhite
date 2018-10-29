package com.bluewhite.product.primecost.needlework.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.bluewhite.product.primecost.embroidery.action.EmbroideryAction;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.embroidery.service.EmbroideryService;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
import com.bluewhite.product.primecost.needlework.service.NeedleworkService;
import com.bluewhite.product.primecost.tailor.service.TailorService;

public class NeedleworkAction {
	
	
private final static Log log = Log.getLog(NeedleworkAction.class);
	
	@Autowired
	private NeedleworkService needleworkService;
	
	@Autowired
	private TailorService tailorService;
	
	
	/**
	 * 针工填写
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/product/addNeedlework", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addMachinist(HttpServletRequest request,Needlework needlework) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(needlework.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			if(needlework.getId()!=null){
				Needlework oldNeedlework = needleworkService.findOne(needlework.getId());
				BeanCopyUtils.copyNullProperties(oldNeedlework,needlework);
				needlework.setCreatedAt(oldNeedlework.getCreatedAt());
			}
			needleworkService.saveNeedlework(needlework);
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	
	/**
	 * 分页查看 针工填写
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/product/getNeedlework", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getEmbroidery(HttpServletRequest request,PageParameter page,Needlework needlework) {
		CommonResponse cr = new CommonResponse();
		cr.setData(needleworkService.findPages(needlework,page));
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 删除 针工填写
	 * 
	 */
	@RequestMapping(value = "/product/deleteEmbroidery", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteEmbroidery(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					needleworkService.deleteNeedlework(id);
				}
			}
				cr.setMessage("删除成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("裁片id不能为空");
			}
		return cr;
	}
	
	
	
//	/**
//	 * 获取绣花填写面料值
//	 * 
//	 * @param request 请求
//	 * @return cr
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/product/getEmbroideryName", method = RequestMethod.GET)
//	@ResponseBody
//	public CommonResponse getMachinistName(HttpServletRequest request,Long productId) {
//		CommonResponse cr = new CommonResponse();
//		cr.setData(tailorService.findByProductId(productId));
//		cr.setMessage("查询成功");
//		return cr;
//	}
	
	
	
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
