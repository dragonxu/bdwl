package com.liaoin.service.impl;

import com.liaoin.Enum.CouponStatus;
import com.liaoin.Enum.OrderStatus;
import com.liaoin.Enum.PayType;
import com.liaoin.Enum.Result;
import com.liaoin.bean.Goods;
import com.liaoin.bean.Order;
import com.liaoin.bean.UserCoupon;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dao.OrderRepository;
import com.liaoin.dao.UserCouponRepository;
import com.liaoin.dao.WeiXinUserRepository;
import com.liaoin.dfbs.DfbsVmUtils;
import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.dao.QrCodeContentRecordRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.OrderService;
import com.liaoin.service.WeiXinUserService;
import com.liaoin.util.DateFormat;
import com.liaoin.util.StringUtils;
import com.liaoin.wechat.JSSDK;
import com.liaoin.wechat.WechatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

import static com.liaoin.Enum.Result.HAVE_NOT_BALANCE;
import static com.liaoin.message.OperateResult.fail;
import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WeiXinUserRepository weiXinUserRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private QrCodeContentRecordRepository qrCodeContentRecordRepository;

    @Override
    public String createOrderNo() {
        return System.currentTimeMillis()+"";
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> queryByWhere(Order order) {
        Specification<Order> specification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNotNull(order.getWeiXinUser().getId())) {
                    predicate.add(cb.equal(root.get("weiXinUser").get("id").as(String.class), order.getWeiXinUser().getId()));
                }

                if(order.getOrderStatus() != null){
                    predicate.add(cb.equal(root.get("orderStatus").as(OrderStatus.class), order.getOrderStatus()));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        List<Order> orders = orderRepository.findAll(specification,sort);
        return orders;
    }

    @Override
    public OperateResult payWithBalance(WeiXinUser weiXinUser, String orderId) {
        weiXinUser = weiXinUserRepository.findByOpenid(weiXinUser.getOpenid());
        Order order = orderRepository.findById(orderId).get();
        if(weiXinUser.getBalance() < order.getTotalMoney()){
            return fail(Result.HAVE_NOT_BALANCE);
        }else{
            order.setOrderStatus(OrderStatus.ORDER_PAID);
            order.setUpdateTime(new Date());
            order.setPayType(PayType.PAY_WITH_BALANCE);
            orderRepository.save(order);

            //修改用户优惠券状态
            if(StringUtils.isNotNull(order.getUserCouponId())){
                UserCoupon userCoupon = userCouponRepository.findById(order.getUserCouponId()).get();
                userCoupon.setCouponStatus(CouponStatus.ALREADY_USED);
                userCouponRepository.save(userCoupon);
            }

            //减少微信用户余额
            weiXinUser.setBalance(weiXinUser.getBalance() - order.getTotalMoney());
            weiXinUserRepository.save(weiXinUser);

            //通知出货
            try {
                QrCodeContentRecord qrCodeContentRecord = qrCodeContentRecordRepository.findById(order.getQrCodeContentRecordId()).get();
                DfbsVmUtils.noticeShipment(qrCodeContentRecord.getNotify_url(),"");
                //修改出货状态
//                order.setOrderStatus(OrderStatus.SHIPMENT_SCUEESS);
//                orderRepository.save(order);
            }catch (Exception e){
                //修改出货状态
                order.setOrderStatus(OrderStatus.SHIPMENT_FAIL);
                orderRepository.save(order);
                //执行退款操作
                refund(orderId);
            }
            return success();
        }
    }


    @Override
    public OperateResult payWithWechat(WeiXinUser weiXinUser, String orderId,String uri) {
        Order order = orderRepository.findById(orderId).get();
        Map<String,Object> map = new HashMap<String, Object>();
        Map<String,String> signPackage = JSSDK.sign(uri);
        //发起支付
        try {
            Map resultMap = WechatUtils.weChatPay(order.getId(), (int) (order.getTotalMoney() * 100), weiXinUser.getOpenid());
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
    public Order findById(String id) {
        if(orderRepository.existsById(id)){
            return orderRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public OperateResult delete(String id) {
        orderRepository.deleteById(id);
        return success();
    }

    @Override
    public Page<Order> queryWithPage(int page, int size, OrderStatus orderStatus,String assetId,String beginDate,String endDate) {
        Specification<Order> specification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(orderStatus != null) {
                    predicate.add(cb.equal(root.get("orderStatus").as(OrderStatus.class), orderStatus));
                }else{
                    predicate.add(cb.notEqual(root.get("orderStatus").as(OrderStatus.class), OrderStatus.WAIT_FOR_PAY));
                }

                if(StringUtils.isNotNull(assetId)){
                    predicate.add(cb.equal(root.get("assetInfo").get("id").as(String.class), assetId));

                }

                if(StringUtils.isNotNull(beginDate) && StringUtils.isNull(endDate)){
                    try {
                        predicate.add(cb.greaterThanOrEqualTo(root.get("updateTime").as(Date.class), DateFormat.dateToStampEndmm(beginDate)));
                    }catch (Exception e){

                    }
                }

                if(StringUtils.isNull(beginDate) && StringUtils.isNotNull(endDate)){
                    try {
                        predicate.add(cb.lessThanOrEqualTo(root.get("updateTime").as(Date.class), DateFormat.dateToStampEndmm(endDate)));
                    }catch (Exception e){

                    }
                }
                if(StringUtils.isNotNull(beginDate) && StringUtils.isNotNull(endDate)){
                    try {
                        predicate.add(cb.between(root.get("updateTime"),DateFormat.dateToStampEndmm(beginDate),DateFormat.dateToStampEndmm(endDate)));
                    }catch (Exception e){

                    }
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<Order> orders = orderRepository.findAll(specification,pageRequest);
        return orders;
    }

    /**
     * 出货失败退款
     */
    public void refund(String orderId){
        Order order = orderRepository.findById(orderId).get();
        WeiXinUser weiXinUser = weiXinUserRepository.findByOpenid(order.getWeiXinUser().getOpenid());
        weiXinUser.setBalance(weiXinUser.getBalance() + order.getTotalMoney());
        weiXinUserRepository.save(weiXinUser);
        order.setOrderStatus(OrderStatus.REFUND);
        orderRepository.save(order);
    }

    @Override
    public Map<String,Double> queryTotalMoney(OrderStatus orderStatus, String beginDate, String endDate,String assetId) {
        Map<String,Double> result = null;
        if(StringUtils.isNull(assetId)){
            if((StringUtils.isNull(beginDate) || StringUtils.isNull(endDate)) && orderStatus == null){
                result = orderRepository.queryTotalMoney();
            }
            if(orderStatus != null && (StringUtils.isNull(beginDate) || StringUtils.isNull(endDate))){
                result = orderRepository.queryTotalMoneyWhereByOrderStatus(orderStatus.getIndex());
            }
            if(orderStatus != null && StringUtils.isNotNull(beginDate) && StringUtils.isNotNull(endDate)){
                try {
                    result = orderRepository.queryTotalMoney(orderStatus.getIndex(),DateFormat.dateToStampEndmm(beginDate),DateFormat.dateToStampEndmm(endDate));
                }catch (Exception e){

                }
            }

            if(orderStatus != null && (StringUtils.isNull(beginDate) || StringUtils.isNotNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneyendDate(orderStatus.getIndex(),DateFormat.dateToStampEndmm(endDate));
                }catch (Exception e){

                }
            }

            if(orderStatus != null && (StringUtils.isNotNull(beginDate) || StringUtils.isNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneybeginDate(orderStatus.getIndex(),DateFormat.dateToStampEndmm(beginDate));
                }catch (Exception e){

                }
            }

            if(orderStatus == null && (StringUtils.isNotNull(beginDate) && StringUtils.isNotNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoney(DateFormat.dateToStampEndmm(beginDate),DateFormat.dateToStampEndmm(endDate));
                }catch (Exception e){

                }
            }

            if(orderStatus == null && (StringUtils.isNull(beginDate) || StringUtils.isNotNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneyendDate(DateFormat.dateToStampEndmm(endDate));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(orderStatus == null && (StringUtils.isNotNull(beginDate) || StringUtils.isNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneybeginDate(DateFormat.dateToStampEndmm(beginDate));
                }catch (Exception e){

                }
            }
        }else{
            if((StringUtils.isNull(beginDate) || StringUtils.isNull(endDate)) && orderStatus == null){
                result = orderRepository.queryTotalMoney(assetId);
            }
            if(orderStatus != null && (StringUtils.isNull(beginDate) || StringUtils.isNull(endDate))){
                result = orderRepository.queryTotalMoneyWhereByOrderStatus(orderStatus.getIndex(),assetId);
            }
            if(orderStatus != null && StringUtils.isNotNull(beginDate) && StringUtils.isNotNull(endDate)){
                try {
                    result = orderRepository.queryTotalMoney(orderStatus.getIndex(),DateFormat.dateToStampEndmm(beginDate),DateFormat.dateToStampEndmm(endDate),assetId);
                }catch (Exception e){

                }
            }

            if(orderStatus != null && (StringUtils.isNull(beginDate) || StringUtils.isNotNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneyendDate(orderStatus.getIndex(),DateFormat.dateToStampEndmm(endDate),assetId);
                }catch (Exception e){

                }
            }

            if(orderStatus != null && (StringUtils.isNotNull(beginDate) || StringUtils.isNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneybeginDate(orderStatus.getIndex(),DateFormat.dateToStampEndmm(beginDate),assetId);
                }catch (Exception e){

                }
            }

            if(orderStatus == null && (StringUtils.isNotNull(beginDate) && StringUtils.isNotNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoney(DateFormat.dateToStampEndmm(beginDate),DateFormat.dateToStampEndmm(endDate),assetId);
                }catch (Exception e){

                }
            }

            if(orderStatus == null && (StringUtils.isNull(beginDate) || StringUtils.isNotNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneyendDate(DateFormat.dateToStampEndmm(endDate),assetId);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(orderStatus == null && (StringUtils.isNotNull(beginDate) || StringUtils.isNull(endDate))){
                try {
                    result = orderRepository.queryTotalMoneybeginDate(DateFormat.dateToStampEndmm(beginDate),assetId);
                }catch (Exception e){

                }
            }
        }
        return result;
    }
}
