package com.bluewhite.finance.ledger.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.ledger.dao.ContactDao;
import com.bluewhite.finance.ledger.dao.CustomerDao;
import com.bluewhite.finance.ledger.dao.OrderDao;
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.finance.ledger.entity.Customer;
import com.bluewhite.finance.ledger.entity.Order;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private ContactDao contactDao;
	@Override
	public PageResult<Order> findPages(Order param, PageParameter page) {
		if (!StringUtils.isEmpty(param.getContractTime())) {
			Sort sort = new Sort(Direction.DESC, "createdAt");
			page.setSort(sort);
		}
		Page<Order> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}

			if (!StringUtils.isEmpty(param.getContractTime())) {

				predicate.add(cb.between(root.get("contractTime").as(Date.class),
						DatesUtil.getFirstDayOfMonth(param.getContractTime()),
						DatesUtil.getLastDayOfMonth(param.getContractTime())));
			}
			Predicate[] pre = new Predicate[predicate.size()];

			query.where(predicate.toArray(pre));

			return null;
		}, page);
		PageResult<Order> result = new PageResult<Order>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public void addOrder(Order order) {
		order.setPrice(NumUtils.setzro(order.getPrice()));
		order.setContractNumber(NumUtils.setzro(order.getContractNumber()));
		order.setAshoreNumber(NumUtils.setzro(order.getAshoreNumber()));
		order.setDisputeNumber(NumUtils.setzro(order.getDisputeNumber()));
		List<Order> orderList = this.findPages(order, new PageParameter()).getRows();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		String w = dateFormat.format(order.getContractTime());
		if (orderList.size() > 0) {
			order.setSalesNumber(w + "-" + "#" + (orderList.size() + 1));
		} else {
			order.setSalesNumber(w + "-" + "#" + "1");
		}
		order.setContractPrice(order.getContractNumber() * order.getPrice());
		
		if (order.getId() != null && order.getPrice()!=0) {
			List<Customer> customerList = customerDao
					.findByCusProductNameAndCusPartyNames(order.getProductName().trim(), order.getPartyNames().trim());
			if (customerList.size() > 0) {
				for (Customer customer2 : customerList) {
					customer2.setCusPrice(order.getPrice());
				}
				customerDao.save(customerList);
			} else {
				Customer customer = new Customer();
				customer.setCusPartyNames(order.getPartyNames());
				customer.setCusProductName(order.getProductName());
				customer.setCusPrice(order.getPrice());
				customerDao.save(customer);
			}
		}
		if(order.getId() != null && order.getAshoreNumber()!=null){
			order.setRoadNumber(order.getContractNumber()-order.getAshoreNumber()-order.getDisputeNumber());
		}
		Contact contact=null;
		if(order.getPartyNamesId()==null){
			contact=new Contact();
			contact.setConPartyNames(order.getPartyNames());
			contactDao.save(contact);
			order.setPartyNamesId(contact.getId());
		}
		dao.save(order);
	}

	@Override
	@Transactional
	public void deleteOrder(String ids) {
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
