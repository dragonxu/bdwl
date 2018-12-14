package com.liaoin.controller;

import com.liaoin.annotation.LoadWeiXinUser;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.service.RechargeAmountService;
import com.liaoin.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("Recharge")
public class RechargeController {

    @Autowired
    private RechargeService rechargeService;

    @RequestMapping(value = "/recharge",method = RequestMethod.POST)
    public ResponseEntity recharge (
            @LoadWeiXinUser WeiXinUser weiXinUser,
            @RequestParam String rechargeAmountId,
            HttpServletRequest request
    ){
        return ok(rechargeService.recharge(weiXinUser,rechargeAmountId,request.getRequestURL().toString()));
    }
}
