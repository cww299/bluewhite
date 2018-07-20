package com.bluewhite.product.primecost.cutparts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;

@Service
public class CutPartsServiceImpl  extends BaseServiceImpl<CutParts, Long> implements CutPartsService{

	@Autowired
	private CutPartsDao dao;
	
	
	@Override
	public CutParts saveCutParts(CutParts cutParts) throws Exception {
		if(StringUtils.isEmpty(cutParts.getCutPartsNumber())){
			throw new ServiceException("使用片数不能为空");
		}
		if(StringUtils.isEmpty(cutParts.getOneMaterial())){
			throw new ServiceException("单片用料不能为空");
		}
		cutParts.setAddMaterial(cutParts.getCutPartsNumber()*cutParts.getOneMaterial());
		
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterial(0.0);
		}else{
			cutParts.setBatchMaterial(0.0);
		}
		
		
		//各单片比全套用料
		
		
		
		return null;
	}

}
