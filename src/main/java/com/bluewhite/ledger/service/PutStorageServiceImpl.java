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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OutStorageDao;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.entity.OutStorage;
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

	@Override
	public void savePutStorage(PutStorage putStorage) {
		if(putStorage.getId()!=null){
			PutStorage ot = dao.findOne(putStorage.getId());
			update(putStorage, ot, "");
		}else{
			inventoryService.putInStorage(putStorage.getProductId(), putStorage.getInWarehouseTypeId());
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
			List<OutStorage> outStorageList = outStorageDao.findByPutStorageId(m.getId());
			double arrNumber = outStorageList.stream().mapToDouble(OutStorage::getArrivalNumber).sum();
			m.setSurplusNumber(NumUtils.sub(m.getArrivalNumber(), arrNumber));
		});
		PageResult<PutStorage> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deletePutStorage(String ids) {
		int i = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				PutStorage putStorage = dao.findOne(id);
				List<OutStorage> outStorage = outStorageDao.findByPutStorageId(id);
				if(outStorage.size()>0){
					throw new ServiceException("第"+(i+1)+"条入库单已有出库记录，无法删除，请先删除出库单");
				}
				delete(id);
				i++;
			}
		}
		return i;
	}

}
