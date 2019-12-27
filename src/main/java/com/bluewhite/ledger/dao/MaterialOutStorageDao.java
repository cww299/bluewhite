package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import java.lang.Long;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface MaterialOutStorageDao extends BaseRepository<MaterialOutStorage, Long>{
	
	/**
	 * 根据入库单查找
	 * @param materialputstorageid
	 * @return
	 */
	@Query(nativeQuery=true,value ="SELECT distinct s.material_out_storage_id FROM ledger_put_out_material_storage s WHERE s.material_put_storage_id = ?1")
	List<Long> findMaterialPutStorageId(Long materialputstorageid);

	/**
	 * 根据领料单查找
	 * @param materialrequisitionid
	 * @return
	 */
	List<MaterialOutStorage> findByMaterialRequisitionId(Long materialrequisitionid);
}
