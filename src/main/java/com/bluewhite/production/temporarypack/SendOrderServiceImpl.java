 package com.bluewhite.production.temporarypack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;

/**
 * @author ZhangLiang
 * @date 2020/03/18
 */
@Service
public class SendOrderServiceImpl extends BaseServiceImpl<SendOrder, Long> implements SendOrderService{
    
    @Autowired
    private SendOrderDao dao;
    @Autowired
    private UnderGoodsDao underGoodsDao;
    @Autowired
    private QuantitativeDao quantitativeDao;

    @Override
    public Quantitative saveSendOrder(Quantitative quantitative) {
        SendOrder sendOrder = new SendOrder();
        sendOrder.setAudit(0);
        sendOrder.setCustomerId(quantitative.getCustomerId());
        sendOrder.setSumPackageNumber(quantitative.getSumPackageNumber());
        sendOrder.setLogisticsId(quantitative.getLogisticsId());
        int sumNuber = 0 ; 
        // 新增子单
        if (!StringUtils.isEmpty(quantitative.getChild())) {
            JSONArray jsonArray = JSON.parseArray(quantitative.getChild());
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //下货单id
                Long underGoodsId = jsonObject.getLong("underGoodsId");
                UnderGoods underGoods = underGoodsDao.findOne(underGoodsId);
                SendOrderChild sendOrderChild = new SendOrderChild();
                sendOrderChild.setProductId(underGoods.getProductId());
                sendOrderChild.setBacthNumber(underGoods.getBacthNumber());
                sendOrderChild.setSingleNumber(jsonObject.getInteger("singleNumber"));
                sendOrder.getSendOrderChild().add(sendOrderChild);
                sumNuber+=(sendOrderChild.getSingleNumber()*quantitative.getSumPackageNumber());
            }
        }
        sendOrder.setNumber(sumNuber);
        dao.save(sendOrder);
        quantitative.setSendOrderId(sendOrder.getId());
        return quantitative;
    }

    
    @Override
    public void updateSendOrder(SendOrder sendOrder) {
        //通过修改单价，计算总运费价格
        SendOrder ot = findOne(sendOrder.getId());
        update(sendOrder, ot, "");
    }


    @Override
    public PageResult<SendOrder> findPages(Map<String, Object> params, PageParameter page) {
         return findAll(page, params);
    }


    @Override
    public int auditSendOrder(String ids, Integer audit) {
        int count = 0;
        if (!StringUtils.isEmpty(ids)) {
            String[] idArr = ids.split(",");
            if (idArr.length > 0) {
                for (int i = 0; i < idArr.length; i++) {
                    Long id = Long.parseLong(idArr[i]);
                    SendOrder sendOrder = findOne(id);
                    if(sendOrder.getCustomerId()==null || sendOrder.getLogisticsId()==null || sendOrder.getOuterPackagingId()==null) {
                        throw new ServiceException("客户或物流公司或包装方式为空，无法审核发货单");
                    }
                    //审核，进行物流费用的新增
                    if(audit==1) {
                        
                    }
                    //取消审核，进行物流费用的减少
                    if(audit==0) {
                        
                    }
                }
                count++;
            }
        }
        return count;
         
    }


    @Override
    public List<Quantitative> getQuantitativeList(Long id) {
         return quantitativeDao.findBysendOrderId(id);
    }
    
    

}
