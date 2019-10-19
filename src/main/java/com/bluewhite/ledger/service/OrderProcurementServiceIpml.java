package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderMaterialDao;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.dao.ScatteredOutboundDao;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.ledger.entity.ScatteredOutbound;

@Service
public class OrderProcurementServiceIpml extends BaseServiceImpl<OrderProcurement, Long> implements OrderProcurementService {
	
	@Autowired
	private OrderProcurementDao dao;
	@Autowired
	private OrderMaterialDao orderMaterialDao;
	@Autowired
	private ScatteredOutboundDao scatteredOutboundDao;
	
	
	@Override
	public PageResult<OrderProcurement> findPages(OrderProcurement param, PageParameter page) {
		Page<OrderProcurement> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按产品名称
			if (!StringUtils.isEmpty(param.getProductName())){
				predicate.add(cb.like(root.get("orderMaterial").get("order").get("product").get("name").as(String.class),"%"+StringUtil.specialStrKeyword(param.getProductName())+"%") );
			}
			// 按合同id
			if (param.getOrderId()!=null){
				predicate.add(cb.equal(root.get("orderMaterial").get("orderId").as(Long.class),param.getProductName()));
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
	public void saveOrderProcurement(OrderProcurement orderProcurement) {
		//修改
		if(orderProcurement.getId()!=null){
			OrderProcurement ot = findOne(orderProcurement.getId());
			List<ScatteredOutbound> scatteredOutboundList = scatteredOutboundDao.findByOrderProcurementId(orderProcurement.getId());
			if(scatteredOutboundList.size()>0){
				throw new ServiceException("当前批次采购单已有出库记录，无法修改");
			}
		}
		OrderMaterial orderMaterial = orderMaterialDao.findOne(orderProcurement.getOrderMaterialId());
		//生成新编号,暂时不跟面料进行关联，当采购单实际入库后，关联面料
		orderProcurement.setOrderProcurementNumber(	orderMaterial.getOrder().getBacthNumber()+"/"+orderMaterial.getOrder().getProduct().getName()+"/"
						+orderMaterial.getMateriel().getName()+"/"+orderProcurement.getNewCode());
		orderProcurement.setUseUp(0);
		save(orderProcurement);
	}

	@Override
	public int deleteOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					List<ScatteredOutbound> scatteredOutboundList = scatteredOutboundDao.findByOrderProcurementId(id);
					if(scatteredOutboundList.size()>0){
						throw new ServiceException("当前批次采购单已有出库记录，无法修改");
					}
					delete(id);
					count++;
				}
			}
		}
		return count;
	}

}
