package com.bluewhite.ledger.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.LogisticsCostsDao;
import com.bluewhite.ledger.entity.LogisticsCosts;

/**
 * @author ZhangLiang
 * @date 2020/03/19
 */
@Service
public class LogisticsCostsServiceImpl extends BaseServiceImpl<LogisticsCosts, Long> implements LogisticsCostsService {
    
    @Autowired
    private LogisticsCostsDao dao;

    @Override
    public PageResult<LogisticsCosts> findPages(Map<String, Object> params, PageParameter page) {
        return findAll(page, params);
    }

    @Override
    public int deleteLogisticsCosts(String ids) {
        return delete(ids);
    }
    
    @Override
    public List<LogisticsCosts> findAll(LogisticsCosts param) {
        return dao.findByCustomerIdAndLogisticsIdAndOuterPackagingId(param.getCustomerId(), param.getLogisticsId(), param.getOuterPackagingId());
    }

}
