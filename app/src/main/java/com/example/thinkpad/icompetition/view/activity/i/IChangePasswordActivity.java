package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.user.ChangePasswordRoot;

public interface IChangePasswordActivity extends IBaseActivity{
    void changePassword(String oldPassword, String newPassword);
    void changePasswordReturn(ChangePasswordRoot root);
}
