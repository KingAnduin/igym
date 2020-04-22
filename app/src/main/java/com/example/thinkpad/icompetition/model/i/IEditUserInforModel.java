package com.example.thinkpad.icompetition.model.i;

import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;

public interface IEditUserInforModel {
    void submitUserInfor(UserInforBean bean,String user_acoount);
    void getUserInfor(String num);
}
