package com.bluewhite.production.productionutils.constant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.production.productionutils.constant.dao.ProductionConstantDao;
import com.bluewhite.production.productionutils.constant.entity.ProductionConstant;
@Service
public class ProductionConstantServiceImpl extends BaseServiceImpl<ProductionConstant, Long> implements ProductionConstantService{
	
	@Autowired
	private ProductionConstantDao productionConstantDao;

	@Override
	public ProductionConstant findByExcelNameAndType(String excelName, Integer type) {
		return productionConstantDao.findByExcelNameAndType(excelName, type);
	}
	
	
	

}
