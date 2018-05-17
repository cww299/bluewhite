package com.bluewhite.production.procedure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;

@Service
public class ProcedureServiceImpl extends BaseServiceImpl<Procedure, Long> implements ProcedureService{

	@Autowired
	private ProcedureDao procedureDao;
	@Override
	public List<Procedure> findByProductIdAndType(Long productId, Integer type) {
		return procedureDao.findByProductIdAndType(productId,type);
	}

	

}
