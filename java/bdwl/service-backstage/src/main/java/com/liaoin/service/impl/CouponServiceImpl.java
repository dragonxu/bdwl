package com.liaoin.service.impl;

import com.liaoin.Enum.CouponStatus;
import com.liaoin.bean.Coupon;
import com.liaoin.bean.Menu;
import com.liaoin.bean.UserCoupon;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dao.CouponRepository;
import com.liaoin.dao.UserCouponRepository;
import com.liaoin.dao.WeiXinUserRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.CouponService;
import com.liaoin.service.WeiXinUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.liaoin.message.OperateResult.fail;
import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private WeiXinUserRepository weiXinUserRepository;

    @Override
    public Page<Coupon> queryWithPage(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<Coupon> coupons = couponRepository.findAll(pageRequest);
        return coupons;
    }

    @Override
    public OperateResult save(Coupon coupon) {
        couponRepository.save(coupon);
        return success();
    }

    public Coupon findById(String id){
        if(couponRepository.existsById(id)){
            return couponRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public OperateResult deleteById(String id){
        if(couponRepository.existsById(id)){
            Coupon coupon = couponRepository.findById(id).get();
            couponRepository.delete(coupon);
            //同时删除用户的优惠券
            userCouponRepository.deleteByCoupon(coupon);
            return success();
        }else{
            return fail();
        }

    }

    @Override
    public OperateResult send(String id) {
        if(couponRepository.existsById(id)){
            Coupon coupon = couponRepository.findById(id).get();
            List<WeiXinUser> weiXinUsers = weiXinUserRepository.findAll();
            for (WeiXinUser weiXinUser : weiXinUsers){
                UserCoupon userCoupon = UserCoupon.builder()
                        .coupon(coupon)
                        .weiXinUserId(weiXinUser.getId())
                        .couponStatus(CouponStatus.WAIT)
                        .build();
                userCouponRepository.save(userCoupon);
            }
            return success();
        }else{
            return fail();
        }
    }

    @Override
    public OperateResult sendToUser(String couponId, String weiXinUserId) {
        if(couponRepository.existsById(couponId)){
            Coupon coupon = couponRepository.findById(couponId).get();
            WeiXinUser weiXinUser = weiXinUserRepository.findById(weiXinUserId).get();
            UserCoupon userCoupon = UserCoupon.builder()
                    .coupon(coupon)
                    .weiXinUserId(weiXinUser.getId())
                    .couponStatus(CouponStatus.WAIT)
                    .build();
            userCouponRepository.save(userCoupon);
            return success();
        }else{
            return fail();
        }
    }
}
