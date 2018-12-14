package com.liaoin.bean;

import com.liaoin.Enum.CouponStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户优惠券
 */
@Entity
@Table(name = "t_user_coupon")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "优惠券")
    @OneToOne(targetEntity = Coupon.class)
    private Coupon coupon;

    @ApiModelProperty(value = "微信用户")
    private String weiXinUserId;

    @ApiModelProperty(value = "状态")
    private CouponStatus couponStatus;
}
