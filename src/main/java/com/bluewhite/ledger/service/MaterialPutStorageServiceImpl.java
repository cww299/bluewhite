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
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.ledger.dao.MaterialPutStorageDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.entity.OrderProcurement;

@Service
public class MaterialPutStorageServiceImpl extends BaseServiceImpl<MaterialPutStorage, Long> implements MaterialPutStorageService  {
	
	@Autowired
	private MaterialPutStorageDao dao;
	@Autowired
	private OrderProcurementDao orderProcurementDao;

	@Override
	public void saveMaterialPutStorage(MaterialPutStorage materialPutStorage) {
		dao.save(materialPutStorage);
	}



	@Override
	public PageResult<MaterialPutStorage> findPages(PageParameter page, MaterialPutStorage param) {
		Page<MaterialPutStorage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			
			
			
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<MaterialPutStorage> result = new PageResult<>(pages, page);
		return result;
	}


	@Override
	public int deletematerialPutStorage(String ids) {
		return delete(ids);
	}



	@Override
	public void inspectionMaterialPutStorage(MaterialPutStorage materialPutStorage) {
		MaterialPutStorage ot = findOne(materialPutStorage.getId()); 
		//采购单
		OrderProcurement orderProcurement = orderProcurementDao.findOne(materialPutStorage.getOrderProcurementId());
		//当实际克重和约定克重不相符
		if (orderProcurement.getConventionSquareGram() != null && materialPutStorage.getSquareGram() != null
				&& orderProcurement.getConventionSquareGram() > materialPutStorage.getSquareGram()) {
			ot.setGramPrice(NumUtils.mul(
					NumUtils.div(
							NumUtils.sub(orderProcurement.getConventionSquareGram(),
									materialPutStorage.getSquareGram()),
							orderProcurement.getConventionSquareGram(), 3),
					orderProcurement.getPrice(), ot.getArrivalNumber()));
			
			
		}
		
		
	}


	@Override
	public double getArrivalNumber(Long id) {
		// 获取到货数量
		List<MaterialPutStorage>  materialPutStorageList =  dao.findByOrderProcurementId(id);
		double arrivalNumber = materialPutStorageList.stream().mapToDouble(MaterialPutStorage::getArrivalNumber).sum();
		return arrivalNumber;
	}


}
