package com.liaoin.dfbs.message;

import com.liaoin.Enum.Result;
import com.liaoin.message.OperateResult;

public class DfbsResult {

    private String result;
    private String qr_code;


    public DfbsResult(String result, String qr_code) {
        this.result = result;
        this.qr_code = qr_code;
    }

    public static DfbsResult success(String result, String qr_code){
        return new DfbsResult(result,qr_code);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
}
