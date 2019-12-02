package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.MaterialOutStorageDao;
import com.bluewhite.ledger.dao.MaterialPutStorageDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.entity.OrderProcurement;

@Service
public class MaterialPutStorageServiceImpl extends BaseServiceImpl<MaterialPutStorage, Long>
		implements MaterialPutStorageService {

	@Autowired
	private MaterialPutStorageDao dao;
	@Autowired
	private OrderProcurementDao orderProcurementDao;
	@Autowired
	private MaterialOutStorageDao materialOutStorageDao;

	@Override
	public void saveMaterialPutStorage(MaterialPutStorage materialPutStorage) {
		if (materialPutStorage.getId() != null) {
			MaterialPutStorage ot = dao.findOne(materialPutStorage.getId());
			update(materialPutStorage, ot, "");
		} else {
			materialPutStorage.setInspection(0);
			dao.save(materialPutStorage);
		}
	}

	@Override
	public PageResult<MaterialPutStorage> findPages(PageParameter page, MaterialPutStorage param) {
		Page<MaterialPutStorage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按物料编号
			if (!StringUtils.isEmpty(param.getMaterielName())) {
				predicate.add(cb.like(root.get("number").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getMaterielName()) + "%"));
			}
			// 按物料名称
			if (!StringUtils.isEmpty(param.getMaterielNumber())) {
				predicate.add(cb.like(root.get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getMaterielNumber()) + "%"));
			}
			// 按库区
			if (param.getStorageAreaId() != null) {
				predicate.add(cb.equal(root.get("storageArea").get("id").as(Long.class), param.getStorageAreaId()));
			}
			// 按库位
			if (param.getStorageLocationId() != null) {
				predicate.add(
						cb.equal(root.get("storageLocation").get("id").as(Long.class), param.getStorageLocationId()));
			}
			// 按验货
			if (param.getInspection() != null) {
				predicate.add(cb.equal(root.get("inspection").as(Long.class), param.getInspection()));
			}
			// 按发货贴包日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("arrivalTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按采购状态
			if (param.getInStatus() != null) {
				predicate.add(cb.equal(root.get("inStatus").as(Integer.class), param.getInStatus()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		pages.getContent().stream().forEach(m -> {
			List<MaterialOutStorage> materialOutStorageList = materialOutStorageDao
					.findByMaterialPutStorageId(m.getId());
			double arrNumber = materialOutStorageList.stream().mapToDouble(MaterialOutStorage::getArrivalNumber).sum();
			m.setSurplusNumber(NumUtils.sub(m.getArrivalNumber(), arrNumber));
		});
		PageResult<MaterialPutStorage> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deleteMaterialPutStorage(String ids) {
		return delete(ids);
	}

	@Override
	public void inspectionMaterialPutStorage(MaterialPutStorage materialPutStorage) {
		MaterialPutStorage ot = findOne(materialPutStorage.getId());
		if (ot.getInspection() == 1) {
			throw new ServiceException("当前采购入库单已检验，请勿重复检验");
		}
		materialPutStorage.setInspection(1);
		BeanCopyUtils.copyNotEmpty(materialPutStorage, ot, "");
		// 采购单
		OrderProcurement orderProcurement = orderProcurementDao.findOne(ot.getOrderProcurementId());
		// 当实际克重和约定克重不相符
		if (orderProcurement.getConventionSquareGram() != null && ot.getSquareGram() != null
				&& orderProcurement.getConventionSquareGram() > ot.getSquareGram()) {
			ot.setGramPrice(NumUtils.mul(
					NumUtils.div(NumUtils.sub(orderProcurement.getConventionSquareGram(), ot.getSquareGram()),
							orderProcurement.getConventionSquareGram(), 3),
					orderProcurement.getPrice(), ot.getArrivalNumber()));
		}
		save(ot);
	}

	@Override
	public double getArrivalNumber(Long id) {
		// 获取到货数量
		List<MaterialPutStorage> materialPutStorageList = dao.findByOrderProcurementId(id);
		// 计算退货总数
		List<MaterialOutStorage> list = new ArrayList<>();
		materialPutStorageList.stream().forEach(m -> {
			MaterialOutStorage materialOutStorage = materialOutStorageDao.findOne(m.getId());
			if (materialOutStorage != null) {
				list.add(materialOutStorage);
			}
		});
		double returnNumber = list.stream().mapToDouble(MaterialOutStorage::getArrivalNumber).sum();
		double arrivalNumber = materialPutStorageList.stream().mapToDouble(MaterialPutStorage::getArrivalNumber).sum();
		return arrivalNumber - returnNumber;
	}

	@Override
	public List<MaterialPutStorage> findByOrderProcurementId(Long id) {
		return dao.findByOrderProcurementId(id);
	}

	@Override
	public List<MaterialPutStorage> findByOrderProcurementIdAndInspection(Long id, int i) {
		return dao.findByOrderProcurementIdAndInspection(id, i);
	}

}
