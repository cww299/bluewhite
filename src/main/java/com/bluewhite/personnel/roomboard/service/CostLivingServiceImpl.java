package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.roomboard.dao.CostLivingDao;
import com.bluewhite.personnel.roomboard.dao.HydropowerDao;
import com.bluewhite.personnel.roomboard.entity.CostLiving;

@Service
public class CostLivingServiceImpl extends BaseServiceImpl<CostLiving, Long>
		implements CostLivingService {
	@Autowired
	private CostLivingDao dao;
	@Autowired
	private PersonVariableDao personVariableDao;
	@Autowired
	private HydropowerDao hydropowerDao;
	
	@Override
	public PageResult<CostLiving> findPage(CostLiving param, PageParameter page) {
		Page<CostLiving> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按公司所在场所过滤
			if (param.getSiteTypeId() != null) {
				predicate.add(cb.equal(root.get("siteTypeId").as(Long.class), param.getSiteTypeId()));
			}
			// 按费用类型
			if (param.getCostTypeId() != null) {
				predicate.add(cb.equal(root.get("costTypeId").as(Long.class), param.getCostTypeId()));
			}		
			// 按开始时间过滤
			if(!StringUtils.isEmpty(param.getBeginTime())){
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("beginTime").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}			
			}
			// 按结束时间过滤
			if(!StringUtils.isEmpty(param.getEndTime())){
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("endTime").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}			
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<CostLiving> result = new PageResult<>(pages, page);
		return result;
	}
	

	@Override
	public CostLiving saveCostLiving(CostLiving costLiving) {
		CostLiving oldCostLiving = dao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime(costLiving.getCostTypeId(), 
				costLiving.getSiteTypeId(), costLiving.getBeginTime(), costLiving.getEndTime());
		if(oldCostLiving!=null){
			throw new ServiceException("改地点.改类型.改时间段的生活费用已添加,无需再次添加");
		}
		long day= DatesUtil.getDaySub(costLiving.getBeginTime(), costLiving.getEndTime());
		Double averageCost = NumUtils.div(costLiving.getTotalCost(), day, 2);
		costLiving.setAverageCost(averageCost);
		return dao.save(costLiving);
	}


	@Override
	public int deleteCostLiving(String ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			String[] idStrings = ids.split(",");
			for(String id : idStrings ){
				dao.delete(Long.valueOf(id));
				count++;
			}
		}
		return count;
	}

	




	
	
	
}
