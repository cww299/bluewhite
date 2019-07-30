package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.PackingChildDao;
import com.bluewhite.ledger.dao.PackingDao;
import com.bluewhite.ledger.dao.SendGoodsDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.SendGoods;

@Service
public class PackingServiceImpl extends BaseServiceImpl<Packing, Long> implements PackingService {

	@Autowired
	private PackingDao dao;
	@Autowired
	private SendGoodsDao sendGoodsDao;
	@Autowired
	private PackingChildDao packingChildDao;
	@Autowired
	private OrderDao orderDao;
	

	@Override
	public PageResult<Packing> findPages(Packing param, PageParameter page) {
		Page<Packing> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customerName").as(String.class), "%" + param.getCustomerName() + "%"));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate
						.add(cb.equal(root.get("packingChilds").get("productId").as(Long.class), param.getProductId()));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("packingChilds").get("bacthNumber").as(String.class),
						"%" + param.getBacthNumber() + "%"));
			}
			// 按发货贴包日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("packingDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Packing> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public String getPackingNumber() {
		Calendar now = Calendar.getInstance();
		List<Packing> PackingList = dao.findByPackingDate(now.getTime());
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month =  String.valueOf(now.get(Calendar.MONTH) + 1);
		String day =  String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		String yearString = year.substring(year.length() -2,year.length());   //截取最后两位
		String packingNumber = yearString+"N"+month+"Y"+day+"R" +(PackingList.size()+1)+"D";
		return packingNumber;
	}

	@Override
	public Packing addPacking(Packing packing) {	            
		packing.setPackingDate(packing.getPackingDate()!=null ? packing.getPackingDate() : new Date());
		// 新增子单
		if (!StringUtils.isEmpty(packing.getChildPacking())) { 
			JSONArray jsonArray = JSON.parseArray(packing.getChildPacking());
			for (int i = 0; i < jsonArray.size(); i++) {
				PackingChild packingChild = new PackingChild();
				packingChild.setFlag(0);
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				packingChild.setBacthNumber(jsonObject.getString("bacthNumber"));
				packingChild.setProductId(jsonObject.getLong("productId"));
				packingChild.setSendGoodsId(jsonObject.getLong("sendGoodsId"));
				packingChild.setCount(jsonObject.getInteger("count"));
				SendGoods sendGoods = sendGoodsDao.findOne(jsonObject.getLong("sendGoodsId"));
				sendGoods.setSendNumber(sendGoods.getNumber()+packingChild.getCount());
				sendGoodsDao.save(sendGoods);
				packing.getPackingChilds().add(packingChild);
			}
		}
		dao.save(packing);
 		return packing;
	}

	@Override
	public int sendPacking(String ids,Date time) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) { 
			String [] idStrings = ids.split(",");
			for(String id : idStrings){
				Long idLong = Long.valueOf(id);
				Packing packing = dao.findOne(idLong);
				List<PackingChild> packingChildList = packing.getPackingChilds();
				for(PackingChild pc : packingChildList){
					pc.setSendDate(time);
					pc.setFlag(1);
					//判定是否拥有版权
					if(pc.getProduct().getName().contains(Constants.LX) 
							||pc.getProduct().getName().contains(Constants.KT)
							||pc.getProduct().getName().contains(Constants.MW)
							||pc.getProduct().getName().contains(Constants.BM)
							||pc.getProduct().getName().contains(Constants.LP)
							||pc.getProduct().getName().contains(Constants.AB)
							||pc.getProduct().getName().contains(Constants.ZMJ)
							||pc.getProduct().getName().contains(Constants.XXYJN)){
						pc.setCopyright(true);	
					}
					//判定是否更换客户发货，更换客户发货变成新批次，->Y
					Order order = orderDao.findByBacthNumber(pc.getBacthNumber());
					if(order.getCustomerId() != pc.getPacking().getCustomerId()){
						
						
					}
				}
				
				
				dao.save(packing);
				count++;
			}
		}
		return count;
	}

	@Override
	public PageResult<PackingChild> findPackingChildPage(PackingChild param, PageParameter page) {
		Page<PackingChild> pages = packingChildDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("packingChilds").get("bacthNumber").as(String.class),
						"%" + param.getBacthNumber() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<PackingChild> result = new PageResult<>(pages, page);
		return result;
	}
}
