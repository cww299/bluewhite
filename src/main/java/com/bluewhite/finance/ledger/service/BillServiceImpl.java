package com.bluewhite.finance.ledger.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.ledger.dao.BillDao;
import com.bluewhite.finance.ledger.dao.OrderDao;
import com.bluewhite.finance.ledger.entity.Bill;
import com.bluewhite.finance.ledger.entity.Order;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.entity.NonLine;
@Service
public class BillServiceImpl extends BaseServiceImpl<Bill, Long> implements BillService{

	@Autowired
	private BillDao dao;
	@Autowired
	private OrderDao orderdao;
	
	@Override
	public PageResult<Bill> findPages(Bill param, PageParameter page) {
		
		Page<Bill> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			
			// 按id过滤
			if (param.getPartyNamesId() != null) {
				predicate.add(cb.equal(root.get("partyNamesId").as(Long.class), param.getPartyNamesId()));
			}
			
			//按姓名查找
			if (!StringUtils.isEmpty(param.getPartyNames())) {
				predicate.add(cb.like(root.get("partyNames").as(String.class),"%" + param.getPartyNames() + "%"));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Bill> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public Bill addBill(Order order) {
		Bill bill = dao.findByPartyNamesIdAndBillDateBetween(order.getPartyNamesId(),DatesUtil.getFirstDayOfMonth(order.getContractTime()),	DatesUtil.getLastDayOfMonth(order.getContractTime()));
		if(bill==null){
			bill = new Bill();
			bill.setPartyNames(order.getPartyNames());
			bill.setPartyNamesId(order.getPartyNamesId());
			bill.setBillDate(order.getContractTime());
		}
		List<Order> orderList = orderdao.findByPartyNamesIdAndContractTimeBetween(order.getPartyNamesId(),DatesUtil.getFirstDayOfMonth(order.getContractTime()),DatesUtil.getLastDayOfMonth(order.getContractTime()));
		double	OffshorePay = orderList.stream().filter(Order->Order.getPartyNamesId()==order.getPartyNamesId()).mapToDouble(Order::getContractPrice).sum();
		bill.setOffshorePay(OffshorePay);
		
		
		return bill;
	}
	
	

	@Override
	public Object addBillDate(Long id, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");   
		long size = 0;
		try {
			size = DatesUtil.getDaySub(DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth( format.parse(date))),DatesUtil.getLastDayOftime(DatesUtil.getLastDayOfMonth( format.parse(date))));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		JSONObject outData = new JSONObject();
		JSONArray gResTable = new JSONArray(); 
		Date beginTimes = null;
		Bill bill = dao.findOne(id);
		//当货款不为null时，将数据取出，返回前端
		if(bill.getDateToPay()!=null){
			JSONObject jsonObj = JSONObject.parseObject(bill.getDateToPay());
			JSONArray on = jsonObj.getJSONArray("data");
			for (int i = 0; i < on.size(); i++) {
				JSONObject jo = on.getJSONObject(i); 
		         String value =  jo.getString("name");
		         try {
					if(DatesUtil.equalsDate(format.parse(value), format.parse(date))){
						gResTable.add(jo);
					 }
				} catch (ParseException e) {
				}
			}
			outData.put("data", gResTable);
			
			//当货款为null时，填充无数据json格式返回
		}
		if(bill.getDateToPay()==null || gResTable.size()==0){
			for(int j=0 ; j<size ; j++){
				if(j!=0){
					//获取下一天的时间
					beginTimes = DatesUtil.nextDay(beginTimes);
				}else{
					//获取第一天的开始时间
					try {
						beginTimes = DatesUtil.getfristDayOftime(DatesUtil.getFirstDayOfMonth(format.parse(date)));
					} catch (ParseException e) {
					}
				}
				JSONObject name = new JSONObject(); 
				name.put("name",sdf.format(beginTimes));
				name.put("value","");
				name.put("price","");
				gResTable.add(name);
				
				//当月货款字段中没有当月json数据是，将当月json数据拼接到，原货款数据中
				if(bill.getDateToPay()!=null){
					JSONObject jsonObj = JSONObject.parseObject(bill.getDateToPay());
					JSONArray on = jsonObj.getJSONArray("data");
					on.add(name);
					JSONObject data = new JSONObject();
					data.put("data", on);
					bill.setDateToPay(JSONObject.toJSONString(data));
					dao.save(bill);
				}
				
			}
			outData.put("data", gResTable);
		}
		return outData;
		
		
	}

	@Override
	public Bill updateBill(Bill bill) {
		Bill bl = dao.findOne(bill.getId());
		bl.setDateToPay(bill.getDateToPay());
//		总货款
		Double arrivalPay = 0.0;
		if(bl.getDateToPay()!=null){
			JSONObject jsonObj = JSONObject.parseObject(bl.getDateToPay());
			JSONArray on = jsonObj.getJSONArray("data");
			for (int i = 0; i < on.size(); i++) {
				JSONObject jo = on.getJSONObject(i); 
		         String price =  jo.getString("price");
		         price = price.equals("") ? "0.0" : price;  
		         arrivalPay+=Double.parseDouble(price);
			}
		}
		//当月货款已到
		bl.setArrivalPay(arrivalPay);
		//当月货款未到
		bl.setNonArrivalPay(bl.getAcceptPay()+bl.getAcceptPayable()-arrivalPay);
		//当月客户多付货款转下月应付
		bl.setOverpaymentPay(bl.getNonArrivalPay()<0 ?bl.getNonArrivalPay() :0.0);
		return dao.save(bl);
	}

}
