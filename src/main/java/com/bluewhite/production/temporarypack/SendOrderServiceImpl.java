package com.bluewhite.production.temporarypack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.service.ConsumptionService;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
@Service
public class SendOrderServiceImpl extends BaseServiceImpl<SendOrder, Long> implements SendOrderService {

    @Autowired
    private SendOrderDao dao;
    @Autowired
    private UnderGoodsDao underGoodsDao;
    @Autowired
    private QuantitativeDao quantitativeDao;
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional
    public Quantitative saveSendOrder(Quantitative quantitative) {
        SendOrder sendOrder = new SendOrder();
        sendOrder.setAudit(0);
        sendOrder.setCustomerId(quantitative.getCustomerId());
        sendOrder.setSumPackageNumber(quantitative.getSumPackageNumber());
        sendOrder.setLogisticsId(quantitative.getLogisticsId());
        int sumNuber = 0;
        // 新增子单
        if (!StringUtils.isEmpty(quantitative.getChild())) {
            JSONArray jsonArray = JSON.parseArray(quantitative.getChild());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 下货单id
                Long underGoodsId = jsonObject.getLong("underGoodsId");
                UnderGoods underGoods = underGoodsDao.findOne(underGoodsId);
                SendOrderChild sendOrderChild = new SendOrderChild();
                sendOrderChild.setProductId(underGoods.getProductId());
                Product pro = productDao.findOne(sendOrderChild.getProductId());
                sendOrderChild.setProductName(pro.getName());
                sendOrderChild.setBacthNumber(underGoods.getBacthNumber());
                sendOrderChild.setSingleNumber(jsonObject.getInteger("singleNumber"));
                sendOrder.getSendOrderChild().add(sendOrderChild);
                sumNuber += (sendOrderChild.getSingleNumber() * quantitative.getSumPackageNumber());
            }
        }
        sendOrder.setNumber(sumNuber);
        dao.save(sendOrder);
        quantitative.setSendOrderId(sendOrder.getId());
        return quantitative;
    }

    @Override
    public void updateSendOrder(SendOrder sendOrder) {
        // 通过修改单价，计算总运费价格
        SendOrder ot = findOne(sendOrder.getId());
        BeanCopyUtils.copyNotEmpty(sendOrder, ot, "");
        ot.setSendPrice(NumberUtil.mul(ot.getSumPackageNumber(),ot.getSingerPrice()));
        ot.setLogisticsPrice(NumberUtil.add(ot.getExtraPrice(), ot.getSingerPrice()));
        save(ot);
    }

    @Override
    public PageResult<SendOrder> findPages(Map<String, Object> params, PageParameter page) {
        return findAll(page, params);
    }

    @Override
    @Transactional
    public int auditSendOrder(String ids, Integer audit) {
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
                    // 根据申请时间和物流点查询是否有已存在数据
                    Consumption consumption = consumptionService.findByTypeAndLogisticsIdAndExpenseDateBetween(5,
                        sendOrder.getCustomerId(), DateUtil.beginOfMonth(sendOrder.getSendTime()),
                        DateUtil.endOfMonth(sendOrder.getSendTime()));
                    // 审核，进行物流费用的新增
                    if (audit == 1) {
                        if (consumption == null) {
                            consumption = new Consumption();
                            consumption.setType(5);
                            consumption.setCustomerId(sendOrder.getCustomerId());
                            // 将发货时间赋值给申请时间
                            consumption.setExpenseDate(sendOrder.getSendTime());
                            consumption.setFlag(0);
                            consumption.setMoney(sendOrder.getLogisticsPrice().doubleValue());
                            consumption.setLogisticsId(sendOrder.getLogisticsId());
                        } else {
                            consumption.setMoney(NumberUtil.add(consumption.getMoney(), sendOrder.getLogisticsPrice()).doubleValue());
                        }
                    }
                    // 取消审核，进行物流费用的减少
                    if (audit == 0) {
                        consumption.setMoney(NumberUtil.sub(consumption.getMoney(), sendOrder.getLogisticsPrice()).doubleValue());
                    }
                    consumptionService.addConsumption(consumption);
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

}
