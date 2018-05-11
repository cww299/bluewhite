package com.bluewhite.product.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductAction {
	
	/**
	 * 跳转到产品信息
	 * @return
	 */
	@RequestMapping(value="/product/information")
	public String index() {
		return "product/information";
		
	}
	
	
}
