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
import com.bluewhite.common.BeanCopyUtils;
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
		NumUtils.setzro(cutParts);
		//该片在这个货中的单只用料（累加处）
		cutParts.setAddMaterial(NumUtils.mul(cutParts.getCutPartsNumber(), cutParts.getOneMaterial()));
		//当批各单片用料
		if(cutParts.getComposite()==0){
			cutParts.setBatchMaterial(NumUtils.div(NumUtils.mul(cutParts.getAddMaterial(), 
					NumUtils.sum(cutParts.getManualLoss(),1),(double)cutParts.getCutPartsNumber()), 
					NumUtils.mul(cutParts.getCutPartsNumber(),cutParts.getNumber()),3));
			//当批各单片价格
			cutParts.setBatchMaterialPrice(NumUtils.mul(cutParts.getBatchMaterial(),cutParts.getMateriel().getPrice()));
		}
		if(cutParts.getComposite()==1){
			cutParts.setComplexBatchMaterial(NumUtils.mul(cutParts.getAddMaterial(), 
					NumUtils.sum(cutParts.getCompositeManualLoss(),1),(double)cutParts.getNumber()));
			cutParts.setBatchComplexMaterialPrice(NumUtils.mul(cutParts.getComplexBatchMaterial(),cutParts.getMateriel().getPrice()));
			cutParts.setBatchComplexAddPrice(NumUtils.mul(cutParts.getComplexBatchMaterial(),cutParts.getComplexMateriel().getPrice()));
		}
		//使用片数周长
		cutParts.setAllPerimeter(NumUtils.mul(cutParts.getPerimeter(),cutParts.getCutPartsNumber()));
		dao.save(cutParts);
		
		//从cc裁片填写后，自动增加到裁剪页面
		Tailor tailor =  new Tailor();
		//增加和产品关联关系
		tailor.setProductId(cutParts.getProductId());
		//裁片和裁剪页面关联关系
		tailor.setCutPartsId(cutParts.getId());
		//批量产品数量或模拟批量数
		tailor.setNumber(cutParts.getNumber());
		//裁剪部位名称
		tailor.setTailorName(cutParts.getCutPartsName());
		//裁剪片数
		tailor.setTailorNumber(cutParts.getCutPartsNumber());
		//当批片数
		tailor.setBacthTailorNumber(cutParts.getCutPartsNumber()*cutParts.getNumber());
		//物料压价,通过cc裁片填写中该裁片该面料的价值 得到
		tailor.setPriceDown((cutParts.getBatchMaterialPrice()==null ? 0.0 : cutParts.getBatchMaterialPrice())
				+(cutParts.getBatchComplexAddPrice()==null ? 0.0 :cutParts.getBatchComplexAddPrice()));

		if(cutParts.getTailorId()==null){ 
			tailor.setTailorTypeId((long)71);
			tailor.setTailorSize(0.01); 
			tailorDao.save(tailor);
			OrdinaryLaser prams = new OrdinaryLaser();
			prams.setSave(0);
			prams.setTailorId(tailor.getId());
			tailorService.getOrdinaryLaserDate(tailor, prams);
			//不含绣花环节的为机工压价	
			//含绣花环节的为机工压价
			//为机工准备的压价
			tailorService.getTailorDate(tailor,prams);
			ordinaryLaserDao.save(prams);
			//将裁剪方式和裁剪页面数据进行关联，实现一对一的同步更新
			tailor.setOrdinaryLaserId(prams.getId());
		}else{
			Tailor oldtailor = tailorDao.findOne(cutParts.getTailorId());
			BeanCopyUtils.copyNotEmpty(tailor,oldtailor);
			tailorDao.save(oldtailor);
		}
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
			cp.setScaleMaterial(NumUtils.div(cp.getAddMaterial(), scaleMaterial, 3));
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
