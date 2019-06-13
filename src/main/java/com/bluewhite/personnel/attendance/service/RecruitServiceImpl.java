package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.RecruitDao;
import com.bluewhite.personnel.attendance.entity.Recruit;

@Service
public class RecruitServiceImpl extends BaseServiceImpl<Recruit, Long>
		implements RecruitService {
	@Autowired
	private RecruitDao dao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Recruit> findPage(Recruit sundry, PageParameter page) {
		Page<Recruit> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按宿舍查询
			
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Recruit> result = new PageResult<>(pages, page);
		return result;
	}
	@Override
	public Recruit addRecruit(Recruit recruit) {
		
		return dao.save(recruit);
	}
	@Override
	public boolean deleteRecruit(String ids) {
		// TODO Auto-generated method stub
		return false;
	}




	
	
	
}
