package com.bluewhite.product.primecost.embroidery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.bluewhite.product.primecost.embroidery.dao.EmbroideryDao;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

@Service
public class EmbroideryServiceImpl extends BaseServiceImpl<Embroidery, Long> implements EmbroideryService {

	@Autowired
	private EmbroideryDao dao;

	@Autowired
	private CutPartsDao cutPartsDao;

	@Autowired
	private TailorDao tailorDao;

	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;

	@Override
	@Transactional
	public Embroidery saveEmbroidery(Embroidery embroidery) {
		//自动将类型为null的属性赋值为0
		NumUtils.setzro(embroidery);
		PrimeCoefficient primeCoefficient = primeCoefficientDao.findByType("embroidery");
		// 单片机走时间
		embroidery.setSinglechipApplique(NumUtils.round(
				NumUtils.division((primeCoefficient.getEmbroideryTwo() / 1000) * embroidery.getNeedleNumber() / embroidery.getFew()), 1));
		// 铺布或裁片秀贴布和上绷子时间
		embroidery.setTime(NumUtils.division(embroidery.getCloth() == null ? primeCoefficient.getEmbroideryFour()
				: primeCoefficient.getEmbroideryThree() / embroidery.getEmbroiderySlice()));
		// 贴片时间,通过基础数据三将数值返回
		double pasterTime = 0;
		if (!StringUtils.isEmpty(embroidery.getAppliqueTime())) {
			String[] arr = embroidery.getAppliqueTime().split(",");
			if (arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					pasterTime += Double.parseDouble(arr[i]);
				}
			}
		}
		embroidery.setPasterTime(pasterTime);
		// 站机含快手时间
		embroidery.setVentilatorStationTime(
				NumUtils.round((embroidery.getSinglechipApplique() + embroidery.getTime() + embroidery.getPasterTime())
						* 1.08 * primeCoefficient.getQuickWorker(), 2));
		// 剪线时间含快手
		embroidery.setCuttingLineTime(embroidery.getEmbroideryColorNumber() * primeCoefficient.getEmbroiderySix() * 1.08
				* primeCoefficient.getQuickWorker());
		// 绣花线用量/米
		embroidery.setLineDosage(
				NumUtils.round((embroidery.getNeedleNumber() * primeCoefficient.getEmbroideryEleven()), 2));
		// 垫纸用量
		embroidery.setPackingPaperDosage(embroidery.getAffirmSize() + embroidery.getAffirmSizeTwo());
		// 薄膜用量/平方米
		embroidery.setMembraneDosage(
				(embroidery.getAffirmSize() + embroidery.getAffirmSizeTwo()) * embroidery.getMembrane());
		// 绣花物料线价格
		embroidery.setEmbroideryPrice(embroidery.getLineDosage() * primeCoefficient.getEmbroideryNine());
		// 绣花物料垫纸价格（通过前台计算得到）（用）
		embroidery.setPackingPaperPrice(embroidery.getPaperPrice() * embroidery.getPackingPaperDosage());
		// 绣花物料薄膜价格
		embroidery.setMembranePrice(embroidery.getMembraneDosage() * primeCoefficient.getEmbroideryOne());
		// 电脑推算站机工价（含快手)
		embroidery.setReckoningPrice(embroidery.getVentilatorStationTime() * primeCoefficient.getPerSecondMachinist());
		// 剪线工价
		embroidery.setCutLinePrice(embroidery.getCuttingLineTime() * primeCoefficient.getPerSecondMachinistTwo());
		// 设备折旧和房水电费
		embroidery.setEquipmentPrice((primeCoefficient.getDepreciation() + primeCoefficient.getLaserTubePriceSecond()
				+ primeCoefficient.getMaintenanceChargeSecond() + primeCoefficient.getPerSecondPrice())
				* (embroidery.getVentilatorStationTime() + embroidery.getCuttingLineTime()));
		// 管理人员费用
		embroidery.setAdministrativeAtaff(primeCoefficient.getPerSecondManage()
				* (embroidery.getVentilatorStationTime() + embroidery.getCuttingLineTime()));
		// 电脑推算绣花价格
		embroidery.setReckoningEmbroideryPrice(
				NumUtils.round(embroidery.getReckoningPrice() + embroidery.getCutLinePrice()
						+ embroidery.getEquipmentPrice() + embroidery.getAdministrativeAtaff(), 3));
		// 目前市场价格
		embroidery.setReckoningSewingPrice(
				(NumUtils.division((NumUtils.round((double) embroidery.getNeedleNumber() / 1000, 0) * 1000) < embroidery.getNeedleNumber()
						? (NumUtils.round((double) embroidery.getNeedleNumber() / 1000, 0) * 1000 + 1000)
						: (NumUtils.round((double) embroidery.getNeedleNumber() / 1000, 0) * 1000)) / 1000
						* primeCoefficient.getEmbroideryTwelve()
						+ (embroidery.getApplique() * primeCoefficient.getEmbroideryThirteen())
						+ (embroidery.getEmbroideryColorNumber() - 1) * primeCoefficient.getEmbroideryFourteen()));
		// 入成本价格
		embroidery.setAllCostPrice(embroidery.getNumber() * NumUtils.setzro(embroidery.getCostPrice()));
		// 各单道比全套工价
		List<Embroidery> embroideryList = dao.findByProductId(embroidery.getProductId());
		embroideryList.add(embroidery);
		double scaleMaterial = NumUtils.division(embroidery.getAllCostPrice()
				/ (embroideryList.stream().filter(Machinist -> Machinist.getAllCostPrice() != null)
						.mapToDouble(Embroidery::getAllCostPrice).sum()));
		embroidery.setScaleMaterial(scaleMaterial);
		// 物料和上道压（裁剪）价(裁片绣上道压价 = 裁剪页面的不含绣花环节的为机工压价 ， 整布绣上道压价 = cc裁片页面 当批各单片价格)
		List<Tailor> tailorList = tailorDao.findByProductId(embroidery.getProductId());
		tailorList.stream().filter(Tailor -> Tailor.getId() == embroidery.getTailorId()).collect(Collectors.toList());
		double caipian = tailorList.get(0).getNoeMbroiderPriceDown();
		List<CutParts> cutPartsList = cutPartsDao.findByProductId(embroidery.getProductId());
		cutPartsList.stream().filter(CutParts -> CutParts.getTailorId() == tailorList.get(0).getId())
				.collect(Collectors.toList());
		double zhengbu = cutPartsList.get(0).getBatchMaterialPrice();
		if(!StringUtils.isEmpty(embroidery.getEmbroideryMode())){
			embroidery.setPriceDown(embroidery.getEmbroideryMode().equals("整布绣") ? zhengbu : caipian);
		}
		embroidery.setPriceDownRemark(embroidery.getPriceDown() + embroidery.getAllCostPrice());

