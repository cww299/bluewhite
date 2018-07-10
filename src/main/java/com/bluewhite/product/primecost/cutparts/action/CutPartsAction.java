package com.bluewhite.product.primecost.cutparts.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluewhite.common.Log;
import com.bluewhite.product.primecost.cutparts.service.CutPartsService;

@Controller
public class CutPartsAction {
	
private final static Log log = Log.getLog(CutPartsAction.class);
	
	@Autowired
	private CutPartsService cutPartsService;
	
	
	
	
	
	

}
