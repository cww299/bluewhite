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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.bluewhite.ledger.dao.CustomerDao;
import com.bluewhite.ledger.dao.LogisticsCostsDao;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.LogisticsCosts;
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
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LogisticsCostsDao logisticsCostsDao;

    @Override
    @Transactional
    public Quantitative saveSendOrder(Quantitative quantitative) {
        SendOrder sendOrder = new SendOrder();
        if (quantitative.getCustomerId() != null) {
            // 内部员工发货单在发货时创建
            Customer customer = customerDao.findOne(quantitative.getCustomerId());
            if (customer.getInterior() == 1) {
                return quantitative;
            }
            List<LogisticsCosts> list = logisticsCostsDao.findByCustomerId(quantitative.getCustomerId());
            if (list.size() > 0) {
                sendOrder.setOuterPackagingId(list.get(0).getOuterPackagingId());
                sendOrder.setLogisticsId(list.get(0).getLogisticsId());
                sendOrder.setSingerPrice(new BigDecimal(list.get(0).getTaxIncluded()));
            } else {
                sendOrder.setLogisticsId(quantitative.getLogisticsId());
            }
        }
        sendOrder.setTax(1);
        sendOrder.setAudit(0);
        sendOrder.setCustomerId(quantitative.getCustomerId());
        sendOrder.setSendPackageNumber(0);
        sendOrder.setSumPackageNumber(quantitative.getSumPackageNumber());
        sendOrder.setInterior(0);

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
                sendOrder.setWarehouseTypeId(underGoods.getWarehouseTypeId());
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
        if (ot.getAudit() == 1) {
            throw new ServiceException("发货单已生成物流费用，无法修改");
        }
        BeanCopyUtils.copyNotEmpty(sendOrder, ot, "logisticsId", "outerPackagingId", "singerPrice", "tax",
            "logisticsNumber", "extraPrice");
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
    public int auditSendOrder(String ids, Date expenseDate, Date paymentDate) {
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
                    if(sendOrder.getSendPackageNumber()==0) {
                        throw new ServiceException("未发货无法生成物流费");
                    }
                    //当总数等于已发包数 全部审核
                    if(sendOrder.getSumPackageNumber()==sendOrder.getSendPackageNumber()) {
                        sendOrder.setAudit(1);
                    }
                    //当总数大于已发包数 部分生成
                    if(sendOrder.getSumPackageNumber()<sendOrder.getSendPackageNumber()) {
                        sendOrder.setAudit(2);
                    }
                    //生成当前物流当月的父类应付账单
                    // 根据申请时间和物流点查询是否有已存在数据
                    Consumption  consumptionPrent =  consumptionService.findByTypeAndLogisticsIdAndExpenseDateBetween(5, id, DateUtil.beginOfMonth(expenseDate), DateUtil.endOfMonth(expenseDate));
                    if(consumptionPrent!=null) {
                        
                    } else {
                        
                        
                    }
                    
                    
                   
                    Consumption consumption = new Consumption();
                    // 审核，进行物流费用的新增
                    Consumption ot = consumptionService.findBySendOrderId(id);
                    if (ot != null) {
                        // 获取已生成金额
                        BigDecimal money = new BigDecimal(ot.getMoney().toString());
                        // 相等表示费用全部生成
                        if (money.compareTo(sendOrder.getLogisticsPrice()) == 0) {
                            throw new ServiceException("发货单已生成物流费用，无需多次生成");
                        }
                        // 小于表示有新增发货的，费用增加
                        if (money.compareTo(sendOrder.getLogisticsPrice()) == -1) {
                            consumption.setMoney(sendOrder.getLogisticsPrice().subtract(money).doubleValue());
                        }
                    }
                    consumption.setSendOrderId(id);
                    consumption.setType(5);
                    consumption.setExpenseDate(expenseDate);
                    consumption.setPaymentDate(paymentDate);
                    consumption.setFlag(0);
                    consumption.setMoney(sendOrder.getLogisticsPrice().doubleValue());
                    consumption.setLogisticsId(sendOrder.getLogisticsId());
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
