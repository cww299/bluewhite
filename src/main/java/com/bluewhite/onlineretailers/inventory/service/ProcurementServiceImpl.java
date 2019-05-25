package com.bluewhite.onlineretailers.inventory.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.ProcurementChild;

@Service
public class ProcurementServiceImpl extends BaseServiceImpl<Procurement, Long> implements ProcurementService {

	@Autowired
	private ProcurementDao dao;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private InventoryDao inventoryDao;

	@Override
	public PageResult<Procurement> findPage(Procurement param, PageParameter page) {
		Page<Procurement> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}

			// 按单据类型过滤
			if (param.getType()!=null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}
			
			// 按批次号过滤
			if (!StringUtils.isEmpty(param.getBatchNumber())) {
				predicate.add(cb.equal(root.get("b atchNumber").as(Integer.class), param.getBatchNumber()));
			}
			
			//按单据生产时间过滤
    		if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
    			predicate.add(cb.between(root.get("createdAt").as(Date.class),
    					param.getOrderTimeBegin(),
    					param.getOrderTimeEnd()));
    		}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);

		PageResult<Procurement> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public Procurement saveProcurement(Procurement procurement) {
		// 逻辑处理：优先处理父级单据所有数据
		Procurement upProcurement = new Procurement();
		if (procurement.getId() != null) {  
			// 查找是否已经拥有上级单据id的单据
			upProcurement = dao.findByParentId(procurement.getId());
			// 当单据为null，说明是新增，否则是修改
			if (upProcurement == null) { 
				upProcurement = new Procurement();
				// 将 转换的单据id变成新单据的父id
				upProcurement.setParentId(procurement.getId());
				// 获取到上一级单据的数据
				Procurement oldProcurement = dao.findOne(procurement.getId());
				upProcurement.setBatchNumber(oldProcurement.getBatchNumber());
				upProcurement.setType(procurement.getType());
				upProcurement.setNumber(procurement.getNumber());
				upProcurement.setResidueNumber(procurement.getNumber());
				// 将上级单据的剩余总数改变
				oldProcurement.setResidueNumber(oldProcurement.getResidueNumber() - procurement.getNumber());
			} else {
				// 表示是同一个上级转化的针工单，此时更新剩余数量和总数
				upProcurement.setNumber(upProcurement.getNumber() + procurement.getNumber());
				upProcurement.setResidueNumber(upProcurement.getResidueNumber() + procurement.getNumber());
			}
		} else {
			upProcurement = procurement;
			upProcurement.setResidueNumber(upProcurement.getNumber());
		}

		// 创建子单据
		if (!StringUtils.isEmpty(procurement.getCommodityNumber())) {
			JSONArray jsonArray = JSON.parseArray(procurement.getCommodityNumber());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ProcurementChild procurementChild = new ProcurementChild();
				procurementChild.setCommodityId(jsonObject.getLong("commodityId"));
				procurementChild.setNumber(jsonObject.getIntValue("number"));
				procurementChild.setChildRemark(jsonObject.getString("childRemark"));
				// 表示拥有上一阶段的单据，减少上一次单据的子单数量
				if (upProcurement.getParentId() != null) {
					Procurement parentProcurement = findOne(upProcurement.getParentId());
					// 减少子单数量
					for (ProcurementChild pChild : parentProcurement.getProcurementChilds()) {
						if (pChild.getCommodityId() == procurementChild.getCommodityId()) {
							pChild.setResidueNumber(pChild.getResidueNumber() - procurementChild.getNumber());
							if (procurement.getType() == 2) {
								pChild.setResidueNumber((pChild.getResidueNumber() - procurementChild.getNumber()) < 0
										? 0 : pChild.getResidueNumber() - procurementChild.getNumber());
							}
						}
					}
					//更新上级单据
					dao.save(parentProcurement);

					// 当单据为入库单时,针工单转化数量不够自动变成0
					if (procurement.getType() == 2) {
						Commodity commodity = commodityService.findOne(procurementChild.getCommodityId());
						procurementChild.setWarehouseId(jsonObject.getLong("warehouseId"));
						procurementChild.setStatus(4);
						// 创建商品的库存
						Set<Inventory> inventorys = commodity.getInventorys();
						Inventory inventory = new Inventory();
						inventory.setPlace(jsonObject.getString("place"));
						inventory.setCommodityId(procurementChild.getCommodityId());
						inventory.setNumber(procurementChild.getNumber());
						inventory.setWarehouseId(procurementChild.getWarehouseId());
						inventorys.add(inventory);
						commodity.setInventorys(inventorys);
						commodityService.save(commodity);
					}

					// 出库单
					if (procurement.getType() == 3) {
						Commodity commodity = commodityService.findOne(procurementChild.getCommodityId());
						Set<Inventory> inventorys = commodity.getInventorys();
						if (inventorys.size() == 0) {
							throw new ServiceException(commodity.getName() + "没有任何库存,无法出库");
						}
						// 减少库存
						if (inventorys.size() > 0) {
							for (Inventory inventory : inventorys) {
								if (inventory.getWarehouseId().equals(procurementChild.getWarehouseId())) {
									inventory.setNumber(inventory.getNumber() - procurementChild.getNumber());
								}
							}
						}
						commodityService.save(commodity);
					}
				}
				// 将子单放入父单
				upProcurement.getProcurementChilds().add(procurementChild);
				upProcurement.setNumber(upProcurement.getProcurementChilds().stream().mapToInt(ProcurementChild::getNumber).sum());
			}
		}
		return dao.save(upProcurement);
	}

	@Override
	public int deleteProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) { 
			String[] pers = ids.split(",");
			if (pers.length > 0) {
				for (String idString : pers) {
					Long id = Long.valueOf(idString);
					Procurement procurement = dao.findOne(id);
					procurement.setFlag(1);
					// 当单据的父id存在，说明拥有上级单据，反冲恢复上级单据的总剩余数量
					if (procurement.getParentId() != null) {
						Procurement parentProcurement = dao.findOne(procurement.getParentId());
						parentProcurement.setResidueNumber(parentProcurement.getNumber());
						// 将上级子单数据恢复
						for (ProcurementChild procurementChilds : parentProcurement.getProcurementChilds()) {
							procurementChilds.setResidueNumber(procurementChilds.getNumber());
						}
					}

					// 当单据为出库入库单时，恢复库存
					for (ProcurementChild procurementChilds : procurement.getProcurementChilds()) {
						// 获取商品
						Commodity commodity = procurementChilds.getCommodity();
						// 获取所有商品的库存
						Set<Inventory> inventorys = commodity.getInventorys();
						// 反冲库存数据
						if (inventorys.size() > 0) {
							for (Inventory inventory : inventorys) {
								if (inventory.getWarehouseId().equals(procurementChilds.getWarehouseId())) {
									// 入库单
									if (procurement.getType() == 2) {
										inventory.setNumber(inventory.getNumber() - procurementChilds.getNumber());
									}
									// 出库单
									if (procurement.getType() == 3) {
										inventory.setNumber(inventory.getNumber() + procurementChilds.getNumber());
									}
									inventoryDao.save(inventory);
								}
							}
						}
						commodityService.save(commodity);
					}
					dao.save(procurement);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public List<Procurement> findByTypeAndCreatedAt(int type, Date startTime, Date endTime) {
		return dao.findByTypeAndCreatedAtBetween(type,startTime,endTime);
	}

}
