package com.bluewhite.personnel.attendance.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	Date orderTimeBegin=DatesUtil.getFirstDayOfMonth(basics.getTime()); 
	Date orderTimeEnd=DatesUtil.getLastDayOfMonth(basics.getTime());
		//查询出当月所有广告费
	List<Advertisement> list=advertisementDao.findByType(0);
	//过滤 1.开始时间在区间时间之前 结束时间在区间时间之后（4.1  5.1~5.31  6.1） 2. 开始时间在区间时间之后 结束时间在区间时间之后 （5.2  5.1~5.31  6.1）
	//过滤 3.开始时间在区间时间之前 结束时间在区间时间之前 （4.1  5.1~5.31  5.30）4. 开始时间在区间时间之后 结束时间在区间时间之前（5.2  5.1~5.31  5.3）
	List<Advertisement> listFilter= list.stream().filter(Advertisement->(Advertisement.getStartTime().compareTo(orderTimeBegin)!=1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=-1)
									  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getStartTime().compareTo(orderTimeEnd)!=1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=-1)
									  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=1 && Advertisement.getEndTime().compareTo(orderTimeBegin)!=-1 &&  Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
									  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
										).collect(Collectors.toList());
		//查询出当月所有培训费
	List<Advertisement> list2=advertisementDao.findByType(1);
	List<Advertisement> listFilter2= list2.stream().filter(Advertisement->(Advertisement.getStartTime().compareTo(orderTimeBegin)!=1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=-1)
			  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getStartTime().compareTo(orderTimeEnd)!=1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=-1)
			  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=1 && Advertisement.getEndTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
			  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
				).collect(Collectors.toList());
	
			double trainPrice=0;//培训费
			double advertisementPrice=0;//广告费
			Integer sum=0;//应邀面试人数汇总
			Integer sum2=0;//当月应聘被录取人员数量
			if (listFilter2.size()>0) {
				for (Advertisement advertisement : listFilter2) {
					//过滤 1.开始时间在区间时间之前 结束时间在区间时间之后（4.1  5.1~5.31  6.1）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=-1) {
						long day=DatesUtil.getDaySub(advertisement.getEndTime(),advertisement.getStartTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeEnd,orderTimeBegin);//筛选后一共多少天
						trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),2),advertisement.getPrice());//筛选后的广告费
						
					}
					//2. 开始时间在区间时间之后 结束时间在区间时间之后 （5.2  5.1~5.31  6.1）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && advertisement.getStartTime().compareTo(orderTimeEnd)!=1&& advertisement.getEndTime().compareTo(orderTimeEnd)!=-1) {
						long day=DatesUtil.getDaySub(advertisement.getEndTime(),advertisement.getStartTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(advertisement.getEndTime(),orderTimeBegin);//筛选后一共多少天
						trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),2),advertisement.getPrice());//筛选后的广告费
					}
					//过滤 3.开始时间在区间时间之前 结束时间在区间时间之前 （4.1  5.1~5.31  5.30）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=1&& advertisement.getEndTime().compareTo(orderTimeBegin)!=-1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=1) {
						long day=DatesUtil.getDaySub(advertisement.getEndTime(),advertisement.getStartTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeEnd,advertisement.getStartTime());//筛选后一共多少天
						trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),2),advertisement.getPrice());//筛选后的广告费
					}
					//4. 开始时间在区间时间之后 结束时间在区间时间之前（5.2  5.1~5.31  5.3）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=1) {
						trainPrice=trainPrice+advertisement.getPrice();
					}
				}
			}
			if (listFilter.size()>0) {
				for (Advertisement advertisement : listFilter) {
					//过滤 1.开始时间在区间时间之前 结束时间在区间时间之后（4.1  5.1~5.31  6.1）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=-1) {
						long day=DatesUtil.getDaySub(advertisement.getEndTime(),advertisement.getStartTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeEnd,orderTimeBegin);//筛选后一共多少天
						advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),2),advertisement.getPrice());//筛选后的广告费
						
					}
					//2. 开始时间在区间时间之后 结束时间在区间时间之后 （5.2  5.1~5.31  6.1）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=-1) {
						long day=DatesUtil.getDaySub(advertisement.getEndTime(),advertisement.getStartTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(advertisement.getEndTime(),orderTimeBegin);//筛选后一共多少天
						advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),2),advertisement.getPrice());//筛选后的广告费
					}
					//过滤 3.开始时间在区间时间之前 结束时间在区间时间之前 （4.1  5.1~5.31  5.30）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=1) {
						long day=DatesUtil.getDaySub(advertisement.getEndTime(),advertisement.getStartTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeEnd,advertisement.getStartTime());//筛选后一共多少天
						advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),2),advertisement.getPrice());//筛选后的广告费
					}
					//4. 开始时间在区间时间之后 结束时间在区间时间之前（5.2  5.1~5.31  5.3）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=1) {
						advertisementPrice=advertisementPrice+advertisement.getPrice();
					}
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
		if(basics2==null){
			 basics2=new Basics();
		}
		basics2.setRecruitUserPrice(basics2.getRecruitUserPrice()==null ? 0.0 : basics2.getRecruitUserPrice());//日期内面试招聘人员费用/元填写→
		basics2.setTrainPrice(trainPrice);//培训费
		basics2.setAdvertisementPrice(advertisementPrice);//广告费
		basics2.setNumber(sum);//应邀面试人数汇总
		basics2.setAdmissionNum(sum2);//当月应聘被录取人员数量
		Double d1= NumUtils.sum(advertisementPrice, basics2.getRecruitUserPrice());//广告费+日期内面试招聘人员费用/元填写→
		if(sum!=0){
			basics2.setOccupyPrice(NumUtils.div(d1,sum, 2));//每人占到应聘费用
		}else{
			basics2.setOccupyPrice(0.0);
		}
	return basics2;
	}
	/*
	 *新增修改
	 */
	@Override
	public Basics addBasics(Basics basics) {
		
		return dao.save(basics);
	}

public static void main(String[] args) {
	String aString="2019-06-02 11:00:00";
	String aString2="2019-06-03 11:00:00";
	int c= aString.compareTo(aString2);
	System.err.println(c);
}


	
	
	
}
