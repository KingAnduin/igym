package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.user.RegisterRoot;

public class RegisterEvent extends BaseEvent {

    public static final int REGISTE_OK = 100;
    public static final int REGISTE_FAIL = 0;
    private RegisterRoot root;
    private String message = "";

    public RegisterRoot getRoot() {
        return root;
    }

    public void setRoot(RegisterRoot root) {
        this.root = root;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
