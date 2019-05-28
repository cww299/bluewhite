package com.bluewhite.onlineretailers.inventory.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
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
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}
			
			// 按状态过滤
			if (param.getStatus() != null) {
				predicate.add(cb.equal(root.get("status").as(Integer.class), param.getStatus()));
			}

			// 按是否反冲
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			
			// 按仓库id
			if (param.getWarehouseId() != null) {
				predicate.add(cb.equal(root.get("procurementChilds").get("warehouseId").as(Long.class), param.getWarehouseId()));
			}

			// 按批次号过滤
			if (!StringUtils.isEmpty(param.getBatchNumber())) {
				predicate.add(cb.equal(root.get("batchNumber").as(String.class), param.getBatchNumber()));
			}

			// 按单据生产时间过滤
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("createdAt").as(Date.class), param.getOrderTimeBegin(),
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
		// 获取到上一级单据的数据
		Procurement oldProcurement = null;
		if (procurement.getId() != null) {
			upProcurement = new Procurement();
			// 将 转换的单据id变成新单据的父id
			upProcurement.setParentId(procurement.getId());
			oldProcurement = dao.findOne(procurement.getId());
			upProcurement.setBatchNumber(oldProcurement.getBatchNumber());
			upProcurement.setType(procurement.getType());
			upProcurement.setNumber(procurement.getNumber());
			upProcurement.setResidueNumber(procurement.getNumber());
			upProcurement.setRemark(procurement.getRemark());
			// 将上级单据的剩余总数改变
			oldProcurement.setResidueNumber(oldProcurement.getResidueNumber() - procurement.getNumber());
		} else {
			upProcurement = procurement;
			upProcurement.setResidueNumber(upProcurement.getNumber());
			upProcurement.setFlag(0);
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
				procurementChild.setResidueNumber(jsonObject.getIntValue("number"));
				// 表示拥有上一阶段的单据，减少上一次单据的子单数量
				if (procurement.getId() != null) {
					// 减少子单数量
					for (ProcurementChild pChild : oldProcurement.getProcurementChilds()) {
						if (pChild.getCommodityId() == procurementChild.getCommodityId()) {
							pChild.setResidueNumber(pChild.getResidueNumber() - procurementChild.getNumber());
							// 当单据为入库单时,针工单转化数量不够自动变成0
							if (procurement.getType() == 2) {
								pChild.setResidueNumber((pChild.getResidueNumber() - procurementChild.getNumber()) < 0
										? 0 : pChild.getResidueNumber() - procurementChild.getNumber());
							}
						}
					}
					// 更新上级单据
					dao.save(oldProcurement);
				} else {
					procurementChild.setResidueNumber(jsonObject.getIntValue("number"));
				}

				if (procurement.getType() == 2) {
					procurementChild.setWarehouseId(jsonObject.getLong("warehouseId"));
					procurementChild.setPlace(jsonObject.getString("place"));
					Commodity commodity = commodityService.findOne(procurementChild.getCommodityId());
					if (procurement.getId() != null) {
						procurementChild.setStatus(4);
					} else {
						procurementChild.setStatus(jsonObject.getIntValue("status"));
					}
					// 创建商品的库存
					Set<Inventory> inventorys = commodity.getInventorys();
					// 获取库存
					Inventory inventory = inventoryDao.findByCommodityIdAndWarehouseId(
							jsonObject.getLong("commodityId"), jsonObject.getLong("warehouseId"));
					if (inventory == null) {
						inventory = new Inventory();
						inventory.setCommodityId(procurementChild.getCommodityId());
						inventory.setNumber(procurementChild.getNumber());
						inventory.setWarehouseId(procurementChild.getWarehouseId());
						inventorys.add(inventory);
						commodity.setInventorys(inventorys);
					} else {
						inventory.setNumber(inventory.getNumber() + procurementChild.getNumber());
					}
					commodityService.save(commodity);
				}

				// 出库单
				if (procurement.getType() == 3) {
					procurementChild.setWarehouseId(jsonObject.getLong("warehouseId"));
					procurementChild.setStatus(jsonObject.getIntValue("status"));
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

				// 将子单放入父单
				upProcurement.getProcurementChilds().add(procurementChild);
				upProcurement.setNumber(
						upProcurement.getProcurementChilds().stream().mapToInt(ProcurementChild::getNumber).sum());
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
						parentProcurement.setResidueNumber(procurement.getNumber());
						// 那本级的子单和上级子单对比，同时将上级子单数据恢复
						for (ProcurementChild parentProcurementChilds : parentProcurement.getProcurementChilds()) {
							for (ProcurementChild procurementChilds : procurement.getProcurementChilds()) {
								if (parentProcurementChilds.getCommodityId() == procurementChilds.getCommodityId()) {
									parentProcurementChilds.setResidueNumber(procurement.getNumber());
								}
							}
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
		return dao.findByTypeAndCreatedAtBetween(type, startTime, endTime);
	}

	@Override
	public List<Map<String, Object>> reportStorage(Procurement procurement) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		long size = 0;
		//按天查询
		if(procurement.getReport()==1){
			size =  DatesUtil.getDaySub(procurement.getOrderTimeBegin(), procurement.getOrderTimeEnd());
		}
		//按月查询
		if(procurement.getReport()==2){
			size =  1;
		}
		for (int i = 0; i < size; i++) {
			Date beginTimes = null;
			Date endTimes =  null;
			//按天查询
			if(procurement.getReport()==1){
				if (i != 0) {
					// 获取下一天的时间
					beginTimes = DatesUtil.nextDay(procurement.getOrderTimeBegin());
				} else {
					// 获取第一天的开始时间
					beginTimes = procurement.getOrderTimeBegin();
				}
				// 获取一天的结束时间
				endTimes = DatesUtil.getLastDayOftime(beginTimes);
			}
			//按月查询
			if(procurement.getReport()==2){
				beginTimes = procurement.getOrderTimeBegin();
				endTimes = procurement.getOrderTimeEnd();
			}
			Map<String, Object> mapSale = new HashMap<String, Object>();
			// 获取所有的单据
			List<Procurement> procurementList = dao.findByTypeAndCreatedAtBetween(procurement.getType(),
					beginTimes,endTimes);
			// 单数
			mapSale.put("singular", procurementList.size());
			// 宝贝数量
			int proNumber = procurementList.stream().mapToInt(Procurement::getNumber).sum();
			mapSale.put("proNumber", proNumber);
			mapList.add(mapSale);
		}
		return mapList;
	}
	
	

	@Override
	public List<Map<String, Object>> reportStorageGoods(Procurement procurement) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 获取所有的单据
		List<Procurement> procurementList = dao.findByTypeAndCreatedAtBetween(
				procurement.getType(), procurement.getOrderTimeBegin(), procurement.getOrderTimeEnd());
		// 将所有的子单数据取出
		List<ProcurementChild> procurementChildList = new ArrayList<>();
		// 将当前时间段所有的出库商品过滤出
		procurementList.stream().forEach(pt -> {
			procurementChildList.addAll(pt.getProcurementChilds());
		});
		//根据商品id分组
		Map<Long, List<ProcurementChild>> mapProcurementChildList = procurementChildList.stream()
				.collect(Collectors.groupingBy(ProcurementChild::getCommodityId, Collectors.toList()));
		for (Long ps : mapProcurementChildList.keySet()) {
			List<ProcurementChild> psList = mapProcurementChildList.get(ps);
			Map<String, Object> mapSale = new HashMap<String, Object>();
			int sunNumber = psList.stream().mapToInt(ProcurementChild::getNumber).sum();
			mapSale.put("name", psList.get(0).getCommodity().getSkuCode());
			mapSale.put("singular", psList.size());
			mapSale.put("sunNumber", sunNumber);
			mapList.add(mapSale);
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> reportStorageUser(Procurement procurement) {
		return null;
	}

}
