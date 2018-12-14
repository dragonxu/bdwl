package com.liaoin.bean;

import com.liaoin.Enum.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 充值记录
 */
@Entity
@Table(name = "t_recharge_record")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "充值用户")
    @OneToOne(targetEntity = WeiXinUser.class)
    private WeiXinUser weiXinUser;

    @ApiModelProperty(value = "充值金额")
    private float money;

    @ApiModelProperty(value = "赠送金额")
    private float discount;

    @ApiModelProperty(value = "充值时间")
    private Date addTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    @ApiModelProperty(value = "充值状态")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "微信交易号")
    private String transactionId;
}
