package com.bluewhite.production.group.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.group.dao.GroupDao;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.system.user.entity.User;
import com.sun.tools.classfile.Opcode.Set;

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
		List<Map<String, Object>> mapList = new ArrayList<>();
		// 获取当前时间所有外调人员信息
		List<Temporarily> temporarilyList = temporarilyDao
				.findByTypeAndTemporarilyDateBetween(temporarily.getType(), temporarily.getOrderTimeBegin(),
						temporarily.getViewTypeDate() == 1 ? temporarily.getOrderTimeEnd()
								: DatesUtil.getLastDayOfMonth(temporarily.getOrderTimeBegin()))
				.stream().collect(Collectors.toList());

		// 获取特急人员b工资
		List<PayB> payBList = payBDao.findByTypeAndAllotTimeBetween(temporarily.getType(),
				temporarily.getOrderTimeBegin(), temporarily.getViewTypeDate() == 1 ? temporarily.getOrderTimeEnd()
						: DatesUtil.getLastDayOfMonth(temporarily.getOrderTimeBegin()));
		// 按天按月查看
		long size = 0;
		switch (temporarily.getViewTypeDate()) {
		case 1:
			size = DatesUtil.getDaySub(temporarily.getOrderTimeBegin(), temporarily.getOrderTimeEnd());
			break;
		case 2:
			size = 1;
			break;
		}

		Map<Long, List<Temporarily>> mapTemporarilyList = null;
		// 按个人按分组查看
		switch (temporarily.getViewTypeUser()) {
		case 1:
			mapTemporarilyList = temporarilyList.stream().filter(Temporarily -> Temporarily.getUserId() != null)
					.collect(Collectors.groupingBy(Temporarily::getUserId, Collectors.toList()));
			break;
		case 2:
			mapTemporarilyList = temporarilyList.stream().filter(Temporarily -> Temporarily.getGroupId() != null)
					.collect(Collectors.groupingBy(Temporarily::getGroupId, Collectors.toList()));
			break;
		}
		// 获取一天的开始时间
		Date beginTimes = temporarily.getOrderTimeBegin();
		for (int i = 0; i < size; i++) {
			for (Long ps : mapTemporarilyList.keySet()) {
				Map<String, Object> mapTe = new HashMap<>();
				List<Temporarily> psList = mapTemporarilyList.get(ps);
				List<Temporarily> psListTe = null;
				double sumPayb = 0.0;
				if (temporarily.getViewTypeDate() == 1) {
					psListTe = new ArrayList<>();
					for (Temporarily te : psList) {
						if (te.getTemporarilyDate().compareTo(beginTimes) == 0) {
							psListTe.add(te);
						}
					}
					psList = psListTe;
					// 获取b工资
					if (temporarily.getViewTypeDate() == 1) {
						sumPayb = payBList.stream().mapToDouble(PayB::getPayNumber).sum();
					}
					if (temporarily.getViewTypeDate() == 2) {
						sumPayb =payBList.stream().mapToDouble(PayB::getPayNumber).sum();
					}
					

				}
				if (psList.size() > 0) {
					double sumWorkTime = psList.stream().filter(Temporarily -> Temporarily.getWorkTime() != null)
							.mapToDouble(Temporarily::getWorkTime).sum();
					Group group = null;
					if (temporarily.getViewTypeUser() == 2) {
						group = dao.findOne(ps);
						// 获取分组中的所有的人员
						List<Temporarily> tList = temporarilyDao.findByGroupId(ps);
						if (temporarily.getViewTypeDate() == 1) {
							
						}

					}
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM");
					mapTe.put("date", temporarily.getViewTypeDate() == 1 ? formatter.format(beginTimes)
							: formatter2.format(beginTimes));
					mapTe.put("name", temporarily.getViewTypeUser() == 1 ? psList.get(0).getUser().getUserName()
							: group == null ? "" : group.getName());
					mapTe.put("foreigns", temporarily.getViewTypeUser() == 1
							? (psList.get(0).getUser().getForeigns() == 0 ? "本厂" : "外厂") : "");
					mapTe.put("bPay", sumPayb);
					mapTe.put("sumWorkTime", sumWorkTime);
					mapTe.put("kindWork", "");
					mapList.add(mapTe);
				}
			}
			beginTimes = DatesUtil.nextDay(beginTimes);
		}
		return mapList;

	}

}
