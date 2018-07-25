package com.bluewhite.product.primecost.cutparts.action;

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
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;

@Controller
public class CutPartsAction {
	
private final static Log log = Log.getLog(CutPartsAction.class);
	
	@Autowired
	private CutPartsService cutPartsService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(CutParts.class,"productId","cutPartsName","cutPartsNumber","allPerimeter","perimeter","materielNumber"
						,"materielName","composite","doubleComposite","complexMaterielNumber","complexMaterielName","oneMaterial","unit"
						,"scaleMaterial","addMaterial","manualLoss","productCost","productRemark","batchMaterial","batchMaterialPrice"
						,"complexProductCost","complexBatchMaterial","batchComplexMaterialPrice","batchComplexAddPrice");
	}
	
	/**
	 * cc裁片填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addCutParts", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addProduct(HttpServletRequest request,CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(cutParts.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			try {
				cutPartsService.saveCutParts(cutParts);
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
	 * cc裁片修改
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/updateCutParts", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateCutParts(HttpServletRequest request,CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(cutParts.getProductId()) || StringUtils.isEmpty(cutParts.getId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品或裁片不能为空");
		}else{
			try {
				CutParts oldCutParts = cutPartsService.findOne(cutParts.getId());
				BeanCopyUtils.copyNullProperties(oldCutParts,cutParts);
				cutParts.setCreatedAt(oldCutParts.getCreatedAt());
				cutPartsService.saveCutParts(cutParts);
			} catch (Exception e) {
				cr.setMessage(e.getMessage());
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				return cr;
			}
			cr.setMessage("修改成功");
		}
		return cr;
	}
	
	
	
	/**
	 * 分页查看cc裁片
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getCutParts", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse productPages(HttpServletRequest request,PageParameter page,CutParts cutParts) {
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(cutPartsService.findPages(cutParts,page))
				.toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 删除cc裁片
	 * 
	 */
	@RequestMapping(value = "/product/deleteCutParts", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteBacth(HttpServletRequest request,CutParts cutParts) {
		CommonResponse cr = new CommonResponse();
		if(cutParts.getId()!=null){
			cutPartsService.deleteCutParts(cutParts);
			cr.setMessage("删除成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("裁片id不能为空");
		}
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
