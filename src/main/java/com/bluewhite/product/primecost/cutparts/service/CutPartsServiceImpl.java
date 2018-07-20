package com.bluewhite.product.primecost.cutparts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;

@Service
public class CutPartsServiceImpl  extends BaseServiceImpl<CutParts, Long> implements CutPartsService{

	@Autowired
	private CutPartsDao dao;
	
	
	@Override
	public CutParts saveCutParts(CutParts cutParts) {
		
		
		
		return null;
	}

}
