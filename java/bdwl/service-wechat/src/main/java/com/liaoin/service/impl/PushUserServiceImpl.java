package com.liaoin.service.impl;

import com.liaoin.Enum.Result;
import com.liaoin.bean.PushUser;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dao.PushUserRepository;
import com.liaoin.dao.WeiXinUserRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.PushUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.liaoin.message.OperateResult.fail;
import static com.liaoin.message.OperateResult.success;

@Service
public class PushUserServiceImpl implements PushUserService {

    @Autowired
    private PushUserRepository pushUserRepository;

    @Autowired
    private WeiXinUserRepository weiXinUserRepository;

    @Override
    public List<PushUser> findAllLists() {
        return pushUserRepository.findAll();
    }

    @Override
    public Page<PushUser> queryWithPage(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<PushUser> pushUsers = pushUserRepository.findAll(pageRequest);
        return pushUsers;
    }

    @Override
    public OperateResult deleteById(String id){
        if(pushUserRepository.existsById(id)){
            pushUserRepository.deleteById(id);
            return success();
        }else{
            return fail();
        }
    }

    @Override
    public OperateResult add(String weiXinUserId) {
        if(weiXinUserRepository.existsById(weiXinUserId)){
            WeiXinUser weiXinUser = weiXinUserRepository.findById(weiXinUserId).get();
            if(pushUserRepository.findByWeiXinUserId(weiXinUserId) != null){
                return fail(Result.USER_DATA_ADDED);
            }else{
                PushUser pushUser = PushUser.builder()
                        .weiXinUserId(weiXinUser.getId())
                        .openId(weiXinUser.getOpenid())
                        .nickname(weiXinUser.getNickname())
                        .build();
                pushUserRepository.save(pushUser);
                return success();
            }
        }else{
            return fail();
        }
    }
}
