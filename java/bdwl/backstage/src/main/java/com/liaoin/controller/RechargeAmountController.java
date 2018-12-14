package com.liaoin.controller;

import com.liaoin.Enum.RoleStatus;
import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.Role;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.RechargeAmountService;
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
@RequestMapping("RechargeAmount")
public class RechargeAmountController {

    @Autowired
    private RechargeAmountService rechargeAmountService;
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
        Page<RechargeAmount> weiXinUsers = rechargeAmountService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT);
        PageResult<RechargeAmount> pageResult = new PageResult<RechargeAmount>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,weiXinUsers);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("keyword", keyword);
        modelAndView.setViewName("RechargeAmount/index");
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
        modelAndView.setViewName("RechargeAmount/add");
        return modelAndView;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity post(
            @RequestParam(required = false) String id,
            @RequestParam(defaultValue = "0") Float money,
            @RequestParam(defaultValue = "0") Float discount
    ){
        RechargeAmount rechargeAmount = RechargeAmount.builder()
                .id(id)
                .money(money)
                .discount(discount)
                .build();
        return ResponseEntity.ok(rechargeAmountService.save(rechargeAmount));
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
        RechargeAmount rechargeAmount = rechargeAmountService.findById(id);
        modelAndView.addObject("rechargeAmount",rechargeAmount);
        modelAndView.setViewName("RechargeAmount/update");
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
        return ok(rechargeAmountService.deleteById(id));
    }
}
