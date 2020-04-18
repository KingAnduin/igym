package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.user.LoginRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;
import com.example.thinkpad.icompetition.model.event.LoginEvent;
import com.example.thinkpad.icompetition.model.i.ILoginModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by Hjg on 2018/11/27.
 */
public class LoginModel extends BaseModel implements ILoginModel {
    public LoginModel(Handler handler) {
        super(handler);
    }

    //用户登陆
    @Override
    public void userLogin(String name, String pwd) {
        Callback callback = new CallbackIntercept(){

            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                LoginRoot root = gson.fromJson(jsonBody, LoginRoot.class);
                if(root!=null){
                    LoginEvent event = new LoginEvent();
                    event.setRoot(root);
                    event.setWhat(LoginEvent.LOGIN_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                LoginEvent event = new LoginEvent();
                event.setWhat(LoginEvent.LOGIN_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.userLogIn(callback, name, pwd);
    }

    @Override
    public void userGetInfor(String num) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                UserInforRoot root = gson.fromJson(jsonBody,UserInforRoot.class);
                if(root!=null){
                    LoginEvent event = new LoginEvent();
                    event.setUserInforRoot(root);
                    event.setWhat(LoginEvent.GETUERINFOR_OK);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                LoginEvent event = new LoginEvent();
                event.setWhat(LoginEvent.GETUSERINFOR_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.userInfor(callback,num);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

}
