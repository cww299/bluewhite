package com.bluewhite.ledger.service;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.dao.ScatteredOutboundDao;
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
					// 出库单
					ScatteredOutbound scatteredOutbound = new ScatteredOutbound();
					scatteredOutbound.setOrderMaterialId(id);
					scatteredOutbound
							.setOutboundNumber(ot.getOrder().getBacthNumber() + ot.getOrder().getProduct().getName());
					// 按id排序，保证库存先入先出
					// 遍历当前物料的库存采购单，一般只会存在一条，当库存量不足，需要重新下单采购单，会出现两条
					Set<OrderProcurement> orderProcurementSet = ot.getMateriel().getOrderProcurements().stream()
							.sorted(Comparator.comparing(OrderProcurement::getId))
							.filter(OrderProcurement -> OrderProcurement.getResidueNumber() > 0)
							.collect(Collectors.toSet());
					// 物料的库存采购单的剩余总量，根据总量判断是否需要继续生成采购单
					double sumResidueNumber = orderProcurementSet.stream()
							.mapToDouble(OrderProcurement::getResidueNumber).sum();
					if (sumResidueNumber < ot.getDosage()) {
						throw new ServiceException(ot.getMateriel().getNumber() + ot.getMateriel().getName() + "库存不足，无法出库，请核对库存采购后进行出库");
					}
					if (orderProcurementSet.size() > 0) {
						for (OrderProcurement orderProcurement : orderProcurementSet) {
							scatteredOutbound.setOrderProcurementId(orderProcurement.getId());
							// 出库分为两种情况，1.当库存量不足，补充库存后，出现两条采购单
							// 2.库存充足只存在一条采购单
							if (orderProcurement.getResidueNumber() < ot.getDosage()) {
								scatteredOutbound.setDosage(orderProcurement.getResidueNumber());
								orderProcurement.setResidueNumber((double) 0);
								ot.setDosage(NumUtils.sub(ot.getDosage(), orderProcurement.getResidueNumber()));
								ot.setOutbound(1);
							}
							if (orderProcurement.getResidueNumber() >= ot.getDosage()) {
								scatteredOutbound.setDosage(ot.getDosage());
								orderProcurement.setResidueNumber(NumUtils.sub(orderProcurement.getResidueNumber(), ot.getDosage()));
								ot.setOutbound(1);
							}
						}
						orderMaterialDao.save(ot);
					} else {
						throw new ServiceException(ot.getMateriel().getNumber() + ot.getMateriel().getName() + "无库存，请先采购");
					}
					save(scatteredOutbound);
					count++;
				}
			}
		}
		return count;
	}
}
