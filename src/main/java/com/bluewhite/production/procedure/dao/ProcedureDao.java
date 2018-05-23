package com.bluewhite.production.procedure.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.procedure.entity.Procedure;
/**
 * 
 * @author zhangliang
 *
 */
public interface ProcedureDao extends BaseRepository<Procedure, Long>{
	/**
	 * 根据产品id和所属部门类型查询其产品工序
	 * @param productId
	 * @param type
	 * @return
	 */
	List<Procedure> findByProductIdAndType(Long productId, Integer type);
	/**
	 *  根据产品和工序类型查询工序具体
	 * @param productId
	 * @param procedureTypeId
	 * @param type
	 * @return
	 */
	List<Procedure> findByProductIdAndProcedureTypeIdAndType(Long productId, Long procedureTypeId, Integer type);

}
