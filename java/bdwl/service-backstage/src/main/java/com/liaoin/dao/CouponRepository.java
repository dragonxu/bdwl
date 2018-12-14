package com.liaoin.dao;

import com.liaoin.bean.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CouponRepository extends JpaRepository<Coupon,String>,JpaSpecificationExecutor<Coupon> {


}
