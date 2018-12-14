package com.liaoin.controller;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.AssetInfo;
import com.liaoin.bean.Goods;
import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.RechargeRecord;
import com.liaoin.constant.Constant;
import com.liaoin.message.PageResult;
import com.liaoin.service.AssetInfoService;
import com.liaoin.service.GoodsService;
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
@RequestMapping("Goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AssetInfoService assetInfoService;
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
        Page<Goods> goods = goodsService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT,keyword);
        PageResult<Goods> pageResult = new PageResult<Goods>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,goods);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("keyword", keyword);
        modelAndView.setViewName("Goods/index");
        return modelAndView;
    }

    /**
     * 商品同步
     */
    @RequestMapping(value = "/synchronization",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity synchronization(){
        assetInfoService.synchronization();
        return ok(goodsService.synchronization());
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
        Goods goods = goodsService.findById(id);
        modelAndView.addObject("goods",goods);
        modelAndView.setViewName("Goods/update");
        return modelAndView;
    }

    /**
     * 提交数据
     */
    @RequestMapping(value = "/post",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity post(
            @RequestParam(required = false) String id,
            @RequestParam Float price,
            @RequestParam(defaultValue = "1") Float discount,
            @RequestParam(required = false) String beginDate,
            @RequestParam(required = false) String endDate
    ){
        return ResponseEntity.ok(goodsService.discount(id,price,discount,beginDate,endDate));
    }

}
