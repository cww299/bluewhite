package com.bluewhite.onlineretailers.inventory.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.ledger.entity.PackingChild;
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
import com.bluewhite.onlineretailers.inventory.entity.poi.OutProcurementPoi;

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
						.join(root.getModel().getList("procurementChilds", ProcurementChild.class), JoinType.LEFT);
				predicate.add(cb.equal(root.get("warehouseId").as(Long.class), param.getWarehouseId()));
			}
			//按单据编号
			if (!StringUtils.isEmpty(param.getDocumentNumber())) {
				predicate.add(cb.like(root.get("documentNumber").as(String.class),"%" +  param.getDocumentNumber()+ "%" ));
			}

			// 按批次号过滤
			if (!StringUtils.isEmpty(param.getBatchNumber())) {
				Join<Procurement, ProcurementChild> join = root
						.join(root.getModel().getList("procurementChilds", ProcurementChild.class), JoinType.LEFT);
				predicate.add(cb.equal(join.get("batchNumber").as(String.class), param.getBatchNumber()));
			}

			// 按商品名称过滤
			if (!StringUtils.isEmpty(param.getCommodityName())) {
				Join<Procurement, ProcurementChild> join = root
						.join(root.getModel().getList("procurementChilds", ProcurementChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("commodity").get("skuCode").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getCommodityName()) + "%"));
			}

			// 按单据生产时间过滤
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("createdAt").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			// 去重
			if (!StringUtils.isEmpty(param.getBatchNumber()) || !StringUtils.isEmpty(param.getCommodityName())) {
				query.distinct(true);
			}
			return null;
		}, page);
		PageResult<Procurement> result = new PageResult<>(pages, page);
		return result;
	}
	
	@Override
	public PageResult<ProcurementChild> findPages(ProcurementChild param, PageParameter page) {
		Page<ProcurementChild> pages = procurementChildDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}

			// 按单据类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("procurement").get("type").as(Integer.class), param.getType()));
			}

			// 按状态过滤
			if (param.getStatus() != null) {
				predicate.add(cb.equal(root.get("status").as(Integer.class), param.getStatus()));
			}

			// 按是否反冲
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("procurement").get("flag").as(Integer.class), param.getFlag()));
			}
			
			// 按批次
			if (param.getBatchNumber() != null) {
				predicate.add(cb.equal(root.get("batchNumber").as(String.class), param.getBatchNumber()));
			}

			//按单据编号
			if (!StringUtils.isEmpty(param.getDocumentNumber())) {
				predicate.add(cb.like(root.get("procurement").get("documentNumber").as(String.class),"%" +  param.getDocumentNumber()+ "%" ));
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
		PageResult<ProcurementChild> result = new PageResult<>(pages, page);
		return result;
	}
	

	@Override
	@Transactional
	public Procurement saveProcurement(Procurement procurement) {
		procurement.setCreatedAt(procurement.getCreatedAt() !=null ? procurement.getCreatedAt() : new Date());
		if(procurement.getOrderId()!=null){
			Procurement oProcurement = dao.findByOrderId(procurement.getOrderId());
			if(oProcurement!=null){
				throw new ServiceException("该生产单已存在，请勿重复添加");
			}
		}
		// 生成单据编号
		procurement.setDocumentNumber(StringUtil.getDocumentNumber(String.valueOf(procurement.getType())) + SalesUtils.get0LeftString((int) dao.count(), 8));
		procurement.setFlag(0);
		procurement.setResidueNumber(procurement.getNumber());
		// 创建子单据
		if (!StringUtils.isEmpty(procurement.getCommodityNumber())) {
			JSONArray jsonArray = JSON.parseArray(procurement.getCommodityNumber());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ProcurementChild procurementChild = new ProcurementChild();
				Commodity commodity = commodityService.findByProductId(jsonObject.getLong("productId"));
				procurementChild.setCommodityId(commodity.getId());
				procurementChild.setBatchNumber(jsonObject.getString("batchNumber"));
				procurementChild.setNumber(jsonObject.getIntValue("number"));
				procurementChild.setChildRemark(jsonObject.getString("childRemark"));
				procurementChild.setResidueNumber(jsonObject.getIntValue("number"));
				//获取转换的子单id
				procurementChild.setParentId(jsonObject.getLong("parentId"));
				// 表示拥有上一阶段的单据，减少上一次单据的子单数量
				if (procurementChild.getParentId() != null) {
					ProcurementChild procurementParentChild = procurementChildDao.findOne(procurementChild.getParentId());
					procurementParentChild.setResidueNumber(procurementParentChild.getResidueNumber()-procurementChild.getNumber());
					//同时减少父单的剩余数量
					procurementParentChild.getProcurement().setResidueNumber(procurementParentChild.getProcurement().getResidueNumber()-procurementChild.getNumber());
				} 

				//入库单
				if (procurement.getType() == 2) {
					procurementChild.setWarehouseId(jsonObject.getLong("warehouseId"));
					procurementChild.setPlace(jsonObject.getString("place"));
					procurementChild.setStatus(jsonObject.getIntValue("status"));
				}

				// 出库单
				if (procurement.getType() == 3) {
					//设置未审核
					procurement.setAudit(0);
					// 查询商品在当前库存下所有数量大于0的入库单，优先入库时间最早的入库单出库,出库数量可能存在一单无法满足，按时间依次删减出库单数量
					List<ProcurementChild> procurementChildList = procurementChildDao
							.findByCommodityIdAndStatusAndResidueNumberGreaterThan(procurementChild.getCommodityId(), 0,
									0);
					procurementChildList = procurementChildList.stream()
							.filter(ProcurementChild -> ProcurementChild.getProcurement().getType() == 2
									&& ProcurementChild.getProcurement().getFlag() == 0)
							.sorted(Comparator.comparing(ProcurementChild::getCreatedAt)).collect(Collectors.toList());
					// 出库单剩余数量
					int residueNumber = procurementChild.getNumber();
					String ids = "";
					List<ProcurementChild> newProcurementChild = new ArrayList<>();
					for (ProcurementChild updateProcurementChild : procurementChildList) {
						// 当入库单数量小于出库单时,更新剩余数量
						if (updateProcurementChild.getResidueNumber() < residueNumber) {
							procurementChild.setBatchNumber( 
									(StringUtils.isEmpty(procurementChild.getBatchNumber()) ? updateProcurementChild.getBatchNumber()
											: procurementChild.getBatchNumber() + "," + updateProcurementChild.getBatchNumber())+":"+ updateProcurementChild.getResidueNumber()   );
							residueNumber = procurementChild.getNumber() - updateProcurementChild.getResidueNumber();
							updateProcurementChild.setResidueNumber(0);
							newProcurementChild.add(updateProcurementChild);
							ids += updateProcurementChild.getId() + ",";
						} else {
							procurementChild.setBatchNumber( 
									(StringUtils.isEmpty(procurementChild.getBatchNumber()) ? updateProcurementChild.getBatchNumber()
											: procurementChild.getBatchNumber() + "," + updateProcurementChild.getBatchNumber())+":"+ residueNumber  );
							updateProcurementChild.setResidueNumber(updateProcurementChild.getResidueNumber() - residueNumber);
							newProcurementChild.add(updateProcurementChild);
							ids += updateProcurementChild.getId() + ",";
							break;
						}
					}
					
					// 更新改变数量的入库单的剩余数量
					procurementChildDao.save(newProcurementChild);
					// 将出库单ids存入入库单，便于反冲
					procurementChild.setPutWarehouseIds(ids);
					procurementChild.setWarehouseId(jsonObject.getLong("warehouseId"));
					procurementChild.setStatus(jsonObject.getIntValue("status"));
					
					Set<Inventory> inventorys = commodity.getInventorys();
					if (inventorys.size() == 0) {
						throw new ServiceException(commodity.getName() + "没有任何库存,无法出库");
					}
					// 减少库存
					if (inventorys.size() > 0) {
						for (Inventory inventory : inventorys) {
							if (inventory.getWarehouseId().equals(procurementChild.getWarehouseId())) {
								if(inventory.getNumber() < procurementChild.getNumber()){
									throw new ServiceException(commodity.getName() + "当前仓库库存不足,无法出库，请补充库存");
								}
								inventory.setNumber(inventory.getNumber() - procurementChild.getNumber());
							}
						}
					}
					commodityService.save(commodity);
				}

				// 将子单放入父单
				procurement.getProcurementChilds().add(procurementChild);
			}
		}
		return dao.save(procurement);
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
					procurement.getProcurementChilds().stream().forEach(p->{
						// 检查是否拥有下级单据
						List<ProcurementChild> nextProcurementChild = procurementChildDao.findByParentId(p.getId()).stream().filter(ProcurementChild->ProcurementChild.getProcurement().getFlag()==0).collect(Collectors.toList());
						if(nextProcurementChild.size()>0){
							throw new ServiceException("该数据已拥有下级单据，无法反冲，请先反冲下级单据");
						}
						if(p.getParentId()!=null){
							ProcurementChild parentProcurementChild= procurementChildDao.findOne(p.getParentId());
							parentProcurementChild.setResidueNumber(parentProcurementChild.getResidueNumber() + p.getNumber());
							parentProcurementChild.getProcurement().setResidueNumber(parentProcurementChild.getProcurement().getResidueNumber()+p.getNumber());
						}
					});

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
									if(procurement.getAudit()==1){
										throw new ServiceException("已审核入库，无法反冲");
									}
									inventory.setNumber(inventory.getNumber() - procurementChild.getNumber());
								}
								// 出库单
								if (procurement.getType() == 3) {
									if (!StringUtils.isEmpty(procurementChild.getPutWarehouseIds())) {
										// 获取出库单出库数据的ids
										String[] idArr = procurementChild.getPutWarehouseIds().split(",");
										// 反冲入库单数据
										int residueNumber = procurementChild.getNumber();
										for (String idtoSting : idArr) {
											ProcurementChild procurementChildSale = procurementChildDao
													.findOne(Long.valueOf(idtoSting));
											if (procurementChildSale.getNumber() - procurementChildSale.getResidueNumber() < residueNumber) {
												residueNumber -= procurementChildSale.getNumber() - procurementChildSale.getResidueNumber();
												procurementChildSale.setResidueNumber(procurementChildSale.getNumber());
											} else {
												procurementChildSale.setResidueNumber( procurementChildSale.getResidueNumber() + residueNumber);
											}
											procurementChildDao.save(procurementChildSale);
										}
									}
									// 当出库单类型是销售出库，同时反冲销售单的状态
									if (procurement.getStatus() == 0) {
										if (procurementChild.getOnlineOrderId() != null) {
											OnlineOrderChild onlineOrderChild = onlineOrderChildDao.findOne(procurementChild.getOnlineOrderId());
											//反冲销售状态
											onlineOrderChild.setStatus(Constants.ONLINEORDER_4);
											//反冲销售剩余数量
											onlineOrderChild.setResidueNumber(procurementChild.getNumber());
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
				.filter(ProcurementChild -> (procurement.getCommodityName() != null
						? ProcurementChild.getCommodity().getSkuCode().contains(procurement.getCommodityName())
						: ProcurementChild.getCommodity() != null))
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

	@Override
	public int excelProcurement(ExcelListener excelListener, Long userId, Long warehouseId) {
		int count = 0;
		Procurement procurement = new Procurement();
		procurement.setType(3);
		procurement.setUserId(userId);
		procurement.setStatus(0);
		int sumNumber = 0;
		// 获取导入的出库单
		List<Object> excelListenerList = excelListener.getData();
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < excelListenerList.size(); i++) {
			count++;
			OutProcurementPoi cPoi = (OutProcurementPoi) excelListenerList.get(i);
			JSONObject jsonObject = new JSONObject();
			Commodity commodity = commodityService.findByName(cPoi.getName());
			if (commodity != null) {
				jsonObject.put("commodityId", commodity.getId());
			} else {
				throw new ServiceException("当前导入excel第" + (i + 2) + "条数据的商品不存在，请先添加");
			}
			jsonObject.put("number", cPoi.getNumber());
			jsonObject.put("warehouseId", warehouseId);
			jsonObject.put("status", 0);
			jsonObject.put("batchNumber", "");
			jsonObject.put("childRemark", "导入出库单");
			jsonArray.add(jsonObject);
			sumNumber += cPoi.getNumber();
		}
		procurement.setCommodityNumber(jsonArray.toJSONString());
		procurement.setNumber(sumNumber);
		saveProcurement(procurement);
		return count;
	}

	@Override
	public List<Procurement> findByTypeAndStatusAndCreatedAtBetween(int type, int status, Date startTime,
			Date endTime) {
		return dao.findByTypeAndStatusAndCreatedAtBetween(type, status, startTime, endTime);
	}

	@Override
	@Transactional
	public Object test(Procurement procurement) {
		// 获取所有批次为null的出库单
		List<ProcurementChild> list = procurementChildDao.findByPutWarehouseIdsIsNull();
		list = list.stream().filter(ProcurementChild -> ProcurementChild.getProcurement().getType() == 3
				&& ProcurementChild.getProcurement().getFlag() == 0).collect(Collectors.toList());
		for (ProcurementChild procurementChild : list) {
			// 查询商品在当前库存下所有数量大于0的入库单，优先入库时间最早的入库单出库,出库数量可能存在一单无法满足，按时间依次删减出库单数量
			List<ProcurementChild> procurementChildList = procurementChildDao
					.findByCommodityIdAndStatusAndResidueNumberGreaterThan(procurementChild.getCommodityId(), 0, 0);
			procurementChildList = procurementChildList.stream()
					.filter(ProcurementChild -> ProcurementChild.getProcurement().getType() == 2
							&& ProcurementChild.getProcurement().getFlag() == 0)
					.sorted(Comparator.comparing(ProcurementChild::getCreatedAt)).collect(Collectors.toList());
			// 出库单剩余数量
			int residueNumber = procurementChild.getNumber();
			String ids = "";
			List<ProcurementChild> newProcurementChild = new ArrayList<>();
			for (ProcurementChild updateProcurementChild : procurementChildList) {
				// 当入库单数量小于出库单时,更新剩余数量
				if (updateProcurementChild.getResidueNumber() < residueNumber) {
					procurementChild.setBatchNumber( 
							(StringUtils.isEmpty(procurementChild.getBatchNumber()) ? updateProcurementChild.getBatchNumber()
									: procurementChild.getBatchNumber() + "," + updateProcurementChild.getBatchNumber())+":"+ updateProcurementChild.getResidueNumber()   );
					residueNumber = procurementChild.getNumber() - updateProcurementChild.getResidueNumber();
					updateProcurementChild.setResidueNumber(0);
					newProcurementChild.add(updateProcurementChild);
					ids += updateProcurementChild.getId() + ",";
				} else {
					procurementChild.setBatchNumber( 
							(StringUtils.isEmpty(procurementChild.getBatchNumber()) ? updateProcurementChild.getBatchNumber()
									: procurementChild.getBatchNumber() + "," + updateProcurementChild.getBatchNumber())+":"+ residueNumber  );
					updateProcurementChild.setResidueNumber(updateProcurementChild.getResidueNumber() - residueNumber);
					newProcurementChild.add(updateProcurementChild);
					ids += updateProcurementChild.getId() + ",";
					break;
				}
			}
			// 更新改变数量的入库单的剩余数量
			procurementChildDao.save(newProcurementChild);
			// 将出库单ids存入入库单，便于反冲
			procurementChild.setPutWarehouseIds(ids);
		}
		procurementChildDao.save(list);
		return list.size();
	}

	@Override
	public Object test1(Procurement procurement) {
		List<ProcurementChild> list = procurementChildDao.findByPutWarehouseIdsNotNull();
		list = list.stream().filter(ProcurementChild -> ProcurementChild.getProcurement().getType() == 3
				&& ProcurementChild.getProcurement().getFlag() == 0).collect(Collectors.toList());
		for (ProcurementChild procurementChild : list) {
			if(!StringUtils.isEmpty(procurementChild.getPutWarehouseIds())){
				String[] idStrings = procurementChild.getPutWarehouseIds().split(",");
				for(String id : idStrings){
					ProcurementChild procurementChild1 = procurementChildDao.findOne(Long.valueOf(id));
					procurementChild.setBatchNumber(StringUtils.isEmpty(procurementChild.getBatchNumber()) ? procurementChild1.getBatchNumber()
									: procurementChild.getBatchNumber() + "," + procurementChild1.getBatchNumber());
				}
			}
		}
		procurementChildDao.save(list);
		return null;
	}

	@Override
	public List<PackingChild> conversionProcurement(String ids) {
		if(!StringUtils.isEmpty(ids)){
			String [] idStrings = ids.split(",");
			for(String id :idStrings){
				Procurement procurement = dao.findOne(Long.valueOf(id));
				if(procurement.getProcurementChilds().size()>0){
					procurement.getProcurementChilds().stream().forEach(p->{
						if(p.getBatchNumber()==null){
							throw new ServiceException("出库单批次为空，无法转换");
						}
						//分隔批次号
						String[] batchNumber =  p.getBatchNumber().split(",");
						for(String  bc :  batchNumber){
							//分割出批次号和数量
							String[] bnStrings = bc.split(":");
							if(bnStrings.length>1){
									//新增发货清单
									PackingChild packingChild  = new PackingChild();
									packingChild.setBacthNumber(bnStrings[0]);
									packingChild.setCount(Integer.getInteger(bnStrings[1]));
									packingChild.setCustomerId(procurement.getOnlineCustomerId());
									
									 
							}
							
						}
				});
			}
			}
		}
		return null;
	}
	
	
	@Override
	public int auditProcurement(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				Procurement procurement = dao.findOne(Long.valueOf(id));
				if(procurement.getAudit() != null){
					throw new ServiceException(procurement.getDocumentNumber()+"入库单已审核，请勿再次审核");
				}
				if (procurement.getProcurementChilds().size() > 0) {
					procurement.getProcurementChilds().stream().forEach(p -> {
						Commodity commodity = p.getCommodity();
						// 创建商品的库存
						Set<Inventory> inventorys = commodity.getInventorys();
						// 获取库存
						Inventory inventory = inventoryDao.findByCommodityIdAndWarehouseId(
								p.getCommodityId(), p.getWarehouseId());
						if (inventory == null) {
							inventory = new Inventory();
							inventory.setCommodityId(p.getCommodityId());
							inventory.setNumber(p.getNumber());
							inventory.setWarehouseId(p.getWarehouseId());
							inventorys.add(inventory);
							commodity.setInventorys(inventorys);
						} else {
							inventory.setNumber(inventory.getNumber() + p.getNumber());
						}
						commodityService.save(commodity);
					});
				}
				count++;
			}
		}
		return count;
	}


	

}
