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
         return ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userApiExtOrderlist);
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
         return ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userApiExtOrderListGoods);
     }
     
     
     /**
      * 获取所有用户（获取所有分销商）
      * 
      * isSeller true
      */
     @RequestMapping(value = "/user/apiExtUser/list", method = RequestMethod.GET)
     @ResponseBody
     public CommonResponse userApiExtUserList(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         return ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userApiExtUserList);
     }
     
     
     /**
      *  用户发展关系
      *  邀请人id  uidm
      *  获取分销商团队人数统计(根据时间  dateAddBegin   dateAddEnd)
      *  level  直接发展为1，间接发展为2
      */
     @RequestMapping(value = "/user/apiExtUserInviter/list", method = RequestMethod.GET)
     @ResponseBody
     public CommonResponse userApiExtUserInviterList(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         return ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userApiExtUserInviterList);
     }
     
     /**
      *  用户佣金明细
      *  分销商一段时间内的销售额
      *  uidm 收入用户编号
      *  uids 消费用户编号
      *  
      *  unit 0 现金返佣  1 积分返佣
      *  
      *  level 发展级别，1，2，3
      *  
      *  
      *  个人 团队
      */
     @RequestMapping(value = "/user/saleDistributionCommisionLog/list", method = RequestMethod.GET)
     @ResponseBody
     public CommonResponse userSaleDistributionCommisionLogList(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         paramMap.put("pageSize", Integer.MAX_VALUE);
         CommonResponse cr = ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userSaleDistributionCommisionLogList);
         return cr;
     }
     

}
