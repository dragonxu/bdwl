package com.liaoin.controller;

import com.liaoin.annotation.LoadGoods;
import com.liaoin.annotation.LoadQrCodeContentRecord;
import com.liaoin.bean.Goods;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.service.QrCodeContentRecordService;
import com.liaoin.service.GoodsService;
import com.liaoin.service.WeiXinUserService;
import com.liaoin.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("Goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private WeiXinUserService weiXinUserService;

    @Autowired
    private QrCodeContentRecordService qrCodeContentRecordService;

    /**
     * 商品详情页
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @RequestParam String qrCodeContentRecordId,
            @RequestParam(required = false) String code,
            ModelAndView modelAndView,
            HttpServletRequest request
    ){
        QrCodeContentRecord qrCodeContentRecord = qrCodeContentRecordService.findById(qrCodeContentRecordId);
        Goods goods = goodsService.findByNumber(qrCodeContentRecord.getProductNum());
        request.getSession().setAttribute("goodsId",goods.getId());
        request.getSession().setAttribute("qrCodeContentRecordId",qrCodeContentRecord.getId());

        if(StringUtils.isNotNull(code)){
            weiXinUserService.authorization(code,request);
        }

        modelAndView.addObject("goods",goods);
        float price = goods.getPrice();
        if(goods.getDiscount() > 0){
            long now = new Date().getTime();
            if(goods.getBeginDate().getTime() <= now && now <= goods.getEndDate().getTime()){
                price = price * goods.getDiscount();
            }
        }
        modelAndView.addObject("price",price);
        modelAndView.addObject("discount",(double)Math.round(goods.getDiscount()*10*100)/100);
        modelAndView.setViewName("Goods/info");
        return modelAndView;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public void index(
            HttpServletRequest request){
        WeiXinUser weiXinUser = weiXinUserService.findById("2c9b8091661acf6c01661ad0f2f10000");
        request.getSession().setAttribute("weiXinUser",weiXinUser);

    }
}
