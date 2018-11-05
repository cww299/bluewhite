package com.bluewhite.product.primecost.machinist.action;

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
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.DateTimePattern;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.machinist.service.MachinistService;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.product.product.service.ProductService;

@Controller 
public class MachinistAction {
	
	
	private final static Log log = Log.getLog(MachinistAction.class);
	
	
	@Autowired
	private MachinistService machinistService;
	
	@Autowired
	private TailorService tailorService;
	
	@Autowired
	private ProductService productService;
	
	
	/**
	 * 机工填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addMachinist", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addMachinist(HttpServletRequest request,Machinist machinist) {
		CommonResponse cr = new CommonResponse();
		if(StringUtils.isEmpty(machinist.getProductId())){
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("产品不能为空");
		}else{
			if(machinist.getId()!=null){
				Machinist oldMachinist = machinistService.findOne(machinist.getId());
				BeanCopyUtils.copyNullProperties(oldMachinist,machinist);
				machinist.setCreatedAt(oldMachinist.getCreatedAt());
			}
			machinistService.saveMachinist(machinist);
			PrimeCost primeCost = new PrimeCost();
			primeCost.setProductId(machinist.getProductId());
			productService.getPrimeCost(primeCost, request);
			machinist.setOneMachinistPrice(primeCost.getOneMachinistPrice());
			cr.setData(machinist);
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	
	/**
	 * 分页查看  机工填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMachinist", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMachinist(HttpServletRequest request,PageParameter page,Machinist machinist) {
		CommonResponse cr = new CommonResponse();
		PageResult<Machinist>  machinistList= new PageResult<>(); 
		if(machinist.getProductId()!=null){
			machinistList = machinistService.findPages(machinist,page);
			PrimeCost primeCost = new PrimeCost();
			primeCost.setProductId(machinist.getProductId());
			productService.getPrimeCost(primeCost, request);
			for(Machinist mt : machinistList.getRows()){
				mt.setOneMachinistPrice(primeCost.getOneMachinistPrice());
			}
		}
		cr.setData(machinistList);
		cr.setMessage("查询成功");
		return cr;
	}
	
	
	/**
	 * 删除机工填写
	 * 
	 */
	@RequestMapping(value = "/product/deleteMachinist", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse deleteMachinist(HttpServletRequest request,String ids) {
		CommonResponse cr = new CommonResponse();
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					machinistService.deleteProductMaterials(id);
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
	 * 获取机缝上道或裁片名称，同时得到裁剪页面的  为机工准备的压价
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMachinistName", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMachinistName(HttpServletRequest request,PageParameter page,Long id) {
		CommonResponse cr = new CommonResponse();
		List<Map<String,Object>> mapList = new ArrayList<>();
		Map<String,Object> map = null;
		//获取（cc裁片）裁剪页面的所有选定的物料名
		List<Tailor> tailorList  = tailorService.findByProductId(id);
		for(Tailor tailor : tailorList){
			map =  new HashMap<String, Object>();
			map.put("name", tailor.getTailorName());
			map.put("price", tailor.getMachinistPriceDown()!=null ? tailor.getMachinistPriceDown() :0);
			mapList.add(map);
		}
		//获取机工页面所有选定的物料名
		List<Machinist> machinistList = machinistService.findByProductId(id);
		for(Machinist machinist : machinistList){
			map =  new HashMap<String, Object>();
			map.put("name", machinist.getMachinistName());
			map.put("price", machinist.getSumPriceDownRemark()!=null ? machinist.getSumPriceDownRemark():0);
			mapList.add(map);
		}
		cr.setData(mapList);
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
