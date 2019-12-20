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
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.CustomerDao;
import com.bluewhite.ledger.entity.Customer;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long> implements CustomerService {

	@Autowired
	private CustomerDao dao;

	@Override
	public PageResult<Customer> findPages(Customer param, PageParameter page) {
		Page<Customer> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按名称过滤
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getName()) + "%"));
			}
			// 按真实名称过滤
			if (!StringUtils.isEmpty(param.getBuyerName())) {
				predicate.add(cb.like(root.get("buyerName").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getBuyerName()) + "%"));
			}
			// 按手机号过滤
			if (!StringUtils.isEmpty(param.getPhone())) {
				predicate.add(cb.like(root.get("phone").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getPhone()) + "%"));
			}
			// 按等级过滤
			if (param.getGrade() != null) {
				predicate.add(cb.equal(root.get("grade").as(Integer.class), param.getGrade()));
			}
			// 按客户归属过滤
			if (param.getCustomerAttributionId() != null) {
				predicate.add(cb.equal(root.get("customerAttributionId").as(Long.class), param.getCustomerAttributionId()));
			}
			// 按客户类型过滤
			if (param.getCustomertypeId() != null) {
				predicate.add(cb.equal(root.get("customertypeId").as(Long.class), param.getCustomertypeId()));
			}
			// 所属业务员过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Customer> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteCustomr(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				Long idLong = Long.valueOf(id);
				try {
					dao.delete(idLong);
				} catch (Exception e) {
					throw new ServiceException("第"+(count+1)+"位客户存在数据关联，无法删除");
				}
				count++;
			}
		}
		return count;
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		if (customer.getId() != null) {
			Customer ot = dao.findOne(customer.getId());
			update(customer, ot, "");
		} else {
			if (dao.findByPhone(customer.getPhone()) != null) {
				throw new ServiceException("客户手机号已存在，请勿重复添加");
			}
			if (dao.findByName(customer.getName()) != null) {
				throw new ServiceException("客户真实姓名已存在，请勿重复添加");
			}
			dao.save(customer);
		}
	}

	@Override
	public List<Customer> getCustomer(Customer param) {
		List<Customer> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按名称过滤
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getName()) + "%"));
			}
			// 按真实名称过滤
			if (!StringUtils.isEmpty(param.getBuyerName())) {
				predicate.add(cb.like(root.get("buyerName").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getBuyerName()) + "%"));
			}
			// 按手机号过滤
			if (!StringUtils.isEmpty(param.getPhone())) {
				predicate.add(cb.like(root.get("phone").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getPhone()) + "%"));
			}
			// 按等级过滤
			if (param.getGrade() != null) {
				predicate.add(cb.equal(root.get("grade").as(Integer.class), param.getGrade()));
			}
			// 按经手人过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

}
