 package com.bluewhite.production.temporarypack;

import com.bluewhite.base.BaseCRUDService;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
public interface SendOrderService extends BaseCRUDService<SendOrder,Long>{

    /**
     * @param quantitative
     */
    void saveSendOrder(Quantitative quantitative);

}
