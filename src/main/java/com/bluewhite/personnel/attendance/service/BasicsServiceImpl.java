package com.bluewhite.personnel.attendance.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.AdvertisementDao;
import com.bluewhite.personnel.attendance.dao.BasicsDao;
import com.bluewhite.personnel.attendance.entity.Advertisement;
import com.bluewhite.personnel.attendance.entity.Basics;
import com.bluewhite.personnel.attendance.entity.Recruit;

@Service
public class BasicsServiceImpl extends BaseServiceImpl<Basics, Long>
		implements BasicsService {
	@Autowired
	private BasicsDao dao;
	@Autowired
	private AdvertisementDao advertisementDao;
	@Autowired
	private RecruitService recruitService;
	/*
	 *查询汇总数据
	 */
	@Override
	public Basics findBasics(Basics basics) {
		//查询出当月所有广告费
	List<Advertisement> list=advertisementDao.findByTypeAndTimeBetween(0,DatesUtil.getFirstDayOfMonth(basics.getTime()),DatesUtil.getLastDayOfMonth(basics.getTime()));
		//查询出当月所有培训费
	List<Advertisement> list2=advertisementDao.findByTypeAndTimeBetween(1,DatesUtil.getFirstDayOfMonth(basics.getTime()),DatesUtil.getLastDayOfMonth(basics.getTime()));
			double trainPrice=0;//培训费
			double advertisementPrice=0;//广告费
			Integer sum=0;//应邀面试人数汇总
			Integer sum2=0;//当月应聘被录取人员数量
			if (list2.size()>0) {
				for (Advertisement advertisement : list2) {
					trainPrice=trainPrice+advertisement.getPrice();
				}
			}
			if (list.size()>0) {
				for (Advertisement advertisement : list) {
					advertisementPrice=advertisementPrice+advertisement.getPrice();
				}
			}
			Recruit recruit=new Recruit();
			recruit.setTime(basics.getTime());
			List<Map<String, Object>> maps=recruitService.Statistics(recruit);
			for (Map<String, Object> map : maps) {
				 Object aInteger= map.get("mod2");
				 Object aInteger3= map.get("mod8");
				 sum=sum+Integer.parseInt(aInteger==null?"":aInteger.toString());
				 sum2=sum2+Integer.parseInt(aInteger3==null?"":aInteger3.toString());
			}
		Basics basics2=	dao.findByTimeBetween(DatesUtil.getFirstDayOfMonth(basics.getTime()),DatesUtil.getLastDayOfMonth(basics.getTime()));
		basics.setId(basics2.getId());
		basics.setRecruitUserPrice(basics2.getRecruitUserPrice()==null ? 0 : basics2.getRecruitUserPrice());//日期内面试招聘人员费用/元填写→
		basics.setTrainPrice(trainPrice);//培训费
		basics.setAdvertisementPrice(advertisementPrice);//广告费
		basics.setNumber(sum);//应邀面试人数汇总
		basics.setAdmissionNum(sum2);//当月应聘被录取人员数量
		Double d1= NumUtils.sub(advertisementPrice, basics.getRecruitUserPrice());//广告费+日期内面试招聘人员费用/元填写→
		basics.setOccupyPrice(NumUtils.div(d1,sum, 2));//每人占到应聘费用
	return basics;
	}
	/*
	 *新增修改
	 */
	@Override
	public Basics addBasics(Basics basics) {
		
		return dao.save(basics);
	}




	
	
	
}
