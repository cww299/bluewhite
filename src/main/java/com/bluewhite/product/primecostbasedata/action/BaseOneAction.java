package com.bluewhite.product.primecostbasedata.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.BaseOneTimeDao;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseOneTime;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.primecostbasedata.service.MaterielService;

@Controller
public class BaseOneAction {
	
	private final static Log log = Log.getLog(BaseOneAction.class);
	
	@Autowired
	private BaseOneDao baseOneDao;
	
	@Autowired
	private BaseOneTimeDao baseOneTimeDao;
	
	@Autowired
	private MaterielService materielService;
	
	/**
	 * 产品基础数据获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseOne", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseOne(HttpServletRequest request,String type) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(BaseOne.class, "id","name","textualTime","time")
				.format(baseOneDao.findByType(type)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	
	/**
	 * 产品基础数据详细获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getBaseOneTime", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getBaseOneTime(HttpServletRequest request,Long id) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(BaseOneTime.class,"textualTime","time","categorySetting")
				.format(baseOneTimeDao.findByBaseOneId(id)).toJSON());
		cr.setMessage("成功");
		return cr;
	}
	
	/**
	 * 物料产品基础数据获取
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/getMateriel", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getMateriel(HttpServletRequest request,Materiel materiel) {
		CommonResponse cr = new CommonResponse();
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(Materiel.class,"number","name","price","type","unit","changePrice","count","convertUnit","convertPrice")
				.format(materielService.findPages(materiel)).toJSON());
		cr.setMessage("成功");
		return cr;
	}

}
