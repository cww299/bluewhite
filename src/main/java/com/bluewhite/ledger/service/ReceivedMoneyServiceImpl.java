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
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.ledger.dao.ReceivedMoneyDao;
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.ReceivedMoney;

@Service
public class ReceivedMoneyServiceImpl extends BaseServiceImpl<ReceivedMoney, Long> implements ReceivedMoneyService {

	@Autowired
	private ReceivedMoneyDao dao;
	
	@Override
	public List<ReceivedMoney> receivedMoneyList(Bill param) {
		List<ReceivedMoney> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按合同签订日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("receivedMoneyDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public PageResult<ReceivedMoney> receivedMoneyPage(ReceivedMoney param, PageParameter page) {
		if(param.getOrderTimeBegin()!=null){
			param.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(param.getOrderTimeBegin()));
		}
		Page<ReceivedMoney> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按合同签订日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("receivedMoneyDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);

		PageResult<ReceivedMoney> result = new PageResult<ReceivedMoney>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteReceivedMoney(String ids) {
		int count= 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					dao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

}
