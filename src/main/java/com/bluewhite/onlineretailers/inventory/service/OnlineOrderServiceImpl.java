package com.bluewhite.onlineretailers.inventory.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;

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
		Set<Commodity> commoditySet = new HashSet<>();
		if(!StringUtils.isEmpty(onlineOrder.getCommoditysIds())){
			String[] pers = onlineOrder.getCommoditysIds().split(",");
			if(pers.length>0){
				for(String idString : pers){
					Commodity commodity = commodityDao.findOne(Long.valueOf(idString));
					commoditySet.add(commodity);
				}
			}
		}
		onlineOrder.setCommoditys(commoditySet);
		
		//当订单状态是下单，减少库存
		if(onlineOrder.getStatus().equals(Constants.ONLINEORDER_4)){
			
			
		}
		
		
		return dao.save(onlineOrder);
	}


}
