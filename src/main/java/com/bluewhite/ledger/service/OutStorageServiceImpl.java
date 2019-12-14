package com.bluewhite.ledger.service;

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
import com.bluewhite.common.utils.RoleUtil;
import com.bluewhite.common.utils.SalesUtils;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.ApplyVoucherDao;
import com.bluewhite.ledger.dao.OutStorageDao;
import com.bluewhite.ledger.dao.PutOutStorageDao;
import com.bluewhite.ledger.dao.SendGoodsDao;
import com.bluewhite.ledger.entity.ApplyVoucher;
import com.bluewhite.ledger.entity.OrderChild;
import com.bluewhite.ledger.entity.OutStorage;
import com.bluewhite.ledger.entity.PutOutStorage;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.ledger.entity.SendGoods;

@Service
public class OutStorageServiceImpl extends BaseServiceImpl<OutStorage, Long> implements OutStorageService {

	@Autowired
	private OutStorageDao dao;
	@Autowired
	private SendGoodsDao sendGoodsDao;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PutStorageService putStorageService;
	@Autowired
	private PutOutStorageDao putOutStorageDao;
	@Autowired
	private ApplyVoucherDao applyVoucherDao;
	@Autowired
	private OrderOutSourceService orderOutSourceService;

	@Override
	public void saveOutStorage(OutStorage outStorage) {
		if (outStorage.getId() != null) {
			OutStorage ot = dao.findOne(outStorage.getId());
			update(outStorage, ot, "");
		} else {
			if (!StringUtils.isEmpty(outStorage.getPutOutStorageIds())) {
				String[] idStrings = outStorage.getPutOutStorageIds().split(",");
				if (idStrings.length > 0) {
					for (String ids : idStrings) {
						Long id = Long.parseLong(ids);
						PutStorage putStorage = putStorageService.findOne(id);

					}
				}
			}
			outStorage.setSerialNumber(
					Constants.CPCK + StringUtil.getDate() + SalesUtils.get0LeftString((int) (dao.count() + 1), 8));
			save(outStorage);
		}
		;
	}

