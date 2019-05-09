package com.bluewhite.common;

public class Constants {
    /**
     * 操作名称
     */
	public static String OP_NAME = "op";

    /**
     * 消息key
     */
    public static String MESSAGE = "message";

    /**
     * 错误key
     */
    public static String ERROR = "error";

    /**
     * 上个页面地址
     */
    public static String BACK_URL = "BackURL";

    public static String IGNORE_BACK_URL = "ignoreBackURL";

    /**
     * 当前请求的地址 带参数
     */
    public static String CURRENT_URL = "currentURL";

    /**
     * 当前请求的地址 不带参数
     */
    public static String NO_QUERYSTRING_CURRENT_URL = "noQueryStringCurrentURL";

    public static String CONTEXT_PATH = "ctx";

    /**
     * 当前登录的用户
     */
    public static String CURRENT_USER = "user";
    public static String CURRENT_USERNAME = "username";

    public static String ENCODING = "UTF-8";
    
    public static final String PARAM_TOKEN = "token";
    public static final String PARAM_USERNAME = "username";
    /**
     * 生成随机字符串用的参考字符串
     */
    public static final String RANDOM_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWHYZ0123456789";
    
    /**        角色英文名称           **/
    
    /**
     *  生产部一楼质检
     */
    public static String PRODUCT_FRIST_QUALITY = "productFristQuality";
    //包含的部门
    public static String QUALITY_ORGNAME = "48";
    
    /**
     *  生产部一楼打包
     */
    public static String PRODUCT_FRIST_PACK = "productFristPack";
    //包含的部门
    public static String PACK_ORGNAME = "79";
    
    /**
     *  生产部二楼针工
     */
    public static String PRODUCT_TWO_DEEDLE = "productTwoDeedle";
    //包含的部门
    public static String DEEDLE_ORGNAME = "84";
    
    /**
     *  生产部二楼机工
     */
    public static String PRODUCT_TWO_MACHINIST = "productTwoMachinist";
    //包含的部门
    public static String MACHINIST_ORGNAME = "76";
    
    /**
     *  生产部八号裁剪  
     */
    public static String PRODUCT_RIGHT_TAILOR = "productEightTailor";
    //包含的部门
    public static String TAILOR_ORGNAME = "72";
    
    
    /**
     * 包装部
     */
    public static String  BAGABOARD = "大包上车";   
    public static String  BOXBOARD= "箱上车";
    
    /**
     *  试制部 
     */
    public static String TRIALPRODUCT = "trialProduce";
    
    
    
    
    
    /**
     *  电商订单状态
     */
    public static String  ONLINEORDER_1 = "TRADE_NO_CREATE_PAY";//(没有创建支付宝交易)
    public static String  ONLINEORDER_2 ="WAIT_BUYER_PAY";//(等待买家付款) 
    public static String  ONLINEORDER_3 ="SELLER_CONSIGNED_PART";//(卖家部分发货) 
    public static String  ONLINEORDER_4 ="WAIT_SELLER_SEND_GOODS";//(等待卖家发货,即:买家已付款)
    public static String  ONLINEORDER_5 ="WAIT_BUYER_CONFIRM_GOODS";//(等待买家确认收货,即:卖家已发货) 
    public static String  ONLINEORDER_6 ="TRADE_BUYER_SIGNED";//(买家已签收,货到付款专用) 
    public static String  ONLINEORDER_7 ="TRADE_FINISHED";//(交易成功); 
    public static String  ONLINEORDER_8 ="TRADE_CLOSED";//(付款以后用户退款成功，交易自动关闭) 
    public static String  ONLINEORDER_9 ="TRADE_CLOSED_BY_TAOBAO";//(付款以前，卖家或买家主动关闭交易) 
    public static String  ONLINEORDER_10 ="PAY_PENDING";//(国际信用卡支付付款确认中) 
    public static String  ONLINEORDER_11 ="WAIT_PRE_AUTH_CONFIRM";//(0元购合约中) 
    public static String  ONLINEORDER_12 ="PAID_FORBID_CONSIGN";//(拼团中订单或者发货强管控的订单，已付款但禁止发货)
    

}
