package com.liaoin.service.impl;

import com.liaoin.Enum.CouponStatus;
import com.liaoin.bean.UserCoupon;
import com.liaoin.dao.UserCouponRepository;
import com.liaoin.service.UserCouponService;
import com.liaoin.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Override
    public List<UserCoupon> findByWhere(String weiXinUserId, CouponStatus couponStatus,Date endDate,Float money) {
        Specification<UserCoupon> specification = new Specification<UserCoupon>() {
            @Override
            public Predicate toPredicate(Root<UserCoupon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNotNull(weiXinUserId)) {
                    predicate.add(cb.equal(root.get("weiXinUserId").as(String.class), weiXinUserId));
                }

                if(couponStatus != null){
                    predicate.add(cb.equal(root.get("couponStatus").as(CouponStatus.class), couponStatus));
                }

                if(endDate != null){
                    predicate.add(cb.greaterThanOrEqualTo(root.get("coupon").get("endDate").as(Date.class), endDate));
                }

                if(money != null){
                    predicate.add(cb.greaterThan(root.get("coupon").get("enableStandard").as(Float.class), money));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };

        List<UserCoupon> userCoupons = userCouponRepository.findAll(specification);
        return userCoupons;
    }

    @Override
    public UserCoupon findById(String id) {
        if(userCouponRepository.existsById(id)){
            return userCouponRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public UserCoupon save(UserCoupon userCoupon) {
        return userCouponRepository.save(userCoupon);
    }
}
