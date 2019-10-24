package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.List;

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
				predicate.add(cb.equal(root.get("orderId").as(Long.class),param.getOrderId()));
			}
			// 是否到货
			if (param.getArrival()!=null){
				predicate.add(cb.equal(root.get("arrival").as(Integer.class),param.getArrival()));
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
		//修改
		OrderMaterial orderMaterial = null;
		if(orderProcurement.getId()!=null){
			OrderProcurement ot = findOne(orderProcurement.getId());
			List<ScatteredOutbound> scatteredOutboundList = scatteredOutboundDao.findByOrderProcurementId(orderProcurement.getId());
			if(scatteredOutboundList.size()>0){
				throw new ServiceException("当前批次采购单已有出库记录，无法修改");
			}
			orderMaterial = orderMaterialDao.findOne(ot.getOrderMaterialId());
		}else{
			orderMaterial = orderMaterialDao.findOne(orderProcurement.getOrderMaterialId());
		}
		orderProcurement.setInOutError(0);
		orderProcurement.setArrival(0);
		orderProcurement.setOrderId(orderMaterial.getOrderId());
		//生成新编号
		orderProcurement.setOrderProcurementNumber(orderMaterial.getOrder().getBacthNumber()+"/"+orderMaterial.getOrder().getProduct().getName()+"/"
						+orderMaterial.getMateriel().getName()+"/"+orderProcurement.getNewCode());
		//跟面料进行关联，进行虚拟入库，当采购单实际入库后，进行真实库存的确定
		orderProcurement.setMaterielId(orderMaterial.getMaterielId());
		//剩余数量
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
					if(scatteredOutboundList.size()>0){
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
	@Transactional
	public int auditOrderProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					OrderProcurement orderProcurement = dao.findOne(id);
					//审核采购单进行入库，将实际的入库数值，修改后进行入库
					//1.进行面料库存的更新
					//2.标记入库数值和订购数值不同的采购单，做为库存预警表示
					if(orderProcurement!=null){
						if(orderProcurement.getArrival()==1){
							throw new ServiceException("当前采购面料已成功入库，请不要多次审核");
						}	
						if(orderProcurement.getArrivalNumber()==null || orderProcurement.getArrivalTime() == null){
							throw new ServiceException("当前采购面料未填写到库数值或日期，无法审核入库");
						}
						if(orderProcurement.getArrivalNumber()!=orderProcurement.getPlaceOrderNumber()){
							orderProcurement.setInOutError(1);
						}
						orderProcurement.getMateriel().setInventoryNumber(NumUtils.mul(orderProcurement.getMateriel().getInventoryNumber(), orderProcurement.getArrivalNumber()));
						save(orderProcurement);
						count++;
					}
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
					//根据采购单id查询出所有使用了，该采购单id的
					List<ScatteredOutbound> scatteredOutboundList = scatteredOutboundDao.findByOrderProcurementId(id);
					if(scatteredOutboundList.size()>0){
						
						
						
					}
					count++;
				}
			}
		}
		return count;
	}

}
