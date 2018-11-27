package com.bluewhite.finance.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.ledger.dao.BillDao;
import com.bluewhite.finance.ledger.dao.MixedDao;
import com.bluewhite.finance.ledger.entity.Bill;
import com.bluewhite.finance.ledger.entity.Mixed;

@Service
public class MixedServiceImpl extends BaseServiceImpl<Mixed, Long> implements MixedService{
	 
	@Autowired
	private MixedDao dao;
	
	@Autowired
	private BillDao billdao;
	
	
	@Override
	public PageResult<Mixed> findPages(Mixed param, PageParameter page) {
		Page<Mixed> pages = dao.findAll((root,query,cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
        	return null;
		},page);
		
			PageResult<Mixed> result = new PageResult<Mixed>(pages, page);
        return result;
	}
	
	
	@Override
	@Transactional
	public void addMixed(Mixed mixed) {
		
		List<Bill> billList = billdao.findByPartyNamesIdAndBillDateBetween(mixed.getMixPartyNamesId(),DatesUtil.getFirstDayOfMonth(mixed.getMixtSubordinateTime()),	DatesUtil.getLastDayOfMonth(mixed.getMixtSubordinateTime()));
		Bill bill = billList.get(0);
		NumUtils.setzro(bill);
		if(bill!=null){
			List<Mixed> mixedList = dao.findByMixPartyNamesIdAndMixtSubordinateTimeBetween(mixed.getMixPartyNamesId(),DatesUtil.getFirstDayOfMonth(mixed.getMixtSubordinateTime()),DatesUtil.getLastDayOfMonth(mixed.getMixtSubordinateTime()));
			double	acceptPayable = mixedList.stream().filter(Mixed->Mixed.getMixPartyNamesId()==mixed.getMixPartyNamesId()).mapToDouble(Mixed::getMixPrice).sum();
			bill.setAcceptPayable(acceptPayable);
			//当月货款未到
			bill.setNonArrivalPay(bill.getAcceptPay()+bill.getAcceptPayable()-bill.getArrivalPay());
			//当月客户多付货款转下月应付
			bill.setOverpaymentPay(bill.getNonArrivalPay()<0 ?bill.getNonArrivalPay() :0.0);
			billdao.save(bill);
		}
		dao.save(mixed);
	}

}
