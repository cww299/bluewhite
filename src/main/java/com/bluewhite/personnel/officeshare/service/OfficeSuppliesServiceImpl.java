package com.bluewhite.personnel.officeshare.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.officeshare.dao.OfficeSuppliesDao;
import com.bluewhite.personnel.officeshare.entity.OfficeSupplies;

@Service
public class OfficeSuppliesServiceImpl extends BaseServiceImpl<OfficeSupplies, Long> implements OfficeSuppliesService {
	
	
	@Autowired
	private OfficeSuppliesDao dao;

	@Override
	public PageResult<OfficeSupplies> findPages(OfficeSupplies param, PageParameter page) {
		Page<OfficeSupplies> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			// 按辦公用品名称过滤
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class), "%" + param.getName() + "%"));
			}
			// 按仓位过滤
			if (!StringUtils.isEmpty(param.getLocation())) {
				predicate.add(cb.like(root.get("location").as(String.class), "%" + param.getLocation() + "%"));
			}
			if(!StringUtils.isEmpty(param.getCreatedAt())){
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
					predicate.add(cb.between(root.get("createdAt").as(Date.class), param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}			
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResultStat<OfficeSupplies> result = new PageResultStat<>(pages, page);
		return result;
	}

	@Override
	public void addOfficeSupplies(OfficeSupplies officeSupplies) {
		if(officeSupplies.getId()!=null){
			OfficeSupplies ot = dao.findOne(officeSupplies.getId());
			update(officeSupplies, ot);
		}else{
			officeSupplies.setInventoryNumber(0);
			officeSupplies.setLibraryValue(NumUtils.mul(officeSupplies.getInventoryNumber(),officeSupplies.getPrice()));
			dao.save(officeSupplies);
		}
	}

	@Override
	public int deleteOfficeSupplies(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					dao.delete(id);
					count++;
				}
			}
		}
		return count;
	}

}
