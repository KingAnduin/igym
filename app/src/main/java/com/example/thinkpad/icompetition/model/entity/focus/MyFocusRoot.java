package com.example.thinkpad.icompetition.model.entity.focus;

import com.example.thinkpad.icompetition.model.entity.search.UsersBean;

import java.util.List;

/**
 * Created By hjg on 2018/12/28
 */
public class MyFocusRoot {
    private int code;
    private String msg;
    private List<UsersBean> data;

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

    public List<UsersBean> getData() {
        return data;
    }

    public void setData(List<UsersBean> data) {
        this.data = data;
    }

}
