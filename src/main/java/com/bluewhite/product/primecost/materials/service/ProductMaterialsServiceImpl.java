package com.bluewhite.product.primecost.materials.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.materials.dao.ProductMaterialsDao;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;

@Service
public class ProductMaterialsServiceImpl extends BaseServiceImpl<ProductMaterials, Long> implements ProductMaterialsService {
	
	@Autowired
	private ProductMaterialsDao dao ;
	@Autowired
	private ProductDao productdao;
	
	@Override
	@Transactional
	public ProductMaterials saveProductMaterials(ProductMaterials productMaterials) {
		NumUtils.setzro(productMaterials);
		if(StringUtils.isEmpty(productMaterials.getManualLoss())){
			productMaterials.setBatchMaterial(productMaterials.getOneMaterial()*productMaterials.getNumber());
		}else{
			productMaterials.setBatchMaterial(productMaterials.getManualLoss()*productMaterials.getOneMaterial()*productMaterials.getNumber());
		}
		productMaterials.setBatchMaterialPrice(productMaterials.getBatchMaterial()*productMaterials.getUnitCost());
		dao.save((ProductMaterials)NumUtils.setzro(productMaterials));
		return productMaterials;
	}


	@Override
	public PageResult<ProductMaterials> findPages(ProductMaterials param, PageParameter page) {
		 Page<ProductMaterials> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按产品id过滤
	        	if (param.getProductId() != null) {
					predicate.add(cb.equal(root.get("productId").as(Long.class),param.getProductId()));
				}
	        	//按裁片名称过滤
	        	if (!StringUtils.isEmpty(param.getMaterialsName())) {
					predicate.add(cb.like(root.get("cutPartsName").as(String.class),"%"+param.getMaterialsName()+"%"));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
		 PageResult<ProductMaterials> result = new PageResult<ProductMaterials>(pages,page);
		return result;
	}


	@Override
	@Transactional
	public void deleteProductMaterials(Long id) {
		ProductMaterials productMaterials = dao.findOne(id);
		//删除
		dao.delete(productMaterials.getId());
	}


	@Override
	public List<ProductMaterials> findByProductIdAndOverstockId(Long productId, Long id) {
		
		return dao.findByProductIdAndOverstockId(productId,id);
	}


	@Override
	public List<ProductMaterials> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}

}
