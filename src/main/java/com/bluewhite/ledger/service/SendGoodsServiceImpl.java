package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.PackingChildDao;
import com.bluewhite.ledger.dao.SendGoodsDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.SendGoods;

@Service
public class SendGoodsServiceImpl extends BaseServiceImpl<SendGoods, Long> implements SendGoodsService {
	
	@Autowired
	private SendGoodsDao dao;
	@Autowired
	private PackingChildDao packingChildDao;
	@Autowired
	private OrderDao orderdao;

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
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("product").get("name").as(String.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class),"%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getSendDate())) {
				predicate.add(cb.equal(root.get("sendDate").as(Date.class), param.getSendDate()));
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
		Order order = orderdao.findOne(sendGoods.getOrderId());
		sendGoods.setBacthNumber(order.getBacthNumber());
		sendGoods.setProductId(order.getProductId());
		if(sendGoods.getId()!=null){
			List<PackingChild> sendGoodsList = packingChildDao.findBySendGoodsId(sendGoods.getId());
			if(sendGoodsList.size()>0){
				throw new ServiceException("该待发货单已有贴包发货单，无法修改，请先核对贴包发货单");
			}
			SendGoods ot = dao.findOne(sendGoods.getId());
			BeanCopyUtils.copyNotEmpty(sendGoods, ot, "");
			dao.save(ot);
		}else{
			sendGoods.setSendNumber(0);
			dao.save(sendGoods);
		}
		return sendGoods;
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
				predicate.add(cb.equal(root.get("product").get("productId").as(Long.class), param.getProductId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("product").get("name").as(String.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class),"%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getSendDate())) {
				predicate.add(cb.equal(root.get("sendDate").as(Date.class), param.getSendDate()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	@Transactional
	public int deleteSendGoods(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					List<PackingChild> sendGoodsList = packingChildDao.findBySendGoodsId(id);
					if(sendGoodsList.size()>0){
						throw new ServiceException("该待发货单已有贴包发货单，无法删除，请先核对贴包发货单");
					}
					SendGoods sendGoods = dao.findOne(id);
					sendGoods.setCustomerId(null);
					dao.delete(sendGoods); 
					count++;
				}
			}
		}
		return count;
	}
}
