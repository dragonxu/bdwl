package com.liaoin.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 商品信息表
 */
@Entity
@Table(name = "t_goods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品图片")
    private String picture;

    @ApiModelProperty(value = "价格")
    private float price;

    @ApiModelProperty(value = "已销售")
    private int sales;

    @ApiModelProperty(value = "商品编号")
    private String number;

    @ApiModelProperty(value = "商品折扣")
    private float discount;

    @ApiModelProperty(value = "折扣开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date beginDate;

    @ApiModelProperty(value = "折扣结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date endDate;
}
