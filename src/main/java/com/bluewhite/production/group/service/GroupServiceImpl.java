package com.bluewhite.production.group.service;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.group.dao.GroupDao;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.system.user.entity.User;

@Service
public class GroupServiceImpl extends BaseServiceImpl<Group, Long> implements GroupService {
	@Autowired
	private GroupDao dao;

	@Autowired
	private TemporarilyDao temporarilyDao;

	@Autowired
	private PayBDao payBDao;

	@Override
	public List<Group> findByType(Integer type) {
		return dao.findByType(type);
	}

	@Override
	public List<Group> findList(Group param) {
		List<Group> result = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按类型
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
			}
			// 按工种类型
			if (param.getKindWorkId() != null) {
				predicate.add(cb.equal(root.get("kindWorkId").as(Long.class), param.getKindWorkId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return result;
	}

	@Override
	public void deleteGroup(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Group group = dao.findOne(id);
					for (User user : group.getUsers()) {
						user.setGroupId(null);
						user.setGroup(null);
					}
					group.setUsers(null);
					dao.delete(group);
				}
				;
			}
		}
	}

	@Override
	public List<Map<String, Object>> sumTemporarily(Temporarily temporarily) {
		CurrentUser cu = SessionManager.getUserSession();
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 获取当前时间所有外调人员信息
		// 过滤掉本厂的人.stream().filter(Temporarily->Temporarily.getUser().getForeigns()==1).collect(Collectors.toList())
		List<Temporarily> temporarilyList = temporarilyDao.findByTypeAndTemporarilyDateBetween(temporarily.getType(),
				temporarily.getOrderTimeBegin(), temporarily.getViewTypeDate() == 1 ? temporarily.getOrderTimeEnd()
						: DatesUtil.getLastDayOfMonth(temporarily.getOrderTimeBegin()));
		 Map<Object, List<Temporarily>> mapTemporarilyList =temporarilyList.stream().collect(Collectors.groupingBy(Temporarily::getUserId, Collectors.toList()));
	
		// 按天按月查看
		long size = DatesUtil.getDaySub(temporarily.getOrderTimeBegin(), temporarily.getViewTypeDate() == 1
				? temporarily.getOrderTimeEnd() : DatesUtil.getLastDayOfMonth(temporarily.getOrderTimeBegin()));
		// 按个人按分组查看
		switch (temporarily.getViewTypeUser()) {
		case 1:
			 //针工按人員id和工种分组
			 if(temporarily.getType()==3){
				 mapTemporarilyList = temporarilyList.stream()
						 .collect(Collectors.groupingBy(o ->  o.getUserId() + "_" + o.getGroup().getKindWorkId(),Collectors.toList()));
			 }
			break;
		case 2:
			mapTemporarilyList = temporarilyList.stream().filter(Temporarily -> Temporarily.getGroupId() != null)
					.collect(Collectors.groupingBy(Temporarily::getGroupId, Collectors.toList()));
			break;
		}
		
		
		//获取外调人员的b工资
		List<PayB> payBList = null;
		if (!cu.getRole().contains("superAdmin") && !cu.getRole().contains("personnel")) {
			// 获取外调人员的b工资
			List<Long> userIds = new ArrayList<>();
			for (Object ps : mapTemporarilyList.keySet()) {
				Long userId = null;
				String[] temp = ps.toString().split("_");
				if (temp.length > 1) {
					userId = Long.valueOf(temp[0]);
				} else {
					userId = Long.valueOf(ps.toString());
				}
				userIds.add(userId);
			}
			payBList = payBDao.findByUserIdInAndAllotTimeBetween(userIds, temporarily.getOrderTimeBegin(),
					temporarily.getViewTypeDate() == 1 ? temporarily.getOrderTimeEnd()
							: DatesUtil.getLastDayOfMonth(temporarily.getOrderTimeBegin()));
		
		}
		
		// 获取一天的开始时间
		Date beginTimes = temporarily.getOrderTimeBegin();
		for (int i = 0; i < size; i++) {
			List<PayB> paybDayList = new ArrayList<>();
			if (payBList != null) {
				for (PayB payB : payBList) {
					if (payB.getAllotTime().compareTo(beginTimes) != -1
							&& payB.getAllotTime().compareTo(DatesUtil.getLastDayOftime(beginTimes)) != 1) {
						paybDayList.add(payB);
					}
				}
			}
			for (Object ps : mapTemporarilyList.keySet()) {
				Map<String, Object> mapTe = new HashMap<>();
				// 获取特急人员或者分组
				List<Temporarily> psList = mapTemporarilyList.get(ps);
				List<Temporarily> psListTe = new ArrayList<>();
				double sumPayb = 0.0;
				Group group = null;
				// 按日获取所有的特急人员考勤
				for (Temporarily te : psList) {
					if (te.getTemporarilyDate().compareTo(beginTimes) == 0) {
						psListTe.add(te);
					}
				}
				psList = psListTe;
				// 获取b工资
				// 按人员
				if (temporarily.getViewTypeUser() == 1) {
					String[] temp = ps.toString().split("_");
					if (paybDayList.size() > 0) {
						sumPayb = paybDayList.stream().filter(PayB -> PayB.getUserId().equals(temporarily.getType()==3 ? Long.valueOf(temp[0]):ps ))
								.mapToDouble(PayB::getPayNumber).sum();
					}
				}
				// 按分组
				if (temporarily.getViewTypeUser() == 2) {
					group = dao.findOne(Long.valueOf(ps.toString()));
					// 获取分组中的所有的人员
					Map<Long, List<Temporarily>> mapUser = psList.stream()
							.collect(Collectors.groupingBy(Temporarily::getUserId, Collectors.toList()));
					for (Long ps1 : mapUser.keySet()) {
						if (paybDayList.size() > 0) {
							double sumGroupPayb = paybDayList.stream().filter(PayB -> PayB.getUserId().equals(ps1))
									.mapToDouble(PayB::getPayNumber).sum();
							sumPayb += NumUtils.round(sumGroupPayb, 4);
						}
					}
				}

				if (psList.size() > 0) {
					double sumWorkTime = psList.stream().filter(Temporarily -> Temporarily.getWorkTime() != null)
							.mapToDouble(Temporarily::getWorkTime).sum();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM");
					mapTe.put("date", temporarily.getViewTypeDate() == 1 ? formatter.format(beginTimes)
							: formatter2.format(beginTimes));
					mapTe.put("name", temporarily.getViewTypeUser() == 1 ? psList.get(0).getUser().getUserName()
							: group == null ? "" : group.getName());
					mapTe.put("foreigns", temporarily.getViewTypeUser() == 1
							? ((psList.get(0).getUser().getForeigns() == 0 && psList.get(0).getUser().getQuit() == 0)
									? "本厂" : "外厂")
							: "");
					mapTe.put("bPay", NumUtils.round(sumPayb, 4));
					mapTe.put("sumWorkTime", sumWorkTime);
					double price = temporarily.getViewTypeUser() == 1
							? (psList.get(0).getUser().getPrice() != null ? psList.get(0).getUser().getPrice() : 0) : 0;
					mapTe.put("id",
							temporarily.getViewTypeUser() == 1
									? (psList.get(0).getUserId() != null ? psList.get(0).getUserId() : "")
									: psList.get(0).getGroupId());
					mapTe.put("price", price);
					mapTe.put("timePrice", NumUtils.div(NumUtils.round(sumPayb, 4), sumWorkTime, 5));
					mapTe.put("sumPrice", NumUtils.mul(price, sumWorkTime));
					mapTe.put("kindWork",
							temporarily.getViewTypeUser() == 1
									? (psList.get(0).getGroup().getKindWork() == null ? ""
											: psList.get(0).getGroup().getKindWork().getName())
									: group.getKindWork() == null ? "" : group.getKindWork().getName());
					mapList.add(mapTe);
				}
			}
			beginTimes = DatesUtil.nextDay(beginTimes);
		}

		// 按月汇总
		if (temporarily.getViewTypeDate() == 2 && mapList.size() > 0) {
			List<Map<String, Object>> mapListMonth = new ArrayList<>();
			Map<String, List<Map<String, Object>>> glist = mapList.stream().collect(Collectors.groupingBy(e -> e.get("id").toString() + "_" + e.get("kindWork").toString()));
			
			for (String ps : glist.keySet()) {
				List<Map<String, Object>> slist = glist.get(ps);
				Map<String, Object> nmap = new HashMap<>();
				DoubleSummaryStatistics mapSumbPay = slist.stream()
						.collect(Collectors.summarizingDouble(e -> Double.valueOf(e.get("bPay").toString())));
				DoubleSummaryStatistics mapSumWorkTime = slist.stream()
						.collect(Collectors.summarizingDouble(e -> Double.valueOf(e.get("sumWorkTime").toString())));
				DoubleSummaryStatistics mapsumPrice = slist.stream()
						.collect(Collectors.summarizingDouble(e -> Double.valueOf(e.get("sumPrice").toString())));
				nmap.put("date", slist.get(0).get("date"));
				nmap.put("name", slist.get(0).get("name"));
				nmap.put("foreigns", slist.get(0).get("foreigns"));
				nmap.put("bPay", NumUtils.round(mapSumbPay.getSum(), 4));
				nmap.put("sumWorkTime", mapSumWorkTime.getSum());
				nmap.put("id", slist.get(0).get("id"));
				nmap.put("price", slist.get(0).get("price"));
				nmap.put("sumPrice", mapsumPrice.getSum());
				nmap.put("kindWork", slist.get(0).get("kindWork"));
				mapListMonth.add(nmap);
			}
			;
			return mapListMonth;
		}
		return mapList;

	}
	
}
