package com.bluewhite.production.temporarypack;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bluewhite.base.BaseRepository;

import cn.hutool.core.date.DateTime;

public interface QuantitativeDao extends BaseRepository<Quantitative, Long> {

    /**
     * 查询下货单已发货数量
     */
    @Query(nativeQuery = true,
        value = "SELECT distinct qc.id FROM pro_quantitative q,pro_quantitative_child qc,pro_under_goods u WHERE q.id = qc.quantitative_id and  qc.underGoods_id = u.id AND q.flag = 1 and u.id = (?1)")
    List<Object> findSendNumber(Long id);

    /**
     * 根据贴包日期
     * 
     * @param time
     * @return
     */
    List<Quantitative> findByTimeBetweenOrderByIdDesc(Date startTime, Date endTime);

    /**
     * 根据发货单id 查找 贴包单
     */
    List<Quantitative> findBySendOrderId(Long id);

    /**
     * 自动检测 以量化 未发货发货 且时间超过三天
     * 
     * @return
     * 
     */
    @Query(nativeQuery = true,
        value = "SELECT * FROM pro_quantitative where flag = 0 and time < SUBDATE(now(),interval 3 day) limit :page,:size")
    List<Quantitative> warehousing(@Param("page") int page, @Param("size") int size);

    /**
     * 统计条数
     * @return
     */
    @Query(nativeQuery = true,
        value = "SELECT count(*) FROM pro_quantitative where flag = 0 and time < SUBDATE(now(),interval 3 day)")
    int warehousing();
    

    /**根据发货时间
     * @param beginOfDay
     * @param endOfDay
     */
    List<Quantitative> findBySendTimeBetweenAndCustomerIdAndWarehouseTypeId(Date startTime, Date endTime,long id,long warehouseTypeId);

    /**根据上车编号查找
     * @param vehicleNumber
     */
    Quantitative findByVehicleNumberAndWarehouseTypeId(String vehicleNumber,Long warehouseTypeId);

}
