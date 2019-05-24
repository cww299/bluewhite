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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderChildDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.ProcurementChild;
import com.bluewhite.onlineretailers.inventory.entity.poi.OnlineOrderPoi;

@Service
public class OnlineOrderServiceImpl extends BaseServiceImpl<OnlineOrder, Long> implements  OnlineOrderService{
	
	@Autowired
	private OnlineOrderDao dao;
	@Autowired
	private CommodityDao commodityDao;
	@Autowired
	private OnlineOrderChildDao  onlineOrderChildDao;
	@Autowired
	private InventoryDao inventoryDao;
	

	@Override
	public PageResult<OnlineOrder> findPage(OnlineOrder param, PageParameter page) {
		 Page<OnlineOrder> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	
	        	//按客服id过滤
	        	if (param.getUserId() != null) {
					predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
				}
	        	//按客户id过滤
	        	if (param.getOnlineCustomerId() != null) {
					predicate.add(cb.equal(root.get("onlineCustomerId").as(Long.class),param.getOnlineCustomerId()));
				}
	        	//交易状态过滤
	        	if (!StringUtils.isEmpty(param.getStatus())) {
					predicate.add(cb.equal(root.get("status").as(String.class),param.getStatus()));
				}
	        	//按物流方式
	        	if (!StringUtils.isEmpty(param.getShippingType())) {
					predicate.add(cb.equal(root.get("shippingType").as(String.class),param.getShippingType()));
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
					Long id = Long.valueOf(idString);
					OnlineOrder onlineOrder = dao.findOne(id);
					for(OnlineOrderChild onlineOrderChild : onlineOrder.getOnlineOrderChilds()){
						//当订单的状态是已发货
						if(onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_5)){
							//获取商品
							Commodity commodity = onlineOrderChild.getCommodity();
							//获取所有商品的库存
							Set<Inventory> inventorys = commodity.getInventorys();
							//减少库存的同时改变状态
							if(inventorys.size()>0){
								for(Inventory inventory : inventorys){
									if(inventory.getWarehouseId().equals(onlineOrderChild.getWarehouseId())){
										inventory.setNumber(inventory.getNumber()-onlineOrderChild.getNumber());
										inventoryDao.save(inventory);
										onlineOrderChild.setStatus(Constants.ONLINEORDER_4);
									}
								}
							}	
						} 
					}
					dao.save(onlineOrder);
					onlineOrder.setFlag(1);
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
				onlineOrderChild.setStatus(jsonObject.getString("status"));
				onlineOrderChild.setOnlineOrderId(onlineOrder.getId());
				onlineOrder.getOnlineOrderChilds().add(onlineOrderChild);
			}
		}
		//总数量
		onlineOrder.setNum(onlineOrder.getOnlineOrderChilds().stream().mapToInt(OnlineOrderChild::getNumber).sum());
		onlineOrder.setFlag(0);
		return dao.save(onlineOrder);
	}


	@Override
	public int delivery(String delivery) {
		int count =0;
		if(!StringUtils.isEmpty(delivery)){
			JSONArray jsonArray = JSON.parseArray(delivery);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Long id = jsonObject.getLong("id");
				Long warehouseId = jsonObject.getLong("warehouseId");
				int number = jsonObject.getIntValue("number");
				//获取子订单
				OnlineOrderChild onlineOrderChild = onlineOrderChildDao.findOne(id);
				//当订单的状态是买家已付款时
				if(onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_4)){
					//获取商品
					Commodity commodity = onlineOrderChild.getCommodity();
					//获取所有商品的库存
					Set<Inventory> inventorys = commodity.getInventorys();
					if(inventorys.size()==0){
						throw new ServiceException(commodity.getName()+"没有任何库存,无法出库");
					}
					//减少库存的同时改变状态
					if(inventorys.size()>0){
						for(Inventory inventory : inventorys){
							if(inventory.getWarehouseId().equals(warehouseId)){
								inventory.setNumber(inventory.getNumber()-number);
								inventoryDao.save(inventory);
								onlineOrderChild.setStatus(Constants.ONLINEORDER_5);
								onlineOrderChildDao.save(onlineOrderChild);
							}
						}
					}	
				} 
				count++;
			}
		}
		return count;
	}


	@Override
	public int excelOnlineOrder(ExcelListener excelListener) {
		int count = 0;
		//获取导入的订单
		List<Object> excelListenerList = excelListener.getData();
		List<OnlineOrder> onlineOrderList = new ArrayList<>();
		List<Procurement> procurementList = new ArrayList<>();
		for(int i = 0; i < excelListenerList.size(); i++){
			OnlineOrder onlineOrder = null;
			Procurement procurement = null;
			OnlineOrderPoi  cPoi = (OnlineOrderPoi)excelListenerList.get(i);
			if(cPoi.getDocumentNumber()!=null){
				onlineOrder = new OnlineOrder();
				procurement = new Procurement();
				procurement.setType(3);
				onlineOrder.setDocumentNumber(cPoi.getDocumentNumber());
				onlineOrder.setStatus(Constants.ONLINEORDER_5);
				onlineOrder.setName(cPoi.getName());
				onlineOrder.setPayment(cPoi.getPayment());
				onlineOrder.setPostFee(cPoi.getPostFee());
				onlineOrder.setAllBillPreferential(cPoi.getAllBillPreferential());
				onlineOrder.setSumPrice(cPoi.getSumPrice());
				onlineOrder.setBuyerName(cPoi.getBuyerName());
				onlineOrder.setAddress(cPoi.getAddress());
				onlineOrder.setPhone(cPoi.getPhone());
				onlineOrder.setBuyerMessage(cPoi.getBuyerMessage());
				onlineOrder.setTrackingNumber(cPoi.getTrackingNumber());
			}
				List<OnlineOrderChild> onlineOrderChilds = onlineOrder.getOnlineOrderChilds();
				//创建子订单
				OnlineOrderChild onlineOrderChild = new OnlineOrderChild();
				onlineOrder.setFlag(0);
				onlineOrderChild.setStatus(Constants.ONLINEORDER_5);
				onlineOrderChild.setOnlineOrder(onlineOrder);
				onlineOrderChild.setPrice(cPoi.getPrice());
				onlineOrderChild.setNumber(cPoi.getNumber());
				onlineOrderChild.setWarehouseId(cPoi.getWarehouseId());
				onlineOrderChild.setSumPrice(NumUtils.mul(onlineOrderChild.getPrice(), onlineOrderChild.getNumber()));
				if(cPoi.getCommodityName()!=null){
					Commodity commodity = commodityDao.findByName(cPoi.getCommodityName());
					if(commodity!=null){
						onlineOrderChild.setCommodityId(commodity.getId());
						ProcurementChild procurementChild = new ProcurementChild();
						procurementChild.setCommodityId(commodity.getId());
						procurementChild.setNumber(cPoi.getNumber());
						procurementChild.setWarehouseId(cPoi.getWarehouseId());
						//获取所有商品的库存
						Set<Inventory> inventorys = commodity.getInventorys();
						//出库单
						if(procurement.getType()==3){
							if(inventorys.size()==0){
								throw new ServiceException(commodity.getName()+"没有任何库存,无法出库");
							}
							//减少库存
							if(inventorys.size()>0){
								for(Inventory inventory : inventorys){
									if(inventory.getWarehouseId().equals(procurementChild.getWarehouseId())){
										inventory.setNumber(inventory.getNumber()-procurementChild.getNumber());
									}
								}
							}
						}
					}else{
						throw new ServiceException("当前导入excel第"+(i+2)+"条数据的商品不存在，请先添加");
					}
					
				}
				onlineOrderChilds.add(onlineOrderChild);
				//当下一条数据没有订单编号时,自动存储上面所有的父子订单
				OnlineOrderPoi onlineOrderPoiNext = (OnlineOrderPoi)excelListenerList.get(i+1);
				if(onlineOrderPoiNext.getDocumentNumber()!=null){
					onlineOrderList.add(onlineOrder);
					procurementList.add(procurement);
				}
				
		}
		dao.save(onlineOrderList);
		return count;
	}


}
