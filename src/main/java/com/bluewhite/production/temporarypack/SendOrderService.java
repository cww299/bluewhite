package com.bluewhite.production.temporarypack;

import com.bluewhite.base.BaseCRUDService;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
public interface SendOrderService extends BaseCRUDService<SendOrder, Long> {

    /**
     * 新增发货单
     * @param quantitative
     */
    public Quantitative saveSendOrder(Quantitative quantitative);

    /**修改发货单
     * @param sendOrder
     */
    public void updateSendOrder(SendOrder sendOrder);

}
