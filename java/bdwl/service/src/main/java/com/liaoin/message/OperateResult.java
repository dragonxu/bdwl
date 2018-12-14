package com.liaoin.message;

import com.liaoin.Enum.Result;

public class OperateResult {

    private Integer code;
    private String msg="";
    private Object data="";
    private String url="";


    public OperateResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public OperateResult(Integer code, String msg, String url) {
        this.code = code;
        this.msg = msg;
        this.url = url;
    }

    public OperateResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static OperateResult over(){
        return new OperateResult(Result.DISABLED_TOKEN.code,Result.DISABLED_TOKEN.msg);
    }

    public static OperateResult success(){
        return new OperateResult(Result.SUCCESS.code,Result.SUCCESS.msg);
    }

    public static OperateResult success(String url){
        return new OperateResult(Result.SUCCESS.code,Result.SUCCESS.msg,url);
    }


    public static OperateResult success(Object data){
        return new OperateResult(Result.SUCCESS.code,Result.SUCCESS.msg,data);
    }

    public static OperateResult fail(){
        return new OperateResult(Result.FAIL.code,Result.FAIL.msg);
    }

    public static OperateResult fail(Result result){
        return new OperateResult(result.code,result.msg);
    }

    public static OperateResult fail(Integer code, String msg){
        return new OperateResult(code,msg);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
