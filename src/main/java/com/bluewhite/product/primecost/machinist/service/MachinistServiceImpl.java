package com.bluewhite.product.primecost.machinist.service;

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
import com.bluewhite.product.primecost.machinist.dao.MachinistDao;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.primecostbasedata.service.MaterielService;

@Service
public class MachinistServiceImpl extends BaseServiceImpl<Machinist, Long> implements MachinistService {

	@Autowired
	private MachinistDao dao;

	@Autowired
	private TailorDao tailorDao;

	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;

	@Autowired
	private MaterielService materielService;

	@Override
	@Transactional
	public Machinist saveMachinist(Machinist machinist) {
		PrimeCoefficient primeCoefficient = primeCoefficientDao.findByType("machinist");
		// 自动将类型为null的属性赋值为0
		NumUtils.setzro(machinist);
		// 在填写机工名称时，同时将裁片或上到填写，第一次填写，此时裁片的压价可以显示，机工的压价没有，
		// 在填完之后，同时保存更新技工的压价，也就是裁片的压价总和 .
		// 原理，机工的压价是只能通过裁片的压价获取到
		// 当我第一次填写机工名称时，同时传入了裁片的压价，此时更新当前机工的压价
		double sumPrice = 0;
		if (!StringUtils.isEmpty(machinist.getCutparts())) {
			String[] arr = machinist.getCutparts().split(",");
			String[] arrPrice = machinist.getCutpartsPrice().split(",");
			machinist.setCutpartsNumber(arr.length);
			if (arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					List<Tailor> tailorList = tailorDao.findByProductId(machinist.getProductId());
					for (Tailor tr : tailorList) {
						if (arr[i].equals(tr.getTailorName())) {
							sumPrice += Double.parseDouble(arrPrice[i]);
						}
					}
				}
			}
		}
		// 更新当前机工名称的机工压价，相当于裁片压价的总和，相当于ao
		machinist.setSumPriceDownRemark(sumPrice);
		// 更新机工压价的总和，也就是ap
		double sumPriceDownRemark = 0;
		if (!StringUtils.isEmpty(machinist.getCutparts())) {
			String[] arr = machinist.getCutpartsPrice().split(",");
			if (arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					List<Machinist> machinistList1 = dao.findByProductId(machinist.getProductId());
					machinistList1.add(machinist);
					for (Machinist mc : machinistList1) {
						if (arr[i].equals(mc.getCutpartsNumber())) {
							sumPriceDownRemark += mc.getSumPriceDownRemark();
						}
					}
				}
			}
		}

		// 该工序涉及回针次数时间/秒
		machinist.setBackStitch(NumUtils.mul(machinist.getBackStitchCount(), primeCoefficient.getMachinistThree()));
		// 该工序涉及粘片次数时间/秒
		machinist.setSticking(NumUtils.mul(primeCoefficient.getMachinistOne(), machinist.getCutpartsNumber()));

		if (machinist.getNeedleSpurId() != null && machinist.getBeelineId() != null) {
			double baseFourDateOne = materielService.getBaseFourDate(machinist.getBeelineId(),
					machinist.getNeedleSpurId());
			machinist.setBaseFourDateOne(baseFourDateOne);
			if (baseFourDateOne != 0) {
				// 1类模式可走（每CM时间/秒）
				machinist.setModeOne(NumUtils.div(machinist.getBeelineNumber(), machinist.getBaseFourDateOne(),3));
			}
		}
		if (machinist.getNeedleSpurId() != null && machinist.getArcId() != null) {
			double baseFourDateTwo = materielService.getBaseFourDate(machinist.getArcId(),
					machinist.getNeedleSpurId());
			machinist.setBaseFourDateTwo(baseFourDateTwo);
			if (baseFourDateTwo != 0) {
				// 2类模式可走（每CM时间/秒）
				machinist.setModeTwo(NumUtils.div(machinist.getArcNumber(), machinist.getBaseFourDateTwo() ,3));
			}
		}
		if (machinist.getNeedleSpurId() != null && machinist.getBendId() != null) {
			double baseFourDateThree = materielService.getBaseFourDate(machinist.getBendId(),
					machinist.getNeedleSpurId());
			machinist.setBaseFourDateThree(baseFourDateThree);
			if (baseFourDateThree != 0) {
				// 3类模式可走（每CM时间/秒）
				machinist.setModeThree(NumUtils.div(machinist.getBendNumber() , machinist.getBaseFourDateThree() ,3));
			}
		}

		// 单一机缝需要时间/秒
		machinist.setOneSewingTime(NumUtils.sum(machinist.getBackStitch(), machinist.getSticking(),
				machinist.getModeOne(), machinist.getModeTwo(), machinist.getModeThree()));
		// 剪线时间/秒
		machinist.setCutLineTime(primeCoefficient.getMachinisttwo());
		// 单片机缝放快手时间
		machinist.setSewingQuickWorkerTime(
				NumUtils.mul(machinist.getOneSewingTime(), 1.08, primeCoefficient.getMachinistThree()));
		// 单片剪线放快手时间
		machinist.setLineQuickWorkerTime(
				NumUtils.mul(machinist.getCutLineTime(), 1.08, primeCoefficient.getMachinistThree()));
		// 试制大工工价（含快手)
		machinist.setTrialProducePrice(
				NumUtils.mul(machinist.getTime(), 1.08, primeCoefficient.getPerSecondMachinist()));
		// 电脑推算大工工价（含快手)
		machinist.setReckoningPrice(
				NumUtils.mul(machinist.getSewingQuickWorkerTime(), primeCoefficient.getPerSecondMachinist()));
		// 剪线工价
		machinist.setCutLinePrice(
				NumUtils.mul(machinist.getLineQuickWorkerTime(), primeCoefficient.getPerSecondMachinistTwo()));
		// 设备折旧和房水电费
		machinist.setEquipmentPrice(NumUtils.mul(
				NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
						primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
				machinist.getTime()));
		// 管理人员费用
		machinist.setAdministrativeAtaff(NumUtils.mul(primeCoefficient.getPerSecondManage(), machinist.getTime()));
		// 电脑推算机缝该工序费用
		machinist
				.setReckoningSewingPrice(NumUtils.mul(
						NumUtils.sum(machinist.getReckoningPrice(), machinist.getEquipmentPrice(),
								machinist.getCutLinePrice(), machinist.getAdministrativeAtaff()),
						primeCoefficient.getEquipmentProfit()));
		// 试制机缝该工序费用
		machinist.setTrialSewingPrice(NumUtils.mul(
				NumUtils.sum(machinist.getTrialProducePrice(), machinist.getCutLinePrice(),
						machinist.getEquipmentPrice(), machinist.getAdministrativeAtaff()),
				primeCoefficient.getEquipmentProfit()));
		// 入成本价格
		machinist.setAllCostPrice(NumUtils.mul(machinist.getNumber(), machinist.getCostPrice()));
		// 各单道比全套工价
		List<Machinist> machinistList = dao.findByProductId(machinist.getProductId());
		machinistList.add(machinist);

		double sumAllCostPrice = machinistList.stream().filter(Machinist -> Machinist.getAllCostPrice() != null)
				.mapToDouble(Machinist::getAllCostPrice).sum();
		if (sumAllCostPrice != 0) {
			double scaleMaterial = NumUtils.div(machinist.getAllCostPrice(), sumAllCostPrice, 3);
			machinist.setScaleMaterial(scaleMaterial);
		}
		// 物料和上道压（裁剪）价
		machinist.setPriceDown(NumUtils.sum(machinist.getAllCostPrice(), sumPrice));
		// 物料和上道压（裁剪,上道机工）价-只有在当行机工环节单独发放给某个加工店，这个才起作用
		machinist.setPriceDownRemark(NumUtils.sum(machinist.getAllCostPrice(), sumPrice, sumPriceDownRemark));
		// 为针工准备的压价
		machinist.setNeedleworkPriceDown(NumUtils.sum(machinist.getAllCostPrice(), machinist.getPriceDown()));
		// 单独机工工序外发的压价
		machinist.setMachinistPriceDown(NumUtils.sum(machinist.getAllCostPrice(), machinist.getPriceDownRemark()));
		return dao.save(machinist);
	}

	@Override
	public PageResult<Machinist> findPages(Machinist param, PageParameter page) {
		Page<Machinist> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
			}
			// 按裁片名称过滤
			if (!StringUtils.isEmpty(param.getMachinistName())) {
				predicate
						.add(cb.like(root.get("machinistName").as(String.class), "%" + param.getMachinistName() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Machinist> result = new PageResult<Machinist>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void deleteProductMaterials(Long id) {
		dao.delete(id);
	}

	@Override
	public List<Machinist> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}

}
