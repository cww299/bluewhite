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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.primecostbasedata.service.MaterielService;

@Service
public class OrdinaryLaserServiceImpl extends BaseServiceImpl<OrdinaryLaser, Long> implements OrdinaryLaserService {

	@Autowired
	private OrdinaryLaserDao dao;
	@Autowired
	private MaterielService materielService;
	@Autowired
	private TailorService tailorService;
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;

	@Override
	@Transactional
	public OrdinaryLaser saveOrdinaryLaser(OrdinaryLaser ordinaryLaser) {
		// 自动将类型为double为空的属性赋值为0
		NumUtils.setzro(ordinaryLaser);
		double managePrice = 0;
		// 在对裁剪方式页面的数据进行更新的时候，同步更新裁剪页面的数据
		Tailor tailor = tailorService.findOne(ordinaryLaser.getTailorId());
		PrimeCoefficient primeCoefficient = primeCoefficientDao.findByType(ordinaryLaser.getTailorType());
		switch (ordinaryLaser.getTailorTypeId().intValue()) {
		case 71:// 普通激光切割
			// 得到理论(市场反馈）含管理价值
			ordinaryLaser.setManagePrice(NumUtils
					.div(NumUtils.mul(primeCoefficient.getPeripheralLaser(), ordinaryLaser.getPerimeter()), 100, 5));
			double singleLaserTime = NumUtils.mul(ordinaryLaser.getPerimeter(), primeCoefficient.getTime(),
					(double) ordinaryLaser.getStallPoint(), primeCoefficient.getPauseTime());
			// 单片激光需要用净时
			if (ordinaryLaser.getSingleDouble() == 2) {
				ordinaryLaser.setSingleLaserTime(
						NumUtils.sum(NumUtils.div(singleLaserTime, 2, 3), ordinaryLaser.getRabbTime()));
			} else {
				ordinaryLaser.setSingleLaserTime(NumUtils.sum(singleLaserTime, ordinaryLaser.getRabbTime()));
			}
			// 单片激光放快手时间
			ordinaryLaser.setSingleLaserHandTime(
					NumUtils.mul(ordinaryLaser.getSingleLaserTime(), 1.08, primeCoefficient.getQuickWorker()));
			// 工价（含快手)
			ordinaryLaser.setLabourCost(
					NumUtils.mul(ordinaryLaser.getSingleLaserHandTime(), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			ordinaryLaser.setEquipmentPrice(NumUtils.mul(
					NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
							primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
					ordinaryLaser.getSingleLaserHandTime()));
			// 管理人员费用
			ordinaryLaser.setAdministrativeAtaff(
					NumUtils.mul(primeCoefficient.getPerSecondManage(), ordinaryLaser.getSingleLaserHandTime()));
			break;
		case 72:// 绣花激光切割
			// 得到理论(市场反馈）含管理价值
			if (ordinaryLaser.getPerimeter() < primeCoefficient.getPerimeterLess()) {
				ordinaryLaser.setManagePrice(NumUtils.sum(primeCoefficient.getPerimeterLessNumber(),
						primeCoefficient.getPerimeterLessNumber()));
			} else {
				ordinaryLaser.setManagePrice(NumUtils.div(
						NumUtils.mul(primeCoefficient.getPeripheralLaser(), ordinaryLaser.getPerimeter()), 100, 5));
			}
			// 单片激光需要用净时
			double singleLaserTimeOne = NumUtils.mul(ordinaryLaser.getPerimeter(), primeCoefficient.getTime(),
					(double) ordinaryLaser.getStallPoint(), primeCoefficient.getPauseTime());
			if (ordinaryLaser.getSingleDouble() == 2) {
				ordinaryLaser.setSingleLaserTime(
						NumUtils.sum(NumUtils.div(singleLaserTimeOne, 2, 3), ordinaryLaser.getRabbTime()));
			} else {
				ordinaryLaser.setSingleLaserTime(NumUtils.sum(singleLaserTimeOne, ordinaryLaser.getRabbTime()));
			}
			// 单片激光放快手时间
			ordinaryLaser.setSingleLaserHandTime(
					NumUtils.mul(ordinaryLaser.getSingleLaserTime(), 1.08, primeCoefficient.getQuickWorker()));
			// 工价（含快手)
			ordinaryLaser.setLabourCost(
					NumUtils.mul(ordinaryLaser.getSingleLaserHandTime(), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			ordinaryLaser.setEquipmentPrice(NumUtils.mul(
					NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
							primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
					ordinaryLaser.getSingleLaserHandTime()));
			// 管理人员费用
			ordinaryLaser.setAdministrativeAtaff(
					NumUtils.mul(primeCoefficient.getPerSecondManage(), ordinaryLaser.getSingleLaserHandTime()));

			break;
		case 73:// 手工电烫
			managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(),
					ordinaryLaser.getTailorSizeId());
			// 得到理论(市场反馈）含管理价值
			ordinaryLaser.setManagePrice(managePrice);
			// 电烫秒数（含快手)
			if (ordinaryLaser.getTypesettingNumber() != 0) {
				ordinaryLaser.setPermSeconds(
						NumUtils.div(NumUtils.mul(primeCoefficient.getPermThree(), primeCoefficient.getQuickWorker()),
								ordinaryLaser.getTypesettingNumber(), 5));
			}
			// 撕片秒数（含快手)
			double tearingSeconds = 0;
			// 易
			if (primeCoefficient.getPermFour() == 0) {
				tearingSeconds = materielService.getBaseThreeOne((long) 79, ordinaryLaser.getTailorSizeId());
				// 难
			} else if (primeCoefficient.getPermFour() == 1) {
				tearingSeconds = materielService.getBaseThreeOne((long) 80, ordinaryLaser.getTailorSizeId());
			}
			ordinaryLaser.setTearingSeconds(NumUtils.mul(tearingSeconds, primeCoefficient.getQuickWorker()));
			// 拉布秒数（含快手)
			ordinaryLaser.setRabbTime(NumUtils.division(NumUtils.div(primeCoefficient.getPermOne(),
					NumUtils.mul(NumUtils.div(1.5, ordinaryLaser.getTailorSize().getOrdinaryLaser(), 5),
							primeCoefficient.getQuickWorker()),
					5)));
			// 电烫工价（含快手)
			ordinaryLaser.setPermPrice(
					NumUtils.mul(NumUtils.sum(ordinaryLaser.getPermSeconds(), ordinaryLaser.getRabbTime()),
							primeCoefficient.getPerSecondMachinist()));
			// 撕片工价
			ordinaryLaser.setTearingPrice(
					NumUtils.mul(ordinaryLaser.getTearingSeconds(), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			ordinaryLaser.setEquipmentPrice(NumUtils.mul(
					NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
							primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
					NumUtils.sum(ordinaryLaser.getPermSeconds(), ordinaryLaser.getRabbTime())));
			// 管理人员费用
			ordinaryLaser.setAdministrativeAtaff(NumUtils.mul(primeCoefficient.getPerSecondManage(), NumUtils.sum(
					ordinaryLaser.getPermSeconds(), ordinaryLaser.getTearingSeconds(), ordinaryLaser.getRabbTime())));
			break;
		case 74:// 设备电烫

			break;
		case 75:// 冲床
			managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(),
					ordinaryLaser.getTailorSizeId());
			// 得到理论(市场反馈）含管理价值
			ordinaryLaser.setManagePrice(managePrice);
			// 叠布秒数（含快手)
			ordinaryLaser
					.setOverlappedSeconds(NumUtils.division(NumUtils.div(
							(ordinaryLaser.getNumber() < primeCoefficient.getPuncherTwo()
									? primeCoefficient.getPuncherFour()
									: NumUtils.mul(primeCoefficient.getPuncherOne(), ordinaryLaser.getLayerNumber())),
							NumUtils.div(
									NumUtils.mul(ordinaryLaser.getLayerNumber(), primeCoefficient.getPuncherThree(),
											1.5),
									NumUtils.mul(ordinaryLaser.getTailorSize().getOrdinaryLaser(),
											primeCoefficient.getQuickWorker()),
									5),
							5)));
			if (ordinaryLaser.getLayerNumber() != 0) {
				ordinaryLaser.setPunchingSeconds(
						NumUtils.mul(NumUtils.div(primeCoefficient.getPuncherFive(), ordinaryLaser.getLayerNumber(), 5),
								primeCoefficient.getQuickWorker()));
			}

			// 工价（含快手)
			ordinaryLaser.setLabourCost(
					NumUtils.mul(NumUtils.sum(ordinaryLaser.getOverlappedSeconds(), ordinaryLaser.getPunchingSeconds(),
							ordinaryLaser.getOtherTimeOne(), ordinaryLaser.getOtherTimeThree(),
							ordinaryLaser.getOtherTimeTwo()), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			ordinaryLaser.setEquipmentPrice(NumUtils.mul(
					NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
							primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
					NumUtils.sum(ordinaryLaser.getOverlappedSeconds(), ordinaryLaser.getPunchingSeconds(),
							ordinaryLaser.getOtherTimeOne(), ordinaryLaser.getOtherTimeThree(),
							ordinaryLaser.getOtherTimeTwo())));

			// 管理人员费用
			ordinaryLaser.setAdministrativeAtaff(
					NumUtils.mul(NumUtils.sum(ordinaryLaser.getOverlappedSeconds(), ordinaryLaser.getPunchingSeconds()),
							primeCoefficient.getPerSecondManage()));

			break;
		case 76:// 电推
			managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(),
					ordinaryLaser.getTailorSizeId());
			// 得到理论(市场反馈）含管理价值
			ordinaryLaser.setManagePrice(managePrice);
			// 叠布秒数（含快手)
			double num = NumUtils.mul(
					NumUtils.mul(primeCoefficient.getElectricPushTwo(), 1.5, ordinaryLaser.getLayerNumber()),
					ordinaryLaser.getTailorSize().getOrdinaryLaser());
			if (num != 0) {
				ordinaryLaser.setOverlappedSeconds(NumUtils.div(NumUtils.sum(
						NumUtils.mul(primeCoefficient.getElectricPushOne(), ordinaryLaser.getLayerNumber()),
						primeCoefficient.getElectricPushThree(), (double) primeCoefficient.getElectricPushFour()), num,
						5));
			}
			// 电推秒数（含快手)
			if(primeCoefficient.getElectricPushFive() !=0 && primeCoefficient.getElectricPushSix()!=0){
				ordinaryLaser.setElectricSeconds(NumUtils.div(
						NumUtils.div(ordinaryLaser.getPerimeter(),
								NumUtils.mul(primeCoefficient.getElectricPushFive(),primeCoefficient.getElectricPushSix()),
								5),
						ordinaryLaser.getLayerNumber(), 5));
			}
			// 工价（含快手)
			ordinaryLaser.setLabourCost(
					NumUtils.mul(NumUtils.sum(ordinaryLaser.getOverlappedSeconds(), ordinaryLaser.getElectricSeconds()),
							primeCoefficient.getQuickWorker(), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			ordinaryLaser.setEquipmentPrice(NumUtils.mul(
					NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
							primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
					NumUtils.sum(ordinaryLaser.getOverlappedSeconds(), ordinaryLaser.getElectricSeconds())));
			// 管理人员费用
			ordinaryLaser.setAdministrativeAtaff(
					NumUtils.mul(NumUtils.sum(ordinaryLaser.getOverlappedSeconds(), ordinaryLaser.getElectricSeconds()),
							ordinaryLaser.getSingleLaserHandTime()));
			break;
		case 77:// 手工剪刀
			managePrice = materielService.getBaseThreeOne(ordinaryLaser.getTailorTypeId(),
					ordinaryLaser.getTailorSizeId());
			// 得到理论(市场反馈）含管理价值
			ordinaryLaser.setManagePrice(managePrice);
			// 手工秒数（含快手)
			ordinaryLaser.setManualSeconds(NumUtils.mul(ordinaryLaser.getPerimeter(), primeCoefficient.getManualTwo()));
			// 工价（含快手)
			ordinaryLaser.setLabourCost(
					NumUtils.mul(ordinaryLaser.getManualSeconds(), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			ordinaryLaser.setEquipmentPrice(
					NumUtils.mul(ordinaryLaser.getManualSeconds(), primeCoefficient.getPerSecondPrice()));
			// 管理人员费用
			ordinaryLaser.setAdministrativeAtaff(
					NumUtils.mul(primeCoefficient.getPerSecondManage(), ordinaryLaser.getManualSeconds()));
			break;
		case 78:// 绣花领取
			break;
		default:
			break;
		}
		// 普通激光切割该裁片费用
		ordinaryLaser.setStallPrice(
				NumUtils.mul(NumUtils.sum(ordinaryLaser.getLabourCost(), ordinaryLaser.getEquipmentPrice(),
						ordinaryLaser.getAdministrativeAtaff()), primeCoefficient.getEquipmentProfit()));
		// 同时需要去更新裁剪页面的（得到实验推算价格）
		tailor.setExperimentPrice(ordinaryLaser.getStallPrice());
		dao.save(ordinaryLaser);
		tailorService.save(tailor);
		return ordinaryLaser;
	}

	@Override
	public PageResult<OrdinaryLaser> findPages(OrdinaryLaser param, PageParameter page) {
		Page<OrdinaryLaser> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
			}
			// 按类型id过滤
			if (param.getTailorTypeId() != null) {
				predicate.add(cb.equal(root.get("tailorTypeId").as(Long.class), param.getTailorTypeId()));
			}
			// 按裁片名称过滤
			if (!StringUtils.isEmpty(param.getTailorName())) {
				predicate.add(cb.like(root.get("tailorName").as(String.class), "%" + param.getTailorName() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<OrdinaryLaser> result = new PageResult<OrdinaryLaser>(pages, page);
		return result;
	}

	@Override
	public OrdinaryLaser findByTailorId(Long tailorId) {
		return dao.findByTailorId(tailorId);
	}

}
