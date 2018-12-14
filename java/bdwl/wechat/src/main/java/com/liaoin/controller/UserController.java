package com.liaoin.controller;

import com.liaoin.annotation.LoadWeiXinUser;
import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.service.RechargeAmountService;
import com.liaoin.service.WeiXinUserService;
import com.liaoin.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("User")
public class UserController {

    @Autowired
    private WeiXinUserService weiXinUserService;

    @Autowired
    private RechargeAmountService rechargeAmountService;

    /**
     * 个人中心
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @LoadWeiXinUser WeiXinUser weiXinUser,
            ModelAndView modelAndView
    ){
        weiXinUser = weiXinUserService.findById(weiXinUser.getId());
        weiXinUser.setNickname(Base64.decryptBASE64(weiXinUser.getNickname()));
        modelAndView.addObject("weiXinUser",weiXinUser);
        modelAndView.setViewName("User/index");
        return modelAndView;
    }

    /**
     * 余额
     */
    @RequestMapping(value = "/balance",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView balance(
            @LoadWeiXinUser WeiXinUser weiXinUser,
            ModelAndView modelAndView
    ){
        modelAndView.addObject("weiXinUser",weiXinUserService.findByOpenid(weiXinUser.getOpenid()));

        //查询充值金额
        List<RechargeAmount> rechargeAmounts = rechargeAmountService.findAll();
        modelAndView.addObject("rechargeAmounts",rechargeAmounts);
        modelAndView.setViewName("User/balance");
        return modelAndView;
    }
}
