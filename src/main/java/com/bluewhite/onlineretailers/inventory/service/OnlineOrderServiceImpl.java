package com.bluewhite.onlineretailers.inventory.service;

import java.util.ArrayList;
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
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;

@Service
public class OnlineOrderServiceImpl extends BaseServiceImpl<OnlineOrder, Long> implements  OnlineOrderService{
	
	@Autowired
	private OnlineOrderDao dao;
	
	@Autowired
	private CommodityDao commodityDao;
	

	@Override
	public PageResult<OnlineOrder> findPage(OnlineOrder param, PageParameter page) {
		 Page<OnlineOrder> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	
	        	//按编号过滤
	        	if (!StringUtils.isEmpty(param.getNum())) {
					predicate.add(cb.equal(root.get("num").as(String.class),param.getNum()));
				}
	        	
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);

	        PageResult<OnlineOrder> result = new PageResult<>(pages,page);
	        return result;
	    }


	@Override
	public int deleteOnlineOrder(String ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			String[] pers = ids.split(",");
			if(pers.length>0){
				for(String idString : pers){
					dao.delete(Long.valueOf(idString));
					count++;
				}
			}
		}
		return count;
	}


	@Override
	public OnlineOrder addOnlineOrder(OnlineOrder onlineOrder) {
		//新增子订单
		if(!StringUtils.isEmpty(onlineOrder.getChildOrder())){
			JSONArray jsonArray = JSON.parseArray(onlineOrder.getChildOrder());
			for (int i = 0; i < jsonArray.size(); i++) {
				OnlineOrderChild onlineOrderChild = new OnlineOrderChild();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				onlineOrderChild.setCommodityId(jsonObject.getLong("commodityId"));
				onlineOrderChild.setNumber(jsonObject.getInteger("number"));
				onlineOrderChild.setPrice(jsonObject.getDouble("price"));
				onlineOrderChild.setSumPrice(jsonObject.getDouble("sumPrice"));
				onlineOrderChild.setSystemPreferential(jsonObject.getDouble("systemPreferential"));
				onlineOrderChild.setSellerReadjustPrices(jsonObject.getDouble("sellerReadjustPrices"));
				onlineOrderChild.setActualSum(jsonObject.getDouble("actualSum"));
				onlineOrderChild.setOnlineOrderId(onlineOrder.getId());
				//当订单状态是下单，减少库存
				if(onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_4)){
					Commodity commodity = commodityDao.findOne(onlineOrderChild.getCommodityId());
					commodity.setQuantity(commodity.getQuantity()-onlineOrderChild.getNumber());
					commodityDao.save(commodity);
				}
				onlineOrder.getOnlineOrderChilds().add(onlineOrderChild);
			}
		}
		//总数量
		onlineOrder.setNum(onlineOrder.getOnlineOrderChilds().stream().mapToInt(OnlineOrderChild::getNumber).sum());
		return dao.save(onlineOrder);
	}


}
