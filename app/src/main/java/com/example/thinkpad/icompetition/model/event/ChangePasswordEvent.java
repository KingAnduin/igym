package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.user.ChangePasswordRoot;

public class ChangePasswordEvent extends BaseEvent{
    public static final int CHANGE_PASSWORD_OK = 1;
    public static final int CHANGE_PASSWORD_FAIL = -1;
    ChangePasswordRoot root;
    public ChangePasswordRoot getRoot() {
        return root;
    }

    public void setRoot(ChangePasswordRoot root) {
        this.root = root;
    }
}
