package com.liaoin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 出货结果通知
 */
@Entity
@Table(name = "t_shipping_notice")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingNotice {


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "身份标识")
    private String clientId;

    @ApiModelProperty(value = "机器编号")
    private String assetId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "出货状态 0 出货成功 2，3，4，5 为出货失败")
    private String deliverStatus;

    @ApiModelProperty(value = "签名的随机数")
    private String nonce_str;

    @ApiModelProperty(value = "签名")
    private String sign;
}
