package com.bluewhite.product.primecost.embroidery.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;

public interface EmbroideryService extends BaseCRUDService<Embroidery,Long> {

	public Embroidery  saveEmbroidery(Embroidery embroidery);

}
