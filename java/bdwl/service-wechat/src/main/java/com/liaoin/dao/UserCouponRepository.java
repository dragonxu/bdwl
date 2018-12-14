package com.liaoin.dao;

import com.liaoin.bean.Coupon;
import com.liaoin.bean.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon,String>,JpaSpecificationExecutor<UserCoupon> {

    void deleteByCoupon(Coupon coupon);
}
