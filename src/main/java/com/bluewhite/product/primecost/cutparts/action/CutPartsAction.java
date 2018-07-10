package com.bluewhite.product.primecost.cutparts.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.Log;
import com.bluewhite.common.annotation.SysLogAspectAnnotation;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.sys.entity.SysLog;

@Controller
public class CutPartsAction {
	
private final static Log log = Log.getLog(CutPartsAction.class);
	
	@Autowired
	private CutPartsService cutPartsService;
	
	
	
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
			cr.setMessage("产品都不能为空");
		}else{
			cutPartsService.save(cutParts);
			cr.setMessage("添加成功");
		}
		return cr;
	}
	
	
	

}
