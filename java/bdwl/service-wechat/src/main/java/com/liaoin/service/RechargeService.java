package com.liaoin.service;

import com.liaoin.bean.RechargeRecord;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.message.OperateResult;

public interface RechargeService {

    public OperateResult recharge(WeiXinUser weiXinUser, String rechargeAmountId, String uri);

    public RechargeRecord findById(String id);

    public OperateResult save(RechargeRecord rechargeRecord);
}
