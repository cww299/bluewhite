package com.bluewhite.product.primecost.tailor.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
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
		//在对裁剪方式页面的数据进行更新的时候，同步更新裁剪页面的数据
		Tailor tailor = tailorService.findOne(oldOrdinaryLaser.getTailorId());
		
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
				//单片激光放快手时间
				oldOrdinaryLaser.setSingleLaserHandTime(oldOrdinaryLaser.getSingleLaserTime()*1.08*primeCoefficient.getQuickWorker());
				//工价（含快手)
				oldOrdinaryLaser.setLabourCost(oldOrdinaryLaser.getSingleLaserHandTime()*primeCoefficient.getPerSecondMachinist());
				//设备折旧和房水电费
				oldOrdinaryLaser.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
						primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*oldOrdinaryLaser.getSingleLaserHandTime());
				//管理人员费用
				oldOrdinaryLaser.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*oldOrdinaryLaser.getSingleLaserHandTime());
				
				
				
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
				//单片激光放快手时间
				oldOrdinaryLaser.setSingleLaserHandTime(oldOrdinaryLaser.getSingleLaserTime()*1.08*primeCoefficient.getQuickWorker());
				//工价（含快手)
				oldOrdinaryLaser.setLabourCost(oldOrdinaryLaser.getSingleLaserHandTime()*primeCoefficient.getPerSecondMachinist());
				//设备折旧和房水电费
				oldOrdinaryLaser.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
						primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*oldOrdinaryLaser.getSingleLaserHandTime());
				//管理人员费用
				oldOrdinaryLaser.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*oldOrdinaryLaser.getSingleLaserHandTime());
				
				break;
			case 73://手工电烫
				managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(), ordinaryLaser.getTailorSize());
				//得到理论(市场反馈）含管理价值
				oldOrdinaryLaser.setManagePrice(managePrice);
				//电烫秒数（含快手)
				oldOrdinaryLaser.setPermSeconds(primeCoefficient.getPermThree()/ordinaryLaser.getTypesettingNumber()*primeCoefficient.getQuickWorker());
				//撕片秒数（含快手)
				double tearingSeconds = 0;
				//易
				if(primeCoefficient.getPermFour()==0){
					tearingSeconds = materielService.getBaseThreeOne((long)79, ordinaryLaser.getTailorSize());
				//难
				}else if(primeCoefficient.getPermFour()==1){
					tearingSeconds = materielService.getBaseThreeOne((long)80, ordinaryLaser.getTailorSize());
				}
				oldOrdinaryLaser.setTearingSeconds(tearingSeconds*primeCoefficient.getQuickWorker());
				//拉布秒数（含快手)
				oldOrdinaryLaser.setRabbTime((primeCoefficient.getPermOne()/1.5/oldOrdinaryLaser.getTailorSize())*primeCoefficient.getQuickWorker());
				//电烫工价（含快手)
				oldOrdinaryLaser.setPermPrice((oldOrdinaryLaser.getPermSeconds()+oldOrdinaryLaser.getRabbTime())*primeCoefficient.getPerSecondMachinist());
				//撕片工价
				oldOrdinaryLaser.setTearingPrice(oldOrdinaryLaser.getTearingSeconds()*primeCoefficient.getPerSecondMachinist());
				//设备折旧和房水电费
				oldOrdinaryLaser.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
						primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())*(oldOrdinaryLaser.getPermSeconds()+oldOrdinaryLaser.getRabbTime()));
				//管理人员费用
				oldOrdinaryLaser.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*(oldOrdinaryLaser.getPermSeconds()+oldOrdinaryLaser.getTearingSeconds()+oldOrdinaryLaser.getRabbTime()));
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
			
			
				break;
			case 76://电推
				managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(), ordinaryLaser.getTailorSize());
				//得到理论(市场反馈）含管理价值
				oldOrdinaryLaser.setManagePrice(managePrice);
				//叠布秒数（含快手)
				oldOrdinaryLaser.setOverlappedSeconds((primeCoefficient.getElectricPushOne()*ordinaryLaser.getLayerNumber()+primeCoefficient.getElectricPushThree()+primeCoefficient.getElectricPushFour())
						/(primeCoefficient.getElectricPushTwo()*1.5*ordinaryLaser.getLayerNumber())/ordinaryLaser.getTailorSize());
				//电推秒数（含快手)
				oldOrdinaryLaser.setElectricSeconds((ordinaryLaser.getPerimeter()/primeCoefficient.getElectricPushFive()*primeCoefficient.getElectricPushSix())
						/ordinaryLaser.getLayerNumber());
				//工价（含快手)
				oldOrdinaryLaser.setLabourCost((oldOrdinaryLaser.getOverlappedSeconds()+oldOrdinaryLaser.getElectricSeconds())*primeCoefficient.getQuickWorker()*primeCoefficient.getPerSecondMachinist());
				//设备折旧和房水电费
				oldOrdinaryLaser.setEquipmentPrice((primeCoefficient.getDepreciation()+primeCoefficient.getLaserTubePriceSecond()+
						primeCoefficient.getMaintenanceChargeSecond()+primeCoefficient.getPerSecondPrice())
						*(oldOrdinaryLaser.getOverlappedSeconds()+oldOrdinaryLaser.getElectricSeconds()));
				//管理人员费用
				oldOrdinaryLaser.setAdministrativeAtaff((oldOrdinaryLaser.getOverlappedSeconds()+oldOrdinaryLaser.getElectricSeconds())*oldOrdinaryLaser.getSingleLaserHandTime());
				break;
			case 77://手工剪刀
				managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(), ordinaryLaser.getTailorSize());
				//得到理论(市场反馈）含管理价值
				oldOrdinaryLaser.setManagePrice(managePrice);
				//手工秒数（含快手)
				oldOrdinaryLaser.setManualSeconds(ordinaryLaser.getPerimeter()*primeCoefficient.getManualTwo());
				//工价（含快手)
				oldOrdinaryLaser.setLabourCost(oldOrdinaryLaser.getManualSeconds()*primeCoefficient.getPerSecondMachinist());
				//设备折旧和房水电费
				oldOrdinaryLaser.setEquipmentPrice(oldOrdinaryLaser.getManualSeconds()*primeCoefficient.getPerSecondPrice());
				//管理人员费用
				oldOrdinaryLaser.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()*oldOrdinaryLaser.getManualSeconds());
				break;
			case 78://绣花领取
				break;
			default:
				break;
			}	
		//普通激光切割该裁片费用
		oldOrdinaryLaser.setStallPrice((oldOrdinaryLaser.getLabourCost()+oldOrdinaryLaser.getEquipmentPrice()+oldOrdinaryLaser.getAdministrativeAtaff())*primeCoefficient.getEquipmentProfit());
		//同时需要去更新裁剪页面的（得到实验推算价格）
		tailor.setExperimentPrice(oldOrdinaryLaser.getStallPrice());
		dao.save(oldOrdinaryLaser);
		tailorService.save(tailor);
		return ordinaryLaser;
	}


	@Override
	public PageResult<OrdinaryLaser> findPages(OrdinaryLaser param, PageParameter page) {
			Page<OrdinaryLaser> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按产品id过滤
	        	if (param.getProductId() != null) {
					predicate.add(cb.equal(root.get("productId").as(Long.class),param.getProductId()));
				}
	        	//按类型id过滤
	        	if (param.getTailorTypeId() != null) {
					predicate.add(cb.equal(root.get("tailorTypeId").as(Long.class),param.getTailorTypeId()));
				}
	        	//按裁片名称过滤
	        	if (!StringUtils.isEmpty(param.getTailorName())) {
					predicate.add(cb.like(root.get("tailorName").as(String.class),"%"+param.getTailorName()+"%"));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
		 PageResult<OrdinaryLaser> result = new PageResult<OrdinaryLaser>(pages,page);
		return result;
		}

}
