package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OutStorageDao;
import com.bluewhite.ledger.dao.PutOutStorageDao;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.entity.PutOutStorage;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.onlineretailers.inventory.service.InventoryService;

@Service
public class PutStorageServiceImpl extends BaseServiceImpl<PutStorage, Long> implements PutStorageService {

	@Autowired
	private PutStorageDao dao;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private OutStorageDao outStorageDao;
	@Autowired
	private PutOutStorageDao putOutStorageDao;
	

	@Override
	public void savePutStorage(PutStorage putStorage) {
		if (putStorage.getId() != null) {
			PutStorage ot = dao.findOne(putStorage.getId());
			update(putStorage, ot, "");
		} else {
			putStorage.setSerialNumber(
					Constants.CPRK + StringUtil.getDate() + SalesUtils.get0LeftString((int) (dao.count() + 1), 8));
			putStorage.setPublicStock(0);
			dao.save(putStorage);
		}
	}

	@Override
	public PageResult<PutStorage> findPages(PageParameter page, PutStorage param) {
		Page<PutStorage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按产品名字
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按产品编号
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(cb.like(root.get("product").get("number").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductNumber()) + "%"));
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
			// 按到货日期
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
			//入库单实际出库数量
			List<PutOutStorage> outPutStorageList = putOutStorageDao.findByPutStorageId(m.getId());
			int arrNumber = outPutStorageList.stream().mapToInt(PutOutStorage::getNumber).sum();
			m.setSurplusNumber(m.getArrivalNumber() - arrNumber);
		});
		PageResult<PutStorage> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deletePutStorage(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				delete(id);
				count++;
			}
		}
		return count;
	}

	@Override
	public List<PutStorage> detailsInventory(Long warehouseTypeId, Long productId) {
		List<PutStorage> putStorageList = dao.findByProductId(productId);
		if(warehouseTypeId!=null){
			putStorageList= dao.findByWarehouseTypeIdAndProductId(warehouseTypeId, productId);
		}
		putStorageList.forEach(m->{
			//入库单实际出库数量
			List<PutOutStorage> outPutStorageList = putOutStorageDao.findByPutStorageId(m.getId());
			int arrNumber = outPutStorageList.stream().mapToInt(PutOutStorage::getNumber).sum();
			//入库单剩余数量
			m.setSurplusNumber(m.getArrivalNumber() - arrNumber);
		});
		// 排除掉已经全部出库的入库单
		putStorageList = putStorageList.stream().filter(PutStorage->PutStorage.getSurplusNumber()>0).sorted(Comparator.comparing(PutStorage::getArrivalTime)).collect(Collectors.toList());
		return putStorageList;
	}

}
