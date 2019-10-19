package com.bluewhite.ledger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.ledger.dao.OrderProcurementDao;
import com.bluewhite.ledger.dao.ScatteredOutboundDao;
import com.bluewhite.ledger.entity.ScatteredOutbound;

@Service
public class ScatteredOutboundServiceImpl extends BaseServiceImpl<ScatteredOutbound, Long> implements ScatteredOutboundService{
	
	@Autowired
	private ScatteredOutboundDao dao;
	@Autowired
	private OrderProcurementDao  orderProcurementDao;
	
	

	@Override
	public int saveScatteredOutbound(String ids) {
		int count= 0;
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length > 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					
					
					
					
					count++;
				}
			}
		}
		return count;
	}

	

}
