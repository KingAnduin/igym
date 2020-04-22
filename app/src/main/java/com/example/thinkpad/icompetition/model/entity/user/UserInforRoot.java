package com.example.thinkpad.icompetition.model.entity.user;

import java.util.List;

public class UserInforRoot {
    private int code;
    private List<UserInforBean> data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<UserInforBean> getData() {
        return data;
    }

    public void setData(List<UserInforBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
