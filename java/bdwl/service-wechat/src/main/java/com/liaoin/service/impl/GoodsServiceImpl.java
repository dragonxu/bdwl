package com.liaoin.service.impl;

import com.liaoin.bean.Goods;
import com.liaoin.bean.Order;
import com.liaoin.bean.RechargeRecord;
import com.liaoin.dao.GoodsRepository;
import com.liaoin.dfbs.DfbsVmUtils;
import com.liaoin.dfbs.bean.DfbsGoods;
import com.liaoin.message.OperateResult;
import com.liaoin.service.GoodsService;
import com.liaoin.util.DateFormat;
import com.liaoin.util.StringUtils;
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
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.liaoin.message.OperateResult.fail;
import static com.liaoin.message.OperateResult.success;

@Service
@Transactional
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Goods findById(String id) {
        if(goodsRepository.existsById(id)){
            return goodsRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public OperateResult synchronization() {
        try {
            List<DfbsGoods> dfbsGoodsList = DfbsVmUtils.getGoodsLists();
            for (DfbsGoods dfbsGoods : dfbsGoodsList){
                Goods goods = goodsRepository.findByNumber(dfbsGoods.getNumber());
                if(goods == null){
                    goods = new Goods();
                }
                goods.setName(dfbsGoods.getFullName());
                goods.setNumber(dfbsGoods.getNumber());
                goods.setPrice(Float.parseFloat(dfbsGoods.getPrice()));
                goods.setPicture(dfbsGoods.getImgCdnPath());
                goodsRepository.save(goods);
            }
            return success();
        }catch (Exception e){
            log.info("同步异常");
        }
        return fail();
    }

    @Override
    public Goods findByNumber(String number) {
        return goodsRepository.findByNumber(number);
    }

    @Override
    public Goods save(Goods goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public Page<Goods> queryWithPage(int page, int size, String keywords) {
        Specification<Goods> specification = new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(StringUtils.isNotNull(keywords)) {
                    predicate.add(cb.like(root.get("name").as(String.class), "%"+keywords+"%"));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page,size,sort);
        Page<Goods> goods = goodsRepository.findAll(specification,pageRequest);
        return goods;
    }

    @Override
    public OperateResult discount(String id, Float price, Float discount, String beginDate, String endDate) {
        if(goodsRepository.existsById(id)){
            Goods goods = goodsRepository.findById(id).get();
            goods.setPrice(price);
            goods.setDiscount(discount);
            try {
                if(StringUtils.isNotNull(beginDate)){
                    goods.setBeginDate(DateFormat.dateToStampEndmm(beginDate));
                }
                if(StringUtils.isNotNull(endDate)){
                    goods.setEndDate(DateFormat.dateToStampEndmm(endDate));
                }
            }catch (Exception e){
            }
            goodsRepository.save(goods);
            return success();
        }else{
            return fail();
        }
    }
}
