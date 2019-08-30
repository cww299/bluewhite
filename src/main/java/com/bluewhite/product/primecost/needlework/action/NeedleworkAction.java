package com.bluewhite.product.primecost.needlework.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.materials.service.ProductMaterialsService;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
import com.bluewhite.product.primecost.needlework.service.NeedleworkService;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.product.product.service.ProductService;
@Controller
public class NeedleworkAction {
	
	
private final static Log log = Log.getLog(NeedleworkAction.class);
	
	@Autowired
	private NeedleworkService needleworkService;
	@Autowired
	private CutPartsService cutPartsService;
	@Autowired
	private ProductMaterialsService productMaterialsService;
	@Autowired
	private ProductService productService;
	
	/**
	 * 针工填写
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/product/addNeedlework", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addNeedlework(HttpServletRequest request,Needlework needlework) {
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
			PrimeCost primeCost = new PrimeCost();
			primeCost.setProductId(needlework.getProductId());
			productService.getPrimeCost(primeCost);
			needlework.setOneNeedleworkPrice(primeCost.getOneNeedleworkPrice());
			cr.setData(needlework);
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
	public CommonResponse getNeedlework(HttpServletRequest request,PageParameter page,Needlework needlework) {
		CommonResponse cr = new CommonResponse();
		PageResult<Needlework>  needleworkList= new PageResult<>(); 
		if(needlework.getProductId()!=null){
			needleworkList = needleworkService.findPages(needlework,page);
			PrimeCost primeCost = new PrimeCost();
			primeCost.setProductId(needlework.getProductId());
			productService.getPrimeCost(primeCost);
			for(Needlework nw : needleworkList.getRows()){
				nw.setOneNeedleworkPrice(primeCost.getOneNeedleworkPrice());
			}
		}
		cr.setData(needleworkList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 删除 针工填写
	 * 
	 */
	@RequestMapping(value = "/product/deleteNeedlework", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteNeedlework(HttpServletRequest request,String ids) {
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
	
	
	
	/**
	 * 该工序有可能用到的物料(机工，针工，包装)
	 * 
	 * @param request 请求
	 * @return cr
	 */
	@RequestMapping(value = "/product/getOverStock", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getOverStock(HttpServletRequest request,String type,Long productId) {
		CommonResponse cr = new CommonResponse();
		Long id = null  ;
		if(type.equals("machinist")){
			id = (long) 81;
		}
		if(type.equals("needlework")){
			id = (long) 82;
		}
		if(type.equals("pack")){
			id = (long) 83;
		}
		List<Map<String,Object>> overStock = new ArrayList<Map<String,Object>>();
		List<CutParts> cutPartsList = null;
		List<ProductMaterials> productMaterialsList = null;
		Map<String,Object> map =null;
		switch (id.intValue()) {
		case 79://裁剪
			break;
		case 80://绣花
			break;
		case 81://机工
			cutPartsList = cutPartsService.findByProductIdAndOverstockId(productId,id);
			productMaterialsList =productMaterialsService.findByProductIdAndOverstockId(productId,id);
			break;
		case 82://针工
			cutPartsList = cutPartsService.findByProductIdAndOverstockId(productId,id);
			productMaterialsList = productMaterialsService.findByProductIdAndOverstockId(productId,id);
			break;
		case 83://内包装 //外包装
			cutPartsList = cutPartsService.findByProductIdAndOverstockId(productId,(long)83);
			productMaterialsList = productMaterialsService.findByProductIdAndOverstockId(productId,(long)83);
			List<CutParts> cutPartsList1 = cutPartsService.findByProductIdAndOverstockId(productId,(long)84);
			List<ProductMaterials> productMaterialsList1 = productMaterialsService.findByProductIdAndOverstockId(productId,(long)84);
			cutPartsList.addAll(cutPartsList1);
			productMaterialsList.addAll(productMaterialsList1);
			break;
		case 85://库房出入
			break;
		default:
			break;
		}
		for(CutParts ct : cutPartsList){
			map = new HashMap<>();
			map.put("name", ct.getCutPartsName());
			overStock.add(map);
		}
		for(ProductMaterials pm : productMaterialsList){
			map = new HashMap<>();
			map.put("name", pm.getMateriel().getName());
			overStock.add(map);
		}
		cr.setData(overStock);
		cr.setMessage("查询成功");
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
