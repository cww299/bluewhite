package com.bluewhite.production.procedure.service;

import java.util.List;
import java.util.Map;

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
	List<Procedure> findByProductIdAndType(Long productId,Integer type,Integer flag);
	/**
	 * 根据工序计算出外发单价和生产单价
	 * @param productId
	 * @param type
	 */
	void countPrice(Procedure procedure);
	/**
	 * 根据产品和工序类型查询工序具体
	 * @param productId
	 * @param procedureTypeId
	 * @param type
	 * @return
	 */
	List<Procedure> findByProductIdAndProcedureTypeIdAndType(Long productId, Long procedureTypeId, Integer type);
	/**
	 * 按条件查询工序
	 * @param procedure
	 * @return
	 */
	List<Procedure> findList(Procedure procedure);
	/**
	 * 保存全部list
	 * @param procedureList
	 */
	List<Procedure> saveList(List<Procedure> procedureList);
	
	/**
	 * 分组工序
	 * @param procedure
	 * @return
	 */
	 List<Map<String, Object>> soon(Procedure procedure);
	
	/**
	 * 删除工序
	 * @param id
	 */
	void deleteProcedure(Long id);
	
	/**
	 * 保存
	 * @param procedureList
	 */
	public Procedure add(Procedure procedure);

}
