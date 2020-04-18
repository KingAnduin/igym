package com.example.thinkpad.icompetition.presenter.i;

import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;

public interface IEditUserInforPresenter extends IBasePresenter{
    //提交用户信息
    void submitUserInfor(UserInforBean bean,boolean haveHeadImage);
    //获取用户信息
    void getUserInfor(String num);
}
