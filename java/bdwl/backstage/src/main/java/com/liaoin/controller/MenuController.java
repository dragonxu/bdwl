package com.liaoin.controller;

import com.liaoin.Enum.Status;
import com.liaoin.bean.Menu;
import com.liaoin.service.MenuService;
import com.liaoin.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.liaoin.message.OperateResult.success;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by Administrator on 2017/7/22 0022.
 */
@Controller
@RequestMapping("Menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 列表
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("menuString", menuService.queryMenus(null, request));
        modelAndView.setViewName("Menu/index");
        return modelAndView;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView add(
            @RequestParam(required = false) String menuId
    ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("menuString", menuService.queryMenus(null, 0, menuId));
        modelAndView.setViewName("Menu/add");
        return modelAndView;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity post(
            @RequestParam(required = false) String id,
            @RequestParam(required = false, defaultValue = "0") String parentId,
            @RequestParam String name,
            @RequestParam String app,
            @RequestParam String model,
            @RequestParam String action,
            @RequestParam String params,
            @RequestParam String icon,
            @RequestParam String remark,
            @RequestParam Status status
    ){
        Menu menu = Menu.builder().id(id).parentId(parentId).name(name).app(app).model(model).action(action).params(params).icon(icon).remark(remark).status(status).build();
        return ok(menuService.save(menu));
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView update(
            @RequestParam String id
    ){
        Menu menu = menuService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("menu",menu);
        if(StringUtils.isNotNull(menu.getParentId())){
            modelAndView.addObject("menuString", menuService.queryMenus(null, 0, menu.getParentId()));
        }else{
            modelAndView.addObject("menuString", menuService.queryMenus(null, 0, null));
        }
        modelAndView.setViewName("Menu/update");
        return modelAndView;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity delete(@RequestParam String id) throws Exception{
        return ok(menuService.delete(id));
    }

    /**
     * 排序
     */
    @RequestMapping(value = "/listorders",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity listorders(
            HttpServletRequest request
    ){
        String[] ids= request.getParameterValues("ids[]");
        for (String id : ids){
            Menu menu = menuService.findById(id);
            String listorder = request.getParameter("listorders"+id);
            menu.setListorder(Integer.parseInt(listorder));
            menuService.save(menu);
        }
        return ok(success());
    }





}
