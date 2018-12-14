package com.liaoin.controller;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.annotation.LoadWeiXinUser;
import com.liaoin.bean.Order;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 我的订单列表
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(
            @LoadWeiXinUser WeiXinUser weiXinUser,
            @RequestParam(required = false) OrderStatus orderStatus,
            ModelAndView modelAndView
    ){
        Order order = Order.builder()
                .weiXinUser(weiXinUser)
                .orderStatus(orderStatus)
                .build();
        List<Order> orders = orderService.queryByWhere(order);
        modelAndView.addObject("orders",orders);
        modelAndView.addObject("orderStatus",orderStatus);
        modelAndView.setViewName("Order/index");
        return modelAndView;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResponseEntity delete (
            @RequestParam String orderId
    ){
        return ok(orderService.delete(orderId));
    }
}
