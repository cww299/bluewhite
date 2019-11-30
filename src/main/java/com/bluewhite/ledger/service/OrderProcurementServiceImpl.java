package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.service.ConsumptionService;
import com.bluewhite.ledger.dao.MaterialPutStorageDao;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.dao.ScatteredOutboundDao;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.ScatteredOutbound;

@Service
public class OrderProcurementServiceImpl extends BaseServiceImpl<OrderProcurement, Long>
		implements OrderProcurementService {

	@Autowired
	private OrderProcurementDao dao;
	@Autowired
	private OrderMaterialDao orderMaterialDao;
	@Autowired
	private ScatteredOutboundDao scatteredOutboundDao;
	@Autowired
	private ConsumptionService consumptionService;
	@Autowired
	private MaterialPutStorageDao materialPutStorageDao;

	@Override
	public PageResult<OrderProcurement> findPages(OrderProcurement param, PageParameter page) {
		Page<OrderProcurement> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("order").get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按采购单编号
			if (!StringUtils.isEmpty(param.getOrderProcurementNumber())) {
				predicate.add(cb.like(root.get("orderProcurementNumber").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getOrderProcurementNumber()) + "%"));
			}
			// 按合同id
			if (param.getOrderId() != null) {
				predicate.add(cb.equal(root.get("orderId").as(Long.class), param.getOrderId()));
			}
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 是否到货
			if (param.getArrival() != null) {
				predicate.add(cb.equal(root.get("arrival").as(Integer.class), param.getArrival()));
			}
			// 是否验货
			if (param.getInspection() != null) {
				predicate.add(cb.equal(root.get("inspection").as(Integer.class), param.getInspection()));
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
			// 按下单日期
			if (param.getPlaceOrderTime() != null) {
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("placeOrderTime").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<OrderProcurement> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int confirmOrderProcurement(OrderProcurement orderProcurement) {

		return 0;
	}

	@Override
	@Transactional
	public void saveOrderProcurement(OrderProcurement orderProcurement) {
		// 修改
		if (orderProcurement.getId() != null) {
			OrderProcurement ot = findOne(orderProcurement.getId());
			if (ot.getAudit() == 1) {
				throw new ServiceException("当前批次采购单已审核，无法修改");
			}
			List<ScatteredOutbound> scatteredOutboundList = scatteredOutboundDao
					.findByOrderProcurementId(orderProcurement.getId());
			if (scatteredOutboundList.size() > 0) {
				throw new ServiceException("当前批次采购单已有出库记录，无法修改");
			}
			orderProcurement.setOrderId(ot.getOrderId());
			orderProcurement.setMaterielId(ot.getMaterielId());
			orderProcurement.setOrderProcurementNumber(ot.getOrderProcurementNumber());
		} else {
			OrderMaterial orderMaterial = orderMaterialDao.findOne(orderProcurement.getOrderMaterialId());
			orderProcurement.setOrderId(orderMaterial.getOrderId());
			// 跟面料进行关联，进行虚拟入库，当采购单实际入库后，进行真实库存的确定
			orderProcurement.setMaterielId(orderMaterial.getMaterielId());
			// 生成新编号
			orderProcurement.setOrderProcurementNumber(
					orderMaterial.getOrder().getBacthNumber() + "/" + orderMaterial.getOrder().getProduct().getName()
							+ "/" + orderMaterial.getMateriel().getName() + "/" + orderProcurement.getNewCode());
		}
		orderProcurement.setInOutError(0);
		orderProcurement.setArrival(0);
		orderProcurement.setAudit(0);
		orderProcurement.setInspection(0);
		orderProcurement.setReplenishment(0);
		orderProcurement.setBill(0);
		// 剩余数量
		orderProcurement.setResidueNumber(orderProcurement.getPlaceOrderNumber());
		save(orderProcurement);
	}

	@Override
	@Transactional
	public int deleteOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					List<ScatteredOutbound> scatteredOutboundList = scatteredOutboundDao.findByOrderProcurementId(id);
					if (scatteredOutboundList.size() > 0) {
						throw new ServiceException("当前批次库存采购单已有出库记录，无法删除");
					}
					delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public List<OrderProcurement> warningOrderProcurement(Integer inOut) {
		return dao.findByInOutError(inOut);
	}

	@Override
	public int fixOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					// 采购单审核入库后入库数量和下单数量不符
					// 根据采购单id查询出所有使用了该采购单的所有分散出库单
					List<ScatteredOutbound> scatteredOutboundList = scatteredOutboundDao.findByOrderProcurementId(id);
					OrderProcurement orderProcurement = findOne(id);
					if (scatteredOutboundList.size() > 0) {
						// 采购生成的所有出库单总耗料未审核
						double sumDosage = scatteredOutboundList.stream()
								.filter(ScatteredOutbound -> ScatteredOutbound.getAudit() == 0)
								.mapToDouble(ScatteredOutbound::getDosage).sum();
						//获取该采购单的到货单
						List<MaterialPutStorage>  materialPutStorageList =  materialPutStorageDao.findByOrderProcurementIdAndInspection(id,1);
						double arrivalNumber = materialPutStorageList.stream().mapToDouble(MaterialPutStorage::getArrivalNumber).sum();
						if (arrivalNumber < sumDosage) {
							throw new ServiceException(
									orderProcurement.getOrderProcurementNumber() + "采购单生成的分散出库单所消耗总量小于采购单实际到货数量，无法审核");
						}
						//将到货数量更新到下单数量
						orderProcurement.setPlaceOrderNumber(arrivalNumber);
						orderProcurement.setInOutError(0);
						save(orderProcurement);
						count++;
					}
				}
			}
		}
		return count;
	}


	@Override
	public int auditOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderProcurement orderProcurement = dao.findOne(id);
					if (orderProcurement != null) {
						if (orderProcurement.getAudit() == 1) {
							throw new ServiceException("当前采购单已审核，请勿重复审核");
						}
						orderProcurement.setAudit(1);
						save(orderProcurement);
						count++;
					}
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int arrivalOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderProcurement orderProcurement = dao.findOne(id);
					if (orderProcurement != null) {
						if (orderProcurement.getArrival() == 1) {
							throw new ServiceException(orderProcurement.getOrderProcurementNumber()+"采购单已成功审核，请不要多次审核");
						}
						// 判断是否有入库单
						List<MaterialPutStorage>  materialPutStorageList =  materialPutStorageDao.findByOrderProcurementId(id);
						if(materialPutStorageList.size()<0){
							throw new ServiceException(orderProcurement.getOrderProcurementNumber()+"采购单没有生成入库单，无法审核");
						}
						int size = materialPutStorageList.stream().filter(MaterialPutStorage->MaterialPutStorage.getInspection()==1).collect(Collectors.toList()).size();
						if(size>0){
							throw new ServiceException(orderProcurement.getOrderProcurementNumber()+"采购单有入库单，未完成验货，无法审核");
						}
						orderProcurement.setArrival(1);
						save(orderProcurement);
						count++;
					}
				}
			}
		}
		return count;
	}

	@Override
	public int inspectionOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderProcurement orderProcurement = dao.findOne(id);
					if (orderProcurement != null) {
						if (orderProcurement.getArrival() == 0) {
							throw new ServiceException("当前采购入库单未入库，无法验货，请先入库");
						}
						if (orderProcurement.getInspection() == 1) {
							throw new ServiceException("当前采购入库单已检验，请勿重复检验");
						}
						// 当实际克重和约定克重不相符
//						if (orderProcurement.getConventionSquareGram() != null
//								&& orderProcurement.getSquareGram() != null
//								&& orderProcurement.getConventionSquareGram() > orderProcurement.getSquareGram()) {
//							orderProcurement.setGramPrice(NumUtils.mul(
//									NumUtils.div(
//											NumUtils.sub(orderProcurement.getConventionSquareGram(),
//													orderProcurement.getSquareGram()),
//											orderProcurement.getConventionSquareGram(), 3),
//									orderProcurement.getPrice(), orderProcurement.getArrivalNumber()));
//						}
//						// 当退货数量存在时，更新:付款金额，面料库存
//						Double returnNumber = NumUtils.setzro(orderProcurement.getReturnNumber());
//						orderProcurement.setPaymentMoney(NumUtils.mul(orderProcurement.getPrice(),
//								NumUtils.sub(orderProcurement.getArrivalNumber(), returnNumber)));
//						orderProcurement.getMateriel().setInventoryNumber(
//								NumUtils.sub(orderProcurement.getMateriel().getInventoryNumber(), returnNumber));
//						// 更新到货状态
//						// 1.全部接收
//						// 2.全部退货
//						// 3.降价接收
//						// 4.部分接收，部分退货
//						// 5.部分接收，部分延期付款
//						orderProcurement.setArrivalStatus(1);
//						if (orderProcurement.getReturnNumber() != null) {
//							if (orderProcurement.getReturnNumber() == orderProcurement.getPlaceOrderNumber()) {
//								orderProcurement.setArrivalStatus(2);
//								orderProcurement.setReplenishment(1);
//							}
//							if (orderProcurement.getReturnNumber() < orderProcurement.getPlaceOrderNumber()) {
//								orderProcurement.setArrivalStatus(4);
//								orderProcurement.setReplenishment(1);
//							}
//						}
						//占用供应商资金利息
//						orderProcurement.setInterest(NumUtils.mul(
//								NumUtils.sum(NumUtils.setzro(orderProcurement.getPartDelayPrice()), orderProcurement.getPaymentMoney()),
//								(double)DatesUtil.getDaySub(orderProcurement.getExpectPaymentTime(), orderProcurement.getArrivalTime()),
//								orderProcurement.getInterestday()));
						orderProcurement.setInspection(1);
						save(orderProcurement);
						count++;
					}
				}
			}
		}
		return count;
	}

	@Override
	public int billOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderProcurement orderProcurement = dao.findOne(id);
					if (orderProcurement != null) {
						if (orderProcurement.getBill() == 1) {
							throw new ServiceException("当前采购单已生成采购应付账单，请勿重复生成");
						}
						// 生成采购应付账单
						Consumption consumption = new Consumption();
						consumption.setType(2);
						consumption.setContent(orderProcurement.getOrderProcurementNumber());
						consumption.setOrderProcurementId(id);
						consumption.setCustomerId(orderProcurement.getCustomerId());
						consumption.setMoney(orderProcurement.getPaymentMoney());
						consumption.setExpenseDate(orderProcurement.getExpectPaymentTime());
						consumption.setFlag(0);
						consumption.setUserId(orderProcurement.getUserId());
						consumptionService.save(consumption);
						orderProcurement.setBill(1);
						save(orderProcurement);
						count++;
					}
				}
			}
		}
		return count;
	}

	@Override
	public void updateBillOrderProcurement(OrderProcurement orderProcurement) {
		OrderProcurement ot = dao.findOne(orderProcurement.getId());
		update(orderProcurement, ot, "");
		//占用供应商资金利息
//		ot.setInterest(NumUtils.mul(
//				NumUtils.sum(NumUtils.setzro(ot.getPartDelayPrice()), ot.getPaymentMoney()),
//				(double)DatesUtil.getDaySub(ot.getExpectPaymentTime(), ot.getArrivalTime()),
//				ot.getInterestday()));
		save(ot);
	}

}
