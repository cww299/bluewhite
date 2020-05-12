 package com.bluewhite.production.temporarypack;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
public interface SendOrderDao extends BaseRepository<SendOrder, Long>{
    
    /**
     * 根据物流点和上车编号查找  唯一
     */
    @Query(nativeQuery = true,
        value = "SELECT * FROM pro_send_order p WHERE p.logistics_number REGEXP (?1);")
    SendOrder getVehicleNumber(String vehicleNumber);

}
