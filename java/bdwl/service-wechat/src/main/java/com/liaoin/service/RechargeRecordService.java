package com.liaoin.service;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.RechargeRecord;
import org.springframework.data.domain.Page;

public interface RechargeRecordService {

    public Page<RechargeRecord> queryWithPage(int page, int size, OrderStatus orderStatus,String beginDate,String endDate);

    public double queryTotalMoney(String beginDate,String endDate);

    public int queryCount(String beginDate,String endDate);
}
