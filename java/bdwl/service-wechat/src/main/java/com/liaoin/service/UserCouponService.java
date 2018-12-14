package com.liaoin.service;

import com.liaoin.Enum.CouponStatus;
import com.liaoin.bean.Order;
import com.liaoin.bean.UserCoupon;

import java.util.Date;
import java.util.List;

public interface UserCouponService {

    public List<UserCoupon> findByWhere(String weiXinUserId, CouponStatus couponStatus,Date endDate,Float money);

    public UserCoupon findById(String id);

    public UserCoupon save(UserCoupon userCoupon);
}
