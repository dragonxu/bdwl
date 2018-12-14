package com.liaoin.controller;

import com.liaoin.bean.Coupon;
import com.liaoin.bean.PushUser;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.PushUserService;
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

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("PushUser")
public class PushUserController {

    @Autowired
    private PushUserService pushUserService;

    @Autowired
    private WeiXinUserService weiXinUserService;

    /**
     * 列表
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @RequestParam(required = false) Integer p,
            HttpServletRequest request,
            ModelAndView modelAndView
    ){
        int page = 0;
        if(p != null){
            page = p - 1;
        }
        Page<PushUser> pushUsers = pushUserService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT);
        PageResult<PushUser> pageResult = new PageResult<PushUser>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,pushUsers);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.setViewName("PushUser/index");
        return modelAndView;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView users(
            @RequestParam(required = false) Integer p,
            @RequestParam(required = false) String keyword,
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
        modelAndView.setViewName("PushUser/users");
        return modelAndView;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity delete(
            @RequestParam String id
    ){
        return ok(pushUserService.deleteById(id));
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity add(
            @RequestParam(required = false) String id
    ){
        return ok(pushUserService.add(id));
    }
}
