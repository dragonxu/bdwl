package com.liaoin.controller;

import com.liaoin.Enum.CouponStatus;
import com.liaoin.Enum.OrderStatus;
import com.liaoin.Enum.PayType;
import com.liaoin.annotation.LoadGoods;
import com.liaoin.annotation.LoadQrCodeContentRecord;
import com.liaoin.annotation.LoadWeiXinUser;
import com.liaoin.bean.*;
import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.service.QrCodeContentRecordService;
import com.liaoin.service.AssetInfoService;
import com.liaoin.service.GoodsService;
import com.liaoin.service.OrderService;
import com.liaoin.service.UserCouponService;
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
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("Pay")
public class PayController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private QrCodeContentRecordService qrCodeContentRecordService;

    @Autowired
    private AssetInfoService assetInfoService;

    /**
     * 支付
     */
    @RequestMapping(value = "/pay",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView pay(
            @LoadWeiXinUser WeiXinUser weiXinUser,
            @LoadGoods String goodsId,
            @RequestParam(required = false,defaultValue = "1") Integer number,
            @RequestParam(required = false) String userCouponId,
            @LoadQrCodeContentRecord String qrCodeContentRecordId,
            ModelAndView modelAndView
    ){
        Goods goods = goodsService.findById(goodsId);
        modelAndView.addObject("goods",goods);
        float price = goods.getPrice();
        float discount = 0;
        if(goods.getDiscount() > 0){
            long now = new Date().getTime();
            if(goods.getBeginDate().getTime() <= now && now <= goods.getEndDate().getTime()){
                price = price * goods.getDiscount();
                discount = (float)Math.round((goods.getPrice() - price)*100)/100;
            }
        }
        //生成订单信息
        QrCodeContentRecord qrCodeContentRecord = qrCodeContentRecordService.findById(qrCodeContentRecordId);
        AssetInfo assetInfo = assetInfoService.findByAssetId(qrCodeContentRecord.getAssetId());
        Order order = Order.builder()
                .orderNo(orderService.createOrderNo())
                .goods(goods)
                .price(goods.getPrice())
                .count(number)
                .totalMoney(price * number)
                .orderStatus(OrderStatus.WAIT_FOR_PAY)
                .payType(PayType.PAY_WITH_WECHAT)
                .addTime(new Date())
                .discount(discount)
                .updateTime(new Date())
                .weiXinUser(weiXinUser)
                .qrCodeContentRecordId(qrCodeContentRecord.getId())
                .assetInfo(assetInfo)
                .build();
        if(StringUtils.isNotNull(userCouponId)){
            UserCoupon userCoupon = userCouponService.findById(userCouponId);
            order.setUserCouponId(userCouponId);
            order.setDiscount(userCoupon.getCoupon().getMoney());
            order.setTotalMoney(order.getTotalMoney() - order.getDiscount());
        }
        order = orderService.save(order);
        modelAndView.addObject("order",order);
        modelAndView.setViewName("Pay/pay");
        return modelAndView;
    }

    /**
     * 选择优惠券
     */
    @RequestMapping(value = "/choose",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView choose(
            @LoadWeiXinUser WeiXinUser weiXinUser,
            @RequestParam(required = false)Float money,
            ModelAndView modelAndView
    ){
        //获取用户未使用的优惠券
        List<UserCoupon> userCoupons = userCouponService.findByWhere(weiXinUser.getId(),CouponStatus.WAIT,new Date(),money);
        modelAndView.addObject("userCoupons",userCoupons);
        modelAndView.setViewName("Pay/choose");
        return modelAndView;
    }

    /**
     * 成功
     */
    @RequestMapping(value = "/ok",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView success(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("Pay/ok");
        return modelAndView;
    }

    /**
     * 失败
     */
    @RequestMapping(value = "/fail",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView fail(
            ModelAndView modelAndView
    ){
        modelAndView.setViewName("Pay/fail");
        return modelAndView;
    }

    /**
     * 余额支付
     */
    @RequestMapping(value = "/payWithBalance",method = RequestMethod.POST)
    public ResponseEntity payWithBalance (
            @LoadWeiXinUser WeiXinUser weiXinUser,

            @RequestParam String orderId
    ){
        return ok(orderService.payWithBalance(weiXinUser,orderId));
    }

    /**
     * 微信支付
     */
    @RequestMapping(value = "/payWithWechat",method = RequestMethod.POST)
    public ResponseEntity payWithWechat (
            @LoadWeiXinUser WeiXinUser weiXinUser,
            @RequestParam String orderId,
            @LoadQrCodeContentRecord String qrCodeContentRecordId,
            HttpServletRequest request
    ){
        return ok(orderService.payWithWechat(weiXinUser,orderId,request.getRequestURL().toString()));
    }
}
