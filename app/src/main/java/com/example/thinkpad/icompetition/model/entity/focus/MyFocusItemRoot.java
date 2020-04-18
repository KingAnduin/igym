package com.example.thinkpad.icompetition.model.entity.focus;

import java.io.Serializable;

/**
 * Created By hjg on 2018/12/28
 */
public class MyFocusItemRoot implements Serializable {


    private String user_birthday;
    private String user_headimage;
    private String user_interest;
    private String user_name;
    private String user_num;
    private String user_roleid;
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

    public String getUser_interest() {
        return user_interest;
    }

    public void setUser_interest(String user_interest) {
        this.user_interest = user_interest;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getUser_roleid() {
        return user_roleid;
    }

    public void setUser_roleid(String user_roleid) {
        this.user_roleid = user_roleid;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
}
