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
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.primecostbasedata.service.MaterielService;
import com.bluewhite.product.product.dao.ProductDao;

@Service
public class TailorServiceImpl extends BaseServiceImpl<Tailor, Long>  implements TailorService {
	
	@Autowired
	private TailorDao dao;
	@Autowired
	private ProductDao productdao;
	@Autowired
	private CutPartsDao  cutPartsDao;
	@Autowired
	private OrdinaryLaserDao ordinaryLaserDao;
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	@Autowired
	private MaterielService materielService;
	
	
	@Override
	@Transactional
	public Tailor saveTailor(Tailor tailor) throws Exception {
		//当同一个裁剪页面实体，改变了类型，进行保存操作。同时删除之前关联的类型实体
		OrdinaryLaser  prams = ordinaryLaserDao.findByTailorId(tailor.getId());
		if(prams!=null && !prams.getId().equals(tailor.getOrdinaryLaserId())){
			ordinaryLaserDao.delete(prams);
		}
		//根据裁剪类型进行新增
		OrdinaryLaser  prams1 = null;
		if(tailor.getOrdinaryLaserId()!=null){
			prams1 = ordinaryLaserDao.findOne(tailor.getOrdinaryLaserId());
			//当裁减类型实体种没有数据
			if(prams1==null){
				prams1 = new OrdinaryLaser();
			}
			
		}
		this.getOrdinaryLaserDate(tailor,prams1);
		return tailor;
	}

	
	//根据裁剪类型进行计算出不同的价格,获取裁剪页面所需要的数据
	@Override
	public OrdinaryLaser getOrdinaryLaserDate(Tailor tailor,OrdinaryLaser  prams) {
		PrimeCoefficient primeCoefficient = null;
		String type = null;
		 prams.setProductId(tailor.getProductId());
		 prams.setTailorTypeId(tailor.getTailorTypeId());
		 prams.setTailorType(tailor.getTailorType());
		 prams.setTailorName(tailor.getTailorName());
		 prams.setTailorNumber(tailor.getTailorNumber());
		 prams.setTailorSize(tailor.getTailorSize());
		 prams.setTailorId(tailor.getId());
		switch (tailor.getTailorTypeId().intValue()) {
		case 71://普通激光切割
			type = "ordinarylaser";
			primeCoefficient = primeCoefficientDao.findByType(type);
			prams.setType(type);
			//拉布时间
			prams.setRabbTime(prams.getTailorSize()/primeCoefficient.getRabbTime()*primeCoefficient.getQuilt());
			//单片激光需要用净时
			if(prams.getSingleDouble()==2){
				prams.setSingleLaserTime((prams.getPerimeter()*primeCoefficient.getTime()*prams.getStallPoint()*primeCoefficient.getPauseTime()/2)
						+ prams.getRabbTime());
			}else{
				prams.setSingleLaserTime((prams.getPerimeter()*primeCoefficient.getTime()*prams.getStallPoint()*primeCoefficient.getPauseTime())
						+ prams.getRabbTime());
			}
			//单片激光放快手时间
			prams.setSingleLaserHandTime(prams.getSingleLaserTime()*1.08*primeCoefficient.getQuickWorker());
			//工价（含快手)
			prams.setLabourCost(prams.getSingleLaserHandTime()*primeCoefficient.getPerSecondMachinist());
			//设备折旧和房水电费
			prams.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
					primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*prams.getSingleLaserHandTime());
			//管理人员费用
			prams.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*prams.getSingleLaserHandTime());
			//普通激光切割该裁片费用
			prams.setStallPrice((prams.getLabourCost()+prams.getEquipmentPrice()+prams.getAdministrativeAtaff())*primeCoefficient.getEquipmentProfit());
			
			
			
			break;
		case 72://绣花激光切割
			type = "embroideryLaser";
			primeCoefficient = primeCoefficientDao.findByType(type);
			prams.setType(type);
			
