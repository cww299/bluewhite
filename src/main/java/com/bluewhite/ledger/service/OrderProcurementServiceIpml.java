package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderProcurement;

@Service
public class OrderProcurementServiceIpml extends BaseServiceImpl<OrderProcurement, Long> implements OrderProcurementService {
	
	@Autowired
	private OrderProcurementDao dao;
	@Autowired
	private OrderMaterialDao orderMaterialDao;
	
	
	@Override
	public PageResult<OrderProcurement> findPages(OrderProcurement param, PageParameter page) {
		Page<OrderProcurement> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())){
				predicate.add(cb.like(root.get("order").get("product").get("name").as(String.class),"%"+StringUtil.specialStrKeyword(param.getProductName())+"%") );
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<OrderProcurement> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int confirmOrderProcurement(String ids) {
		if(!StringUtils.isEmpty(ids)){
			String[] idStrings = ids.split(",");
			for(String id : idStrings ){
				Long orderMaterialId = Long.valueOf(id);
				OrderMaterial orderMaterial = orderMaterialDao.findOne(orderMaterialId);
				//生成采购订单
				OrderProcurement orderProcurement = new OrderProcurement();
				orderProcurement.setOrderMaterialId(orderMaterialId);
				orderProcurement.setMaterielId(orderMaterial.getMaterielId());
				
				
				
				
			}
		}
		return 0;
	}




}
