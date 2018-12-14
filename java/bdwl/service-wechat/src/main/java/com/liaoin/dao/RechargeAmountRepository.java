package com.liaoin.dao;

import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.WeiXinUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechargeAmountRepository extends JpaRepository<RechargeAmount,String> {

}
