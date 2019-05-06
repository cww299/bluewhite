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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.consumption.dao.ConsumptionDao;
import com.bluewhite.finance.consumption.dao.CustomDao;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.entity.Custom;

@Service
public class ConsumptionServiceImpl extends BaseServiceImpl<Consumption, Long> implements ConsumptionService {

	@Autowired
	private ConsumptionDao dao;

	@Autowired
	private CustomDao customDao;

	@Override
	public PageResult<Consumption> findPages(Consumption param, PageParameter page) {
		CurrentUser cu = SessionManager.getUserSession();
		if(cu!=null && !cu.getIsAdmin() && cu.getOrgNameId() != 6 ){
			param.setOrgNameId(cu.getOrgNameId());
		}
		Page<Consumption> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按部门id过滤
			if (param.getOrgNameId() != null) {
				if (param.getParentId() == null) {
					predicate.add(cb.isNotNull(root.get("parentId").as(Long.class)));
				}
				predicate.add(cb.equal(root.get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			
			// 按父类id过滤
			if (param.getParentId() != null) {
				predicate.add(cb.equal(root.get("parentId").as(Long.class), param.getParentId()));
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
			if (!StringUtils.isEmpty(param.getCustomId())) {
				predicate.add(cb.equal(root.get("customerId").as(String.class), param.getCustomId()));
			}

			if (!StringUtils.isEmpty(param.getExpenseDate())) {
				// 按申请日期
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
	@Transactional
	public Consumption addConsumption(Consumption consumption) {
		if (consumption.getId() != null) {
			Consumption ot = dao.findOne(consumption.getId());
			if (ot.getFlag() == 1) {
				throw new ServiceException("已放款，无法修改");
			}
			this.update(consumption, ot);
		} else {
			if(consumption.getExpenseDate()==null){
				throw new ServiceException("申请时间不能为空");
			}
			if(consumption.getMoney()==null){
				throw new ServiceException("申请金额不能为空");
			}
			CurrentUser cu = SessionManager.getUserSession();
			consumption.setOrgNameId(cu.getOrgNameId());
			consumption.setFlag(0);
			boolean flag = true;
			switch (consumption.getType()) {
			case 1:
				flag = false;
				if(consumption.getParentId()!=null){
					Consumption parentConsumption = dao.findOne(consumption.getParentId());
					parentConsumption.setMoney(NumUtils.sub(parentConsumption.getMoney(), consumption.getMoney()));
					dao.save(parentConsumption);
				}
				break;
			case 2:
				break;
			case 3:
				flag = false;
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				flag = false;
				break;
			}
			if (flag && consumption.getCustomId() == null) {
				Custom custom = new Custom();
				custom.setName(consumption.getCustomerName());
				custom.setType(consumption.getType());
				customDao.save(custom);
				consumption.setCustomId(custom.getId());
			}
			dao.save(consumption);
		}
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
						List<Consumption> consumptionList= dao.findByParentId(id);
						consumptionList.stream().forEach(co->co.setParentId(null));
						dao.save(consumptionList);
						dao.delete(id);
						count++;
					} else {
						throw new ServiceException(consumption.getContent() + "已经审核放款无法删除");
					}

				}
			}
		}
		return count;
	}

	@Override
	public int auditConsumption(String ids, Integer flag) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Consumption consumption = dao.findOne(id);
					if (consumption.getPaymentDate() == null && consumption.getPaymentMoney() == null) {
						throw new ServiceException("返款金额或放款时间不能为空");
					}
					consumption.setFlag(flag);
					dao.save(consumption);
					count++;
				}
			}
		}
		return count;
	}

}
