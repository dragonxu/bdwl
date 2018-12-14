package com.liaoin.dfbs.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DfbsGoods {

    private String typeName;

    private String number;

    private String name;

    private String fullName;

    private String packingForm;

    private String manufacturer;

    private String price;

    private String imgCdnPath;
}
