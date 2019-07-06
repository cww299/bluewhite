package com.bluewhite.personnel.attendance.service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.AdvertisementDao;
import com.bluewhite.personnel.attendance.dao.RecruitDao;
import com.bluewhite.personnel.attendance.dao.RewardDao;
import com.bluewhite.personnel.attendance.entity.Advertisement;
import com.bluewhite.personnel.attendance.entity.Recruit;
import com.bluewhite.personnel.attendance.entity.Reward;
import com.bluewhite.system.user.dao.UserDao;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class RecruitServiceImpl extends BaseServiceImpl<Recruit, Long>
		implements RecruitService {
	@Autowired
	private RecruitDao dao;
	@Autowired
	private BaseDataDao baseDataDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BaseDataDao baseDataDao2;
	@Autowired
	private UserService userService;
	@Autowired
	private RewardDao rewardDao;
	@Autowired
	private AdvertisementDao advertisementDao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Recruit> findPage(Recruit sundry, PageParameter page) {
		Page<Recruit> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按姓名查找
			if (!StringUtils.isEmpty(sundry.getName())) {
				predicate.add(cb.equal(root.get("name").as(String.class), sundry.getName()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(sundry.getOrgNameId())) {
				predicate.add(cb.equal(root.get("orgName").get("id").as(Long.class), sundry.getOrgNameId()));
			}
			if (sundry.getType()!= null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), sundry.getType()));
			}
			if (sundry.getTypeOne()!= null) {
				predicate.add(cb.equal(root.get("typeOne").as(Integer.class), sundry.getTypeOne()));
			}
			if (sundry.getTypeTwo()!= null) {
				predicate.add(cb.equal(root.get("typeTwo").as(Integer.class), sundry.getTypeTwo()));
			}
			
			if (sundry.getState()!= null) {
				predicate.add(cb.equal(root.get("state").as(Integer.class), sundry.getState()));
			}
			//是否离职
			if (sundry.getQuit()!= null) {
				predicate.add(cb.equal(root.get("user").get("quit").as(Integer.class), sundry.getQuit()));
			}
			// 按日期
			if (sundry.getTime()!=null) {
			if (!StringUtils.isEmpty(sundry.getOrderTimeBegin()) && !StringUtils.isEmpty(sundry.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), sundry.getOrderTimeBegin(),
						sundry.getOrderTimeEnd()));
			}
			}
			// 按入职时间
			if (sundry.getTestTime()!=null) {
			if (!StringUtils.isEmpty(sundry.getOrderTimeBegin()) && !StringUtils.isEmpty(sundry.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("testTime").as(Date.class), sundry.getOrderTimeBegin(),
						sundry.getOrderTimeEnd()));
			}
			}
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
	public List<Map<String, Object>>  Statistics (Recruit recruit) {
		List<Recruit> list= dao.findByTimeBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()), DatesUtil.getLastDayOfMonth(recruit.getTime()));
		List<Map<String, Object>> allList = new ArrayList<>();
		Map<String, Object> allMap = null;
		Map<Long, List<Recruit>> map = list.stream()
				.filter(Recruit -> Recruit.getOrgNameId() != null)
				.collect(Collectors.groupingBy(Recruit::getOrgNameId, Collectors.toList()));
		for (Long ps1 : map.keySet()) {
			allMap = new HashMap<>();
			Date date = new Date();
			List<Recruit> psList1 = map.get(ps1);
			Long d=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getType().equals(1)).count();//邀约面试
			Long c=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getTypeOne().equals(1)).count();//应邀面试
			Long b=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) &&  Recruit.getAdopt()!=null && Recruit.getAdopt().equals(1)).count();//面试合格
			Long e=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(2)).count();//拒绝入职
			Long f=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(1) && Recruit.getUser().getQuit().equals(0)).count();//已入职且在职
			Long g=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(3)).count();//即将入职
			//短期入职离职
			Long h=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getUserId()!=null && Recruit.getUser().getQuit().equals(1) && DatesUtil.getDaySub(date, Recruit.getUser().getQuitDate())<32).count();
			Long k=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(1)).count();//已入职
			Long l=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(1) && Recruit.getUser().getQuit().equals(1)).count();//已入职且离职
			BaseData baseData=baseDataDao.findOne(ps1);
			String string= baseData.getName();
			allMap.put("username", string);
			allMap.put("mod1",d);
			allMap.put("mod2",c);
			allMap.put("mod3",b);
			allMap.put("mod4",e);
			allMap.put("mod5",f);
			allMap.put("mod6",g);
			allMap.put("mod7",h);
			allMap.put("mod8",k);
			allMap.put("mod9",l);
			allList.add(allMap);
			}
		return allList;
	}
	@Override
	public List<Recruit> soon(Recruit recruit) {
		Date date = new Date();
		List<Recruit> list= dao.findByTestTimeBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()), DatesUtil.getLastDayOfMonth(recruit.getTime()));
		List<Recruit> recruits=	list.stream().filter(Recruit->Recruit.getUserId()!=null && Recruit.getUser().getQuit().equals(1) && DatesUtil.getDaySub(date, Recruit.getUser().getQuitDate())<32).collect(Collectors.toList());
		return recruits;
	}
	@Override
	public Map<String, List<Map<String, Object>>> users(Recruit recruit) {
		Map<String, Object> allMap = null;
		Map<String, Object> countMap = null;
		List<Map<String, Object>> allList = new ArrayList<>();
		List<Map<String, Object>> countList = new ArrayList<>();
		Map<String, List<Map<String, Object>>> allMapList= new HashMap<>();
		List<User> list=userDao.findByQuitDateBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()), DatesUtil.getLastDayOfMonth(recruit.getTime()));
		List<User> list2=list.stream().filter(User->User.getQuit().equals(1)).collect(Collectors.toList());
		for (User user : list2) {
			allMap = new HashMap<>();
		String userName=user.getUserName();
		String orgName = null;
		if (user.getOrgName()!=null) {
			 orgName=user.getOrgName().getName();
		}
		String positionName = null;
		if (user.getPosition()!=null) {
			positionName=user.getPosition().getName();
		}
		Date entry=	user.getEntry();
		Date quitDate=user.getQuitDate();
		String reason= user.getReason();
		allMap.put("userName",userName);
		allMap.put("orgName",orgName);
		allMap.put("positionName",positionName);
		allMap.put("entry",entry);
		allMap.put("quitDate",quitDate);
		allMap.put("reason",reason);
		allList.add(allMap);
		}
		
		List<User> list3=userDao.findByQuitDateBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()), DatesUtil.getLastDayOfMonth(recruit.getTime()));
		Map<Long, List<User>> map2 = list3.stream().filter(User->User.getOrgNameId() !=null).collect(Collectors.groupingBy(User::getOrgNameId, Collectors.toList()));
		for (Long ps2 : map2.keySet()) {
			countMap = new HashMap<>();
			List<User> psList2 = map2.get(ps2);
			Long i=psList2.stream().filter(User->User.getOrgNameId().equals(User.getOrgNameId()) && User.getQuit().equals(1)).count();
			String orgName=baseDataDao2.findOne(ps2).getName();
			countMap.put("count",i);
			countMap.put("orgName",orgName);
			countList.add(countMap);
		}
		allMapList.put("StringUser", allList);
		allMapList.put("countUser", countList);
		return allMapList;
	}
	@Override
	public Map<String, List<Map<String, Object>>> analysis(Recruit recruit) {
		List<Map<String, Object>> maps=	Statistics(recruit);
		List<Map<String, Object>> allList = new ArrayList<>();
		List<Map<String, Object>> countList = new ArrayList<>();
		Map<String, Object> allMap  = new HashMap<>();
		Map<String, Object> countMap = null;
		Map<String, List<Map<String, Object>>> allMapList= new HashMap<>();
		List<User> list=userDao.findByQuitDateBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()), DatesUtil.getLastDayOfMonth(recruit.getTime()));
		double sum4=list.stream().filter(User->User.getQuit().equals(1)).count();//离职人数
		double sum=0;//应邀面试人数汇总
		double sum1=0;//面试合格人数汇总
		double sum2=0;//入职人数
		double sum3=0;//入职且离职
		double sum6=0;//入职且在职人数
		User user = new User();
		user.setIsAdmin(false);
		user.setForeigns(0);
		double sum5=userService.findUserList(user).stream().filter(User ->(User.getQuitDate()!=null && User.getQuitDate().before(recruit.getTime())) && (User.getEntry() != null && User.getEntry().before(recruit.getTime()))).count();//初期人员
		for (Map<String, Object> map : maps) {
		 Object aInteger= map.get("mod2");
		 Object aInteger2= map.get("mod3");
		 Object aInteger3= map.get("mod8");
		 Object aInteger4= map.get("mod9");
		 Object aInteger5= map.get("mod5");
		 sum=sum+Integer.parseInt(aInteger==null?"":aInteger.toString());
		 sum1=sum1+Integer.parseInt(aInteger2==null?"":aInteger2.toString());
		 sum2=sum2+Integer.parseInt(aInteger3==null?"":aInteger3.toString());
		 sum3=sum3+Integer.parseInt(aInteger4==null?"":aInteger4.toString());
		 sum6=sum6+Integer.parseInt(aInteger5==null?"":aInteger5.toString());
		}
		double a = NumUtils.div(sum, sum1, 4)*100;//面试通过率
		double b = NumUtils.div(sum2, sum1, 4)*100;//入职率
		double c = NumUtils.div(sum3, sum2, 4)*100;//短期流失率
		double d = NumUtils.div(sum4, (sum5+sum2), 4)*100;//离职率
		double e = NumUtils.div(sum6,sum1,2)*100;//留用率
		allMap.put("md1", a);
		allMap.put("md2", b);
		allMap.put("md3", c);
		allMap.put("md4", d);
		allMap.put("md5", e);
		allList.add(allMap);
		allMapList.put("Analysis", allList);
		
		List<Recruit> list2= dao.findByTimeBetween(DatesUtil.getFirstDayOfMonth(recruit.getTime()), DatesUtil.getLastDayOfMonth(recruit.getTime()));
		Map<Long, List<Recruit>> map = list2.stream()
				.filter(Recruit -> Recruit.getPlatformId() != null)
				.collect(Collectors.groupingBy(Recruit::getPlatformId, Collectors.toList()));
		for (Long ps1 : map.keySet()) {
			countMap = new HashMap<>();
			List<Recruit> psList1 = map.get(ps1);
			Long f=psList1.stream().filter(Recruit->Recruit.getPlatformId().equals(Recruit.getPlatformId())).count();//统计入职途径的人数
			BaseData baseData=baseDataDao.findOne(ps1);
			String string= baseData.getName();
			countMap.put("md7", string);
			countMap.put("md6", f);
			countList.add(countMap);
		}
		allMapList.put("summaryCount", countList);
		return allMapList;
	}
	@Override
	public List<Recruit> findList() {
		List<Recruit> recruits=	dao.findAll();
		List<Recruit> list= recruits.stream().filter(Recruit->Recruit.getUserId()!=null && Recruit.getState()==1).collect(Collectors.toList());
		return list;
	}
	@Override
	public List<Map<String, Object>> findfGroupList() {
		List<Recruit> recruits=	dao.findAll();
		List<Map<String, Object>> allList = new ArrayList<>();
		Map<String, Object> allMap = null;
		List<Recruit> list= recruits.stream().filter(Recruit->Recruit.getUserId()!=null && Recruit.getState()==1).collect(Collectors.toList());
		Map<Long, List<Recruit>> map = list.stream()
				.filter(Recruit -> Recruit.getRecruitId() != null)
				.collect(Collectors.groupingBy(Recruit::getRecruitId, Collectors.toList()));
		for (Long ps1 : map.keySet()) {
			allMap = new HashMap<>();
			List<Recruit> psList1 = map.get(ps1);
			List<Recruit> recruits2=psList1.stream().filter(Recruit->Recruit.getRecruitId().equals(Recruit.getRecruitId())).collect(Collectors.toList());
			for (Recruit recruit : recruits2) {
				String string=null;
				string=	recruit.getRecruitName();
				Long integer=recruit.getRecruitId();
				List<Reward> rewards=rewardDao.findByRecruitIdAndType(integer,0);
				List<Reward> rewards2=rewardDao.findByRecruitIdAndType(integer,1);
				Long integer2=recruit.getId();
				double price = 0;//汇总奖励多少钱
				double ReceivePrice = 0;//汇总领取多少钱
				if (rewards.size()>0) {
					for (Reward reward : rewards) {
						price=price+reward.getPrice();
					}
				}
				if (rewards2.size()>0) {
					for (Reward reward : rewards2) {
						ReceivePrice=ReceivePrice+reward.getPrice();
					}
				}
				allMap.put("recruitName", string);
				allMap.put("coverRecruitId", integer2);
				allMap.put("recruitId", integer);
				allMap.put("price", price);
				allMap.put("ReceivePrice", ReceivePrice);
			}
			allList.add(allMap);
		}
		return allList;
	}
	/*
	 * 按条件查询被招聘的人
	 */
	@Override
	public List<Recruit> findCondition(Recruit recruit) {
		List<Recruit> recruits=	dao.findByRecruitIdAndState(recruit.getRecruitId(),1);
		return recruits;
	}
	@Override
	public Recruit findPrice(Recruit recruit) {
			List<Reward> rewards=rewardDao.findBycoverRecruitIdAndType(recruit.getId(),0);
			double ReceivePrice=0;
			for (Reward reward2 : rewards) {
				ReceivePrice=ReceivePrice+reward2.getPrice();
			}
			recruit.setReceivePrice(ReceivePrice);
		return recruit;
	}
	@Override
	@Transactional
	public int deletes(String[] ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				Recruit recruit=dao.findOne(id);
				Long userId = recruit.getUserId();
				recruit.setUserId(null);
				recruit.setUser(null);
			 List<Advertisement> advertisement=advertisementDao.findByRecruitIdAndType(id, 1);
			 if (advertisement.size()>0) {
				 for (Advertisement advertisement2 : advertisement) {
					 advertisementDao.delete(advertisement2.getId());
				 }
			}
			List<Reward> reward= rewardDao.findBycoverRecruitId(id);
			if (reward.size()>0) {
				for (Reward reward2 : reward) {
					rewardDao.delete(reward2.getId());
				}
			}
			dao.delete(id); 
				if (userId!=null) {
					userService.delete(userId);
				}
				count++;
			}
		}
		return count;
	}
	



	
	
	
}
