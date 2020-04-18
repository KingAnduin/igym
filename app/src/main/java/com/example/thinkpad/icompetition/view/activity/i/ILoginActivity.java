package com.example.thinkpad.icompetition.view.activity.i;

import com.example.thinkpad.icompetition.model.entity.user.LoginRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;

/**
 * Created by Hjg on 2018/11/27.
 */
public interface ILoginActivity extends IBaseActivity{
    void loginReturn(LoginRoot root);    //登陆请求的回调
    void getUserInforReturn(UserInforRoot root); //请求返回用户数据的回调
}
