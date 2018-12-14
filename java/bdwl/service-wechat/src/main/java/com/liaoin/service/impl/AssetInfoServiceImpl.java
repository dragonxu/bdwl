package com.liaoin.service.impl;

import com.liaoin.bean.AssetInfo;
import com.liaoin.dao.AssetInfoRepository;
import com.liaoin.dfbs.DfbsVmUtils;
import com.liaoin.dfbs.bean.DfbsAssetInfo;
import com.liaoin.message.OperateResult;
import com.liaoin.service.AssetInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.liaoin.message.OperateResult.success;

@Service
@Slf4j
public class AssetInfoServiceImpl implements AssetInfoService {

    @Autowired
    private AssetInfoRepository assetInfoRepository;

    @Override
    public OperateResult synchronization() {
        try {
            List<DfbsAssetInfo> dfbsAssetInfos = DfbsVmUtils.queryAssetInfo();
            for (DfbsAssetInfo dfbsAssetInfo : dfbsAssetInfos){
                AssetInfo assetInfo = assetInfoRepository.findByAssetId(dfbsAssetInfo.getAssetId());
                if(assetInfo == null){
                    assetInfo = new AssetInfo();
                }
                assetInfo.setAssetId(dfbsAssetInfo.getAssetId());
                assetInfo.setVender(dfbsAssetInfo.getVender());
                assetInfo.setAreaName(dfbsAssetInfo.getAreaName());
                assetInfo.setSiteName(dfbsAssetInfo.getSiteName());
                assetInfoRepository.save(assetInfo);
            }
        }catch (Exception e){
            log.info("售货机信息同步异常：");
        }
        return success();
    }

    @Override
    public AssetInfo findByAssetId(String assetId) {
        return assetInfoRepository.findByAssetId(assetId);
    }

    @Override
    public List<AssetInfo> findAll() {
        return assetInfoRepository.findAll();
    }
}
