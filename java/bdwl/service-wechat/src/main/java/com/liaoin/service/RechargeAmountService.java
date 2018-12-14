package com.liaoin.service;

import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RechargeAmountService {

    public List<RechargeAmount> findAll();

    public Page<RechargeAmount> queryWithPage(int page, int size);

    public OperateResult save(RechargeAmount rechargeAmount);

    public RechargeAmount findById(String id);

    public OperateResult deleteById(String id);
}
