package com.bluewhite.finance.expenseAccount.service;

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
import com.bluewhite.finance.expenseAccount.dao.ExpenseAccountDao;
import com.bluewhite.finance.expenseAccount.entity.ExpenseAccount;

@Service
public class ExpenseAccountServiceImpl extends BaseServiceImpl<ExpenseAccount, Long> implements ExpenseAccountService {

	@Autowired
	private ExpenseAccountDao dao;

	@Override
	public PageResult<ExpenseAccount> findPages(ExpenseAccount param, PageParameter page) {
		Page<ExpenseAccount> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}

			// 按是否已付款报销过滤
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			// 按是否已付款报销过滤
			if (param.getBudget() != null) {
				predicate.add(cb.equal(root.get("budget").as(Integer.class), param.getBudget()));
			}

			// 按报销人姓名查找
			if (!StringUtils.isEmpty(param.getUsername())) {
				predicate.add(
						cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUsername() + "%"));
			}

			// 按报销內容查找
			if (!StringUtils.isEmpty(param.getContent())) {
				predicate.add(cb.like(root.get("content").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getContent()) + "%"));
			}

			if (!StringUtils.isEmpty(param.getExpenseDate())) {
				// 按申请报销单日期
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("expenseDate").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}
			if (!StringUtils.isEmpty(param.getPaymentDate())) {
				// 按财务付款日期
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("paymentDate").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, SalesUtils.getQueryNoPageParameter());
		PageResultStat<ExpenseAccount> result = new PageResultStat<>(pages, page);
		result.setAutoStateField(null, "money");
		result.count();
		return result;
	}

	@Override
	public ExpenseAccount addExpenseAccount(ExpenseAccount expenseAccount) {
		expenseAccount.setFlag(0);
		dao.save(expenseAccount);
		return expenseAccount;
	}

	@Override
	@Transactional
	public int deleteAccountService(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					ExpenseAccount expenseAccount = dao.findOne(id);
					if (expenseAccount.getFlag() == 0) {
						dao.delete(id);
						count++;
					} else {
						throw new ServiceException(expenseAccount.getContent() + "的报销单已经审核放款无法删除");
					}

				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public ExpenseAccount updateExpenseAccount(ExpenseAccount expenseAccount) {
		ExpenseAccount oldExpenseAccount = dao.findOne(expenseAccount.getId());
		if (oldExpenseAccount != null) {
			if (oldExpenseAccount.getFlag() == 1) {
				throw new ServiceException("该报销单已放款，无法修改");
			}
			BeanCopyUtils.copyNullProperties(oldExpenseAccount, expenseAccount);
			expenseAccount.setCreatedAt(oldExpenseAccount.getCreatedAt());
			dao.save(expenseAccount);
		} else {
			throw new ServiceException("该报销单不存在，查证后修改");
		}
		return expenseAccount;
	}

	@Override
	public ExpenseAccount auditExpenseAccount(ExpenseAccount expenseAccount) {
		ExpenseAccount oldExpenseAccount = dao.findOne(expenseAccount.getId());
		if (oldExpenseAccount != null) {
			oldExpenseAccount.setFlag(1);
			oldExpenseAccount.setPaymentMoney(expenseAccount.getPaymentMoney());
			oldExpenseAccount.setPaymentDate(expenseAccount.getPaymentDate());
			dao.save(oldExpenseAccount);
		} else {
			throw new ServiceException("该报销单不存在，查证后修改");
		}
		return expenseAccount;
	}

}
