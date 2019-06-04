package com.bluewhite.onlineretailers.inventory.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.basedata.service.BaseDataService;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.dao.WarningDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.ProcurementChild;
import com.bluewhite.onlineretailers.inventory.entity.Warning;

import javassist.expr.NewArray;

@Service
public class CommodityServiceImpl extends BaseServiceImpl<Commodity, Long> implements CommodityService {

	@Autowired
	private CommodityDao dao;
	@Autowired
	private WarningDao warningDao;
	@Autowired
	private ProcurementService procurementService;
	@Autowired
	private BaseDataService service;
	@Autowired
	private CommodityDao commodityDao;

	@Override
	public PageResult<Commodity> findPage(Commodity param, PageParameter page) {
		Page<Commodity> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}

			// 按编号过滤
			if (!StringUtils.isEmpty(param.getSkuCode())) {
				predicate.add(cb.like(root.get("skuCode").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getSkuCode()) + "%"));
			}

			// 按产品名称过滤
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getName()) + "%"));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);

		PageResult<Commodity> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deleteCommodity(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] pers = ids.split(",");
			if (pers.length > 0) {
				for (String idString : pers) {
					try {
						dao.delete(Long.valueOf(idString));
					} catch (Exception e) {
						throw new ServiceException("商品已拥有销售单或采购单，无法删除");
					}

					count++;
				}
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> checkWarning() {
		// 预警集合
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 1.库存下限预警
		// 2.库存上限预警
		List<Warning> warningList = warningDao.findAll();
		if (warningList.size() == 0) {
			return mapList;
		}
		for (Warning warning : warningList) {
			// 获取天数
			Integer time = warning.getTime();
			// 获取离当前日期加上预警天数之间的出库单
			Date beginTime = DatesUtil.getDaySum(new Date(), -time);
			List<Procurement> procurementList = procurementService.findByTypeAndCreatedAt(3, beginTime, new Date());
			if (warning.getType() == 3) {
				// 获取时间段内的生产入库单,如果存在，且里面的商品数量存在且合计的数量大于库存，则预警
				procurementList = procurementService.findByTypeAndCreatedAt(2, beginTime, new Date());
			}
			if (procurementList.size() > 0) {
				List<ProcurementChild> procurementChildList = new ArrayList<>();
				// 将当前时间段所有的出库商品过滤出
				procurementList.stream().forEach(pt -> {
					procurementChildList.addAll(pt.getProcurementChilds());
				});
				// 按产品id分组获取所有的产品分组的出库数据
				// 如果是type=3，按产品id分组获取所有的产品分组的入库数据
				Map<Long, List<ProcurementChild>> mapProcurementChildList = procurementChildList.stream()
						.collect(Collectors.groupingBy(ProcurementChild::getCommodityId, Collectors.toList()));
				for (Long ps : mapProcurementChildList.keySet()) {
					List<ProcurementChild> psList = mapProcurementChildList.get(ps);
					// 当前商品的出库数量
					// 如果是type=3，则是商品的入库数量
					int countSales = psList.stream()
							.filter(ProcurementChild -> ProcurementChild.getWarehouseId()
									.equals(warning.getWarehouse().getId()))
							.mapToInt(ProcurementChild::getNumber).sum();
					// 当前商品的库存
					Commodity commodity = commodityDao.findOne(ps);
					int countInventory = commodity.getInventorys().stream()
							.filter(Inventory -> Inventory.getWarehouseId().equals(warning.getWarehouse().getId()))
							.mapToInt(Inventory::getNumber).sum();
					Map<String, Object> map = new HashMap<>();
					// 1.库存下限预警
					if (warning.getType() == 1) {
						// 当出售商品数量大于等于剩余库存数，将商品存入预警集合
						if (countSales >= countInventory) {
							map.put("name", commodity.getSkuCode());
							map.put("type", 1);
							map.put("countSales", countSales);
							map.put("countInventory", countInventory);
							map.put("inventoryName", warning.getWarehouse().getName());
							mapList.add(map);
						}
					}

					// 2.库存上限限预警
					if (warning.getType() == 2) {
						// 当出售商品数量小于等于剩余库存数，将商品存入预警集合
						if (countSales >= countInventory) {
							map.put("name", commodity.getSkuCode());
							map.put("type", 2);
							map.put("countSales", countSales);
							map.put("countInventory", countInventory);
							map.put("inventoryName", warning.getWarehouse().getName());
							mapList.add(map);
						}
					}

					// 3.库存时间过长预警
					if (warning.getType() == 3) {
						// 当入库商品数量小于等于剩余库存数，将商品存入预警集合
						if (countSales < countInventory) {
							map.put("name", commodity.getSkuCode());
							map.put("type", 3);
							// 商品剩余库存
							map.put("countSales", countSales);
							// 商品预警多余库存
							map.put("countInventory", countInventory - countSales);
							map.put("inventoryName", warning.getWarehouse().getName());
							mapList.add(map);
						}
					}
				}
			}
		}
		return mapList;

	}

	@Override
	public Warning saveWarning(Warning warning) {
		if (warning.getId() == null) {
			Warning wn = warningDao.findByTypeAndWarehouseId(warning.getType(), warning.getWarehouseId());
			if (wn != null) {
				throw new ServiceException("已有该预警类型和仓库类型的库存预警，不可再次添加");
			}
		}
		return warningDao.save(warning);
	}

	@Override
	public int deleteWarning(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] pers = ids.split(",");
			if (pers.length > 0) {
				for (String idString : pers) {
					warningDao.delete(Long.valueOf(idString));
					count++;
				}
			}
		}
		return count;
	}
}
