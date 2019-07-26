//package com.bluewhite.ledger.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.criteria.Predicate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.bluewhite.base.BaseServiceImpl;
//import com.bluewhite.common.entity.PageParameter;
//import com.bluewhite.common.entity.PageResult;
//import com.bluewhite.common.utils.DatesUtil;
//import com.bluewhite.common.utils.NumUtils;
//import com.bluewhite.ledger.dao.BillDao;
//import com.bluewhite.ledger.dao.OrderDao;
//import com.bluewhite.ledger.entity.Bill;
//import com.bluewhite.ledger.entity.Order;
//@Service
//public class BillServiceImpl extends BaseServiceImpl<Bill, Long> implements BillService{
//
//	@Autowired
//	private BillDao dao;
//	@Autowired
//	private OrderDao orderdao;
//
//
//	@Override
//	public PageResult<Bill> findPages(Bill param, PageParameter page) {
//		
//		Page<Bill> pages = dao.findAll((root, query, cb) -> {
//			List<Predicate> predicate = new ArrayList<>();
//			// 按id过滤
//			if (param.getId() != null) {
//				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
//			}
//			
//			// 按id过滤
//			if (param.getPartyNamesId() != null) {
//				predicate.add(cb.equal(root.get("partyNamesId").as(Long.class), param.getPartyNamesId()));
//			}
//			
//			//按姓名查找
//			if (!StringUtils.isEmpty(param.getPartyNames())) {
//				predicate.add(cb.like(root.get("partyNames").as(String.class),"%" + param.getPartyNames() + "%"));
//			}
//			//按账单日期
//			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
//    			predicate.add(cb.between(root.get("billDate").as(Date.class),
//    					param.getOrderTimeBegin(),
//    					param.getOrderTimeEnd()));
//    		}
//		
//			Predicate[] pre = new Predicate[predicate.size()];
//			query.where(predicate.toArray(pre));
//			return null;
//		}, page);
//		PageResult<Bill> result = new PageResult<>(pages, page);
//		return result;
//	}
//
//	@Override
//	public Bill addBill(Order order) {
//		List<Bill> billList = dao.findByPartyNamesIdAndBillDateBetween(order.getPartyNamesId(),DatesUtil.getFirstDayOfMonth(order.getContractTime()),	DatesUtil.getLastDayOfMonth(order.getContractTime()));
//		Bill bill = new Bill();
//		if(billList.size()>0){	
//			bill =  billList.get(0);
//		}else{
//			bill.setPartyNames(order.getPartyNames());
//			bill.setPartyNamesId(order.getPartyNamesId());
//			bill.setBillDate(order.getContractTime());
//		}
//		NumUtils.setzro(bill);
//		List<Order> orderList = orderdao.findByPartyNamesIdAndContractTimeBetween(order.getPartyNamesId(),DatesUtil.getFirstDayOfMonth(order.getContractTime()),DatesUtil.getLastDayOfMonth(order.getContractTime()));
//		double	OffshorePay = orderList.stream().filter(Order->Order.getPartyNamesId().equals(order.getPartyNamesId()) && Order.getContractPrice()!=null).mapToDouble(Order::getContractPrice).sum();
//		bill.setOffshorePay(NumUtils.round(OffshorePay,4));
//		double	acceptPay = orderList.stream().filter(Order->Order.getPartyNamesId().equals(order.getPartyNamesId()) && Order.getAshorePrice()!=null).mapToDouble(Order::getAshorePrice).sum();
//		bill.setAcceptPay(NumUtils.round(acceptPay,4));
//		//当表在途和有争议货款
//		bill.setDisputePay(NumUtils.sub(OffshorePay,acceptPay));
//		//当月货款未到
//		bill.setNonArrivalPay(NumUtils.sub(NumUtils.sum(bill.getAcceptPay(),bill.getAcceptPayable()),bill.getArrivalPay()));
//		//当月客户多付货款转下月应付
//		bill.setOverpaymentPay(bill.getNonArrivalPay()<0 ?bill.getNonArrivalPay() :0.0);
//		return dao.save(bill);
//	}
//	
//	
//	@Override
//	public Object getBillDate(Long id) {  
//		JSONObject outData = new JSONObject();
//		JSONArray gResTable = new JSONArray(); 
////		Bill bill = dao.findOne(id);
//		//当货款不为null时，将数据取出，返回前端
//		if(bill.getDateToPay()!=null){
//			JSONObject jsonObj = JSONObject.parseObject(bill.getDateToPay());
//			JSONArray on = jsonObj.getJSONArray("data");
//			for (int i = 0; i < on.size(); i++) {
//				JSONObject jo = on.getJSONObject(i); 
//		        gResTable.add(jo);	 	
//			}
//			outData.put("data", gResTable);
//		}
//		return outData;
//		
//		
//	}
//
//	@Override
//	public Bill updateBill(Bill bill) {
//		Bill bl = dao.findOne(bill.getId());
//		bl.setDateToPay(bill.getDateToPay());
//		NumUtils.setzro(bl);
////		总货款
//		Double arrivalPay = 0.0;
//		if(bl.getDateToPay()!=null){
//			JSONObject jsonObj = JSONObject.parseObject(bl.getDateToPay());
//			JSONArray on = jsonObj.getJSONArray("data");
//			for (int i = 0; i < on.size(); i++) {
//				JSONObject jo = on.getJSONObject(i); 
//		         String price =  jo.getString("price");
//		         price = price.equals("") ? "0.0" : price;  
//		         arrivalPay+=Double.parseDouble(price);
//			}
//		}
//		//当月货款已到
//		bl.setArrivalPay(NumUtils.round(arrivalPay,4));
//		//当月货款未到
//		bl.setNonArrivalPay(NumUtils.sub(NumUtils.sum(bl.getAcceptPay(),bl.getAcceptPayable()),NumUtils.round(arrivalPay,4)));
//		//当月客户多付货款转下月应付
//		bl.setOverpaymentPay(bl.getNonArrivalPay()<0 ?bl.getNonArrivalPay() :0.0);
//		return dao.save(bl);
//	}
//	
//
//	@Override
//	public Bill collectBill(Bill bill) {
//		PageParameter page = new PageParameter();
//		page.setSize(Integer.MAX_VALUE);
//		List<Bill> billList  = this.findPages(bill, page).getRows();
//		double	OffshorePay = billList.stream().mapToDouble(Bill::getOffshorePay).sum();
//		bill.setOffshorePay(OffshorePay);
//		double	acceptPay = billList.stream().mapToDouble(Bill::getAcceptPay).sum();
//		bill.setAcceptPay(acceptPay);
//		double	acceptPayable = billList.stream().mapToDouble(Bill::getAcceptPayable).sum();
//		bill.setAcceptPayable(acceptPayable);
//		return bill;
//	}
//
//}
