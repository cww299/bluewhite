package com.bluewhite.onlineretailers.inventory.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.excel.ExcelListener;
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.dao.DeliveryDao;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderChildDao;
import com.bluewhite.onlineretailers.inventory.dao.OnlineOrderDao;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementChildDao;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.Delivery;
import com.bluewhite.onlineretailers.inventory.entity.DeliveryChild;
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
	@Autowired
	private ProcurementDao procurementDao;
	@Autowired
	private ProcurementChildDao procurementChildDao;
	@Autowired
	private ProcurementService procurementService;
	@Autowired
	private DeliveryDao deliveryDao;

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

			// 按所在省份过滤,多个
			if (!StringUtils.isEmpty(param.getProvincesIds())) {
				List<Long> provincesIdList = new ArrayList<Long>();
				String[] idArr = param.getProvincesIds().split(",");
				for (String idStr : idArr) {
					Long id = Long.parseLong(idStr);
					provincesIdList.add(id);
				}
				predicate.add(cb.and(root.get("provincesId").as(Long.class).in(provincesIdList)));
			}
			// 按客户名称过滤
			if (!StringUtils.isEmpty(param.getOnlineCustomerName())) {
				predicate.add(cb.like(root.get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getOnlineCustomerName()) + "%"));
			}

			// 交易状态过滤
			if (!StringUtils.isEmpty(param.getStatus())) {
				List<String> statusList = new ArrayList<>();
				String[] idArr = param.getStatus().split(",");
				for (String idStr : idArr) {
					statusList.add(idStr);
				}
				predicate.add(cb.and(root.get("status").as(String.class).in(statusList)));
			}

			// 按物流方式
			if (!StringUtils.isEmpty(param.getShippingType())) {
				predicate.add(cb.equal(root.get("shippingType").as(String.class), param.getShippingType()));
			}

			// 按订单编号过滤
			if (!StringUtils.isEmpty(param.getDocumentNumber())) {
				predicate.add(
						cb.like(root.get("documentNumber").as(String.class), "%" + param.getDocumentNumber() + "%"));
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

			// 按商品名称过滤
			if (!StringUtils.isEmpty(param.getCommodityName())) {
				Join<OnlineOrder, OnlineOrderChild> join = root
						.join(root.getModel().getList("onlineOrderChilds", OnlineOrderChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("commodity").get("skuCode").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getCommodityName()) + "%"));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);

		PageResult<OnlineOrder> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public PageResult<OnlineOrderChild> findPage(OnlineOrderChild param, PageParameter page) {
		Page<OnlineOrderChild> pages = onlineOrderChildDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按用户id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("onlineOrder").get("userId").as(Long.class), param.getUserId()));
			}
			// 按客服id过滤
			if (param.getOnlineCustomerId() != null) {
				predicate.add(cb.equal(root.get("onlineOrder").get("onlineCustomerId").as(Long.class),
						param.getOnlineCustomerId()));
			}
			// 交易状态过滤
			if (!StringUtils.isEmpty(param.getStatus())) {
				predicate.add(cb.equal(root.get("status").as(String.class), param.getStatus()));
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

		PageResult<OnlineOrderChild> result = new PageResult<>(pages, page);
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
					if (onlineOrder.getFlag() == 1) {
						throw new ServiceException("该数据已经反冲，无法再次反冲");
					}
					onlineOrder.setFlag(1);
					for (OnlineOrderChild onlineOrderChild : onlineOrder.getOnlineOrderChilds()) {
						// 当订单的状态是已发货
						if (onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_5)) {
							// 获取商品
							Commodity commodity = onlineOrderChild.getCommodity();
							// 获取出库单,反冲出库单
							List<ProcurementChild> procurementChildList = procurementChildDao
									.findByOnlineOrderId(onlineOrderChild.getId());
							// 排除已反冲的出库单
							procurementChildList = procurementChildList.stream()
									.filter(ProcurementChild -> ProcurementChild.getProcurement().getFlag() == 0)
									.collect(Collectors.toList());
							procurementService.deleteProcurement(
									String.valueOf(procurementChildList.get(0).getProcurement().getId()));
						}
					}
					dao.save(onlineOrder);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public OnlineOrder addOnlineOrder(OnlineOrder onlineOrder) {
		if (onlineOrder.getOnlineCustomerId() == null) {
			throw new ServiceException("没有客户，无法新增订单");
		}
		// 生成销售单编号
		onlineOrder.setDocumentNumber(
				StringUtil.getDocumentNumber(Constants.XS) + SalesUtils.get0LeftString((int) dao.count(), 8));
		// 新增子订单
		if (!StringUtils.isEmpty(onlineOrder.getChildOrder())) {
			JSONArray jsonArray = JSON.parseArray(onlineOrder.getChildOrder());
			for (int i = 0; i < jsonArray.size(); i++) {
				OnlineOrderChild onlineOrderChild = new OnlineOrderChild();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				onlineOrderChild.setCommodityId(jsonObject.getLong("commodityId"));
				if (onlineOrderChild.getCommodityId() == null) {
					throw new ServiceException("没有商品，无法新增订单");
				}
				onlineOrderChild.setNumber(jsonObject.getInteger("number"));
				onlineOrderChild.setResidueNumber(jsonObject.getInteger("number"));
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
		// 总剩余数量
		onlineOrder.setResidueNumber(onlineOrder.getNum());
		onlineOrder.setFlag(0);
		return dao.save(onlineOrder);
	}

	@Override
	@Transactional
	public int delivery(String delivery) {
		CurrentUser cu = SessionManager.getUserSession();
		int count = 0;
		if (!StringUtils.isEmpty(delivery)) {
			// 新建父出库单
			Procurement procurement = new Procurement();
			// 新建主发货单
			Delivery deli = new Delivery();
			procurement.setUserId(cu.getId());
			// 出库单编号
			procurement.setDocumentNumber(
					StringUtil.getDocumentNumber("3") + SalesUtils.get0LeftString((int) procurementDao.count(), 8));
			procurement.setStatus(0);
			procurement.setFlag(0);
			procurement.setType(3);
			JSONArray jsonArray = JSON.parseArray(delivery);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Long id = jsonObject.getLong("id");
				Long warehouseId = jsonObject.getLong("warehouseId");
				int number = jsonObject.getIntValue("number");
				String trackingNumber = jsonObject.getString("trackingNumber");
				// 获取子订单
				OnlineOrderChild onlineOrderChild = onlineOrderChildDao.findOne(id);
				// 获取父订单
				OnlineOrder onlineOrder = onlineOrderChild.getOnlineOrder();
				if (onlineOrder.getFlag() == 1) {
					throw new ServiceException(onlineOrder.getDocumentNumber() + "销售单，反冲数据，无法发货");
				}
				procurement.setRemark("销售出库：" + onlineOrder.getDocumentNumber());
				deli.setOnlineOrderId(onlineOrder.getId());
				deli.setTrackingNumber(trackingNumber);
				// 发货单
				DeliveryChild deliveryChild = new DeliveryChild();
				deliveryChild.setCommodityId(onlineOrderChild.getCommodityId());
				deliveryChild.setNumber(number);
				deli.getDeliveryChilds().add(deliveryChild);

				// 新建子出库单，一个子订单拥有一个子出库单
				ProcurementChild procurementChild = new ProcurementChild();
				procurementChild.setCommodityId(onlineOrderChild.getCommodityId());
				procurementChild.setNumber(number);
				procurementChild.setResidueNumber(number);
				procurementChild.setWarehouseId(warehouseId);
				procurementChild.setStatus(0);
				// 存入销售子单的销售单id
				procurementChild.setOnlineOrderId(onlineOrderChild.getId());
				procurement.getProcurementChilds().add(procurementChild);

				// 查询商品在当前库存下所有数量大于0的入库单，优先入库时间最早的入库单出库,出库数量可能存在一单无法满足，按时间依次删减出库单数量
				List<ProcurementChild> procurementChildList = procurementChildDao
						.findByCommodityIdAndStatusAndResidueNumberGreaterThan(onlineOrderChild.getCommodityId(), 0, 0);
				procurementChildList = procurementChildList.stream()
						.filter(ProcurementChild -> ProcurementChild.getProcurement().getType() == 2
								&& ProcurementChild.getProcurement().getFlag() == 0)
						.sorted(Comparator.comparing(ProcurementChild::getCreatedAt)).collect(Collectors.toList());
				// 出库单剩余数量
				int residueNumber = number;
				String ids = "";
				List<ProcurementChild> newProcurementChild = new ArrayList<>();
				for (ProcurementChild updateProcurementChild : procurementChildList) {
					// 当入库单数量小于出库单时,更新剩余数量
					if (updateProcurementChild.getResidueNumber() < residueNumber) {
						residueNumber = number - updateProcurementChild.getResidueNumber();
						updateProcurementChild.setResidueNumber(0);
						newProcurementChild.add(updateProcurementChild);
						ids += updateProcurementChild.getId() + ",";
					} else {
						updateProcurementChild
								.setResidueNumber(updateProcurementChild.getResidueNumber() - residueNumber);
						newProcurementChild.add(updateProcurementChild);
						ids += updateProcurementChild.getId() + ",";
						break;
					}
				}
				// 更新改变数量的入库单的剩余数量
				procurementChildDao.save(newProcurementChild);
				// 将出库单ids存入入库单，便于反冲
				procurementChild.setPutWarehouseIds(ids);
				// 当订单的状态是买家已付款时或部分发货
				if (onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_4)
						|| onlineOrderChild.getStatus().equals(Constants.ONLINEORDER_3)) {

					// 获取商品
					Commodity commodity = onlineOrderChild.getCommodity();
					// 获取库存
					Inventory inventory = inventoryDao.findByCommodityIdAndWarehouseId(commodity.getId(), warehouseId);
					if (inventory != null) {
						// 子订单部分发货
						if (onlineOrderChild.getResidueNumber() != 0 && onlineOrderChild.getResidueNumber() > number) {
							onlineOrderChild.setStatus(Constants.ONLINEORDER_3);
							onlineOrderChild.setResidueNumber(onlineOrderChild.getResidueNumber() - number);
						} else {
							onlineOrderChild.setResidueNumber(0);
							onlineOrderChild.setStatus(Constants.ONLINEORDER_5);
						}
						inventory.setNumber(inventory.getNumber() - number);
						inventoryDao.save(inventory);
						onlineOrderChildDao.save(onlineOrderChild);
					} else {
						throw new ServiceException(commodity.getName() + "当前仓库没有库存,无法出库");
					}
				} else {
					throw new ServiceException(onlineOrder.getDocumentNumber() + "不是等待卖家发货状态,无法发货");
				}

				// 更新父订单剩余数量为0更新为卖家已发货，否则是部分发货
				List<OnlineOrderChild> onlineOrderChildList = onlineOrder.getOnlineOrderChilds();
				int onlineOrderChildListNumber = onlineOrderChildList.stream().mapToInt(p -> p.getResidueNumber())
						.sum();
				onlineOrder.setResidueNumber(onlineOrderChildListNumber);
				if (onlineOrderChildListNumber == 0) {
					onlineOrder.setStatus(Constants.ONLINEORDER_5);
				} else {
					onlineOrder.setStatus(Constants.ONLINEORDER_3);
				}
				count++;

			}
			// 更新总发货单的数量
			int procurementNumber = procurement.getProcurementChilds().stream().mapToInt(p -> p.getNumber()).sum();
			int sumNumber = deli.getDeliveryChilds().stream().mapToInt(p -> p.getNumber()).sum();
			procurement.setNumber(procurementNumber);
			deli.setSumNumber(sumNumber);
			procurementDao.save(procurement);
			deliveryDao.save(deli);
		}
		return count;
	}

	@Override
	@Transactional
	public int excelOnlineOrder(ExcelListener excelListener, Long onlineCustomerId, Long userId, Long warehouseId) {
		// 获取导入的订单
		List<Object> excelListenerList = excelListener.getData();
		List<OnlineOrder> onlineOrderList = new ArrayList<>();
		List<Procurement> procurementList = new ArrayList<>();
		List<Delivery> deliveryList = new ArrayList<>();
		OnlineOrder onlineOrder = null;
		Procurement procurement = null;
		Delivery deli = null;
		for (int i = 0; i < excelListenerList.size(); i++) {
			OnlineOrderPoi cPoi = (OnlineOrderPoi) excelListenerList.get(i);
			if (cPoi.getDocumentNumber() != null) {
				onlineOrder = new OnlineOrder();
				procurement = new Procurement();
				// 新建主发货单
				deli = new Delivery();
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
				onlineOrder.setProvincesId(provinces.getId());
				onlineOrder.setCityId(citys.getId());
				onlineOrder.setCountyId(countys.getId());
				onlineOrder.setAddress(cPoi.getAddress());
				onlineOrder.setPhone(cPoi.getPhone());
				onlineOrder.setBuyerMessage(cPoi.getBuyerMessage());
				dao.save(onlineOrder);
				deli.setOnlineOrderId(onlineOrder.getId());
				deli.setTrackingNumber(cPoi.getTrackingNumber());
			}
			List<OnlineOrderChild> onlineOrderChilds = onlineOrder.getOnlineOrderChilds();
			List<DeliveryChild> deliverys = deli.getDeliveryChilds();
			// 创建子订单
			OnlineOrderChild onlineOrderChild = new OnlineOrderChild();
			onlineOrder.setFlag(0);
			onlineOrderChild.setStatus(Constants.ONLINEORDER_5);
			onlineOrderChild.setOnlineOrder(onlineOrder);
			onlineOrderChild.setPrice(cPoi.getPrice());
			onlineOrderChild.setNumber(cPoi.getNumber());
			onlineOrderChild.setResidueNumber(0);
			onlineOrderChild.setWarehouseId(cPoi.getWarehouseId() == null ? 157 : warehouseId);
			onlineOrderChild.setSumPrice(NumUtils.mul(onlineOrderChild.getPrice(), onlineOrderChild.getNumber()));
			onlineOrderChild.setActualSum(onlineOrderChild.getSumPrice());
			onlineOrderChild.setSystemPreferential(0.0);
			onlineOrderChild.setSellerReadjustPrices(0.0);
			// 发货子单
			DeliveryChild deliveryChild = new DeliveryChild();
			deliveryChild.setNumber(cPoi.getNumber());

			if (cPoi.getCommodityName() != null) {
				Commodity commodity = commodityDao.findByName(cPoi.getCommodityName());
				if (commodity != null) {
					onlineOrderChild.setCommodityId(commodity.getId());
					deliveryChild.setCommodityId(onlineOrderChild.getCommodityId());
					ProcurementChild procurementChild = new ProcurementChild();
					procurementChild.setCommodityId(commodity.getId());
					procurementChild.setNumber(cPoi.getNumber());
					procurementChild.setWarehouseId(warehouseId);
					// 出库单
					if (procurement.getType() == 3) {
						procurement.setNumber(cPoi.getNumber());
						procurementChild.setResidueNumber(cPoi.getNumber());
						// 获取库存
						Inventory inventory = inventoryDao.findByCommodityIdAndWarehouseId(commodity.getId(),
								warehouseId);
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
			deliverys.add(deliveryChild);

			// 当下一条数据没有订单编号时,自动存储上面所有的父子订单
			OnlineOrderPoi onlineOrderPoiNext = null;
			try {
				onlineOrderPoiNext = (OnlineOrderPoi) excelListenerList.get(i + 1);
			} catch (Exception e) {
			}
			if (i == excelListenerList.size() - 1 || onlineOrderPoiNext == null
					|| onlineOrderPoiNext.getDocumentNumber() != null) {
				onlineOrder.setNum(
						onlineOrder.getOnlineOrderChilds().stream().mapToInt(OnlineOrderChild::getNumber).sum());
				deli.setSumNumber(deli.getDeliveryChilds().stream().mapToInt(DeliveryChild::getNumber).sum());
				onlineOrderList.add(onlineOrder);
				procurementList.add(procurement);
				deliveryList.add(deli);
			}
		}
		dao.save(onlineOrderList);
		procurementDao.save(procurementList);
		deliveryDao.save(deliveryList);
		return onlineOrderList.size();
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
		// 开始时间
		Date beginTimes = onlineOrder.getOrderTimeBegin();
		for (int i = 0; i < size; i++) {
			// 获取一天的结束时间
			Date endTimes = DatesUtil.getLastDayOftime(beginTimes);
			// 按月查询
			if (onlineOrder.getReport() == 2) {
				beginTimes = onlineOrder.getOrderTimeBegin();
				endTimes = onlineOrder.getOrderTimeEnd();
			}

			Map<String, Object> mapSale = new HashMap<String, Object>();
			// 获取所有订单
			List<OnlineOrder> onlineOrderList = onlineOrderDao.findByFlagAndCreatedAtBetween(0, beginTimes, endTimes);
			// 实付金额
			List<Double> listPayment = new ArrayList<>();
			// 实际运费
			List<Double> listPostFee = new ArrayList<>();
			Double sumPayment = 0.0;
			Double sumpostFee = 0.0;
			if (onlineOrderList.size() > 0) {
				onlineOrderList.stream().forEach(c -> {
					listPayment.add(c.getPayment() == null ? 0 : c.getPayment());
					listPostFee.add(c.getPostFee() == null ? 0 : c.getPostFee());
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
						listPayment.add(c.getCommodity().getPropagandaCost() == null ? 0
								: c.getCommodity().getPropagandaCost());
					});
					sumCost = NumUtils.sum(listPayment);
				}
				mapSale.put("sumCost", sumCost);
				// 利润
				mapSale.put("profits", NumUtils.sub(sumPayment, sumCost, sumpostFee));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (onlineOrder.getReport() == 1) {
					// 时间
					mapSale.put("time", sdf.format(beginTimes));
				}
				if (onlineOrder.getReport() == 2) {
					sdf = new SimpleDateFormat("yyyy-MM");
					// 时间
					mapSale.put("time", sdf.format(beginTimes));
				}
				mapList.add(mapSale);
			}
			beginTimes = DatesUtil.nextDay(beginTimes);
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
		List<OnlineOrderChild> onlineOrderChildList = onlineOrderChildDao
				.findByCreatedAtBetween(onlineOrder.getOrderTimeBegin(), onlineOrder.getOrderTimeEnd());
		// 根据商品id分组
		Map<Long, List<OnlineOrderChild>> mapOnlineOrderChildList = onlineOrderChildList.stream()
				.filter(OnlineOrderChild -> OnlineOrderChild.getOnlineOrder().getFlag() != 1
						&& onlineOrder.getCommodityName() != null ? OnlineOrderChild.getCommodity().getSkuCode().contains(onlineOrder.getCommodityName())
								: OnlineOrderChild.getCommodity() != null
				).collect(Collectors.groupingBy(OnlineOrderChild::getCommodityId, Collectors.toList()));
		for (Long ps : mapOnlineOrderChildList.keySet()) {
			List<OnlineOrderChild> psList = mapOnlineOrderChildList.get(ps);
			if (psList.size() == 0) {
				continue;
			}
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
		// 获取所有的订单
		List<OnlineOrder> onlineOrderList = onlineOrderDao.findByFlagAndCreatedAtBetween(0,
				onlineOrder.getOrderTimeBegin(), onlineOrder.getOrderTimeEnd());
		Map<Long, List<OnlineOrder>> mapOnlineOrderList = null;
		if (onlineOrder.getReport() == 3) {
			// 根据员工id分组
			mapOnlineOrderList = onlineOrderList.stream()
					.filter(OnlineOrder -> onlineOrder.getUserId() != null
							? OnlineOrder.getUserId().equals(onlineOrder.getUserId()) : OnlineOrder.getUserId() != null)
					.collect(Collectors.groupingBy(OnlineOrder::getUserId, Collectors.toList()));
		}
		if (onlineOrder.getReport() == 4) {
			// 根据客户id分组
			mapOnlineOrderList = onlineOrderList.stream()
					.filter(OnlineOrder -> onlineOrder.getOnlineCustomerId() != null
							? OnlineOrder.getOnlineCustomerId().equals(onlineOrder.getOnlineCustomerId())
							: OnlineOrder.getOnlineCustomerId() != null)
					.collect(Collectors.groupingBy(OnlineOrder::getOnlineCustomerId, Collectors.toList()));
		}
		for (Long ps : mapOnlineOrderList.keySet()) {
			List<OnlineOrder> psList = mapOnlineOrderList.get(ps);
			if (psList.size() == 0) {
				continue;
			}
			Map<String, Object> mapSale = new HashMap<String, Object>();
			// id
			mapSale.put("userId",
					onlineOrder.getReport() == 3 ? psList.get(0).getUserId() : psList.get(0).getOnlineCustomerId());
			// 姓名
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
			Double sumPostFee = 0.0;
			if (psList.size() > 0) {
				psList.stream().forEach(c -> {
					listSumPayment.add(c.getPayment() == null ? 0 : c.getPayment());
					listPostFee.add(c.getPostFee() == null ? 0 : c.getPostFee());
				});
				sumPayment = NumUtils.sum(listSumPayment);
				sumPostFee = NumUtils.sum(listPostFee);
			}
			// 成交金额
			mapSale.put("sumPayment", sumPayment);
			// 实际邮费
			mapSale.put("sumpostFee", sumPostFee);
			// 每单平均金额
			mapSale.put("averageAmount", NumUtils.div(sumPayment, psList.size(), 2));
			mapList.add(mapSale);
		}
		return mapList;
	}

	@Override
	public double getOnlineOrderPrice(Long commodityId) {
		Double price = onlineOrderChildDao.getOnlineOrderPrice(commodityId);
		return price == null ? 0 : price;
	}

}
