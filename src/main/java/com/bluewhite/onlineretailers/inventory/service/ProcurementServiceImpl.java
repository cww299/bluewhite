package com.bluewhite.onlineretailers.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderChildDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderDao;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.ProcurementChild;
@Service
public class ProcurementServiceImpl extends BaseServiceImpl<Procurement, Long> implements  ProcurementService{
	
	@Autowired
	private ProcurementDao dao;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private  InventoryDao inventoryDao;
	

	@Override
	public PageResult<Procurement> findPage(Procurement param, PageParameter page) {
		 Page<Procurement> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	
	        	//按类型过滤
	        	if (!StringUtils.isEmpty(param.getType())) {
					predicate.add(cb.equal(root.get("type").as(Integer.class),param.getType()));
				}
	        	
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);

	        PageResult<Procurement> result = new PageResult<>(pages,page);
	        return result;
	    }

	@Override
	public Procurement saveProcurement(Procurement procurement) {
		if(!StringUtils.isEmpty(procurement.getCommodityNumber())){
			JSONArray jsonArray = JSON.parseArray(procurement.getCommodityNumber());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ProcurementChild procurementChild = new ProcurementChild();
				procurementChild.setCommodityId(jsonObject.getLong("commodityId"));
				procurementChild.setNumber(jsonObject.getIntValue("number"));
				procurementChild.setWarehouseId(jsonObject.getLong("warehouseId"));
				Commodity commodity = commodityService.findOne(procurementChild.getCommodityId());
				//获取所有商品的库存
				Set<Inventory> inventorys = commodity.getInventorys();
				//生产单
				if(procurement.getType()==0){
					
				}
				//针工单
				if(procurement.getType()==1){
					
				}
				//入库单
				if(procurement.getType()==2){
					//新增库存
					if(inventorys.size()>0){
						for(Inventory inventory : inventorys){
							if(inventory.getWarehouseId().equals(procurementChild.getWarehouseId())){
								inventory.setNumber(inventory.getNumber()+procurementChild.getNumber());
							}
						}
					}	
				}
				//出库单
				if(procurement.getType()==3){
					//减少库存
					if(inventorys.size()>0){
						for(Inventory inventory : inventorys){
							if(inventory.getWarehouseId().equals(procurementChild.getWarehouseId())){
								inventory.setNumber(inventory.getNumber()-procurementChild.getNumber());
							}
						}
					}
				}
				inventoryDao.save(inventorys);
			}
		}
		return dao.save(procurement);
	}

	@Override
	public int deleteProcurement(String ids) {
		// TODO Auto-generated method stub
		return 0;
	}

}
