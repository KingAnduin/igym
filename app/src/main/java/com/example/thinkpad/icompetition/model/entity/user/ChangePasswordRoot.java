package com.example.thinkpad.icompetition.model.entity.user;

public class ChangePasswordRoot {
    private int code;  //0：失败 1：用户不存在 2：原密码错误 200：成功
    private String msg;
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

}
