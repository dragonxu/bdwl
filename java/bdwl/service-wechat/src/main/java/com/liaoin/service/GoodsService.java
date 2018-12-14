package com.liaoin.service;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.Goods;
import com.liaoin.bean.Order;
import com.liaoin.bean.RechargeRecord;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface GoodsService {

    public Goods findById(String id);

    public Goods findByNumber(String number);

    /**
     * 同步售货机商品
     */
    public OperateResult synchronization();

    public Goods save(Goods goods);

    public Page<Goods> queryWithPage(int page, int size, String keywords);

    public OperateResult discount(String id, Float price, Float discount, String beginDate,String endDate);

}
