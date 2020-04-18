package com.example.thinkpad.icompetition.model.impl;

import android.os.Handler;

import com.example.thinkpad.icompetition.model.entity.search.IsConcernRoot;
import com.example.thinkpad.icompetition.model.event.FocusEvent;
import com.example.thinkpad.icompetition.model.i.IUserBySearchModel;
import com.example.thinkpad.icompetition.network.CallbackIntercept;
import com.example.thinkpad.icompetition.network.NetworkInterfaces;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;

public class UserBySearchModel extends BaseModel implements IUserBySearchModel {
    public UserBySearchModel(Handler handler) {
        super(handler);
    }

    @Override
    protected NetworkInterfaces getNetworkInterface() {
        return new NetworkInterfaces();
    }

    @Override
    public void getIsConcern(String other_num) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                IsConcernRoot root = gson.fromJson(jsonBody,IsConcernRoot.class);
                FocusEvent event = new FocusEvent();
                if(root!=null){
                    event.setRoot(root);
                    event.setWhat(FocusEvent.GET_ISCONCERN_OK);
                    postEvent(event);
                }else {
                    event.setWhat(FocusEvent.GET_ISCONCERN_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                FocusEvent event = new FocusEvent();
                event.setWhat(FocusEvent.GET_ISCONCERN_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.getIsConcern(callback,other_num);
    }

    @Override
    public void addConcern(String other_num) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                IsConcernRoot root = gson.fromJson(jsonBody,IsConcernRoot.class);
                FocusEvent event = new FocusEvent();
                if(root!=null){
                    event.setRoot(root);
                    event.setWhat(FocusEvent.ADD_CONCERN_OK);
                    postEvent(event);
                }else {
                    event.setWhat(FocusEvent.ADD_CONCERN_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                FocusEvent event = new FocusEvent();
                event.setWhat(FocusEvent.ADD_CONCERN_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.addConcern(callback,other_num);
    }

    @Override
    public void deleteConcern(String other_num) {
        Callback callback = new CallbackIntercept() {
            @Override
            public void onSuccess(Call call, String jsonBody) {
                Gson gson = new Gson();
                IsConcernRoot root = gson.fromJson(jsonBody,IsConcernRoot.class);
                FocusEvent event = new FocusEvent();
                if(root!=null){
                    event.setRoot(root);
                    event.setWhat(FocusEvent.DELETE_CONCERN_OK);
                    postEvent(event);
                }else {
                    event.setWhat(FocusEvent.DELETE_CONCERN_FAIL);
                    postEvent(event);
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                FocusEvent event = new FocusEvent();
                event.setWhat(FocusEvent.DELETE_CONCERN_FAIL);
                postEvent(event);
            }
        };
        mNetworkInterface.deleteConcern(callback,other_num);
    }
}
