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
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.service.ConsumptionService;
import com.bluewhite.ledger.dao.MaterialOutStorageDao;
import com.bluewhite.ledger.dao.MaterialPutStorageDao;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.dao.ScatteredOutboundDao;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.ScatteredOutbound;

import cn.hutool.core.date.DateUtil;

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
	private MaterialPutStorageService materialPutStorageService;
    @Autowired
    private MaterialPutStorageDao materialPutStorageDao;
	@Autowired
	private MaterialOutStorageDao materialOutStorageDao;

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
//		result.getRows().forEach(o->{
//		    // 获取到货数量
//	        List<MaterialPutStorage> materialPutStorageList = materialPutStorageDao.findByOrderProcurementId(o.getId());
//	        double warehousingNumber = materialPutStorageList.stream().mapToDouble(MaterialPutStorage::getArrivalNumber).sum();
//	        // 计算退货总数
//	        List<MaterialOutStorage> list = new ArrayList<>();
//	        materialPutStorageList.stream().forEach(m -> {
//	            List<Long> longList = materialOutStorageDao.findMaterialPutStorageId(m.getId());
//	            List<MaterialOutStorage> listMaterialOutStorage =  materialOutStorageDao.findAll(longList);
//	            if(listMaterialOutStorage.size()>0) {
//	                List<MaterialOutStorage>  mList = listMaterialOutStorage.stream().filter(MaterialOutStorage->MaterialOutStorage.getOutStatus()==4).collect(Collectors.toList());
//	                list.addAll(mList);
//	            }
//	        });
//	        double returnNumber = list.stream().mapToDouble(MaterialOutStorage::getArrivalNumber).sum();
//	        o.setReturnNumber(returnNumber);
//	        //获取已入库数量
//	        o.setWarehousingNumber(NumUtils.sub(warehousingNumber,returnNumber));
//	        if(returnNumber>0) {
//	            o.setReplenishment(1);
//	        }
//		});
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
						double arrivalNumber = materialPutStorageService.getArrivalNumber(id);
						//采购单生成的分散出库单所消耗总量小于采购单实际到货数量的时候，要进行补订货或者其他操作
						if (arrivalNumber < sumDosage) {
							throw new ServiceException(
									orderProcurement.getOrderProcurementNumber() + "采购单生成的分散出库单所消耗总量小于采购单实际到货数量，无法审核");
						}
						//将到货数量更新到下单数量
						orderProcurement.setPlaceOrderNumber(arrivalNumber);
						orderProcurement.setResidueNumber(NumUtils.sub(arrivalNumber, sumDosage));
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
	public int arrivalOrderProcurement(String ids,Date time) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderProcurement orderProcurement = dao.findOne(id);
					if (orderProcurement != null) {
//						if (orderProcurement.getArrival() == 1) {
//							throw new ServiceException(orderProcurement.getOrderProcurementNumber()+"采购单已成功审核，请不要多次审核");
//						}
						// 判断是否有入库单
						List<MaterialPutStorage>  materialPutStorageList =  materialPutStorageService.findByOrderProcurementId(id);
						if(materialPutStorageList.size()==0){
							throw new ServiceException(orderProcurement.getOrderProcurementNumber()+"采购单还未生成入库单，无法审核");
						}
						int size = materialPutStorageList.stream().filter(MaterialPutStorage->MaterialPutStorage.getInspection()==0).collect(Collectors.toList()).size();
						if(size>0){
							throw new ServiceException(orderProcurement.getOrderProcurementNumber()+"采购单有入库单，未完成验货，无法审核");
						}
						//已经到货数量，以排除退货数量
						double arrivalNumber = materialPutStorageService.getArrivalNumber(id);
						//不相同时则标记出
						if(orderProcurement.getPlaceOrderNumber()!=arrivalNumber){
							orderProcurement.setInOutError(1);
						}else {
						    orderProcurement.setInOutError(0);
						}
						//缺克重价值
						double gramPrice = materialPutStorageList.stream().filter(MaterialPutStorage->MaterialPutStorage.getGramPrice()!=null).mapToDouble(MaterialPutStorage->MaterialPutStorage.getGramPrice()).sum();
						orderProcurement.setGramPrice(gramPrice);
						orderProcurement.setArrival(1);
						orderProcurement.setArrivalStatus(1);
						orderProcurement.setArrivalTime(time);
						orderProcurement.setArrivalNumber(arrivalNumber);
						orderProcurement.setPaymentMoney(NumUtils.mul(orderProcurement.getPrice(), orderProcurement.getArrivalNumber()));
						//占用供应商资金利息 总金额*(付款时间-到货时间)*日利息
						Long day = (long)0;
						if(orderProcurement.getExpectPaymentTime().after(orderProcurement.getArrivalTime())) {
						     day = DateUtil.betweenDay(orderProcurement.getArrivalTime(), orderProcurement.getExpectPaymentTime(), true);
						}
						if(orderProcurement.getPartDelayNumber()!=null && orderProcurement.getPartDelayPrice()!=null) {
						    orderProcurement.setInterest(
						        NumUtils.mul(
						            (NumUtils.sum(orderProcurement.getPartDelayNumber(),orderProcurement.getPartDelayPrice())),
						            day.doubleValue(),
						            OrderProcurement.getInterestday()));
						}
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

}
