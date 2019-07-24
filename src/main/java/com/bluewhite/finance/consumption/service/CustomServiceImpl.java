package com.bluewhite.finance.consumption.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.consumption.dao.CustomDao;
import com.bluewhite.finance.consumption.entity.Custom;

@Service
public class CustomServiceImpl extends BaseServiceImpl<Custom, Long> implements CustomService {

	@Autowired
	private CustomDao dao;

	@Override
	public List<Custom> findCustom(Integer type, String name) {
		return dao.findByTypeAndNameLike(type, "%" + name + "%");
	}

	@Override
	public PageResult<Custom> findPages(Custom param, PageParameter page) {
		Page<Custom> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), param.getType()));
			}
			// 按名称查找
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class), "%" + param.getName() + "%"));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Custom> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deleteCustom(String ids) {
		int count = 0;

		return count;
	}

	@Override
	public List<Custom> findByType(Integer type) {
		List<Custom> customs = dao.findByType(type);
		return customs;
	}
}
