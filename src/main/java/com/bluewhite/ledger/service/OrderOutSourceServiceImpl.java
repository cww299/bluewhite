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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.RoleUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.OrderOutSourceDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;

@Service
public class OrderOutSourceServiceImpl extends BaseServiceImpl<OrderOutSource, Long> implements OrderOutSourceService {

	@Autowired
	private OrderOutSourceDao dao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private InventoryDao inventoryDao;

	@Override
	@Transactional
	public void saveOrderOutSource(OrderOutSource orderOutSource) {
		if (orderOutSource.getOrderId() != null) {
			Order order = orderDao.findOne(orderOutSource.getOrderId());
			if(order.getPrepareEnough()==0){
				throw new ServiceException("当前下单合同备料不足，无法进行外发");
			}
			List<OrderOutSource> orderOutSourceList = dao.findByOrderIdAndFlag(orderOutSource.getOrderId(), 1);
			if (orderOutSourceList.size() > 0) {
				double sumNumber = orderOutSourceList.stream().mapToDouble(OrderOutSource::getProcessNumber).sum();
				if (NumUtils.sum(sumNumber, orderOutSource.getProcessNumber()) > order.getNumber()) {
					throw new ServiceException("外发总数量不能大于下单合同数量，请核实后填写");
				}
			}
			orderOutSource.setRemark(order.getRemark());
			orderOutSource.setFlag(0);
			orderOutSource.setAudit(0);
			save(orderOutSource);
		} else {
			throw new ServiceException("生产下单合同不能为空");
		}
	}

	@Override
	public PageResult<OrderOutSource> findPages(OrderOutSource param, PageParameter page) {
		CurrentUser cu = SessionManager.getUserSession();
		Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		if(warehouseTypeDeliveryId!=null){
			param.setWarehouseTypeId(warehouseTypeDeliveryId);
		}
		Page<OrderOutSource> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按加工点id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按跟单人id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按预计入库仓库
			if (param.getWarehouseType() != null) {
				predicate.add(cb.equal(root.get("warehouseType").as(Long.class), param.getWarehouseType()));
			}
			// 按入库仓库
			if (param.getInWarehouseType() != null) {
				predicate.add(cb.equal(root.get("inWarehouseType").as(Long.class), param.getInWarehouseType()));
			}
			// 按跟单人
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUserName() + "%"));
			}
			// 按客户
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),"%" + param.getCustomerName() + "%"));
			}
			// 按加工点
			if (!StringUtils.isEmpty(param.getOutSourceNumber())) {
				predicate.add(cb.like(root.get("outSourceNumber").as(String.class),
						"%" + param.getOutSourceNumber() + "%"));
			}
			// 是否作废
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 是否作废
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按外发工序过滤
			if (param.getProcess() != null) {
				predicate.add(cb.equal(root.get("process").as(String.class), param.getProcess()));
			}
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("order").get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 是否到货
			if (param.getArrival() != null) {
				predicate.add(cb.equal(root.get("arrival").as(Integer.class), param.getArrival()));
			}
			// 按外发日期
			if (param.getOutGoingTime() != null) {
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("outGoingTime").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			// 按下单日期
			if (param.getOpenOrderTime() != null) {
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("openOrderTime").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			// 按到货日期
			if (param.getArrivalTime() != null) {
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("arrivalTime").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<OrderOutSource> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteOrderOutSource(String ids) {
		return delete(ids);
	}

	@Override
	@Transactional
	public void updateOrderOutSource(OrderOutSource orderOutSource) {
		if (orderOutSource.getId() != null) {
			OrderOutSource ot = findOne(orderOutSource.getId());
			if(orderOutSource.getAudit()==1){
				throw new ServiceException("已审核，无法修改");
			}
			update(orderOutSource, ot, "");
		}
		if (orderOutSource.getOrderId() != null) {
			Order order = orderDao.findOne(orderOutSource.getOrderId());
			List<OrderOutSource> orderOutSourceList = dao.findByOrderIdAndFlag(orderOutSource.getOrderId(), 1);
			double sumNumber = orderOutSourceList.stream().mapToDouble(OrderOutSource::getProcessNumber).sum();
			if (sumNumber > order.getNumber()) {
				throw new ServiceException("外发总数量不能大于下单合同数量，请核实后填写");
			}
		} else {
			throw new ServiceException("生产下单合同不能为空");
		}

	}

	@Override
	@Transactional
	public int invalidOrderOutSource(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderOutSource orderOutSource = findOne(id);
					orderOutSource.setFlag(1);
					save(orderOutSource);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int auditOrderOutSource(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderOutSource orderOutSource = findOne(id);
					if(orderOutSource.getFlag()==1){
						throw new ServiceException("第"+(i+1)+"条单据已作废，无法审核");
					}
					if(orderOutSource.getAudit()==1){
						throw new ServiceException("第"+(i+1)+"条单据已审核，请勿重复审核");
					}
					if(orderOutSource.getOutGoingTime()==null){
						throw new ServiceException("第"+(i+1)+"条单据无外发时间，无法审核");
					}
					if (orderOutSource.getWarehouseTypeId() == null) {
						throw new ServiceException("第"+(i+1)+"条单据未填写预计入库仓库，无法审核，请先确认入库仓库");
					}
					orderOutSource.setAudit(1);
					save(orderOutSource);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public void updateInventoryOrderOutSource(OrderOutSource orderOutSource) {
		if (orderOutSource.getId() != null) {
			OrderOutSource ot = findOne(orderOutSource.getId());
			if(ot.getArrival()==1){
				throw new ServiceException("已入库，无法修改");
			}
			update(orderOutSource, ot, "");
		}
	}

	@Override
	public int confirmOrderOutSource(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderOutSource orderOutSource = findOne(id);
					if(orderOutSource.getArrival()==1){
						throw new ServiceException("第"+(i+1)+"条单据已入库，请勿重复入库");
					}
					if (orderOutSource.getInWarehouseTypeId() == null) {
						throw new ServiceException("第"+(i+1)+"条单据未填写入库仓库，无法入库，请先确认入库仓库");
					}
					// 库存表
					Inventory inventory = inventoryDao.findByProductIdAndWarehouseTypeId(
							orderOutSource.getOrder().getProductId(), orderOutSource.getInWarehouseTypeId());
					if (inventory == null) {
						inventory = new Inventory();
					}
					inventory.setNumber(inventory.getNumber() + orderOutSource.getArrivalNumber());
					inventoryDao.save(inventory);
					count++;
				}
			}
		}
		return count;
	}
}
