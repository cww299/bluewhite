package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.RoleUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.consumption.dao.ConsumptionDao;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.ledger.dao.MaterialRequisitionDao;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.OrderOutSourceDao;
import com.bluewhite.ledger.dao.ProcessPriceDao;
import com.bluewhite.ledger.dao.RefundBillsDao;
import com.bluewhite.ledger.entity.MaterialRequisition;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.OutStorage;
import com.bluewhite.ledger.entity.ProcessPrice;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.ledger.entity.RefundBills;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;

@Service
public class OrderOutSourceServiceImpl extends BaseServiceImpl<OrderOutSource, Long> implements OrderOutSourceService {

	@Autowired
	private OrderOutSourceDao dao;
	@Autowired
	private BaseDataDao baseDataDao;
	@Autowired
	private MaterialRequisitionDao materialRequisitionDao;
	@Autowired
	private RefundBillsDao refundBillsDao;
	@Autowired
	private ProcessPriceDao processPriceDao;
	@Autowired
	private ConsumptionDao consumptionDao;
	@Autowired
	private BaseDataService baseDataService;
	@Autowired
	private PutStorageService putStorageService;
	@Autowired
	private OutStorageService outStorageService;

	@Override
	@Transactional
	public void saveOrderOutSource(OrderOutSource orderOutSource) {
		// 	领料单
		MaterialRequisition materialRequisition = materialRequisitionDao
				.findOne(orderOutSource.getMaterialRequisitionId());
		// 	根据领料单查找加工单
		List<OrderOutSource> orderOutSourceList = dao
				.findByMaterialRequisitionId(orderOutSource.getMaterialRequisitionId());
		// 	将工序任务变成set存入，存在退货情况是，要去除退货数量
		if (!StringUtils.isEmpty(orderOutSource.getOutsourceTaskIds())) {
			String[] idStrings = orderOutSource.getOutsourceTaskIds().split(",");
			if (idStrings.length > 0) {
				for (String ids : idStrings) {
					Long id = Long.parseLong(ids);
					BaseData baseData = baseDataDao.findOne(id);
					//	根据工序数量新建加工单，一个工序对应一个加工单
					OrderOutSource newOrderOutSource = new OrderOutSource();
					BeanCopyUtils.copyNotEmpty(orderOutSource, newOrderOutSource, "");
					newOrderOutSource.setAudit(0);
					newOrderOutSource.setChargeOff(0);
					String outSourceNumber = (newOrderOutSource.getOutsource() == 0 ? Constants.JGD : Constants.WFJGD)
							+ StringUtil.getDate() + StringUtil.get0LeftString((int) (dao.count() + 1), 8);
					newOrderOutSource.setOutSourceNumber(outSourceNumber);
					newOrderOutSource.getOutsourceTask().add(baseData);
					save(newOrderOutSource);
					// 	新增加工单工序的原始价格数据
					ProcessPrice processPrice = new ProcessPrice();
					processPrice.setOrderOutSourceId(newOrderOutSource.getId());
					processPrice.setProcessTaskId(id);
					processPrice.setCustomerId(newOrderOutSource.getCustomerId());
					processPriceDao.save(processPrice);
					// 	对加工单数量进行限制判断，加工单数量和工序挂钩，每个工序最大数量为领料单数量，无法超出
					// 	工序可以由不同的加工单加工，但是不能超出领料单数量
					// 	该工序已经加工总数
					int sumNumber = orderOutSourceList.stream().filter(o -> {
						Set<BaseData> baseDataSet = o.getOutsourceTask().stream()
								.filter(BaseData -> baseData.getId().equals(id)).collect(Collectors.toSet());
						if (baseDataSet.size() > 0) {
							return true;
						}
						return false;
					}).mapToInt(OrderOutSource::getProcessNumber).sum();
					// 	查找该加工单该工序的通过审核的退货单
					List<Integer> returnNumberList = refundBillsDao.getReturnNumber(newOrderOutSource.getMaterialRequisitionId(), id);
					// 	退货总数
					Integer returnNumber = returnNumberList.stream().reduce(Integer::sum).orElse(0);
					// 	实际剩余数量
					int actualNumber =  materialRequisition.getProcessNumber() - sumNumber - returnNumber;
					if (actualNumber < newOrderOutSource.getProcessNumber()) {
						throw new ServiceException(baseData.getName() + "的任务工序数量不足，无法生成加工单 ");
					}
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getProcessNumber(Long id) {
		List<Map<String, Object>> listMap = new ArrayList<>();
		// 	领料单
		MaterialRequisition materialRequisition = materialRequisitionDao.findOne(id);
		// 	根据领料单查找加工单
		List<OrderOutSource> orderOutSourceList = dao.findByMaterialRequisitionId(id);
		// 	查询出所有的工序类型
		List<BaseData> baseDatas = baseDataService.getBaseDataTreeByType("taskProcessType");
		baseDatas.forEach(b -> {
			Map<String, Object> map = new HashMap<>();
			int sumNumber = orderOutSourceList.stream().filter(o -> {
				Set<BaseData> baseDataSet = o.getOutsourceTask().stream()
						.filter(BaseData -> BaseData.getId().equals(b.getId())).collect(Collectors.toSet());
				if (baseDataSet.size() > 0) {
					return true;
				}
				return false;
			}).mapToInt(OrderOutSource::getProcessNumber).sum();
			// 	查找该加工单该工序的通过审核的退货单
			List<Integer> returnNumberList = refundBillsDao.getReturnNumber(id, b.getId());
			// 	退货总数
			Integer returnNumber = returnNumberList.stream().reduce(Integer::sum).orElse(0);
			int actualNumber = materialRequisition.getProcessNumber() - sumNumber - returnNumber;
			map.put("id", b.getId());
			map.put("name", b.getName());
			map.put("number", actualNumber);
			listMap.add(map);
		});
		return listMap;
	}

	@Override
	public PageResult<OrderOutSource> findPages(OrderOutSource param, PageParameter page) {
		// 根据仓管登陆用户权限，获取不同的仓库库存
		CurrentUser cu = SessionManager.getUserSession();
		Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		Page<OrderOutSource> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//	按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 	按生产计划单id
			if (param.getOrderId() != null) {
				predicate.add(
						cb.equal(root.get("materialRequisition").get("orderId").as(Long.class), param.getOrderId()));
			}
			// 	按加工点id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 	按跟单人id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 	按生产工序过滤
			if (param.getOutsourceTaskId() != null) {
				Join<OrderOutSource, BaseData> join = root.join(root.getModel().getSet("outsourceTask", BaseData.class),
						JoinType.LEFT);
				predicate.add(cb.equal(join.get("id").as(Long.class), param.getOutsourceTaskId()));
			}
			// 	是否外发
			if (param.getOutsource() != null) {
				predicate.add(cb.equal(root.get("outsource").as(Integer.class), param.getOutsource()));
			}
			// 	按跟单人
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(
						cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUserName() + "%"));
			}
			// 	按客户
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 	按加工点
			if (!StringUtils.isEmpty(param.getOutSourceNumber())) {
				predicate.add(cb.like(root.get("outSourceNumber").as(String.class), "%" + param.getOutSourceNumber() + "%"));
			}
			// 	是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 	按产品名称
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("order").get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 	按下单日期
			if (param.getOpenOrderTime() != null) {
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("openOrderTime").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			query.distinct(true);
			return null;
		}, page);
		PageResult<OrderOutSource> result = new PageResult<>(pages, page);
		if(warehouseTypeDeliveryId!=null){
			result.getRows().forEach(o->{
				//	成品remainingInventory
				//	针工单入库后剩余入库数量
				//	皮壳
				//	1.入库,机工单，展示入库后剩余数量
				//	remainingInventory
				List<PutStorage> putStorageList = putStorageService.findByWarehouseTypeIdAndOrderOutSourceId(warehouseTypeDeliveryId, o.getId());
				o.setRemainingInventory(o.getProcessNumber());
				if(putStorageList.size()>0){
					int number = putStorageList.stream().mapToInt(PutStorage::getArrivalNumber).sum();
					o.setRemainingInventory(o.getProcessNumber()-number);
				}
				//	2.出库,针工单，需要发货总数，已发货数量，未发货数量，库存状态
				//	根据加工单获取已发货数量
				List<OutStorage> outStorageList = outStorageService.findByOrderOutSourceId(o.getId());
				o.setCotSurplusNumber(o.getProcessNumber());
				if(outStorageList.size()>0){
					int sendNumber = outStorageList.stream().filter(OutStorage->OutStorage.getOrderOutSourceId().equals(o.getId())).mapToInt(OutStorage::getArrivalNumber).sum();
					//	皮壳剩余数量
					o.setCotSurplusNumber(o.getProcessNumber()-sendNumber);
				}
				List<Map<String, Object>> mapsList = outStorageService.getOrderOutSourcePutStorageDetails(o.getId());
				if(mapsList.size()>0){
					int status = 0;
					int number = mapsList.stream().mapToInt(m->Integer.valueOf(m.get("number").toString())).sum();
					if(o.getCotSurplusNumber()>number){
						status = 1;
					}
					if(number<=0){
						status = 2;
					}
					o.setCotStatus(status);
				}else{
					o.setCotStatus(2);
				}
			});
		}
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
					if (orderOutSource.getAudit() == 1) {
						throw new ServiceException("已审核，无法删除");
					}
					orderOutSource.setOutsourceTask(null);
					List<ProcessPrice> processPrices = processPriceDao.findByOrderOutSourceId(id);
					processPriceDao.delete(processPrices);
					delete(orderOutSource);
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
		if (ot.getAudit() == 1) {
			throw new ServiceException("已审核，无法修改");
		}
		BeanCopyUtils.copyNotEmpty(orderOutSource, ot, "");
		ot.getOutsourceTask().clear();
		save(ot);
		// 	领料单
		MaterialRequisition materialRequisition = materialRequisitionDao
				.findOne(orderOutSource.getMaterialRequisitionId());
		// 	根据领料单查找加工单
		List<OrderOutSource> orderOutSourceList = dao
				.findByMaterialRequisitionId(orderOutSource.getMaterialRequisitionId());
		// 	将工序任务变成set存入
		if (!StringUtils.isEmpty(ot.getOutsourceTaskIds())) {
			String[] idStrings = ot.getOutsourceTaskIds().split(",");
			if (idStrings.length > 0) {
				for (String ids : idStrings) {
					Long id = Long.parseLong(ids);
					BaseData baseData = baseDataDao.findOne(id);
					// 	对加工单数量进行限制判断，加工单数量和工序挂钩，每个工序最大数量为订单数量，无法超出
					// 	工序可以由不同的加工单加工，但是不能超出订单数量
					int sumNumber = orderOutSourceList.stream().filter(o -> {
						Set<BaseData> baseDataSet = o.getOutsourceTask().stream()
								.filter(BaseData -> baseData.getId().equals(id)).collect(Collectors.toSet());
						if (baseDataSet.size() > 0) {
							return true;
						}
						return false;
					}).mapToInt(OrderOutSource::getProcessNumber).sum();
					// 	查找改加工单该工序的退货单
					List<Integer> returnNumberList = refundBillsDao.getReturnNumber(ot.getMaterialRequisitionId(), id);
					// 	退货总数
					Integer returnNumber = returnNumberList.stream().reduce(Integer::sum).orElse(0);
					// 	实际数量=(总加工数-退货数)
					int actualNumber = sumNumber - returnNumber;
					if (actualNumber > materialRequisition.getProcessNumber()) {
						throw new ServiceException(baseData.getName() + "的任务工序数量不足，无法修改加工单 ");
					}
					ot.getOutsourceTask().add(baseData);
				}
			}
		}
		save(ot);
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
					if (orderOutSource.getAudit() == 1) {
						throw new ServiceException("第" + (i + 1) + "条单据已审核，请勿重复审核");
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
			update(orderOutSource, ot, "");
		}
	}

	@Override
	public void saveOutSoureBills(OrderOutSource orderOutSource) {
		// 	生成账单
		Consumption consumption = new Consumption();
		OrderOutSource ot = dao.findOne(orderOutSource.getId());
		if (ot.getChargeOff() == 1) {
			throw new ServiceException("外发加工对账单已生成，请勿多次申请");
		}
		consumption.setOrderOutSourceId(orderOutSource.getId());
		// 	外发加工对账单
		consumption.setType(10);
		// 	加工点
		consumption.setCustomerId(ot.getCustomerId());
		consumption.setSettleAccountsMode(2);
		consumption.setFlag(0);
		// 	申请金额
		consumption.setMoney(orderOutSource.getMoney());
		// 	申请日期
		consumption.setExpenseDate(orderOutSource.getExpenseDate());
		consumptionDao.save(consumption);
		ot.setChargeOff(1);
		save(ot);
	}

	@Override
	public List<Map<String, Object>> mixOutSoureRefund(Long id) {
		List<Map<String, Object>> mixList = new ArrayList<>();
		// 	加工单
		OrderOutSource orderOutSource = findOne(id);
		// 	退货单
		List<RefundBills> refundBills = refundBillsDao.findByOrderOutSourceId(id);
		// 	加工单工序
		Set<BaseData> outsourceTask = orderOutSource.getOutsourceTask();
		// 	加工单工序对应价格
		List<ProcessPrice> processPriceList = processPriceDao.findByOrderOutSourceId(id);

		outsourceTask.stream().forEach(outB -> {
			Map<String, Object> map = new HashMap<>();
			List<ProcessPrice> pList = processPriceList.stream()
					.filter(ProcessPrice -> ProcessPrice.getProcessTaskId().equals(outB.getId()))
					.collect(Collectors.toList());
			// 	工序id
			map.put("id", pList.get(0).getId());
			// 	工序名称
			map.put("name", outB.getName());
			// 	工序价格
			map.put("price", pList.get(0).getPrice());
			int returnNumber = 0;
			// 	循环退货单，将退货单的工序取出
			for (RefundBills r : refundBills) {
				// 	当加工单工序等于退货单工序时，更新加工单工序的任务数量
				Set<BaseData> setBaseData = r.getOutsourceTask().stream()
						.filter(BaseData -> BaseData.getId().equals(outB.getId())).collect(Collectors.toSet());
				returnNumber += (setBaseData.size() > 0 ? r.getReturnNumber() : 0);
			}
			// 	工序数量
			map.put("number", orderOutSource.getProcessNumber() - returnNumber);
			mixList.add(map);
		});
		return mixList;
	}

	@Override
	public void updateProcessPrice(ProcessPrice processPrice) {
		ProcessPrice ot = processPriceDao.findOne(processPrice.getId());
		if (ot.getOrderOutSource().getChargeOff() == 1) {
			throw new ServiceException("已出账，无法修改价格");
		}
		ot.setPrice(processPrice.getPrice());
		processPriceDao.save(ot);
	}

	@Override
	public PageResult<ProcessPrice> processNumberPage(ProcessPrice param, PageParameter page) {
		Page<ProcessPrice> pages = processPriceDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<ProcessPrice> result = new PageResult<>(pages, page);
		return result;
	}

}
