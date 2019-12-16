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
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.RoleUtil;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.MaterialOutStorageDao;
import com.bluewhite.ledger.dao.MaterialPutOutStorageDao;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import com.bluewhite.ledger.entity.MaterialPutOutStorage;
import com.bluewhite.ledger.entity.MaterialPutStorage;

@Service
public class MaterialOutStorageServiceImpl extends BaseServiceImpl<MaterialOutStorage, Long>
		implements MaterialOutStorageService {

	@Autowired
	private MaterialOutStorageDao dao;
	@Autowired
	private MaterialPutStorageService materialPutStorageService;
	@Autowired
	private MaterialPutOutStorageDao materialPutOutStorageDao;

	@Override
	public void saveMaterialOutStorage(MaterialOutStorage materialOutStorage) {
		
		materialOutStorage.setSerialNumber(Constants.WLCK + StringUtil.getDate() + SalesUtils.get0LeftString((int) (dao.count() + 1), 8));
		save(materialOutStorage);
	}

	@Override
	public PageResult<MaterialOutStorage> findPages(PageParameter page, MaterialOutStorage param) {
		Page<MaterialOutStorage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按物料编号
			if (!StringUtils.isEmpty(param.getMaterielName())) {
				predicate.add(cb.like(root.get("materiel").get("number").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getMaterielName()) + "%"));
			}
			// 按物料名称
			if (!StringUtils.isEmpty(param.getMaterielNumber())) {
				predicate.add(cb.like(root.get("materiel").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getMaterielNumber()) + "%"));
			}
			// 按到货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("arrivalTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按出库状态
			if (param.getOutStatus() != null) {
				predicate.add(cb.equal(root.get("outStatus").as(Integer.class), param.getOutStatus()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<MaterialOutStorage> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deleteMaterialOutStorage(String ids) {
		int i = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				List<MaterialPutOutStorage> list = materialPutOutStorageDao.findByMaterialOutStorageId(id);
				if(list.size()>0){
					materialPutOutStorageDao.delete(list);
				}
				delete(id);
				i++;
			}
		}
		return i;
	}

	@Override
	public void outboundMaterialRequisition(MaterialOutStorage materialOutStorage) {
		if(materialOutStorage.getMaterialRequisitionId()==null){
			throw new ServiceException("领料单不能为空");
		}
		CurrentUser cu = SessionManager.getUserSession();
		Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		materialOutStorage.setUserStorageId(cu.getId());
		materialOutStorage.setOutStatus(1);
		materialOutStorage.setSerialNumber(Constants.WLCK + StringUtil.getDate() + SalesUtils.get0LeftString((int) (dao.count() + 1), 8));
		save(materialOutStorage);
		List<MaterialPutStorage> listMaterialPutStorage = materialPutStorageService.detailsInventory(warehouseTypeDeliveryId,materialOutStorage.getMaterielId());
		for(MaterialPutStorage m : listMaterialPutStorage){
			MaterialPutOutStorage materialPutOutStorage = new MaterialPutOutStorage();
			materialPutOutStorage.setMaterialPutStorageId(m.getId());
			materialPutOutStorage.setMaterialOutStorageId(materialOutStorage.getId());
			// 当发货数量小于等于剩余数量时,当前入库单可以满足出库数量，终止循环
			if(materialOutStorage.getArrivalNumber() <= m.getSurplusNumber()){ 
				materialPutOutStorage.setNumber(materialOutStorage.getArrivalNumber());
				materialPutOutStorageDao.save(materialPutOutStorage);
				break;
			}
			// 当发货数量大于剩余数量时,当前入库单数量无法满足出库数量，库存相减后继续循环出库
			if (materialOutStorage.getArrivalNumber() > m.getSurplusNumber()) {
				materialPutOutStorage.setNumber(m.getSurplusNumber());
				materialOutStorage.setArrivalNumber(NumUtils.sub(materialOutStorage.getArrivalNumber(),m.getSurplusNumber()));
				materialPutOutStorageDao.save(materialPutOutStorage);
			}
		}
	}
}
