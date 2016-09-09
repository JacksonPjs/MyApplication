package com.android.ming.app;

/**
 * Created by KingYang on 16/4/14.
 * E-Mail: admin
 */
public class Consts {
    // APP常量
    public static class APP {
        public final static String CONTACT_QQ = "3481579428";// 客户QQ
        public final static String CHANNEL_NAME = "UMENG_CHANNEL";
    }

    // 服务器API接口地址
    public static class URL {
//        private final static String BASE_URL = "http://www.yanchi0539.com/Api/";// sing服务器API接口
        private final static String BASE_URL = "http://fire.loveshops365.com/Api/";// ping服务器API接口
//        private final static String BASE_URL = "http://fire.yanchi0539.com/Api/";// 测试服务器API接口
        public final static String NOTIFY_WFT = BASE_URL + "Order/wft";
        public final static String NOTIFY_SS = BASE_URL + "Order/shanshi";
        public final static String VIDEO_HOME = BASE_URL + "Video/getHome";
        public final static String CHANNEL_LIST = BASE_URL + "Channel/getList";
        public final static String VIDEO_BY_CHANNEL = BASE_URL + "Video/getByChannel/cid/%d";
        public final static String VIDEO_BY_ID = BASE_URL + "Video/getInfo/id/%d";
        public final static String COMMENT_BY_VID = BASE_URL + "Comment/getByVid/vid/%d";
        public final static String ACTIVE_VIP = BASE_URL + "Order/active/code/%s";
        public final static String ORDER_LOG = BASE_URL + "Order/log";
    }

    // SharedPreferences
    public static class SP {
        public final static String VIP = "vip";
        public final static String UID = "uid";
        public final static String LOGED = "loged";
        public static  String IS_FIRST="isfirst";
    }

    // 威富通手机qq支付
    public static class WFT {
        // 支付回调接口
        public final static String CREATE_ORDER = "https://paya.swiftpass.cn/pay/gateway";
        public final static String MCH_ID2 = "7552000165";//威富通商户号
        public final static String SIGN_KEY2 = "36f99edf70091e28ba45d76138e818be";//威富通密钥
        public final static String MCH_ID = "7552000194";//威富通商户号(微信？)
        public final static String SIGN_KEY = "d55b69d47d542039316f65d834ed8af6";//威富通密钥
    }
    public static class WapPay {
        // 支付回调接口
        private final static String BASE_URL = "http://112.74.230.8:8081/posp-api/";// ping服务器API接口

        public final static String WAPPAY = BASE_URL+"wapPay";
        public final static String QUERY = BASE_URL+"qrcodeQuery";
        public final static String NOTIFY_URL = "http://gf-info.cn:8085/result.jsp";


        public final static String MCH_ID = "688440357220001";
        public final static String SIGN_KEY = "53DDC95577CAC4E58E3B579842EA3188";
    }
    public static class NewPay{
        public final static  String url="http://wappay.vitongpay.com/wappay/pay";
        public final static  String CustomerNo="800002100110";//商户代号
        public final static  String GoodNo="GN10T";//商户商品代号
        public final static  String ProductNo="PN10T";//支付中心产品代号
        public final static  String OrderNo ="?";//商户订单号


    }
}
