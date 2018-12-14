package com.liaoin.service.impl;

import com.google.gson.Gson;
import com.liaoin.Enum.CouponStatus;
import com.liaoin.bean.UserCoupon;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dao.RechargeRecordRepository;
import com.liaoin.dao.WeiXinUserRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.WeiXinUserService;
import com.liaoin.util.Base64;
import com.liaoin.util.StringUtils;
import com.liaoin.wechat.AccessToken;
import com.liaoin.wechat.UserInfo;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
@Slf4j
public class WeiXinUserServiceImpl implements WeiXinUserService {

    @Autowired
    private WeiXinUserRepository weiXinUserRepository;

    @Autowired
    private RechargeRecordRepository rechargeRecordRepository;

    @Override
    public WeiXinUser findByOpenid(String openid) {
        return weiXinUserRepository.findByOpenid(openid);
    }

    @Override
    public WeiXinUser save(WeiXinUser paramWeiXinUser) {
        WeiXinUser weiXinUser = weiXinUserRepository.findByOpenid(paramWeiXinUser.getOpenid());
        if(weiXinUser != null){
            weiXinUser.setAccess_token(paramWeiXinUser.getAccess_token());
            weiXinUser.setRefresh_token(paramWeiXinUser.getRefresh_token());
            weiXinUser.setExpire_in(paramWeiXinUser.getExpire_in());
            weiXinUser.setOpenid(paramWeiXinUser.getOpenid());
            weiXinUser.setNickname(paramWeiXinUser.getNickname());
            weiXinUser.setSex(paramWeiXinUser.getSex());
            weiXinUser.setHeadimgurl(paramWeiXinUser.getHeadimgurl());
            weiXinUser.setCountry(paramWeiXinUser.getCountry());
            weiXinUser.setProvince(paramWeiXinUser.getProvince());
            weiXinUser.setCity(paramWeiXinUser.getCity());
            weiXinUser.setAddTime(paramWeiXinUser.getAddTime());
            weiXinUserRepository.save(weiXinUser);
            return weiXinUser;
        }else{
            weiXinUserRepository.save(paramWeiXinUser);
            return paramWeiXinUser;
        }
    }

    @Override
    public WeiXinUser findById(String id) {
        if(weiXinUserRepository.existsById(id)){
            return weiXinUserRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public void authorization(String code, HttpServletRequest request) {
        AccessToken accessToken = WechatUtils.getAccessToken(code);
        log.info("accessTokenJson：" + new Gson().toJson(accessToken));
        log.info("accessToken：" + accessToken.getAccess_token());
        if(accessToken.getAccess_token() != null && !accessToken.getAccess_token().equals("")){
            UserInfo userInfo  = WechatUtils.get_user_info(accessToken.getAccess_token(),accessToken.getOpenid());
            log.info("userInfo：" + new Gson().toJson(userInfo));
            if(userInfo.getOpenid() != null && !userInfo.getOpenid().equals("")) {
                try {
                    WeiXinUser weiXinUser = WeiXinUser.builder()
                            .access_token(accessToken.getAccess_token())
                            .refresh_token(accessToken.getRefresh_token())
                            .expire_in(accessToken.getExpires_in())
                            .openid(userInfo.getOpenid())
                            .nickname(Base64.encryptBASE64(userInfo.getNickname()))
                            .sex(userInfo.getSex())
                            .headimgurl(userInfo.getHeadimgurl())
                            .country(userInfo.getCountry())
                            .province(userInfo.getProvince())
                            .city(userInfo.getCity())
                            .addTime(new Date())
                            .build();
                    weiXinUser = save(weiXinUser);
                    request.getSession().setAttribute("weiXinUser",weiXinUser);
                }catch (Exception e){
                    log.info("微信授权异常");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Page<WeiXinUser> queryWithPage(int page, int size, String keywords) {
        Specification<WeiXinUser> specification = new Specification<WeiXinUser>() {
            @Override
            public Predicate toPredicate(Root<WeiXinUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNotNull(keywords)) {
                    try {
                        predicate.add(cb.like(root.get("nickname").as(String.class), "%"+Base64.encryptBASE64(keywords.trim())+"%"));
                    }catch (Exception e){

                    }
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<WeiXinUser> weiXinUsers = weiXinUserRepository.findAll(specification,pageRequest);
        return weiXinUsers;
    }


}
