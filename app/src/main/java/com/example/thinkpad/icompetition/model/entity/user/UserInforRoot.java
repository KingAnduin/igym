package com.example.thinkpad.icompetition.model.entity.user;

public class UserInforRoot {
    private int code;
    private UserInforBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserInforBean getData() {
        return data;
    }

    public void setData(UserInforBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
