package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import java.lang.Long;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface MaterialPutStorageDao  extends BaseRepository<MaterialPutStorage, Long>{
	
	/**
	 * 根据采购单查找
	 * @param orderprocurementid
	 * @return
	 */
	List<MaterialPutStorage> findByOrderProcurementIdAndInspection(Long orderprocurementid,Integer inspection);
	
	/**
	 * 根据采购单查找
	 * @param orderprocurementid
	 * @return
	 */
	List<MaterialPutStorage> findByOrderProcurementId(Long orderprocurementid);
	
	/**
	 * 根据物料id
	 * @param materielid
	 * @return
	 */
	List<MaterialPutStorage> findByMaterielId(Long materielid);
	
	/**
	 * 根据入库仓库和物料
	 * @param warehouseTypeId
	 * @param materielId
	 * @return
	 */
	List<MaterialPutStorage> findByInWarehouseTypeIdAndMaterielId(Long warehouseTypeId, Long materielId);
	
	 /**
     * 根据采购单查找
     * @param materialputstorageid
     * @return
     */
    @Query(nativeQuery=true,value ="SELECT r.id FROM ledger_order_procurement_return r, ledger_material_put_storage s,ledger_order_procurement p WHERE r.material_put_storage_id = s.id AND p.id = ?1")
    List<Long> findOrderProcurementIdGetMaterialPutStorage(Long orderProcurementId);
	
}
