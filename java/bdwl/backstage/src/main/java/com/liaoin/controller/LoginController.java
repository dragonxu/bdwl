package com.liaoin.controller;

import com.liaoin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public ResponseEntity login (
            @RequestParam String phone,
            @RequestParam String password,
            HttpServletRequest request
    ){
        return ok(adminService.login(request,phone,password));
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @ResponseBody
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().removeAttribute("admin");
        response.sendRedirect(request.getContextPath());
    }

}
