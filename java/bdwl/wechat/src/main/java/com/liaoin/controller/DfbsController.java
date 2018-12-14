package com.liaoin.controller;

import com.google.gson.Gson;
import com.liaoin.bean.ShippingNotice;
import com.liaoin.constant.WechatConstant;
import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.constant.DfbsConstant;
import com.liaoin.dfbs.message.DfbsResult;
import com.liaoin.dfbs.service.QrCodeContentRecordService;
import com.liaoin.service.GoodsService;
import com.liaoin.service.ShippingNoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URLEncoder;

import static org.springframework.http.ResponseEntity.ok;

/**
 * 售货机会员支付接口
 */
@Controller
@RequestMapping("Dfbs")
@Slf4j
public class DfbsController {

    @Autowired
    private QrCodeContentRecordService qrCodeContentRecordService;

    @Autowired
    private ShippingNoticeService shippingNoticeService;

    /**
     * 获取二维码内容接口
     * @return
     */
    @RequestMapping(value = "/queryQrCodeContent",method = RequestMethod.GET)
    public ResponseEntity queryQrCodeContent(
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) String assetId,
            @RequestParam(required = false) String goodsName,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String productNum,
            @RequestParam(required = false) String notify_url,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String nonce_str,
            @RequestParam(required = false) String sign,
            HttpServletRequest request
    ){
        QrCodeContentRecord qrCodeContentRecord = QrCodeContentRecord.builder()
                .clientId(clientId)
                .assetId(assetId)
                .goodsName(goodsName)
                .price(price)
                .productNum(productNum)
                .notify_url(notify_url)
                .orderNo(orderNo)
                .nonce_str(nonce_str)
                .sign(sign)
                .build();
        qrCodeContentRecord = qrCodeContentRecordService.save(qrCodeContentRecord);
        log.info("获取二维码内容接口：",new Gson().toJson(request.getParameterMap()));
        return ok(DfbsResult.success(DfbsConstant.Result.SUCCESS,WechatConstant.HTTP_URL+"/Dfbs/goods?qrCodeContentRecordId="+qrCodeContentRecord.getId()));
    }

    @RequestMapping(value = "/goods",method = RequestMethod.GET)
    public void goods(
            @RequestParam String qrCodeContentRecordId,
            HttpServletResponse response
    )throws Exception{
        String url = URLEncoder.encode(WechatConstant.HTTP_URL + "/Goods/info?qrCodeContentRecordId="+qrCodeContentRecordId);
        String goods_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WechatConstant.APPID +"&redirect_uri="+url+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";//微信授权链接
        response.sendRedirect(goods_url);
    }

    /**
     * 出货通知接口
     */
    @RequestMapping(value = "/shippingNotice",method = RequestMethod.POST)
    public ResponseEntity shippingNotice(
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) String assetId,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String deliverStatus,
            @RequestParam(required = false) String nonce_str,
            @RequestParam(required = false) String sign,
            HttpServletRequest request
    ){
        ShippingNotice shippingNotice = ShippingNotice.builder()
                .clientId(clientId)
                .assetId(assetId)
                .orderNo(orderNo)
                .deliverStatus(deliverStatus)
                .nonce_str(nonce_str)
                .sign(sign)
                .build();
        shippingNoticeService.shippingNotice(shippingNotice);
        log.info("出货通知接口：",new Gson().toJson(request.getParameterMap()));
        return ok(DfbsResult.success(DfbsConstant.Result.SUCCESS,""));
    }

}
