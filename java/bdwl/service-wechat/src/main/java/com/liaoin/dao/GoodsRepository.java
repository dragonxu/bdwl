package com.liaoin.dao;

import com.liaoin.bean.Coupon;
import com.liaoin.bean.Goods;
import com.liaoin.bean.RechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsRepository extends JpaRepository<Goods,String>,JpaSpecificationExecutor<Goods> {

    Goods findByNumber(String number);
}
