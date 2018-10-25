package com.bluewhite.product.primecost.cutparts.service;

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
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;

@Service
public class CutPartsServiceImpl  extends BaseServiceImpl<CutParts, Long> implements CutPartsService{

	@Autowired
	private CutPartsDao dao;
	@Autowired
	private ProductDao productdao;
	@Autowired
	private TailorDao tailorDao;
	@Autowired
	private OrdinaryLaserDao ordinaryLaserDao;
	

	
	@Override
	@Transactional
	public CutParts saveCutParts(CutParts cutParts) throws Exception {
		if(StringUtils.isEmpty(cutParts.getCutPartsNumber())){
			throw new ServiceException("使用片数不能为空");
		}
		if(StringUtils.isEmpty(cutParts.getOneMaterial())){
			throw new ServiceException("单片用料不能为空");
		}
		if(StringUtils.isEmpty(cutParts.getNumber())){
			throw new ServiceException("批量产品数量或模拟批量数不能为空");
		}
		
		cutParts.setAddMaterial(cutParts.getCutPartsNumber()*cutParts.getOneMaterial());
		//当批各单片用料
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterial(cutParts.getAddMaterial()*(cutParts.getManualLoss()+1)*cutParts.getCutPartsNumber()/cutParts.getCutPartsNumber()*cutParts.getNumber());
		}else{
			cutParts.setBatchMaterial(0.0);
		}
		//当批各单片价格
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterialPrice(cutParts.getBatchMaterial()*cutParts.getProductCost());
		}else{
			cutParts.setBatchMaterialPrice(0.0);
		}
		
		if(cutParts.getComposite()==1){
			cutParts.setComplexBatchMaterial(cutParts.getAddMaterial()*(cutParts.getCompositeManualLoss()+1)*cutParts.getNumber());
			cutParts.setBatchComplexMaterialPrice(cutParts.getComplexBatchMaterial()*cutParts.getProductCost());
			cutParts.setBatchComplexAddPrice(cutParts.getComplexBatchMaterial()*cutParts.getComplexProductCost());
		}
		
		dao.save(cutParts);
		
		//从cc裁片填写后，自动增加到裁剪页面
		Tailor tailor = new Tailor();
		
		//增加和产品关联关系
		tailor.setProductId(cutParts.getProductId());
		//批量产品数量或模拟批量数
		tailor.setNumber(cutParts.getNumber());
		//裁片id
		tailor.setBaseId(cutParts.getBaseId());
		//裁剪部位名称
		tailor.setTailorName(cutParts.getCutPartsName());
		//裁剪片数
		tailor.setTailorNumber(cutParts.getCutPartsNumber());
		//当批片数
		tailor.setBacthTailorNumber(cutParts.getCutPartsNumber()*cutParts.getNumber());
		//物料压价,通过cc裁片填写中该裁片该面料的价值 得到
		tailor.setPriceDown((cutParts.getBatchMaterialPrice()==null ? 0.0 : cutParts.getBatchMaterialPrice())
				+(cutParts.getBatchComplexAddPrice()==null ? 0.0 :cutParts.getBatchComplexAddPrice()));
		//不含绣花环节的为机工压价	
		
		//含绣花环节的为机工压价
		
		//为机工准备的压价
		
		
		
		tailorDao.save(tailor);
		//更新裁剪页面id到裁片中
		cutParts.setTailorId(tailor.getId());
		
		
		//各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		cutParts.setScaleMaterial(cutParts.getAddMaterial()/scaleMaterial);
		cutPartsList.add(cutParts);
		dao.save(cutParts);
		//同时更新产品成本价格表(面料价格(含复合物料和加工费)
		Product product =  productdao.findOne(cutParts.getProductId());
		double batchMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchMaterialPrice()!=null).mapToDouble(CutParts::getBatchMaterialPrice).sum();
		double batchComplexMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexMaterialPrice()!=null).mapToDouble(CutParts::getBatchComplexMaterialPrice).sum();
		double batchComplexAddPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexAddPrice()!=null).mapToDouble(CutParts::getBatchComplexAddPrice).sum();
		PrimeCost primeCost = product.getPrimeCost();
		if(primeCost==null){
			 primeCost = new PrimeCost();
		}
		primeCost.setNumber(cutParts.getNumber());
		primeCost.setCutPartsPrice((batchMaterialPrice+batchComplexMaterialPrice+batchComplexAddPrice)/cutParts.getNumber());
		product.setPrimeCost(primeCost);
		productdao.save(product);
		
		
		return cutParts;
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
	@Transactional
	public void deleteCutParts(Long id) {
		CutParts cutParts = dao.findOne(id);
		if(cutParts.getTailorId()!=null){
			//删除裁剪页面
			tailorDao.delete(cutParts.getTailorId());
			//删除裁减类型页面
			ordinaryLaserDao.delete(ordinaryLaserDao.findByTailorId(cutParts.getTailorId()));
		}
		//删除裁片
		dao.delete(cutParts);
		//更新其他各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		for(CutParts cp : cutPartsList){
			cp.setScaleMaterial(cp.getAddMaterial()/scaleMaterial);
		}
		///同时更新产品成本价格表(面料价格(含复合物料和加工费)
		Product product =  productdao.findOne(cutParts.getProductId());
		double batchMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchMaterialPrice()!=null).mapToDouble(CutParts::getBatchMaterialPrice).sum();
		double batchComplexMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexMaterialPrice()!=null).mapToDouble(CutParts::getBatchComplexMaterialPrice).sum();
		double batchComplexAddPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexAddPrice()!=null).mapToDouble(CutParts::getBatchComplexAddPrice).sum();
		product.getPrimeCost().setCutPartsPrice(batchMaterialPrice+batchComplexMaterialPrice+batchComplexAddPrice);
		productdao.save(product);
		dao.save(cutPartsList);
	}


	@Override
	public List<CutParts> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}

}
