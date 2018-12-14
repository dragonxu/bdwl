package com.liaoin.wechat;

import com.google.gson.Gson;
import com.liaoin.constant.WechatConstant;
import com.liaoin.wechat.template.HttpRequestUtil;
import com.liaoin.wechat.template.TemplateData;
import com.liaoin.wechat.template.WechatTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class WeChatTemplateUtil {

    public static void sendTemplateMessage(String accessToken, WechatTemplate weixinTemplate) {
        String jsonString = new Gson().toJson(weixinTemplate);
        TreeMap<String, String> map = new TreeMap<String, String>();
        map.put("access_token", accessToken);
        String result = HttpRequestUtil.HttpDefaultExecute(HttpRequestUtil.POST_METHOD, WechatConstant.WeChatTemplate.SEND_TEMPLAYE_MESSAGE_URL, map, jsonString);
        log.info("微信推送消息结果："+result);
    }

    /**
     * 发送充值通知消息
     */
    public static void sendRechargeSuccessTemplateMessage(String openId, String account, float money, String orderId, String date){
        String accessToken = WechatUtils.getAccessToken().getAccess_token();
        log.info("为模板消息接口获取的accessToken是"+accessToken);
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(WechatConstant.WeChatTemplate.TemplateId.RECHARGE_SUCCESS_TEMPLATE_ID);
        wechatTemplate.setTouser(openId);
//        wechatTemplate.setUrl("#");

        Map<String,TemplateData> m = new HashMap<String,TemplateData>();
        TemplateData first = new TemplateData();
        first.setColor("#000000");
        first.setValue("会员充值成功提醒：");
        m.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setColor("#ea0000");
        keyword1.setValue(account);
        m.put("keyword1", keyword1);

        TemplateData keyword2 = new TemplateData();
        keyword2.setColor("#000000");
        keyword2.setValue(money+"");
        m.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();
        keyword3.setColor("#000000");
        keyword3.setValue(orderId+"");
        m.put("keyword3", keyword3);

        TemplateData keyword4 = new TemplateData();
        keyword4.setColor("#000000");
        keyword4.setValue(date);
        m.put("keyword4", keyword4);

        TemplateData remark = new TemplateData();
        remark.setColor("#000000");
        remark.setValue("详情请登录系统后台查看！");
        m.put("remark", remark);
        wechatTemplate.setData(m);
        try {
            WeChatTemplateUtil.sendTemplateMessage(accessToken, wechatTemplate);
        } catch (Exception e) {
            log.info("异常"+e.getMessage());
        }
    }
}
