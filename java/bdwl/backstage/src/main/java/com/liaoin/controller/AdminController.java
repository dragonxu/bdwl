package com.liaoin.controller;

import com.liaoin.annotation.LoadAdmin;
import com.liaoin.bean.Admin;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.AdminService;
import com.liaoin.service.RoleService;
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

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;


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
        Page<Admin> admins = adminService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT);
        for (Admin admin : admins.getContent()){
            admin.setAdminRoles(roleService.findByAdminId(admin.getId()));
        }
        PageResult<Admin> pageResult = new PageResult<Admin>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,admins);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.setViewName("Admin/index");
        return modelAndView;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView add(
            ModelAndView modelAndView
    ){
        //查询用户角色
        modelAndView.addObject("roles",roleService.findAllRoleLists());
        modelAndView.setViewName("Admin/add");
        return modelAndView;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity post(
            HttpServletRequest request
    ){
        String id = request.getParameter("id");//id
        String username = request.getParameter("username");//用户名
        String password = request.getParameter("password");//密码
        String roleIds[] = request.getParameterValues("roleId");//用户角色Id
        Admin admin = Admin.builder().id(id).username(username).password(password).build();
        return ok(adminService.save(admin,roleIds));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ModelAndView update(
            @RequestParam String id,
            ModelAndView modelAndView
    ){
        Admin admin = adminService.findById(id);
        modelAndView.addObject("admin",admin);
        //查询用户角色
        modelAndView.addObject("roles",roleService.findAllRoleLists(admin.getId()));
        modelAndView.setViewName("Admin/update");
        return modelAndView;
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/password",method = RequestMethod.GET)
    public ModelAndView password(
            @LoadAdmin Admin admin,
            ModelAndView modelAndView
    ){
        modelAndView.addObject("admin",admin);
        //查询用户角色
        modelAndView.addObject("roles",roleService.findAllRoleLists(admin.getId()));
        modelAndView.setViewName("Admin/password");
        return modelAndView;
    }

    /**
     * 修改密码提交
     */
    @RequestMapping(value = "/postPassword",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity postPassword(
            @RequestParam String oldPassword,
            @RequestParam String password,
            @RequestParam String repassword,
            @LoadAdmin Admin admin
    ){
        admin = adminService.findById(admin.getId());
        return ok(adminService.updatePassword(admin,oldPassword,password,repassword));
    }
}
