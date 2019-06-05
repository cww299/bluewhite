package com.bluewhite.onlineretailers.inventory.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.utils.courier.KdGoldAPI;




@Controller
public class TopAction {
	
/*	@Autowired
	private OnlineOrderService onlineOrderService;
	@Autowired
	private OnlineCustomerService onlineCustomerService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private ProcurementService procurementService;
	@Autowired
	private TopService topService;
	
	
	
	*//********* 获取各店铺的授权 ***********//*
	
	
	*//****** 订单  *****//*
	
	*//** 
	 * 同步销售单
	 * 
	 *//*
	@RequestMapping(value = "/getSellerOrderList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getSellerOrderList(PageParameter page) {
		CommonResponse cr = new CommonResponse();
		//设置appkey和密钥(seckey)
		ApiExecutor apiExecutor = new ApiExecutor(Constants.ALI_APP_KEY,Constants.ALI_APP_SECRET);
		//订单列表
		AlibabaTradeGetSellerOrderListParam param  = new AlibabaTradeGetSellerOrderListParam();
		//调用API并获取返回结果
		AlibabaTradeGetSellerOrderListResult result = apiExecutor.execute(param,topService.getAccessToken()).getResult(); 
		AlibabaOpenplatformTradeModelTradeInfo[] list = null;
		if(result.getResult()!=null){
			list = result.getResult();
			if(list.length>0){
				for(AlibabaOpenplatformTradeModelTradeInfo info : list){
					OnlineOrder onlineOrder = new OnlineOrder();
					onlineOrder.setAddress(info.getNativeLogistics().getAddress());
					
				}
			}
			cr.setData(list);
			cr.setMessage("同步成功");
		}else{
			cr.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
			cr.setMessage("请重试！");
		}
		return cr;
	}
	
	*//****** 商品  *****//*
	
	*//**
	 * 同步商品
	 * @param onlineOrder
	 * @param page
	 * @return
	 *//*
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getProductList(PageParameter page) {
		CommonResponse cr = new CommonResponse();
		//设置appkey和密钥(seckey)
		ApiExecutor apiExecutor = new ApiExecutor(Constants.ALI_APP_KEY,Constants.ALI_APP_SECRET); 
		//商品列表
		AlibabaProductListGetParam  param = new AlibabaProductListGetParam();
		param.setPageNo(1);
		param.setPageSize(40);
		//调用API并获取返回结果
		AlibabaProductListGetResult result = apiExecutor.execute(param,topService.getAccessToken()).getResult(); 
		AlibabaProductProductInfoListResult list = null;
		if(result.getResult()!=null){
			list = result.getResult();
			Commodity Commodity = new Commodity();
		}
		cr.setData(list);
		cr.setMessage("查询成功");
		return cr;
	}*/
	
	
	// 电商ID
	private static String EBusinessID = "1530092";
	// 电商加密私钥，快递鸟提供，注意保管，不要泄漏
	private static String AppKey = "5353bbe7-bc47-4e5a-949f-f4dc42f54b4b";
    final Integer IsPreview = 1; //是否预览 0-不预览 1-预览

    @RequestMapping("/getPrintData")
    @ResponseBody
    public Map<String, Object> getCloudPrintData(HttpServletRequest request,String OrderCode,String ip) throws Exception{
        System.out.println("ok:"+OrderCode+"---"+ip);
        Map<String,Object> map = new HashMap<String, Object>();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String,Object> dataMap = new HashMap<String, Object>();
        dataMap.put("OrderCode", OrderCode);
        dataMap.put("PortName", "Xprinter XP-DT108A LABEL");
        list.add(dataMap);
        String jsonString = JSONArray.toJSONString(dataMap);
        
        map.put("RequestData", URLEncoder.encode(jsonString, "UTF-8"));
        if(StringUtils.isEmpty(ip)) {
            ip = getIpAddress(request);
        }
        map.put("DataSign",encrpy(ip + jsonString, AppKey));
        System.out.println("map:"+map);
        return map;
    }
    
    @RequestMapping("/getIpAddress")
    @ResponseBody
    public String getIpAddressByJava(HttpServletRequest request) throws IOException {
        return getIpAddress(request);
    }
    
    @RequestMapping("/electronicSurfaceSingle")
    @ResponseBody
    public CommonResponse electronicSurfaceSingle(HttpServletRequest request) throws Exception {
    	CommonResponse cr =new CommonResponse();
    	String result = KdGoldAPI.orderOnlineByJson();
		// 根据公司业务处理返回的信息......
		 Object jsonArray = JSON.parse(result);
		 cr.setData(jsonArray);
        return cr;
    }
    
    
    private String encrpy(String content, String key) throws UnsupportedEncodingException, Exception {
        String charset = "UTF-8";
        return new String(Base64.encode(md5(content + key, charset).getBytes(charset)));
    }
    
    private String md5(String str, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }
    
    // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
