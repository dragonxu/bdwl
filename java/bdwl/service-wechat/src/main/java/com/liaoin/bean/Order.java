package com.liaoin.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liaoin.Enum.OrderStatus;
import com.liaoin.Enum.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "商品")
    @OneToOne(targetEntity = Goods.class)
    private Goods goods;

    @ApiModelProperty(value = "单价")
    private float price;

    @ApiModelProperty(value = "数量")
    private int count;

    @ApiModelProperty(value = "总价")
    private float totalMoney;

    @ApiModelProperty(value = "订单状态")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "支付方式")
    private PayType payType;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "用户优惠券")
    private String userCouponId;

    @ApiModelProperty(value = "优惠金额")
    private float discount;

    @ApiModelProperty(value = "微信用户")
    @OneToOne(targetEntity = WeiXinUser.class)
    private WeiXinUser weiXinUser;

    @ApiModelProperty(value = "微信交易号")
    private String transactionId;

    @ApiModelProperty(value = "售货机通知记录")
    private String qrCodeContentRecordId;

    @ApiModelProperty(value = "售货机")
    @OneToOne(targetEntity = AssetInfo.class)
    private AssetInfo assetInfo;
}
