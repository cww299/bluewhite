package com.bluewhite.common.utils.apiUtil;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.bluewhite.common.entity.CommonResponse;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * api工厂后台接口
 * 
 * @author ZhangLiang
 * @date 2020/04/01
 */
@Component
public class ApiUtil {
    

    /** api工厂 商户号和密匙 **/
    public final static String API_MERCHANTNO = "2002200667633022";
    public final static String API_MERCHANTKEY = "d2847356722bdad2652268957eb82eea";
    public final static String API_URl = "https://user.api.it120.cc";
    /**
     * token 有效性验证
     */
    public final static String userCheckToken = "/user/checkToken";
    /**
     * 登录接口
     */
    public final static String loginKey = "/login/key";
    /**
     * 获取订单信息 
     * page 
     * pageSize 
     * dateAddBegin 
     * dateAddEnd 
     * dateUpdateBegin 
     * dateUpdateEnd 
     * extendKeywords 
     * goodsId 
     * goodsName
     * mobile 
     * nick 
     * orderNumber 
     * refundStatus 退款状态：0无退款 ;1有退款；2退款中；3退款成功 
     * shopId 门店id 
     * status 订单状态 -1关闭订单；0待支付；1已支付待发货；2已发货待确认收货；3确认收货待评价；4已评价
     */
    public final static String userApiExtOrderlist = "/user/apiExtOrder/list";
    /** 拉取订单商品明细
     *  dateBegin 开始时间 
     *  dateEnd结束时间
     */
    public final static String userApiExtOrderListGoods = "/user/apiExtOrder/list/goods";
    /**
     * 用户列表
     */
    public final static String userApiExtUserList = "/user/apiExtUser/list";
    
    /**
     * 用户详细信息
     */
    public final static String userApiExtUserInfo = "/user/apiExtUser/info";
    
    /** 佣金明细
     */
    public final static String userSaleDistributionCommisionLogList = "/user/saleDistributionCommisionLog/list";
    
    /**
     * 用户发展关系
     */
    public final static String userApiExtUserInviterList = "/user/apiExtUserInviter/list";
    
    /**
     * 资金明细
     */
    public final static String userApiExtUserCashLogList = "/user/apiExtUserCashLog/list";

    /**
     * 提现列表
     */
    public final static String userExtUserWithdrawList = "/user/extUserWithdraw/list";
    
    /**
     * 提现成功
     */
    public final static String userExtUserPaySuccess = "/user/apiExtUserPay/success";
    
    /**
     * 提现失败
     */
    public final static String userExtUserPayRefuse = "/user/extUserWithdraw/refuse";
    
    /**
     * 返回数据格式化 url 接口地址 paramMap参数
     */
    public static CommonResponse useApiCommonResponse(HashMap<String, Object> paramMap, String url) {
        CommonResponse commonResponse = new CommonResponse();
        String result = HttpRequest.post(API_URl + url).header("X-Token", loginGetToken())// 头信息，多个头信息多次调用此方法即可
            .form(paramMap)// 表单内容
            .execute().body();
        JSONObject jSONObject = JSONUtil.parseObj(result);
        commonResponse.setCode(jSONObject.getInt("code"));
        commonResponse.setData(jSONObject.getObj("data"));
        commonResponse.setMessage(jSONObject.getStr("msg"));
        return commonResponse;
    }
    
    /**
     * get请求
     * 返回数据格式化 url 
     * 接口地址 paramMap参数
     */
    public static CommonResponse useApiCommonResponseGet(HashMap<String, Object> paramMap, String url) {
        CommonResponse commonResponse = new CommonResponse();
        String result = HttpRequest.get(API_URl + url).header("X-Token", loginGetToken())// 头信息，多个头信息多次调用此方法即可
            .form(paramMap)// 表单内容
            .execute().body();
        JSONObject jSONObject = JSONUtil.parseObj(result);
        commonResponse.setCode(jSONObject.getInt("code"));
        commonResponse.setData(jSONObject.getObj("data"));
        commonResponse.setMessage(jSONObject.getStr("msg"));
        return commonResponse;
    }
    
    /**
     * 返回数据格式化 url 接口地址 paramMap参数
     */
    public static JSONObject useApi(HashMap<String, Object> paramMap, String url) {
        String result = HttpRequest.post(API_URl + url).header("X-Token", loginGetToken())// 头信息，多个头信息多次调用此方法即可
            .form(paramMap)// 表单内容
            .execute().body();
        return JSONUtil.parseObj(result);
    }
    

    /**
     * 登录获取token
     */
    public static String loginGetToken() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("merchantKey", API_MERCHANTKEY);
        paramMap.put("merchantNo", API_MERCHANTNO);
        String result = HttpUtil.post(API_URl + loginKey, paramMap);
        JSONObject jSONObject = JSONUtil.parseObj(result);
        return (String)jSONObject.get("data");
//        Cache<String, User> token = ApiUtil.cacheManager.getCache("api-token");
//        if(token==null) {
//        }else {
//            return token.toString();
//        }
    }

    public static void main(String[] args) {
        HashMap<String, Object> paramMap = new HashMap<>();
        System.out.println(useApiCommonResponse(paramMap, userApiExtUserList).getData());
    }

}
