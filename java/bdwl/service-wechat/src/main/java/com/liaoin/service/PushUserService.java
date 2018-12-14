package com.liaoin.service;

import com.liaoin.bean.PushUser;
import com.liaoin.message.OperateResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PushUserService {

    public List<PushUser> findAllLists();

    public Page<PushUser> queryWithPage(int page, int size);

    public OperateResult deleteById(String id);

    public OperateResult add(String weiXinUserId);
}
