package com.example.thinkpad.icompetition.model.i;

import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;

public interface IEditUserInforModel {
    void submitUserInfor(UserInforBean bean,boolean haveHeadImage);
    void getUserInfor(String num);
}
