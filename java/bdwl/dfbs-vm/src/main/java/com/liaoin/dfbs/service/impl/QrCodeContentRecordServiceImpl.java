package com.liaoin.dfbs.service.impl;

import com.liaoin.dfbs.bean.QrCodeContentRecord;
import com.liaoin.dfbs.dao.QrCodeContentRecordRepository;
import com.liaoin.dfbs.service.QrCodeContentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class QrCodeContentRecordServiceImpl implements QrCodeContentRecordService {

    @Autowired
    private QrCodeContentRecordRepository qrCodeContentRecordRepository;

    @Override
    public QrCodeContentRecord save(QrCodeContentRecord qrCodeContentRecord) {
        return qrCodeContentRecordRepository.save(qrCodeContentRecord);
    }

    @Override
    public QrCodeContentRecord findById(String id) {
        if(qrCodeContentRecordRepository.existsById(id)){
            return qrCodeContentRecordRepository.findById(id).get();
        }else{
            return null;
        }
    }

    @Override
    public QrCodeContentRecord findByOrderNo(String orderNo) {
        return qrCodeContentRecordRepository.findByOrderNo(orderNo);
    }
}
