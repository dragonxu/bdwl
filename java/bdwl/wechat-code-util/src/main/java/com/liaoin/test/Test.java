package com.liaoin.test;

import com.liaoin.util.DateFormat;
import com.liaoin.wechat.WeChatTemplateUtil;
import org.springframework.boot.SpringApplication;

import java.net.URLEncoder;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
//        String url = "http://bdwl.cqfuyuan.cn/index";
//        System.out.println(URLEncoder.encode(url));
        WeChatTemplateUtil.sendRechargeSuccessTemplateMessage("o--Z95kr4Jvkh1exKzgE_dhz_flM","会员名称",10,"订单号",DateFormat.stampToDate(new Date().getTime()+""));
    }
}
