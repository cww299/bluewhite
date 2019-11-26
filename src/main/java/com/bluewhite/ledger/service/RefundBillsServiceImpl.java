package com.bluewhite.ledger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.ledger.dao.OrderOutSourceDao;
import com.bluewhite.ledger.dao.RefundBillsDao;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.RefundBills;

@Service
public class RefundBillsServiceImpl extends BaseServiceImpl<RefundBills, Long> implements RefundBillsService {

	@Autowired
	private RefundBillsDao dao;
	@Autowired
	private OrderOutSourceDao orderOutSourceDao;

	@Override
	@Transactional
	public void saveRefundBills(RefundBills refundBills) {
		// 获取加工单
		OrderOutSource orderOutSource = orderOutSourceDao.findOne(refundBills.getOrderOutSourceId());
		if (orderOutSource.getAudit() == 0) {
			throw new ServiceException("加工单还未审核，无法退货");
		}
		refundBills.setOrderId(orderOutSource.getOrderId());
		save(refundBills);
	}

	@Override
	@Transactional
	public int deleteRefundBills(String ids) {
		return delete(ids);
	}

	@Override
	public void updateRefundBills(RefundBills refundBills) {
		RefundBills ot = dao.findOne(refundBills.getId());
		update(refundBills, ot, "");
	}

}
