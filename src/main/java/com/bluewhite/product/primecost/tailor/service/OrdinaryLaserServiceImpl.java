package com.bluewhite.product.primecost.tailor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.primecostbasedata.service.MaterielService;
@Service
public class OrdinaryLaserServiceImpl extends BaseServiceImpl<OrdinaryLaser, Long>  implements OrdinaryLaserService{

	@Autowired
	private OrdinaryLaserDao  dao;
	@Autowired
	private MaterielService materielService ;
	@Autowired
	private TailorService tailorService ;
	
	
	@Override
	public OrdinaryLaser saveOrdinaryLaser(OrdinaryLaser ordinaryLaser,PrimeCoefficient primeCoefficient) {
		OrdinaryLaser oldOrdinaryLaser = dao.findOne(ordinaryLaser.getId());
		double managePrice = 0;
			switch (ordinaryLaser.getTailorTypeId().intValue()) {
			case 71://普通激光切割
				//得到理论(市场反馈）含管理价值
				oldOrdinaryLaser.setManagePrice(primeCoefficient.getPeripheralLaser()*100*ordinaryLaser.getPerimeter());
				//单片激光需要用净时
				if(ordinaryLaser.getSingleDouble()==2){
					oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime()/2)
							+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime());
				}else{
					oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime())
							+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime());
				}
				break;
			case 72://绣花激光切割
				//得到理论(市场反馈）含管理价值
				if(ordinaryLaser.getPerimeter()<primeCoefficient.getPerimeterLess()){
					oldOrdinaryLaser.setManagePrice(primeCoefficient.getPerimeterLessNumber()+primeCoefficient.getPerimeterLessNumber());
				}else{
					oldOrdinaryLaser.setManagePrice(primeCoefficient.getPeripheralLaser()*100*ordinaryLaser.getPerimeter()+primeCoefficient.getEmbroideryLaserNumber());
				}
				//单片激光需要用净时
				if(ordinaryLaser.getSingleDouble()==2){
					oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime()/2)
							+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime()+ordinaryLaser.getEmbroiderTime());
				}else{
					oldOrdinaryLaser.setSingleLaserTime((ordinaryLaser.getPerimeter()*primeCoefficient.getTime()*ordinaryLaser.getStallPoint()*primeCoefficient.getPauseTime())
							+ ordinaryLaser.getRabbTime()+ordinaryLaser.getTime()+ordinaryLaser.getEmbroiderTime());
				}
				break;
			case 73://手工电烫
				managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(), ordinaryLaser.getTailorSize());
				//得到理论(市场反馈）含管理价值
				oldOrdinaryLaser.setManagePrice(managePrice);
				//电烫秒数（含快手)
				oldOrdinaryLaser.setPermSeconds(primeCoefficient.getPermThree()/ordinaryLaser.getTypesettingNumber()*primeCoefficient.getQuickWorker());
//				//撕片秒数（含快手)
//				oldOrdinaryLaser.setTearingSeconds(tearingSeconds);
//				//拉布秒数（含快手)
//				oldOrdinaryLaser.setPermPrice(permPrice);
				
				break;
			case 74://设备电烫
				
				
				
				break;
			case 75://冲床
				
				managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(), ordinaryLaser.getTailorSize());
				//得到理论(市场反馈）含管理价值
				oldOrdinaryLaser.setManagePrice(managePrice);
				//叠布秒数（含快手)
				oldOrdinaryLaser.setOverlappedSeconds(
						(ordinaryLaser.getNumber()<primeCoefficient.getPuncherTwo() 
								? primeCoefficient.getPuncherFour() 
										: (primeCoefficient.getPuncherOne()*ordinaryLaser.getLayerNumber()))
								/ ((ordinaryLaser.getLayerNumber()*primeCoefficient.getPuncherThree()*1.5)
										/(ordinaryLaser.getTailorSize()*primeCoefficient.getQuickWorker()))
									
						);
				oldOrdinaryLaser.setPunchingSeconds(primeCoefficient.getPuncherFive()/ordinaryLaser.getLayerNumber()*primeCoefficient.getQuickWorker());	
				
				//工价（含快手)
				oldOrdinaryLaser.setLabourCost(
						(oldOrdinaryLaser.getOverlappedSeconds()+oldOrdinaryLaser.getPunchingSeconds()+oldOrdinaryLaser.getOtherTimeOne()+oldOrdinaryLaser.getOtherTimeThree()+oldOrdinaryLaser.getOtherTimeTwo())
						*primeCoefficient.getPerSecondMachinist()
						);
				//设备折旧和房水电费
				oldOrdinaryLaser.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
						primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())
						*(oldOrdinaryLaser.getOverlappedSeconds()+oldOrdinaryLaser.getPunchingSeconds()+oldOrdinaryLaser.getOtherTimeOne()+oldOrdinaryLaser.getOtherTimeThree()+oldOrdinaryLaser.getOtherTimeTwo())
						);
				
				//管理人员费用
				oldOrdinaryLaser.setAdministrativeAtaff((oldOrdinaryLaser.getOverlappedSeconds()+oldOrdinaryLaser.getPunchingSeconds())*primeCoefficient.getPerSecondManage());
				//普通激光切割该裁片费用
				oldOrdinaryLaser.setStallPrice((oldOrdinaryLaser.getLabourCost()+oldOrdinaryLaser.getEquipmentPrice()+oldOrdinaryLaser.getAdministrativeAtaff())*primeCoefficient.getEquipmentProfit());
				//同时需要去更新裁剪页面的（得到实验推算价格）
				Tailor tailor = tailorService.findOne(oldOrdinaryLaser.getTailorId());
				tailor.setExperimentPrice(oldOrdinaryLaser.getStallPrice());
				tailorService.save(tailor);
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
		oldOrdinaryLaser.setStallPrice((oldOrdinaryLaser.getLabourCost()+oldOrdinaryLaser.getEquipmentPrice()+oldOrdinaryLaser.getAdministrativeAtaff())*primeCoefficient.getEquipmentProfit());
		
		
		
		
		
		dao.save(oldOrdinaryLaser);
		
		
		
		
		
		return ordinaryLaser;
	}

}
