package com.liaoin.dfbs.service;

import com.liaoin.dfbs.bean.QrCodeContentRecord;

public interface QrCodeContentRecordService {

    public QrCodeContentRecord save(QrCodeContentRecord qrCodeContentRecord);

    public QrCodeContentRecord findById(String id);

    public QrCodeContentRecord findByOrderNo(String orderNo);
}
