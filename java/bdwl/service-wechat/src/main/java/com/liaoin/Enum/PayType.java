package com.liaoin.Enum;

/**
 * 角色状态
 * Created by Administrator on 2015/1/4 0004.
 */
public enum PayType {
    PAY_WITH_BALANCE("余额支付", 0),
    PAY_WITH_WECHAT("微信支付", 1);

    PayType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    private String name;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
