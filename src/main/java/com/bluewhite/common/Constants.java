package com.bluewhite.common;

public class Constants {

	/** Ail 密匙   **/
	public final static String ALI_APP_KEY = "4879538";
	public final static String ALI_APP_SECRET = "oBhKXN1W38";
	/** TOP 密匙   **/
	public final static String TOP_APP_KEY = "4879538";
	public final static String TOP_APP_SECRET = "oBhKXN1W38";
	
	/**Ail refreshToken 失效时期为半年，失效后重新获取，用户获取ali accessToken **/
	public final static String  ALI_REFRESH_TOKEN = "78d8da3d-d2e0-428b-b383-053fbd7cea2e";
	/**	获取  accessToken 的url   **/
	public final static String  ALI_URL = "https://gw.open.1688.com/openapi/http/1/system.oauth2/getToken/4879538";
	
	
	
	
    
    /**   角色英文名称           **/
    /**
     *  生产部一楼质检
     */
    public final static String PRODUCT_FRIST_QUALITY = "productFristQuality";
    //包含的部门
    public final static String QUALITY_ORGNAME = "48";
    /**
     *  生产部一楼打包
     */
    public final static String PRODUCT_FRIST_PACK = "productFristPack";
    //包含的部门
    public final static String PACK_ORGNAME = "79";
    /**
     *  生产部二楼针工
     */
    public final static String PRODUCT_TWO_DEEDLE = "productTwoDeedle";
    //包含的部门
    public final static String DEEDLE_ORGNAME = "84";
    /**
     *  生产部二楼机工
     */
    public final static String PRODUCT_TWO_MACHINIST = "productTwoMachinist";
    //包含的部门
    public final static String MACHINIST_ORGNAME = "76";
    /**
     *  生产部八号裁剪  
     */
    public final static String PRODUCT_RIGHT_TAILOR = "productEightTailor";
    //包含的部门
    public final static String TAILOR_ORGNAME = "72";
    /**
     * 包装部
     */
    public final static String  BAGABOARD = "上车";   
    /**
     *  试制部 
     */
    public final static String TRIALPRODUCT = "trialProduce";
    
    
    
    /******* 仓库管理员权限  ******/
    /**
     * 电商仓库管理员
     */
    public final static String ONLINEWAREHOUSE  = "onlineWarehouse";
    /**
     * 蓝白现场仓库管理员
     */
    public final static String SCENEWAREHOUSE  = "sceneWarehouse";
    /**
     * 蓝白成品仓库管理员
     */
    public final static String FINISHEDWAREHOUSE  = "finishedWarehouse";
    /**
     * 八号成品仓库管理员
     */
    public final static String EIGHTFINISHEDWAREHOUSE  = "eightFinishedWarehouse";
    
    
    /**
     * 人事考勤机ip
     */
    public final static String THREE_FLOOR = "192.168.1.204";
    public final static String ONE_FLOOR = "192.168.1.205";
//    public final static String TWO_FLOOR= "192.168.1.250";
    public final static String EIGHT_WAREHOUSE = "192.168.7.123";
    public final static String NEW_IGHT_WAREHOUSE = "192.168.6.73";
    public final static String ELEVEN_WAREHOUSE = "192.168.14.201";
    
    /**
     *  电商订单状态
     */
    public final static String  ONLINEORDER_1 = "TRADE_NO_CREATE_PAY";//(没有创建支付宝交易)
    public final static String  ONLINEORDER_2 ="WAIT_BUYER_PAY";//(等待买家付款) 
    public final static String  ONLINEORDER_3 ="SELLER_CONSIGNED_PART";//(卖家部分发货) 
    public final static String  ONLINEORDER_4 ="WAIT_SELLER_SEND_GOODS";//(等待卖家发货,即:买家已付款)
    public final static String  ONLINEORDER_5 ="WAIT_BUYER_CONFIRM_GOODS";//(等待买家确认收货,即:卖家已发货) 
    public final static String  ONLINEORDER_6 ="TRADE_BUYER_SIGNED";//(买家已签收,货到付款专用) 
    public final static String  ONLINEORDER_7 ="TRADE_FINISHED";//(交易成功); 
    public final static String  ONLINEORDER_8 ="TRADE_CLOSED";//(付款以后用户退款成功，交易自动关闭) 
    public final static String  ONLINEORDER_9 ="TRADE_CLOSED_BY_TAOBAO";//(付款以前，卖家或买家主动关闭交易) 
    
    /**
     *  电商单据编号前缀
     */
    public final static String  XS = "XS";
    public final static String  CK = "CK";
    public final static String  RK = "RK";
    public final static String  SC = "SC";
    public final static String  ZG = "ZG";
    
    
    /**
     * 财务销售单版权货物
     */
    public final static String  LX = "裸熊";
    public final static String  KT = "KT";
    public final static String  MW = "漫威";
    public final static String  BM = "哔莫";
    public final static String  LP = "老皮";
    public final static String  AB = "阿宝";
    public final static String  ZMJ = "芝麻街";
    public final static String  XXYJN = "熊熊遇见你";
    
    /**
     * 生产计划中的编号前缀
     */
    //加工单
    public final static String  JGD = "JGD";
    //外发加工单
    public final static String  WFJGD = "WFJGD";
    //生产耗料
    public final static String  SCHL = "SCHL";
    //生产领料
    public final static String  SCLL = "SCLL";
    //物料出库
    public final static String  WLCK = "WLCK";
    //物料入库
    public final static String  WLRK = "WLRK";
    //成品出库
    public final static String  CPCK = "CPCK";
    //成品入库
    public final static String  CPRK = "CPRK";
    //皮壳出库
    public final static String  PKCK = "PKCK";
    //皮壳入库
    public final static String  PKRK = "PKRK";
    //量化单
    public final static String  LHTB = "LHTB";
    //申请单
    public final static String  SQD = "SQD";
    
}