	@Override
	public int deleteOutStorage(String ids) {
		int i = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idStrings = ids.split(",");
			for (String idString : idStrings) {
				Long id = Long.parseLong(idString);
				OutStorage outStorage = dao.findOne(id);
				delete(id);
				i++;
			}
		}
		return i;
	}

	@Override
	public PageResult<OutStorage> findPages(PageParameter page, OutStorage param) {
		Page<OutStorage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按产品名字
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.like(root.get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按产品编号
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(cb.like(root.get("product").get("number").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductNumber()) + "%"));
			}
			// 按到货日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("arrivalTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按采购状态
			if (param.getOutStatus() != null) {
				predicate.add(cb.equal(root.get("outStatus").as(Integer.class), param.getOutStatus()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<OutStorage> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public void sendOutStorage(Long id, Integer sendNumber, String putStorage) {
		CurrentUser cu = SessionManager.getUserSession();
		SendGoods sendGoods = sendGoodsDao.findOne(id);
		// 生成出库单
		OutStorage outStorage = new OutStorage();
		outStorage.setSendGoodsId(id);
		outStorage.setArrivalNumber(sendNumber);
		outStorage.setArrivalTime(new Date());
		outStorage.setOutStatus(1);
		outStorage.setSerialNumber(
				Constants.CPCK + StringUtil.getDate() + SalesUtils.get0LeftString((int) (dao.count() + 1), 8));
		outStorage.setProductId(sendGoods.getProductId());
		outStorage.setUserStorageId(cu.getId());
		save(outStorage);

		if (!StringUtils.isEmpty(putStorage)) {
			JSONArray jsonArray = JSON.parseArray(putStorage);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Long idPut = jsonObject.getLong("id");
				Integer number = jsonObject.getInteger("number");
				PutStorage p = putStorageService.findOne(idPut);
				//入库单实际出库数量
				List<PutOutStorage> outPutStorageList = putOutStorageDao.findByPutStorageId(p.getId());
				int arrNumber = outPutStorageList.stream().mapToInt(PutOutStorage::getNumber).sum();
				//入库单剩余数量
				p.setSurplusNumber(p.getArrivalNumber() - arrNumber);
				PutOutStorage putOutStorage = new PutOutStorage();
				putOutStorage.setOutStorageId(id);
				putOutStorage.setPutStorageId(p.getId());
				//当子单填写发货数量
				if(number!=null){
					putOutStorage.setNumber(number);
					putOutStorageDao.save(putOutStorage);
				}else{
					// 当发货数量小于等于剩余数量时,当前入库单可以满足出库数量，终止循环
					if (sendGoods.getNumber() <= p.getSurplusNumber()) {
						putOutStorage.setNumber(sendGoods.getNumber());
						putOutStorageDao.save(putOutStorage);
						break;
					}
					// 当发货数量大于剩余数量时,当前入库单数量无法满足出库数量，库存相减后继续循环出库
					if (sendGoods.getNumber() > p.getSurplusNumber()) {
						putOutStorage.setNumber(p.getSurplusNumber());
						sendGoods.setNumber(sendGoods.getNumber() - p.getSurplusNumber());
						putOutStorageDao.save(putOutStorage);
					}
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getSendPutStorage(Long id) {
		List<Map<String, Object>> list = new ArrayList<>();
		//根据仓管登陆用户权限，获取不同的仓库库存
		CurrentUser cu = SessionManager.getUserSession();
		Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		SendGoods sendGoods = sendGoodsDao.findOne(id);
		// 获取登陆库管的仓库出库单剩余数量
		List<PutStorage> putStorageList = putStorageService.detailsInventory(warehouseTypeDeliveryId,
				sendGoods.getProductId());
		// 获取发货申请人自己的库存
		List<PutStorage> putStorageListSelf = putStorageList.stream().filter(p -> {
			if (p.getOrderOutSource() != null) {
				List<OrderChild> ocList = p.getOrderOutSource().getMaterialRequisition().getOrder().getOrderChilds();
				for (OrderChild oc : ocList) {
					if (sendGoods.getUserId().equals(oc.getUserId())) {
						return true;
					}
				}
			}
			return false;
		}).collect(Collectors.toList());
		if (putStorageListSelf.size() > 0) {
			putStorageListSelf.forEach(p -> {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", p.getId());
				map.put("number", p.getSurplusNumber());
				map.put("bacthNumber", p.getOrderOutSource().getMaterialRequisition().getOrder().getBacthNumber());
				map.put("serialNumber", p.getSerialNumber());
				list.add(map);
			});
		}
		// 获取申请通过库存
		// 循环申请单,将被申请人取出,同时过滤出被申请人的入库单,进行入库单的记录
		List<ApplyVoucher> applyVoucherList = applyVoucherDao.findBySendGoodsIdAndPass(id, 1);
		if (applyVoucherList.size() > 0) {
			Map<Long, List<ApplyVoucher>> mapApplyVoucher = applyVoucherList.stream()
					.collect(Collectors.groupingBy(ApplyVoucher::getApprovalUserId));
			for (Long ps1 : mapApplyVoucher.keySet()) {
				List<PutStorage> putStorageListOther = putStorageList.stream().filter(p -> {
					if (p.getOrderOutSource() != null) {
						List<OrderChild> ocList = p.getOrderOutSource().getMaterialRequisition().getOrder().getOrderChilds();
						for (OrderChild oc : ocList) {
							if (ps1.equals(oc.getUserId())) {
								return true;
							}
						}
					}
					return false;
				}).collect(Collectors.toList());
				if (putStorageListOther.size() > 0) {
					putStorageListOther.forEach(p -> {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", p.getId());
						map.put("number", p.getSurplusNumber());
						map.put("bacthNumber", p.getOrderOutSource().getMaterialRequisition().getOrder().getBacthNumber());
						map.put("serialNumber", p.getSerialNumber());
						list.add(map);
					});
				}
			}
		}
		// 获取公共库存
		List<PutStorage> publicStorageList = putStorageList.stream()
				.filter(PutStorage -> PutStorage.getPublicStock() == 1).collect(Collectors.toList());
		if (publicStorageList.size() > 0) {
			publicStorageList.forEach(p -> {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", p.getId());
				map.put("number", p.getSurplusNumber());
				map.put("bacthNumber", "");
				map.put("serialNumber", p.getSerialNumber());
				list.add(map);
			});
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getPutStorageCotDetails(Long id) {
		List<Map<String, Object>> list = new ArrayList<>();
		CurrentUser cu = SessionManager.getUserSession();
		Long warehouseTypeDeliveryId = RoleUtil.getWarehouseTypeDelivery(cu.getRole());
		if (warehouseTypeDeliveryId == null) {
			throw new ServiceException("请使用仓库管理员账号");
		}
		
		
		
		
		

		return null;
	}

	@Override
	public void sendOutStorageCot(Long id, String putStorageIds) {
		// TODO Auto-generated method stub
		
	}
}
