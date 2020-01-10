package com.bluewhite.production.temporarypack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.StringUtil;

import cn.hutool.core.date.DateUtil;

/**
 * @author ZhangLiang
 * @date 2020/01/10
 */
@Service
public class MantissaLiquidationServiceImpl extends BaseServiceImpl<MantissaLiquidation, Long>
    implements MantissaLiquidationService {

    @Autowired
    private MantissaLiquidationDao dao;
    @Autowired
    private UnderGoodsDao underGoodsDao;

    @Override
    public void saveMantissaLiquidation(MantissaLiquidation mantissaLiquidation) {
        if (mantissaLiquidation.getId() != null) {
            MantissaLiquidation ot = findOne(mantissaLiquidation.getId());
            update(mantissaLiquidation, ot, "");
        } else {
            int count = dao.findByTimeBetween(DatesUtil.getfristDayOftime(mantissaLiquidation.getTime()),
                DatesUtil.getLastDayOftime(mantissaLiquidation.getTime())).size();
            mantissaLiquidation
                .setMantissaNumber(Constants.LHTB + DateUtil.format(mantissaLiquidation.getTime(), "yyyyMMdd")
                    + StringUtil.get0LeftString((count + 1), 4));
            save(mantissaLiquidation);
        }
    }

    @Override
    public PageResult<MantissaLiquidation> findPages(Map<String, Object> params, PageParameter page) {
        PageResult<MantissaLiquidation> result = findAll(page, params);
        result.getRows().stream().forEach(m -> {
            List<UnderGoods> list = underGoodsDao.findByMantissaLiquidationId(m.getId());
            int numberSum = list.stream().mapToInt(UnderGoods::getNumber).sum();
            m.setSurplusNumber(m.getNumber()-numberSum);
        });
        return result;
    }

    @Override
    public void mantissaLiquidationToUnderGoods(MantissaLiquidation mantissaLiquidation) {
        if (mantissaLiquidation.getId() != null) {
            MantissaLiquidation ot = findOne(mantissaLiquidation.getId());
            // 获取原始下货单
            UnderGoods ug = ot.getUnderGoods();
            // 新增下货单
            UnderGoods underGoods = new UnderGoods();
            BeanCopyUtils.copyNotEmpty(ug, underGoods, "");
            underGoods.setId(null);
            underGoods.setNumber(mantissaLiquidation.getNumber());
            underGoods.setMantissaLiquidationId(mantissaLiquidation.getId());
            underGoods.setAllotTime(mantissaLiquidation.getTime());
            underGoodsDao.save(underGoods);
        }
    }

}
