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
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.tailor.dao.OrdinaryLaserDao;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.BaseThreeDao;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;
import com.bluewhite.product.primecostbasedata.service.MaterielService;
import com.bluewhite.product.product.dao.ProductDao;

@Service
public class TailorServiceImpl extends BaseServiceImpl<Tailor, Long> implements TailorService {

	@Autowired
	private TailorDao dao;
	@Autowired
	private ProductDao productdao;
	@Autowired
	private CutPartsDao cutPartsDao;
	@Autowired
	private OrdinaryLaserDao ordinaryLaserDao;
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;
	@Autowired
	private MaterielService materielService;
	@Autowired
	private BaseThreeDao baseThreeDao;

	@Override
	@Transactional
	public Tailor saveTailor(Tailor tailor) {
		NumUtils.setzro(tailor);
		// 得到理论(市场反馈）含管理价值
		Double managePrice = materielService.getBaseThreeOne(tailor.getTailorTypeId(), tailor.getTailorSizeId());
		tailor.setManagePrice(managePrice);
		//裁剪方式
		OrdinaryLaser prams = null;
		if(tailor.getOrdinaryLaserId()==null){
			prams = new OrdinaryLaser();
		}else{
			prams = ordinaryLaserDao.findOne(tailor.getOrdinaryLaserId());
		}
		NumUtils.setzro(prams);
		// 得到实验推算价格
		tailor.setExperimentPrice(prams.getStallPrice());
		if(tailor.getCostPriceSelect() == 0){
			tailor.setCostPriceSelect(1);
		}
		if(tailor.getCostPriceSelect() == 1){
			tailor.setCostPrice(tailor.getManagePrice());
		}else if(tailor.getCostPriceSelect() == 2){
			tailor.setCostPrice(tailor.getExperimentPrice());
		}
		// 总入成本价格
		tailor.setAllCostPrice(NumUtils.mul(tailor.getBacthTailorNumber() , tailor.getCostPrice()));
		// 得到市场价与实推价比
		if (tailor.getExperimentPrice() != 0) {
			tailor.setRatePrice(NumUtils.div(tailor.getExperimentPrice(), tailor.getCostPrice(), 3));
		}
		// 各单道比全套工价
		List<Tailor> tailorList = dao.findByProductId(tailor.getProductId());
		double sumAllCostPrice = tailorList.stream().filter(Tailor -> Tailor.getAllCostPrice() != null).mapToDouble(Tailor::getAllCostPrice).sum();
		if(sumAllCostPrice!=0){
			tailor.setScaleMaterial(NumUtils.div(tailor.getAllCostPrice(),sumAllCostPrice,3));
		}
		// 不含绣花环节的为机工压价
		tailor.setNoeMbroiderPriceDown(NumUtils.sum(tailor.getAllCostPrice() , tailor.getPriceDown()));
		// 含绣花环节的为机工压价(填写了绣花工序时才会出现)
		
		// 为机工准备的压价
		double machinistPriceDown = tailor.getNoeMbroiderPriceDown() >= tailor.getEmbroiderPriceDown() ? tailor.getNoeMbroiderPriceDown() : tailor.getEmbroiderPriceDown();
		tailor.setMachinistPriceDown(machinistPriceDown);
		prams.setTailorTypeId(tailor.getTailorTypeId());
		getOrdinaryLaserDate(tailor, prams);
		return tailor;
	}

