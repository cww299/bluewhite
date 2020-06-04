package com.bluewhite.production.temporarypack;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
public interface SendOrderService extends BaseCRUDService<SendOrder, Long> {

    /**
     * 修改发货单
     * 
     * @param sendOrder
     */
    public void updateSendOrder(SendOrder sendOrder);

    /**
     * 分页查看发货单
     * 
     * @param params
     * @param page
     * @return
     */
    public PageResult<SendOrder> findPages(Map<String, Object> params, PageParameter page);

    /**
     * 生成物流费用
     * expenseDate 申请付款时间
     * paymentDate 预计付款时间
     * @param ids
     * @param audit
     */
    public int auditSendOrder(String ids, Date expenseDate ,Date paymentDate );

    /**
     * 查看发货单实际已发货的贴包明细
     * 
     * @param id
     */
    public List<Quantitative> getQuantitativeList(Long id,String productName);

    /**批量修改发货单
     * @param sendOrder
     */
    public int bacthUpdate(SendOrder sendOrder,String ids);

}
