package com.liaoin.controller;

import com.liaoin.annotation.LoadAdmin;
import com.liaoin.bean.Admin;
import com.liaoin.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(
            ModelAndView modelAndView,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception{
        Admin admin = (Admin)request.getSession().getAttribute("admin");
        if(admin != null){
            response.sendRedirect(request.getContextPath()+"/Main/index");
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }


}
