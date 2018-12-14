package com.liaoin.controller;

import com.liaoin.Enum.RoleStatus;
import com.liaoin.bean.Role;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.MenuService;
import com.liaoin.service.impl.RoleServiceImpl;
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
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Administrator on 2017/8/4.
 */
@Controller
@RequestMapping("Role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private MenuService menuService;
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
        Page<Role> educations = roleService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT);
        PageResult<Role> pageResult = new PageResult<Role>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,educations);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.setViewName("Role/index");
        return modelAndView;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView add(
            ModelAndView modelAndView
    ){
        modelAndView.addObject("roleStatusLists",RoleStatus.values());
        modelAndView.setViewName("Role/add");
        return modelAndView;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity post(
            @RequestParam(required = false) String id,
            @RequestParam String name,
            @RequestParam String introduce,
            @RequestParam RoleStatus roleStatus
    ){
        return ResponseEntity.ok(roleService.save(id,name,roleStatus,introduce));
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
        Role role = roleService.findById(id);
        modelAndView.addObject("roleStatusLists",RoleStatus.values());
        modelAndView.addObject("role",role);
        modelAndView.setViewName("Role/update");
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
        return ok(roleService.deleteById(id));
    }

    /**
     * 获取权限列表
     */
    @RequestMapping(value = "/power",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @RequestParam String roleId,
            ModelAndView modelAndView
    ){
        modelAndView.addObject("roleId",roleId);
        modelAndView.addObject("menuString", menuService.queryMenus("0",roleId,0));
        modelAndView.setViewName("Role/power");
        return modelAndView;
    }

    /**
     * 权限信息提交
     */
    @RequestMapping(value = "/power_post",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity power_post(
            HttpServletRequest request
    ){
        String roleId = request.getParameter("roleId");
        String menuIds[] = request.getParameterValues("menuId");//菜单ID
        return ok(roleService.setRolePowers(roleId,menuIds));
    }

}
