package com.bluewhite.product.primecost.embroidery.action;

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
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.embroidery.service.EmbroideryService;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.product.product.service.ProductService;
@Controller
public class EmbroideryAction {
	
	private final static Log log = Log.getLog(EmbroideryAction.class);
	
	@Autowired
	private EmbroideryService embroideryService;
	
	@Autowired
	private TailorService tailorService;
	
	@Autowired
	private ProductService productService;
	
	
	/**
	 * 绣花填写
	 * 
	 * 其中面料数据通过裁剪页面获得，传入面料字符串的同时，将裁剪页面id传入
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addEmbroidery", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addEmbroidery(HttpServletRequest request,Embroidery embroidery) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(embroidery.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			if(embroidery.getId()!=null){
				Embroidery oldEmbroidery = embroideryService.findOne(embroidery.getId());
				BeanCopyUtils.copyNullProperties(oldEmbroidery,embroidery);
				embroidery.setCreatedAt(oldEmbroidery.getCreatedAt());
			}
			embroideryService.saveEmbroidery(embroidery);
			PrimeCost primeCost = new PrimeCost();
			primeCost.setProductId(embroidery.getProductId());
			productService.getPrimeCost(primeCost, request);
			embroidery.setOneEmbroiderPrice(primeCost.getOneEmbroiderPrice());
			cr.setData(embroidery);
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	
	/**
	 * 分页查看 绣花填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getEmbroidery", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getEmbroidery(HttpServletRequest request,PageParameter page,Embroidery embroidery) {
		CommonResponse cr = new CommonResponse();
		PageResult<Embroidery>  embroideryList= new PageResult<>(); 
		if(embroidery.getProductId()!=null){
			embroideryList = embroideryService.findPages(embroidery,page);
			PrimeCost primeCost = new PrimeCost();
			primeCost.setProductId(embroidery.getProductId());
			productService.getPrimeCost(primeCost, request);
			for(Embroidery ey : embroideryList.getRows()){
				ey.setOneEmbroiderPrice(primeCost.getOneEmbroiderPrice());
			}
		}
		cr.setData(embroideryList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 删除绣花填写
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
					embroideryService.deleteEmbroidery(id);
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
	 * 获取绣花填写面料值
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getEmbroideryName", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getEmbroideryName(HttpServletRequest request,Long productId) {
		CommonResponse cr = new CommonResponse();
		cr.setData(tailorService.findByProductId(productId));
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
