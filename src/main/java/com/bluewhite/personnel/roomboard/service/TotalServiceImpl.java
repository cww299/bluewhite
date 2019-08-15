package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.personnel.roomboard.dao.HydropowerDao;
import com.bluewhite.personnel.roomboard.dao.TotalDao;
import com.bluewhite.personnel.roomboard.entity.Hydropower;
import com.bluewhite.personnel.roomboard.entity.Total;

@Service
public class TotalServiceImpl extends BaseServiceImpl<Total, Long>
		implements TotalService {
	@Autowired
	private TotalDao dao;
	@Autowired
	private PersonVariableDao personVariableDao;
	@Autowired
	private HydropowerDao hydropowerDao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Total> findPage(Total sundry, PageParameter page) {
		Page<Total> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按宿舍查询
			if (sundry.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), sundry.getType()));
			}
			if (sundry.getState() != null) {
				predicate.add(cb.equal(root.get("state").as(Long.class), sundry.getState()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Total> result = new PageResult<>(pages, page);
		return result;
	}
	/*新增*/
	@Override
	public Total addTotal(Total total) {
		if (total.getType()==2) {
			double a=NumUtils.sub(total.getOneUpperNum(),total.getOneNowNum());
			double b=NumUtils.sub(total.getTwoUpperNum(),total.getTwoNowNum());
			double c=NumUtils.sub(total.getThreeUpperNum(),total.getThreeNowNum());
			total.setSummary(NumUtils.sum(a,b,c,total.getLoss(),total.getBuse(),total.getCopper(),total.getIndividual()));
			total.setState(0);
			/*水费*/
			if (total.getType()==1) {
				PersonVariable variable=personVariableDao.findByType(2);
				total.setSummaryPrice(NumUtils.mul(Double.valueOf(variable.getKeyValue()), total.getSummary()));
			}
			/*电费*/
			if (total.getType()==2) {
				PersonVariable variable=personVariableDao.findByType(3);
				total.setSummaryPrice(NumUtils.mul(Double.valueOf(variable.getKeyValue()), total.getSummary()));
			}
			return dao.save(total);
		}
		if (total.getType()==1) {
			Integer integer = 0;
		List<Hydropower> hydropowers=hydropowerDao.findByMonthDateAndType(total.getMonthDate(), 1);
		for (Hydropower hydropower : hydropowers) {
			integer+=hydropower.getSum();
		}
		total.setSummary(NumUtils.sum(Double.valueOf(integer),total.getLoss(),total.getBuse()));
		PersonVariable variable=personVariableDao.findByType(2);
		total.setSummaryPrice(NumUtils.mul(Double.valueOf(variable.getKeyValue()), total.getSummary()));
		return dao.save(total);
		}
		return null;
	}

	




	
	
	
}
