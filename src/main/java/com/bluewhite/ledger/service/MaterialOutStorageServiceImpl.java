package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.MaterialOutStorageDao;
import com.bluewhite.ledger.entity.MaterialOutStorage;

@Service
public class MaterialOutStorageServiceImpl extends BaseServiceImpl<MaterialOutStorage, Long> implements MaterialOutStorageService  {
	
	@Autowired
	private MaterialOutStorageDao dao;
	
	
	@Override
	public void saveMaterialOutStorage(MaterialOutStorage materialOutStorage) {
		save(materialOutStorage);
	}

	@Override
	public PageResult<MaterialOutStorage> findPages(PageParameter page, MaterialOutStorage materialOutStorage) {
		Page<MaterialOutStorage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			
			
			
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
