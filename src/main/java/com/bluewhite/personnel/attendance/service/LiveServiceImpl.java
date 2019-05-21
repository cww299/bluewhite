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
import com.bluewhite.personnel.attendance.dao.LiveDao;
import com.bluewhite.personnel.attendance.entity.Live;

@Service
public class LiveServiceImpl extends BaseServiceImpl<Live, Long>
		implements LiveService {
	@Autowired
	private LiveDao dao;

	
	
	//新增修改
	@Override
	public Live addLive(Live live) {
	Live lives=dao.findOne(live.getId());
		if (live.getId()!=null) {
			lives.setType(2);
			dao.save(lives);
		}
		Live live2=new Live();
		live2.setBed(live.getBed());
		live2.setHostelId(live.getHostelId());
		live2.setInLiveDate(live.getInLiveDate());
		live2.setOtLiveDate(live.getOtLiveDate());
		live2.setUserId(live.getUserId());
		live2.setLiveRemark(live.getLiveRemark());
		live2.setType(1);
		return dao.save(live2);
	}


	/**
     * 按条件查询住宿员工信息+
     */
	@Override
	public PageResult<Live> findPage(Live live, PageParameter page) {
		Page<Live> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			if (live.getHostelId() != null) {
				predicate.add(cb.equal(root.get("hostelId").as(Long.class), live.getHostelId()));
			}
			if (live.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), live.getType()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Live> result = new PageResult<>(pages, page);
		return result;
	}
}
