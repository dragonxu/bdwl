package com.liaoin.Enum;

/**
 * Created by Administrator on 2015/1/4 0004.
 */
public enum Status {
    BLOCK("显示", 0),
    NONE("隐藏", 1);

    Status(String name, int index) {
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
