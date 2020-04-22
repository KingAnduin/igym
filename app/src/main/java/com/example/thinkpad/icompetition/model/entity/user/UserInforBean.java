package com.example.thinkpad.icompetition.model.entity.user;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

@Entity
public class UserInforBean implements Serializable {
    /**
     *  "id": 2,
     *  "user_account_pk": "2",
     *  "name": "yue",
     *  "nickname": "yxf",
     *  "sex": "女",
     *  "birthday": null,
     *  "head_image": "",
     *  "contact_phone": "18681690999",
     *  "user_account": 2
     */
    @Id
    private long id;
    @Property
    private String user_account_pk;
    private String name;
    private String nickname;
    private String sex;
    private String birthday;
    private String head_image;
    private String contact_phone;
    private int user_account;
    public int getUser_account() {
        return this.user_account;
    }
    public void setUser_account(int user_account) {
        this.user_account = user_account;
    }
    public String getContact_phone() {
        return this.contact_phone;
    }
    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }
    public String getHead_image() {
        return this.head_image;
    }
    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUser_account_pk() {
        return this.user_account_pk;
    }
    public void setUser_account_pk(String user_account_pk) {
        this.user_account_pk = user_account_pk;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 110770095)
    public UserInforBean(long id, String user_account_pk, String name,
            String nickname, String sex, String birthday, String head_image,
            String contact_phone, int user_account) {
        this.id = id;
        this.user_account_pk = user_account_pk;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.birthday = birthday;
        this.head_image = head_image;
        this.contact_phone = contact_phone;
        this.user_account = user_account;
    }
    @Generated(hash = 1780652936)
    public UserInforBean() {
    }


}
