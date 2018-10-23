package com.bluewhite.product.primecost.machinist.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
@Service
public class MachinistServiceImpl extends BaseServiceImpl<Machinist, Long> implements MachinistService{

	@Override
	public Machinist saveMachinist(Machinist machinist) {
		
		if(StringUtils.isEmpty(machinist.getNumber())){
			throw new ServiceException("批量产品数量或模拟批量数不能为空");
		}
		
		
		
		
		return null;
	}

	@Override
	public PageResult<Machinist> findPages(Machinist machinist, PageParameter page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductMaterials(Machinist machinist) {
		// TODO Auto-generated method stub
		
	}

}
