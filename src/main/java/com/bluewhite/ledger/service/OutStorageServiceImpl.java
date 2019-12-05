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
import com.bluewhite.ledger.dao.OutStorageDao;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.entity.OutStorage;
import com.bluewhite.ledger.entity.PutStorage;

@Service
public class OutStorageServiceImpl extends BaseServiceImpl<OutStorage, Long> implements OutStorageService {
	
	@Autowired
	private OutStorageDao dao;
	@Autowired
	private PutStorageDao putStorageDao;

	@Override
	public void saveOutStorage(OutStorage outStorage) {
		if(outStorage.getId()!=null){  
			OutStorage ot = dao.findOne(outStorage.getId());
			update(outStorage, ot, "");
		}else{
			if (!StringUtils.isEmpty(outStorage.getPutOutStorageIds())) {
				String[] idStrings = outStorage.getPutOutStorageIds().split(",");
				if (idStrings.length > 0) {
					for (String ids : idStrings) {
						Long id = Long.parseLong(ids);
						PutStorage putStorage = putStorageDao.findOne(id);
						outStorage.getPutOutStorage().add(putStorage);
					}
				}
			}
			outStorage.setSerialNumber(Constants.CPCK+StringUtil.getDate()+SalesUtils.get0LeftString((int) (dao.count()+1), 8));
			save(outStorage);
		};
	}

	@Override
	public int deleteOutStorage(String ids) {
		int i = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				OutStorage outStorage = dao.findOne(id);
				delete(id);
				i++;
			}
		}
		return i;
	}

	@Override
	public PageResult<OutStorage> findPages(PageParameter page, OutStorage param) {
		Page<OutStorage> pages = dao.findAll((root, query, cb) -> {
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
			// 按到货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("arrivalTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按采购状态
			if (param.getOutStatus() != null) {
				predicate.add(cb.equal(root.get("outStatus").as(Integer.class), param.getOutStatus()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<OutStorage> result = new PageResult<>(pages, page);
		return result;
	}


}
