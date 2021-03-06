package com.bluewhite.product.primecost.materials.action;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.entity.poi.ProductMaterialsPoi;
import com.bluewhite.product.primecost.materials.service.ProductMaterialsService;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Controller 
public class ProductMaterialsAction {
	
	@Autowired
	private ProductMaterialsService productMaterialsService;
	
	private ClearCascadeJSON clearCascadeJSON;
	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addRetainTerm(ProductMaterials.class, "id", "productId", "number", "materiel", "overstock", 
						"oneMaterial", "unit","unitCost","manualLoss","batchMaterial","batchMaterialPrice","convertUnit ")
				.addRetainTerm(BaseOne.class, "id" ,"name","type")
				.addRetainTerm(Materiel.class,"id","number","name","price","unit","convertUnit","converts");
	}
	
	/**
	 * dd除裁片以外的所有生产用料填写
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/product/addProductMaterials", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addProductMaterials(ProductMaterials productMaterials) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(productMaterials.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			productMaterialsService.saveProductMaterials(productMaterials);
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	/**
	 * dd除裁片以外的所有生产用料  导入
	 * @return cr
	 */
	@RequestMapping(value = "/product/uploadProductMaterials", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadProductMaterials(@RequestParam(value = "file", required = false) MultipartFile file,
    		Long productId) throws IOException {
		CommonResponse cr = new CommonResponse();
        if(productId == null) {
        	cr.setCode(1500);
        	cr.setMessage("商品id不能为空");
    		return cr;
        }
        InputStream inputStream = file.getInputStream();
		ExcelListener excelListener = new ExcelListener();
		EasyExcel.read(inputStream, ProductMaterialsPoi.class, excelListener).sheet().doRead();
		int count = productMaterialsService.uploadProductMateruals(excelListener, productId);
		inputStream.close();
		cr.setMessage("成功导入" + count + "条数据");
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
			cr.setMessage("生产用料不能为空");
		}else{
			ProductMaterials oldProductMaterials = productMaterialsService.findOne(productMaterials.getId());
			BeanCopyUtils.copyNotEmpty(productMaterials,oldProductMaterials,"");
			productMaterialsService.saveProductMaterials(oldProductMaterials);
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
		PageResult<ProductMaterials>  productMaterialsList = productMaterialsService.findPages(productMaterials,page);
		cr.setData(clearCascadeJSON.format(productMaterialsList).toJSON());
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
			productMaterialsService.deleteProductMaterials(ids);
				cr.setMessage("删除成功");
			}else{
				cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
				cr.setMessage("裁片id不能为空");
			}
		return cr;
	}
	
}
