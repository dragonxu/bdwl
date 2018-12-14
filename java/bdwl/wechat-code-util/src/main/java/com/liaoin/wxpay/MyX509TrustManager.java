package com.liaoin.wxpay;

import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import java.security.cert.CertificateException;

/**
 * Created by Administrator on 2017/5/17.
 */
public class MyX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }

//    public X509Certificate[] getAcceptedIssuers() {
//// TODO Auto-generated method stub
//        return null;
//    }

    public boolean isClientTrusted(X509Certificate[] arg0) {
// TODO Auto-generated method stub
        return true;
    }

    public boolean isServerTrusted(X509Certificate[] arg0) {
// TODO Auto-generated method stub
        return true;
    }

}
