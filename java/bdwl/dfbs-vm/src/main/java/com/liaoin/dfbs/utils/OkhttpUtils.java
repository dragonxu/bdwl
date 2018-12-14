package com.liaoin.dfbs.utils;

import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;


public class OkhttpUtils {

    private  static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory(),createX509TrustManager())
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    private static X509TrustManager createX509TrustManager(){
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    /**get请求*/
    public static Response get(String url) throws IOException {
        Request request=new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();

    }


    /**post*/
    public static Response post(String url, Map<String,String> params) throws IOException {
        String str = FastJsonUtil.toJSONString(params);
        RequestBody body = FormBody.create(MediaType.parse("application/json"), str);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }


}
