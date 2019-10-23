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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.materials.dao.ProductMaterialsDao;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.product.dao.ProductDao;

@Service
public class ProductMaterialsServiceImpl extends BaseServiceImpl<ProductMaterials, Long> implements ProductMaterialsService {
	
	@Autowired
	private ProductMaterialsDao dao ;
	@Autowired
	private ProductDao productdao;
	@Autowired
	private MaterielDao materielDao;
	
	@Override
	@Transactional
	public ProductMaterials saveProductMaterials(ProductMaterials productMaterials) {
		NumUtils.setzro(productMaterials);
		countComposite(productMaterials);
		Materiel materiel = materielDao.findOne(productMaterials.getMaterielId());
		if(productMaterials.getConvertUnit()==0){
			productMaterials.setBatchMaterialPrice(NumUtils.mul(productMaterials.getBatchMaterial(),materiel.getPrice()));
		}else{
			productMaterials.setBatchMaterialPrice(NumUtils.mul(productMaterials.getBatchMaterial(),materiel.getConvertPrice()));
		}
		return dao.save(productMaterials);
	}


	@Override
	public ProductMaterials countComposite(ProductMaterials productMaterials) {
		productMaterials.setBatchMaterial(NumUtils.mul(productMaterials.getManualLoss(),productMaterials.getOneMaterial(),(double)productMaterials.getNumber()));
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
	        	//按压货环节id
	        	if (param.getOverstockId() != null) {
					predicate.add(cb.equal(root.get("overstockId").as(Long.class),param.getOverstockId()));
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
	public int deleteProductMaterials(String ids) {
		return delete(ids);
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
