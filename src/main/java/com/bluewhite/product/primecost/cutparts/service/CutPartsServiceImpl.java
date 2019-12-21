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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.product.product.dao.ProductDao;

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
	@Autowired
	private MaterielDao materielDao;
	
	
	@Override
	@Transactional
	public CutParts saveCutParts(CutParts cutParts) {
		NumUtils.setzro(cutParts);
		//该片在这个货中的单只用料（累加处）
		cutParts.setAddMaterial(NumUtils.mul(cutParts.getCutPartsNumber(), cutParts.getOneMaterial()));
		countComposite(cutParts);
		//使用片数周长
		cutParts.setAllPerimeter(NumUtils.mul(cutParts.getPerimeter(),cutParts.getCutPartsNumber()));
		dao.save(cutParts);
		
		//从cc裁片填写后，自动增加到裁剪页面
		Tailor tailor = null;
		if(cutParts.getTailorId()==null){
			tailor = new Tailor();
			//增加和产品关联关系
			tailor.setProductId(cutParts.getProductId());
			//裁片和裁剪页面关联关系
			tailor.setCutPartsId(cutParts.getId());
			tailor.setTailorTypeId((long)71); 
			tailor.setTailorSizeId((long)1);
		}else{
			tailor = tailorDao.findOne(cutParts.getTailorId());
		}
		NumUtils.setzro(tailor);
		//批量产品数量或模拟批量数
		tailor.setNumber(cutParts.getNumber());
		//裁剪部位名称
		tailor.setTailorName(cutParts.getCutPartsName());
		//裁剪片数
		tailor.setTailorNumber(cutParts.getCutPartsNumber());
		//当批片数
		tailor.setBacthTailorNumber(cutParts.getCutPartsNumber()*cutParts.getNumber());
		//物料压价,通过cc裁片填写中该裁片该面料的价值 得到
		tailor.setPriceDown(NumUtils.sum(cutParts.getBatchMaterialPrice(),cutParts.getBatchComplexAddPrice()));
		tailorService.saveTailor(tailor);
		//更新裁剪页面id到裁片中
		cutParts.setTailorId(tailor.getId());
		//各单片比全套用料
		List<CutParts> cutPartsList = dao.findByProductId(cutParts.getProductId());
		double scaleMaterial = 0;
		if(cutPartsList.size()>0){
			scaleMaterial =  cutPartsList.stream().mapToDouble(CutParts::getAddMaterial).sum();
		}
		for(CutParts cp : cutPartsList){
			cp.setScaleMaterial(NumUtils.div(cp.getAddMaterial(), scaleMaterial, 3));
		}
		dao.save(cutPartsList);
		return cutParts;
	}

	@Override
	public CutParts countComposite(CutParts cutParts) {
		Materiel materiel  =  materielDao.findOne(cutParts.getMaterielId());
		//当批各单片用料
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterial(NumUtils.mul(cutParts.getAddMaterial(), NumUtils.sum(cutParts.getManualLoss(),1),(double)cutParts.getNumber()));
			//当批各单片价格
			cutParts.setBatchMaterialPrice(NumUtils.mul(cutParts.getBatchMaterial(),materiel.getPrice()));
		}
		if(cutParts.getComposite()==1){
			Materiel complexMateriel  =  materielDao.findOne(cutParts.getComplexMaterielId());
			cutParts.setComplexBatchMaterial(NumUtils.mul(cutParts.getAddMaterial(), 
					NumUtils.sum(cutParts.getCompositeManualLoss(),1),(double)cutParts.getNumber()));
			cutParts.setBatchComplexMaterialPrice(NumUtils.mul(cutParts.getComplexBatchMaterial(),materiel.getPrice()));
			cutParts.setBatchComplexAddPrice(NumUtils.mul(cutParts.getComplexBatchMaterial(),complexMateriel.getPrice()));
		}
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
	        	//按压货环节id
	        	if (param.getOverstockId() != null) {
					predicate.add(cb.equal(root.get("overstockId").as(Long.class),param.getOverstockId()));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, StringUtil.getQueryNoPageParameter());
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
			cp.setScaleMaterial(NumUtils.div(cp.getAddMaterial(), scaleMaterial, 3));
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
