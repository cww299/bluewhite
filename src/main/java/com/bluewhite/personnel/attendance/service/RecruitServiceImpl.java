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
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.personnel.attendance.dao.RecruitDao;
import com.bluewhite.personnel.attendance.entity.Recruit;

@Service
public class RecruitServiceImpl extends BaseServiceImpl<Recruit, Long>
		implements RecruitService {
	@Autowired
	private RecruitDao dao;
	@Autowired
	private BaseDataDao baseDataDao;
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
			// 按日期
			if (!StringUtils.isEmpty(sundry.getOrderTimeBegin()) && !StringUtils.isEmpty(sundry.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), sundry.getOrderTimeBegin(),
						sundry.getOrderTimeEnd()));
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
			List<Recruit> psList1 = map.get(ps1);
			Long d=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getType().equals(1)).count();
			Long c=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getTypeOne().equals(1)).count();
			Long b=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getTypeTwo().equals(1)).count();
			Long e=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(2)).count();
			Long f=psList1.stream().filter(Recruit->Recruit.getOrgNameId().equals(Recruit.getOrgNameId()) && Recruit.getState().equals(1)).count();
			BaseData baseData=baseDataDao.findOne(ps1);
			String string= baseData.getName();
			allMap.put("username", string);
			allMap.put("mod1",d);
			allMap.put("mod2",c);
			allMap.put("mod3",b);
			allMap.put("mod4",e);
			allMap.put("mod5",f);
			allList.add(allMap);
			}
		return allList;
	}
	




	
	
	
}
