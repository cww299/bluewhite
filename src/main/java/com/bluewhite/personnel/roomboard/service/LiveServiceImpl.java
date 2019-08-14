package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.roomboard.dao.HydropowerDao;
import com.bluewhite.personnel.roomboard.dao.LiveDao;
import com.bluewhite.personnel.roomboard.dao.SundryDao;
import com.bluewhite.personnel.roomboard.entity.Hydropower;
import com.bluewhite.personnel.roomboard.entity.Live;
import com.bluewhite.personnel.roomboard.entity.Sundry;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class LiveServiceImpl extends BaseServiceImpl<Live, Long>
		implements LiveService {
	@Autowired
	private LiveDao dao;
	@Autowired
	private HydropowerDao hydropowerDao;
	@Autowired
	private UserService userService;
	@Autowired
	private SundryDao sundryDao;
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


	@Override
	public List<Live> findLivePage(Live live) {
		List<Live> list = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 宿舍过滤
			if (live.getHostelId() != null) {
				predicate.add(cb.equal(root.get("hostelId").as(Long.class), live.getHostelId()));
			}
			//按类型过滤
			if (live.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), live.getType()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return list;
	}

    //人员分摊
	@Override
	public List<Map<String, Object>> findShareSummary(Date monthDate,Long hostelId,Long orgNameId) {
		List<Map<String, Object>> allList = new ArrayList<>();
		Map<String, Object> allMap = null;
		List<Hydropower> hydropowers= hydropowerDao.findByMonthDate(monthDate);
		List<Live> objects2=new  ArrayList<Live>();
		Map<Long, List<Hydropower>> mealMap = hydropowers.stream()
				.filter(Hydropower -> Hydropower.getHostelId() != null)
				.collect(Collectors.groupingBy(Hydropower::getHostelId, Collectors.toList()));
		for (Long ps1 : mealMap.keySet()) {
			List<Hydropower> psList1 = mealMap.get(ps1);
			List<Double> listDouble = new ArrayList<>();
			psList1.stream().filter(Hydropower->Hydropower.getHostelId().equals(Hydropower.getHostelId())).forEach(
					c->{listDouble.add(c.getExceedPrice());
			});
			//宿舍水电费合计
			double summarywp = NumUtils.sum(listDouble);
			//这个宿舍住的所有人
			List<Live> list= dao.findByHostelIdAndType(ps1, 1);
			//总的天数
			int allday = 0;
			//获取当月的第一天
			Date date=DatesUtil.getFirstDayOfMonth(monthDate);
			//获取当月的最后天
			Date date2=DatesUtil.getLastDayOfMonth(monthDate);
			List<Live> objects=new  ArrayList<Live>();
			List<Live> list2=dao.findByHostelIdAndTypeAndOtLiveDateBetween(ps1, 2, date, date2);
			if (list2.size()>0) {
				for (Live live2 : list2) {
					Live e=new Live();
					long date4 = 0;//以前住宿人员的入住天数
					int flag2=live2.getOtLiveDate().compareTo(monthDate);
					if(flag2==-1 || flag2==0){
						//获取当月入住天数
					date4=DatesUtil.getDaySub(date,date2);
					allday+=date4;
					e.setUserId(live2.getUserId());
					e.setDay(date4);
					objects.add(e);
					}
					//当月未住满
					if (flag2==1) {
						//获取当月的天数
						 date4=DatesUtil.getDaySub(date,live2.getOtLiveDate());
						allday+=date4;
						e.setUserId(live2.getUserId());
						e.setDay(date4);
						objects.add(e);
					};
				}
			}
			for (Live live : list) {
				long date3 = 0;//现居住宿舍人员的入住天数
				//比对入住时间跟选择月份 返回-1，大于返回1，相等返回0
				int flag=live.getInLiveDate().compareTo(monthDate);
				Live e=new Live();
				if(flag==-1 || flag==0){
						//获取当月入住天数
					date3=DatesUtil.getDaySub(date,date2);
					allday+=date3;
					e.setUserId(live.getUserId());
					e.setDay(date3);
					objects.add(e);
				}
				//当月未住满
				if (flag==1) {
					//获取当月的天数
					 date3=DatesUtil.getDaySub(live.getInLiveDate(),date2);
					allday+=date3;
					e.setUserId(live.getUserId());
					e.setDay(date3);
					objects.add(e);
				};
			}
			
			
			//当前宿舍人员分摊的钱
			for (Live live : objects) {
				Live live3=new Live();
			Long a=	live.getUserId();
			Long b= live.getDay();
			//当前宿舍人员分摊的钱
			double share=NumUtils.mul(NumUtils.div(Double.valueOf(b).doubleValue(),(double)allday,2), summarywp);
			live3.setUserId(a);
			live3.setMoney(share);
			objects2.add(live3);
			}
			}
		Map<Long, List<Live>> mealMap1 = objects2.stream()
				.filter(Live -> Live.getUserId() != null)
				.collect(Collectors.groupingBy(Live::getUserId, Collectors.toList()));
		for (Long ps2 : mealMap1.keySet()) {
			allMap = new HashMap<>();
			Double double1=	objects2.stream().filter(Live->Live.getUserId().equals(ps2)).mapToDouble(Live::getMoney).sum();
			User user= userService.findOne(ps2);
			Long long1= user.getOrgNameId();
			Long aLong=user.getHostelId();
			if (hostelId!=null || orgNameId!=null) {
				if (hostelId!=null && orgNameId==null) {
					if (aLong==hostelId) {
						String aString= user.getUserName();
						String aString2= user.getOrgName().getName();
						allMap.put("username", aString);
						allMap.put("OrgName", aString2);
						allMap.put("money", double1);
						allList.add(allMap);
					}
				}
				if (hostelId==null && orgNameId!=null) {
					if (long1==orgNameId) {
						String aString= user.getUserName();
						String aString2= user.getOrgName().getName();
						allMap.put("username", aString);
						allMap.put("OrgName", aString2);
						allMap.put("money", double1);
						allList.add(allMap);
					}
				}
				if (aLong==hostelId && long1==orgNameId) {
					String aString= user.getUserName();
					String aString2= user.getOrgName().getName();
					allMap.put("username", aString);
					allMap.put("OrgName", aString2);
					allMap.put("money", double1);
					allList.add(allMap);
				}
			}else{
					String aString= user.getUserName();
					String aString2= user.getOrgName().getName();
					allMap.put("username", aString);
					allMap.put("OrgName", aString2);
					allMap.put("money", double1);
					allList.add(allMap);
			}
		}
		return allList;
	}

	//部门分摊
	@Override
	public List<Map<String, Object>> findShareSummaryDepartment(Date monthDate, Long hostelId,Long orgNameId) {
		List<Map<String, Object>> allList = new ArrayList<>();
		Map<String, Object> allMap = null;
		List<Hydropower> hydropowers= hydropowerDao.findByMonthDate(monthDate);
		List<Live> objects2=new  ArrayList<Live>();
		Map<Long, List<Hydropower>> mealMap = hydropowers.stream()
				.filter(Hydropower -> Hydropower.getHostelId() != null)
				.collect(Collectors.groupingBy(Hydropower::getHostelId, Collectors.toList()));
		for (Long ps1 : mealMap.keySet()) {
			List<Hydropower> psList1 = mealMap.get(ps1);
			List<Double> listDouble = new ArrayList<>();
			psList1.stream().filter(Hydropower->Hydropower.getHostelId().equals(Hydropower.getHostelId())).forEach(
					c->{listDouble.add(c.getNotPrice());
			});
			Sundry sundry=	sundryDao.findByHostelIdAndMonthDate(ps1, monthDate);
			double allList2=0;
			if (sundry!=null) {
				allList2=sundry.getSummaryPrice();
			}
			//宿舍水电费+其他费用的合计
			double summarywp = NumUtils.sum(allList2, NumUtils.sum(listDouble));
			//这个宿舍住的所有人
			List<Live> list= dao.findByHostelIdAndType(ps1, 1);
			//总的天数
			int allday = 0;
			//获取当月的第一天
			Date date=DatesUtil.getFirstDayOfMonth(monthDate);
			//获取当月的最后天
			Date date2=DatesUtil.getLastDayOfMonth(monthDate);
			List<Live> objects=new  ArrayList<Live>();
			List<Live> list2=dao.findByHostelIdAndTypeAndOtLiveDateBetween(ps1, 2, date, date2);
			if (list2.size()>0) {
				for (Live live2 : list2) {
					Live e=new Live();
					long date4 = 0;//以前住宿人员的入住天数
					int flag2=live2.getOtLiveDate().compareTo(monthDate);
					if(flag2==-1 || flag2==0){
						//获取当月入住天数
					date4=DatesUtil.getDaySub(date,date2);
					allday+=date4;
					e.setUserId(live2.getUserId());
					e.setDay(date4);
					objects.add(e);
					}
					//当月未住满
					if (flag2==1) {
						//获取当月的天数
						 date4=DatesUtil.getDaySub(date,live2.getOtLiveDate());
						allday+=date4;
						e.setUserId(live2.getUserId());
						e.setDay(date4);
						objects.add(e);
					};
				}
			}
			for (Live live : list) {
				long date3 = 0;//现居住宿舍人员的入住天数
				//比对入住时间跟选择月份 返回-1，大于返回1，相等返回0
				int flag=live.getInLiveDate().compareTo(monthDate);
				Live e=new Live();
				if(flag==-1 || flag==0){
						//获取当月入住天数
					date3=DatesUtil.getDaySub(date,date2);
					allday+=date3;
					e.setUserId(live.getUserId());
					e.setDay(date3);
					objects.add(e);
				}
				//当月未住满
				if (flag==1) {
					//获取当月的天数
					 date3=DatesUtil.getDaySub(live.getInLiveDate(),date2);
					allday+=date3;
					e.setUserId(live.getUserId());
					e.setDay(date3);
					objects.add(e);
				};
			}
			
			
			//当前宿舍人员分摊的钱
			for (Live live : objects) {
				Live live3=new Live();
			Long a=	live.getUserId();
			Long b= live.getDay();
			//当前宿舍人员分摊的钱
			double share=NumUtils.mul(NumUtils.div(Double.valueOf(b).doubleValue(),(double)allday,2), summarywp);
			live3.setUserId(a);
			live3.setMoney(share);
			objects2.add(live3);
			}
			}
		Map<Long, List<Live>> mealMap1 = objects2.stream()
				.filter(Live -> Live.getUserId() != null)
				.collect(Collectors.groupingBy(Live::getUserId, Collectors.toList()));
		for (Long ps2 : mealMap1.keySet()) {
			allMap = new HashMap<>();
			Double double1=	objects2.stream().filter(Live->Live.getUserId().equals(ps2)).mapToDouble(Live::getMoney).sum();
			User user= userService.findOne(ps2);
			Long long1= user.getOrgNameId();
			Long aLong=user.getHostelId();
			if (hostelId!=null || orgNameId!=null) {
				if (hostelId!=null && orgNameId==null) {
					if (aLong==hostelId) {
						String aString= user.getUserName();
						String aString2= user.getOrgName().getName();
						allMap.put("username", aString);
						allMap.put("OrgName", aString2);
						allMap.put("money", double1);
						allList.add(allMap);
					}
				}
				if (hostelId==null && orgNameId!=null) {
					if (long1==orgNameId) {
						String aString= user.getUserName();
						String aString2= user.getOrgName().getName();
						allMap.put("username", aString);
						allMap.put("OrgName", aString2);
						allMap.put("money", double1);
						allList.add(allMap);
					}
				}
				if (aLong==hostelId && long1==orgNameId) {
					String aString= user.getUserName();
					String aString2= user.getOrgName().getName();
					allMap.put("username", aString);
					allMap.put("OrgName", aString2);
					allMap.put("money", double1);
					allList.add(allMap);
				}
			}else{
					String aString= user.getUserName();
					String aString2= user.getOrgName().getName();
					allMap.put("username", aString);
					allMap.put("OrgName", aString2);
					allMap.put("money", double1);
					allList.add(allMap);
			}
		}
		return allList;
	}
	


}
