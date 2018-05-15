package com.bluewhite.product.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.Log;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.product.entity.Product;
import com.bluewhite.product.service.ProductService;

@Controller
public class ProductAction {
	
	private static final Log log = Log.getLog(ProductAction.class);
	
	@Autowired
	private ProductService productService;
	
	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get().addRetainTerm(Product.class,
				"id","number", "name");
	}
	
	
	/**
	 * 跳转到产品信息
	 * @return
	 */
	@RequestMapping(value="/product/information")
	public String index() {
		return "product/information";
		
	}
	
	
	/**
	 * 分页查看所有的产品
	 * 
	 * @param request 请求
	 * @return cr
	 * @throws Exception
	 */
	@RequestMapping(value = "/productPages", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse productPages(HttpServletRequest request,PageParameter page,Product product) {
		CurrentUser cu = SessionManager.getUserSession();
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(productService.findPages(product,page))
				.toJSON());
		return cr;
	}
	
	
}
