package com.liaoin.controller;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.RechargeRecord;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.RechargeRecordService;
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
@RequestMapping("RechargeRecord")
public class RechargeRecordController {

    @Autowired
    private RechargeRecordService rechargeRecordService;

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
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) String beginDate,
            @RequestParam(required = false) String endDate,
            HttpServletRequest request,
            ModelAndView modelAndView
    ){
        int page = 0;
        if(p != null){
            page = p - 1;
        }
        Page<RechargeRecord> rechargeRecords = rechargeRecordService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT,orderStatus,beginDate,endDate);
        PageResult<RechargeRecord> pageResult = new PageResult<RechargeRecord>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,rechargeRecords);

        double totalMoney = rechargeRecordService.queryTotalMoney(beginDate,endDate);
        int count = rechargeRecordService.queryCount(beginDate,endDate);
        modelAndView.addObject("totalMoney",(double)Math.round(totalMoney*100)/100);
        modelAndView.addObject("count",count);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("beginDate",beginDate);
        modelAndView.addObject("endDate",endDate);
        modelAndView.addObject("keyword", keyword);
        modelAndView.setViewName("RechargeRecord/index");
        return modelAndView;
    }
}
