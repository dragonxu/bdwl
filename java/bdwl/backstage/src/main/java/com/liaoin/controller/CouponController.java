package com.liaoin.controller;

import com.liaoin.Enum.RoleStatus;
import com.liaoin.bean.Coupon;
import com.liaoin.bean.Role;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.CouponService;
import com.liaoin.service.OrderService;
import com.liaoin.service.WeiXinUserService;
import com.liaoin.util.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/Coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeiXinUserService weiXinUserService;

    /**
     * 列表
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @RequestParam(required = false) Integer p,
            ModelAndView modelAndView,
            HttpServletRequest request
    ){
        int page = 0;
        if(p != null){
            page = p - 1;
        }
        Page<Coupon> coupons = couponService.queryWithPage(page, Constant.PageConstant.ADMIN_PAGE_COUNT);

        PageResult<Coupon> pageResult = new PageResult<>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,coupons);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.setViewName("Coupon/index");
        return modelAndView;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelAndView){
        modelAndView.setViewName("Coupon/add");
        return modelAndView;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity post(
            @RequestParam(required = false) String id,
            @RequestParam float money,
            @RequestParam float enableStandard,
            @RequestParam String beginDate,
            @RequestParam String endDate
    )throws Exception{

        Coupon coupon = Coupon.builder()
                .id(id)
                .number(orderService.createOrderNo())
                .money(money)
                .enableStandard(enableStandard)
                .beginDate(DateFormat.dateToStamp(beginDate))
                .endDate(DateFormat.dateToStamp(endDate))
                .build();
        return ok(couponService.save(coupon));
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView update(
            @RequestParam String id,
            ModelAndView modelAndView
    ){
        Coupon coupon = couponService.findById(id);
        modelAndView.addObject("coupon",coupon);
        modelAndView.setViewName("Coupon/update");
        return modelAndView;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity delete(
            @RequestParam String id
    ) throws Exception{
        return ok(couponService.deleteById(id));
    }

    /**
     * 赠送用户
     */
    @RequestMapping(value = "/send",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity send(
            @RequestParam String id
    ){
        return ok(couponService.send(id));
    }

    /**
     * 赠送用户
     */
    @RequestMapping(value = "/sendToUser",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity sendToUser(
            @RequestParam String weiXinUserId,
            @RequestParam String couponId
    ){
        return ok(couponService.sendToUser(couponId,weiXinUserId));
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView users(
            @RequestParam(required = false) Integer p,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String couponId,
            HttpServletRequest request,
            ModelAndView modelAndView
    ){
        int page = 0;
        if(p != null){
            page = p - 1;
        }
        Page<WeiXinUser> weiXinUsers = weiXinUserService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT,keyword);
        PageResult<WeiXinUser> pageResult = new PageResult<WeiXinUser>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,weiXinUsers);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("couponId", couponId);
        modelAndView.setViewName("Coupon/users");
        return modelAndView;
    }
}
