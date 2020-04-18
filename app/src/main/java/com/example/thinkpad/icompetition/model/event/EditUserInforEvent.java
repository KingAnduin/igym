package com.example.thinkpad.icompetition.model.event;

import com.example.thinkpad.icompetition.model.entity.user.EditUserInforRoot;

public class EditUserInforEvent extends BaseEvent{
    public static final int EDITUSERINFOR_OK = 100;
    public static final int EDITUSERINFOR_FAIL = -100;
    private EditUserInforRoot root;

    public EditUserInforRoot getRoot() {
        return root;
    }

    public void setRoot(EditUserInforRoot root) {
        this.root = root;
    }
}
