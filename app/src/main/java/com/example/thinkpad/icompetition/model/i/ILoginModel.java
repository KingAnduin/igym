package com.example.thinkpad.icompetition.model.i;

/**
 * Created by Hjg on 2018/11/27.
 */
public interface ILoginModel extends IBaseModel{
    void userLogin(String name, String pwd);
    void userGetInfor(String name);
}
