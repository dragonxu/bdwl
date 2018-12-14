package com.liaoin.Enum;

public enum Result {
    SUCCESS("请求成功",0),
    FAIL("请求失败",1),
    DISABLED_TOKEN("token过期",2),
    NO_USER("用户不存在",3),
    ERROR_PASSWORD("密码错误",4),
    DATA_IS_NULL("查询数据为空",5),
    HAVE_NOT_DATA("数据信息不存在",6),
    HAVE_DATA("数据信息已存在",7),
    HAVE_USER("该管理员已存在",8),
    HAVE_NOT_BALANCE("余额不足",9),
    TWICE_PASSWORD_NOT_EQUAL("新密码和重复密码不一致",10),
    OLD_PASSWORD_NOT_EQUAL("原始密码不正确",11),
    USER_DATA_ADDED("该用户已添加",12),
    ;

    Result(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public Integer code;
    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
