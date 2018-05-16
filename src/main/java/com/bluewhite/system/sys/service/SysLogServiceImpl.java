package com.bluewhite.system.sys.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.sys.dao.SysLogDao;
import com.bluewhite.system.sys.entity.SysLog;
import com.bluewhite.system.user.dao.UserDao;

@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLog, Long> implements
		SysLogService {

	@Autowired
	private SysLogDao dao;
	@Autowired
	private UserDao userDao;

	@Override
	public void log(SysLog log) {
		dao.save(log);
	}

	@Override
	public Map<String ,Object> findLog(SysLog log,PageParameter page) {
		page.setSort(null);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String ,Object>> listMap= new ArrayList<>();
		Map<String, Object> map = null;
		Map<String,Integer> map1 = null;
		List<Map> list = null;
		Map<String, Object> mapResult = new HashMap<>();
		Calendar ca=Calendar.getInstance();
		try {
			if(log.getOperateTime() != null){//如果传了时间
				Date date = dateFormat.parse(dateFormat.format(log.getOperateTime()));
				ca.setTime(date);
				ca.add(Calendar.HOUR_OF_DAY, -2);//计算两个小时之前的时间
				log.setEndTime(ca.getTime());
			}else{//如果没传时间取当前系统时间
				Date date = dateFormat.parse(dateFormat.format(new Date()));
				log.setOperateTime(new Date());
				ca.setTime(date);
				ca.add(Calendar.HOUR_OF_DAY, -2);
				log.setEndTime(ca.getTime());
			}
		Page<SysLog> logs =dao.findAll(queryCondition(log),page);
		PageResult<SysLog> result = new PageResult<>(logs);//分页数据
		mapResult.put("total", result.getTotal());//总条数
		for (SysLog sysLog : result.getRows()) {//遍历处理
			list = new ArrayList<>();
			map1 = new HashMap<>();
			map = new HashMap<>();
			Integer id=null;
			if(sysLog.getOpertionType() != null){
				if("登录".equals(sysLog.getOpertionType())){
					id=20180001;
				}else if("登出".equals(sysLog.getOpertionType())){
					id=20180002;
				}else if("添加".equals(sysLog.getOpertionType())){
					id=20180003;
				}
			}
			map.put("appId", "20180001");
			map.put("primaryType", 104);
			map.put("secondaryType", 104101);
			map.put("dataType", 104101101);
			map.put("description", sysLog.getOpertionType());
			map.put("finishTime", sysLog.getOperateTime().getTime()/1000);
			map1.put("id", id);
			list.add(map1);
			map.put("extensionLabelList", list);
			listMap.add(map);
		}
		mapResult.put("rows", listMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mapResult;
	}
	
	private Specification<SysLog> queryCondition(SysLog log) {
		Specification<SysLog> spec = new Specification<SysLog>() {
			@Override
			public Predicate toPredicate(Root<SysLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				List<String> opertionTypeList = new ArrayList<>();
				List<String> logTypeList = new ArrayList<>();
				opertionTypeList.add("登录");
				opertionTypeList.add("登出");
				opertionTypeList.add("添加");
				logTypeList.add(SysLog.VIEW_LOG_TYPE);
				logTypeList.add("前端用户日志");
				if(log.getEndTime() != null &&  log.getOperateTime() != null){
					predicate.add(cb.between(root.get("operateTime").as(Date.class),  log.getEndTime(),log.getOperateTime()));
				}
				/*Predicate pre1=cb.equal(root.get("opertionType").as(String.class), "登录");
				Predicate pre2=cb.equal(root.get("opertionType").as(String.class), "登出");
				Predicate pre3=cb.equal(root.get("opertionType").as(String.class), "添加");
				predicate.add(cb.or(pre1,pre2,pre3));*/
				predicate.add(cb.and(root.get("opertionType").as(String.class).in(opertionTypeList)));
				predicate.add(cb.and(root.get("logType").as(String.class).in(logTypeList)));
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
				return null;
			}
		};
		return spec;
	}
	
	
}
