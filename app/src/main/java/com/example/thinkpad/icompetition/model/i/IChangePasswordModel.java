package com.example.thinkpad.icompetition.model.i;

public interface IChangePasswordModel extends IBaseModel{
    void changePassword(String oldPassword, String newPassword);
}
