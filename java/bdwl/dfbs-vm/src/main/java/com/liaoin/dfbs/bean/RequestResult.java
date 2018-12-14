package com.liaoin.dfbs.bean;

public class RequestResult<T> {

    private Integer limit;
    private Integer total;
    private String result;
    private Integer cursor;
    private T list;

    public RequestResult() {
    }

    public RequestResult(Integer limit, Integer total, String result, Integer cursor, T list) {
        this.limit = limit;
        this.total = total;
        this.result = result;
        this.cursor = cursor;
        this.list = list;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getCursor() {
        return cursor;
    }

    public void setCursor(Integer cursor) {
        this.cursor = cursor;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }
}
