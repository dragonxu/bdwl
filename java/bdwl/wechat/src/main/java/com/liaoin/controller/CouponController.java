package com.liaoin.controller;

import com.liaoin.Enum.CouponStatus;
import com.liaoin.annotation.LoadWeiXinUser;
import com.liaoin.bean.UserCoupon;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("Coupon")
public class CouponController {

    @Autowired
    private UserCouponService userCouponService;
    /**
     * 优惠券列表
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @LoadWeiXinUser WeiXinUser weiXinUser,
            @RequestParam(required = false) CouponStatus couponStatus,
            ModelAndView modelAndView
    ){
        List<UserCoupon> userCoupons = userCouponService.findByWhere(weiXinUser.getId(),couponStatus,null,null);
        modelAndView.addObject("userCoupons",userCoupons);
        modelAndView.addObject("couponStatus",couponStatus);
        modelAndView.setViewName("Coupon/index");
        return modelAndView;
    }
}
