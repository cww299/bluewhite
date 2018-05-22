package com.bluewhite.production.procedure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.entity.Product;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.ProTypeUtils;

@Service
public class ProcedureServiceImpl extends BaseServiceImpl<Procedure, Long> implements ProcedureService{

	@Autowired
	private ProcedureDao procedureDao;
	@Override
	public List<Procedure> findByProductIdAndType(Long productId, Integer type) {
		return procedureDao.findByProductIdAndType(productId,type);
	}
	
	
	@Override
	public void countPrice(Procedure procedure) {
		List<Procedure> procedureList = procedureDao.findByProductIdAndType(procedure.getProductId(), procedure.getType());
		//计算部门生产总价
		Double sumTime = 0.0;
		for(Procedure pro : procedureList){
			sumTime += pro.getWorkingTime();
		}
		Double sumPrice = ProTypeUtils.sumProTypePrice(sumTime, procedure.getType());
		for(Procedure pro : procedureList){
			pro.setDepartmentPrice(NumUtils.round(sumPrice));
		}
		//计算外发价格
		Double price = ProTypeUtils.sumProTypeHairPrice(procedureList,procedure.getType());
		for(Procedure pro : procedureList){
			pro.setHairPrice(NumUtils.round(price));
		}
		procedureDao.save(procedureList);
	}

}
