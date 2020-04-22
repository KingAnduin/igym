package com.example.thinkpad.icompetition.model.entity.user;

/**
 * Created by Hjg on 2018/11/27.
 * 登陆的返回结果
 */
public class LoginRoot {
    private int code;
    private String msg;
    private LoginBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoginBean getData() {
        return data;
    }

    public void setData(LoginBean data) {
        this.data = data;
    }
}
