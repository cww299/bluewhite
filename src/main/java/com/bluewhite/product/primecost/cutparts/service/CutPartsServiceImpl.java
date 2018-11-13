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
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.production.bacth.entity.Bacth;

@Service
public class CutPartsServiceImpl  extends BaseServiceImpl<CutParts, Long> implements CutPartsService{

	@Autowired
	private CutPartsDao dao;
	@Autowired
	private ProductDao productdao;
	@Autowired
	private TailorDao tailorDao;
	@Autowired
	private TailorService tailorService;
	@Autowired
	private OrdinaryLaserDao ordinaryLaserDao;
	

	
	@Override
	@Transactional
	public CutParts saveCutParts(CutParts cutParts) {
		//该片在这个货中的单只用料（累加处）
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
		//使用片数周长
		cutParts.setAllPerimeter(cutParts.getPerimeter()*cutParts.getCutPartsNumber());
		
		dao.save(cutParts);
		
		//从cc裁片填写后，自动增加到裁剪页面
		Tailor tailor =  new Tailor();
		if(cutParts.getTailorId()!=null){
			tailor = tailorDao.findOne(cutParts.getTailorId());
		}
		
		//增加和产品关联关系
		tailor.setProductId(cutParts.getProductId());
		//裁片和裁剪页面关联关系
		tailor.setCutPartsId(cutParts.getId());
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
		
		tailor.setTailorTypeId((long)71);
		tailor.setTailorSize(0.01);
		OrdinaryLaser prams = new OrdinaryLaser();
		tailorService.getOrdinaryLaserDate(tailor, prams);
		//不含绣花环节的为机工压价	
		//含绣花环节的为机工压价
		//为机工准备的压价
		tailorService.getTailorDate(tailor,prams);
		tailorDao.save(tailor);
		
		//更新裁剪页面id到裁片中
		cutParts.setTailorId(tailor.getId());
		//各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		for(CutParts cp : cutPartsList){
			cp.setScaleMaterial(NumUtils.division(cp.getAddMaterial()/scaleMaterial));
		}
		dao.save(cutPartsList);
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
	        }, SalesUtils.getQueryNoPageParameter());
		 	PageResultStat<CutParts> result = new PageResultStat<>(pages,page);
			result.setAutoStateField("allPerimeter", "perimeter","addMaterial");
			result.count();
		return result;
	}


	@Override
	@Transactional
	public void deleteCutParts(Long id) {
		CutParts cutParts = dao.findOne(id);
		if(cutParts.getTailorId()!=null){
			//删除裁减类型页面
			OrdinaryLaser ordinaryLaser = ordinaryLaserDao.findByTailorId(cutParts.getTailorId());
			if(ordinaryLaser!=null){
				ordinaryLaserDao.delete(ordinaryLaser);
			}
			//删除裁剪页面
			tailorDao.delete(cutParts.getTailorId());
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
		dao.save(cutPartsList);
	}


	@Override
	public List<CutParts> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}


	@Override
	public List<CutParts> findByProductIdAndOverstockId(Long productId, Long id) {
		return dao.findByProductIdAndOverstockId( productId, id);
	}

}
