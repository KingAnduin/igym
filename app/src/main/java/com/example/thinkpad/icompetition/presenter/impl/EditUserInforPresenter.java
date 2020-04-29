package com.example.thinkpad.icompetition.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.user.EditUserInforRoot;
import com.example.thinkpad.icompetition.model.entity.user.LoginRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;
import com.example.thinkpad.icompetition.model.event.EditUserInforEvent;
import com.example.thinkpad.icompetition.model.event.LoginEvent;
import com.example.thinkpad.icompetition.model.impl.EditUserInforModel;
import com.example.thinkpad.icompetition.presenter.i.IBasePresenter;
import com.example.thinkpad.icompetition.presenter.i.IEditUserInforPresenter;
import com.example.thinkpad.icompetition.view.activity.impl.EditUserInforActivity;
import com.google.gson.Gson;

public class EditUserInforPresenter extends BasePresenter<EditUserInforActivity,EditUserInforModel> implements IBasePresenter,IEditUserInforPresenter {
    public EditUserInforPresenter(EditUserInforActivity view) {
        super(view);
    }

    @Override
    protected EditUserInforModel getModel(Handler handler) {
        return new EditUserInforModel(handler);
    }

    @Override
    public void submitUserInfor(UserInforBean bean,String user_account) {
        mModel.submitUserInfor(bean,user_account);
    }

    @Override
    public void getUserInfor(String num) {
        mModel.getUserInfor(num);
    }

    @Override
    protected void eventReceive(Message msg) {
        switch (msg.what){
            case EditUserInforEvent.EDITUSERINFOR_OK:
                mView.getSubmitReturn(((EditUserInforEvent)msg.obj).getRoot());
                break;
            case EditUserInforEvent.EDITUSERINFOR_FAIL:
                mView.failBecauseNotNetworkReturn(msg.what);
                break;
            case LoginEvent.GETUERINFOR_OK:
                UserInforRoot root = ((LoginEvent)msg.obj).getUserInforRoot();
                mView.getUserInforReturn(root);
                mView.getSharedPreferences("ApplicationBase", Context.MODE_PRIVATE).edit().putLong("userNumber",root.getData().get(0).getId()).apply();
                //mView.getSharedPreferences("user", Context.MODE_PRIVATE).edit().putLong("userNumber",root.getData().get(0).getId()).apply();
                saveUserInforToDB(root.getData().get(0));
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
            mView.failBecauseNotNetworkReturn(-1);
        }
    }
}
