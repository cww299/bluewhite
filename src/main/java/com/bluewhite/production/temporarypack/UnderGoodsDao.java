package com.bluewhite.production.temporarypack;

import java.util.List;

import com.bluewhite.base.BaseRepository;

public interface UnderGoodsDao extends BaseRepository<UnderGoods, Long>{

    /**
     * 根据尾货单查找
     * 
     * @param id
     * @return
     */
    List<UnderGoods> findByMantissaLiquidationId(Long id);


}
