package com.bluewhite.product.primecost.pack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.pack.entity.Pack;
@Service
public interface PackService extends BaseCRUDService<Pack,Long>{

	public Pack savePack(Pack pack);

	public PageResult<Pack> findPages(Pack pack, PageParameter page);

	public void deletePack(Long id);

	public List<Pack> findByProductId(Long productId);

}
