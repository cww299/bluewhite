package com.bluewhite.product.primecostbasedata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.product.entity.Product;

@Service
public interface MaterielService extends BaseCRUDService<Materiel,Long>{
	
	
	/**
	 * 分页查询产品列表
	 * @return
	 */
	public List<Materiel>  findPages(Materiel materiel);
	
	/**
	 * 分页查询产品列表
	 * @return
	 */
	public  List<BaseOne> findPagesBaseOne(BaseOne baseOne);

}
