package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.roomboard.dao.FixedDao;
import com.bluewhite.personnel.roomboard.dao.HydropowerDao;
import com.bluewhite.personnel.roomboard.dao.SundryDao;
import com.bluewhite.personnel.roomboard.entity.Fixed;
import com.bluewhite.personnel.roomboard.entity.Hydropower;
import com.bluewhite.personnel.roomboard.entity.Sundry;

@Service
public class SundryServiceImpl extends BaseServiceImpl<Sundry, Long>
		implements SundryService {
	@Autowired
	private SundryDao dao;
	@Autowired
	private HydropowerDao hydropowerDao;
	@Autowired
	private FixedDao fixedDao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Sundry> findPage(Sundry sundry, PageParameter page) {
		Page<Sundry> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按宿舍查询
			if (sundry.getHostelId() != null) {
				predicate.add(cb.equal(root.get("hostelId").as(Long.class), sundry.getHostelId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Sundry> result = new PageResult<>(pages, page);
		return result;
	}

	/*新增*/
	@Override
	public Sundry addSundry(Sundry sundry) {
		Hydropower hydropower=hydropowerDao.findByMonthDateAndHostelIdAndType(sundry.getMonthDate(), sundry.getHostelId(),1);
		Hydropower hydropower2=hydropowerDao.findByMonthDateAndHostelIdAndType(sundry.getMonthDate(), sundry.getHostelId(),2);
		if(hydropower2==null){
			throw new ServiceException("请先填写当前月份的电费");
		}
		if(hydropower==null){
			throw new ServiceException("请先填写当前月份的水费");
		}
		//实际吨数
		Integer integer=hydropower.getSum();
		//标准吨数
		Integer integer2=hydropower.getTalonNum();
		Integer w;
		if (integer<=integer2) {
			w=integer;
		}else {
			w=integer2;
		}
		sundry.setWater(w*hydropower.getPrice());
		
		//实际吨数
		Integer integere=hydropower2.getSum();
		//标准吨数
		Integer integer3=hydropower2.getTalonNum();
		Integer a;
		if (integere<=integer3) {
			a=integere;
		}else {
			a=integer3;
		}
		sundry.setPower(a*hydropower.getPrice());
		if (sundry.getId()==null) {
		double allprice = 0;
		List<Fixed> fixeds=fixedDao.findByHostelId(sundry.getHostelId());
		if (fixeds.size()>0) {
		for (Fixed fixed2 : fixeds) {
			if (fixed2.getSurplusSum()>0) {
				Double aDouble=	NumUtils.sub(fixed2.getSurplusSum(),fixed2.getPrice());
				allprice+=fixed2.getPrice();
				fixed2.setSurplusSum(aDouble);
				fixedDao.save(fixed2);
			}
		}
		}
		sundry.setFixed(allprice);
		}
		sundry.setSummaryPrice(NumUtils.sum(sundry.getRent(),sundry.getWater(),sundry.getPower(),sundry.getCoal(),sundry.getFixed(),sundry.getBroadband(),sundry.getAdministration()));
		return dao.save(sundry);
	}



	
	
	
}
