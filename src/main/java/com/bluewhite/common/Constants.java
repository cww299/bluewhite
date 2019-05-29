package com.bluewhite.common;

public class Constants {

	/** Ail 密匙   **/
	public static String ALI_APP_KEY = "4879538";
	public static String ALI_APP_SECRET = "oBhKXN1W38";
	/** TOP 密匙   **/
	public static String TOP_APP_KEY = "4879538";
	public static String TOP_APP_SECRET = "oBhKXN1W38";
	
	/**Ail refreshToken 失效时期为半年，失效后重新获取，用户获取ali accessToken **/
	public static String  ALI_REFRESH_TOKEN = "78d8da3d-d2e0-428b-b383-053fbd7cea2e";
	/**	获取  accessToken 的url   **/
	public static String  ALI_URL = "https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/4879538";
	
	
	
	
    
    /**   角色英文名称           **/
    
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
    
    /**
     *  电商单据编号前缀
     */
    public static String  XS = "XS";
    public static String  CK = "CK";
    public static String  RK = "RK";
    public static String  SC = "SC";
    public static String  ZG = "ZG";
    

}
