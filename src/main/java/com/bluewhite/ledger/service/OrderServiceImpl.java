package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
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
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.OrderChildDao;
import com.bluewhite.ledger.dao.OrderDao;
import com.bluewhite.ledger.dao.OutStorageDao;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderChild;
import com.bluewhite.ledger.entity.OutStorage;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.onlineretailers.inventory.dao.ProcurementDao;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import com.bluewhite.onlineretailers.inventory.entity.ProcurementChild;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private ProcurementDao procurementDao;
	@Autowired
	private BaseDataDao baseDataDao;
	@Autowired
	private OrderChildDao orderChildDao;
	@Autowired
	private PutStorageService putStorageService;
	@Autowired
	private OutStorageDao outStorageDao;

	@Override
	public PageResult<Order> findPages(Order param, PageParameter page) {
		Page<Order> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			// 按客户名称
			if (!StringUtils.isEmpty(param.getCustomerName())) {
				Join<Order, OrderChild> join = root.join(root.getModel().getList("orderChilds", OrderChild.class),
						JoinType.LEFT);
				predicate.add(cb.like(join.get("customer").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getCustomerName()) + "%"));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按产品name过滤
			if (!StringUtils.isEmpty(param.getProductName())) {
				predicate.add(cb.equal(root.get("product").get("name").as(String.class),
						"%" + StringUtil.specialStrKeyword(param.getProductName()) + "%"));
			}
			// 按业务员id
			if (param.getUserId() != null) {
				Join<Order, OrderChild> join = root.join(root.getModel().getList("orderChilds", OrderChild.class),
						JoinType.LEFT);
				predicate.add(cb.equal(join.get("userId").as(String.class), param.getUserId()));
			}
			// 按产品编号过滤
			if (!StringUtils.isEmpty(param.getProductNumber())) {
				predicate.add(
						cb.equal(root.get("productNumber").as(String.class), "%" + param.getProductNumber() + "%"));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("orderDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Order> result = new PageResult<Order>(pages, page);
		return result;
	}

	@Override
	@Transactional
	public int deleteOrder(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Order order = dao.findOne(id);
					Procurement procurement = procurementDao.findByOrderId(id);
					if (procurement != null) {
						throw new ServiceException("批次号：" + order.getBacthNumber() + "产品名："
								+ order.getProduct().getName() + "的下单合同已进行生成，无法删除");
					}
					dao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	public void addOrder(Order order) {
		Order oldOrder = dao.findByBacthNumber(order.getBacthNumber());
		if (oldOrder != null) {
			throw new ServiceException("系统已有" + order.getBacthNumber() + "批次号下单合同，请不要重复添加");
		}
		order.setPrepareEnough(0);
		order.setAudit(0);
		// 新增子单
		if (!StringUtils.isEmpty(order.getOrderChild())) {
			JSONArray jsonArray = JSON.parseArray(order.getOrderChild());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				OrderChild orderChild = new OrderChild();
				orderChild.setCustomerId(jsonObject.getLong("customerId"));
				orderChild.setUserId(jsonObject.getLong("userId"));
				orderChild.setChildNumber(jsonObject.getInteger("childNumber"));
				orderChild.setChildRemark(jsonObject.getString("childRemark"));
				order.getOrderChilds().add(orderChild);
			}
		}
		dao.save(order);
	}

	@Override
	public List<Order> findList(Order param) {
		List<Order> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getProductId() != null) {
				predicate.add(cb.equal(root.get("productId").as(Long.class), param.getProductId()));
			}
			// 按是否完结
			if (param.getComplete() != null) {
				predicate.add(cb.equal(root.get("complete").as(Integer.class), param.getComplete()));
			}
			// 按批次
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("bacthNumber").as(String.class), "%" + param.getBacthNumber() + "%"));
			}
			// 按下单日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("orderDate").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			// 按业务员id
			if (param.getUserId() != null) {
				Join<Order, OrderChild> join = root.join(root.getModel().getList("orderChilds", OrderChild.class),
						JoinType.LEFT);
				predicate.add(cb.equal(join.get("userId").as(Long.class), param.getUserId()));
			}
			// 按客户id
			if (param.getCustomerId() != null) {
				Join<Order, OrderChild> join = root.join(root.getModel().getList("orderChilds", OrderChild.class),
						JoinType.LEFT);
				predicate.add(cb.equal(join.get("customerId").as(Long.class), param.getCustomerId()));
			}
			// 按是否审核
			if (param.getAudit() != null) {
				predicate.add(cb.equal(root.get("audit").as(Integer.class), param.getAudit()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	@Transactional
	public void updateOrder(Order order) {
		Order ot = dao.findOne(order.getId());
		if (ot.getAudit() == 1) {
			throw new ServiceException("批次号为" + ot.getBacthNumber() + "下单合同已审核，无法修改");
		}
		BeanCopyUtils.copyNotEmpty(order, ot, "");
		// 新增子单
		if (!StringUtils.isEmpty(ot.getOrderChild())) {
			JSONArray jsonArray = JSON.parseArray(ot.getOrderChild());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				OrderChild orderChild = new OrderChild();
				if (jsonObject.getLong("id") != null) {
					orderChild = orderChildDao.findOne(jsonObject.getLong("id"));
				}
				orderChild.setCustomerId(jsonObject.getLong("customerId"));
				orderChild.setUserId(jsonObject.getLong("userId"));
				orderChild.setChildNumber(jsonObject.getInteger("childNumber"));
				orderChild.setChildRemark(jsonObject.getString("childRemark"));
				orderChild.setOrderId(order.getId());
				orderChildDao.save(orderChild);
			}
		}
		dao.save(ot);

		if (!StringUtils.isEmpty(order.getDeleteIds())) {
			deleteOrderChild(order.getDeleteIds());
		}
	}

	@Override
	@Transactional
	public int auditOrder(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Order order = dao.findOne(id);
					if (order.getAudit() == 1) {
						throw new ServiceException(order.getOrderNumber() + "的下单合同已审核,请勿多次审核");
					}
					order.setAudit(1);
					dao.save(order);
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public int deleteOrderChild(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					orderChildDao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

	
	//公共库存未处理
	@Override
	public List<Map<String, Object>> findListSend(Order param) {
		//是否是自己的库存
		//include = 0  false
		//include = 1  true   
		CurrentUser cu = SessionManager.getUserSession();
		// 通过产品查询所有的入库单
		List<PutStorage> putStorageList = putStorageService.detailsInventory((long)473, param.getProductId());
		putStorageList  = putStorageList.stream().filter(p->{
			//排除公共库存
			if(p.getOrderOutSource().getOrderId()!=null){
				List<OrderChild> ocList = p.getOrderOutSource().getOrder().getOrderChilds();
				for(OrderChild oc : ocList){
					if(cu.getId().equals(oc.getUserId())){
						return !param.isInclude();
					}
				}
			}
			return param.isInclude();
		}).collect(Collectors.toList());
		// 需要将申请通过的库存取出
		
		
		// 通过入库单拿到所有的生产计划单
		List<Map<String, Object>> listMap = new ArrayList<>();
		putStorageList.forEach(p -> {
			Map<String, Object> map = new HashMap<String, Object>();
			if(p.getOrderOutSource().getOrderId()!=null){
				map.put("putStorageId", p.getId());
				map.put("id", p.getOrderOutSource().getOrderId());
				map.put("order", p.getOrderOutSource().getOrder());
				map.put("number", p.getSurplusNumber());
				listMap.add(map);
			}
		});
		
		//数据返回格式处理
		Map<Object, List<Map<String, Object>>> mapOnlineOrderChildList = listMap.stream()
				.collect(Collectors.groupingBy(m -> m.get("id").toString()));
		List<Map<String, Object>> result = new ArrayList<>();
		mapOnlineOrderChildList.forEach((k, slist) -> {
			Map<String, Object> nmap = new HashMap<>();
			IntSummaryStatistics sumcc = slist.stream()
					.collect(Collectors.summarizingInt(e -> Integer.valueOf(e.get("number").toString())));
			Order order = (Order) slist.get(0).get("order");
			List<Map<String, Object>> userList = new ArrayList<>();
			order.getOrderChilds().forEach(o -> {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", o.getUser().getUserName());
				map.put("id", o.getUserId());
				userList.add(map);
			});
			// 当是自己库存时，判断下单数量是否大于库存数量，当大于时取库存作为返回数量，小于则取下单数返回 。
			if(!param.isInclude()){
				int num = order.getOrderChilds().stream()
						.filter(OrderChild->cu.getId().equals(OrderChild.getUserId())).mapToInt(OrderChild::getChildNumber).sum();
				nmap.put("number", num >= (int)sumcc.getSum() ? (int)sumcc.getSum() : num);
			}else{
				nmap.put("number", sumcc.getSum());
			}
			nmap.put("bacth", order.getBacthNumber());
			nmap.put("userList", userList);
			nmap.put("putStorageId", slist.get(0).get("putStorageId"));
			result.add(nmap);
		});
		return result;
	}

}
