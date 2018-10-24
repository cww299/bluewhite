package com.bluewhite.product.primecost.machinist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.machinist.dao.MachinistDao;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
@Service
public class MachinistServiceImpl extends BaseServiceImpl<Machinist, Long> implements MachinistService{
	
	@Autowired
	private MachinistDao dao;
	
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	
	@Override
	public Machinist saveMachinist(Machinist machinist) {
		
		PrimeCoefficient primeCoefficient = primeCoefficientDao.findByType("machinist");
		
		//该工序涉及回针次数时间/秒
		machinist.setBackStitch(machinist.getBackStitchCount()*primeCoefficient.getMachinistThree());
		//该工序涉及粘片次数时间/秒
		machinist.setSticking(primeCoefficient.getMachinistOne()*machinist.getCutpartsNumber());
		//1类模式可走（每CM时间/秒）
		machinist.setModeOne(1/machinist.getBaseFourDate()*machinist.getBeelineNumber());
		//2类模式可走（每CM时间/秒）
		machinist.setModeTwo(1/machinist.getBaseFourDate()*machinist.getArcNumber());
		//3类模式可走（每CM时间/秒）
		machinist.setModeThree(1/machinist.getBaseFourDate()*machinist.getBendNumber());
		
		//单一机缝需要时间/秒
		machinist.setOneSewingTime(machinist.getBackStitch()+machinist.getSticking()+machinist.getModeOne()+machinist.getModeTwo()+machinist.getModeThree());
		//剪线时间/秒
		machinist.setCutLineTime(primeCoefficient.getMachinisttwo());
		//单片机缝放快手时间
		machinist.setSewingQuickWorkerTime(machinist.getOneSewingTime()*1.08*primeCoefficient.getMachinistThree());
		//单片剪线放快手时间
		machinist.setLineQuickWorkerTime(machinist.getCutLineTime()*1.08*primeCoefficient.getMachinistThree());
		//试制大工工价（含快手)
		machinist.setTrialProducePrice(machinist.getTime()*1.08*primeCoefficient.getPerSecondMachinist());
		//电脑推算大工工价（含快手)
		machinist.setReckoningPrice(machinist.getSewingQuickWorkerTime()*primeCoefficient.getPerSecondMachinist());
		//剪线工价
		machinist.setCutLinePrice(machinist.getLineQuickWorkerTime()*primeCoefficient.getPerSecondMachinistTwo());
		//设备折旧和房水电费
		machinist.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
				primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*machinist.getTime());
		//管理人员费用
		machinist.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*machinist.getTime());
		//电脑推算机缝该工序费用
		machinist.setReckoningSewingPrice(NumUtils.round((machinist.getReckoningPrice()+machinist.getEquipmentPrice()+machinist.getCutLinePrice()+machinist.getAdministrativeAtaff())*primeCoefficient.getEquipmentProfit(),3));		
		//试制机缝该工序费用
		machinist.setTrialSewingPrice(NumUtils.round((machinist.getTrialProducePrice()+machinist.getCutLinePrice()+machinist.getEquipmentPrice()+machinist.getAdministrativeAtaff())*primeCoefficient.getEquipmentProfit(),3));
		
		//入成本价格
		machinist.setAllCostPrice(machinist.getNumber()*NumUtils.setzro(machinist.getCostPrice()));
		//各单道比全套工价
		List<Machinist> machinistList = dao.findByProductId(machinist.getProductId());
		machinistList.add(machinist);
		double scaleMaterial = machinist.getAllCostPrice()/(machinistList.stream().filter(Machinist->Machinist.getAllCostPrice()!=null).mapToDouble(Machinist::getAllCostPrice).sum());
		machinist.setScaleMaterial(scaleMaterial);
		
		
		return machinist;
	}

	@Override
	public PageResult<Machinist> findPages(Machinist machinist, PageParameter page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductMaterials(Machinist machinist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Machinist> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}

}
