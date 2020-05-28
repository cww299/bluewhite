package com.bluewhite.production.temporarypack;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.entity.PageResultStat;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.common.utils.AutoSearchUtils.SearchUtils;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.service.ConsumptionService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
@Service
public class SendOrderServiceImpl extends BaseServiceImpl<SendOrder, Long> implements SendOrderService {

    @Autowired
    private QuantitativeDao quantitativeDao;
    @Autowired
    private ConsumptionService consumptionService;


    @Override
    public void updateSendOrder(SendOrder sendOrder) {
        // 通过修改单价，计算总运费价格
        SendOrder ot = findOne(sendOrder.getId());
        if (ot.getAudit() == 1) {
            throw new ServiceException("发货单已生成物流费用，无法修改");
        }
        BeanCopyUtils.copyNotEmpty(sendOrder, ot, "logisticsId", "outerPackagingId", "singerPrice", "tax",
            "logisticsNumber", "extraPrice","remarks");
        if (ot.getSendPackageNumber() != null && ot.getSingerPrice() != null) {
            ot.setSendPrice(NumberUtil.mul(ot.getSendPackageNumber(), ot.getSingerPrice()));
            ot.setLogisticsPrice(NumberUtil.add(ot.getExtraPrice(), ot.getSendPrice()));
        }
        if (ot.getExtraPrice() != null) {
            ot.setLogisticsPrice(NumberUtil.add(ot.getExtraPrice(), ot.getSendPrice()));
        }
        save(ot);
    }

    @Override
    public PageResult<SendOrder> findPages(Map<String, Object> params, PageParameter page) {
        Page<SendOrder> pages = baseRepository.findAll((root, query, cb) -> {
            SearchUtils.autoBuildQuery(root, query, cb, params);
            return null;
        }, StringUtil.getQueryNoPageParameter());
        PageResultStat<SendOrder> result = new PageResultStat<>(pages, page);
        result.setAutoStateField("sendPackageNumber", "sendPrice", "extraPrice", "logisticsPrice");
        result.count();
        return result;
    }

    @Override
    @Transactional
    public int auditSendOrder(String ids, Date expenseDate, Date expectDate) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    SendOrder sendOrder = findOne(id);
                    if (sendOrder.getCustomerId() == null || sendOrder.getLogisticsId() == null
                        || sendOrder.getOuterPackagingId() == null) {
                        throw new ServiceException("客户或物流公司或包装方式为空，无法审核发货单");
                    }
                    if (sendOrder.getSendPackageNumber() == 0) {
                        throw new ServiceException("未发货无法生成物流费");
                    }
                    if(sendOrder.getAudit()==1) {
                        throw new ServiceException("发货单已生成物流费用，无需多次生成");
                    }
                    // 生成当前物流当月的父类应付账单
                    // 根据申请时间和物流点查询是否有已存在数据
                    Consumption consumptionPrent =
                        consumptionService.findByTypeAndLogisticsIdAndExpenseDateBetween(5, sendOrder.getLogisticsId(),
                            DateUtil.beginOfMonth(expenseDate), DateUtil.endOfMonth(expenseDate));
                    if (consumptionPrent != null) {
                        BigDecimal money = new BigDecimal(consumptionPrent.getMoney().toString());
                        consumptionPrent.setMoney(money.add(sendOrder.getLogisticsPrice()).doubleValue());
                    } else {
                        consumptionPrent = new Consumption();
                        consumptionPrent.setParentId((long)0);
                        consumptionPrent.setType(5);
                        consumptionPrent.setExpenseDate(expenseDate);
                        consumptionPrent.setExpectDate(expectDate);
                        consumptionPrent.setFlag(0);
                        consumptionPrent.setMoney(sendOrder.getLogisticsPrice().doubleValue());
                        consumptionPrent.setLogisticsId(sendOrder.getLogisticsId());
                    }
                    consumptionService.save(consumptionPrent);
                    // 生成子类条单据
                    Consumption consumption = new Consumption();
                    consumption.setParentId(consumptionPrent.getId());
                    consumption.setSendOrderId(id);
                    consumption.setType(5);
                    consumption.setExpenseDate(expenseDate);
                    consumption.setExpectDate(expectDate);
                    consumption.setFlag(0);
                    consumption.setMoney(sendOrder.getLogisticsPrice().doubleValue());
                    consumption.setLogisticsId(sendOrder.getLogisticsId());
                    consumption.setCustomerId(sendOrder.getCustomerId());
                    // 无法取消审核，在物流申请中删除单据
                    consumptionService.addConsumption(consumption);
                    sendOrder.setAudit(1);
                    save(sendOrder);
                }
                count++;
            }
        }
        return count;
    }

    @Override
    public List<Quantitative> getQuantitativeList(Long id) {
        return quantitativeDao.findBySendOrderId(id);
    }

    @Override
    public int bacthUpdate(SendOrder sendOrder, String ids) {
        int count = 0;
        String[] idsArr = ids.split(",");
        if (idsArr != null) {
            for (String id : idsArr) {
                // 通过修改单价，计算总运费价格
                SendOrder ot = findOne(Long.valueOf(id));
                if (ot.getAudit() == 1) {
                    throw new ServiceException("发货单已生成物流费用，无法修改");
                }
                BeanCopyUtils.copyNotEmpty(sendOrder, ot, "");
                if (ot.getSendPackageNumber() != null && ot.getSingerPrice() != null) {
                    ot.setSendPrice(NumberUtil.mul(ot.getSendPackageNumber(), ot.getSingerPrice()));
                    ot.setLogisticsPrice(NumberUtil.add(ot.getExtraPrice(), ot.getSendPrice()));
                }
                if (ot.getExtraPrice() != null) {
                    ot.setLogisticsPrice(NumberUtil.add(ot.getExtraPrice(), ot.getSendPrice()));
                }
                save(ot);
                count++;
            }
        }
        return count;
    }
}
