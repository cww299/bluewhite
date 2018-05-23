package com.bluewhite.production.productionutils.constant.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.productionutils.constant.entity.ProductionConstant;

public interface ProductionConstantDao extends BaseRepository<ProductionConstant, Long>{
	
	public ProductionConstant findByExcelNameAndType(String excelName , Integer type);

}

