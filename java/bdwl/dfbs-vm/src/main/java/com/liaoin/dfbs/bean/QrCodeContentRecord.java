package com.liaoin.dfbs.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 获取二维码内容接口记录
 */
@Entity
@Table(name = "t_qr_code_content_record")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeContentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "身份标识，clientId 为平台为用户分配唯一身份标识")
    private String clientId;

    @ApiModelProperty(value = "机器编号")
    private String assetId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品价格")
    private String price;

    @ApiModelProperty(value = "商品编号")
    private String productNum;

    @ApiModelProperty(value = "支付成功回调地址 POST 请求，当用户支付成功后需通过此 地址通知 ")
    private String notify_url;

    @ApiModelProperty(value = "订单编号，对于每个订单两方系统需使用此参数对应 ")
    private String orderNo;

    @ApiModelProperty(value = "签名的随机数 ")
    private String nonce_str;

    @ApiModelProperty(value = "签名")
    private String sign;

}
