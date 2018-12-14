package com.liaoin.dfbs.dao;

import com.liaoin.dfbs.bean.QrCodeContentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeContentRecordRepository extends JpaRepository<QrCodeContentRecord,String> {

    QrCodeContentRecord findByOrderNo(String orderNo);
}
