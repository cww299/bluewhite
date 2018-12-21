package com.bluewhite.finance.tax.service;

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
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.tax.dao.TaxDao;
import com.bluewhite.finance.tax.entity.Tax;

@Service
public class TaxServiceImpl extends BaseServiceImpl<Tax, Long> implements TaxService {

	@Autowired
	private TaxDao dao;

	@Override
	public PageResult<Tax> findPages(Tax param, PageParameter page) {
		Page<Tax> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}

			// 按是否已付款过滤
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			// 按供应商查找
			if (!StringUtils.isEmpty(param.getContent())) {
				predicate.add(cb.like(root.get("content").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getContent()) + "%"));
			}

			// 按申请日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("expenseDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}

			// 按财务付款日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("paymentDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, SalesUtils.getQueryNoPageParameter());
		PageResultStat<Tax> result = new PageResultStat<>(pages, page);
		result.setAutoStateField("", "money");
		result.count();
		return result;
	}

	@Override
	public Tax addTax(Tax tax) {
		tax.setFlag(0);
		return dao.save(tax);
	}

	@Override
	@Transactional
	public int deleteTax(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Tax tax = dao.findOne(id);
					if (tax.getFlag() == 0) {
						dao.delete(id);
						count++;
					} else {
						throw new ServiceException(tax.getContent() + "的税点单已经审核放款无法删除");
					}

				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public Tax updateTax(Tax tax) {
		Tax oldTax = dao.findOne(tax.getId());
		if (oldTax != null) {
			if (oldTax.getFlag() == 1) {
				throw new ServiceException("该税点单已放款，无法修改");
			}
			BeanCopyUtils.copyNullProperties(oldTax, tax);
			tax.setCreatedAt(oldTax.getCreatedAt());
			dao.save(tax);
		} else {
			throw new ServiceException("该税点单不存在，查证后修改");
		}
		return tax;
	}

	@Override
	public Tax auditTax(Tax tax) {
		Tax oldTax = dao.findOne(tax.getId());
		if (oldTax != null) {
			oldTax.setFlag(1);
			oldTax.setPaymentMoney(tax.getPaymentMoney());
			oldTax.setPaymentDate(tax.getPaymentDate());
			dao.save(oldTax);
		} else {
			throw new ServiceException("该税点单不存在，查证后修改");
		}
		return tax;
	}

}
