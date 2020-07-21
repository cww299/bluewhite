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
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.cutparts.entity.poi.CutPartsPoi;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecost.tailor.service.TailorService;
import com.bluewhite.product.primecostbasedata.dao.BaseOneDao;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;

@Service
public class CutPartsServiceImpl  extends BaseServiceImpl<CutParts, Long> implements CutPartsService{

	@Autowired
	private CutPartsDao dao;
	@Autowired
	private TailorDao tailorDao;
	@Autowired
	private TailorService tailorService;
	@Autowired
	private OrdinaryLaserDao ordinaryLaserDao;
	@Autowired
	private MaterielDao materielDao;
	@Autowired
	private BaseOneDao baseOneDao;
	
	
	@Override
	@Transactional
	public CutParts saveCutParts(CutParts cutParts) {
		NumUtils.setzro(cutParts);
		//该片在这个货中的单只用料（累加处）
		cutParts.setAddMaterial(NumUtils.mul(cutParts.getCutPartsNumber(), cutParts.getOneMaterial()));
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
		//裁剪部位名称
		tailor.setTailorName(cutParts.getCutPartsName());
		//裁剪片数
		tailor.setTailorNumber(cutParts.getCutPartsNumber());
		//面料
		Materiel materiel  =  materielDao.findOne(cutParts.getMaterielId());
		Materiel complexMateriel  = new Materiel();
		//复合物
		if(cutParts.getComplexMaterielId()!=null) {
		       complexMateriel  =  materielDao.findOne(cutParts.getComplexMaterielId());
		}
		//物料压价,通过cc裁片填写中该裁片该面料和复合物的价值 得到（原表格公式为批次数值，先取单一片数计算）
		tailor.setPriceDown(NumUtils.sum(NumUtils.setzro(materiel.getPrice()),NumUtils.setzro(complexMateriel.getPrice())));
		//选择价格来源，默认选择得到理论(市场反馈）含管理价值
		tailor.setCostPriceSelect(1);
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
			cutParts.setBatchMaterialPrice(NumUtils.mul(cutParts.getBatchMaterial(),NumUtils.setzro(materiel.getPrice())));
		}
		if(cutParts.getComposite()==1){
			Materiel complexMateriel  =  materielDao.findOne(cutParts.getComplexMaterielId());
			cutParts.setComplexBatchMaterial(NumUtils.mul(cutParts.getAddMaterial(), 
					NumUtils.sum(cutParts.getCompositeManualLoss(),1),(double)cutParts.getNumber()));
			cutParts.setBatchComplexMaterialPrice(NumUtils.mul(cutParts.getComplexBatchMaterial(),NumUtils.setzro(materiel.getPrice())));
			cutParts.setBatchComplexAddPrice(NumUtils.mul(cutParts.getComplexBatchMaterial(),NumUtils.setzro(complexMateriel.getPrice())));
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

	@Override
	@Transactional
	public int uploadCutParts(ExcelListener excelListener, Long productId) {
		int count = 0;
        // 获取导入的裁片
        List<Object> excelListenerList = excelListener.getData();
        List<BaseOne> unitList = baseOneDao.findByType("unit");
        for (Object object : excelListenerList) {
        	CutPartsPoi poi = (CutPartsPoi) object;
        	if(poi.getCutPartsName() == null || poi.getCutPartsName().isEmpty() || poi.getCutPartsNumber() == null ||
        	   poi.getPerimeter() == null || poi.getMaterialNumber() == null || poi.getMaterialNumber().isEmpty() || 
        	   poi.getOneMaterial() == null || poi.getUnitName() == null || poi.getUnitName().isEmpty() ||
        	   poi.getManualLoss() == null) {
        		throw new ServiceException("导入的数据第 " + (excelListenerList.indexOf(object) + 1) + "行存在空数据，请检查");
        	}
        	CutParts cut = new CutParts();
        	// 根据面料编号查找面类，321 为面料类型id
        	Materiel mate = materielDao.findByNumberAndMaterielTypeId(poi.getMaterialNumber(), 321L);
        	if(mate == null || mate.getId() == null) {
        		throw new ServiceException("导入的数据第 " + (excelListenerList.indexOf(object) + 1) + "行找不到面料：" + poi.getMaterialNumber());
        	}
        	// 查找复合物id
        	if(poi.getComplexMaterielNumber() != null && !poi.getComplexMaterielNumber().isEmpty()) {
        		Materiel complex = materielDao.findByNumber(poi.getComplexMaterielNumber());
        		if(complex == null || complex.getId() == null) {
            		throw new ServiceException("导入的数据第 " + (excelListenerList.indexOf(object) + 1) + "行找不到复合物：" + poi.getComplexMaterielNumber());
            	}
        		cut.setComplexMaterielId(complex.getId());
        	}
        	// 查找单位
        	unitList.forEach(unit -> {
        		if(unit.getName().equals(poi.getUnitName())) {
        			// 单位id
                	cut.setUnitId(unit.getId());
        		}
        	});
        	if(cut.getUnitId() == null) {
        		throw new ServiceException("导入的数据第 " + (excelListenerList.indexOf(object) + 1) + "行找不到单位：" + poi.getUnitName());
        	}
        	// 产品id
        	cut.setProductId(productId);
        	// 物料id
        	cut.setMaterielId(mate.getId());
        	cut.setCutPartsName(poi.getCutPartsName());
        	cut.setCutPartsNumber(poi.getCutPartsNumber());
        	cut.setPerimeter(poi.getPerimeter());
        	cut.setOneMaterial(poi.getOneMaterial());
        	cut.setDoubleComposite(poi.getDoubleComposite().equals("是") ? 1 : 0);
        	cut.setManualLoss(poi.getManualLoss());
        	cut.setCompositeManualLoss(poi.getCompositeManualLoss());
        	cut.setOverstockId(poi.getOverstockId());
        	saveCutParts(cut);
            count++;
        }
        return count;
	}

}
