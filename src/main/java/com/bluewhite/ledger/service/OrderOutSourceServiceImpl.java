package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.consumption.dao.ConsumptionDao;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.ledger.dao.MaterialRequisitionDao;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.OrderOutSourceDao;
import com.bluewhite.ledger.dao.ProcessPriceDao;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.dao.RefundBillsDao;
import com.bluewhite.ledger.entity.MaterialRequisition;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.ProcessPrice;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.ledger.entity.RefundBills;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.onlineretailers.inventory.service.InventoryService;
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
	@Autowired
	private RefundBillsDao refundBillsDao;
	@Autowired
	private ProcessPriceDao processPriceDao;
	@Autowired
	private ConsumptionDao consumptionDao;

	@Override
	@Transactional
	public void saveOrderOutSource(OrderOutSource orderOutSource) {
		if (orderOutSource.getOrderId() != null) {
			Order order = orderDao.findOne(orderOutSource.getOrderId());
			if (order.getPrepareEnough() == 0) {
				throw new ServiceException("当前下单合同备料不足，无法进行外发");
			}
			List<OrderOutSource> orderOutSourceList = dao.findByOrderId(orderOutSource.getOrderId());
			// 将工序任务变成set存入，存在退货情况是，要去除退货数量
			if (!StringUtils.isEmpty(orderOutSource.getOutsourceTaskIds())) {
				String[] idStrings = orderOutSource.getOutsourceTaskIds().split(",");
				if (idStrings.length > 0) {
					for (String ids : idStrings) {
						Long id = Long.parseLong(ids);
						BaseData baseData = baseDataDao.findOne(id);
						//新增加工单工序的原始价格数据
						ProcessPrice processPrice  = new ProcessPrice();
						processPrice.setOrderOutSourceId(orderOutSource.getId());
						processPrice.setProcessTaskId(id);
						processPrice.setCustomerId(orderOutSource.getCustomerId());
						processPriceDao.save(processPrice);
						
						// 对加工单数量进行限制判断，加工单数量和工序挂钩，每个工序最大数量为订单数量，无法超出
						// 工序可以由不同的加工单加工，但是不能超出订单数量
						// 改工序已经加工总数
						int sumNumber = orderOutSourceList.stream().filter(o -> {
							Set<BaseData> baseDataSet = o.getOutsourceTask().stream()
									.filter(BaseData -> baseData.getId().equals(id)).collect(Collectors.toSet());
							if (baseDataSet.size() > 0) {
								return true;
							}
							return false;
						}).mapToInt(OrderOutSource::getProcessNumber).sum();
						// 查找改加工单该工序的退货单
						List<Integer> returnNumberList = refundBillsDao.getReturnNumber(orderOutSource.getOrderId(),
								id);
						// 退货总数
						Integer returnNumber = returnNumberList.stream().reduce(Integer::sum).orElse(0);
						// 实际数量=(总加工数-退货数)
						int actualNumber = sumNumber - returnNumber;
						if (actualNumber > order.getNumber()) {
							throw new ServiceException(baseData.getName() + "的任务工序数量不足，无法生成加工单 ");
						}
						orderOutSource.getOutsourceTask().add(baseData);
					}
				}
			}
			orderOutSource.setAudit(0);
			save(orderOutSource);
		} else {
			throw new ServiceException("生产下单合同不能为空");
		}
	}

	@Override
	public PageResult<OrderOutSource> findPages(OrderOutSource param, PageParameter page) {
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
			// 是否外发
			if (param.getOutsource() != null) {
				predicate.add(cb.equal(root.get("outsource").as(Integer.class), param.getOutsource()));
			}
			// 按跟单人
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(
						cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUserName() + "%"));
			}
			// 按客户
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 按加工点
			if (!StringUtils.isEmpty(param.getOutSourceNumber())) {
				predicate.add(
						cb.like(root.get("outSourceNumber").as(String.class), "%" + param.getOutSourceNumber() + "%"));
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
			// 按下单日期
			if (param.getOpenOrderTime() != null) {
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("openOrderTime").as(Date.class), param.getOrderTimeBegin(),
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
					if (orderOutSource.getAudit() == 1) {
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
		if (ot.getAudit() == 1) {
			throw new ServiceException("已审核，无法修改");
		}
		BeanCopyUtils.copyNotEmpty(orderOutSource, ot, "");
		Order order = orderDao.findOne(ot.getOrderId());
		List<OrderOutSource> orderOutSourceList = dao.findByOrderId(ot.getOrderId());
		// 将工序任务变成set存入
		if (!StringUtils.isEmpty(ot.getOutsourceTaskIds())) {
			String[] idStrings = ot.getOutsourceTaskIds().split(",");
			if (idStrings.length > 0) {
				for (String ids : idStrings) {
					Long id = Long.parseLong(ids);
					BaseData baseData = baseDataDao.findOne(id);
					// 对加工单数量进行限制判断，加工单数量和工序挂钩，每个工序最大数量为订单数量，无法超出
					// 工序可以由不同的加工单加工，但是不能超出订单数量
					int sumNumber = orderOutSourceList.stream().filter(o -> {
						Set<BaseData> baseDataSet = o.getOutsourceTask().stream()
								.filter(BaseData -> baseData.getId().equals(id)).collect(Collectors.toSet());
						if (baseDataSet.size() > 0) {
							return true;
						}
						return false;
					}).mapToInt(OrderOutSource::getProcessNumber).sum();
					// 查找改加工单该工序的退货单
					List<Integer> returnNumberList = refundBillsDao.getReturnNumber(ot.getOrderId(), id);
					// 退货总数
					Integer returnNumber = returnNumberList.stream().reduce(Integer::sum).orElse(0);
					// 实际数量=(总加工数-退货数)
					int actualNumber = sumNumber - returnNumber;

					if (actualNumber > order.getNumber()) {
						throw new ServiceException(baseData.getName() + "的任务工序数量不足，无法生成加工单 ");
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
	public int judgeOrderOutSource(Long orderid) {
		List<MaterialRequisition> materialRequisitionList = materialRequisitionDao.findByOrderId(orderid);
		if (materialRequisitionList.size() > 0) {
			long size = materialRequisitionList.stream()
					.filter(MaterialRequisition -> MaterialRequisition.getAudit() == 0).count();
			if (size > 0) {
				return -1;
			}
		} else {
			return 0;
		}
		return 1;
	}

	@Override
	public void saveOutSoureBills(OrderOutSource orderOutSource) {
		// 生成账单
		Consumption consumption = new Consumption();
		// 加工单工序对应价格
		List<ProcessPrice> processPriceList = processPriceDao.findByOrderOutSourceId(orderOutSource.getId());
		OrderOutSource ot = dao.findOne(orderOutSource.getId());
		consumption.setOrderOutSourceId(orderOutSource.getId());
		//外发加工对账单
		consumption.setType(10);
		//加工点
		consumption.setCustomerId(ot.getCustomerId());
		consumption.setSettleAccountsMode(2);
		consumption.setFlag(0);
		//申请金额
		consumption.setMoney(ot.getMoney());
		//申请日期
		consumption.setExpenseDate(new Date());
		consumptionDao.save(consumption);
	}

	@Override
	public List<Map<String, Object>> mixOutSoureRefund(Long id) {
		List<Map<String, Object>> mixList = new ArrayList<>();
		// 加工单
		OrderOutSource orderOutSource = findOne(id);
		// 退货单
		List<RefundBills> refundBills = refundBillsDao.findByOrderOutSourceId(id);
		// 加工单工序
		Set<BaseData> outsourceTask = orderOutSource.getOutsourceTask();
		// 加工单工序对应价格
		List<ProcessPrice> processPriceList = processPriceDao.findByOrderOutSourceId(id);
		outsourceTask.stream().forEach(outB -> {
			Map<String, Object> map = new HashMap<>();
			List<ProcessPrice> pList = processPriceList.stream()
					.filter(ProcessPrice -> ProcessPrice.getProcessTaskId().equals(outB.getId()))
					.collect(Collectors.toList());
			// 工序id
			map.put("id", outB.getId());
			// 工序名称
			map.put("name", outB.getName());
			// 工序价格
			map.put("price", pList.get(0).getPrice());
			int returnNumber = 0;
			// 循环退货单，将退货单的工序取出
			for (RefundBills r : refundBills) {
				// 当加工单工序等于退货单工序时，更新加工单工序的任务数量
				Set<BaseData> setBaseData = r.getOutsourceTask().stream()
						.filter(BaseData -> BaseData.getId().equals(outB.getId())).collect(Collectors.toSet());
				returnNumber += orderOutSource.getProcessNumber() - (setBaseData.size() > 0 ? r.getReturnNumber() : 0);
			}
			//工序数量
			map.put("number",returnNumber);
		});
		return mixList;
	}

	@Override
	public void updateProcessPrice(ProcessPrice processPrice) {
		ProcessPrice ot = processPriceDao.findOne(processPrice.getId());
		if(ot.getOrderOutSource().getChargeOff()==1){
			throw new ServiceException("已出账，无法修改价格");
		}
		ot.setPrice(processPrice.getPrice());
		processPriceDao.save(ot);
	}

}
