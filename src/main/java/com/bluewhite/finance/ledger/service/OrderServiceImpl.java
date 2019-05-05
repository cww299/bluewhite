package com.bluewhite.finance.ledger.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.ledger.dao.ContactDao;
import com.bluewhite.finance.ledger.dao.CustomerDao;
import com.bluewhite.finance.ledger.dao.OrderDao;
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.finance.ledger.entity.Customer;
import com.bluewhite.finance.ledger.entity.Order;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private BillService billService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductDao productDao;
	@PersistenceContext
	protected EntityManager entityManager;
	@Override
	public PageResult<Order> findPages(Order param, PageParameter page) {
		CurrentUser cu = SessionManager.getUserSession();
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
			//按登录账户查询
			/*if(cu.getOrgNameId()==10 || cu.getOrgNameId()==35){
				predicate.add(cb.equal(root.get("firstNamesId").as(Long.class), cu.getId()));
			}*/
			//按甲方Id过滤
			if (param.getFirstNamesId() != null) {
				predicate.add(cb.equal(root.get("firstNamesId").as(Long.class), param.getFirstNamesId()));
				predicate.add(cb.notEqual(root.get("price").as(Integer.class),0));
			}
			//按审核状态dispute
			if (param.getAshoreCheckr() != null) {
				predicate.add(cb.equal(root.get("ashoreCheckr").as(Integer.class), param.getAshoreCheckr()));
			}
			//按实战预算过滤
			if (param.getDispute() != null) {
				predicate.add(cb.equal(root.get("dispute").as(Integer.class), param.getDispute()));
			}
			//按争议状态
			if (param.getType() != null) {
				predicate.add(cb.isNotNull(root.get("disputeNumber").as(Integer.class)));
				predicate.add(cb.notEqual(root.get("disputeNumber").as(Integer.class),0));
			}
			//按甲方
        	if(!StringUtils.isEmpty(param.getFirstNames())){
        		predicate.add(cb.like(root.get("firstNames").as(String.class), "%"+param.getFirstNames()+"%"));
        	}
        	//按乙方
        	if(!StringUtils.isEmpty(param.getPartyNames())){
        		predicate.add(cb.like(root.get("partyNames").as(String.class), "%"+param.getPartyNames()+"%"));
        	}
        	//按产品名
        	if(!StringUtils.isEmpty(param.getProductName())){
        		predicate.add(cb.like(root.get("productName").as(String.class), "%"+StringUtil.specialStrKeyword(param.getProductName())+"%"));
        	}
        	//按批次号
        	if(!StringUtils.isEmpty(param.getBatchNumber())){
        		predicate.add(cb.like(root.get("batchNumber").as(String.class), "%"+param.getBatchNumber()+"%"));
        	}
        	//按合同签订日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
    			predicate.add(cb.between(root.get("contractTime").as(Date.class),
    					param.getOrderTimeBegin(),
    					param.getOrderTimeEnd()));
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

	@Transactional
	public String  Downloaded(){
		String ex = "";
		List<Order> list = dao.findAll();
		for (Order order2 : list) {
		if(order2.getProductName()!=null){
			try {
				Product product=productDao.findByNameAndOriginDepartmentIsNull(order2.getProductName());
				if(product!=null){
					order2.setProductId(product.getId());
				}
			} catch (Exception e) {
				ex+=order2.getProductName()+',';
			}
		}else{
			try {
				String[] y=order2.getProductName().split("（");
				Product product=productDao.findByNameAndOriginDepartmentIsNull(y[0]);
				if(product!=null){
					order2.setProductId(product.getId());
				}
			} catch (Exception e) {
				ex+=order2.getProductName()+',';
			}
			}
		}
		saveAll(list);
		return ex;
	}
	
	private void saveAll(List<Order> list) {
		entityManager.setFlushMode(FlushModeType.COMMIT);
		 for (int i = 0; i < list.size(); i++){
			 Order courtsResident = list.get(i);
			 entityManager.merge(courtsResident);
	            if (i % 1000 == 0 && i > 0) {
	            	entityManager.flush();
	            	entityManager.clear();
	            }
	        }
		 entityManager.close();
	    }	
	@Override
	public void addOrder(Order order) {
		order.setPrice(NumUtils.setzro(order.getPrice()));
		order.setContractNumber(NumUtils.setzro(order.getContractNumber()));
		order.setAshoreNumber(NumUtils.setzro(order.getAshoreNumber()));
		order.setDisputeNumber(NumUtils.setzro(order.getDisputeNumber()));
		 String test=order.getProductName();
		 if (test.indexOf("裸熊")!=-1 || test.indexOf("KT")!=-1 || test.indexOf("漫威")!=-1 || test.indexOf("老皮")!=-1 || test.indexOf("阿宝")!=-1 || test.indexOf("哔莫")!=-1){
			 order.setProductName(order.getProductName()+"(版权)");//当批产品名
	        }else{
	        order.setProductName(order.getProductName());//当批产品名
	        }
		if(order.getId()==null){
			Order order2 = new Order();
			order2.setOrderTimeBegin(DatesUtil.getFirstDayOfMonth(order.getContractTime()));
			order2.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(order.getContractTime()));
			List<Order> orderList = this.findPages(order2, new PageParameter()).getRows();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
				if (orderList.size() > 0) {
					int a = Integer.valueOf(orderList.get(0).getSalesNumber().substring(9).trim());
					order.setSalesNumber(dateFormat.format(order.getContractTime()) + "-" + "#" + (a+1));
				} else {
					order.setSalesNumber(dateFormat.format(order.getContractTime()) + "-" + "#" + "1");
				}
		}
		
		
		order.setContractPrice(NumUtils.mul(order.getContractNumber(),order.getPrice()));
		User user = userService.findOne(order.getFirstNamesId());
		if(user.getOrgNameId()!=null && user.getOrgNameId() != 10 && user.getOrgNameId() != 35){
			if (order.getId() != null && order.getPrice()!=0) {
				List<Customer> customerList = customerDao
						.findByCusProductNameLikeAndCusPartyNames(order.getProductName().trim(), order.getPartyNames().trim());
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
		}
		
		if(order.getId() != null && order.getAshoreNumber()!=null){
			order.setRoadNumber(order.getContractNumber()-order.getAshoreNumber()-order.getDisputeNumber());
			order.setAshorePrice(NumUtils.mul(order.getAshoreNumber(),order.getPrice()));
			dao.save(order);
			billService.addBill(order);
		}
		
		if(order.getPartyNamesId()==null){
			Contact contact = new Contact();
			contact.setConPartyNames(order.getPartyNames());
			contactDao.save(contact);
			order.setPartyNamesId(contact.getId());
		}
		
		dao.save(order);
	}

	
	@Override
	@Transactional
	public int deleteOrder(String ids) throws Exception {
		int count =0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Order order=dao.findOne(id);
					
					if(order.getAshoreNumber()==null || order.getAshoreNumber()==0){
						dao.delete(id);
						count++;
						Order order2=new Order();
						order2.setPartyNamesId(order.getPartyNamesId());
						order2.setContractTime(order.getContractTime());
						billService.addBill(order2);
					}else{
						throw new ServiceException("销售编号为"+order.getSalesNumber()+"的任务已填写到岸数量无法删除");
					}
					
				}
			}
		}
		return count;
		

	}


}
