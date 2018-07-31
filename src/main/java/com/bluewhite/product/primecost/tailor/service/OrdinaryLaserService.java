package com.bluewhite.product.primecost.tailor.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

@Service
public interface OrdinaryLaserService extends BaseCRUDService<OrdinaryLaser,Long>{
	
	/**
	 * 裁剪普通激光填写
	 * @param ordinaryLaser
	 * @return
	 */
	public OrdinaryLaser saveOrdinaryLaser(OrdinaryLaser ordinaryLaser,PrimeCoefficient primeCoefficient);

}
