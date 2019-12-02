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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.MaterialOutStorageDao;
import com.bluewhite.ledger.entity.MaterialOutStorage;

@Service
public class MaterialOutStorageServiceImpl extends BaseServiceImpl<MaterialOutStorage, Long>
		implements MaterialOutStorageService {

	@Autowired
	private MaterialOutStorageDao dao;

	@Override
	public void saveMaterialOutStorage(MaterialOutStorage materialOutStorage) {
		if(materialOutStorage.getId()!=null){  
			MaterialOutStorage ot = dao.findOne(materialOutStorage.getId());
			update(materialOutStorage, ot, "");
		}else{
			materialOutStorage.setSerialNumber(Constants.WLCK+StringUtil.getDate()+SalesUtils.get0LeftString((int) dao.count(), 8));
			save(materialOutStorage);
		}
	}

	@Override
	public PageResult<MaterialOutStorage> findPages(PageParameter page, MaterialOutStorage param) {
		Page<MaterialOutStorage> pages = dao.findAll((root, query, cb) -> {
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
		return delete(ids);
	}

}
