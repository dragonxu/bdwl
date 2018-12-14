package com.liaoin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/6.
 * 微信用户信息
 */
@Entity
@Table(name = "t_weixin_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeiXinUser {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;
    private String openid;
    private String nickname;
    private String sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String refresh_token;
    private String access_token;
    private String expire_in;

    @ApiModelProperty(value = "账户余额")
    private float balance = 0;

    @ApiModelProperty(value = "添加时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date addTime;

}
