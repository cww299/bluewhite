package com.bluewhite.ledger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.ApplyVoucherDao;
import com.bluewhite.ledger.entity.ApplyVoucher;

@Service
public class ApplyVoucherServiceImpl extends BaseServiceImpl<ApplyVoucher, Long> implements ApplyVoucherService{
	
	@Autowired
	private ApplyVoucherDao dao;

	@Override
	public void saveApplyVoucher(ApplyVoucher applyVoucher) {
		dao.save(applyVoucher);
	}

	@Override
	public PageResult<ApplyVoucher> findPages(ApplyVoucher applyVoucher, PageParameter page) {
		
		return null;
	}

	@Override
	public int passApplyVoucher(String ids) {
		
		return 0;
	}
	


}
