package com.bluewhite.ledger.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OutStorage;

public interface OutStorageDao extends BaseRepository<OutStorage, Long>{
	
	/**
	 * 根据入库单id查找
	 * @param id
	 * @return
	 */
	List<OutStorage> findByPutStorageId(Long id);
	
//	/**
//	 * 根据入库单查找
//	 * @param materialputstorageid
//	 * @return
//	 */
//	@Query(nativeQuery=true,value ="SELECT distinct s.material_out_storage_id FROM ledger_put_out_material_storage s WHERE s.material_put_storage_id = ?1")
//	List<Long> findMaterialPutStorageId(Long materialputstorageid);

}
