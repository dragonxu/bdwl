package com.liaoin.service.impl;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.RechargeRecord;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dao.RechargeAmountRepository;
import com.liaoin.dao.RechargeRecordRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.RechargeService;
import com.liaoin.wechat.JSSDK;
import com.liaoin.wechat.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.liaoin.message.OperateResult.fail;
import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
@Slf4j
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private RechargeRecordRepository rechargeRecordRepository;

    @Autowired
    private RechargeAmountRepository rechargeAmountRepository;

    @Override
    public OperateResult recharge(WeiXinUser weiXinUser, String rechargeAmountId, String uri) {
        RechargeAmount rechargeAmount = rechargeAmountRepository.findById(rechargeAmountId).get();
        RechargeRecord rechargeRecord = RechargeRecord.builder()
                .weiXinUser(weiXinUser)
                .money(rechargeAmount.getMoney())
                .discount(rechargeAmount.getDiscount())
                .addTime(new Date())
                .updateTime(new Date())
                .orderStatus(OrderStatus.WAIT_FOR_PAY)
                .build();
        rechargeRecordRepository.save(rechargeRecord);
        Map<String,Object> map = new HashMap<String, Object>();
        Map<String,String> signPackage = JSSDK.sign(uri);
        //发起支付
        try {
            Map resultMap = WechatUtils.weChatPay(rechargeRecord.getId(), (int) (rechargeRecord.getMoney() * 100), weiXinUser.getOpenid());
//            if(resultMap.get("return_code").equals("FAIL")){
//                return fail(1,resultMap.get("return_msg").toString());
//            }
            map.put("payMessage",resultMap);
            map.put("signPackage",signPackage);
            return success(map);
        }catch (Exception e){
            log.info("支付发起异常：");
            e.printStackTrace();
            return fail();
        }

    }

    @Override
    public RechargeRecord findById(String id) {
        if(rechargeRecordRepository.existsById(id)){
            return rechargeRecordRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public OperateResult save(RechargeRecord rechargeRecord) {
        rechargeRecordRepository.save(rechargeRecord);
        return success();
    }
}
