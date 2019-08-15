package com.bluewhite.ledger.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.RoleUtil;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.PackingChildDao;
import com.bluewhite.ledger.dao.PackingDao;
import com.bluewhite.ledger.dao.PackingMaterialsDao;
import com.bluewhite.ledger.dao.SaleDao;
import com.bluewhite.ledger.dao.SendGoodsDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.PackingMaterials;
import com.bluewhite.ledger.entity.Sale;
import com.bluewhite.ledger.entity.SendGoods;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;

@Service
public class PackingServiceImpl extends BaseServiceImpl<Packing, Long> implements PackingService {

	@Autowired
	private PackingDao dao;
	@Autowired
	private SendGoodsDao sendGoodsDao;
	@Autowired
	private PackingChildDao packingChildDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private PackingMaterialsDao packingMaterialsDao;
	@Autowired
	private MixedService mixedService;
	@Autowired
	private ReceivedMoneyService receivedMoneyService;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SaleDao saleDao;

	@Override
	public PageResult<Packing> findPages(Packing param, PageParameter page) {
		CurrentUser cu = SessionManager.getUserSession();
		if (!cu.getRole().contains("superAdmin")) {
			Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
			if (warehouseTypeDeliveryId != null) {
				param.setWarehouseTypeDeliveryId(warehouseTypeDeliveryId);
			}
		}
		Page<Packing> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按是否发货过滤
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}
			// 按贴包类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}
			// 按调拨仓库过滤
			if (param.getWarehouseTypeId() != null) {
				predicate.add(cb.equal(root.get("warehouseTypeId").as(Long.class), param.getWarehouseTypeId()));
			}

