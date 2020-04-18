package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.impl.UerInforModel;
import com.example.thinkpad.icompetition.presenter.i.IUserInforPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.UserInforActivity;

public class UserInforPresenter extends BasePresenter<UserInforActivity,UerInforModel> implements IBaseModel,IUserInforPresenter {
    public UserInforPresenter(UserInforActivity view) {
        super(view);
    }

    @Override
    protected UerInforModel getModel(Handler handler) {
        return new UerInforModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {

    }
}
