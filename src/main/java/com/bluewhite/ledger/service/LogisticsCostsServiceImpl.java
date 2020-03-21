package com.bluewhite.ledger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

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
        List<LogisticsCosts> result = dao.findAll((root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            // 按客户id过滤
            if (param.getCustomerId() != null) {
                predicate.add(cb.equal(root.get("customerId").as(Long.class), param.getCustomerId()));
            }
            // 按物流id过滤
            if (param.getLogisticsId() != null) {
                predicate.add(cb.equal(root.get("logisticsId").as(Long.class), param.getLogisticsId()));
            }
            // 按外包装id过滤
            if (param.getOuterPackagingId() != null) {
                predicate.add(cb.equal(root.get("outerPackagingId").as(Long.class), param.getOuterPackagingId()));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            query.where(predicate.toArray(pre));
            return null;
        });
        return result;
    }

}
