package com.bluewhite.finance.consumption.service;

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
import com.bluewhite.finance.consumption.dao.ConsumptionDao;
import com.bluewhite.finance.consumption.dao.CustomerDao;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.entity.Customer;

@Service
public class ConsumptionServiceImpl extends BaseServiceImpl<Consumption, Long> implements ConsumptionService {

	@Autowired
	private ConsumptionDao dao;
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public PageResult<Consumption> findPages(Consumption param, PageParameter page) {
		Page<Consumption> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			
			// 按消费类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
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
			
			// 按客户查找
			if (!StringUtils.isEmpty(param.getCustomerId())) {
				predicate.add(
						cb.equal(root.get("customerId").as(String.class), param.getCustomerId()));
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
		PageResultStat<Consumption> result = new PageResultStat<>(pages, page);
		result.setAutoStateField(null, "money");
		result.count();
		return result;
	}

	@Override
	public Consumption addConsumption(Consumption consumption) {
		consumption.setFlag(0);
		if(consumption.getCustomerId()==null){
			Customer customer = new Customer();
			customer.setName(consumption.getCustomerName());
			customer.setType(consumption.getType());
			customerDao.save(customer);
			consumption.setCustomerId(customer.getId());
		}
		dao.save(consumption);
		return consumption;
	}

	@Override
	@Transactional
	public int deleteConsumption(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Consumption consumption = dao.findOne(id);
					if (consumption.getFlag() == 0) {
						dao.delete(id);
						count++;
					} else {
						throw new ServiceException(consumption.getContent() + "的报销单已经审核放款无法删除");
					}

				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public Consumption updateConsumption(Consumption consumption) {
		Consumption oldConsumption = dao.findOne(consumption.getId());
		if (oldConsumption != null) {
			if (oldConsumption.getFlag() == 1) {
				throw new ServiceException("该报销单已放款，无法修改");
			}
			BeanCopyUtils.copyNullProperties(oldConsumption, consumption);
			consumption.setCreatedAt(oldConsumption.getCreatedAt());
			dao.save(consumption);
		} else {
			throw new ServiceException("该报销单不存在，查证后修改");
		}
		return consumption;
	}

	@Override
	public Consumption auditConsumption(Consumption consumption) {
		if(consumption.getPaymentDate() == null && consumption.getPaymentMoney() == null){
			throw new ServiceException("返款金额或放款时间不能为空");
		}
		Consumption oldConsumption = dao.findOne(consumption.getId());
		if (oldConsumption != null) {
			oldConsumption.setFlag(consumption.getFlag());
			oldConsumption.setPaymentMoney(consumption.getPaymentMoney());
			oldConsumption.setPaymentDate(consumption.getPaymentDate());
			dao.save(oldConsumption);
		} else {
			throw new ServiceException("该报销单不存在，查证后修改");
		}
		return consumption;
	}

}
