package com.liaoin.dfbs.utils;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class TrustAllCerts implements X509TrustManager {



    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {}

    @Override
    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}




}
