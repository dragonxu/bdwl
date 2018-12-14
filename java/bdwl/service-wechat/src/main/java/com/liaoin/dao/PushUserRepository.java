package com.liaoin.dao;

import com.liaoin.bean.PushUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushUserRepository extends JpaRepository<PushUser,String> {

    PushUser findByWeiXinUserId(String weiXinUserId);
}
