package com.bluewhite.product.primecost.tailor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
@Service
public class OrdinaryLaserServiceImpl extends BaseServiceImpl<OrdinaryLaser, Long>  implements OrdinaryLaserService{

	@Autowired
	private OrdinaryLaserDao  dao;
	
	@Override
	public OrdinaryLaser saveordinaryLaser(OrdinaryLaser ordinaryLaser,PrimeCoefficient primeCoefficient) {
		OrdinaryLaser oldOrdinaryLaser = dao.findOne(ordinaryLaser.getId());
		oldOrdinaryLaser.setManagePrice(primeCoefficient.getPeripheralLaser()*100*ordinaryLaser.getPerimeter());
		if(ordinaryLaser.getSingleDouble()==2){
			oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime()/2)
					+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime());
		}else{
			oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime())
					+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime());
		}
		oldOrdinaryLaser.setSingleLaserHandTime(oldOrdinaryLaser.getSingleLaserTime()*1.08*primeCoefficient.getQuickWorker());
		
		oldOrdinaryLaser.setLabourCost(oldOrdinaryLaser.getSingleLaserHandTime()*primeCoefficient.getPerSecondMachinist());
		
		oldOrdinaryLaser.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
				primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*oldOrdinaryLaser.getSingleLaserHandTime());
		
		oldOrdinaryLaser.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*oldOrdinaryLaser.getSingleLaserHandTime());
		
		List<OrdinaryLaser> ordinaryLaserList = dao.findByProductId(oldOrdinaryLaser.getProductId());
		double sum = ordinaryLaserList.stream().mapToDouble(OrdinaryLaser::getLabourCost).sum();
		oldOrdinaryLaser.setStallPrice(sum*primeCoefficient.getEquipmentProfit());
		
		return dao.save(oldOrdinaryLaser);
	}

}
