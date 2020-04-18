package com.example.thinkpad.icompetition.model.entity.search;

import java.util.List;

public class SearchRoot {
    private int code;
    private SearchDataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SearchDataBean getData() {
        return data;
    }

    public void setData(SearchDataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
