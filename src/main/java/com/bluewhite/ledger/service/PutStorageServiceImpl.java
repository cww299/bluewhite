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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.RoleUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.PutOutStorageDao;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.entity.PutOutStorage;
import com.bluewhite.ledger.entity.PutStorage;

@Service
public class PutStorageServiceImpl extends BaseServiceImpl<PutStorage, Long> implements PutStorageService {

	@Autowired
	private PutStorageDao dao;
	@Autowired
	private PutOutStorageDao putOutStorageDao;

	@Override
	public void savePutStorage(PutStorage putStorage) {
		// 	根据仓管登陆用户权限，获取不同的仓库库存
		CurrentUser cu = SessionManager.getUserSession();
		Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		putStorage.setSerialNumber((putStorage.getFlag() == 1 ? Constants.CPRK : Constants.PKRK)  + StringUtil.getDate() + StringUtil.get0LeftString((int) (dao.count() + 1), 8));
		putStorage.setWarehouseTypeId(warehouseTypeDeliveryId);
		putStorage.setPublicStock(0);
		dao.save(putStorage);
	}

	@Override
	public PageResult<PutStorage> findPages(PageParameter page, PutStorage param) {
		// 	根据仓管登陆用户权限，获取不同的仓库库存
		CurrentUser cu = SessionManager.getUserSession();
		Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		param.setWarehouseTypeId(warehouseTypeDeliveryId);
		Page<PutStorage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//	 按产品名字
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 	按产品编号
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(cb.like(root.get("product").get("number").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductNumber()) + "%"));
			}
			// 	按库区
			if (param.getStorageAreaId() != null) {
				predicate.add(cb.equal(root.get("storageArea").get("id").as(Long.class), param.getStorageAreaId()));
			}
			// 	按库位
			if (param.getStorageLocationId() != null) {
				predicate.add(cb.equal(root.get("storageLocation").get("id").as(Long.class), param.getStorageLocationId()));
			}
			// 	按仓库种类
			if (param.getWarehouseTypeId() != null) {
				predicate.add(cb.equal(root.get("warehouseTypeId").as(Long.class), param.getWarehouseTypeId()));
			}
			// 	按到货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("arrivalTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 	按采购状态
			if (param.getInStatus() != null) {
				predicate.add(cb.equal(root.get("inStatus").as(Integer.class), param.getInStatus()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		pages.getContent().stream().forEach(m -> {
			// 	入库单实际出库数量
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
				try {
					delete(id);
				} catch (Exception e) {
					throw new ServiceException("该入库单已有出库记录，无法撤销");
				}
				count++;
			}
		}
		return count;
	}

	@Override
	public List<PutStorage> detailsInventory(Object warehouseTypeId, Long productId) {
	    List<PutStorage> putStorageList = null;
	    if(warehouseTypeId!=null) {
	        putStorageList = dao.findByWarehouseTypeIdAndProductId(Long.valueOf(warehouseTypeId.toString()), productId);
	    } else {
	        putStorageList = dao.findByProductId(productId);
	    }
	    if(putStorageList.size()>0) {
	        putStorageList.forEach(m -> {
	            // 	入库单实际出库数量
	            List<PutOutStorage> outPutStorageList = putOutStorageDao.findByPutStorageId(m.getId());
	            int arrNumber = outPutStorageList.stream().mapToInt(PutOutStorage::getNumber).sum();
	            // 	入库单剩余数量
	            m.setSurplusNumber(m.getArrivalNumber() - arrNumber);
	        });
	        // 	排除掉已经全部出库的入库单
	        putStorageList = putStorageList.stream().filter(PutStorage -> PutStorage.getSurplusNumber() > 0)
	            .sorted(Comparator.comparing(PutStorage::getArrivalTime)).collect(Collectors.toList());
	    }
		return putStorageList;
	}

	@Override
	public List<PutStorage> findByWarehouseTypeIdAndOrderOutSourceId(Long warehouseTypeId, Long orderOutSourceId) {
		return dao.findByWarehouseTypeIdAndOrderOutSourceId(warehouseTypeId, orderOutSourceId);
	}

}
