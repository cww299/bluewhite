 package com.bluewhite.production.temporarypack;

import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;

/**
 * @author ZhangLiang
 * @date 2020/01/10
 */
public interface MantissaLiquidationService extends BaseCRUDService<MantissaLiquidation,Long>{

    /**新增尾数清算
     * @param mantissaLiquidation
     */
    public void saveMantissaLiquidation(MantissaLiquidation mantissaLiquidation);

    /**分页查找
     * @param mantissaLiquidation
     * @param page
     * @return
     */
    public PageResult<MantissaLiquidation> findPages(Map<String, Object> params, PageParameter page);

    /**尾数单出库转换成下货单
     * @param underGoods
     */
    public void mantissaLiquidationToUnderGoods(MantissaLiquidation mantissaLiquidation);
}
