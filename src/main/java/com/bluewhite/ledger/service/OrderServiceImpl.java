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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.entity.Order;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Autowired
	private OrderDao dao;

	@Override
	public PageResult<Order> findPages(Order param, PageParameter page) {
		Page<Order> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 是否电子商务部下单合同
			if (param.getInternal() != null) {
				predicate.add(cb.equal(root.get("internal").as(Long.class), param.getInternal()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class), "%" + param.getCustomerName() + "%"));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.equal(root.get("product").get("String").as(Long.class), "%" + param.getProductName() + "%"));
			}
			// 按产品编号过滤
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(cb.equal(root.get("productNumber").as(String.class), "%" + param.getProductNumber() + "%"));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("orderDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Order> result = new PageResult<Order>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteOrder(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Order order = dao.findOne(id);
					
					
					dao.delete(order); 
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public List<Order> addOrder(Order order) {
		List<Order> orderList = new ArrayList<>();
		// 新增子单
		if (!StringUtils.isEmpty(order.getOrderChild())) { 
			JSONArray jsonArray = JSON.parseArray(order.getOrderChild());
			for (int i = 0; i < jsonArray.size(); i++) {
				Order orderNew = new Order();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				orderNew.setOrderDate(order.getOrderDate()!=null ? order.getOrderDate() : new Date());
				Order oldOrder = dao.findByBacthNumber(jsonObject.getString("bacthNumber"));
				if(oldOrder!=null){
					throw new ServiceException("系统已有"+jsonObject.getString("bacthNumber")+"批次号下单合同，请不要重复添加");
				}
				orderNew.setBacthNumber(jsonObject.getString("bacthNumber"));
				orderNew.setProductId(jsonObject.getLong("productId"));
				orderNew.setCustomerId(order.getCustomerId());
				//判定是否属于电子商务部的订单合同
				if(orderNew.getCustomerId().equals(1)){
					orderNew.setInternal(1);
				}
				orderNew.setNumber(jsonObject.getInteger("number"));
				orderNew.setPrice(jsonObject.getDouble("price"));
				orderNew.setRemark(jsonObject.getString("remark"));
				orderNew.setOrderDate(jsonObject.getDate("orderDate")!= null ? jsonObject.getDate("orderDate"): new Date());
				orderList.add(orderNew);
			}
		}
		return dao.save(orderList);
	}

	@Override
	public List<Order> findList(Order param) {
		List<Order> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customrId").as(Long.class), param.getCustomerId()));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("packingDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

}
