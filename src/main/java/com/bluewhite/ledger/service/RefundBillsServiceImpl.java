package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.bluewhite.common.utils.StringUtil;
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
	@Autowired
	private BaseDataDao baseDataDao;

	@Override
	@Transactional
	public void saveRefundBills(RefundBills refundBills) {
		// 获取加工单
		OrderOutSource orderOutSource = orderOutSourceDao.findOne(refundBills.getOrderOutSourceId());
		if (orderOutSource.getAudit() == 0) {
			throw new ServiceException("加工单还未审核，无法退货");
		}
		// 将工序任务变成set存入，存在退货情况是，要去除退货数量
		if (!StringUtils.isEmpty(refundBills.getOutsourceTaskIds())) {
			String[] idStrings = refundBills.getOutsourceTaskIds().split(",");
			if (idStrings.length > 0) {
				for (String ids : idStrings) {
					Long id = Long.parseLong(ids);
					BaseData baseData = baseDataDao.findOne(id);
					refundBills.getOutsourceTask().add(baseData);
				}
			}
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
		BeanCopyUtils.copyNotEmpty(refundBills, ot, "");
		// 将工序任务变成set存入，存在退货情况是，要去除退货数量
		if (!StringUtils.isEmpty(ot.getOutsourceTaskIds())) {
			String[] idStrings = ot.getOutsourceTaskIds().split(",");
			if (idStrings.length > 0) {
				for (String ids : idStrings) {
					Long id = Long.parseLong(ids);
					BaseData baseData = baseDataDao.findOne(id);
					ot.getOutsourceTask().add(baseData);
				}
			}
		}
		save(ot);
	}

	@Override
	public PageResult<RefundBills> findPages(RefundBills param, PageParameter page) {
		Page<RefundBills> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按生产单编号
			if (!StringUtils.isEmpty(param.getOrderName())){ 
				predicate.add(cb.like(root.get("orderOutSource").get("order").get("orderNumber").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getOrderName()) + "%"));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("returnTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<RefundBills> result = new PageResult<>(pages, page);
		return result;
	}

}
