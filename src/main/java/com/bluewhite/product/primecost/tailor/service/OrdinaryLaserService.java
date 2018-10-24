package com.bluewhite.product.primecost.tailor.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

@Service
public interface OrdinaryLaserService extends BaseCRUDService<OrdinaryLaser,Long>{
	
	/**
	 * 裁剪普通激光填写
	 * @param ordinaryLaser
	 * @return
	 */
	public OrdinaryLaser saveOrdinaryLaser(OrdinaryLaser ordinaryLaser);
	
	/**
	 * 分页查看
	 * @param ordinaryLaser
	 * @param page
	 * @return
	 */
	public  PageResult<OrdinaryLaser> findPages(OrdinaryLaser ordinaryLaser, PageParameter page);

}