			// 按出库仓库过滤
			if (param.getWarehouseTypeDeliveryId() != null) {
				predicate.add(cb.equal(root.get("warehouseTypeDeliveryId").as(Long.class),
						param.getWarehouseTypeDeliveryId()));
			}

			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 按商品名称过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				Join<Packing, PackingChild> join = root
						.join(root.getModel().getList("packingChilds", PackingChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按批次号过滤
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				Join<Packing, PackingChild> join = root
						.join(root.getModel().getList("packingChilds", PackingChild.class), JoinType.LEFT);
				predicate.add(cb.like(join.get("batchNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按发货贴包日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("packingDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Packing> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public String getPackingNumber(Date sendDate) {
		List<Packing> packingList = dao.findByPackingDate(sendDate);
		String numberDef = null;
		List<Integer> numberList = new ArrayList<>();
		packingList.stream().forEach(p -> {
			String number = p.getNumber().substring(p.getNumber().length() - 2, p.getNumber().length() - 1);
			numberList.add(Integer.parseInt(number));
		});
		// 正序
		numberList.sort(Comparator.naturalOrder());
		for (int i = 0; i < numberList.size(); i++) {
			if (numberList.get(i) != (i + 1)) {
				numberDef = String.valueOf((i + 1));
				break;
			}
		}
		Calendar now = Calendar.getInstance();
		now.setTime(sendDate);
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		String yearString = year.substring(year.length() - 2, year.length()); // 截取最后两位
		String packingNumber = yearString + "N" + month + "Y" + day + "R"
				+ (numberDef != null ? numberDef : (packingList.size() + 1)) + "D";
		return packingNumber;
	}

	@Override
	@Transactional
	public Packing addPacking(Packing packing) {
		CurrentUser cu = SessionManager.getUserSession();
		if (cu.getRole().contains("superAdmin")) {
			throw new ServiceException("请使用仓库管理员账号登录添加");
		}
		long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		packing.setFlag(0);
		packing.setWarehouseTypeDeliveryId(warehouseTypeDeliveryId);
		// 新增子单
		if (!StringUtils.isEmpty(packing.getChildPacking())) {
			JSONArray jsonArray = JSON.parseArray(packing.getChildPacking());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				PackingChild packingChild = new PackingChild();
				if (jsonObject.getLong("packingChildId") != null) {
					packingChild = packingChildDao.findOne(jsonObject.getLong("packingChildId"));
					if (packingChild.getFlag() == 1) {
						throw new ServiceException("贴报单已发货，无法修改");
					}
				}
				// 蓝白现场仓库发货 改变待发货单的剩余数量
				if (jsonObject.getLong("sendGoodsId") != null) {
					packingChild.setSurplusNumber(packingChild.getCount());
					SendGoods sendGoods = sendGoodsDao.findOne(jsonObject.getLong("sendGoodsId"));
					sendGoods.setSurplusNumber(sendGoods.getSurplusNumber() - packingChild.getCount());
					sendGoodsDao.save(sendGoods);
					packingChild.setSendGoodsId(sendGoods.getId());
					packingChild.setBacthNumber(sendGoods.getBacthNumber());
					packingChild.setProductId(sendGoods.getProductId());
				}
				// 八号成品仓库发货
				if (jsonObject.getLong("lastPackingChildId") != null) {
					PackingChild packingChildOld = packingChildDao.findOne(jsonObject.getLong("lastPackingChildId"));
					packingChild.setLastPackingChildId(jsonObject.getLong("lastPackingChildId"));
					Integer surplusNumber = jsonObject.getLong("packingChildId") != null
							? (packingChildOld.getSurplusNumber() + packingChild.getCount())
							: packingChildOld.getSurplusNumber();
					if (surplusNumber < jsonObject.getInteger("count")) {
						throw new ServiceException("发货数量不能大于剩余数量");
					}
					packingChildOld.setSurplusNumber(surplusNumber - jsonObject.getInteger("count"));
					packingChildDao.save(packingChildOld);
					packingChild.setSurplusNumber(packingChild.getCount());
					packingChild.setBacthNumber(packingChildOld.getBacthNumber());
					packingChild.setProductId(packingChildOld.getProductId());
					packingChild.setCount(jsonObject.getInteger("count"));
					packingChild.setConfirm(0);
					packingChild.setFlag(0);
				}
				packingChild.setWarehouseTypeDeliveryId(warehouseTypeDeliveryId);
				packingChild.setCustomerId(packing.getCustomerId());
				packingChild.setWarehouseTypeId(packing.getWarehouseTypeId());
				packingChild.setType(packing.getType());
				packing.getPackingChilds().add(packingChild);
			}
		}
		// 新增贴包物
		if (!StringUtils.isEmpty(packing.getChildPacking())) {
			JSONArray jsonArrayMaterials = JSON.parseArray(packing.getPackingMaterialsJson());
			for (int i = 0; i < jsonArrayMaterials.size(); i++) {
				PackingMaterials packingMaterials = new PackingMaterials();
				JSONObject jsonObject = jsonArrayMaterials.getJSONObject(i);
				if (jsonObject.getLong("packingMaterialsId") != null) {
					packingMaterials = packingMaterialsDao.findOne(jsonObject.getLong("packingMaterialsId"));
				}
				packingMaterials.setPackagingId(jsonObject.getLong("packagingId"));
				packingMaterials.setPackagingCount(jsonObject.getInteger("packagingCount"));
				packing.getPackingMaterials().add(packingMaterials);
			}
		}
		dao.save(packing);
		return packing;
	}

	@Override
	public int sendPacking(String ids, Date time) {
		int count = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				Long idLong = Long.valueOf(id);
				Packing packing = dao.findOne(idLong);
				time = (time == null ? packing.getPackingDate() : time);
				if (packing.getFlag() == 1) {
					throw new ServiceException("贴报单已发货，请勿重复发货");
				}
				// 已发货
				packing.setFlag(1);
				List<PackingChild> packingChildList = packing.getPackingChilds();
				for (PackingChild pc : packingChildList) {
					// 已发货
					pc.setFlag(1);
					pc.setSendDate(time == null ? packing.getPackingDate() : time);
					// 生成财务销售单
					Sale sale = new Sale();
					sale.setProductId(pc.getProductId());
					sale.setCustomerId(pc.getCustomerId());
					sale.setBacthNumber(pc.getBacthNumber());
					// 生成销售编号
					sale.setSaleNumber(Constants.XS + "-" + sdf.format(time) + "-" + SalesUtils.get0LeftString(
							packingChildDao.findBySendDateBetween(time, DatesUtil.getLastDayOftime(time)).size(), 4));
					// 未审核
					sale.setAudit(0);
					// 不转批次
					sale.setNewBacth(0);
					// 未拥有版权
					sale.setCopyright(0);
					// 未收货
					sale.setDelivery(1);
					// 业务员未确认数据
					sale.setDeliveryStatus(0);
					// 价格
					sale.setPrice(0.0);
					// 判定是否拥有版权
					if (pc.getProduct().getName().contains(Constants.LX)
							|| pc.getProduct().getName().contains(Constants.KT)
							|| pc.getProduct().getName().contains(Constants.MW)
							|| pc.getProduct().getName().contains(Constants.BM)
							|| pc.getProduct().getName().contains(Constants.LP)
							|| pc.getProduct().getName().contains(Constants.AB)
							|| pc.getProduct().getName().contains(Constants.ZMJ)
							|| pc.getProduct().getName().contains(Constants.XXYJN)) {
						sale.setCopyright(1);
					}
					// 判定是否更换客户发货，更换客户发货变成新批次，->Y
					Order order = orderDao.findByBacthNumber(pc.getBacthNumber());
					if (order.getInternal() != 1 && order.getCustomerId() != pc.getCustomerId()) {
						sale.setBacthNumber(pc.getBacthNumber().substring(0, pc.getBacthNumber().length() - 1) + "Y");
						sale.setNewBacth(1);
					}
					saleDao.save(sale);
				}
				dao.save(packing);
				count++;
			}
		}
		return count;
	}

	@Override
	public PageResult<PackingChild> findPackingChildPage(PackingChild param, PageParameter page) {
		CurrentUser cu = SessionManager.getUserSession();
		if (!cu.getRole().contains("superAdmin")) {
			Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
			if (warehouseTypeDeliveryId != null) {
				param.setWarehouseTypeId(warehouseTypeDeliveryId);
			}
		}
		Page<PackingChild> pages = packingChildDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 是否发货
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			// 按贴包类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}

			// 按调拨仓库过滤
			if (param.getWarehouseTypeId() != null) {
				predicate.add(cb.equal(root.get("warehouseTypeId").as(Long.class), param.getWarehouseTypeId()));
			}

			// 调拨仓库是否确认数量
			if (param.getConfirm() != null) {
				predicate.add(cb.equal(root.get("confirm").as(Long.class), param.getConfirm()));
			}

			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(
						cb.equal(root.get("product").get("name").as(Long.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<PackingChild> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deletePacking(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Packing packing = dao.findOne(id);
					if (packing.getFlag() == 1) {
						throw new ServiceException("贴报单已发货，无法删除，请先核对发货单");
					}
					List<PackingChild> packingChildList = packing.getPackingChilds();
					packingChildList.stream().forEach(p -> {
						SendGoods sendGoods = p.getSendGoods();
						if (sendGoods != null) {
							sendGoods.setSurplusNumber(sendGoods.getSurplusNumber() + p.getCount());
							sendGoodsDao.save(sendGoods);
						}
						// 当为八号调拨单发货时，找到调拨单，删除恢复调拨单数量
						if (p.getLastPackingChildId() != null) {
							PackingChild packingChild = packingChildDao.findOne(p.getLastPackingChildId());
							if (packingChild != null) {
								packingChild.setSurplusNumber(packingChild.getSurplusNumber() + p.getCount());
							}
							packingChildDao.save(packingChild);
						}
					});
					dao.delete(packing);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int deletePackingChild(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					PackingChild packingChild = packingChildDao.findOne(id);
					if (packingChild.getFlag() == 1) {
						throw new ServiceException("贴报单已发货，无法删除，请先核对发货单");
					}
					SendGoods sendGoods = packingChild.getSendGoods();
					sendGoods.setSurplusNumber(sendGoods.getSurplusNumber() + packingChild.getCount());
					sendGoodsDao.save(sendGoods);
					packingChildDao.delete(packingChild);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int deletePackingMaterials(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					packingMaterialsDao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public int confirmPackingChild(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				PackingChild packingChild = packingChildDao.findOne(Long.valueOf(id));
				if (packingChild.getConfirm() == 1) {
					throw new ServiceException("调拨单已审核，请勿再次审核");
				}
				if (packingChild.getWarehouseId() == null) {
					throw new ServiceException("入库仓库不能为空，请选择");
				}
				if (packingChild.getConfirmNumber() == null) {
					throw new ServiceException("确认入库数量未填写，请先填写确认入库数量");
				}
				if (packingChild != null) {
					Product product = packingChild.getProduct();
					packingChild.setConfirm(1);
					// 创建商品的库存
					Set<Inventory> inventorys = product.getInventorys();
					// 获取库存
					Inventory inventory = inventoryDao.findByProductIdAndWarehouseId(product.getId(),
							packingChild.getWarehouseId());
					if (inventory == null) {
						inventory = new Inventory();
						inventory.setProductId(product.getId());
						inventory.setNumber(packingChild.getConfirmNumber());
						inventory.setWarehouseId(packingChild.getWarehouseId());
						inventorys.add(inventory);
						product.setInventorys(inventorys);
					} else {
						inventory.setNumber(inventory.getNumber() + packingChild.getConfirmNumber());
					}
					productDao.save(product);
					packingChildDao.save(packingChild);
				}
				;
				count++;
			}
		}
		return count;
	}

	@Override
	public PackingChild updateInventoryPackingChild(PackingChild packingChild) {
		if (packingChild.getId() != null) {
			PackingChild oldPackingChild = packingChildDao.findOne(packingChild.getId());
			if (oldPackingChild.getConfirm() == 1) {
				throw new ServiceException("调拨单已确认入库，无法修改");
			}
			BeanCopyUtils.copyNotEmpty(packingChild, oldPackingChild, "");
			packingChildDao.save(oldPackingChild);
		}
		return packingChild;
	}

	@Override
	public List<PackingChild> packingChildList(PackingChild param) {
		CurrentUser cu = SessionManager.getUserSession();
		if (!cu.getRole().contains("superAdmin")) {
			Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
			if (warehouseTypeDeliveryId != null) {
				param.setWarehouseTypeId(warehouseTypeDeliveryId);
			}
		}
		List<PackingChild> result = packingChildDao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 是否发货
			if (param.getFlag() != null) {
				predicate.add(cb.equal(root.get("flag").as(Integer.class), param.getFlag()));
			}

			// 按贴包类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}

			// 按调拨仓库过滤
			if (param.getWarehouseTypeId() != null) {
				predicate.add(cb.equal(root.get("warehouseTypeId").as(Long.class), param.getWarehouseTypeId()));
			}

			// 调拨仓库是否确认数量
			if (param.getConfirm() != null) {
				predicate.add(cb.equal(root.get("confirm").as(Long.class), param.getConfirm()));
			}

			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(
						cb.equal(root.get("product").get("name").as(Long.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 剩余数量大于0的单据
			if(param.getSurplusNumber()!=null){
				predicate.add(cb.greaterThan(root.get("surplusNumber").as(Integer.class), param.getSurplusNumber()));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public int cancelConfirmPackingChild(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String id : idStrings) {
				PackingChild packingChild = packingChildDao.findOne(Long.valueOf(id));
				List<PackingChild> oldPackingChild = packingChildDao.findByLastPackingChildId(Long.valueOf(id));
				if (oldPackingChild.size() > 0) {
					throw new ServiceException(packingChild.getBacthNumber() + packingChild.getProduct().getName()
							+ "的调拨单已经有发货单记录，需要取消审核，请先删除发货记录");
				}
				if (packingChild.getConfirm() == 0) {
					throw new ServiceException("调拨单未审核，请勿取消审核");
				}
				if (packingChild != null) {
					Product product = packingChild.getProduct();
					packingChild.setConfirm(0);
					// 创建商品的库存
					Set<Inventory> inventorys = product.getInventorys();
					// 获取库存
					Inventory inventory = inventoryDao.findByProductIdAndWarehouseId(product.getId(),
							packingChild.getWarehouseId());
					inventory.setNumber(inventory.getNumber() - packingChild.getConfirmNumber());
					productDao.save(product);
					packingChildDao.save(packingChild);
				}
				;
				count++;
			}
		}
		return count;
	}

}
