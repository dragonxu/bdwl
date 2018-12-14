package com.liaoin.service.impl;

import com.liaoin.Enum.Result;
import com.liaoin.bean.RechargeAmount;
import com.liaoin.bean.WeiXinUser;
import com.liaoin.dao.RechargeAmountRepository;
import com.liaoin.message.OperateResult;
import com.liaoin.service.RechargeAmountService;
import com.liaoin.util.Base64;
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
import java.util.List;

import static com.liaoin.message.OperateResult.fail;
import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
public class RechargeAmountServiceImpl implements RechargeAmountService {

    @Autowired
    private RechargeAmountRepository rechargeAmountRepository;

    @Override
    public List<RechargeAmount> findAll() {
        return rechargeAmountRepository.findAll();
    }

    @Override
    public Page<RechargeAmount> queryWithPage(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<RechargeAmount> rechargeAmounts = rechargeAmountRepository.findAll(pageRequest);
        return rechargeAmounts;
    }

    @Override
    public OperateResult save(RechargeAmount rechargeAmount) {
        rechargeAmountRepository.save(rechargeAmount);
        return success();
    }

    @Override
    public RechargeAmount findById(String id) {
        if(!rechargeAmountRepository.existsById(id)){
            return null;
        }else{
            return rechargeAmountRepository.findById(id).get();
        }
    }

    @Override
    public OperateResult deleteById(String id) {
        if(!rechargeAmountRepository.existsById(id)){
            return fail(Result.HAVE_NOT_DATA);
        }else {
            rechargeAmountRepository.deleteById(id);
            return success();
        }
    }
}
