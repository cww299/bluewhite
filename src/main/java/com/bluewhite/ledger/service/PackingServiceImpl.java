package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.PackingDao;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.PackingChild;

@Service
public class PackingServiceImpl extends BaseServiceImpl<Packing, Long> implements PackingService {

	@Autowired
	private PackingDao dao;

	@Override
	public PageResult<Packing> findPages(Packing param, PageParameter page) {
		Page<Packing> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按客户id过滤
			if (param.getCustomrId() != null) {
				predicate.add(cb.equal(root.get("customrId").as(Long.class), param.getCustomrId()));
			}
			// 按产品id过滤
			if (param.getProductId() != null) {
				predicate
						.add(cb.equal(root.get("packingChilds").get("productId").as(Long.class), param.getProductId()));
			}
			// 按批次查找
			if (!StringUtils.isEmpty(param.getBacthNumber())) {
				predicate.add(cb.like(root.get("packingChilds").get("bacthNumber").as(String.class),
						"%" + param.getBacthNumber() + "%"));
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
	public String getPackingNumber() {
		Calendar now = Calendar.getInstance();
		List<Packing> PackingList = dao.findByPackingDate(now.getTime());
		String year = String.valueOf(now.get(Calendar.YEAR));
		String month =  String.valueOf(now.get(Calendar.MONTH) + 1);
		String day =  String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		String yearString = year.substring(year.length() -2,year.length());   //截取最后两位
		String packingNumber = yearString+"N"+month+"Y"+day+"R" +(PackingList.size()+1)+"D";
		return packingNumber;
	}

	@Override
	public Packing addPacking(Packing packing) {
		packing.setPackingDate(packing.getPackingDate()!=null ? packing.getPackingDate() : new Date());
		// 新增子单
		if (!StringUtils.isEmpty(packing.getChildPacking())) { 
			JSONArray jsonArray = JSON.parseArray(packing.getChildPacking());
			for (int i = 0; i < jsonArray.size(); i++) {
				PackingChild packingChild = new PackingChild();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				packingChild.setBacthNumber(jsonObject.getString("bacthNumber"));
				packingChild.setProductId(jsonObject.getLong("productId"));
				packingChild.setCount(jsonObject.getInteger("count"));
				packing.getPackingChilds().add(packingChild);
			}
		}
		dao.save(packing);
 		return packing;
	}
}