			//得到理论(市场反馈）含管理价值
			if(prams.getPerimeter()<primeCoefficient.getPerimeterLess()){
				prams.setManagePrice(primeCoefficient.getPerimeterLessNumber()+primeCoefficient.getPerimeterLessNumber());
			}else{
				prams.setManagePrice(primeCoefficient.getPeripheralLaser()*100*prams.getPerimeter()+primeCoefficient.getEmbroideryLaserNumber());
			}
			//拉布时间
			prams.setRabbTime(prams.getTailorSize()/primeCoefficient.getRabbTime()*primeCoefficient.getQuilt());
			//单片激光需要用净时
			prams.setSingleDouble(1);
			if(prams.getSingleDouble()==2){
				prams.setSingleLaserTime((prams.getPerimeter()*primeCoefficient.getTime()*prams.getStallPoint()*primeCoefficient.getPauseTime()/2)
						+ prams.getRabbTime());
			}else {
				prams.setSingleLaserTime((prams.getPerimeter()*primeCoefficient.getTime()*prams.getStallPoint()*primeCoefficient.getPauseTime())
						+ prams.getRabbTime());
			}
			//单片激光放快手时间
			prams.setSingleLaserHandTime(prams.getSingleLaserTime()*1.08*primeCoefficient.getQuickWorker());
			//工价（含快手)
			prams.setLabourCost(prams.getSingleLaserHandTime()*primeCoefficient.getPerSecondMachinist());
			//设备折旧和房水电费
			prams.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
					primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*prams.getSingleLaserHandTime());
			//管理人员费用
			prams.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*prams.getSingleLaserHandTime());
			//普通激光切割该裁片费用
			prams.setStallPrice((prams.getLabourCost()+prams.getEquipmentPrice()+prams.getAdministrativeAtaff())*primeCoefficient.getEquipmentProfit());
			
			
			break;
		case 73://手工电烫
			type = "perm";
			primeCoefficient = primeCoefficientDao.findByType(type);
			
			prams.setType(type);
			//撕片秒数（含快手)
//			oldOrdinaryLaser.setTearingSeconds(tearingSeconds);
			//拉布秒数（含快手)
			prams.setRabbTime((primeCoefficient.getPermOne()/1.5/prams.getTailorSize())*primeCoefficient.getQuickWorker());
			
			break;
		case 74://设备电烫
			
			break;
		case 75://冲床
			type = "puncher";
			
			
			
			break;
		case 76://电推
			type = "electricPush";
			
			
			break;
		case 77://手工剪刀
			type = "manual";
			
			break;
		case 78://绣花领取
			
			break;
		default:
			break;
		}
		if(prams.getSave()==null){
			ordinaryLaserDao.save(prams);
			//将裁剪方式和裁剪页面数据进行关联，实现一对一的同步更新
			tailor.setOrdinaryLaserId(prams.getId());
			dao.save(tailor);
		}
		return prams;
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


	@Override
	public Tailor getTailorDate(Tailor tailor,OrdinaryLaser ordinaryLaser) {
		
		//得到理论(市场反馈）含管理价值
		Double managePrice = materielService.getBaseThreeOne(tailor.getTailorTypeId(),tailor.getTailorSize());
		tailor.setManagePrice(managePrice);
		//得到实验推算价格
		tailor.setExperimentPrice(ordinaryLaser.getStallPrice());
		
		//入成本价格
		tailor.setAllCostPrice(tailor.getBacthTailorNumber()*tailor.getCostPrice());
		//得到市场价与实推价比
		if(!StringUtils.isEmpty(tailor.getExperimentPrice())){
			tailor.setRatePrice(tailor.getExperimentPrice()/tailor.getCostPrice());
		}
		//各单道比全套工价
		List<Tailor> tailorList = dao.findByProductId(tailor.getProductId());
		tailorList.add(tailor);
		double scaleMaterial = tailor.getAllCostPrice()/(tailorList.stream().mapToDouble(Tailor::getAllCostPrice).sum());
		tailor.setScaleMaterial(scaleMaterial);
		//不含绣花环节的为机工压价	
		tailor.setNoeMbroiderPriceDown(tailor.getAllCostPrice()+tailor.getPriceDown());
		return tailor;
	}

	
	
	
	
	
}
