package com.bluewhite.product.primecost.pack.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.primecost.cutparts.dao.CutPartsDao;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;
import com.bluewhite.product.primecost.embroidery.dao.EmbroideryDao;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;
import com.bluewhite.product.primecost.machinist.dao.MachinistDao;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.materials.dao.ProductMaterialsDao;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
import com.bluewhite.product.primecost.needlework.dao.NeedleworkDao;
import com.bluewhite.product.primecost.needlework.entity.Needlework;
import com.bluewhite.product.primecost.pack.dao.PackDao;
import com.bluewhite.product.primecost.pack.entity.Pack;
import com.bluewhite.product.primecost.tailor.dao.TailorDao;
import com.bluewhite.product.primecost.tailor.entity.Tailor;
import com.bluewhite.product.primecostbasedata.dao.PrimeCoefficientDao;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

@Service
public class PackServiceImpl extends BaseServiceImpl<Pack, Long> implements PackService {

	@Autowired
	private PackDao dao;
	@Autowired
	private PrimeCoefficientDao primeCoefficientDao;

	@Autowired
	private CutPartsDao cutPartsDao;

	@Autowired
	private ProductMaterialsDao productMaterialsDao;

	@Autowired
	private TailorDao tailorDao;

	@Autowired
	private MachinistDao machinistDao;

	@Autowired
	private EmbroideryDao embroideryDao;

	@Autowired
	private NeedleworkDao needleworkDao;

	@Override
	public Pack savePack(Pack pack) {
		// 自动将类型为null的属性赋值为0
		NumUtils.setzro(pack);

		PrimeCoefficient primeCoefficient = primeCoefficientDao.findByType("pack");
		// 单只产品用时/秒
		pack.setOneTime(NumUtils.division(pack.getTime() / pack.getPackNumber()));
		// 批量用时/秒(含快手）
		pack.setBatchTime(pack.getNumber() * pack.getOneTime() * primeCoefficient.getQuickWorker());
		// 单只时间（含快手）
		pack.setOnePackTime(pack.getOneTime() * 1.08 * primeCoefficient.getQuickWorker());
		// 工价/单只（含快手)
		pack.setPackPrice(pack.getKindWork() == 0 ? pack.getBatchTime() * primeCoefficient.getPerSecondMachinist()
				: pack.getBatchTime() * primeCoefficient.getPerSecondMachinistTwo());
		// 设备折旧和房水电费
		pack.setEquipmentPrice(pack.getBatchTime() * primeCoefficient.getPerSecondPrice());
		// 管理人员费用
		pack.setAdministrativeAtaff(pack.getBatchTime() * primeCoefficient.getPerSecondManage());
		// 入成本批量价格
		pack.setAllCostPrice(pack.getPackPrice() + pack.getEquipmentPrice() + pack.getAdministrativeAtaff());
		// 上道压价（整个成品）
		// cc裁片
		List<CutParts> cutPartsList = cutPartsDao.findByProductId(pack.getProductId());
		// 当批各单片价格
		double batchMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchMaterialPrice()!=null).mapToDouble(CutParts::getBatchMaterialPrice).sum();
		// 当批被复合物用料
		double complexBatchMaterial = cutPartsList.stream().filter(CutParts->CutParts.getComplexBatchMaterial()!=null).mapToDouble(CutParts::getComplexBatchMaterial).sum();
		// 当批被复合各单片价格
		double batchComplexMaterialPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexMaterialPrice()!=null).mapToDouble(CutParts::getBatchComplexMaterialPrice)
				.sum();
		// 当批复合物加加工费价格
		double batchComplexAddPrice = cutPartsList.stream().filter(CutParts->CutParts.getBatchComplexAddPrice()!=null).mapToDouble(CutParts::getBatchComplexAddPrice).sum();
		// dd物料
		List<ProductMaterials> ProductMaterialsList = productMaterialsDao.findByProductId(pack.getProductId());
		double materialsPrice = ProductMaterialsList.stream().filter(ProductMaterials->ProductMaterials.getBatchMaterialPrice()!=null).mapToDouble(ProductMaterials::getBatchMaterialPrice)
				.sum();
		// 裁剪
		List<Tailor> tailorList = tailorDao.findByProductId(pack.getProductId());
		double allCostPriceTailor = tailorList.stream().filter(Tailor->Tailor.getAllCostPrice()!=null).mapToDouble(Tailor::getAllCostPrice).sum();
		// 机工
		List<Machinist> machinistList = machinistDao.findByProductId(pack.getProductId());
		double allCostPriceMachinist = machinistList.stream().filter(Machinist->Machinist.getAllCostPrice()!=null).mapToDouble(Machinist::getAllCostPrice).sum();
		// 绣花
		List<Embroidery> embroideryList = embroideryDao.findByProductId(pack.getProductId());
		double allCostPriceEmbroidery = embroideryList.stream().filter(Embroidery->Embroidery.getAllCostPrice()!=null).mapToDouble(Embroidery::getAllCostPrice).sum();
		// 针工
		List<Needlework> needleworkList = needleworkDao.findByProductId(pack.getProductId());
		double allCostPriceNeedlework = needleworkList.stream().filter(Needlework->Needlework.getAllCostPrice()!=null).mapToDouble(Needlework::getAllCostPrice).sum();
		pack.setPriceDown(batchMaterialPrice + complexBatchMaterial + batchComplexMaterialPrice + batchComplexAddPrice
				+ materialsPrice + allCostPriceTailor + allCostPriceMachinist + allCostPriceEmbroidery
				+ allCostPriceNeedlework);

		return dao.save(pack);
	}

	@Override
	public PageResult<Pack> findPages(Pack param, PageParameter page) {
		Page<Pack> pages = dao.findAll((root, query, cb) -> {
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
		PageResult<Pack> result = new PageResult<Pack>(pages, page);
		return result;
	}

	@Override
	public void deletePack(Long id) {
		dao.delete(id);
	}

}
