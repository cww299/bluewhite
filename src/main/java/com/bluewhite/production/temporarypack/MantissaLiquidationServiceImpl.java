 package com.bluewhite.production.temporarypack;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.StringUtil;

import cn.hutool.core.date.DateUtil;

/**
 * @author zhangliang
 * @date 2020/01/10
 */
@Service
public class MantissaLiquidationServiceImpl extends BaseServiceImpl<MantissaLiquidation, Long> implements MantissaLiquidationService{
    
    @Autowired
    private MantissaLiquidationDao dao;
    
    @Override
    public void saveMantissaLiquidation(MantissaLiquidation mantissaLiquidation) {
        if(mantissaLiquidation.getId()!=null) {
            MantissaLiquidation ot = findOne(mantissaLiquidation.getId());
            update(mantissaLiquidation, ot, "");
        }else {
            int count = dao.findByTimeBetween(DatesUtil.getfristDayOftime(mantissaLiquidation.getTime()), DatesUtil.getLastDayOftime(mantissaLiquidation.getTime())).size();
            mantissaLiquidation.setMantissaNumber(Constants.LHTB + DateUtil.format(mantissaLiquidation.getTime(), "yyyyMMdd") + 
                    StringUtil.get0LeftString((count+1),4));
            save(mantissaLiquidation);
        }
    }

    @Override
    public PageResult<MantissaLiquidation> findPages(Map<String, Object> params, PageParameter page) {
         return findAll(page, params);
    }

}
