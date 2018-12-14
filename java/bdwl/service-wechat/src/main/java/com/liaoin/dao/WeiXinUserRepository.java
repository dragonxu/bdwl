package com.liaoin.dao;

import com.liaoin.bean.WeiXinUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WeiXinUserRepository extends JpaRepository<WeiXinUser,String>,JpaSpecificationExecutor<WeiXinUser> {

    WeiXinUser findByOpenid(String openid);
}
