package com.liaoin.wechat;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.liaoin.constant.WechatConstant;
import com.liaoin.util.HttpClient;
import com.liaoin.wxpay.CommonUtil;
import com.liaoin.wxpay.PayCommonUtil;
import com.liaoin.wxpay.util.MD5Util;
import com.liaoin.wxpay.util.XMLUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class WechatUtils {


    /**
     * 获取access_token
     */
    public static WeChatAccessToken getWeChatAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type","client_credential");
        params.put("appid",WechatConstant.APPID);
        params.put("secret",WechatConstant.APPSECRET);
        String resultJson = HttpClient.sendGet(url,params);
        WeChatAccessToken accessToken = new Gson().fromJson(resultJson,WeChatAccessToken.class);
        return accessToken;
    }

    //通过code换取网页授权access_token
    /**
     * @param code  用户授权码
     * @return mixed
     * @author 王灿
     * @since  2016-01-15
     */
    public static AccessToken getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String,String> params = new HashMap<String, String>();
        params.put("appid",WechatConstant.APPID);
        params.put("secret",WechatConstant.APPSECRET);
        params.put("code",code);
        params.put("grant_type","authorization_code");
        String resultJson = HttpClient.sendGet(url,params);
        System.out.println("resultJson：" + resultJson);
        AccessToken accessToken = new Gson().fromJson(resultJson,AccessToken.class);
        return accessToken;
        //返回结果
        // access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        //expires_in	access_token接口调用凭证超时时间，单位（秒）
        //refresh_token	用户刷新access_token
        //openid	用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
        //scope	用户授权的作用域，使用逗号（,）分隔
    }

    public static AccessToken getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,String> params = new HashMap<String, String>();
        params.put("appid",WechatConstant.APPID);
        params.put("secret",WechatConstant.APPSECRET);
        params.put("grant_type","client_credential");
        String resultJson = HttpClient.sendGet(url,params);
        AccessToken accessToken = new Gson().fromJson(resultJson,AccessToken.class);
        return accessToken;
        //返回结果
        // access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        //expires_in	access_token接口调用凭证超时时间，单位（秒）
        //refresh_token	用户刷新access_token
        //openid	用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
        //scope	用户授权的作用域，使用逗号（,）分隔
    }

    //获取用户信息
    /**
     * @param accessToken 用户token
     * @return mixed
     * @author 王灿
     * @since  2016-01-15
     */
    public static UserInfo get_user_info(String accessToken){
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String,String> params = new HashMap<String, String>();
        params.put("access_token",accessToken);
        params.put("openid",WechatConstant.APPID);
        params.put("lang","zh_CN");
        String resultJson = HttpClient.sendGet(url,params);
        System.out.println("get_user_info_resultJson：" + resultJson);
        UserInfo userInfo = new Gson().fromJson(resultJson,UserInfo.class);
        return userInfo;
        //返回结果
        //openid	用户的唯一标识
        //nickname	用户昵称
        //sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        //province	用户个人资料填写的省份
        //city	普通用户个人资料填写的城市
        //country	国家，如中国为CN
        //headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
        //privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
        //unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
    }

    //获取用户信息
    /**
     * @param accessToken 用户token
     * @return mixed
     * @author 王灿
     * @since  2016-01-15
     */
    public static UserInfo get_user_info(String accessToken,String openId){
        String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String,String> params = new HashMap<String, String>();
        params.put("access_token",accessToken);
        params.put("openid",openId);
        params.put("lang","zh_CN");
        String resultJson = HttpClient.sendGet(url,params);
        System.out.println("get_user_info_resultJson：" + resultJson);
        UserInfo userInfo = new Gson().fromJson(resultJson,UserInfo.class);
        return userInfo;
        //返回结果
        //openid	用户的唯一标识
        //nickname	用户昵称
        //sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        //province	用户个人资料填写的省份
        //city	普通用户个人资料填写的城市
        //country	国家，如中国为CN
        //headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
        //privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
        //unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
    }

