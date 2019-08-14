package com.bluewhite.product.primecost.embroidery.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;

public interface EmbroideryService extends BaseCRUDService<Embroidery,Long> {

	public Embroidery  saveEmbroidery(Embroidery embroidery);

	public PageResult<Embroidery> findPages(Embroidery embroidery, PageParameter page);

	public void deleteEmbroidery(Long id);

	public List<Embroidery> findByProductId(Long productId);

}
