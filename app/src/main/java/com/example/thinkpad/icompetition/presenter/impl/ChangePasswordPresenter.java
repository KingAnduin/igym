package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.entity.user.ChangePasswordRoot;
import com.example.thinkpad.icompetition.model.event.ChangePasswordEvent;
import com.example.thinkpad.icompetition.model.impl.ChangePasswordModel;
import com.example.thinkpad.icompetition.presenter.i.IChangePasswordPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.ChangePasswordActivity;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordActivity,ChangePasswordModel> implements IChangePasswordPresenter {
    public ChangePasswordPresenter(ChangePasswordActivity view) {
        super(view);
    }

    @Override
    protected ChangePasswordModel getModel(Handler handler) {
        return new ChangePasswordModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        switch (msg.what){
            case ChangePasswordEvent.CHANGE_PASSWORD_OK:
                mView.changePasswordReturn(((ChangePasswordEvent)msg.obj).getRoot());
                break;
            case ChangePasswordEvent.CHANGE_PASSWORD_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        mModel.changePassword(oldPassword,newPassword);
    }
}
