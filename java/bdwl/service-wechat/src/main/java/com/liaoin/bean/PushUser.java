package com.liaoin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_push_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushUser {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "微信用户ID")
    private String weiXinUserId;

    @ApiModelProperty(value = "微信用户openId")
    private String openId;

    @ApiModelProperty(value = "微信用户名")
    private String nickname;
}
