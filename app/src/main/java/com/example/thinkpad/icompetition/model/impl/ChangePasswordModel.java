package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.user.ChangePasswordRoot;
import com.example.thinkpad.icompetition.model.event.ChangePasswordEvent;
import com.example.thinkpad.icompetition.model.i.IChangePasswordModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class ChangePasswordModel extends BaseModel implements IChangePasswordModel {
    public ChangePasswordModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                ChangePasswordRoot root = gson.fromJson(jsonBody,ChangePasswordRoot.class);
                if(root!=null){
                    ChangePasswordEvent event = new ChangePasswordEvent();
                    event.setRoot(root);
                    event.setWhat(ChangePasswordEvent.CHANGE_PASSWORD_OK);
                    postEvent(event);
                }
                else {
                    ChangePasswordEvent event = new ChangePasswordEvent();
                    event.setWhat(ChangePasswordEvent.CHANGE_PASSWORD_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                ChangePasswordEvent event = new ChangePasswordEvent();
                event.setWhat(ChangePasswordEvent.CHANGE_PASSWORD_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.changePassword(callback,oldPassword,newPassword);
    }
}
