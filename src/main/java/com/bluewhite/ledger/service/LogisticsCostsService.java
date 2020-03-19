 package com.bluewhite.ledger.service;

import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.LogisticsCosts;

/**
 * @author ZhangLiang
 * @date 2020/03/19
 */
public interface LogisticsCostsService extends BaseCRUDService<LogisticsCosts, Long>{

    /**分页查询
     * @param logisticsCosts
     * @param page
     * @return
     */
    public PageResult<LogisticsCosts> findPages(Map<String, Object> params, PageParameter page);

    /**删除
     * @param ids
     * @return
     */
    public int deleteLogisticsCosts(String ids);

}
