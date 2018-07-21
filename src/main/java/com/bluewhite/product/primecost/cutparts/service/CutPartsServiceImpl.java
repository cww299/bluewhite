package com.bluewhite.product.primecost.cutparts.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.finance.entity.CollectPay;

@Service
public class CutPartsServiceImpl  extends BaseServiceImpl<CutParts, Long> implements CutPartsService{

	@Autowired
	private CutPartsDao dao;
	
	
	@Override
	public CutParts saveCutParts(CutParts cutParts) throws Exception {
		if(StringUtils.isEmpty(cutParts.getCutPartsNumber())){
			throw new ServiceException("使用片数不能为空");
		}
		if(StringUtils.isEmpty(cutParts.getOneMaterial())){
			throw new ServiceException("单片用料不能为空");
		}
		cutParts.setAddMaterial(cutParts.getCutPartsNumber()*cutParts.getOneMaterial());
		//当批各单片用料
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterial(cutParts.getAddMaterial()*(cutParts.getManualLoss()+1)*cutParts.getCutPartsNumber()/cutParts.getCutPartsNumber()*9000);
		}else{
			cutParts.setBatchMaterial(0.0);
		}
		//当批各单片价格
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterialPrice(cutParts.getComposite()*cutParts.getProductCost());
		}else{
			cutParts.setBatchMaterialPrice(0.0);
		}
		
		if(cutParts.getComposite()==1){
			cutParts.setComplexBatchMaterial(cutParts.getAddMaterial()*(cutParts.getManualLoss()+1)*9000);
			cutParts.setBatchComplexMaterialPrice(cutParts.getComplexBatchMaterial()*cutParts.getProductCost());
			cutParts.setBatchComplexAddPrice(cutParts.getComplexBatchMaterial()*cutParts.getComplexProductCost());
		}
		dao.save(cutParts);
		//各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		cutParts.setScaleMaterial(cutParts.getAddMaterial()/scaleMaterial);
		return dao.save(cutParts);
	}


	@Override
	public PageResult<CutParts> findPages(CutParts param, PageParameter page) {
		 Page<CutParts> pages = dao.findAll((root,query,cb) -> {
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
	        	if (!StringUtils.isEmpty(param.getCutPartsName())) {
					predicate.add(cb.like(root.get("cutPartsName").as(String.class),"%"+param.getCutPartsName()+"%"));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
		 PageResult<CutParts> result = new PageResult<CutParts>(pages,page);
		return result;
	}


	@Override
	public void deleteCutParts(CutParts cutParts) {
		//删除
		dao.delete(cutParts.getId());
		//更新其他各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		for(CutParts cp : cutPartsList){
			cp.setScaleMaterial(cp.getAddMaterial()/scaleMaterial);
		}
		dao.save(cutPartsList);
	}

}
