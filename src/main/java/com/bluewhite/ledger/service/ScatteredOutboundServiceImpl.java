package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Comparator;
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
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.dao.ScatteredOutboundDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.ScatteredOutbound;
import com.bluewhite.product.primecostbasedata.dao.MaterielDao;

@Service
public class ScatteredOutboundServiceImpl extends BaseServiceImpl<ScatteredOutbound, Long>
		implements ScatteredOutboundService {

	@Autowired
	private ScatteredOutboundDao dao;
	@Autowired
	private OrderProcurementDao orderProcurementDao;
	@Autowired
	private OrderMaterialDao orderMaterialDao;
	@Autowired
	private MaterielDao materielDao;
	@Autowired
	private OrderService orderService;

	@Override
	@Transactional
	public int saveScatteredOutbound(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderMaterial ot = orderMaterialDao.findOne(id);
					if (ot.getOutbound() == 1) {
						throw new ServiceException(
								ot.getMateriel().getNumber() + ot.getMateriel().getName() + "已生成耗料出库单，请勿多次生成");
					}
					// 按id排序，保证库存先入先出
					// 遍历当前物料的库存采购单，一般只会存在一条，当库存量不足，需要重新下单采购单，会出现两条
					// 一条领料单会关连多条采购单（库存详情单）
					Set<OrderProcurement> orderProcurementSet = ot.getMateriel().getOrderProcurements().stream()
							.sorted(Comparator.comparing(OrderProcurement::getId))
							.filter(OrderProcurement -> OrderProcurement.getResidueNumber() > 0)
							.collect(Collectors.toSet());
					// 物料的库存采购单的剩余总量，根据总量判断是否需要继续生成采购单
					double sumResidueNumber = orderProcurementSet.stream()
							.mapToDouble(OrderProcurement::getResidueNumber).sum();
					if (sumResidueNumber < ot.getDosage()) {
						throw new ServiceException(
								ot.getMateriel().getNumber() + ot.getMateriel().getName() + "库存不足，无法出库，请核对库存采购后进行出库");
					}
					// 该物料总耗料
					double dosage = ot.getDosage();
					// 该物料总数量
					int dosageSumNumber = ot.getOrder().getNumber();
					if (orderProcurementSet.size() > 0) {
						for (OrderProcurement orderProcurement : orderProcurementSet) {
							// 耗料出库单
							ScatteredOutbound scatteredOutbound = new ScatteredOutbound();
							// 关联耗料单
							scatteredOutbound.setOrderMaterialId(id);
							scatteredOutbound.setOutboundNumber(Constants.SCHL + StringUtil.getDate());
							scatteredOutbound.setAudit(0);
							scatteredOutbound.setOrderProcurementId(orderProcurement.getId());
							// 领料分为两种情况，
							// 1.当库存量不足，补充库存后，出现两条采购单(采购库存单剩余数量小于领料单领取数量)
							// 2.库存充足只存在一条采购单(采购库存单剩余数量大于或者领料单领取数量)
							if (orderProcurement.getResidueNumber() < dosage) {
								scatteredOutbound.setDosage(orderProcurement.getResidueNumber());
								scatteredOutbound.setResidueDosage(orderProcurement.getResidueNumber());
								// 将剩余需要分配的用量更新到下一单
								dosage = NumUtils.sub(dosage, orderProcurement.getResidueNumber());
								orderProcurement.setResidueNumber((double) 0);
							} else if (orderProcurement.getResidueNumber() >= dosage) {
								scatteredOutbound.setDosage(dosage);
								scatteredOutbound.setResidueDosage(dosage);
								orderProcurement.setResidueNumber(NumUtils.sub(orderProcurement.getResidueNumber(), dosage));
							}
							int dosageNumber = NumUtils.roundTwo(NumUtils.div(
									NumUtils.mul(dosageSumNumber, scatteredOutbound.getDosage()), ot.getDosage(), 1));
							scatteredOutbound.setDosageNumber(dosageNumber);
							scatteredOutbound.setResidueDosageNumber(dosageNumber);
							save(scatteredOutbound);
							ot.setOutbound(1);
						}
						orderMaterialDao.save(ot);
					} else {
						throw new ServiceException(
								ot.getMateriel().getNumber() + ot.getMateriel().getName() + "无库存，请先采购");
					}
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public PageResult<ScatteredOutbound> findPages(ScatteredOutbound param, PageParameter page) {
		Page<ScatteredOutbound> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate
						.add(cb.like(root.get("orderMaterial").get("order").get("product").get("name").as(String.class),
								"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按合同id
			if (param.getOrderId() != null) {
				predicate.add(cb.equal(root.get("orderMaterial").get("orderId").as(Long.class), param.getOrderId()));
			}
			// 是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按出库编号
			if (!StringUtils.isEmpty(param.getOutboundNumber())) {
				predicate.add(cb.like(root.get("outboundNumber").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getOutboundNumber()) + "%"));
			}
			// 按审核日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("auditTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<ScatteredOutbound> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteScatteredOutbound(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					// 耗料单的出库详细
					List<ScatteredOutbound> scatteredOutboundList = dao.findByOrderMaterialId(id);
					int j = i;
					scatteredOutboundList.stream().forEach(s -> {
						if (s.getAudit() == 1) {
							throw new ServiceException("第" + (j + 1) + "条耗料单已审核，无法清除出库单");
						}
						// 当删除出库单时，恢复出库情况和库存
						OrderProcurement orderProcurement = s.getOrderProcurement();
						orderProcurement.setResidueNumber(NumUtils.sum(orderProcurement.getResidueNumber(), s.getDosage()));
						orderProcurementDao.save(orderProcurement);
					});
					dao.delete(scatteredOutboundList);
					OrderMaterial orderMaterial = orderMaterialDao.findOne(id);
					orderMaterial.setOutbound(0);
					orderMaterialDao.save(orderMaterial);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int auditScatteredOutbound(String ids, Date time) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					// 耗料单的出库详细
					int j = i;
					List<ScatteredOutbound> scatteredOutboundList = dao.findByOrderMaterialId(id);
					scatteredOutboundList.stream().forEach(ot -> {
						if (ot.getAudit() == 1) {
							throw new ServiceException("第" + (j + 1) + "条领料单已审核，请勿多次审核");
						}
						if (ot.getOrderProcurement().getArrival() == 0) {
							throw new ServiceException("第" + (j + 1) + "条领料单，物料未到货，无法审核");
						}
						if (ot.getOrderProcurement().getInOutError() == 1) {
							throw new ServiceException(ot.getOrderProcurement().getOrderProcurementNumber()
									+ "采购单实际数量和下单数量不相符，无法审核，请先修正数量");
						}
						if (time != null) {
							ot.setAuditTime(time);
						}
						if (ot.getAuditTime() == null) {
							throw new ServiceException("第" + (j + 1) + "条分散出库单未填写审核时间");
						}
						ot.setAudit(1);
						dao.save(ot);
					});
					count++;
				}
				// 对订单的备料状态进行更新
				OrderMaterial orderMaterial = orderMaterialDao.findOne(Long.parseLong(idArr[0]));
				Order order = orderMaterial.getOrder();
				if (orderMaterial != null && order != null) {
					List<ScatteredOutbound> scatteredOutboundList = new ArrayList<>();
					order.getOrderMaterials().stream().forEach(om -> {
						List<ScatteredOutbound> so = dao.findByOrderMaterialId(om.getId());
						scatteredOutboundList.addAll(so);
					});
					if (scatteredOutboundList.size() > 0) {
						List<ScatteredOutbound> auditScatteredOutboundList = scatteredOutboundList.stream()
								.filter(ScatteredOutbound -> ScatteredOutbound.getAudit() == 0)
								.collect(Collectors.toList());
						if (auditScatteredOutboundList.size() == 0) {
							order.setPrepareEnough(1);
							orderService.save(order);
						}
					}

				}
			}
		}
		return count;
	}

}
