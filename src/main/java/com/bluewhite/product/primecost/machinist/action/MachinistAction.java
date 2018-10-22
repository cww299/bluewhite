package com.bluewhite.product.primecost.machinist.action;

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
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.machinist.service.MachinistService;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;

@Controller 
public class MachinistAction {
	
	
	private final static Log log = Log.getLog(MachinistAction.class);
	
	
	@Autowired
	private MachinistService machinistService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON
				.get()
				.addRetainTerm(Machinist.class,"productId","number","materialsName","oneMaterial","unit","unitId","unitCost"
						,"manualLoss","productCost","productUnit","batchMaterial","batchMaterialPrice");
	}
	
	
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
			try {
				machinistService.saveMachinist(machinist);
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
	 * 分页查看  机工填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMachinist", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMachinist(HttpServletRequest request,PageParameter page,Machinist machinist) {
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(machinistService.findPages(machinist,page))
				.toJSON());
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
