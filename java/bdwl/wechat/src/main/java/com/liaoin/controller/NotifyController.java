package com.liaoin.controller;

import com.liaoin.Enum.CouponStatus;
import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.*;
import com.liaoin.constant.WechatConstant;
import com.liaoin.dfbs.DfbsVmUtils;
import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.dao.QrCodeContentRecordRepository;
import com.liaoin.service.*;
import com.liaoin.util.Base64;
import com.liaoin.util.DateFormat;
import com.liaoin.util.FileOperation;
import com.liaoin.util.StringUtils;
import com.liaoin.wechat.WeChatTemplateUtil;
import com.liaoin.wechat.WechatUtils;
import com.liaoin.wxpay.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 微信支付回调通知
 */
@Controller
@RequestMapping("Notify")
public class NotifyController {

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private WeiXinUserService weiXinUserService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private QrCodeContentRecordRepository qrCodeContentRecordRepository;

    @Autowired
    private PushUserService pushUserService;

    /**
     * 微信支付回调
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    @ResponseBody
    public void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.print("微信支付回调数据开始");
        String inputLine;
        String notityXml = "";
        String resXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("接收到的报文：" + notityXml);

        ResponseHandler responseHandler = new ResponseHandler(request,response);
        responseHandler.setKey(WechatConstant.PARTNER_KEY);

        //示例报文
//        String xml = "<xml><appid><![CDATA[wx914359180fa11235]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[400]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1331076501]]></mch_id><nonce_str><![CDATA[aab3238922bcc25a6f606eb525ffdc56]]></nonce_str><openid><![CDATA[oxsSAwuwQR4S0XSTxVE5P_YF6Hqw]]></openid><out_trade_no><![CDATA[2c9f554f5d118f91015d11abb1340010]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[FBF1D0644901C327CA1B093AB1D7146D]]></sign><time_end><![CDATA[20170705153219]]></time_end><total_fee>400</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4003122001201707059025178833]]></transaction_id></xml>";
        String filename = "WeChatPayNotify-" + DateFormat.stampToDateEndD(new Date().getTime()+"") + ".txt";
        FileOperation.createFile(new File("./bdwl/wechat-logs/WeChatPay/"+filename));
        FileOperation.writeTxtFile(notityXml+"\r\n",new File("./bdwl/wechat-logs/WeChatPay/"+filename));

//        Map weiXinResult = WeChat.parseXmlToMap(xml);
        Map weiXinResult = WechatUtils.parseXmlToMap(notityXml);
        if(responseHandler.isTenpaySign(weiXinResult)){
            if("SUCCESS".equals(weiXinResult.get("result_code"))){
                //支付成功
                String orderId = weiXinResult.get("out_trade_no").toString();
//                orderId = "2c9f554f5d118f91015d11c250450036";
                //微信交易号
                String transactionId = weiXinResult.get("transaction_id").toString();

                //充值回调
                RechargeRecord rechargeRecord = rechargeService.findById(orderId);
                if (rechargeRecord != null) {
                    if(rechargeRecord.getOrderStatus() == OrderStatus.WAIT_FOR_PAY){
                        rechargeRecord.setOrderStatus(OrderStatus.ORDER_PAID);
                        rechargeRecord.setTransactionId(transactionId);
                        rechargeRecord.setUpdateTime(new Date());
                        rechargeService.save(rechargeRecord);
                        //增加用户余额
                        WeiXinUser weiXinUser = weiXinUserService.findById(rechargeRecord.getWeiXinUser().getId());
                        weiXinUser.setBalance(weiXinUser.getBalance() + rechargeRecord.getMoney() + rechargeRecord.getDiscount());

                        weiXinUserService.save(weiXinUser);
                        //向通知管理员发送充值成功通知消息
                        List<PushUser> pushUsers = pushUserService.findAllLists();
                        for (PushUser pushUser : pushUsers){
                            WeChatTemplateUtil.sendRechargeSuccessTemplateMessage(pushUser.getOpenId(),Base64.decryptBASE64(rechargeRecord.getWeiXinUser().getNickname()),rechargeRecord.getMoney(),rechargeRecord.getTransactionId(),DateFormat.stampToDate(rechargeRecord.getUpdateTime().getTime()+""));
                        }

                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                        out.write(resXml.getBytes());
                        out.flush();
                        out.close();
                        return;
                    }else{
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                        out.write(resXml.getBytes());
                        out.flush();
                        out.close();
                        return;
                    }
                }


                //购买商品回调
                Order order = orderService.findById(orderId);
                if (order != null) {
                    if(order.getOrderStatus() == OrderStatus.WAIT_FOR_PAY){
                        order.setOrderStatus(OrderStatus.ORDER_PAID);
                        order.setTransactionId(transactionId);
                        order.setUpdateTime(new Date());
                        orderService.save(order);

                        //通知出货
                        try {
                            QrCodeContentRecord qrCodeContentRecord = qrCodeContentRecordRepository.findById(order.getQrCodeContentRecordId()).get();
                            DfbsVmUtils.noticeShipment(qrCodeContentRecord.getNotify_url(),"");
                            //修改出货状态
//                            order.setOrderStatus(OrderStatus.SHIPMENT_SCUEESS);
//                            orderService.save(order);
                        }catch (Exception e){
                            //修改出货状态
                            order.setOrderStatus(OrderStatus.SHIPMENT_FAIL);
                            orderService.save(order);
                            //退款操作
                            orderService.refund(order.getId());
                        }
                        //修改用户优惠券状态
                        if(StringUtils.isNotNull(order.getUserCouponId())){
                            UserCoupon userCoupon = userCouponService.findById(order.getUserCouponId());
                            userCoupon.setCouponStatus(CouponStatus.ALREADY_USED);
                            userCouponService.save(userCoupon);
                        }
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                        out.write(resXml.getBytes());
                        out.flush();
                        out.close();
                        return;
                    }else{
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                        out.write(resXml.getBytes());
                        out.flush();
                        out.close();
                        return;
                    }
                }

                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }else{
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println("微信支付回调数据结束");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        return;
    }
}
