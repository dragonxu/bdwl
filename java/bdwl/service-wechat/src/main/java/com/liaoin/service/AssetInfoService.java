package com.liaoin.service;

import com.liaoin.bean.AssetInfo;
import com.liaoin.message.OperateResult;

import java.util.List;

public interface AssetInfoService {

    /**
     * 同步售货机信息
     */
    public OperateResult synchronization();

    public AssetInfo findByAssetId(String assetId);

    public List<AssetInfo> findAll();
}
