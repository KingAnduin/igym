package com.example.thinkpad.icompetition.presenter.i;

/**
 * Created by Hjg on 2018/11/27.
 */
public interface ILoginPresenter extends IBasePresenter{
    void login(String name, String pwd);
    void getUserInfor(String name);
}
