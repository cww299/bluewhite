package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.dao.coeffcientDao;
import com.bluewhite.personnel.roomboard.entity.coefficient;

@Service
public class CoefficientServiceImpl extends BaseServiceImpl<coefficient, Long>
		implements CoefficientService {
	@Autowired
	private coeffcientDao dao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<coefficient> findPage(coefficient coefficient, PageParameter page) {
		Page<coefficient> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按部门查找
			if (!StringUtils.isEmpty(coefficient.getOrgNameId())) {
				predicate.add(cb.equal(root.get("orgName").get("id").as(Long.class), coefficient.getOrgNameId()));
			}
			// 按职位查找
			if (!StringUtils.isEmpty(coefficient.getPositionId())) {
				predicate.add(cb.equal(root.get("position").get("id").as(Long.class), coefficient.getPositionId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<coefficient> result = new PageResult<>(pages, page);
		return result;
	}
	
	/*
	 *新增修改
	 */
	@Override
	public coefficient addCoefficient(coefficient coefficient) {
		coefficient	coefficients=dao.findByOrgNameIdAndPositionId(coefficient.getOrgNameId(), coefficient.getPositionId());
		if (coefficients!=null) {
			throw new ServiceException("该岗位已经添加基础系数 请勿重复添加");
		}else{
			return dao.save(coefficient);
		}
	}
	/*
	 *删除
	 */
	@Override
	public int deletes(String[] ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				dao.delete(id); 
				count++;
			}
		}
		return count;
	}
	
}
