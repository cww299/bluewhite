 package com.bluewhite.production.temporarypack;

import java.util.Date;

import com.bluewhite.base.BaseRepository;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
public interface SendOrderDao extends BaseRepository<SendOrder, Long>{
    
    /**
     * 根据物流点和上车编号查找  唯一
     */
    SendOrder findByVehicleNumberAndWarehouseTypeId(String vehicleNumber,Long warehouseTypeId);
    
    
    /**
     * 根据当天日期和客户查找发货单 一天只会生成一条
     */
    SendOrder findByCustomerIdAndSendTimeBetween(Long id , Date startTime ,Date endTime);
}
