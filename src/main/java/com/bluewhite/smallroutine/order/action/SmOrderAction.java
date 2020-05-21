 package com.bluewhite.smallroutine.order.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
      * 获取用户详细信息
      * ids 用户id
      */
     @RequestMapping(value = "/user/apiExtUser/info", method = RequestMethod.GET)
     @ResponseBody
     public CommonResponse userApiExtUserList(String ids) {
         if (ids==null || ids.isEmpty()) {
             throw new ServiceException("参数不能为空");
         };
         List<Object> list = new ArrayList<Object>();
         for(String id : ids.split(",")) {
        	 HashMap<String, Object> paramMap = new HashMap<String,Object>();
        	 paramMap.put("id", id);
        	 CommonResponse cr = ApiUtil.useApiCommonResponseGet(paramMap, ApiUtil.userApiExtUserInfo);
        	 if(cr.getCode()!=0)
        		 return cr;
        	 list.add(cr.getData());
         }
         return new CommonResponse(list);
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
     
     /**
      *  提现管理
      */
     @RequestMapping(value = "/user/extUserWithdraw/list", method = RequestMethod.GET)
     @ResponseBody
     public CommonResponse withdrawList(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         return ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userExtUserWithdrawList);
     }
     
     /**
      *  撤回用户提现
      *  id 记录id
      */
     @RequestMapping(value = "/user/extUserWithdraw/refuse", method = RequestMethod.POST)
     @ResponseBody
     public CommonResponse withdrawRefuse(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         return ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userExtUserPayRefuse);
     }
     
     /**
      *  手动设置用户提现成功
      *  id 记录id
      *  payGateTradeId 第三方订单号
      */
     @RequestMapping(value = "/user/extUserWithdraw/success", method = RequestMethod.POST)
     @ResponseBody
     public CommonResponse withdrawSuccess(@RequestParam HashMap<String, Object> paramMap) {
         if (MapUtil.isEmpty(paramMap)) {
             throw new ServiceException("参数不能为空");
         };
         return ApiUtil.useApiCommonResponse(paramMap, ApiUtil.userExtUserPaySuccess);
     }

}
