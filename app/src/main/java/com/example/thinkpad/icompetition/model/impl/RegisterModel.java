package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.user.RegisterRoot;
import com.example.thinkpad.icompetition.model.event.RegisterEvent;
import com.example.thinkpad.icompetition.model.i.IRegisterModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.example.thinkpad.icompetition.view.activity.impl.RegisterActivity;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Create By hjg on 2018/11/27
 */
public class RegisterModel extends BaseModel implements IRegisterModel {

    public RegisterModel(Handler handler) {
        super(handler);
    }

    //用户注册
    @Override
    public void userRegister(String name, String pwd) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                RegisterEvent event =new RegisterEvent();
                Gson gson = new Gson();
                RegisterRoot root = gson.fromJson(jsonBody,RegisterRoot.class);
                if(root!=null){
                    event.setWhat(RegisterEvent.REGISTE_OK);
                    event.setRoot(root);
                }
                else {
                    event.setWhat(RegisterEvent.REGISTE_FAIL);
                }
                postEvent(event);
            }

            @Override
            public void onFail(Call call, Exception e) {
                RegisterEvent event =new RegisterEvent();
                event.setWhat(RegisterEvent.REGISTE_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.userRegister(callback, name, pwd);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }
}
