package com.bluewhite.onlineretailers.inventory.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.junit.Ignore;
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
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.StringUtil;
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
import com.bluewhite.system.sys.dao.RegionAddressDao;
import com.bluewhite.system.sys.entity.RegionAddress;

@Service
public class OnlineOrderServiceImpl extends BaseServiceImpl<OnlineOrder, Long> implements OnlineOrderService {

	@Autowired
	private OnlineOrderDao dao;
	@Autowired
	private CommodityDao commodityDao;
	@Autowired
	private OnlineOrderChildDao onlineOrderChildDao;
	@Autowired
	private OnlineOrderDao onlineOrderDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private RegionAddressDao regionAddressDao;

	@Override
	public PageResult<OnlineOrder> findPage(OnlineOrder param, PageParameter page) {
		Page<OnlineOrder> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}

			// 按客服id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			
			//按所在省份过滤,多个
			if (!StringUtils.isEmpty(param.getProvincesIds())) {
				List<Long>  provincesIdList = new ArrayList<Long>();
					String[] idArr = param.getProvincesIds().split(",");
					for (String idStr : idArr) {
						Long id = Long.parseLong(idStr);
						provincesIdList.add(id);
					}
				predicate.add(cb.and(root.get("provincesId").as(Long.class).in(provincesIdList)));
			}
			
			// 按客户名称过滤
			if (!StringUtils.isEmpty(param.getOnlineCustomerName())) {
				predicate.add(cb.like(root.get("name").as(String.class), "%" + StringUtil.specialStrKeyword(param.getOnlineCustomerName()) + "%"));
			}
			// 交易状态过滤
			if (!StringUtils.isEmpty(param.getStatus())) {
				predicate.add(cb.equal(root.get("status").as(String.class), param.getStatus()));
			}
			// 按物流方式
			if (!StringUtils.isEmpty(param.getShippingType())) {
				predicate.add(cb.equal(root.get("shippingType").as(String.class), param.getShippingType()));
			}

			// 按编号过滤
			if (!StringUtils.isEmpty(param.getNum())) {
				predicate.add(cb.equal(root.get("num").as(String.class), param.getNum()));
			}