		// 同时更新裁剪页面
		// 含绣花环节的为机工压价
		tailorList.get(0).setEmbroiderPriceDown(embroidery.getPriceDownRemark());
		// 为机工准备的压价
		double MachinistPriceDown = tailorList.get(0).getNoeMbroiderPriceDown() >= NumUtils
				.setzro(tailorList.get(0).getEmbroiderPriceDown()) ? tailorList.get(0).getNoeMbroiderPriceDown()
						: NumUtils.setzro(tailorList.get(0).getEmbroiderPriceDown());
		tailorList.get(0).setMachinistPriceDown(MachinistPriceDown);
		tailorDao.save(tailorList);
		
		dao.save(embroidery);
		Tailor tr = tailorDao.findOne(embroidery.getTailorId());
		tr.setEmbroideryId(embroidery.getId());
		tailorDao.save(tr);
		return embroidery;
	}

	@Override
	public PageResult<Embroidery> findPages(Embroidery param, PageParameter page) {
		Page<Embroidery> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Embroidery> result = new PageResult<Embroidery>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void deleteEmbroidery(Long id) {
		Embroidery embroidery = dao.findOne(id);
		Tailor tailor = tailorDao.findOne(embroidery.getTailorId());
		// 同时更新去除裁剪页面
		// 含绣花环节的为机工压价
		if(tailor!=null){
			tailor.setEmbroiderPriceDown(null);
			// 为机工准备的压价
			double MachinistPriceDown = tailor.getNoeMbroiderPriceDown() >= NumUtils.setzro(tailor.getEmbroiderPriceDown())
					? tailor.getNoeMbroiderPriceDown() : NumUtils.setzro(tailor.getEmbroiderPriceDown());
					tailor.setMachinistPriceDown(MachinistPriceDown);
					tailorDao.save(tailor);
		}
		dao.delete(embroidery);
	}

	@Override
	public List<Embroidery> findByProductId(Long productId) {
		return dao.findByProductId(productId);
	}
}
