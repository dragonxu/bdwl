package com.liaoin.service;

import com.liaoin.bean.WeiXinUser;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WeiXinUserService {

    public WeiXinUser findByOpenid(String openid);


    public WeiXinUser findById(String id);

    public WeiXinUser save(WeiXinUser weiXinUser);

    /**
     * 微信授权
     */
    public void authorization(String code, HttpServletRequest request);

    public Page<WeiXinUser> queryWithPage(int page, int size,String keywords);


}
