package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.user.LoginRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;

/**
 * Created by Hjg on 2018/11/27.
 */
public class LoginEvent extends BaseEvent {
    public static final int LOGIN_OK = 100;
    public static final int LOGIN_FAIL = 0;
    public static final int GETUERINFOR_OK = 101;
    public static final int GETUSERINFOR_FAIL = 102;
    private String message = "";
    private LoginRoot root;
    private UserInforRoot userInforRoot;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginRoot getRoot() {
        return root;
    }

    public void setRoot(LoginRoot root) {
        this.root = root;
    }

    public void setUserInforRoot(UserInforRoot userInforRoot){
        this.userInforRoot = userInforRoot;
    }
    public UserInforRoot getUserInforRoot(){
        return userInforRoot;
    }
}
