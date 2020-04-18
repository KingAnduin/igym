package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;
import android.util.Log;

import com.example.thinkpad.icompetition.model.entity.user.EditUserInforRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;
import com.example.thinkpad.icompetition.model.event.EditUserInforEvent;
import com.example.thinkpad.icompetition.model.event.LoginEvent;
import com.example.thinkpad.icompetition.model.i.IBaseModel;
import com.example.thinkpad.icompetition.model.i.IEditUserInforModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class EditUserInforModel extends BaseModel implements IBaseModel,IEditUserInforModel {
    public EditUserInforModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    @Override
    public void submitUserInfor(UserInforBean bean,boolean haveHeadImage) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                System.out.println(jsonBody);
                Gson gson = new Gson();
                EditUserInforRoot root = gson.fromJson(jsonBody,EditUserInforRoot.class);
                if(root!=null) {
                    if(root.getCode()==200||root.getCode()==0) {
                        EditUserInforEvent event = new EditUserInforEvent();
                        event.setRoot(root);
                        event.setWhat(EditUserInforEvent.EDITUSERINFOR_OK);
                        postEvent(event);
                    }
                }
                else{
                    EditUserInforEvent event = new EditUserInforEvent();
                    event.setWhat(EditUserInforEvent.EDITUSERINFOR_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                //System.out.println(e);
                EditUserInforEvent event = new EditUserInforEvent();
                event.setWhat(EditUserInforEvent.EDITUSERINFOR_FAIL);
                postEvent(event);
            }
        };
        if(haveHeadImage){
            mNetworkInterface.submitUserInfor(callback,bean.getUser_name(),bean.getUser_sex(),bean.getUser_birthday(),bean.getUser_headimage());
        }
        else {
            mNetworkInterface.submitUserInforWithoutHeadImage(callback, bean.getUser_name(), bean.getUser_sex(), bean.getUser_birthday());
        }
    }
    @Override
    public void getUserInfor(String num) {
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
}
