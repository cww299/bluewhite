package com.bluewhite.product.primecost.embroidery.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.embroidery.service.EmbroideryService;

public class EmbroideryAction {
	
	private final static Log log = Log.getLog(EmbroideryAction.class);
	
	@Autowired
	private EmbroideryService embroideryService;
	
	
	/**
	 * 绣花填写
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/addEmbroidery", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addMachinist(HttpServletRequest request,Embroidery embroidery) {
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
			cr.setMessage("添加成功");
		}
		return cr;
	}

}
