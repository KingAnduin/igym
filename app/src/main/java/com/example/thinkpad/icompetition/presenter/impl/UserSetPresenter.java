package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.impl.UserSetModel;
import com.example.thinkpad.icompetition.presenter.i.IBasePresenter;
import com.example.thinkpad.icompetition.view.activity.impl.UserSetActivity;

public class UserSetPresenter extends BasePresenter<UserSetActivity,UserSetModel> implements IBasePresenter {
    public UserSetPresenter(UserSetActivity view) {
        super(view);
    }

    @Override
    protected UserSetModel getModel(Handler handler) {
        return new UserSetModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {

    }
}
