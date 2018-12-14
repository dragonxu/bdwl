package com.liaoin.service.impl;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.Order;
import com.liaoin.bean.ShippingNotice;
import com.liaoin.dao.OrderRepository;
import com.liaoin.dao.ShippingNoticeRepository;
import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.dao.QrCodeContentRecordRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.OrderService;
import com.liaoin.service.ShippingNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
public class ShippingNoticeServiceImpl implements ShippingNoticeService {

    @Autowired
    private ShippingNoticeRepository shippingNoticeRepository;

    @Autowired
    private QrCodeContentRecordRepository qrCodeContentRecordRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public OperateResult shippingNotice(ShippingNotice shippingNotice) {
        shippingNoticeRepository.save(shippingNotice);

        QrCodeContentRecord qrCodeContentRecord = qrCodeContentRecordRepository.findByOrderNo(shippingNotice.getOrderNo());
        Order order = orderRepository.findByQrCodeContentRecordId(qrCodeContentRecord.getId());
        if(shippingNotice.getDeliverStatus().equals("0")){
            order.setOrderStatus(OrderStatus.SHIPMENT_SCUEESS);
            orderRepository.save(order);
        }else{
            order.setOrderStatus(OrderStatus.SHIPMENT_FAIL);
            orderRepository.save(order);
            //退款操作
            orderService.refund(order.getId());
        }
        return success();
    }
}
