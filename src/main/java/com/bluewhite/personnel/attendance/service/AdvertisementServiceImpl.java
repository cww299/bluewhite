package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
			if (advertisement.getPlatformId() != null) {
				predicate.add(cb.equal(root.get("platformId").as(Long.class), advertisement.getPlatformId()));
			}
			// 按类型
			if (advertisement.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), advertisement.getType()));
			}
			if (advertisement.getRecruitId() != null) {
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
	
	/*
	 *汇总单个人培训费用 
	 */
	@Override
	public Advertisement findRecruitId(Long recruitId) {
	  List<Advertisement> advertisements=dao.findByRecruitIdAndType(recruitId,1);
	  double price=0;
	  for (Advertisement advertisement : advertisements) {
		  price=price+advertisement.getPrice();
	}
	  Advertisement advertisement=new Advertisement();
	  advertisement.setPrice(price);
		return advertisement;
	}




	
	
	
}
