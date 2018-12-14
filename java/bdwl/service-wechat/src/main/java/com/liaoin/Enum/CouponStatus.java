package com.liaoin.Enum;

/**
 * 角色状态
 * Created by Administrator on 2015/1/4 0004.
 */
public enum CouponStatus {
    WAIT("待使用", 0),
    ALREADY_USED("已使用", 1),
    EXPIRES("已过期",2);

    CouponStatus(String name, int index) {
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
