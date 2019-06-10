package com.bluewhite.onlineretailers.inventory.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderChildDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderDao;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementChildDao;
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
	@Autowired
	private OnlineOrderDao onlineOrderDao;
	@Autowired
	private OnlineOrderChildDao onlineOrderChildDao;
	@Autowired
	private ProcurementChildDao procurementChildDao;

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
				Join<Procurement, ProcurementChild> join = root
						.join(root.getModel().getSet("procurementChilds", ProcurementChild.class), JoinType.LEFT);
				predicate.add(cb.equal(root.get("warehouseId").as(Long.class), param.getWarehouseId()));
			}

			// 按批次号过滤
			if (!StringUtils.isEmpty(param.getBatchNumber())) {
				Join<Procurement, ProcurementChild> join = root
						.join(root.getModel().getSet("procurementChilds", ProcurementChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("batchNumber").as(String.class), "%" + param.getBatchNumber() + "%"));
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
	@Transactional
	public Procurement saveProcurement(Procurement procurement) {
		procurement.setCreatedAt(procurement.getCreatedAt() == null ? new Date() : procurement.getCreatedAt());
		// 生成单据编号
		procurement.setDocumentNumber(StringUtil.getDocumentNumber(String.valueOf(procurement.getType()))
				+ SalesUtils.get0LeftString((int) dao.count(), 8));
		// 逻辑处理：优先处理父级单据所有数据
		Procurement upProcurement = new Procurement();
		// 获取到上一级单据的数据
		Procurement oldProcurement = null;
		if (procurement.getId() != null) {
			upProcurement = new Procurement();
			upProcurement.setFlag(0);
			// 将 转换的单据id变成新单据的父id
			upProcurement.setParentId(procurement.getId());
			oldProcurement = dao.findOne(procurement.getId());
			upProcurement.setType(procurement.getType());
			upProcurement.setNumber(procurement.getNumber());
			upProcurement.setResidueNumber(procurement.getNumber());
			upProcurement.setRemark(procurement.getRemark());
			upProcurement.setUserId(procurement.getUserId());
			upProcurement.setStatus(procurement.getStatus());
			upProcurement.setDocumentNumber(procurement.getDocumentNumber());
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
				procurementChild.setBatchNumber(jsonObject.getString("batchNumber"));
				procurementChild.setNumber(jsonObject.getIntValue("number"));
				procurementChild.setChildRemark(jsonObject.getString("childRemark"));
				procurementChild.setResidueNumber(jsonObject.getIntValue("number"));
				// 表示拥有上一阶段的单据，减少上一次单据的子单数量
				if (procurement.getId() != null) {
					// 减少子单数量
					for (ProcurementChild pChild : oldProcurement.getProcurementChilds()) {
						// 当前产品id和上一阶段产品id
						if (pChild.getCommodityId().equals(procurementChild.getCommodityId())) {
							// 当前批次和上一阶段批次
							if (pChild.getBatchNumber().equals(procurementChild.getBatchNumber())) {
								// 当单据为入库单时,针工单转化数量不够自动变成0
								if (procurement.getType() == 2) {
									pChild.setResidueNumber(
											(pChild.getResidueNumber() - procurementChild.getNumber()) < 0 ? 0
													: pChild.getResidueNumber() - procurementChild.getNumber());
								} else {
									pChild.setResidueNumber(pChild.getResidueNumber() - procurementChild.getNumber());
								}
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
					procurementChild.setStatus(jsonObject.getIntValue("status"));
					Commodity commodity = commodityService.findOne(procurementChild.getCommodityId());
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
	@Transactional
	public int deleteProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] pers = ids.split(",");
			if (pers.length > 0) {
				for (String idString : pers) {
					Long id = Long.valueOf(idString);
					Procurement procurement = dao.findOne(id);
					if (procurement.getFlag() == 1) {
						throw new ServiceException("该数据已经反冲，无法再次反冲");
					}
					procurement.setFlag(1);
					// 当单据的父id存在，说明拥有上级单据，反冲恢复上级单据的总剩余数量
					if (procurement.getParentId() != null) {
						Procurement parentProcurement = dao.findOne(procurement.getParentId());
						parentProcurement.setResidueNumber(procurement.getResidueNumber() + procurement.getNumber());
						// 拿本级的子单和上级子单对比，同时将上级子单数据恢复
						for (ProcurementChild parentProcurementChilds : parentProcurement.getProcurementChilds()) {
							for (ProcurementChild procurementChilds : procurement.getProcurementChilds()) {
								if (parentProcurementChilds.getCommodityId().equals(procurementChilds.getCommodityId()) 
										&& parentProcurementChilds.getBatchNumber().equals(procurementChilds.getBatchNumber())) {
									parentProcurementChilds.setResidueNumber(
											parentProcurementChilds.getResidueNumber() + procurementChilds.getNumber());
								}
							}
						}
						dao.save(parentProcurement);
					}

					if (procurement.getType() == 2 || procurement.getType() == 3) {
						// 当单据为出库入库单时，恢复库存
						for (ProcurementChild procurementChild : procurement.getProcurementChilds()) {
							// 获取商品
							Commodity commodity = procurementChild.getCommodity();
							// 获取商品库存
							Inventory inventory = inventoryDao.findByCommodityIdAndWarehouseId(commodity.getId(),
									procurementChild.getWarehouseId());
							// 增加库存的同时改变状态
							if (inventory != null) {
								// 入库单
								if (procurement.getType() == 2) {
									inventory.setNumber(inventory.getNumber() - procurementChild.getNumber());
								}
								// 出库单
								if (procurement.getType() == 3) {
									// 获取出库单出库数据的ids
									String[] idArr = procurementChild.getPutWarehouseIds().split(",");
									// 反冲入库单数据
									int residueNumber = procurementChild.getNumber();
									for (String idtoSting : idArr) {
										ProcurementChild procurementChildSale = procurementChildDao
												.findOne(Long.valueOf(idtoSting));
										if (procurementChildSale.getNumber()
												- procurementChildSale.getResidueNumber() < residueNumber) {
											residueNumber -= procurementChildSale.getNumber()
													- procurementChildSale.getResidueNumber();
											procurementChildSale.setResidueNumber(procurementChildSale.getNumber());
										} else {
											procurementChildSale.setResidueNumber(
													procurementChildSale.getResidueNumber() + residueNumber);
										}
										procurementChildDao.save(procurementChildSale);
									}

									// 当出库单类型是销售出库，同时反冲销售单的状态
									if (procurement.getStatus() == 0) {
										OnlineOrderChild onlineOrderChild = onlineOrderChildDao
												.findOne(procurementChild.getOnlineOrderId());
										onlineOrderChild.setStatus(Constants.ONLINEORDER_4);
										onlineOrderChildDao.save(onlineOrderChild);
										OnlineOrder onlineOrder = onlineOrderChild.getOnlineOrder();
										// 更新父订单的状态当所有的子订单反冲为等待发货更新为卖家为发货，否则是部分发货
										List<OnlineOrderChild> onlineOrderChildList = onlineOrder
												.getOnlineOrderChilds();
										int onlineOrderChildListCount = (int) onlineOrderChildList.stream()
												.filter(OnlineOrderChild -> OnlineOrderChild.getStatus()
														.equals(Constants.ONLINEORDER_4))
												.count();
										if (onlineOrderChildList.size() == onlineOrderChildListCount) {
											onlineOrder.setStatus(Constants.ONLINEORDER_4);
										} else {
											onlineOrder.setStatus(Constants.ONLINEORDER_3);
										}
									}

									// 反冲库存数据
									inventory.setNumber(inventory.getNumber() + procurementChild.getNumber());
								}
								inventoryDao.save(inventory);
							}
						}
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
		// 按天查询
		if (procurement.getReport() == 1) {
			size = DatesUtil.getDaySub(procurement.getOrderTimeBegin(), procurement.getOrderTimeEnd());
		}
		// 按月查询
		if (procurement.getReport() == 2) {
			procurement.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(procurement.getOrderTimeBegin()));
			size = 1;
		}
		// 开始时间
		Date beginTimes = procurement.getOrderTimeBegin();
		for (int i = 0; i < size; i++) {
			// 获取一天的结束时间
			Date endTimes = DatesUtil.getLastDayOftime(beginTimes);
			// 按月查询
			if (procurement.getReport() == 2) {
				beginTimes = procurement.getOrderTimeBegin();
				endTimes = procurement.getOrderTimeEnd();
			}
			Map<String, Object> mapSale = new HashMap<String, Object>();
			// 获取所有的单据
			List<Procurement> procurementList = dao.findByTypeAndCreatedAtBetween(procurement.getType(), beginTimes,
					endTimes);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (procurement.getReport() == 1) {
				// 时间
				mapSale.put("time", sdf.format(beginTimes));
			}
			if (procurement.getReport() == 2) {
				sdf = new SimpleDateFormat("yyyy-MM");
				// 时间
				mapSale.put("time", sdf.format(beginTimes));
			}
			// 单数
			mapSale.put("singular", procurementList.size());
			// 宝贝数量
			int proNumber = procurementList.stream().mapToInt(Procurement::getNumber).sum();
			mapSale.put("proNumber", proNumber);
			mapList.add(mapSale);
			beginTimes = DatesUtil.nextDay(beginTimes);
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> reportStorageGoods(Procurement procurement) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 获取所有的单据
		List<Procurement> procurementList = dao.findByTypeAndCreatedAtBetween(procurement.getType(),
				procurement.getOrderTimeBegin(), procurement.getOrderTimeEnd());
		// 将所有的子单数据取出
		List<ProcurementChild> procurementChildList = new ArrayList<>();
		// 将当前时间段所有的出库商品过滤出
		procurementList.stream().forEach(pt -> {
			procurementChildList.addAll(pt.getProcurementChilds());
		});
		// 根据商品id分组
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
