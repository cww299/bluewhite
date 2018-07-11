package com.bluewhite.product.primecostbasedata.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;

@Controller
public class BaseOneAction {
	
	private final static Log log = Log.getLog(BaseOneAction.class);
	
	@Autowired
	private BaseOneDao baseOneDao;
	
	@Autowired
	private MaterielDao materielDao;
	
	/**
	 * 产品基础数据获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseOne", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getBaseOne(HttpServletRequest request,String type) {
		CommonResponse cr = new CommonResponse();
			
		baseOneDao.findByType(type);
		
		
		cr.setMessage("添加成功");
		return cr;
	}
	
	/**
	 * 物料产品基础数据获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMateriel", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse getMateriel(HttpServletRequest request,String type) {
		CommonResponse cr = new CommonResponse();
			
		baseOneDao.findByType(type);
		
		
		cr.setMessage("添加成功");
		return cr;
	}

}
