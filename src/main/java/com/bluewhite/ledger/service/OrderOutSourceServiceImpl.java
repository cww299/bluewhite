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
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.OrderOutSourceDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.ScatteredOutbound;

@Service
public class OrderOutSourceServiceImpl extends BaseServiceImpl<OrderOutSource, Long> implements OrderOutSourceService {

	@Autowired
	private OrderOutSourceDao dao;
	@Autowired
	private OrderDao orderDao;
	

	@Override
	@Transactional
	public void saveOrderOutSource(OrderOutSource orderOutSource) {
		if(orderOutSource.getOrderId()!=null){
			Order order = orderDao.findOne(orderOutSource.getOrderId());
			List<OrderOutSource> orderOutSourceList = dao.findByOrderIdAndFlag(orderOutSource.getOrderId(),1);
			if(orderOutSourceList.size()>0){
				double sumNumber = orderOutSourceList.stream().mapToDouble(OrderOutSource::getProcessNumber).sum();
				if(NumUtils.sum(sumNumber,orderOutSource.getProcessNumber()) > order.getNumber()){
					throw new ServiceException("外发总数量不能大于下单合同数量，请核实后填写");
				}
			}
			orderOutSource.setRemark(order.getRemark());
			orderOutSource.setFlag(0);
			orderOutSource.setAudit(0);
			save(orderOutSource);
		}else{
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
			// 是否作废
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 按任务工序过滤
			if (param.getProcess()!=null) {
				predicate.add(cb.equal(root.get("process").as(String.class), param.getProcess()));
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
		if(orderOutSource.getId()!=null){
			OrderOutSource ot = findOne(orderOutSource.getId());
			update(orderOutSource, ot, "");
		}
		if(orderOutSource.getOrderId()!=null){
			Order order = orderDao.findOne(orderOutSource.getOrderId());
			List<OrderOutSource> orderOutSourceList = dao.findByOrderIdAndFlag(orderOutSource.getOrderId(),1);
			double sumNumber = orderOutSourceList.stream().mapToDouble(OrderOutSource::getProcessNumber).sum();
			if(sumNumber> order.getNumber()){
				throw new ServiceException("外发总数量不能大于下单合同数量，请核实后填写");
			}
		}else{
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
		if(orderOutSource.getId()!=null){
			OrderOutSource ot = findOne(orderOutSource.getId());
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
					if(orderOutSource.getWarehouseTypeId()==null){
						throw new ServiceException("未填写入库仓库，无法入库，请先确认入库仓库");
					}
					
					
					
					count++;
				}
			}
		}
		return count;
	}
}
