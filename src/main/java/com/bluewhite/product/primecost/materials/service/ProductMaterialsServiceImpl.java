package com.bluewhite.product.primecost.materials.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;

@Service
public class ProductMaterialsServiceImpl extends BaseServiceImpl<ProductMaterials, Long> implements ProductMaterialsService {

	@Override
	public ProductMaterials saveProductMaterials(ProductMaterials productMaterials)
			throws Exception {
		if(StringUtils.isEmpty(productMaterials.getNumber())){
			throw new ServiceException("批量产品数量或模拟批量数不能为空");
		}
		
		
		return null;
	}

}
