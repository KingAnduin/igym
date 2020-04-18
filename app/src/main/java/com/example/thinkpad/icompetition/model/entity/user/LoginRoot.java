package com.example.thinkpad.icompetition.model.entity.user;

import java.util.List;

/**
 * Created by Hjg on 2018/11/27.
 * 登陆的返回结果
 */
public class LoginRoot {
    private int code;
    private String msg;
    private List<LoginBean> data;

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

    public List<LoginBean> getData() {
        return data;
    }

    public void setData(List<LoginBean> data) {
        this.data = data;
    }
}
