package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.dao.HydropowerDao;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.Hydropower;
import com.bluewhite.personnel.attendance.entity.PersonVariable;

@Service
public class HydropowerServiceImpl extends BaseServiceImpl<Hydropower, Long>
		implements HydropowerService {
	@Autowired
	private HydropowerDao dao;
	@Autowired
	private PersonVariableDao personVariableDao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Hydropower> findPage(Hydropower hydropower, PageParameter page) {
		Page<Hydropower> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按宿舍查询
			if (hydropower.getHostelId() != null) {
				predicate.add(cb.equal(root.get("hostelId").as(Long.class), hydropower.getHostelId()));
			}
			//按水费电费类型查询
			if (hydropower.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), hydropower.getType()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Hydropower> result = new PageResult<>(pages, page);
		return result;
	}


	@Override
	public Hydropower addHydropower(Hydropower hydropower) {
		if (hydropower.getId()==null) {
			
			hydropower.setSum(hydropower.getNowDegreeNum()-hydropower.getUpperDegreeNum());
			//每一度（d吨)的钱
			PersonVariable variable=personVariableDao.findByType(2);
			//水费
			if (hydropower.getType()==1) {
				hydropower.setPrice(Double.valueOf(variable.getKeyValue()));
			}
			//电费
			if (hydropower.getType()==2) {
				hydropower.setPrice(Double.valueOf(variable.getKeyValueTwo()));
			 }
			//水电费的标准
			PersonVariable variable2=personVariableDao.findByType(3);
			if (hydropower.getType()==1) {
				hydropower.setTalonNum(Integer.valueOf(variable2.getKeyValue()));
			}
			if (hydropower.getType()==2) {
				hydropower.setTalonNum(Integer.valueOf(variable2.getKeyValueTwo()));
			 }
			hydropower.setSummaryPrice((hydropower.getNowDegreeNum()-hydropower.getUpperDegreeNum())*hydropower.getPrice());
			hydropower.setExceedNum(hydropower.getNowDegreeNum()-hydropower.getUpperDegreeNum()-hydropower.getTalonNum());
			hydropower.setExceedPrice(hydropower.getExceedNum()*hydropower.getPrice());
			 dao.save(hydropower);
		}
		if (hydropower.getId()!=null) {
			hydropower.setSum(hydropower.getNowDegreeNum()-hydropower.getUpperDegreeNum());
			//每一度（d吨)的钱
			hydropower.setSummaryPrice((hydropower.getNowDegreeNum()-hydropower.getUpperDegreeNum())*hydropower.getPrice());
			hydropower.setExceedNum(hydropower.getNowDegreeNum()-hydropower.getUpperDegreeNum()-hydropower.getTalonNum());
			hydropower.setExceedPrice(hydropower.getExceedNum()*hydropower.getPrice());
			 dao.save(hydropower);
		}
		return null;
	}



	
	
	
}