	// 根据裁剪类型进行计算出不同的价格,获取裁剪页面所需要的数据
	@Override
	public OrdinaryLaser getOrdinaryLaserDate(Tailor tailor, OrdinaryLaser prams) {
		//裁剪页面的基础系数
		PrimeCoefficient primeCoefficient = null;
		String type = null;
		CutParts cutParts = cutPartsDao.findOne(tailor.getCutPartsId());
		prams.setProductId(tailor.getProductId());
		prams.setTailorTypeId(tailor.getTailorTypeId());
		prams.setTailorName(tailor.getTailorName());
		prams.setNumber(tailor.getNumber());
		prams.setTailorNumber(tailor.getTailorNumber());
		prams.setTailorSizeId(tailor.getTailorSizeId());
		prams.setTailorId(tailor.getId());
		prams.setPerimeter(NumUtils.setzro(cutParts.getPerimeter()));
		prams.setTime(prams.getTime() != null ? prams.getTime() : 0.5);
		double tailorSize = baseThreeDao.findOne(prams.getTailorSizeId()).getOrdinaryLaser();
		switch (tailor.getTailorTypeId().intValue()) {
		case 71:// 普通激光切割
			type = "ordinarylaser";
			primeCoefficient = primeCoefficientDao.findByType(type);
			prams.setTailorType(type);
			tailor.setTailorType(type);
			prams.setSingleDouble(2);
			prams.setStallPoint(1);
			double singleLaserTime = NumUtils.mul(prams.getPerimeter(), primeCoefficient.getTime(),
					(double)prams.getStallPoint(), primeCoefficient.getPauseTime());
			// 拉布时间
			if(primeCoefficient.getQuilt()!=0){
				prams.setRabbTime(NumUtils.mul(NumUtils.div(tailorSize, primeCoefficient.getQuilt(), 3),
						primeCoefficient.getRabbTime()));
			}
			// 单片激光需要用净时
			if (prams.getSingleDouble() == 2) {
				prams.setSingleLaserTime(NumUtils.sum(NumUtils.div(singleLaserTime, 2, 3), prams.getRabbTime()));
			} else {
				prams.setSingleLaserTime(NumUtils.sum(singleLaserTime, prams.getRabbTime()));
			}
			// 单片激光放快手时间
			prams.setSingleLaserHandTime(
					NumUtils.mul(prams.getSingleLaserTime(), 1.08, primeCoefficient.getQuickWorker()));
			// 工价（含快手)
			prams.setLabourCost(NumUtils.mul(prams.getSingleLaserHandTime(), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			prams.setEquipmentPrice(NumUtils.mul(
					NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
							primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
					prams.getSingleLaserHandTime()));
			// 管理人员费用
			prams.setAdministrativeAtaff(
					NumUtils.mul(primeCoefficient.getPerSecondManage(), prams.getSingleLaserHandTime()));
			// 普通激光切割该裁片费用
			prams.setStallPrice(NumUtils.mul(
					NumUtils.sum(prams.getLabourCost(), prams.getEquipmentPrice(), prams.getAdministrativeAtaff()),
					primeCoefficient.getEquipmentProfit()));

			break;
		case 72:// 绣花激光切割
			type = "embroideryLaser";
			primeCoefficient = primeCoefficientDao.findByType(type);
			prams.setTailorType(type);
			tailor.setTailorType(type);
			prams.setSingleDouble(2);
			prams.setStallPoint(1);
			// 得到理论(市场反馈）含管理价值
			if (prams.getPerimeter() < primeCoefficient.getPerimeterLess()) {
				prams.setManagePrice(
						primeCoefficient.getPerimeterLessNumber() + primeCoefficient.getPerimeterLessNumber());
			} else {
				prams.setManagePrice(primeCoefficient.getPeripheralLaser() * 100 * prams.getPerimeter()
						+ primeCoefficient.getEmbroideryLaserNumber());
			}
			// 拉布时间
			if( primeCoefficient.getQuilt()!=0){
				prams.setRabbTime(NumUtils.mul(NumUtils.div(tailorSize, primeCoefficient.getQuilt(), 3),
						primeCoefficient.getRabbTime()));
			}
			double singleLaserTimeOne = NumUtils.mul(prams.getPerimeter(), primeCoefficient.getTime(),
					(double)prams.getStallPoint(), primeCoefficient.getPauseTime());
			// 单片激光需要用净时
			if (prams.getSingleDouble() == 2) {
				prams.setSingleLaserTime(NumUtils.sum(NumUtils.div(singleLaserTimeOne, 2, 3), prams.getRabbTime()));
			} else {
				prams.setSingleLaserTime(NumUtils.sum(singleLaserTimeOne, prams.getRabbTime()));
			}
			// 单片激光放快手时间
			prams.setSingleLaserHandTime(
					NumUtils.mul(prams.getSingleLaserTime(), 1.08, primeCoefficient.getQuickWorker()));
			// 工价（含快手)
			prams.setLabourCost(NumUtils.mul(prams.getSingleLaserHandTime(), primeCoefficient.getPerSecondMachinist()));
			// 设备折旧和房水电费
			prams.setEquipmentPrice(NumUtils.mul(
					NumUtils.sum(primeCoefficient.getDepreciation(), primeCoefficient.getLaserTubePriceSecond(),
							primeCoefficient.getMaintenanceChargeSecond(), primeCoefficient.getPerSecondPrice()),
					prams.getSingleLaserHandTime()));
			// 管理人员费用
			prams.setAdministrativeAtaff(
					NumUtils.mul(primeCoefficient.getPerSecondManage(), prams.getSingleLaserHandTime()));
			// 普通激光切割该裁片费用
			prams.setStallPrice(NumUtils.mul(
					NumUtils.sum(prams.getLabourCost(), prams.getEquipmentPrice(), prams.getAdministrativeAtaff()),
					primeCoefficient.getEquipmentProfit()));
			break;
		case 73:// 手工电烫
			type = "perm";
			primeCoefficient = primeCoefficientDao.findByType(type);
			prams.setTailorType(type);
			tailor.setTailorType(type);
			// 拉布秒数（含快手)
			prams.setRabbTime(NumUtils.mul(NumUtils.div(NumUtils.div(primeCoefficient.getPermOne(), 1.5 , 3) , tailorSize,3) 
					, primeCoefficient.getQuickWorker()));
			break;
		case 74:// 设备电烫(暂无)
			
			

			break;
		case 75:// 冲床
			type = "puncher";
			prams.setTailorType(type);
			tailor.setTailorType(type);
			// 得到理论(市场反馈）含管理价值
			prams.setManagePrice(materielService.getBaseThreeOne(prams.getTailorTypeId(), prams.getTailorSizeId()));
			break;
		case 76:// 电推
			type = "electricPush";
			prams.setTailorType(type);
			tailor.setTailorType(type);
			// 得到理论(市场反馈）含管理价值
			prams.setManagePrice(materielService.getBaseThreeOne(prams.getTailorTypeId(),  prams.getTailorSizeId()));
			break;
		case 77:// 手工剪刀 
			type = "manual";
			prams.setTailorType(type);
			tailor.setTailorType(type);
			// 得到理论(市场反馈）含管理价值
			prams.setManagePrice(materielService.getBaseThreeOne(prams.getTailorTypeId(), prams.getTailorSizeId()));
			break;
		case 78:// 绣花领取

			break;
		default:
			break;
		}
		// 将裁剪方式和裁剪页面数据进行关联，实现一对一的同步更新
		ordinaryLaserDao.save(prams);
		tailor.setOrdinaryLaserId(prams.getId());
		dao.save(tailor);
		prams.setTailorId(tailor.getId());   
		return ordinaryLaserDao.save(prams);
	}

	@Override
	public PageResult<Tailor> findPages(Tailor param, PageParameter page) {
		Page<Tailor> pages = dao.findAll((root, query, cb) -> {
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
			if (!StringUtils.isEmpty(param.getTailorName())) {
				predicate.add(cb.like(root.get("tailorName").as(String.class), "%" + param.getTailorName() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Tailor> result = new PageResult<Tailor>(pages, page);
		return result;
	}

	@Override
	public List<Tailor> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}

}
