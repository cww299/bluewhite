package com.bluewhite.ledger.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.MaterialPutStorage;
import com.bluewhite.ledger.entity.MaterialRequisition;
import com.bluewhite.ledger.entity.OrderProcurementReturn;

public interface MaterialPutStorageService  extends BaseCRUDService<MaterialPutStorage, Long>{
	
	/**
	 * 生成入库单
	 * @param materialPutStorage
	 */
	public void saveMaterialPutStorage(MaterialPutStorage materialPutStorage);
	
	/**
	 * 入库单列表
	 * @param page
	 * @param materialPutStorage
	 * @return
	 */
	public PageResult<MaterialPutStorage> findPages(PageParameter page, MaterialPutStorage materialPutStorage);
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public int deleteMaterialPutStorage(String ids);
	
	/**
	 * 入库单验货
	 * @param ids
	 * @return
	 */
	public void inspectionMaterialPutStorage(MaterialPutStorage materialPutStorage);
	
	/**
	 * 根据采购单id获取到货数量
	 * @param id
	 * @return
	 */
	public double getArrivalNumber(Long id);
	
	/**
	 * 根据采购单查找
	 * @param id
	 * @return
	 */
	public List<MaterialPutStorage> findByOrderProcurementId(Long id);
	
	/**
	 * 按采购单和已经验货
	 * @param id
	 * @param i
	 * @return
	 */
	public List<MaterialPutStorage> findByOrderProcurementIdAndInspection(Long id, int i);

	/**
	 * 根据面料和库存查看实际入库单和生产计划单 （库存详情）
	 * @param warehouseTypeId
	 * @param productId
	 */
	public List<MaterialPutStorage>  detailsInventory(Long warehouseTypeId, Long materielId);

	/**新增物料退货单
	 * 
	 * @param materialPutOutStorage
	 */
	public void saveMaterialReturn(OrderProcurementReturn orderProcurementReturn);

    /**删除退货单
     * @param ids
     * @return
     */
    public int deleteMaterialReturn(String ids);

    /**查询退货单根据入库单id
     * @param id
     */
    public List<OrderProcurementReturn> getMaterialReturn(Long id);

    /**根据
     * @param orderProcurementId
     * @return
     */
    public  List<OrderProcurementReturn> getMaterialReturnOne(Long orderProcurementId);

}
