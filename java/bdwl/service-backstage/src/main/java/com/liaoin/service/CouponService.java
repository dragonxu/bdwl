package com.liaoin.service;

import com.liaoin.bean.Coupon;
import com.liaoin.bean.Menu;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

public interface CouponService {

    Page<Coupon> queryWithPage(int page, int adminPageCount);

    public OperateResult save(Coupon coupon);

    public Coupon findById(String id);

    public OperateResult deleteById(String id);

    public OperateResult send(String id);

    public OperateResult sendToUser(String couponId, String weiXinUserId);


}

