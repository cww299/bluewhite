package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.roomboard.dao.AdvertisementDao;
import com.bluewhite.personnel.roomboard.dao.BasicsDao;
import com.bluewhite.personnel.roomboard.dao.PlanDao;
import com.bluewhite.personnel.roomboard.dao.RecruitDao;
import com.bluewhite.personnel.roomboard.dao.RewardDao;
import com.bluewhite.personnel.roomboard.entity.Advertisement;
import com.bluewhite.personnel.roomboard.entity.Basics;
import com.bluewhite.personnel.roomboard.entity.Plan;
import com.bluewhite.personnel.roomboard.entity.Recruit;
import com.bluewhite.personnel.roomboard.entity.Reward;

@Service
public class BasicsServiceImpl extends BaseServiceImpl<Basics, Long>
		implements BasicsService {
	@Autowired
	private BasicsDao dao;
	@Autowired
	private AdvertisementDao advertisementDao;
	@Autowired
	private RecruitService recruitService;
	@Autowired
	private BaseDataDao baseDataDao;
	@Autowired
	private RecruitDao recruitDao;
	@Autowired
	private RewardDao rewardDao;
	@Autowired
	private PlanDao planDao;
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
	List<Advertisement> listFilter= list.stream().filter(Advertisement->!Advertisement.getPlatformId().equals(294) && (Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
									  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==1 && Advertisement.getStartTime().compareTo(orderTimeEnd)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
									  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeBegin)==1 &&  Advertisement.getEndTime().compareTo(orderTimeEnd)==-1)
									  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
										).collect(Collectors.toList());
		//查询出当月所有培训费
	List<Advertisement> list2=advertisementDao.findByType(1);
	List<Advertisement> listFilter2= list2.stream().filter(Advertisement->(Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
			  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==1 && Advertisement.getStartTime().compareTo(orderTimeEnd)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
			  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeBegin)==1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==-1)
			  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
				).collect(Collectors.toList());
	
			double trainPrice=0;//培训费
			double advertisementPrice=0;//广告费
			Integer sum=0;//应邀面试人数汇总
			double sum2=0;//当月应聘被录取人员数量
			double sum3=0;//计划招聘人数
			if (listFilter2.size()>0) {
				for (Advertisement advertisement : listFilter2) {
					//过滤 1.开始时间在区间时间之前 结束时间在区间时间之后（4.1  5.1~5.31  6.1）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && advertisement.getEndTime().compareTo(orderTimeEnd)==1) {
						long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeBegin,orderTimeEnd);//筛选后一共多少天
						trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
						
					}
					//2. 开始时间在区间时间之后 结束时间在区间时间之后 （5.2  5.1~5.31  6.1）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)==1 && advertisement.getStartTime().compareTo(orderTimeEnd)==-1&& advertisement.getEndTime().compareTo(orderTimeEnd)==1) {
						long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(advertisement.getStartTime(),orderTimeEnd);//筛选后一共多少天
						trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
					}
					//过滤 3.开始时间在区间时间之前 结束时间在区间时间之前 （4.1  5.1~5.31  5.30）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)==-1&& advertisement.getEndTime().compareTo(orderTimeBegin)==1 && advertisement.getEndTime().compareTo(orderTimeEnd)==-1) {
						long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeBegin,advertisement.getEndTime());//筛选后一共多少天
						trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
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
					if (advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && advertisement.getEndTime().compareTo(orderTimeEnd)==1) {
						long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeBegin,orderTimeEnd);//筛选后一共多少天
						advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
						
					}
					//2. 开始时间在区间时间之后 结束时间在区间时间之后 （5.2  5.1~5.31  6.1）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)==1 && advertisement.getStartTime().compareTo(orderTimeEnd)==-1 && advertisement.getEndTime().compareTo(orderTimeEnd)==1) {
						long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(advertisement.getStartTime(),orderTimeEnd);//筛选后一共多少天
						advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
					}
					//过滤 3.开始时间在区间时间之前 结束时间在区间时间之前 （4.1  5.1~5.31  5.30）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && advertisement.getEndTime().compareTo(orderTimeBegin)==1 && advertisement.getEndTime().compareTo(orderTimeEnd)==-1) {
						long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
						long day1=DatesUtil.getDaySub(orderTimeBegin,advertisement.getEndTime());//筛选后一共多少天
						advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
					}
					//4. 开始时间在区间时间之后 结束时间在区间时间之前（5.2  5.1~5.31  5.3）
					if (advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=1) {
						advertisementPrice=advertisementPrice+advertisement.getPrice();
					}
				}
			}
			Recruit recruit=new Recruit();
			recruit.setTime(basics.getTime());
			List<Plan> plans=planDao.findByTimeBetween(orderTimeBegin, orderTimeEnd);//当前月份计划招聘的人
			for (Plan plan : plans) {
				sum3=NumUtils.sum(sum3, NumUtils.mul(plan.getNumber(),plan.getCoefficient()));
			}
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
		basics2.setPlanNumber(sum3);//计划招聘的人数
		Double d1= NumUtils.sum(advertisementPrice, basics2.getRecruitUserPrice());//广告费+日期内面试招聘人员费用/元填写→
		if (sum2!=0) {
			basics2.setSharePrice(NumUtils.div(d1,sum2,2));//摊到的应聘费用
		}else{
			basics2.setSharePrice(0.0);//摊到的应聘费用
		}
		if (sum3!=0) {
			basics2.setPlanPrice(NumUtils.div(d1,sum3,2));//计划摊到的费用
		}else{
			basics2.setPlanPrice(0.0);//计划摊到的费用
		}
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
	/*
	 *部门汇总
	 */
	@Override
	public List<Map<String, Object>> findBasicsSummary(Basics basics) {
		List<Recruit> list= recruitDao.findByTimeBetween(DatesUtil.getFirstDayOfMonth(basics.getTime()), DatesUtil.getLastDayOfMonth(basics.getTime()));
		List<Map<String, Object>> allList = new ArrayList<>();
		Map<String, Object> allMap =null;
		Map<Long, List<Recruit>> map = list.stream()
				.filter(Recruit -> Recruit.getOrgNameId() != null)
				.collect(Collectors.groupingBy(Recruit::getOrgNameId, Collectors.toList()));
		Basics basics2= findBasics(basics);
		for (Long ps1 : map.keySet()) {
			allMap =new HashMap<>();
			List<Recruit> psList1 = map.get(ps1);
			Long f=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(1) /*&& Recruit.getUser().getQuit().equals(0)*/).count();//已入职且在职
			//得到入职且在职的人
			List<Recruit> list2= psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(1) && Recruit.getUser().getQuit().equals(0)).collect(Collectors.toList());
			BaseData baseData=baseDataDao.findOne(ps1);
			String string= baseData.getName();
		    List<Advertisement> list3=advertisementDao.findByOrgNameIdAndType(ps1, 0);
		    Date orderTimeBegin=DatesUtil.getFirstDayOfMonth(basics.getTime()); 
			Date orderTimeEnd=DatesUtil.getLastDayOfMonth(basics.getTime());
		    List<Advertisement> listFilter= list3.stream().filter(Advertisement->!Advertisement.getPlatformId().equals(294) && (Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
					  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==1 && Advertisement.getStartTime().compareTo(orderTimeEnd)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
					  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeBegin)==1 &&  Advertisement.getEndTime().compareTo(orderTimeEnd)==-1)
					  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
						).collect(Collectors.toList());
		    double advertisementPrice=0;//定向招聘费用
		    if (list3.size()>0) {
		    		for (Advertisement advertisement1 : listFilter) {
						//过滤 1.开始时间在区间时间之前 结束时间在区间时间之后（4.1  5.1~5.31  6.1）
						if (advertisement1.getStartTime().compareTo(orderTimeBegin)==-1 && advertisement1.getEndTime().compareTo(orderTimeEnd)==1) {
							long day=DatesUtil.getDaySub(advertisement1.getStartTime(),advertisement1.getEndTime());//这条记录一共多少天
							long day1=DatesUtil.getDaySub(orderTimeBegin,orderTimeEnd);//筛选后一共多少天
							advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement1.getPrice());//筛选后的广告费
							
						}
						//2. 开始时间在区间时间之后 结束时间在区间时间之后 （5.2  5.1~5.31  6.1）
						if (advertisement1.getStartTime().compareTo(orderTimeBegin)==1 && advertisement1.getStartTime().compareTo(orderTimeEnd)==-1 && advertisement1.getEndTime().compareTo(orderTimeEnd)==1) {
							long day=DatesUtil.getDaySub(advertisement1.getStartTime(),advertisement1.getEndTime());//这条记录一共多少天
							long day1=DatesUtil.getDaySub(advertisement1.getStartTime(),orderTimeEnd);//筛选后一共多少天
							advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement1.getPrice());//筛选后的广告费
						}
						//过滤 3.开始时间在区间时间之前 结束时间在区间时间之前 （4.1  5.1~5.31  5.30）
						if (advertisement1.getStartTime().compareTo(orderTimeBegin)==-1 && advertisement1.getEndTime().compareTo(orderTimeBegin)==1 && advertisement1.getEndTime().compareTo(orderTimeEnd)==-1) {
							long day=DatesUtil.getDaySub(advertisement1.getStartTime(),advertisement1.getEndTime());//这条记录一共多少天
							long day1=DatesUtil.getDaySub(orderTimeBegin,advertisement1.getEndTime());//筛选后一共多少天
							advertisementPrice=advertisementPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement1.getPrice());//筛选后的广告费
						}
						//4. 开始时间在区间时间之后 结束时间在区间时间之前（5.2  5.1~5.31  5.3）
						if (advertisement1.getStartTime().compareTo(orderTimeBegin)!=-1 && advertisement1.getEndTime().compareTo(orderTimeEnd)!=1) {
							advertisementPrice=advertisementPrice+advertisement1.getPrice();
						}
				}
			}
			double d= NumUtils.mul(basics2.getSharePrice(),f);//占到的应聘费用
			double plan= NumUtils.mul(basics2.getPlanPrice(),f);//计划的应聘费用
			double ReceivePrice=0;//奖金
			double trainPrice=0;//培训费
			if (list2.size()>0) {
				for (Recruit recruit : list2) {
					List<Reward> rewards=rewardDao.findBycoverRecruitIdAndTypeAndTimeBetween(recruit.getId(),0,DatesUtil.getFirstDayOfMonth(basics.getTime()), DatesUtil.getLastDayOfMonth(basics.getTime()));
					/*
					 * 查询单个人的培训汇总
					 */
					List<Advertisement> advertisements=advertisementDao.findByRecruitIdAndType(recruit.getId(), 1);
					List<Advertisement> listFilter2= advertisements.stream().filter(Advertisement->(Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
							  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==1 && Advertisement.getStartTime().compareTo(orderTimeEnd)==-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==1)
							  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && Advertisement.getEndTime().compareTo(orderTimeBegin)==1 && Advertisement.getEndTime().compareTo(orderTimeEnd)==-1)
							  ||(Advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && Advertisement.getEndTime().compareTo(orderTimeEnd)!=1)
								).collect(Collectors.toList());
					if (listFilter2.size()>0) {
						for (Advertisement advertisement : listFilter2) {
							//过滤 1.开始时间在区间时间之前 结束时间在区间时间之后（4.1  5.1~5.31  6.1）
							if (advertisement.getStartTime().compareTo(orderTimeBegin)==-1 && advertisement.getEndTime().compareTo(orderTimeEnd)==1) {
								long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
								long day1=DatesUtil.getDaySub(orderTimeBegin,orderTimeEnd);//筛选后一共多少天
								trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
								
							}
							//2. 开始时间在区间时间之后 结束时间在区间时间之后 （5.2  5.1~5.31  6.1）
							if (advertisement.getStartTime().compareTo(orderTimeBegin)==1 && advertisement.getStartTime().compareTo(orderTimeEnd)==-1&& advertisement.getEndTime().compareTo(orderTimeEnd)==1) {
								long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
								long day1=DatesUtil.getDaySub(advertisement.getStartTime(),orderTimeEnd);//筛选后一共多少天
								trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
							}
							//过滤 3.开始时间在区间时间之前 结束时间在区间时间之前 （4.1  5.1~5.31  5.30）
							if (advertisement.getStartTime().compareTo(orderTimeBegin)==-1&& advertisement.getEndTime().compareTo(orderTimeBegin)==1 && advertisement.getEndTime().compareTo(orderTimeEnd)==-1) {
								long day=DatesUtil.getDaySub(advertisement.getStartTime(),advertisement.getEndTime());//这条记录一共多少天
								long day1=DatesUtil.getDaySub(orderTimeBegin,advertisement.getEndTime());//筛选后一共多少天
								trainPrice=trainPrice+NumUtils.mul(NumUtils.div(Double.valueOf(day1).doubleValue(),Double.valueOf(day).doubleValue(),6),advertisement.getPrice());//筛选后的广告费
							}
							//4. 开始时间在区间时间之后 结束时间在区间时间之前（5.2  5.1~5.31  5.3）
							if (advertisement.getStartTime().compareTo(orderTimeBegin)!=-1 && advertisement.getEndTime().compareTo(orderTimeEnd)!=1) {
								trainPrice=trainPrice+advertisement.getPrice();
							}
						}
					}
					
					for (Reward reward2 : rewards) {
						ReceivePrice=ReceivePrice+reward2.getPrice();
					}
				}
			}
			
			allMap.put("username", string);
			allMap.put("occupyPrice",d);
			allMap.put("planPrice",plan);
			allMap.put("ReceivePrice",ReceivePrice);
			allMap.put("trainPrice",trainPrice);
			allMap.put("directional",advertisementPrice);
			allList.add(allMap);
			}
		return allList;
	}



	
	
	
}
