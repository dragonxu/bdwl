package com.liaoin.controller;

import com.liaoin.bean.Role;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.WeiXinUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("WeiXinUser")
public class WeiXinUserController {

    @Autowired
    private WeiXinUserService weiXinUserService;

    /**
     * 列表
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
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
        modelAndView.setViewName("WeiXinUser/index");
        return modelAndView;
    }
}
