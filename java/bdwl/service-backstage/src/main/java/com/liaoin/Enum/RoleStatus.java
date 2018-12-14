package com.liaoin.Enum;

/**
 * 角色状态
 * Created by Administrator on 2015/1/4 0004.
 */
public enum RoleStatus {
    ENABLE("启用", 0),
    DISABLE("禁用",1);

    RoleStatus(String name, int index) {
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
