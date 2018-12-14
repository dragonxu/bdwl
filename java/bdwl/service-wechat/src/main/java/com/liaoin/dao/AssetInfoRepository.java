package com.liaoin.dao;

import com.liaoin.bean.AssetInfo;
import com.liaoin.bean.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetInfoRepository extends JpaRepository<AssetInfo,String> {

    AssetInfo findByAssetId(String assetId);

}
