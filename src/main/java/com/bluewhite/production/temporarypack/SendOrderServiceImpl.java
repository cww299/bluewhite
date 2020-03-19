 package com.bluewhite.production.temporarypack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;

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

    @Override
    public void saveSendOrder(Quantitative quantitative) {
        SendOrder sendOrder = new SendOrder();
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
                if (underGoods.getNumber() == null) {
                    throw new ServiceException("贴包数量未填写，无法新增");
                }
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
         
    }
    
    

}
