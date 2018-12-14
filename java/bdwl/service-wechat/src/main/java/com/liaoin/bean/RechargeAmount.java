package com.liaoin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 充值金额
 */
@Entity
@Table(name = "t_recharge_amount")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeAmount {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "充值金额")
    private float money;

    @ApiModelProperty(value = "赠送金额")
    private float discount;
}
