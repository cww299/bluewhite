package com.bluewhite.product.primecost.tailor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.Tailor;

@Service
public class TailorServiceImpl extends BaseServiceImpl<Tailor, Long>  implements TailorService {
	
	@Autowired
	private TailorDao dao;
	
	
	@Override
	public Tailor saveTailor(Tailor tailor) throws Exception {
		if(StringUtils.isEmpty(tailor.getNumber())){
			throw new ServiceException("批量产品数量或模拟批量数不能为空");
		}
		tailor.setBacthTailorNumber(tailor.getNumber()*tailor.getTailorNumber());
		
		if(!StringUtils.isEmpty(tailor.getExperimentPrice())){
			tailor.setRatePrice(tailor.getExperimentPrice()/tailor.getCostPrice());
		}
		tailor.setAllCostPrice(tailor.getBacthTailorNumber()*tailor.getCostPrice());
		
		dao.save(tailor);
		
		
		
		return tailor;
	}

}
