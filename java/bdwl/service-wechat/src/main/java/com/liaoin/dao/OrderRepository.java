package com.liaoin.dao;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.Coupon;
import com.liaoin.bean.Order;
import com.liaoin.bean.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Map;

public interface OrderRepository extends JpaRepository<Order,String>,JpaSpecificationExecutor<Order> {

    Order findByQrCodeContentRecordId(String qrCodeContentRecordId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order",nativeQuery=true)
    Map<String,Double> queryTotalMoney();

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1",nativeQuery=true)
    Map<String,Double> queryTotalMoneyWhereByOrderStatus(int orderStatus);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1 and update_time >= ?2 and update_time <= ?3",nativeQuery=true)
    Map<String,Double> queryTotalMoney(int orderStatus,Date beginDate,Date endDate);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1 and  update_time <= ?2",nativeQuery=true)
    Map<String,Double> queryTotalMoneyendDate(int orderStatus,Date endDate);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1 and  update_time >= ?2",nativeQuery=true)
    Map<String,Double> queryTotalMoneybeginDate(int orderStatus,Date beginDate);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where update_time >= ?1 and update_time <= ?2",nativeQuery=true)
    Map<String,Double> queryTotalMoney(Date beginDate,Date endDate);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where update_time <= ?1",nativeQuery=true)
    Map<String,Double> queryTotalMoneyendDate(Date endDate);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where update_time >= ?1",nativeQuery=true)
    Map<String,Double> queryTotalMoneybeginDate(Date beginDate);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where asset_info_id = ?1",nativeQuery=true)
    Map<String,Double> queryTotalMoney(String assetId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1 and asset_info_id = ?2",nativeQuery=true)
    Map<String,Double> queryTotalMoneyWhereByOrderStatus(int orderStatus,String assetId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1 and update_time >= ?2 and update_time <= ?3 and asset_info_id = ?4",nativeQuery=true)
    Map<String,Double> queryTotalMoney(int orderStatus,Date beginDate,Date endDate,String assetId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1 and  update_time <= ?2 and asset_info_id = ?3",nativeQuery=true)
    Map<String,Double> queryTotalMoneyendDate(int orderStatus,Date endDate,String assetId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where order_status = ?1 and  update_time >= ?2 and asset_info_id = ?3",nativeQuery=true)
    Map<String,Double> queryTotalMoneybeginDate(int orderStatus,Date beginDate,String assetId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where update_time >= ?1 and update_time <= ?2 and asset_info_id = ?3",nativeQuery=true)
    Map<String,Double> queryTotalMoney(Date beginDate,Date endDate,String assetId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where update_time <= ?1 and asset_info_id = ?2",nativeQuery=true)
    Map<String,Double> queryTotalMoneyendDate(Date endDate,String assetId);

    @Query( value = "select sum(price) as totalPrice,sum(total_money) as totalMoney from t_order where update_time >= ?1 and asset_info_id = ?2",nativeQuery=true)
    Map<String,Double> queryTotalMoneybeginDate(Date beginDate,String assetId);
}
