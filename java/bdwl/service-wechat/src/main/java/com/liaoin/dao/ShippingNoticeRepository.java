package com.liaoin.dao;

import com.liaoin.bean.AssetInfo;
import com.liaoin.bean.ShippingNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingNoticeRepository extends JpaRepository<ShippingNotice,String> {

}
