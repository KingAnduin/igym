package com.example.thinkpad.icompetition.model.entity.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

@Entity
public class UserInforBean implements Serializable {
    /**
     * user_birthday : null
     * user_headimage : null
     * user_interest : null
     * user_name : null
     * user_num : 15681953321
     * user_roleid : 1
     * user_sex : null
     */
    @Id
    private long user_num;
    @Property
    private String user_birthday;
    private String user_headimage;
    private String user_interest;
    private String user_name;
    private int user_roleid;
    private String user_sex;

    @Generated(hash = 1205166776)
    public UserInforBean(long user_num, String user_birthday, String user_headimage, String user_interest, String user_name, int user_roleid, String user_sex) {
        this.user_num = user_num;
        this.user_birthday = user_birthday;
        this.user_headimage = user_headimage;
        this.user_interest = user_interest;
        this.user_name = user_name;
        this.user_roleid = user_roleid;
        this.user_sex = user_sex;
    }
    @Generated(hash = 1780652936)
    public UserInforBean() {
    }
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
