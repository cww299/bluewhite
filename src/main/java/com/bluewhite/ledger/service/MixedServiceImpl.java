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
import com.bluewhite.ledger.dao.BillDao;
import com.bluewhite.ledger.dao.MixedDao;
import com.bluewhite.ledger.entity.Mixed;

@Service
public class MixedServiceImpl extends BaseServiceImpl<Mixed, Long> implements MixedService {

	@Autowired
	private MixedDao dao;

	@Autowired
	private BillDao billdao;

	@Override
	public PageResult<Mixed> findPages(Mixed param, PageParameter page) {
		Page<Mixed> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();

			// 按合同签订日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(
						cb.between(root.get("").as(Date.class), param.getOrderTimeBegin(), param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);

		PageResult<Mixed> result = new PageResult<Mixed>(pages, page);
		return result;
	}

	@Override
	public void addMixed(Mixed mixed) {
		dao.save(mixed);
	}

	@Override
	@Transactional
	public void deleteMixed(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					dao.delete(id);
				}
			}
		}
	}
}
