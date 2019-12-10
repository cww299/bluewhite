package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.ApplyVoucherDao;
import com.bluewhite.ledger.dao.OutStorageDao;
import com.bluewhite.ledger.dao.PackingChildDao;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.dao.SendGoodsDao;
import com.bluewhite.ledger.entity.ApplyVoucher;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.ledger.entity.SendGoods;

@Service
public class SendGoodsServiceImpl extends BaseServiceImpl<SendGoods, Long> implements SendGoodsService {

	@Autowired
	private SendGoodsDao dao;
	@Autowired
	private PackingChildDao packingChildDao;
	@Autowired
	private ApplyVoucherDao applyVoucherDao;
	@Autowired
	private PutStorageDao putStorageDao;
	@Autowired
	private OutStorageDao outStorageDao;
	@Autowired
	private OrderService orderService;
	@Override
	public PageResult<SendGoods> findPages(SendGoods param, PageParameter page) { 
		CurrentUser cu = SessionManager.getUserSession();
		
		
		
		Page<SendGoods> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按产品类型
			if (param.getProductType() != null) {
				predicate.add(cb.equal(root.get("productType").as(Integer.class), param.getProductType()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(
						cb.like(root.get("product").get("name").as(String.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getSendDate())) {
				predicate.add(cb.equal(root.get("sendDate").as(Date.class), param.getSendDate()));
			}
			// 按发货贴包日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<SendGoods> result = new PageResult<>(pages, page);
		result.getRows().forEach(s->{
			//通过销售人员和产品id查询出所属的库存，进行库存状态的更新
			//1.查出销售人员的生产计划单，有多少入库单
			//2.查出销售人员和产品id通过的申请单，获取到申请数量
			//3.查出共有库存
			// 通过产品查询所有的入库单
			Order order = new Order(); 
			order.setProductId(s.getProductId());
			order.setInclude(true);
			List<Map<String, Object>> mapsList = orderService.findListSend(order);
			int number = 0;
			for(Map<String, Object> map : mapsList ){
				number+=Integer.valueOf(map.get("number").toString());
			}
			int status = 0;
			if(s.getNumber()<number){
				status = 1;
			}
			if(number<=0){
				status = 2;
			}
			s.setStatus(status);
		});
		return result;
	}

	@Override
	public void addSendGoods(SendGoods sendGoods) {
		CurrentUser cu = SessionManager.getUserSession();
		sendGoods.setUserId(cu.getId());
		// 新增借货申请单
		List<ApplyVoucher> applyVoucherList = new ArrayList<>();
		if (!StringUtils.isEmpty(sendGoods.getApplyVoucher())) {
			JSONArray jsonArray = JSON.parseArray(sendGoods.getApplyVoucher());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ApplyVoucher applyVoucher = new ApplyVoucher();
				applyVoucher.setApplyVoucherTypeId((long)438);
				applyVoucher.setApplyVoucherKindId((long)441);
				applyVoucher.setTime(jsonObject.getDate("time"));
				applyVoucher.setNumber(jsonObject.getInteger("number"));
				applyVoucher.setApprovalUserId(jsonObject.getLong("approvalUserId"));
				applyVoucher.setUserId(cu.getId());
				applyVoucherList.add(applyVoucher);
			}
		}
		applyVoucherDao.save(applyVoucherList);
		save(sendGoods);
	}

	@Override
	public List<SendGoods> findLists(SendGoods param) {
		List<SendGoods> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomerId() != null) {
				predicate.add(cb.equal(root.get("customrId").as(Long.class), param.getCustomerId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("product").get("productId").as(Long.class), param.getProductId()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				predicate.add(cb.like(root.get("customer").get("name").as(String.class),
						"%" + param.getCustomerName() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(
						cb.like(root.get("product").get("name").as(String.class), "%" + param.getProductName() + "%"));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("order").get("bacthNumber").as(String.class),
						"%" + param.getBacthNumber() + "%"));
			}
			// 按发货日期
			if (!StringUtils.isEmpty(param.getSendDate())) {
				predicate.add(cb.equal(root.get("sendDate").as(Date.class), param.getSendDate()));
			}
			// 按发货贴包日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("sendDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 剩余数量大于0的单据
			if (param.getSurplusNumber() != null) {
				predicate.add(cb.greaterThan(root.get("surplusNumber").as(Integer.class), param.getSurplusNumber()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	@Transactional
	public int deleteSendGoods(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					List<PackingChild> sendGoodsList = packingChildDao.findBySendGoodsId(id);
					if (sendGoodsList.size() > 0) {
						throw new ServiceException("该发货单已有贴包发货单，无法删除，请先核对贴包发货单");
					}
					SendGoods sendGoods = dao.findOne(id);
					dao.delete(sendGoods);
					count++;
				}
			}
		}
		return count;
	}
}
