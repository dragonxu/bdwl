package com.liaoin.util;

public class StringUtils {

    /**
     * 判断字符串是否为空
     */
    public  static boolean isNull(String str){
        if (null==str || "".equals(str) || "null".equals(str)){
            return true;
        }else{
            return false;
        }
    }

    public static  boolean isNotNull(String str){
        if ( null!=str && !"".equals(str) && !"null".equals(str)){
            return true;
        }else{
            return false;
        }
    }
}
