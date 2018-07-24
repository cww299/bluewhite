package com.bluewhite.product.primecost.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.product.primecost.common.entity.CommonData;

@Service
public interface CommonDataService  extends BaseCRUDService<CommonData,Long>{

	List<CommonData> findPages(CommonData commonData);

}
