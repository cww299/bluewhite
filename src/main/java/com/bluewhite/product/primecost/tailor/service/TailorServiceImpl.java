package com.bluewhite.product.primecost.tailor.service;

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
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.product.dao.ProductDao;

@Service
public class TailorServiceImpl extends BaseServiceImpl<Tailor, Long>  implements TailorService {
	
	@Autowired
	private TailorDao dao;
	@Autowired
	private CutPartsDao  cutPartsDao;
	@Autowired
	private OrdinaryLaserDao ordinaryLaserDao;
	@Autowired
	private ProductDao productdao;
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	
	
	@Override
	@Transactional
	public Tailor saveTailor(Tailor tailor) throws Exception {
		if(StringUtils.isEmpty(tailor.getNumber())){
			throw new ServiceException("批量产品数量或模拟批量数不能为空");
		}
		tailor.setBacthTailorNumber(tailor.getNumber()*tailor.getTailorNumber());
		
		if(!StringUtils.isEmpty(tailor.getExperimentPrice())){
			tailor.setRatePrice(tailor.getExperimentPrice()/tailor.getCostPrice());
		}
		tailor.setAllCostPrice(tailor.getBacthTailorNumber()*tailor.getCostPrice());
		
		dao.save(tailor);
		//物料压价,通过cc裁片填写中该裁片该面料的价值 得到
		List<CutParts> cutPartsList = cutPartsDao.findByProductId(tailor.getProductId());
		for(CutParts cutParts : cutPartsList){
			if(cutParts.getCutPartsName().equals(tailor.getTailorName())){
				tailor.setPriceDown((cutParts.getBatchMaterialPrice()==null ? 0.0 : cutParts.getBatchMaterialPrice())
						+(cutParts.getBatchComplexAddPrice()==null ? 0.0 :cutParts.getBatchComplexAddPrice()));
				
			}
		}
		
		//根据裁剪类型进行新增
		this.addcutPartsType(tailor);
		
		
		
		return tailor;
	}

	
	
	private void addcutPartsType(Tailor tailor) {
		PrimeCoefficient primeCoefficient = null;
		String type = null;
		 OrdinaryLaser  prams = new  OrdinaryLaser();
		 prams.setProductId(tailor.getProductId());
		 prams.setTailorTypeId(tailor.getTailorTypeId());
		 prams.setTailorType(tailor.getTailorType());
		 prams.setTailorName(tailor.getTailorName());
		 prams.setTailorNumber(tailor.getTailorNumber());
		 prams.setTailorSize(tailor.getTailorSize());
		switch (tailor.getTailorTypeId().intValue()) {
		case 71://普通激光切割
			type = "ordinarylaser";
			primeCoefficient = primeCoefficientDao.findByType(type);
			prams.setType(type);
			prams.setRabbTime(prams.getTailorSize()*primeCoefficient.getRabbTime()*primeCoefficient.getQuilt());
			 ordinaryLaserDao.save(prams);
			break;
		case 72://绣花激光切割
			type = "embroideryLaser";
			primeCoefficient = primeCoefficientDao.findByType(type);
			prams.setType(type);
			prams.setRabbTime(prams.getTailorSize()*primeCoefficient.getRabbTime()*primeCoefficient.getQuilt());
			ordinaryLaserDao.save(prams);
			
			break;
		case 73://手工电烫
			
			break;
		case 74://设备电烫
			
			break;
		case 75://冲床
			
			break;
		case 76://电推
			
			break;
		case 77://手工剪刀
			
			break;
		case 78://绣花领取
			
			break;
		default:
			break;
		}
		
		
	}



	@Override
	public PageResult<Tailor> findPages(Tailor param, PageParameter page) {
		Page<Tailor> pages = dao.findAll((root,query,cb) -> {
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
        	if (!StringUtils.isEmpty(param.getTailorName())) {
				predicate.add(cb.like(root.get("tailorName").as(String.class),"%"+param.getTailorName()+"%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
        	return null;
        }, page);
	 PageResult<Tailor> result = new PageResult<Tailor>(pages,page);
	return result;
	}

	
	
	
	
	
}
