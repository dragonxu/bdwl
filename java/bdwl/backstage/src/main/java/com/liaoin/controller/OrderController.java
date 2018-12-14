package com.liaoin.controller;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.AssetInfo;
import com.liaoin.bean.Goods;
import com.liaoin.bean.Order;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.constant.Constant;
import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.service.QrCodeContentRecordService;
import com.liaoin.message.PageResult;
import com.liaoin.service.AssetInfoService;
import com.liaoin.service.OrderService;
import com.liaoin.service.WeiXinUserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeiXinUserService weiXinUserService;

    @Autowired
    private QrCodeContentRecordService qrCodeContentRecordService;

    @Autowired
    private AssetInfoService assetInfoService;

    /**
     * 列表
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @RequestParam(required = false) Integer p,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) String assetId,
            @RequestParam(required = false) String beginDate,
            @RequestParam(required = false) String endDate,
            HttpServletRequest request,
            ModelAndView modelAndView
    ){
        int page = 0;
        if(p != null){
            page = p - 1;
        }
        Page<Order> orders = orderService.queryWithPage(page,Constant.PageConstant.ADMIN_PAGE_COUNT,orderStatus,assetId,beginDate,endDate);
//        for (Order order : orders.getContent()){
//            QrCodeContentRecord qrCodeContentRecord = qrCodeContentRecordService.findById(order.getQrCodeContentRecordId());
//            AssetInfo assetInfo = assetInfoService.findByAssetId(qrCodeContentRecord.getAssetId());
//            order.setAssetInfo(assetInfo);
//            orderService.save(order);
//        }
        PageResult<Order> pageResult = new PageResult<Order>();
        pageResult = pageResult.setPageUrl(request,page,Constant.PageConstant.ADMIN_PAGE_COUNT,orders);
        modelAndView.addObject("pageResult", pageResult);
        modelAndView.addObject("orderStatus", orderStatus);
        modelAndView.addObject("assetId", assetId);
        modelAndView.addObject("orderStatusLists", OrderStatus.values());

        //查询所有售货机
        List<AssetInfo> assetInfos = assetInfoService.findAll();
        modelAndView.addObject("assetInfos",assetInfos);
        //统计总金额
        Map<String,Double> total = orderService.queryTotalMoney(orderStatus,beginDate,endDate,assetId);
        if(total != null && total.get("totalPrice") != null){
            modelAndView.addObject("totalPrice",(double)Math.round(total.get("totalPrice")*100)/100);
            modelAndView.addObject("totalMoney",(double)Math.round(total.get("totalMoney")*100)/100);
            Double totalDiscount = total.get("totalPrice") - total.get("totalMoney");
            modelAndView.addObject("totalDiscount",(double)Math.round(totalDiscount*100)/100);
        }else{
            modelAndView.addObject("totalPrice",0);
            modelAndView.addObject("totalMoney",0);
            modelAndView.addObject("totalDiscount",0);
        }
        modelAndView.addObject("beginDate",beginDate);
        modelAndView.addObject("endDate",endDate);
        modelAndView.setViewName("Order/index");
        return modelAndView;
    }
}
