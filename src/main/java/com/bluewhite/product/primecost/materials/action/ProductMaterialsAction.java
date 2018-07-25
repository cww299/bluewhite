package com.bluewhite.product.primecost.materials.action;

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

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.service.ProductMaterialsService;

@Controller 
public class ProductMaterialsAction {
	
	private final static Log log = Log.getLog(ProductMaterialsAction.class);
	
	
	@Autowired
	private ProductMaterialsService productMaterialsService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(ProductMaterials.class,"productId","cutPartsName","cutPartsNumber","allPerimeter","perimeter","materielNumber"
						,"materielName","composite","doubleComposite","complexMaterielNumber","complexMaterielName","oneMaterial","unit"
						,"scaleMaterial","addMaterial","manualLoss","productCost","productRemark","batchMaterial","batchMaterialPrice"
						,"complexProductCost","complexBatchMaterial","batchComplexMaterialPrice","batchComplexAddPrice");
	}
	
	/**
	 * dd除裁片以外的所有生产用料填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addProductMaterials", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addProduct(HttpServletRequest request,ProductMaterials productMaterials) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(productMaterials.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			try {
				productMaterialsService.saveProductMaterials(productMaterials);
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
	 * 分页查看 dd除裁片以外的所有生产用料填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getProductMaterials", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse productPages(HttpServletRequest request,PageParameter page,ProductMaterials productMaterials) {
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(productMaterialsService.findPages(productMaterials,page))
				.toJSON());
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 删除dd除裁片以外的所有生产用料填写
	 * 
	 */
	@RequestMapping(value = "/product/deleteProductMaterials", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteBacth(HttpServletRequest request,ProductMaterials productMaterials) {
		CommonResponse cr = new CommonResponse();
		if(productMaterials.getId()!=null){
			productMaterialsService.deleteProductMaterials(productMaterials);
			cr.setMessage("删除成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("id不能为空");
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
