 package com.bluewhite.ledger.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.LogisticsCosts;

/**
 * @author ZhangLiang
 * @date 2020/03/19
 */
public interface LogisticsCostsDao extends BaseRepository<LogisticsCosts, Long>{
    
    /**
     * 根据客户物流包装查询价格
     * @param customerId
     * @param logisticsId
     * @param outerPackagingId
     * @return
     */
    List<LogisticsCosts> findByCustomerIdAndLogisticsIdAndOuterPackagingId(Long customerId,Long logisticsId,Long outerPackagingId);
    
    /**
     * 根据客户查物流点
     * @param customerId
     * @return
     */
    List<LogisticsCosts> findByCustomerId(Long customerId);
    
}
