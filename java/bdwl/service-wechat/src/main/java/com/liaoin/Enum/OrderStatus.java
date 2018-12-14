package com.liaoin.Enum;

/**
 * 角色状态
 * Created by Administrator on 2015/1/4 0004.
 */
public enum OrderStatus {
    WAIT_FOR_PAY("待付款", 0),
    ORDER_PAID("已付款", 1),
    SHIPMENT_SCUEESS("出货成功", 2),
    SHIPMENT_FAIL("出货失败", 3),
    REFUND("已退款", 4),
    ;

    OrderStatus(String name, int index) {
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
