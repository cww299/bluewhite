package com.bluewhite.product.primecost.cutparts.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;

@Service
public interface CutPartsService extends BaseCRUDService<CutParts,Long>{

	public CutParts saveCutParts(CutParts cutParts) throws Exception;

}
