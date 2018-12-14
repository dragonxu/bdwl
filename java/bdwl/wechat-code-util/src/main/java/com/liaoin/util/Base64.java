package com.liaoin.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * Created by Administrator on 2016/12/19.
 */
public class Base64 {
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key){
        byte[] bt;
        try {
            bt = (new BASE64Decoder()).decodeBuffer(key);
            return new String(bt, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(String key) throws Exception{
//        String result =  new String(key.getBytes("iso8859-1"),"utf-8");
        byte[] bt = key.getBytes("utf-8");
        return (new BASE64Encoder()).encodeBuffer(bt);
    }
}
