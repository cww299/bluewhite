 package com.bluewhite.smallroutine.order.action;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.utils.apiUtil.ApiUtil;

import cn.hutool.core.map.MapUtil;

/**
 * @author ZhangLiang
 * @date 2020/04/01
 */
 @Controller
public class SmOrderAction {
     
     
     /**
      * 获取订单信息 
      */
     @RequestMapping(value = "/apiExtOrder/list", method = RequestMethod.GET)
     @ResponseBody
     public CommonResponse apiExtOrderList(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         return ApiUtil.userApi(paramMap, ApiUtil.userApiExtOrderlist);
     }
    
     /**
      * 拉取订单商品明细
      * dateBegin 开始时间 
      * dateEnd结束时间
      */
     @RequestMapping(value = "/apiExtOrder/list/goods", method = RequestMethod.GET)
     @ResponseBody
     public CommonResponse apiExtOrderListGoods(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         return ApiUtil.userApi(paramMap, ApiUtil.userApiExtOrderListGoods);
     }


}
