package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderChild;

@Service
public class QuantitativeServiceImpl extends BaseServiceImpl<Quantitative, Long> implements QuantitativeService {
	
	@Autowired
	private QuantitativeDao dao;

	@Override
	public PageResult<Quantitative> findPages(Quantitative param, PageParameter page) {
			Page<Quantitative> pages = dao.findAll((root, query, cb) -> {
				List<Predicate> predicate = new ArrayList<>();
				// 按id过滤
				if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
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
				// 按产品编号过滤
				if (!StringUtils.isEmpty(param.getProductNumber())) {
					predicate.add(
							cb.equal(root.get("productNumber").as(String.class), "%" + param.getProductNumber() + "%"));
				}
				// 按下单日期
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
				return null;
			}, page);
			PageResult<Quantitative> result = new PageResult<>(pages, page);
			return result;
		}

	@Override
	public void saveQuantitative(Quantitative quantitative) {
		// 新增子单
		if (!StringUtils.isEmpty(quantitative.getChild())) {
			JSONArray jsonArray = JSON.parseArray(quantitative.getChild());
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				QuantitativeChild quantitativeChild = new QuantitativeChild();
				quantitativeChild.setUnderGoodsId(jsonObject.getLong("underGoodsId"));
				quantitativeChild.setSingleNumber(jsonObject.getInteger("SingleNumber"));
				quantitativeChild.setSumPackageNumber(jsonObject.getInteger("SumPackageNumber"));
				quantitative.getQuantitativeChilds().add(quantitativeChild);
			}
		}
		save(quantitative);
	}
}
