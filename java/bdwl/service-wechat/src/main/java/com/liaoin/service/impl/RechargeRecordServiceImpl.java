package com.liaoin.service.impl;

import com.liaoin.Enum.OrderStatus;
import com.liaoin.bean.RechargeRecord;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dao.RechargeRecordRepository;
import com.liaoin.service.RechargeRecordService;
import com.liaoin.util.Base64;
import com.liaoin.util.DateFormat;
import com.liaoin.util.StringUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordRepository rechargeRecordRepository;

    @Override
    public Page<RechargeRecord> queryWithPage(int page, int size, OrderStatus orderStatus,String beginDate,String endDate) {
        Specification<RechargeRecord> specification = new Specification<RechargeRecord>() {
            @Override
            public Predicate toPredicate(Root<RechargeRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(orderStatus != null) {
                    predicate.add(cb.equal(root.get("orderStatus").as(OrderStatus.class), orderStatus));
                }else{
                    predicate.add(cb.equal(root.get("orderStatus").as(OrderStatus.class), OrderStatus.ORDER_PAID));
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

                if(StringUtils.isNull(beginDate) && StringUtils.isNotNull(endDate)){
                    try {
                        predicate.add(cb.lessThanOrEqualTo(root.get("updateTime").as(Date.class), DateFormat.dateToStampEndmm(endDate)));
                    }catch (Exception e){

                    }
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<RechargeRecord> rechargeRecords = rechargeRecordRepository.findAll(specification,pageRequest);
        return rechargeRecords;
    }

    @Override
    public double queryTotalMoney(String beginDate, String endDate) {
        double totalMoney = 0;
        if(StringUtils.isNull(beginDate) || StringUtils.isNull(endDate)){
            totalMoney = rechargeRecordRepository.querySum();
        }

        if(StringUtils.isNotNull(beginDate) && StringUtils.isNotNull(endDate)){
            try{
            totalMoney = rechargeRecordRepository.querySum(DateFormat.dateToStampEndmm(beginDate),DateFormat.dateToStampEndmm(endDate));
            }catch (Exception e){
            }
        }

        if(StringUtils.isNull(beginDate) && StringUtils.isNotNull(endDate)){
            try{
                totalMoney = rechargeRecordRepository.querySumendDate(DateFormat.dateToStampEndmm(endDate));
            }catch (Exception e){
            }
        }

        if(StringUtils.isNotNull(beginDate) && StringUtils.isNull(endDate)){
            try{
                totalMoney = rechargeRecordRepository.querySumbeginDate(DateFormat.dateToStampEndmm(beginDate));
            }catch (Exception e){
            }
        }
        return totalMoney;
    }

    @Override
    public int queryCount(String beginDate, String endDate) {
        int totalMoney = 0;
        if(StringUtils.isNull(beginDate) || StringUtils.isNull(endDate)){
            totalMoney = rechargeRecordRepository.queryCount();
        }

        if(StringUtils.isNotNull(beginDate) && StringUtils.isNotNull(endDate)){
            try{
            totalMoney = rechargeRecordRepository.queryCount(DateFormat.dateToStampEndmm(beginDate),DateFormat.dateToStampEndmm(endDate));
            }catch (Exception e){
            }
        }

        if(StringUtils.isNull(beginDate) && StringUtils.isNotNull(endDate)){
            try{
                totalMoney = rechargeRecordRepository.queryCountendDate(DateFormat.dateToStampEndmm(endDate));
            }catch (Exception e){
            }
        }

        if(StringUtils.isNotNull(beginDate) && StringUtils.isNull(endDate)){
            try{
                totalMoney = rechargeRecordRepository.queryCountbeginDate(DateFormat.dateToStampEndmm(beginDate));
            }catch (Exception e){
            }
        }

        return totalMoney;
    }
}
