package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.RoleUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.MaterialRequisitionDao;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.OrderOutSourceDao;
import com.bluewhite.ledger.entity.MaterialRequisition;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.product.product.entity.Product;

@Service
public class OrderOutSourceServiceImpl extends BaseServiceImpl<OrderOutSource, Long> implements OrderOutSourceService {

	@Autowired
	private OrderOutSourceDao dao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private BaseDataDao baseDataDao;
	@Autowired
	private MaterialRequisitionDao materialRequisitionDao;

	@Override
	@Transactional
	public void saveOrderOutSource(OrderOutSource orderOutSource) {
		if (orderOutSource.getOrderId() != null) {
			Order order = orderDao.findOne(orderOutSource.getOrderId());
			if(order.getPrepareEnough()==0){
				throw new ServiceException("当前下单合同备料不足，无法进行外发");
			}
			List<OrderOutSource> orderOutSourceList = dao.findByOrderIdAndFlag(orderOutSource.getOrderId(), 0);
			//将工序任务变成set存入
			if(!StringUtils.isEmpty(orderOutSource.getOutsourceTaskIds())){
				String[] idStrings = orderOutSource.getOutsourceTaskIds().split(",");
				if(idStrings.length>0){
					for(String ids : idStrings ){
						Long id = Long.parseLong(ids);
						BaseData baseData = baseDataDao.findOne(id);
						//对加工单数量进行限制判断，加工单数量和工序挂钩，每个工序最大数量为订单数量，无法超出
						//工序可以由不同的加工单加工，但是不能超出订单数量
						double sum = orderOutSourceList.stream().filter(o->{
							Set<BaseData> baseDataSet = o.getOutsourceTask().stream().filter(BaseData->baseData.getId().equals(id)).collect(Collectors.toSet());
							if(baseDataSet.size()>0){
								return true;
							}
							return false;
						}).mapToInt(OrderOutSource::getProcessNumber).sum();
						if((sum+orderOutSource.getProcessNumber()) > order.getNumber()){
							throw new ServiceException(baseData.getName()+"的任务工序数量不足，无法生成加工单 ");
						}
						orderOutSource.getOutsourceTask().add(baseData);
					}
				}
			}
			orderOutSource.setFlag(0);
			orderOutSource.setAudit(0);
			orderOutSource.setArrival(0);
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
			// 是否外发
			if (param.getOutsource()!=null) {
				predicate.add(cb.equal(root.get("outsource").as(Integer.class), param.getOutsource()));
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
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
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
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderOutSource orderOutSource = findOne(id);
					if(orderOutSource.getAudit()==1){
						throw new ServiceException("已审核，无法删除");
					}
					delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public void updateOrderOutSource(OrderOutSource orderOutSource) {
		OrderOutSource ot = findOne(orderOutSource.getId());
		if(ot.getAudit()==1){
			throw new ServiceException("已审核，无法修改");
		}
		//将工序任务变成set存入
		if(!StringUtils.isEmpty(orderOutSource.getOutsourceTaskIds())){
			String[] idStrings = orderOutSource.getOutsourceTaskIds().split(",");
			if(idStrings.length>0){
				for(String ids : idStrings ){
					Long id = Long.parseLong(ids);
					BaseData baseData = baseDataDao.findOne(id);
					orderOutSource.getOutsourceTask().add(baseData);
				}
				
			}
		}
		update(orderOutSource, ot, "");
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
					Product product = orderOutSource.getOrder().getProduct();
					if(product!=null){
						List<Inventory> inventoryList = product.getInventorys().stream().filter(Inventory->Inventory.getWarehouseTypeId().equals(orderOutSource.getInWarehouseTypeId())).collect(Collectors.toList());
						//当前仓库只存在一种
						Inventory inventory = null;
						if(inventoryList.size()>0){
							inventory = inventoryList.get(0);
						}else{
							inventory = new Inventory();
							inventory.setProductId(orderOutSource.getOrder().getProductId());
							inventory.setWarehouseTypeId(orderOutSource.getInWarehouseTypeId());
						}
						inventory.setNumber(NumUtils.setzro(inventory.getNumber()) + orderOutSource.getArrivalNumber());
						inventory.getOrderOutSource().add(orderOutSource);
						inventoryDao.save(inventory);
						orderOutSource.setArrival(1);
						save(orderOutSource);
					}
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int judgeOrderOutSource(Long orderid) {
		List<MaterialRequisition> materialRequisitionList = materialRequisitionDao.findByOrderId(orderid);
		if(materialRequisitionList.size()>0){
			long size = materialRequisitionList.stream().filter( MaterialRequisition->MaterialRequisition.getAudit()==0).count();
			if(size>0){
				return -1;
			}
		}else{
			return 0;
		}
		return 1;
	}
}
