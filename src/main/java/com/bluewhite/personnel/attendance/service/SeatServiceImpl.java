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
import com.bluewhite.personnel.attendance.dao.SeatDao;
import com.bluewhite.personnel.attendance.entity.Seat;

@Service
public class SeatServiceImpl extends BaseServiceImpl<Seat, Long>
		implements SeatService {
	@Autowired
	private SeatDao dao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Seat> findPage(Seat seat, PageParameter page) {
		Page<Seat> pages = dao.findAll((root, query, cb) -> {
			List<Seat> predicate = new ArrayList<>();
		
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Seat> result = new PageResult<>(pages, page);
		return result;
	}
	/*
	 *新增
	 */
	@Override
	public Seat addSeat(Seat seat) {
		
		return dao.save(seat);
	}
	
	



	
	
	
}
