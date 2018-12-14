package com.liaoin.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_asset")
public class AssetInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "售货机编号")
    private String assetId;

    @ApiModelProperty(value = "厂家信息")
    private String vender;

    @ApiModelProperty(value = "区域名称")
    private String areaName ;

    @ApiModelProperty(value = "点位名称")
    private String siteName ;


}
