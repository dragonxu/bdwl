package com.liaoin.Enum;

/**
 * Created by Administrator on 2015/1/4 0004.
 */
public enum CheckStatus {
    CHECK("选中", 0),
    UN_CHECK("未选中",1);

    CheckStatus(String name, int index) {
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
