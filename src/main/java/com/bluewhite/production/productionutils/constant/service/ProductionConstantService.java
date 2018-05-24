package com.bluewhite.production.productionutils.constant.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.production.productionutils.constant.entity.ProductionConstant;
@Service
public interface ProductionConstantService extends BaseCRUDService<ProductionConstant,Long>{
	
	public ProductionConstant findByExcelNameAndType(String excelName , Integer type);
}
