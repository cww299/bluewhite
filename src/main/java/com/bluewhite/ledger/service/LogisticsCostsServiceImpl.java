package com.bluewhite.ledger.service;

import java.util.Map;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.LogisticsCosts;

/**
 * @author ZhangLiang
 * @date 2020/03/19
 */
public class LogisticsCostsServiceImpl extends BaseServiceImpl<LogisticsCosts, Long> implements LogisticsCostsService {

    @Override
    public PageResult<LogisticsCosts> findPages(Map<String, Object> params, PageParameter page) {
        return findAll(page, params);
    }

    @Override
    public int deleteLogisticsCosts(String ids) {
        return delete(ids);
    }

}