//    public static String genProductArgs(Order order) throws JSONException {
//        StringBuffer xml = new StringBuffer();
//        try {
//            String nonceStr = genNonceStr();
//            xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//            packageParams.add(new BasicNameValuePair("appid", WeChat.APPID));
////            byte[] body = ("运费").getBytes();
////            packageParams.add(new BasicNameValuePair("body",new String(body,"utf-8")));
//            packageParams.add(new BasicNameValuePair("body","11"));
//            packageParams.add(new BasicNameValuePair("mch_id",WeChat.PARTNER_ID));
//            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//            packageParams.add(new BasicNameValuePair("notify_url", WeChat.NOTIFY_URL));
//            packageParams.add(new BasicNameValuePair("out_trade_no", order.getId()));
//            InetAddress inetAddress = null;
//            try {
//                inetAddress = inetAddress.getLocalHost();
//                String ip = inetAddress.getHostAddress();
//                packageParams.add(new BasicNameValuePair("spbill_create_ip", ip));
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//            packageParams.add(new BasicNameValuePair("total_fee", (int)(0.01*100)+""));
//            packageParams.add(new BasicNameValuePair("trade_type", "JSAPI"));
//            packageParams.add(new BasicNameValuePair("openid", order.getWeiXinUser().getOpenid()));
//            String sign = genPackageSign(packageParams);
//            packageParams.add(new BasicNameValuePair("sign", sign));
//            String xmlstring = toXml(packageParams);
//            return xmlstring;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public static String genNonceStr() {
        Random random = new Random();
        return MD5Util.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    public static String genOutTradNo() {
        Random random = new Random();
        return MD5Util.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 生成签名
     */
    public static String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(WechatConstant.PARTNER_KEY);
        String packageSign = MD5Util.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }
    public static String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");

            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static String parseXmlToList(String xml) throws JDOMException, IOException {
        StringReader read = new StringReader(xml);
        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        // 创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        // 通过输入源构造一个Document
        Document doc = (Document) sb.build(source);
        Element root = doc.getRootElement();// 指向根节点
        Element element = root.getChild("prepay_id");
        String text ="";
        if(element!=null){
            text = element.getText();
        }
        return text;
    }

    public static Map parseXmlToMap(String xml) {
        Map retMap = new TreeMap();
        try {
            StringReader read = new StringReader(xml);
// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
// 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
// 通过输入源构造一个Document
            org.jdom.Document doc =  sb.build(source);
            Element root = (Element) doc.getRootElement();// 指向根节点
            List<Element> es = root.getChildren();
            if (es != null && es.size() != 0) {
                for (Element element : es) {
                    retMap.put(element.getName(), element.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMap;
    }

    //重点：封装参数调用统一下单接口，生成prepay_id(预支付订单Id)
    /**
     * 微信支付
     * @param orderId 订单编号
     * @param money 实际支付金额
     * @return
     */
//    private static String generateOrderInfoByWeiXinPay(String orderId, float actualPay,HttpServletRequest request,HttpServletResponse response) throws Exception {
    public static String generateOrderInfoByWeiXinPay(String orderId,int money,String openId) throws Exception {
        String uuid = genNonceStr();
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("appid", WechatConstant.APPID);//app_id
        signParams.put("body", "维修");//商品参数信息
        signParams.put("mch_id", WechatConstant.PARTNER_ID);//微信商户账号
        signParams.put("nonce_str",uuid);//32位不重复的编号
        signParams.put("notify_url", WechatConstant.NOTIFY_URL);//回调页面
        signParams.put("out_trade_no", orderId);//订单编号
        InetAddress inetAddress = null;
        try {
            inetAddress = inetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            signParams.put("spbill_create_ip", ip);//请求的实际ip地址
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        signParams.put("total_fee", money+"");//支付金额 单位为分
        signParams.put("trade_type", "JSAPI");
        signParams.put("openid", openId);
//        付款类型为APP
        String sign = PayCommonUtil.createSign("UTF-8", signParams);//生成签名
        signParams.put("sign", sign);
        signParams.remove("key");//调用统一下单无需key（商户应用密钥）
        String requestXml = PayCommonUtil.getRequestXml(signParams);//生成Xml格式的字符串
        String result = CommonUtil.httpsRequest(WechatConstant.PREPAY_URL, "POST", requestXml);//以post请求的方式调用统一下单接口
//        （注：ConstantUtil.UNIFIED_ORDER_URL = https://api.mch.weixin.qq.com/pay/unifiedorder;）

//        返回的result成功结果取出prepay_id：
        Map map = XMLUtil.doXMLParse(result);
        String return_code = (String) map.get("return_code");
        String prepay_id = null;
        String returnSign = null;
        String returnNonce_str = null;
        if (return_code.contains("SUCCESS")) {
            prepay_id = (String) map.get("prepay_id");//获取到prepay_id
        }
        StringBuffer weiXinVo = new StringBuffer();
        long currentTimeMillis = System.currentTimeMillis();//生成时间戳
        long second = currentTimeMillis / 1000L;//（转换成秒）
        String seconds = String.valueOf(second).substring(0, 10);//（截取前10位）
        SortedMap<String, String> signParam = new TreeMap<String, String>();
        signParam.put("appid", WechatConstant.APPID);//app_id
        signParam.put("partnerid", WechatConstant.PARTNER_ID);//微信商户账号
        signParam.put("prepayid", prepay_id);//预付订单id
        signParam.put("package", "Sign=WXPay");//默认sign=WXPay
        signParam.put("noncestr", uuid);//自定义不重复的长度不长于32位
        signParam.put("timestamp", seconds);//北京时间时间戳
        String signAgain = PayCommonUtil.createSign("UTF-8", signParam);//再次生成签名
        signParams.put("sign", signAgain);
        weiXinVo.append("appid=").append(WechatConstant.APPID).append("&partnerid=").append(WechatConstant.PARTNER_ID).append("&prepayid=").append(prepay_id).append("&package=Sign=WXPay").append("&noncestr=").append(uuid).append("&timestamp=").append(seconds).append("&sign=").append(signAgain);//拼接参数返回给移动端
        return weiXinVo.toString();
    }

    /**
     * 发起微信支付
     * @param orderId
     * @param money
     * @param openId
     * @return
     * @throws Exception
     */
    public static Map<String,String> weChatPay(String orderId,int money,String openId) throws Exception {
        String uuid = genNonceStr();
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("appid", WechatConstant.APPID);//app_id
        signParams.put("body", "博达物联");//商品参数信息
        signParams.put("mch_id", WechatConstant.PARTNER_ID);//微信商户账号
        signParams.put("nonce_str",uuid);//32位不重复的编号
        signParams.put("notify_url", WechatConstant.NOTIFY_URL);//回调页面
        signParams.put("out_trade_no", orderId);//订单编号
        InetAddress inetAddress = null;
        try {
            inetAddress = inetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            signParams.put("spbill_create_ip", ip);//请求的实际ip地址
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        signParams.put("total_fee", money+"");//支付金额 单位为分
//        signParams.put("total_fee", 1+"");//支付金额 单位为分
        signParams.put("trade_type", "JSAPI");
        signParams.put("openid", openId);
//        付款类型为APP
        String sign = PayCommonUtil.createSign("UTF-8", signParams);//生成签名
        signParams.put("sign", sign);
        signParams.remove("key");//调用统一下单无需key（商户应用密钥）
        String requestXml = PayCommonUtil.getRequestXml(signParams);//生成Xml格式的字符串
        String result = CommonUtil.httpsRequest(WechatConstant.PREPAY_URL, "POST", requestXml);//以post请求的方式调用统一下单接口
//        （注：ConstantUtil.UNIFIED_ORDER_URL = https://api.mch.weixin.qq.com/pay/unifiedorder;）

//        返回的result成功结果取出prepay_id：
        Map<String,String> map = XMLUtil.doXMLParse(result);
        String return_code = (String) map.get("return_code");
        String prepay_id = null;
        if (return_code.contains("SUCCESS")) {
            prepay_id = (String) map.get("prepay_id");//获取到prepay_id
            map.put("prepay_id","prepay_id="+prepay_id);
        }
        long currentTimeMillis = System.currentTimeMillis();//生成时间戳
        long second = currentTimeMillis / 1000L;//（转换成秒）
        String seconds = String.valueOf(second).substring(0, 10);//（截取前10位）
        map.put("time",seconds);
        map.put("signType", "MD5");
        SortedMap<String, String> signParam = new TreeMap<String, String>();
        signParam.put("appId", WechatConstant.APPID);
        signParam.put("nonceStr", (String) map.get("nonce_str"));
        signParam.put("timeStamp", seconds);
        signParam.put("package", (String) map.get("prepay_id"));
        signParam.put("signType", "MD5");
        String signAgain = PayCommonUtil.createSign("UTF-8", signParam);//再次生成签名
        map.put("sign", signAgain);
        return map;
    }

    public static String getJsapiTicket(){
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type","client_credential");
        params.put("appid",WechatConstant.APPID);
        params.put("secret",WechatConstant.APPSECRET);
        String result = HttpClient.sendGet(requestUrl,params);

        String access_token = JSONObject.parseObject(result).getString("access_token");
        requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
        Map<String,String> params2 = new HashMap<>();
        params2.put("access_token",access_token);
        params2.put("type","jsapi");
        result = HttpClient.sendGet(requestUrl,params2);
        String jsapi_ticket = JSONObject.parseObject(result).getString("ticket");
        return jsapi_ticket;
    }

}
