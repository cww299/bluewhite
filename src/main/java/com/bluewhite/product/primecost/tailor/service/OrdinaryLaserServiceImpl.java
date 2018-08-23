package com.bluewhite.product.primecost.tailor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecostbasedata.dao.BaseThreeDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
@Service
public class OrdinaryLaserServiceImpl extends BaseServiceImpl<OrdinaryLaser, Long>  implements OrdinaryLaserService{

	@Autowired
	private OrdinaryLaserDao  dao;
	@Autowired
	private  BaseThreeDao baseThreeDao;
	
	
	@Override
	public OrdinaryLaser saveOrdinaryLaser(OrdinaryLaser ordinaryLaser,PrimeCoefficient primeCoefficient) {
		OrdinaryLaser oldOrdinaryLaser = dao.findOne(ordinaryLaser.getId());
		//得到理论(市场反馈）含管理价值
		if(oldOrdinaryLaser.getType()=="ordinarylaser"){
			oldOrdinaryLaser.setManagePrice(primeCoefficient.getPeripheralLaser()*100*ordinaryLaser.getPerimeter());
		}
		
		if(oldOrdinaryLaser.getType()=="embroideryLaser"){
			if(ordinaryLaser.getPerimeter()<primeCoefficient.getPerimeterLess()){
				oldOrdinaryLaser.setManagePrice(primeCoefficient.getPerimeterLessNumber()+primeCoefficient.getPerimeterLessNumber());
			}else{
				oldOrdinaryLaser.setManagePrice(primeCoefficient.getPeripheralLaser()*100*ordinaryLaser.getPerimeter()+primeCoefficient.getEmbroideryLaserNumber());
			}
		}
		
		if(oldOrdinaryLaser.getType()=="puncher"){
			
				
		}
			
			oldOrdinaryLaser.setManagePrice(primeCoefficient.getPeripheralLaser()*100*ordinaryLaser.getPerimeter());
		
		
		
		//单片激光需要用净时
		if(ordinaryLaser.getSingleDouble()==2){
			oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime()/2)
					+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime());
		}else{
			oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime())
					+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime());
		}
		//单片激光放快手时间
		oldOrdinaryLaser.setSingleLaserHandTime(oldOrdinaryLaser.getSingleLaserTime()*1.08*primeCoefficient.getQuickWorker());
		//工价（含快手)
		oldOrdinaryLaser.setLabourCost(oldOrdinaryLaser.getSingleLaserHandTime()*primeCoefficient.getPerSecondMachinist());
		//设备折旧和房水电费
		oldOrdinaryLaser.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
				primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*oldOrdinaryLaser.getSingleLaserHandTime());
		//管理人员费用
		oldOrdinaryLaser.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*oldOrdinaryLaser.getSingleLaserHandTime());
		//普通激光切割该裁片费用
		List<OrdinaryLaser> ordinaryLaserList = dao.findByProductId(oldOrdinaryLaser.getProductId());
		double sum = ordinaryLaserList.stream().mapToDouble(OrdinaryLaser::getLabourCost).sum();
		oldOrdinaryLaser.setStallPrice(sum*primeCoefficient.getEquipmentProfit());
		dao.save(oldOrdinaryLaser);
		
		//更新裁剪页面数据（得到实验推算价格）
		
		
		
		
		return ordinaryLaser;
	}

}
