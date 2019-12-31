package com.bluewhite.ledger.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OrderProcurementReturn;

public interface OrderProcurementReturnDao extends BaseRepository<OrderProcurementReturn, Long> {
    /**
     * .根据入库单查找退货单
     * @return
     */
    public List<OrderProcurementReturn> findByMaterialPutStorageId(Long id);
}
