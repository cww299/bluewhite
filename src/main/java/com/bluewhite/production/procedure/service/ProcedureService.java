package com.bluewhite.production.procedure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.production.procedure.entity.Procedure;

@Service
public interface ProcedureService extends BaseCRUDService<Procedure,Long>{
	
	/**
	 *  根据产品id和所属部门类型查询其产品工序
	 * @param productId
	 * @return
	 */
	List<Procedure> findByProductIdAndType(Long productId,Integer type);
	/**
	 * 根据工序计算出外发单价和生产单价
	 * @param productId
	 * @param type
	 */
	void countPrice(Procedure procedure);


}
