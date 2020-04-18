package com.example.thinkpad.icompetition.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.example.thinkpad.icompetition.model.entity.user.RegisterRoot;
import com.example.thinkpad.icompetition.model.event.RegisterEvent;
import com.example.thinkpad.icompetition.model.impl.RegisterModel;
import com.example.thinkpad.icompetition.presenter.i.IRegisterPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.RegisterActivity;

/**
 * Created By hjg on 2018/11/27
 */
public class RegisterPresenter
        extends BasePresenter<RegisterActivity, RegisterModel>
        implements IRegisterPresenter {

    public RegisterPresenter(RegisterActivity view) {
        super(view);
    }

    @Override
    public void register(String name, String pwd) {
        mModel.userRegister(name, pwd);
    }

    @Override
    protected RegisterModel getModel(Handler handler) {
        return new RegisterModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        RegisterRoot registerRoot = ((RegisterEvent)msg.obj).getRoot();
        if(registerRoot!=null) {
            switch (msg.what) {
                case RegisterEvent.REGISTE_OK:
                    mView.registeredReturn(registerRoot);
                    break;
                case RegisterEvent.REGISTE_FAIL:
                    mView.failBecauseNotNetworkReturn(msg.what);
                    break;
            }
        }
    }
}
