package com.liaoin.constant;

import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by Administrator on 2017/4/6.
 */
public interface WechatConstant {
    public static String APPID = "wx24b589359961eccc";
    public static String APPSECRET = "f93a4fbf050f2b81afb2cf9cdfacda3a";
    public static String PARTNER_ID = "1513515921";//商户号ID
    public static String PARTNER_KEY = "5e01RhcX1UZEG2LoCFE77zqgxHk9hQpB";// 财付通商户权限密钥 Key
    public static String HTTP_URL = "http://bdwl.dfbs-vm.com";//支付回调链接
    public static String NOTIFY_URL = HTTP_URL + "/Notify/index";//支付回调链接
    public static String PREPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String CREATE_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create";//二维码创建连接
    public static String TOKEN = "9AB776B93B3C6BBB67F4545371814423";//微信配置token
    public static String AUTHORIZATION_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx24b589359961eccc&redirect_uri=http%3A%2F%2Fbdwl.dfbs-vm.com%2Findex&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";//微信授权链接
    public static String JSAPI_TICKET = "jsapi_ticket";
    public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//获取access_token对应的url
    public static String GRANT_TYPE = "client_credential";//常量固定值
    public static String ACCESS_TOKEN = "access_token";//access_token常量值
    public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
    public static String traceid = "testtraceid001";//测试用户id
    public static String SIGN_METHOD = "sha1";//签名算法常量值
    public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";//获取预支付id的接口url
    public static String EXPIRE_ERRCODE = "42001";//access_token失效后请求返回的errcode
    public static String FAIL_ERRCODE = "40001";//重复获取导致上一次获取的access_token失效,返回错误码
    public static String ERRORCODE = "errcode";//用来判断access_token是否失效的值


    public interface WeChatTemplate{
        public String SEND_TEMPLAYE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";//?access_token=ACCESS_TOKEN

        public interface TemplateId{
            public String RECHARGE_SUCCESS_TEMPLATE_ID = "hMpfOskrxVUgbpwDmj9ZWfpPdZJLKCt_dxGlZitVisw";//充值成功消息模板ID
        }
    }
}
