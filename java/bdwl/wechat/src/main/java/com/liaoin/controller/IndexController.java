package com.liaoin.controller;

import com.google.gson.Gson;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.constant.WechatConstant;
import com.liaoin.service.WeiXinUserService;
import com.liaoin.util.Base64;
import com.liaoin.wechat.AccessToken;
import com.liaoin.wechat.UserInfo;
import com.liaoin.wechat.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private WeiXinUserService weiXinUserService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public void index(
            @RequestParam String code,
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{
        weiXinUserService.authorization(code,request);
        response.sendRedirect(request.getContextPath()+"/User/index");
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public void home(
            HttpServletRequest request,
            HttpServletResponse response
    )throws Exception{
        WeiXinUser weiXinUser = weiXinUserService.findByOpenid("o--Z95kr4Jvkh1exKzgE_dhz_flM");
        request.getSession().setAttribute("weiXinUser",weiXinUser);
        response.sendRedirect(request.getContextPath()+"/Goods/info");
    }
}
