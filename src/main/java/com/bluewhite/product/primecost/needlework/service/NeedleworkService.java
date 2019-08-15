package com.bluewhite.product.primecost.needlework.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
@Service
public interface NeedleworkService extends BaseCRUDService<Needlework,Long>{
	
	/**
	 * 填写针工
	 * @param needlework
	 * @return
	 */
	Needlework saveNeedlework(Needlework needlework);

	
	public PageResult<Needlework> findPages(Needlework needlework, PageParameter page);


	public void deleteNeedlework(Long id);


	List<Needlework> findByProductId(Long productId);

}
