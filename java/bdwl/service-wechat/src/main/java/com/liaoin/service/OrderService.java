package com.liaoin.service;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.Goods;
import com.liaoin.bean.Order;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {

    public String createOrderNo();

    /**
     * 创建订单
     * @return
     */
    public Order save(Order order);

    public List<Order> queryByWhere(Order order);

    /**
     * 余额支付
     */
    public OperateResult payWithBalance(WeiXinUser weiXinUser, String orderId);

    public OperateResult payWithWechat(WeiXinUser weiXinUser, String orderId,String uri);

    public Order findById(String id);

    public OperateResult delete(String id);

    public Page<Order> queryWithPage(int page, int size, OrderStatus orderStatus,String assetId,String beginDate,String endDate);

    /**
     * 退款操作
     */
    public void refund(String orderId);

    public Map<String,Double> queryTotalMoney(OrderStatus orderStatus , String beginDate, String endDate,String assetId);
}