			// 按订单时间过滤
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("createdAt").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按是否反冲
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);

		PageResult<OnlineOrder> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteOnlineOrder(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] pers = ids.split(",");
			if (pers.length > 0) {
				for (String idString : pers) {
					Long id = Long.valueOf(idString);
					OnlineOrder onlineOrder = dao.findOne(id);
					for (OnlineOrderChild onlineOrderChild : onlineOrder.getOnlineOrderChilds()) {
						// 当订单的状态是已发货
						if (onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_5)) {
							// 获取商品
							Commodity commodity = onlineOrderChild.getCommodity();
							// 获取库存
							// Inventory inventory =
							// inventoryDao.findByCommodityIdAndWarehouseId(commodity.getId(),
							// onlineOrderChild.getWarehouseId());
							// 获取所有商品的库存
							Set<Inventory> inventorys = commodity.getInventorys();
							// 减少库存的同时改变状态
							if (inventorys.size() > 0) {
								for (Inventory inventory : inventorys) {
									if (inventory.getWarehouseId().equals(onlineOrderChild.getWarehouseId())) {
										inventory.setNumber(inventory.getNumber() - onlineOrderChild.getNumber());
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
	@Transactional
	public OnlineOrder addOnlineOrder(OnlineOrder onlineOrder) {
		// 新增子订单
		if (!StringUtils.isEmpty(onlineOrder.getChildOrder())) {
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
				onlineOrderChild.setWarehouseId(jsonObject.getLong("warehouseId"));
				onlineOrderChild.setOnlineOrderId(onlineOrder.getId());
				onlineOrder.getOnlineOrderChilds().add(onlineOrderChild);
			}
		}
		// 总数量
		onlineOrder.setNum(onlineOrder.getOnlineOrderChilds().stream().mapToInt(OnlineOrderChild::getNumber).sum());
		onlineOrder.setFlag(0);
		return dao.save(onlineOrder);
	}

	@Override
	@Transactional
	public int delivery(String delivery) {
		int count = 0;
		if (!StringUtils.isEmpty(delivery)) {
			JSONArray jsonArray = JSON.parseArray(delivery);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Long id = jsonObject.getLong("id");
				Long warehouseId = jsonObject.getLong("warehouseId");
				int number = jsonObject.getIntValue("number");
				// 获取子订单
				OnlineOrderChild onlineOrderChild = onlineOrderChildDao.findOne(id);
				// 获取父订单
				OnlineOrder onlineOrder = onlineOrderChild.getOnlineOrder();
				//更新父订单的状态(当自订单)
				List<OnlineOrderChild> onlineOrderChildList = onlineOrder.getOnlineOrderChilds();
				
//				onlineOrderChildList.stream().forEach(o->{
//					int sumStatus = 0;
//					if(onlineOrder.getStatus().equals(Constants.ONLINEORDER_4)){
//						sumStatus++;
//					}
//				});
				
				// 当订单的状态是买家已付款时
				if (onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_4)) {
					// 获取商品
					Commodity commodity = onlineOrderChild.getCommodity();
					// 获取库存
					Inventory inventory = inventoryDao.findByCommodityIdAndWarehouseId(commodity.getId(),
							jsonObject.getLong("warehouseId"));
					if (inventory != null) {
						inventory.setNumber(inventory.getNumber() - number);
						inventoryDao.save(inventory);
						onlineOrderChild.setStatus(Constants.ONLINEORDER_5);
						onlineOrderChildDao.save(onlineOrderChild);
					} else {
						throw new ServiceException(commodity.getName() + "当前仓库没有库存,无法出库");
					}
				}else{
					throw new ServiceException(onlineOrder.getDocumentNumber() + "不是等待卖家发货状态,无法发货");
				}
				count++;
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int excelOnlineOrder(ExcelListener excelListener,Long onlineCustomerId,Long userId) {
		int count = 0;
		// 获取导入的订单
		List<Object> excelListenerList = excelListener.getData();
		List<OnlineOrder> onlineOrderList = new ArrayList<>();
		List<Procurement> procurementList = new ArrayList<>();
		OnlineOrder onlineOrder = null;
		Procurement procurement = null;
		for (int i = 0; i < excelListenerList.size(); i++) {
			OnlineOrderPoi cPoi = (OnlineOrderPoi) excelListenerList.get(i);
			if (cPoi.getDocumentNumber() != null) {  
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
				onlineOrder.setOnlineCustomerId(onlineCustomerId);
				onlineOrder.setUserId(userId);
				// 將地址转换成省市县
				List<Map<String, String>> addressMap = StringUtil.addressResolution(cPoi.getAddress());
				String province = "";
				String city = "";
				String county = "";
				for (Map<String, String> map : addressMap) {
					province = map.get("province");
					city = map.get("city");
					county = map.get("county");
				}
				RegionAddress provinces = regionAddressDao.findByRegionName(province);
				RegionAddress citys = regionAddressDao.findByRegionName(city);
				RegionAddress countys = regionAddressDao.findByRegionName(county);
				onlineOrder.setProvinces(provinces);
				onlineOrder.setCity(citys);
				onlineOrder.setCounty(countys);
				onlineOrder.setAddress(cPoi.getAddress());
				onlineOrder.setPhone(cPoi.getPhone());
				onlineOrder.setBuyerMessage(cPoi.getBuyerMessage());
				onlineOrder.setTrackingNumber(cPoi.getTrackingNumber());
			}
			List<OnlineOrderChild> onlineOrderChilds = onlineOrder.getOnlineOrderChilds();
			// 创建子订单
			OnlineOrderChild onlineOrderChild = new OnlineOrderChild();
			onlineOrder.setFlag(0);
			onlineOrderChild.setStatus(Constants.ONLINEORDER_5);
			onlineOrderChild.setOnlineOrder(onlineOrder);
			onlineOrderChild.setPrice(cPoi.getPrice());
			onlineOrderChild.setNumber(cPoi.getNumber());
			onlineOrderChild.setWarehouseId(cPoi.getWarehouseId()==null ? 157 : cPoi.getWarehouseId());
			onlineOrderChild.setSumPrice(NumUtils.mul(onlineOrderChild.getPrice(), onlineOrderChild.getNumber()));
			if (cPoi.getCommodityName() != null) {
				Commodity commodity = commodityDao.findByName(cPoi.getCommodityName());
				if (commodity != null) {
					onlineOrderChild.setCommodityId(commodity.getId());
					ProcurementChild procurementChild = new ProcurementChild();
					procurementChild.setCommodityId(commodity.getId());
					procurementChild.setNumber(cPoi.getNumber());
					procurementChild.setWarehouseId(cPoi.getWarehouseId());
					// 出库单
					if (procurement.getType() == 3) {
						// 获取库存
						Inventory inventory = inventoryDao.findByCommodityIdAndWarehouseId(commodity.getId(),
								procurementChild.getWarehouseId());
						if (inventory != null) {
							// 减少库存
							inventory.setNumber(inventory.getNumber() - procurementChild.getNumber());
							inventoryDao.save(inventory);
						} else {
							throw new ServiceException(commodity.getName() + "当前仓库没有库存,无法出库");
						}
					}
				} else {
					throw new ServiceException("当前导入excel第" + (i + 2) + "条数据的商品不存在，请先添加");
				}
			}
			onlineOrderChilds.add(onlineOrderChild);
			// 当下一条数据没有订单编号时,自动存储上面所有的父子订单
			OnlineOrderPoi onlineOrderPoiNext = null;
			if(i<excelListenerList.size()-1){
				onlineOrderPoiNext = (OnlineOrderPoi) excelListenerList.get(i + 1);
				if (onlineOrderPoiNext.getDocumentNumber() != null) {
					onlineOrderList.add(onlineOrder);
					procurementList.add(procurement);
				}
			}
		}
		dao.save(onlineOrderList);
		return count;
	}

	@Override
	public List<Map<String, Object>> reportSales(OnlineOrder onlineOrder) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		long size = 0;
		// 按天查询
		if (onlineOrder.getReport() == 1) {
			size = DatesUtil.getDaySub(onlineOrder.getOrderTimeBegin(), onlineOrder.getOrderTimeEnd());
		}
		// 按月查询
		if (onlineOrder.getReport() == 2) {
			onlineOrder.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(onlineOrder.getOrderTimeBegin()));
			size = 1;
		}
		for (int i = 0; i < size; i++) {
			Date beginTimes = null;
			Date endTimes = null;
			// 按天查询
			if (onlineOrder.getReport() == 1) {
				if (i != 0) {
					// 获取下一天的时间
					beginTimes = DatesUtil.nextDay(onlineOrder.getOrderTimeBegin());
				} else {
					// 获取第一天的开始时间
					beginTimes = onlineOrder.getOrderTimeBegin();
				}
				// 获取一天的结束时间
				endTimes = DatesUtil.getLastDayOftime(beginTimes);
			}
			// 按月查询
			if (onlineOrder.getReport() == 2) {
				beginTimes = onlineOrder.getOrderTimeBegin();
				endTimes = onlineOrder.getOrderTimeEnd();
			}

			Map<String, Object> mapSale = new HashMap<String, Object>();
			// 获取所有的已发货的订单
			List<OnlineOrder> onlineOrderList = onlineOrderDao.findByStatusAndCreatedAtBetween(Constants.ONLINEORDER_5,
					beginTimes, endTimes);
			// 实付金额
			List<Double> listPayment = new ArrayList<>();
			// 实际运费
			List<Double> listPostFee = new ArrayList<>();
			Double sumPayment = 0.0;
			Double sumpostFee = 0.0;
			if (onlineOrderList.size() > 0) {
				onlineOrderList.stream().forEach(c -> {
					listPayment.add(c.getPayment());
					listPostFee.add(c.getPostFee());
				});
				sumPayment = NumUtils.sum(listPayment);
				sumpostFee = NumUtils.sum(listPostFee);
				// 将所有的子单数据取出
				List<OnlineOrderChild> onlineOrderChildList = new ArrayList<>();
				// 将当前时间段所有的出库商品过滤出
				onlineOrderList.stream().forEach(pt -> {
					onlineOrderChildList.addAll(pt.getOnlineOrderChilds());
				});
				
				// 成交金额
				mapSale.put("sumPayment", sumPayment);
				// 成交单数
				mapSale.put("singular", onlineOrderList.size());
				// 实际邮费
				mapSale.put("sumpostFee", sumpostFee);
				// 成交宝贝数量
				int proNumber = onlineOrderChildList.stream().mapToInt(OnlineOrderChild::getNumber).sum();
				mapSale.put("proNumber", proNumber);
				// 每单平均金额
				mapSale.put("averageAmount", NumUtils.div(sumPayment, onlineOrderList.size(), 2));
				// 广宣成本
				List<Double> listSumCost = new ArrayList<>();
				Double sumCost = 0.0;
				if (onlineOrderList.size() > 0) {
					onlineOrderChildList.stream().forEach(c -> {
						listPayment.add(c.getCommodity().getPropagandaCost());
					});
					sumCost = NumUtils.sum(listPayment);
				}
				mapSale.put("sumCost", sumCost);
				// 利润
				mapSale.put("profits", NumUtils.sub(sumPayment, sumCost, sumpostFee));
			}
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(onlineOrder.getReport() == 1){
				// 时间
				mapSale.put("time", sdf.format(beginTimes));
			}
			if(onlineOrder.getReport() == 2){
				sdf = new SimpleDateFormat("yyyy-MM");
				// 时间
				mapSale.put("time", sdf.format(beginTimes));
			}
			mapList.add(mapSale);
		}

		return mapList;
	}

	@Override
	public List<OnlineOrderChild> reportSalesChild(OnlineOrderChild onlineOrderChild) {
		// 获取所有的已发货的子订单订单
		List<OnlineOrderChild> onlineOrderChildList = onlineOrderChildDao.findByStatusAndCreatedAtBetween(
				Constants.ONLINEORDER_5, onlineOrderChild.getOrderTimeBegin(), onlineOrderChild.getOrderTimeEnd());
		return null;
	}

	@Override
	public List<Map<String, Object>> reportSalesGoods(OnlineOrder onlineOrder) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 获取所有的已发货的子订单订单
		List<OnlineOrderChild> onlineOrderChildList = onlineOrderChildDao.findByStatusAndCreatedAtBetween(
				Constants.ONLINEORDER_5, onlineOrder.getOrderTimeBegin(), onlineOrder.getOrderTimeEnd());
		// 根据商品id分组
		Map<Long, List<OnlineOrderChild>> mapOnlineOrderChildList = onlineOrderChildList.stream()
				.collect(Collectors.groupingBy(OnlineOrderChild::getCommodityId, Collectors.toList()));
		for (Long ps : mapOnlineOrderChildList.keySet()) {
			List<OnlineOrderChild> psList = mapOnlineOrderChildList.get(ps);
			Map<String, Object> mapSale = new HashMap<String, Object>();
			int sunNumber = psList.stream().mapToInt(OnlineOrderChild::getNumber).sum();
			List<Double> listSumPayment = new ArrayList<>();
			Double sumPayment = 0.0;
			if (psList.size() > 0) {
				psList.stream().forEach(c -> {
					listSumPayment.add(c.getSumPrice());
				});
				sumPayment = NumUtils.sum(sumPayment);
			}
			mapSale.put("name", psList.get(0).getCommodity().getSkuCode());
			mapSale.put("singular", psList.size());
			mapSale.put("sumPayment", sumPayment);
			mapSale.put("sunNumber", sunNumber);
			// 每单平均金额
			mapSale.put("averageAmount", NumUtils.div(sumPayment, psList.size(), 2));
			mapList.add(mapSale);
		}
		return mapList;
	}

	@Override
	public List<Map<String, Object>> reportSalesUser(OnlineOrder onlineOrder) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 获取所有的已发货的订单
		List<OnlineOrder> onlineOrderList = onlineOrderDao.findByStatusAndCreatedAtBetween(Constants.ONLINEORDER_5,
				onlineOrder.getOrderTimeBegin(), onlineOrder.getOrderTimeEnd());
		Map<Long, List<OnlineOrder>> mapOnlineOrderList = null;
		if (onlineOrder.getReport() == 3) {
			// 根据员工id分组
			mapOnlineOrderList = onlineOrderList.stream()
					.collect(Collectors.groupingBy(OnlineOrder::getUserId, Collectors.toList()));
		}
		if (onlineOrder.getReport() == 4) {
			// 根据客户id分组
			mapOnlineOrderList = onlineOrderList.stream()
					.collect(Collectors.groupingBy(OnlineOrder::getOnlineCustomerId, Collectors.toList()));
		}
		for (Long ps : mapOnlineOrderList.keySet()) {
			List<OnlineOrder> psList = mapOnlineOrderList.get(ps);
			Map<String, Object> mapSale = new HashMap<String, Object>();
			// 成交单数
			mapSale.put("user", onlineOrder.getReport() == 3 ? psList.get(0).getUser().getUserName()
					: psList.get(0).getOnlineCustomer().getName());
			// 成交单数
			mapSale.put("singular", psList.size());
			int proNumber = psList.stream().mapToInt(OnlineOrder::getNum).sum();
			// 宝贝数量
			mapSale.put("proNumber", proNumber);
			// 总金额
			List<Double> listSumPayment = new ArrayList<>();
			// 实际运费
			List<Double> listPostFee = new ArrayList<>();
			Double sumPayment = 0.0;
			Double sumpostFee = 0.0;
			if (psList.size() > 0) {
				psList.stream().forEach(c -> {
					listSumPayment.add(c.getSumPrice());
				});
				sumPayment = NumUtils.sum(sumPayment);
				sumpostFee = NumUtils.sum(listPostFee);
			}
			// 成交金额
			mapSale.put("sumPayment", sumPayment);
			// 实际邮费
			mapSale.put("sumpostFee", sumpostFee);
			// 每单平均金额
			mapSale.put("averageAmount", NumUtils.div(sumPayment, psList.size(), 2));
			mapList.add(mapSale);

		}
		return mapList;
	}

}
