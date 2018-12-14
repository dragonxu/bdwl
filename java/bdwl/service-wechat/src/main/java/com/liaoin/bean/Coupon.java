package com.liaoin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 优惠券
 */
@Entity
@Table(name = "t_coupon")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "优惠券号")
    private String number;

    @ApiModelProperty(value = "优惠金额")
    private float money;

    @ApiModelProperty(value = "最小使用金额")
    private float enableStandard;

    @ApiModelProperty(value = "开始时间")
    @Temporal(TemporalType.DATE)
    private Date beginDate;

    @ApiModelProperty(value = "结束时间")
    @Temporal(TemporalType.DATE)
    private Date endDate;

}
