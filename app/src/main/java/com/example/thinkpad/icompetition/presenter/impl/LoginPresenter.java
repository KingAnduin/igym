package com.example.thinkpad.icompetition.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.user.LoginRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;
import com.example.thinkpad.icompetition.model.event.LoginEvent;
import com.example.thinkpad.icompetition.model.impl.LoginModel;
import com.example.thinkpad.icompetition.presenter.i.ILoginPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.LoginActivity;

/**
 * Created by Hjg on 2018/11/27.
 */
public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel> implements ILoginPresenter {
    public LoginPresenter(LoginActivity view) {
        super(view);
    }

    @Override
    public void login(String name, String pwd) {
        mModel.userLogin(name, pwd);
    }

    @Override
    public void getUserInfor(String num) {
        mModel.userGetInfor(num);
    }

    @Override
    protected LoginModel getModel(Handler handler) {
        return new LoginModel(handler);
    }

    @Override
    protected void eventReceive(Message msg) {
        switch (msg.what){
            case LoginEvent.LOGIN_OK:
                LoginRoot root = ((LoginEvent) msg.obj).getRoot();
                mView.loginReturn(root);
                break;
            case LoginEvent.LOGIN_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
            case LoginEvent.GETUERINFOR_OK:
                UserInforRoot inforRoot = ((LoginEvent)msg.obj).getUserInforRoot();
                // TODO ?
                mView.getSharedPreferences("ApplicationBase", Context.MODE_PRIVATE).edit().putLong("userNumber",inforRoot.getData().get(0).getId()).apply();
                saveUserInforToDB(inforRoot.getData().get(0));
                mView.getUserInforReturn(inforRoot);
                break;
            case LoginEvent.GETUSERINFOR_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
        }
    }

    private void saveUserInforToDB(UserInforBean bean){
        if(bean!=null){
            if (mDaoSession.getUserInforBeanDao().count() == 0){
                mDaoSession.getUserInforBeanDao().insertOrReplace(bean);
            }else{
                mDaoSession.getUserInforBeanDao().deleteAll();
                mDaoSession.getUserInforBeanDao().insertOrReplace(bean);
            }
        }else{
            mView.failBecauseNullPointer();
        }
    }
}
