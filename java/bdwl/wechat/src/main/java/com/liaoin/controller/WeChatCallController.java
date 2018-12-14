package com.liaoin.controller;

import com.liaoin.constant.WechatConstant;
import com.liaoin.util.Decript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7.
 */
@Controller
@RequestMapping("WeChatCall")
@Slf4j
public class WeChatCallController {

    /**
     * token验证
     */
    @RequestMapping(value = "/checkSignature",method = RequestMethod.GET)
    @ResponseBody
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("开始签名校验");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        ArrayList<String> array = new ArrayList<String>();
        array.add(signature);
        array.add(timestamp);
        array.add(nonce);

        //排序
        String sortString = Decript.sort(WechatConstant.TOKEN, timestamp, nonce);
        //加密
        String mytoken = Decript.SHA1(sortString);
        //校验签名
        if (mytoken != null && mytoken != "" && mytoken.equals(signature)) {
            log.info("签名校验通过。");
            response.getWriter().println(echostr); //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
        } else {
            log.info("签名校验失败。");
        }
    }
}
