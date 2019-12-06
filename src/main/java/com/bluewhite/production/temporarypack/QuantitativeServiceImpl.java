package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.ledger.dao.PackingMaterialsDao;
import com.bluewhite.ledger.entity.PackingMaterials;

@Service
public class QuantitativeServiceImpl extends BaseServiceImpl<Quantitative, Long> implements QuantitativeService {

	@Autowired
	private QuantitativeDao dao;
	@Autowired
	private PackingMaterialsDao packingMaterialsDao;
	@Autowired
	private UnderGoodsDao underGoodsDao;
	@Autowired
	private QuantitativeChildDao quantitativeChildDao;

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
				quantitativeChild.setNumber(jsonObject.getInteger("number"));
				List<QuantitativeChild> quantitativeChildList = quantitativeChildDao
						.findByUnderGoodsId(jsonObject.getLong("underGoodsId"));
				UnderGoods underGoods = underGoodsDao.findOne(jsonObject.getLong("underGoodsId"));
				if (quantitativeChildList.size() > 0) {
					int numberSum = quantitativeChildList.stream().mapToInt(QuantitativeChild::getNumber).sum();
					if (underGoods.getNumber() < (numberSum + (jsonObject.getInteger("number")))) {
						throw new ServiceException("数量不足，无法新增");
					}
				}
				quantitativeChild.setSingleNumber(jsonObject.getInteger("singleNumber"));
				quantitativeChild.setSumPackageNumber(jsonObject.getInteger("sumPackageNumber"));
				quantitative.getQuantitativeChilds().add(quantitativeChild);
			}
		}
		quantitative.setFlag(0);
		quantitative.setPrint(0);
		save(quantitative);
	}

	@Override
	public void saveQuantitativeMaterials(Quantitative quantitative) {
		// 新增贴包物
		if (!StringUtils.isEmpty(quantitative.getPackingMaterialsJson())) {
			JSONArray jsonArrayMaterials = JSON.parseArray(quantitative.getPackingMaterialsJson());
			for (int i = 0; i < jsonArrayMaterials.size(); i++) {
				PackingMaterials packingMaterials = new PackingMaterials();
				JSONObject jsonObject = jsonArrayMaterials.getJSONObject(i);
				packingMaterials.setPackagingId(jsonObject.getLong("packagingId"));
				packingMaterials.setPackagingCount(jsonObject.getInteger("packagingCount"));
				quantitative.getPackingMaterials().add(packingMaterials);
			}
		}
		save(quantitative);
	}

	@Override
	public int auditQuantitative(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative quantitative = dao.findOne(id);
					quantitative.setPrint(1);
					dao.save(quantitative);
				}
			}
		}
		return count;
	}

	@Override
	public int printQuantitative(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative quantitative = dao.findOne(id);
					if (quantitative.getFlag() == 1) {
						throw new ServiceException("已发货请勿多次发货");
					}
					quantitative.setFlag(1);
					dao.save(quantitative);
				}
			}
		}
		return count;
	}

	@Override
	public int deleteQuantitative(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Quantitative quantitative = dao.findOne(id);
					if (quantitative.getPrint() == 1) {
						throw new ServiceException("已打印无法删除");
					}
					if (quantitative.getFlag() == 1) {
						throw new ServiceException("已发货无法删除");
					}
					dao.delete(id);
				}
			}
		}
		return count;
	}
}
