package com.bluewhite.finance.wage.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.wage.dao.WageDao;
import com.bluewhite.finance.wage.entity.Wage;

@Service
public class WageServiceImpl extends BaseServiceImpl<Wage, Long> implements WageService {

	@Autowired
	private WageDao dao;

	@Override
	public PageResult<Wage> findPages(Wage param, PageParameter page) {
		Page<Wage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按类型
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}
			// 按日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Wage> result = new PageResult<>(pages, page);
		return result;
	}
	/*删除*/
	@Override
	public int deleteWage(String[] ids) {
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
	/*新增修改*/
	@Override
	public Wage addWage(Wage wage) {
		if (wage.getId()==null) {
		List<Wage> list=dao.findByUserIdAndTimeBetween(wage.getUserId(), DatesUtil.getFirstDayOfMonth(wage.getTime()), DatesUtil.getLastDayOfMonth(wage.getTime()));
			if (list.size()==0) {
			return dao.save(wage);
			}else {
				throw new ServiceException("当月该员工已有工资记录");
			}
		}else {
			return dao.save(wage);
		}
	}



	
}
