package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.AdvertisementDao;
import com.bluewhite.personnel.attendance.entity.Advertisement;

@Service
public class AdvertisementServiceImpl extends BaseServiceImpl<Advertisement, Long>
		implements AdvertisementService {
	@Autowired
	private AdvertisementDao dao;
	@Autowired
	private BaseDataDao baseDataDao;
	
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Advertisement> findPage(Advertisement advertisement, PageParameter page) {
		Page<Advertisement> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按平台
			if (!StringUtils.isEmpty(advertisement.getPlatformId())) {
				predicate.add(cb.equal(root.get("platformId").as(Long.class), advertisement.getPlatformId()));
			}
			// 按类型
			if (!StringUtils.isEmpty(advertisement.getType())) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), advertisement.getType()));
			}
			if (!StringUtils.isEmpty(advertisement.getRecruitId())) {
				predicate.add(cb.equal(root.get("recruitId").as(Long.class), advertisement.getRecruitId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Advertisement> result = new PageResult<>(pages, page);
		return result;
	}
	/*
	 *新增修改
	 */
	@Override
	public Advertisement addAdvertisement(Advertisement advertisement) {
		
		return dao.save(advertisement);
	}




	
	
	
}
