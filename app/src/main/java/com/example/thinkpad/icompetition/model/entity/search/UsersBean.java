package com.example.thinkpad.icompetition.model.entity.search;

import java.io.Serializable;

public class UsersBean implements Serializable {
    private String user_birthday;
    private String user_headimage;
    private Object user_interest;
    private String user_name;
    private long user_num;
    private int user_roleid;
    private String user_sex;

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_headimage() {
        return user_headimage;
    }

    public void setUser_headimage(String user_headimage) {
        this.user_headimage = user_headimage;
    }

    public Object getUser_interest() {
        return user_interest;
    }

    public void setUser_interest(Object user_interest) {
        this.user_interest = user_interest;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public long getUser_num() {
        return user_num;
    }

    public void setUser_num(long user_num) {
        this.user_num = user_num;
    }

    public int getUser_roleid() {
        return user_roleid;
    }

    public void setUser_roleid(int user_roleid) {
        this.user_roleid = user_roleid;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
}
