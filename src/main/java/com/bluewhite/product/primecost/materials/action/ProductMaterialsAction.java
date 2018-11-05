package com.bluewhite.product.primecost.materials.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.service.ProductMaterialsService;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.product.product.service.ProductService;

@Controller 
public class ProductMaterialsAction {
	
	private final static Log log = Log.getLog(ProductMaterialsAction.class);
	
	
	@Autowired
	private ProductMaterialsService productMaterialsService;
	
	@Autowired
	private ProductService productService;
	
	
	/**
	 * dd除裁片以外的所有生产用料填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addProductMaterials", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addProductMaterials(HttpServletRequest request,ProductMaterials productMaterials) {
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
	 *  dd除裁片以外的所有生产用料修改
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/updateProductMaterials", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateProductMaterials(HttpServletRequest request,ProductMaterials productMaterials) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(productMaterials.getId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("dd除裁片以外的所有生产用料不能为空");
		}else{
			try {
				ProductMaterials oldProductMaterials = productMaterialsService.findOne(productMaterials.getId());
				BeanCopyUtils.copyNullProperties(oldProductMaterials,productMaterials);
				productMaterials.setCreatedAt(oldProductMaterials.getCreatedAt());
				productMaterialsService.saveProductMaterials(productMaterials);
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
	 * 分页查看 dd除裁片以外的所有生产用料填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getProductMaterials", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getProductMaterials(HttpServletRequest request,PageParameter page,ProductMaterials productMaterials) {
		CommonResponse cr = new CommonResponse();
		PageResult<ProductMaterials>  productMaterialsList= new PageResult<>(); 
		if(productMaterials.getProductId()!=null){
			productMaterialsList = productMaterialsService.findPages(productMaterials,page);
			PrimeCost primeCost = new PrimeCost();
			primeCost.setProductId(productMaterials.getProductId());
			productService.getPrimeCost(primeCost, request);
			for(ProductMaterials ps : productMaterialsList.getRows()){
				ps.setOneOtherCutPartsPrice(primeCost.getOneOtherCutPartsPrice());
			}
		
		}
		cr.setData(productMaterialsList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	
	/**
	 * 删除dd除裁片以外的所有生产用料填写
	 * 
	 */
	@RequestMapping(value = "/product/deleteProductMaterials", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteProductMaterials(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					productMaterialsService.deleteProductMaterials(id);
				}
			}
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
