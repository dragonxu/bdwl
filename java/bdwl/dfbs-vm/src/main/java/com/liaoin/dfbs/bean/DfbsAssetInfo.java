package com.liaoin.dfbs.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DfbsAssetInfo {

    @ApiModelProperty(value = "售货机编号")
    private String assetId;

    @ApiModelProperty(value = "厂家信息")
    private String vender;

    @ApiModelProperty(value = "区域名称")
    private String areaName ;

    @ApiModelProperty(value = "点位名称")
    private String siteName ;


}
