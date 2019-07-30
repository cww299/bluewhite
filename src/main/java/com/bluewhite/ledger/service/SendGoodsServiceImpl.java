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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.SendGoodsDao;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.SendGoods;

@Service
public class SendGoodsServiceImpl extends BaseServiceImpl<SendGoods, Long> implements SendGoodsService {
	
	@Autowired
	private SendGoodsDao dao;

	@Override
	public PageResult<SendGoods> findPages(SendGoods param, PageParameter page) {
		Page<SendGoods> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customrId").as(Long.class), param.getCustomerId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("packingChilds").get("productId").as(Long.class), param.getProductId()));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("packingChilds").get("bacthNumber").as(String.class),
						"%" + param.getBacthNumber() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<SendGoods> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public SendGoods addSendGoods(SendGoods sendGoods) {
		return dao.save(sendGoods);
	}

	@Override
	public List<SendGoods> findLists(SendGoods param) {
		List<SendGoods> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customrId").as(Long.class), param.getCustomerId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate
						.add(cb.equal(root.get("packingChilds").get("productId").as(Long.class), param.getProductId()));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("packingChilds").get("bacthNumber").as(String.class),
						"%" + param.getBacthNumber() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}
}
