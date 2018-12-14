package com.liaoin.dao;

import com.liaoin.bean.RechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface RechargeRecordRepository extends JpaRepository<RechargeRecord,String>,JpaSpecificationExecutor<RechargeRecord> {

    @Query( value = "select sum(money) as totalMoney from t_recharge_record where order_status = 1",nativeQuery=true)
    double querySum();
    @Query( value = "select sum(money) as totalMoney from t_recharge_record where order_status = 1 and update_time >= ?1 and update_time <= ?2",nativeQuery=true)
    double querySum(Date beginDate, Date endDate);

    @Query( value = "select sum(money) as totalMoney from t_recharge_record where order_status = 1 and update_time <= ?1",nativeQuery=true)
    double querySumendDate(Date endDate);

    @Query( value = "select sum(money) as totalMoney from t_recharge_record where order_status = 1 and update_time >= ?1",nativeQuery=true)
    double querySumbeginDate(Date beginDate);

    @Query( value = "select count(*) as number from t_recharge_record where order_status = 1",nativeQuery=true)
    int queryCount();
    @Query( value = "select count(*) as number from t_recharge_record where order_status = 1 and update_time >= ?1 and update_time <= ?2",nativeQuery=true)
    int queryCount(Date beginDate, Date endDate);

    @Query( value = "select count(*) as number from t_recharge_record where order_status = 1 and update_time <= ?1",nativeQuery=true)
    int queryCountendDate(Date endDate);

    @Query( value = "select count(*) as number from t_recharge_record where order_status = 1 and update_time >= ?1",nativeQuery=true)
    int queryCountbeginDate(Date beginDate);
}
